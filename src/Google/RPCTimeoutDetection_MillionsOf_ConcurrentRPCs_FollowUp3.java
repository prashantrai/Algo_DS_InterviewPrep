package Google;

import java.util.*;

public class RPCTimeoutDetection_MillionsOf_ConcurrentRPCs_FollowUp3 {

	public static void main(String[] args) {

		List<RpcEvent> events = Arrays.asList(

				new RpcEvent(1, 0, EventType.START),

				new RpcEvent(2, 1, EventType.START),

				new RpcEvent(1, 2, EventType.END),

				new RpcEvent(3, 6, EventType.START),

				new RpcEvent(2, 7, EventType.END));

		List<Timeout> result = findTimedOutRpcs(events, 3);

		System.out.println(result);
	}

	
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
	/*
	 * For millions of concurrent RPCs, I’d first point out that the current
	 * min-heap solution is correct, but every START inserts into the heap in O(log
	 * n), and with millions of active timers that can become expensive in both CPU
	 * and memory overhead.
	 * 
	 * So I’d consider replacing the global min-heap with a Timing Wheel.
	 * 
	 * The idea is to divide future time into fixed-size buckets. For example, if
	 * each bucket represents one second, then RPCs expiring around second 100 go
	 * into one bucket, RPCs expiring around second 101 go into the next bucket, and
	 * so on.
	 * 
	 * For every START, I still compute:
	 * 
	 * expiration = startTime + timeout
	 * 
	 * But instead of inserting that expiration into a heap, I calculate which
	 * timing-wheel bucket corresponds to that expiration and append the RPC there.
	 * 
	 * That makes timer registration approximately O(1) instead of O(log n).
	 * 
	 * I would still keep the existing HashMap<rpcId, expiration> as the source of
	 * truth.
	 * 
	 * So on END, I would continue doing lazy deletion:
	 * 
	 * active.remove(rpcId)
	 * 
	 * I would not search the timing-wheel buckets to remove the RPC.
	 * 
	 * As time advances, a scheduler moves the timing-wheel pointer one bucket at a
	 * time.
	 * 
	 * When a bucket becomes current, I process all RPC entries in that bucket.
	 * 
	 * For each entry, I check the active map.
	 * 
	 * If the RPC is no longer active, then it already completed, so that
	 * timing-wheel entry is stale and I discard it.
	 * 
	 * If it is still active and its actual expiration time has been reached, then I
	 * report the timeout and remove it from the active map.
	 * 
	 * There is one additional issue if the timeout can be longer than one complete
	 * wheel rotation.
	 * 
	 * For example, if I have 60 one-second buckets, then bucket 5 is visited at 5
	 * seconds, 65 seconds, 125 seconds, and so on.
	 * 
	 * So I need either a remainingRounds value on each timer entry, or a
	 * hierarchical timing wheel.
	 * 
	 * With remainingRounds, I decrement the counter each time that bucket is
	 * visited and only expire the RPC when the counter reaches zero.
	 * 
	 * For a production-scale system with a very wide range of timeout durations,
	 * I’d likely use a hierarchical timing wheel, with coarse buckets for far-away
	 * timers and finer buckets as deadlines get closer.
	 * 
	 * The trade-off is that a heap gives exact global ordering and is simpler,
	 * while a timing wheel gives approximately O(1) scheduling and scales better
	 * for millions of timers, but introduces bucket granularity and more
	 * implementation complexity.
	 * 
	 * So the redesigned structure becomes:
	 * 
	 * HashMap + Timing Wheel + Periodic Scheduler
	 * 
	 * instead of:
	 * 
	 * HashMap + MinHeap
	 * 
	 * The main reason for the change is scalability: avoid maintaining a globally
	 * sorted heap for millions of timeout entries.
	 */
	
	
	/* Complexity
	Time: O(k),
		START / schedule RPC: approximately O(1)
			Compute bucket index: O(1)
			Append timer to bucket: O(1) amortized
			HashMap insert: O(1) average
		END: O(1) average
			Just remove the RPC from the active HashMap.
			We still use lazy deletion.
		Processing a bucket: O(k)
			k = number of timer entries in that bucket.
		
	Space: O(n + W)
		n = number of active/pending timer entries
		W = number of timing-wheel buckets

	Scheduling and cancellation are approximately O(1) average instead of 
	the heap's O(log n). Processing a wheel bucket is O(k) for the entries 
	in that bucket. Space is O(n + W), which is effectively O(n) when 
	the wheel size is fixed.”
	*/
	
	
	/* NOTE::
	 * For a Google phone screen, I would keep this primarily as a design discussion
	 * unless the interviewer explicitly
	 * */
	
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
	
	// Follow-up 3 - Millions of Concurrent RPCs
    static class TimerEntry {

        int rpcId;
        long expiration;

        /*
         * Number of complete wheel rotations that must happen
         * before this timer can expire.
         */
        long remainingRounds;

        TimerEntry(int rpcId, long expiration, long remainingRounds) {
            this.rpcId = rpcId;
            this.expiration = expiration;
            this.remainingRounds = remainingRounds;
        }
    }

    // Follow-up 3 - Millions of Concurrent RPCs
    static class TimingWheel {

        /*
         * Example:
         *
         * wheelSize = 60
         * tickSize  = 1
         *
         * means:
         *
         * 60 buckets
         * each representing one time unit.
         */
        private final int wheelSize;
        private final long tickSize;

        private final List<List<TimerEntry>> buckets;

        private int currentBucket;
        private long currentTime;

        /*
         * rpcId -> expiration
         *
         * This remains the source of truth.
         * END simply removes the RPC from this map.
         */
        private final Map<Integer, Long> active;

        private final List<Timeout> result;

        TimingWheel(
                int wheelSize,
                long tickSize,
                long startTime,
                Map<Integer, Long> active,
                List<Timeout> result) {

            this.wheelSize = wheelSize;
            this.tickSize = tickSize;
            this.currentTime = startTime;
            this.currentBucket = 0;

            this.active = active;
            this.result = result;

            buckets = new ArrayList<>();

            for (int i = 0; i < wheelSize; i++) {
                buckets.add(new ArrayList<>());
            }
        }

        // Follow-up 3 - Millions of Concurrent RPCs
        public void add(int rpcId, long expiration) {

            long delay = expiration - currentTime;

            /*
             * Number of wheel ticks before expiration.
             */
            long ticks = Math.max(0,
                    (delay + tickSize - 1) / tickSize);

            /*
             * Determine which bucket receives the timer.
             */
            int bucketIndex =
                    (int) ((currentBucket + ticks) % wheelSize);

            /*
             * Determine how many complete wheel rotations
             * must occur before processing this timer.
             */
            long rounds = ticks / wheelSize;

            /*
             * If the expiration lands exactly on a future
             * rotation of the same bucket, avoid waiting
             * one extra complete rotation.
             */
            if (ticks > 0 && ticks % wheelSize == 0) {
                rounds--;
            }

            buckets.get(bucketIndex).add(
                    new TimerEntry(
                            rpcId,
                            expiration,
                            rounds
                    )
            );
        }

        // Follow-up 3 - Millions of Concurrent RPCs
        public void advanceTo(long targetTime) {

            /*
             * Move the wheel forward one tick at a time
             * until we reach the target timestamp.
             */
            while (currentTime + tickSize <= targetTime) {

                currentTime += tickSize;

                currentBucket =
                        (currentBucket + 1) % wheelSize;

                processCurrentBucket();
            }
        }

        // Follow-up 3 - Millions of Concurrent RPCs
        private void processCurrentBucket() {

            List<TimerEntry> bucket =
                    buckets.get(currentBucket);

            if (bucket.isEmpty()) {
                return;
            }

            /*
             * Build the list of timers that must remain
             * in this bucket for another wheel rotation.
             */
            List<TimerEntry> remaining =
                    new ArrayList<>();

            for (TimerEntry entry : bucket) {

                if (entry.remainingRounds > 0) {

                    entry.remainingRounds--;

                    remaining.add(entry);

                    continue;
                }

                Long activeExpiration =
                        active.get(entry.rpcId);

                /*
                 * Lazy deletion:
                 *
                 * If the RPC already ended, activeExpiration
                 * is null and the timer entry is stale.
                 *
                 * We also compare expiration in case the same
                 * RPC id was reused later.
                 */
                if (activeExpiration != null
                        && activeExpiration == entry.expiration) {

                    /*
                     * Because a timing wheel can have coarse
                     * buckets, verify the actual expiration.
                     */
                    if (entry.expiration <= currentTime) {

                        result.add(
                                new Timeout(
                                        entry.rpcId,
                                        entry.expiration
                                )
                        );

                        active.remove(entry.rpcId);

                    } else {

                        /*
                         * Bucket granularity placed this timer
                         * slightly early. Reinsert it.
                         */
                        remaining.add(entry);
                    }
                }
            }

            buckets.set(currentBucket, remaining);
        }

        // Follow-up 3 - Millions of Concurrent RPCs
        public void drain() {

            /*
             * Continue advancing until there are
             * no active RPCs remaining.
             */
            while (!active.isEmpty()) {
                advanceTo(currentTime + tickSize);
            }
        }
    }

    public static List<Timeout> findTimedOutRpcs(
            List<RpcEvent> events,
            long timeout) {

        List<Timeout> result = new ArrayList<>();

        Map<Integer, Long> active =
                new HashMap<>();

        if (events.isEmpty()) {
            return result;
        }

        /*
         * Follow-up 3 - Millions of Concurrent RPCs
         *
         * Replace PriorityQueue with TimingWheel.
         *
         * Example configuration:
         *
         * 60 buckets
         * 1 timestamp unit per bucket.
         *
         * In a real service this could instead be:
         *
         * 512 buckets
         * 100 ms per bucket
         */
        TimingWheel wheel =
                new TimingWheel(
                        60,
                        1,
                        events.get(0).timestamp,
                        active,
                        result
                );

        for (RpcEvent event : events) {

            /*
             * Follow-up 3 - Millions of Concurrent RPCs
             *
             * Advance the wheel based on event time
             * instead of popping from a min-heap.
             */
            wheel.advanceTo(event.timestamp);

            if (event.type == EventType.START) {

                long expiration =
                        event.timestamp + timeout;

                active.put(
                        event.rpcId,
                        expiration
                );

                // Follow-up 3 - Millions of Concurrent RPCs
                wheel.add(
                        event.rpcId,
                        expiration
                );

            } else {

                /*
                 * Lazy deletion remains unchanged.
                 *
                 * We don't search timing-wheel buckets.
                 */
                active.remove(event.rpcId);
            }
        }

        /*
         * Follow-up 3 - Millions of Concurrent RPCs
         *
         * Any RPC remaining active eventually times out.
         */
        wheel.drain();

        return result;
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
