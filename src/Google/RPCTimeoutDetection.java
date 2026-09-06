package Google;

import java.util.*;

public class RPCTimeoutDetection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	/* Problem Summary
	 * We receive RPC lifecycle events in chronological order:
	 * 
	 * <rpcId, timestamp, START/END>
	 * 
	 * Each RPC times out after T time units if it has not completed.
	 * We need to return every RPC that times out, along with its timeout timestamp.
	 * 
	 * Important boundary rule: START at s timeout = s + T
	 * 
	 * If an END arrives exactly at s + T, the RPC successfully completes and should
	 * not be reported as timed out.
	 */
	
	/* What This Problem Really Is: 
		This is an active-items + earliest-expiration tracking problem.
		
		We need:

		HashMap       → Is this RPC still active?
		MinHeap       → Which active RPC expires next?
		
		
	  Pattern Recognition
		The clues are:
		
		START / END events
		        ↓
		Need to track currently active RPCs
		        ↓
		HashMap
		
		Need to repeatedly find earliest timeout
		        ↓
		MinHeap / PriorityQueue
		
		So the pattern is:
		
		Event Processing
		+
		HashMap
		+
		MinHeap ordered by expiration time
		+
		Lazy Deletion
		
		Why not only a HashMap?
		
		Because finding which RPC expires next would require scanning every active RPC:
		
		O(number of active RPCs)
		
		for every event.
		
		The heap gives us the earliest expiration in:
		
		O(1) peek
		O(log n) insertion/removal
		
	  
	  Key Insight
		For every START at time: start
		
		calculate:
			expiration = start + timeout
		
		and put it into:
		
		active map
		+
		min-heap
		
		For every incoming event at timestamp t, first remove/report all RPCs whose:
		
		expiration < t
		
		Notice: <  and not: <=
		
		because an RPC ending exactly at its expiration timestamp is considered successful.
		
		After processing all events, every RPC still in the heap and active map will 
		eventually timeout, so report those too.
		
	 * */
	
	/* Interview Explanation Before Coding: 

	A natural way to explain it:

	"I need two pieces of information. First, I need O(1) lookup to know 
	whether an RPC is still active, so I'll use a HashMap. Second, I need 
	to efficiently know which active RPC expires next, so I'll use a min-heap 
	ordered by expiration time.

	For every START, I'll calculate start + timeout, store it in both structures, 
	and for every END I'll remove the RPC from the active map.

	Before processing an event at timestamp t, I'll pop heap entries 
	with expiration strictly less than t. If the RPC is still in the 
	active map, it really timed out; otherwise it's a stale heap entry 
	from an RPC that already completed.

	I use strictly less than t because an END exactly at the timeout 
	timestamp is considered successful."

  *
  * Step-by-Step Algorithm: 

	For each event (rpcId, timestamp, type):

	Step 1 — Process expired RPCs
		While: 
			heap.peek().expiration < event.timestamp
	
		remove the heap entry.
	
		Then check whether that RPC is still active.
	
		If yes:
			report timeout
			remove from active map
	
		If no:
			ignore it
	
		because it already ended.

	Step 2 — Process the current event
		If:
			START
	
		calculate: expiration = timestamp + timeout
	
		and store:
			active.put(rpcId, expiration)
			heap.offer(...)
	
		If:
			END
	
		remove:
			active.remove(rpcId)
	
	Step 3 — After all events

		There may still be active RPCs.
	
		For example:
	
		START(5, 10)
	
		with no later events.
	
		Eventually this RPC will timeout.
	
		Therefore drain the remaining heap.
	
		For every heap entry:
	
		if still active
		    report timeout
	    
	*/
	
	/* Complexity
	Time: O(n log n)
		Each min-heap operation (add/remove) : O(log n).
		HashMap operations (put, get, remove) are O(1) average.
		So overall: O(n log n).
		
	Space: O(n)

	Interview answer: Time O(n log n), auxiliary space O(n) (excluding the output list).
	*/
	
	/** Solution Starts*/
	enum EventType {START, END}
	
	static class RpcEvent {
		int rpcId;
		long timestamp;
		EventType type;
		RpcEvent(int rpcId, long timestamp, EventType type) {
			this.rpcId = rpcId;
			this.timestamp = timestamp;
			this.type = type;
		}
	}

	static class Timeout {
		int rpcId;
		long timestamp;
		Timeout(int rpcId, long timestamp) {
			this.rpcId = rpcId;
			this.timestamp = timestamp;
		}
	}
	
	/*
     * HashMap:
     *   rpcId -> expiration timestamp
     *
     * MinHeap:
     *   keeps the RPC with the earliest expiration at the top.
     *
     * END removes the RPC only from the map.
     * Any remaining heap entry becomes stale and is ignored later.
     */
	private static List<Timeout> findTimedOutRpcs(
				List<RpcEvent> events, long timeout) {
		
		List<Timeout> result = new ArrayList<>();
		
		// rpcId -> expiration timestamp
		Map<Integer, Long> active = new HashMap<>();
		
		// [expirationTimestamp, rpcId]
		PriorityQueue<long[]> minPQ = new PriorityQueue<>((a, b) ->  {
			int cmp = Long.compare(a[0], b[0]);
			if(cmp != 0) {
				return cmp;
			} 
			return Long.compare(a[1], b[1]);
		});
		
		// works
		// PriorityQueue<long[]> minPQ3 = new PriorityQueue<>(
		//		Comparator.comparingLong((long[] a) -> a[0]).thenComparingLong(a -> a[1]));
		
		for(RpcEvent rpcEvent : events) {
			/* Before processing an event at time t,
             * report RPCs that expired strictly before t.
             *
             * We use < instead of <= because:
             *
             * START at 5, timeout = 3
             * expiration = 8
             *
             * END at 8 should complete successfully.
             */
			
			while (!minPQ.isEmpty() 
					&& minPQ.peek()[0] < rpcEvent.timestamp) {
				
				long[] expired = minPQ.poll();
				
				long expiration = expired[0];
				int rpcId = (int) expired[1];
				
				Long activationExpired = active.get(rpcId);
				
				/* If it is still active and this heap entry
                 * represents its current expiration,
                 * then the RPC really timed out.
                 *
                 * Otherwise this is a stale heap entry.
                 */
				if(activationExpired != null 
						&& activationExpired == expiration) {
					
					result.add(new Timeout(rpcId, expiration));
					
					// Once timed out, it is no longer active.
					active.remove(rpcId);
				}
			} // while closed
			
			if(rpcEvent.type == EventType.START) {
				long expiration = rpcEvent.timestamp + timeout;
				
				active.put(rpcEvent.rpcId, expiration);
				minPQ.offer(new long[] {expiration, rpcEvent.rpcId});
			} 
			else {
				// RPC completed successfully.
				active.remove(rpcEvent.rpcId);
			}
			
		} // for closed
		
		/* No more events are coming.
         *
         * Any RPC still active will eventually reach
         * its timeout timestamp, so process the heap.
         */
		while(!minPQ.isEmpty()) {
			long[] expired = minPQ.poll();
			
			long expiration = expired[0];
			int rpcId = (int) expired[1];
			
			Long activeExpiration = active.get(rpcId);
			
			if(activeExpiration != null 
					&& activeExpiration == expiration) {
				
				result.add(new Timeout(rpcId, expiration));
				active.remove(rpcId);
			}
		} // while closed
		
		return result;
	}
	
	
	/** Follow-ups */
	
	/* Follow-up 1 — Stale Heap Entries | Priority: EXTREMELY HIGH
		
		Suppose an RPC completes before its timeout: START(1, 0)
		
		expiration entry: (3, rpcId=1)
		
		END(1, 2)
		
		The heap still contains: (3, rpcId=1)
		
		How do you efficiently remove it?
		
		Expected Discussion
		Do not search the heap and delete arbitrary entries because that can 
		complicate the implementation and may require: O(n)
		
		Instead use lazy deletion.
		
		On END: remove rpcId from active map
		
		Later, when the heap entry reaches the top:
			if rpcId is no longer active:
			    discard the heap entry
		
		This keeps the implementation simple while preserving: O(log n) heap operations.
	 
	 
	 * ANSWER: 
	 * I wouldn't try to remove an arbitrary RPC from the priority queue when 
	 * its END arrives, because finding that heap entry can take O(n). 
	 * 
	 * Instead, I'd use lazy deletion. On END, I only remove the RPC from the 
	 * active HashMap. Its timeout entry can remain in the heap. When that entry 
	 * eventually reaches the top, I check whether the RPC is still active. 
	 * If it's not, it's a stale entry and I simply discard it. 
	 * 
	 * That keeps each heap insertion and removal O(log n), with O(1) 
	 * average-time state lookup. 
	 * 
	 * Our base solution already uses the correct answer: lazy deletion.
	 * 
	 * Specifically:
	 * 		// On END
			active.remove(event.rpcId);
	 * 
	 * We remove the RPC only from the HashMap, not from the heap.
	 * 
	 * Then later, when its old timeout entry reaches the top:
	 * 
	 * Long activeExpiration = active.get(rpcId);

		if (activeExpiration != null
		        && activeExpiration == expiration) {
		    // real timeout
		}
	 * 
	 * If the RPC already ended, active.get(rpcId) returns null, so that heap 
	 * entry is treated as stale and discarded.
	 * 
	 * So for Follow-up 1, no code change is needed.
	 * */
	
	
	/* Follow-up 2 — Process a Real-Time Stream | 	Priority: VERY HIGH
		
		The original problem only discovers that an RPC timed out when another event arrives.
		
		For example:
		
		RPC 1 START at 0
		timeout = 5
		
		Suppose no more RPC events arrive for several minutes.
		
		How would you report RPC 1 at timestamp 5 without waiting for another event?
		
		Expected Discussion
		
		The min-heap now acts as a timer queue.
		
		The system waits until the expiration time of the heap's minimum element:
		
		nextExpiration = heap.peek().expiration
		
		When the timer fires:
		
		remove every RPC whose expiration <= currentTime
		
		and report those still present in the active map.
		
		If a newly started RPC has an earlier expiration than the currently scheduled timer, the timer may need to be rescheduled.
		
		Conceptually:
		
		HashMap
		+
		MinHeap
		+
		Timer / scheduler
		
		This turns the offline event-processing solution into a real timeout-monitoring service.
	 * 
	 * */
	/** Solution - Look into file RPCTimeoutDetection_RealTimeStream_FollowUp2.java  */
	
	/* Follow-up 3 — Millions of Concurrent RPCs | Priority: HIGH

		Suppose the service handles: millions of active RPCs
		and maintaining one heap entry per request becomes expensive.
		
		How could you redesign the timeout mechanism?
		
		Expected Discussion
		If timeout values are relatively bounded or coarse-grained, consider:
		
		Timer Wheel / Timing Wheel
		
		Instead of globally ordering every timeout using a heap, divide future time into buckets.
		
		For example:
		
		bucket 0 -> RPCs expiring around second 100
		bucket 1 -> RPCs expiring around second 101
		bucket 2 -> RPCs expiring around second 102
		...
		
		As time advances, process the next bucket.
		
		This can reduce the cost of scheduling large numbers of timers compared with:
		
		O(log n)
		
		heap operations.
		
		A good interview comparison is:
		
		MinHeap: 
			simpler 
			exact ordering 
			O(log n) insertion/removal 
		
		Timing Wheel: 
			better for extremely large timer workloads 
			often near O(1) scheduling 
			trades some implementation complexity / timer granularity
	 
	 * */
	/*
	 * This follow-up is asking: “The min-heap works well, but does it still scale
	 * when there are millions of active RPC timers?”
	 * 
	 * The interviewer wants you to recognize that the current design has a per-RPC
	 * heap entry and O(log n) heap maintenance, which may become costly at very
	 * large scale.
	 * 
	 * What problem are we solving now?
	 * 
	 * Current design:
	 * 		Each RPC START -> one heap entry
	 * 		Millions of RPCs -> millions of heap entries
	 * 		Each insert/remove -> O(log n)
	 * 
	 */
	
	/*
	 * What I would say in the Google interview
	 * 
	 * “At millions of concurrent RPCs, I’d consider replacing the global min-heap
	 * with a timing wheel. A heap gives exact ordering but requires O(log n)
	 * insertion and removal for every timer. With a timing wheel, I divide time
	 * into fixed-size buckets and place each RPC directly into the bucket
	 * corresponding to its expiration, which is close to O(1). A periodic timer
	 * advances the wheel and processes the current bucket. I would keep the active
	 * HashMap and lazy deletion, so completed RPCs are simply ignored when their
	 * bucket is processed. The trade-off is timer granularity and additional
	 * complexity, and for long timeout ranges I’d use either rotation counts or a
	 * hierarchical timing wheel.”
	 * 
	 * For a Google phone screen, I would keep this primarily as a design discussion
	 * unless the interviewer explicitly asks you to implement the timing wheel.
	 * 
	 */
	
	
}


/* # C4. RPC Timeout Detection ### Priority: EXTREMELY HIGH

	You are given a chronologically ordered stream of RPC lifecycle events.
	
	Each event contains: <rpcId, timestamp, type>
	
	where:
	
	* `rpcId` uniquely identifies an RPC call.
	* `timestamp` is the time at which the event occurs.
	* `type` is either:
	
	  * `START` — the RPC begins.
	  * `END` — the RPC completes.
	
	You are also given a timeout threshold:
	
	```text
	T
	```
	
	An RPC is considered **timed out** if it has remained active for at least `T` time units.
	
	Your task is to report every RPC that times out.
	
	An RPC must be reported **as soon as the event stream reaches or passes its expiration time**, rather than waiting until its `END` event appears.
	
	Assume:
	
	* Events are sorted by `timestamp` in non-decreasing order.
	* Each RPC has at most one `START` and one `END`.
	* `END` always occurs after the corresponding `START`.
	* An RPC that finishes exactly at its timeout boundary is considered completed successfully if its `END` event occurs at that timestamp.
	
	---
	
	## Example 1
	
	### Input
	
	```text
	events =
	[
	    (1, 0, START),
	    (2, 1, START),
	    (1, 2, END),
	    (3, 6, START),
	    (2, 7, END)
	]
	
	T = 3
	```
	
	### Walkthrough
	
	```text
	RPC 1:
	START at 0
	expiration = 3
	END at 2
	=> completed before timeout
	
	RPC 2:
	START at 1
	expiration = 4
	
	The next event arrives at timestamp 6.
	
	Before processing timestamp 6, we know that RPC 2
	has already been active through timestamp 4.
	
	=> RPC 2 timed out at timestamp 4
	
	RPC 3:
	START at 6
	expiration = 9
	```
	
	### Output
	
	```text
	[(2, 4)]
	```
	
	where each result is:
	
	```text
	(rpcId, timeoutTimestamp)
	```
	
	---
	
	## Example 2 — RPC ends exactly at timeout
	
	```text
	events =
	[
	    (1, 5, START),
	    (1, 8, END)
	]
	
	T = 3
	```
	
	RPC 1 expires at:
	
	```text
	5 + 3 = 8
	```
	
	Since its `END` event occurs exactly at timestamp `8`, it completes successfully.
	
	### Output
	
	```text
	[]
	```
	
	---
	
	## Example 3 — Multiple RPCs timeout before the next event
	
	```text
	events =
	[
	    (10, 0, START),
	    (20, 1, START),
	    (30, 2, START),
	    (40, 10, START)
	]
	
	T = 4
	```
	
	Expiration times:
	
	```text
	RPC 10 -> 4
	RPC 20 -> 5
	RPC 30 -> 6
	```
	
	When the event at timestamp `10` arrives, all three earlier RPCs must already be reported as timed out.
	
	### Output
	
	```text
	[
	    (10, 4),
	    (20, 5),
	    (30, 6)
	]
	```
	
	---
	
	## Example 4 — Completion removes RPC from timeout consideration
	
	```text
	events =
	[
	    (1, 0, START),
	    (2, 1, START),
	    (2, 3, END),
	    (3, 7, START)
	]
	
	T = 5
	```
	
	Expiration times:
	
	```text
	RPC 1 -> 5
	RPC 2 -> 6
	```
	
	RPC 2 finishes at timestamp `3`, so it must not later be reported as timed out.
	
	When timestamp `7` is reached:
	
	```text
	RPC 1 -> timed out at 5
	```
	
	### Output
	
	```text
	[(1, 5)]
	```
	
	---
	
	# Suggested Method Signature
	
	```java
	List<Timeout> findTimedOutRpcs(List<RpcEvent> events, long timeout)
	```
	
	Possible supporting classes:
	
	```java
	class RpcEvent {
	    int rpcId;
	    long timestamp;
	    EventType type;
	}
	
	class Timeout {
	    int rpcId;
	    long timestamp;
	}
	```
	
	---
	
	# Expected Direction
	
	The key observation is that two different operations must be efficient:
	
	```text
	END event:
	    quickly determine whether an RPC is still active
	
	Timeout detection:
	    quickly find the active RPC whose timeout occurs next
	```
	
	A natural design is:
	
	```text
	HashMap
	    rpcId -> active RPC information
	
	+
	
	MinHeap
	    ordered by expirationTime
	```
	
	When processing an event at time:
	
	```text
	currentTime
	```
	
	first process any heap entries whose expiration time is strictly less than `currentTime`.
	
	Then process the event occurring at `currentTime`.
	
	This ordering is important because an `END` occurring exactly at:
	
	```text
	startTime + T
	```
	
	is considered successful.
	
	For every `START`:
	
	```text
	expiration = startTimestamp + T
	```
	
	Add the RPC to:
	
	```text
	active map
	+
	min-heap
	```
	
	For every `END`:
	
	```text
	remove rpcId from active map
	```
	
	When the minimum heap entry expires, verify that the RPC is still active before reporting it.
	
	This allows stale heap entries belonging to already-completed RPCs to be ignored.
	
	---
	
	# Complexity
	
	For `n` events:
	
	```text
	Time:
	O(n log n)
	
	Space:
	O(n)
	```
	
	Each RPC is inserted into the heap once and removed from it at most once.
	
	---
	
	# Closest LeetCode
	
	There is no direct LeetCode equivalent.
	
	Conceptually, it combines ideas from:
	
	```text
	HashMap for active state
	+
	PriorityQueue / MinHeap for earliest expiration
	+
	event-stream processing
	```
	
	---
	
	# Follow-up 1 — Stale Heap Entries
	
	### Priority: EXTREMELY HIGH
	
	Suppose an RPC completes before its timeout:
	
	```text
	START(1, 0)
	
	expiration entry:
	(3, rpcId=1)
	
	END(1, 2)
	```
	
	The heap still contains:
	
	```text
	(3, rpcId=1)
	```
	
	How do you efficiently remove it?
	
	### Expected Discussion
	
	Do **not** search the heap and delete arbitrary entries because that can complicate the implementation and may require:
	
	```text
	O(n)
	```
	
	Instead use lazy deletion.
	
	On `END`:
	
	```text
	remove rpcId from active map
	```
	
	Later, when the heap entry reaches the top:
	
	```text
	if rpcId is no longer active:
	    discard the heap entry
	```
	
	This keeps the implementation simple while preserving:
	
	```text
	O(log n)
	```
	
	heap operations.
	
	---
	
	# Follow-up 2 — Process a Real-Time Stream
	
	### Priority: VERY HIGH
	
	The original problem only discovers that an RPC timed out when another event arrives.
	
	For example:
	
	```text
	RPC 1 START at 0
	timeout = 5
	```
	
	Suppose no more RPC events arrive for several minutes.
	
	How would you report RPC 1 at timestamp `5` without waiting for another event?
	
	### Expected Discussion
	
	The min-heap now acts as a timer queue.
	
	The system waits until the expiration time of the heap's minimum element:
	
	```text
	nextExpiration = heap.peek().expiration
	```
	
	When the timer fires:
	
	```text
	remove every RPC whose expiration <= currentTime
	```
	
	and report those still present in the active map.
	
	If a newly started RPC has an earlier expiration than the currently scheduled timer, the timer may need to be rescheduled.
	
	Conceptually:
	
	```text
	HashMap
	+
	MinHeap
	+
	Timer / scheduler
	```
	
	This turns the offline event-processing solution into a real timeout-monitoring service.
	
	---
	
	# Follow-up 3 — Millions of Concurrent RPCs
	
	### Priority: HIGH
	
	Suppose the service handles:
	
	```text
	millions of active RPCs
	```
	
	and maintaining one heap entry per request becomes expensive.
	
	How could you redesign the timeout mechanism?
	
	### Expected Discussion
	
	If timeout values are relatively bounded or coarse-grained, consider:
	
	```text
	Timer Wheel / Timing Wheel
	```
	
	Instead of globally ordering every timeout using a heap, divide future time into buckets.
	
	For example:
	
	```text
	bucket 0 -> RPCs expiring around second 100
	bucket 1 -> RPCs expiring around second 101
	bucket 2 -> RPCs expiring around second 102
	...
	```
	
	As time advances, process the next bucket.
	
	This can reduce the cost of scheduling large numbers of timers compared with:
	
	```text
	O(log n)
	```
	
	heap operations.
	
	A good interview comparison is:
	
	```text
	MinHeap:
	    simpler
	    exact ordering
	    O(log n) insertion/removal
	
	Timing Wheel:
	    better for extremely large timer workloads
	    often near O(1) scheduling
	    trades some implementation complexity / timer granularity
	```
	
	---
	
	# Follow-up 4 — Events Arrive Out of Order
	
	### Priority: HIGH
	
	The original problem assumes events are chronologically ordered.
	
	Suppose events may arrive slightly out of order.
	
	For example:
	
	```text
	(1, 0, START)
	(2, 6, START)
	(1, 2, END)
	```
	
	The `END` event with timestamp `2` arrived after an event with timestamp `6`.
	
	How would you avoid incorrectly reporting RPC 1 as timed out?
	
	### Expected Discussion
	
	You can no longer immediately treat the latest observed timestamp as final.
	
	One approach is to introduce:
	
	```text
	event-time processing
	+
	bounded lateness
	+
	watermark
	```
	
	If events are guaranteed to arrive at most:
	
	```text
	D
	```
	
	time units late, maintain:
	
	```text
	watermark = maxTimestampSeen - D
	```
	
	Only declare an RPC timed out once its expiration is earlier than the watermark.
	
	Example:
	
	```text
	max timestamp seen = 10
	allowed lateness = 3
	
	watermark = 7
	```
	
	You may safely finalize timeout decisions only through timestamp `7`.
	
	This follow-up tests whether the candidate understands the difference between:
	
	```text
	event time
	vs
	processing time
	```
	
	and naturally extends the problem toward distributed stream-processing systems.

	
	Closest LC: none.
	
	VO Prep lists this as Google VO April 29, 2026. 
 * */
