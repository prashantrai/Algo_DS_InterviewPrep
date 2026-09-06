package Google;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RPCTimeoutDetection_RealTimeStream_FollowUp2 {

	public static void main(String[] args) throws Exception {

        /*
         * timeout = 3 seconds
         */
        RpcTimeoutMonitor monitor =
                new RpcTimeoutMonitor(3000);

        long now = System.currentTimeMillis();

        /*
         * RPC 1 starts.
         * expiration = now + 3000
         */
        monitor.onStart(1, now);

        Thread.sleep(1000);

        /*
         * RPC 2 starts one second later.
         * expiration = now + 4000
         */
        monitor.onStart(
                2,
                System.currentTimeMillis()
        );

        Thread.sleep(1000);

        /*
         * RPC 1 finishes before its timeout.
         *
         * Its heap entry remains stale,
         * but lazy deletion handles it.
         */
        monitor.onEnd(1);

        /*
         * No END for RPC 2.
         *
         * Even with no more RPC events,
         * the scheduler wakes up automatically
         * and reports RPC 2.
         */
        Thread.sleep(4000);

        monitor.shutdown();
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
	// Solution - extension of above. Every new/updated line have a comment as "Follow-up 2"
	
	/*
     * Real-time timeout monitor.
     *
     * HashMap:
     *   rpcId -> expiration timestamp
     *
     * MinHeap:
     *   earliest timeout first
     *
     * Scheduler:
     *   wakes us up when the next RPC should expire
     */
	
	// It is a complete solution with the Follow-up 2 changes already integrated.
	

    enum EventType {START, END }

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

        @Override
        public String toString() {
            return "(" + rpcId + ", " + timestamp + ")";
        }
    }

    // Follow-up 2 - Process a Real-Time Stream
    static class RpcTimeoutMonitor {

        private final Map<Integer, Long> active = new HashMap<>();

        private final PriorityQueue<long[]> minHeap =
                new PriorityQueue<>(
                        (a, b) -> {
                            int cmp = Long.compare(a[0], b[0]);

                            if (cmp != 0) {
                                return cmp;
                            }

                            return Long.compare(a[1], b[1]);
                        }
                );

        // Follow-up 2 - Process a Real-Time Stream
        private final ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        // Follow-up 2 - Process a Real-Time Stream
        private ScheduledFuture<?> scheduledTask;

        // Follow-up 2 - Process a Real-Time Stream
        private long scheduledExpiration = Long.MAX_VALUE;

        private final long timeout;

        RpcTimeoutMonitor(long timeout) {
            this.timeout = timeout;
        }

        /*
         * START:
         * 1. Calculate expiration.
         * 2. Add RPC to active map and heap.
         * 3. If this becomes the earliest timeout,
         *    reschedule the timer.
         */
        public synchronized void onStart(int rpcId, long startTimestamp) {

            long expiration = startTimestamp + timeout;

            active.put(rpcId, expiration);

            minHeap.offer(new long[]{
                    expiration,
                    rpcId
            });

            // Follow-up 2 - Process a Real-Time Stream
            if (expiration < scheduledExpiration) {
                scheduleNextTimeout();
            }
        }

        /*
         * END still uses lazy deletion.
         *
         * We remove only from active.
         * The stale heap entry is discarded later.
         */
        public synchronized void onEnd(int rpcId) {
            active.remove(rpcId);
        }

        // Follow-up 2 - Process a Real-Time Stream
        private synchronized void scheduleNextTimeout() {

            /*
             * Before looking at heap.peek(),
             * discard stale entries at the top.
             */
            removeStaleHeapEntries();

            /*
             * Cancel the old timer because the earliest
             * valid expiration may have changed.
             */
            if (scheduledTask != null) {
                scheduledTask.cancel(false);
                scheduledTask = null;
            }

            if (minHeap.isEmpty()) {
                scheduledExpiration = Long.MAX_VALUE;
                return;
            }

            /*
             * The heap top tells us exactly when
             * we need to wake up next.
             */
            scheduledExpiration = minHeap.peek()[0];

            long now = System.currentTimeMillis();

            long delay = Math.max(
                    0,
                    scheduledExpiration - now
            );

            scheduledTask = scheduler.schedule(
                    this::onTimerFired,
                    delay,
                    TimeUnit.MILLISECONDS
            );
        }

        // Follow-up 2 - Process a Real-Time Stream
        private synchronized void onTimerFired() {

            long now = System.currentTimeMillis();

            /*
             * The timer woke us up because at least one
             * RPC may have reached its expiration.
             *
             * Process every timeout that is due.
             */
            while (!minHeap.isEmpty()
                    && minHeap.peek()[0] <= now) {

                long[] entry = minHeap.poll();

                long expiration = entry[0];
                int rpcId = (int) entry[1];

                Long activeExpiration = active.get(rpcId);

                /*
                 * Still active and expiration matches:
                 * this is a real timeout.
                 *
                 * Otherwise this is a stale heap entry.
                 */
                if (activeExpiration != null
                        && activeExpiration == expiration) {

                    System.out.println(
                            "RPC " + rpcId
                                    + " timed out at " + expiration
                    );

                    active.remove(rpcId);
                }
            }

            /*
             * We processed the current earliest timeout.
             * Schedule the next valid heap entry.
             */
            scheduleNextTimeout();
        }

        // Follow-up 2 - Process a Real-Time Stream
        private void removeStaleHeapEntries() {

            while (!minHeap.isEmpty()) {

                long[] entry = minHeap.peek();

                long expiration = entry[0];
                int rpcId = (int) entry[1];

                Long activeExpiration = active.get(rpcId);

                /*
                 * Heap top is still valid.
                 */
                if (activeExpiration != null
                        && activeExpiration == expiration) {
                    break;
                }

                /*
                 * RPC already completed or this is an
                 * old expiration entry.
                 */
                minHeap.poll();
            }
        }

        // Follow-up 2 - Process a Real-Time Stream
        public synchronized void shutdown() {
            scheduler.shutdownNow();
        }
    }
	
	
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
