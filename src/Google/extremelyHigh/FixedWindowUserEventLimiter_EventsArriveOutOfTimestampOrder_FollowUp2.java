package Google.extremelyHigh;

import java.util.*;

public class FixedWindowUserEventLimiter_EventsArriveOutOfTimestampOrder_FollowUp2 {

    /** Follow-up 2 — Events Arrive Out of Timestamp Order */
    /*
    Key Insight:

    The priority queue solves the problem:

    arrival order != timestamp order

    The watermark solves the problem:

    When is it safe to finalize an event?

    The per-user deque still solves the original sliding-window problem:

    Which accepted events are currently inside this user's
    sliding window?

    Overall flow:

    Out-of-order arrivals
            |
            v
    PriorityQueue
            |
            | release events with timestamp <= watermark
            v
    Chronological event stream
            |
            v
    Map<User, Deque<Timestamp>>
            |
            v
    Sliding-window limiter
    */


    /*
    Interview Explanation Before Coding:

    In the previous sliding-window solution, the deque worked
    because events arrived in chronological timestamp order.

    With out-of-order events, I cannot immediately append an
    incoming event's timestamp to the user's deque because a
    future arrival may actually have an earlier timestamp.

    For example, if I process timestamp 100 first and later
    receive timestamp 70, that late event could affect what
    should have been considered inside the sliding window around
    timestamp 100.

    So I need a way to delay processing until I know it is safe.

    I will assume events can arrive at most D seconds late.

    I will:

    1. Buffer incoming events in a min-heap ordered by timestamp.

    2. Track the maximum event timestamp seen so far.

    3. Compute a watermark:

       watermark = maxTimestampSeen - allowedLateness

    4. Any buffered event whose timestamp is <= watermark is now
       safe to finalize, because under the bounded-lateness
       assumption, no earlier event should still arrive.

    5. I remove those events from the min-heap in chronological
       order and pass them into the same per-user deque logic
       from the sliding-window solution.

    For a finite batch input, once all input events have arrived,
    I flush the remaining events from the heap in timestamp order.

    Important interview point:

    If lateness is completely unbounded, exact irreversible
    online decisions are impossible because an arbitrarily old
    event could always arrive later.

    In that case, we would need to either:

    - wait indefinitely
    - use approximate semantics
    - or support correcting previously produced results.
    */


    /*
    Step-by-Step Algorithm:

    For every arriving event:

    1. Remember its original input position.

       We process events by timestamp order, but the final result
       should still correspond to the original arrival order.

       Store:

       PendingEvent:
           event
           originalIndex


    2. Buffer the event.

       Add the PendingEvent to a min-heap ordered by:

       event.timestamp

       The smallest timestamp will always be at the top.


    3. Update the maximum timestamp seen so far.

       maxTimestampSeen =
           Math.max(maxTimestampSeen, event.timestamp);


    4. Compute the watermark.

       watermark =
           maxTimestampSeen - allowedLateness;

       Any event at or before this timestamp is now considered
       safe to process.


    5. Finalize safe events.

       While:

       heap.peek().timestamp <= watermark

       remove the minimum event from the heap.

       Since this is a min-heap, released events are processed
       in chronological timestamp order.


    6. Apply the original sliding-window logic.

       For each released event at timestamp t:

       a. Get the user's deque of accepted timestamps.

          Deque<Long> timestamps = userState.get(userId);

       b. Calculate the expiration boundary.

          long cutoff = t - windowSize;

       c. Remove expired accepted timestamps from the front.

          Remove while:

          timestamps.peekFirst() <= cutoff

       d. After cleanup, the deque contains only accepted events
          inside:

          (t - windowSize, t]

       e. If:

          timestamps.size() < limit

          then:
          - accept the event
          - append t to the deque

       f. Otherwise:
          - reject the event
          - do not append its timestamp.


    7. Store the result using the event's original index.

       This lets us process by timestamp order while still
       returning answers in original arrival order.


    8. End of input.

       For a finite batch, no additional events can arrive.

       Therefore, remove and process every remaining event
       from the min-heap.

       Because the heap is ordered by timestamp, these remaining
       events are also finalized chronologically.
    */


    /*
    Complexity Analysis:

    Let:

    N = total number of events
    B = maximum number of events waiting in the out-of-order buffer
    U = number of users

    Heap Complexity:

    Every event is:

    - inserted into the min-heap exactly once
    - removed from the min-heap exactly once

    The heap contains at most B events.

    Each heap insertion/removal costs:

    O(log B)

    Therefore, total heap work is:

    O(N log B)


    Worst Case:

    If the buffer grows to contain all N events:

    B = N

    Then total time becomes:

    O(N log N)


    Sliding-Window Deque Complexity:

    For each user, every accepted timestamp is:

    - added to its deque once
    - removed from its deque at most once

    Therefore, deque processing is amortized:

    O(1) per event

    and:

    O(N) total

    The heap dominates the total runtime.


    Overall Time Complexity:

    O(N log B)

    Worst case:

    O(N log N)


    Space Complexity:

    The out-of-order buffer stores at most B pending events:

    O(B)

    The per-user sliding-window state stores at most 'limit'
    accepted timestamps per user:

    O(U * limit)

    The result array and original-index bookkeeping require:

    O(N)

    So if counting the returned result storage, total space is:

    O(N + B + U * limit)

    If output storage is excluded from auxiliary space:

    O(B + U * limit)


    For an offline batch where the heap may temporarily contain
    all input events:

    B = N

    So auxiliary space can become:

    O(N + U * limit)
    */
	
	static class Event {
        String userId;
        long timestamp;

        Event(String userId, long timestamp) {
            this.userId = userId;
            this.timestamp = timestamp;
        }
    }

    // Follow-up 2 — Events Arrive Out of Timestamp Order
    static class PendingEvent {
        Event event;
        int originalIndex;

        PendingEvent(Event event, int originalIndex) {
            this.event = event;
            this.originalIndex = originalIndex;
        }
    }
    
    /**
     * Interview script:
     * "Events can now arrive out of timestamp order, so I first buffer
     * them in a min-heap. A watermark tells me when an event is old
     * enough that it can safely be processed.
     *
     * Once an event is released from the heap, events are again processed
     * chronologically, so I can reuse the per-user sliding-window deque."
     *
     * Time:  O(N log B), where B is the maximum buffered events
     * Space: O(B + U * limit)
     */
    public static List<Boolean> limitEvents(List<Event> events,
            long windowSize, int limit, long allowedLateness) {
    	
    	// Follow-up 2 — Events Arrive Out of Timestamp Order
        Boolean[] answers = new Boolean[events.size()];

        /*
         * Follow-up 1 sliding-window state remains useful.
         * Once events leave the heap, they are processed in timestamp order.
         */
        Map<String, Deque<Long>> userState = new HashMap<>();
        
        
        // Follow-up 2 — Events Arrive Out of Timestamp Order
        
        /* TIP: If this PriorityQueue is created frequently (e.g., inside a method that
		   is called thousands of times per second), defining the lambda inline means
		   Java allocates a new comparator object every time. Extracting it to a static
		   final field avoids this allocation overhead.
		   
		   // Define once, reuse everywhere
		   private static final Comparator<PendingEvent> EVENT_COMPARATOR = 
		        Comparator.comparingLong((PendingEvent p) -> p.event.timestamp)
		                  .thenComparingInt(p -> p.originalIndex);
		                  
		   // Inside method, use as below
		   PriorityQueue<PendingEvent> minHeap = new PriorityQueue<>(EVENT_COMPARATOR);
		 */
        
        /* "The min-heap buffers events and lets me release them
         * in event-time order instead of arrival order."
         * 
         * MinHeap, sort by timestamp,and timestamp are same sort by index
         */
        PriorityQueue<PendingEvent> minPQ = new PriorityQueue<>(
        		Comparator.comparingLong((PendingEvent p) -> p.event.timestamp)
        		.thenComparingInt(p -> p.originalIndex)
        	);
        
        // Follow-up 2 — Events Arrive Out of Timestamp Order
        long maxTimestampSeen = Long.MIN_VALUE;
        
        for(int i=0; i<events.size(); i++) {
        	Event event = events.get(i);
        	
        	// Follow-up 2 — Events Arrive Out of Timestamp Order
        	minPQ.offer(new PendingEvent(event, i));
        	
        	/* "I track the largest event timestamp seen so far.
             * Together with bounded lateness, this gives me the watermark." 
             * */
        	maxTimestampSeen = Math.max(maxTimestampSeen, event.timestamp);
        	
        	long waterMark = maxTimestampSeen - allowedLateness;
        	
        	/* "Anything at or before the watermark is now safe to finalize,
             * so I process those events from the heap in timestamp order."
             */
        	while(!minPQ.isEmpty() 
        			&& minPQ.peek().event.timestamp <= waterMark) {
        		
        		PendingEvent pending = minPQ.poll();
        		
        		processEvent(pending, userState, answers, 
        									windowSize, limit );
        		
        	}
        } // for clonsed
    	
        // Follow-up 2 — Events Arrive Out of Timestamp Order
        /* 
         * "For this finite batch input, once all arrivals are known,
         * no additional late events can appear. I can therefore flush
         * the remaining heap in timestamp order."
         */
        while(!minPQ.isEmpty()) {
    		
    		PendingEvent pending = minPQ.poll();
    		
    		processEvent(pending, userState, answers, 
    									windowSize, limit );
    		
    	}
        
        return Arrays.asList(answers);
    }
    
    // Follow-up 2 — Events Arrive Out of Timestamp Order
    /**
     * "This is the same sliding-window logic as before.
     * The important difference is that this method only receives events
     * after the heap has restored chronological event-time order."
     */
    private static void processEvent(PendingEvent pending,
            Map<String, Deque<Long>> userState,
            Boolean[] answers, long windowSize, int limit) {
    	
    	Event event = pending.event;
    	
    	Deque<Long> timestamps = userState.computeIfAbsent(
    								event.userId, 
    								k -> new ArrayDeque<>());
    	
    	long cutOff = event.timestamp - windowSize;
    	
    	/* "Because finalized events are now processed chronologically,
         * expired accepted timestamps are again always at the front."
         */
    	while(!timestamps.isEmpty() && timestamps.peekFirst() <= cutOff) {
    		timestamps.removeFirst();
    	}
    	
    	/* "After expiration, the deque size tells me how many accepted
         * events are still consuming this user's sliding-window limit."
         */
    	if (timestamps.size() < limit) {

            timestamps.addLast(event.timestamp);

            answers[pending.originalIndex] = true;

        } else {

            // Rejected events do not consume capacity.
            answers[pending.originalIndex] = false;
        }
    }
	
    
    public static void main(String[] args) {

        // Follow-up 2 — Events Arrive Out of Timestamp Order
        /*
         * Test 1:
         * Events arrive out of order, but allowed lateness lets us
         * process them correctly in event-time order.
         */
        List<Event> events1 = Arrays.asList(
                new Event("A", 100),
                new Event("A", 70),
                new Event("A", 90),
                new Event("A", 65)
        );

        /*
         * Event-time order:
         *
         * 65
         * 70
         * 90
         * 100
         *
         * windowSize = 60
         * limit = 2
         *
         * 65  -> allowed
         * 70  -> allowed
         * 90  -> rejected
         * 100 -> rejected
         *
         * Returned in ORIGINAL ARRIVAL ORDER:
         *
         * 100 -> false
         * 70  -> true
         * 90  -> false
         * 65  -> true
         */
        List<Boolean> expected1 =
                Arrays.asList(false, true, false, true);

        List<Boolean> actual1 =
                limitEvents(
                        events1,
                        60,
                        2,
                        40
                );

        System.out.println("Test 1 - Out-of-order events");
        System.out.println("Expected: " + expected1);
        System.out.println("Actual:   " + actual1);
        System.out.println();


        /*
         * Test 2:
         * Multiple users remain independent.
         */
        List<Event> events2 = Arrays.asList(
                new Event("A", 20),
                new Event("B", 15),
                new Event("A", 10),
                new Event("B", 5),
                new Event("A", 30),
                new Event("B", 25)
        );

        /*
         * For user A, event-time order:
         *
         * 10 -> allowed
         * 20 -> allowed
         * 30 -> rejected
         *
         * For user B:
         *
         * 5  -> allowed
         * 15 -> allowed
         * 25 -> rejected
         *
         * Arrival-order results:
         *
         * A20 -> true
         * B15 -> true
         * A10 -> true
         * B5  -> true
         * A30 -> false
         * B25 -> false
         */
        List<Boolean> expected2 =
                Arrays.asList(
                        true,
                        true,
                        true,
                        true,
                        false,
                        false
                );

        List<Boolean> actual2 =
                limitEvents(
                        events2,
                        60,
                        2,
                        30
                );

        System.out.println("Test 2 - Multiple users");
        System.out.println("Expected: " + expected2);
        System.out.println("Actual:   " + actual2);
        System.out.println();


        /*
         * Test 3:
         * Expiration still works after restoring event-time order.
         */
        List<Event> events3 = Arrays.asList(
                new Event("A", 70),
                new Event("A", 10),
                new Event("A", 30),
                new Event("A", 20),
                new Event("A", 71)
        );

        /*
         * Event-time order:
         *
         * 10 -> allowed
         * 20 -> allowed
         * 30 -> allowed
         *
         * At 70:
         * active interval = (10,70]
         * 10 expires.
         *
         * active = [20,30]
         * 70 -> allowed
         *
         * At 71:
         * [20,30,70]
         * 71 -> rejected
         *
         * Arrival-order results:
         *
         * 70 -> true
         * 10 -> true
         * 30 -> true
         * 20 -> true
         * 71 -> false
         */
        List<Boolean> expected3 =
                Arrays.asList(
                        true,
                        true,
                        true,
                        true,
                        false
                );

        List<Boolean> actual3 =
                limitEvents(
                        events3,
                        60,
                        3,
                        60
                );

        System.out.println("Test 3 - Expiration with late events");
        System.out.println("Expected: " + expected3);
        System.out.println("Actual:   " + actual3);
        System.out.println();


        /*
         * Test 4:
         * Equal timestamps.
         *
         * For equal event timestamps, we preserve original arrival order.
         */
        List<Event> events4 = Arrays.asList(
                new Event("A", 100),
                new Event("A", 100),
                new Event("A", 100)
        );

        List<Boolean> expected4 =
                Arrays.asList(true, true, false);

        List<Boolean> actual4 =
                limitEvents(
                        events4,
                        60,
                        2,
                        10
                );

        System.out.println("Test 4 - Equal timestamps");
        System.out.println("Expected: " + expected4);
        System.out.println("Actual:   " + actual4);
        System.out.println();


        /*
         * Test 5:
         * Rejected late events must not consume capacity.
         */
        List<Event> events5 = Arrays.asList(
                new Event("A", 4),
                new Event("A", 1),
                new Event("A", 3),
                new Event("A", 2),
                new Event("A", 11),
                new Event("A", 12)
        );

        /*
         * Event-time order with windowSize = 10, limit = 2:
         *
         * 1  -> allowed
         * 2  -> allowed
         * 3  -> rejected
         * 4  -> rejected
         *
         * At 11:
         * interval = (1,11]
         * timestamp 1 expires.
         *
         * 11 -> allowed
         *
         * At 12:
         * timestamp 2 expires.
         *
         * 12 -> allowed
         *
         * Arrival-order results:
         *
         * 4  -> false
         * 1  -> true
         * 3  -> false
         * 2  -> true
         * 11 -> true
         * 12 -> true
         */
        List<Boolean> expected5 =
                Arrays.asList(
                        false,
                        true,
                        false,
                        true,
                        true,
                        true
                );

        List<Boolean> actual5 =
                limitEvents(
                        events5,
                        10,
                        2,
                        10
                );

        System.out.println("Test 5 - Rejected late events");
        System.out.println("Expected: " + expected5);
        System.out.println("Actual:   " + actual5);
    }
    
}


/*
 # Fixed-Window User Event Limiter

	**Reported:** September 1, 2026
	**Round:** Google coding interview; exact round not identified
	**Priority:** **EXTREMELY HIGH**
	
	## Problem
	
	You are given a list of user events in chronological order.
	
	Each event contains:
	
	```text
	userId
	timestamp
	```
	
	where `timestamp` is an integer representing seconds.
	
	Implement a fixed-window event limiter independently for each user.
	
	You are given:
	
	```text
	windowSize
	limit
	```
	
	For each user:
	
	* time is divided into fixed windows of length `windowSize`
	* at most `limit` events are allowed within each window
	* different users are tracked independently
	
	For every event, return whether the event should be:
	
	```text
	ALLOWED
	```
	
	or:
	
	```text
	REJECTED
	```
	
	Assume the input events are already sorted by timestamp in non-decreasing order.
	
	---
	
	## Fixed-Window Semantics
	
	Suppose:
	
	```text
	windowSize = 60 seconds
	```
	
	Then the windows are:
	
	```text
	[0, 60)
	[60, 120)
	[120, 180)
	...
	```
	
	Therefore:
	
	```text
	timestamp = 0   -> window 0
	timestamp = 59  -> window 0
	timestamp = 60  -> window 1
	timestamp = 119 -> window 1
	timestamp = 120 -> window 2
	```
	
	The window containing a timestamp can be identified using:
	
	```text
	timestamp / windowSize
	```
	
	using integer division.
	
	---
	
	# Example 1 — Single User
	
	Input:
	
	```text
	windowSize = 60
	limit = 3
	
	events:
	
	("A", 5)
	("A", 10)
	("A", 20)
	("A", 30)
	("A", 61)
	```
	
	Output:
	
	```text
	ALLOWED
	ALLOWED
	ALLOWED
	REJECTED
	ALLOWED
	```
	
	Explanation:
	
	The first four events belong to:
	
	```text
	[0, 60)
	```
	
	Only three events are allowed.
	
	So:
	
	```text
	5  -> allowed
	10 -> allowed
	20 -> allowed
	30 -> rejected
	```
	
	The event at:
	
	```text
	61
	```
	
	belongs to the next window:
	
	```text
	[60, 120)
	```
	
	so the user's count resets.
	
	---
	
	# Example 2 — Users Are Independent
	
	Input:
	
	```text
	windowSize = 60
	limit = 2
	
	events:
	
	("A", 10)
	("A", 20)
	("B", 25)
	("A", 30)
	("B", 40)
	("B", 50)
	```
	
	Output:
	
	```text
	ALLOWED
	ALLOWED
	ALLOWED
	REJECTED
	ALLOWED
	REJECTED
	```
	
	Explanation:
	
	User `A`:
	
	```text
	10 -> allowed
	20 -> allowed
	30 -> rejected
	```
	
	User `B` has a completely separate counter:
	
	```text
	25 -> allowed
	40 -> allowed
	50 -> rejected
	```
	
	The activity of one user must not affect another user.
	
	---
	
	# Example 3 — Window Boundary
	
	Input:
	
	```text
	windowSize = 60
	limit = 2
	
	events:
	
	("A", 58)
	("A", 59)
	("A", 60)
	("A", 61)
	```
	
	Output:
	
	```text
	ALLOWED
	ALLOWED
	ALLOWED
	ALLOWED
	```
	
	Explanation:
	
	```text
	58 and 59
	```
	
	belong to:
	
	```text
	[0, 60)
	```
	
	while:
	
	```text
	60 and 61
	```
	
	belong to:
	
	```text
	[60, 120)
	```
	
	An event exactly at the boundary starts a new window.
	
	This is an important edge case.
	
	---
	
	# Example 4 — Rejected Events Do Not Increase the Allowed Count
	
	Input:
	
	```text
	windowSize = 10
	limit = 2
	
	events:
	
	("A", 1)
	("A", 2)
	("A", 3)
	("A", 4)
	("A", 10)
	```
	
	Output:
	
	```text
	ALLOWED
	ALLOWED
	REJECTED
	REJECTED
	ALLOWED
	```
	
	The events at timestamps `3` and `4` are rejected because the user has already reached the limit for:
	
	```text
	[0, 10)
	```
	
	At timestamp `10`, a new window starts.
	
	---
	
	# Example 5 — Interleaved Users Across Multiple Windows
	
	Input:
	
	```text
	windowSize = 5
	limit = 2
	
	events:
	
	("A", 1)
	("B", 2)
	("A", 3)
	("B", 4)
	("A", 4)
	("B", 5)
	("A", 6)
	```
	
	Output:
	
	```text
	ALLOWED
	ALLOWED
	ALLOWED
	ALLOWED
	REJECTED
	ALLOWED
	ALLOWED
	```
	
	Explanation:
	
	For user `A`:
	
	```text
	1 -> allowed
	3 -> allowed
	4 -> rejected
	
	6 -> new window -> allowed
	```
	
	For user `B`:
	
	```text
	2 -> allowed
	4 -> allowed
	5 -> new window -> allowed
	```
	
	---
	
	# Expected API
	
	One possible API is:
	
	```java
	class Event {
	    String userId;
	    long timestamp;
	}
	
	List<Boolean> limitEvents(
	        List<Event> events,
	        long windowSize,
	        int limit);
	```
	
	where:
	
	```text
	true  = event is allowed
	false = event is rejected
	```
	
	The interviewer may instead ask for a streaming API such as:
	
	```java
	boolean allow(String userId, long timestamp);
	```
	
	Both formulations test the same core idea.
	
	---
	
	# Constraints
	
	A reasonable interview formulation could use:
	
	```text
	1 <= number of events <= 100,000
	1 <= windowSize
	1 <= limit
	timestamp >= 0
	```
	
	Events are provided in non-decreasing timestamp order.
	
	There may be a very large number of distinct users.
	
	---
	
	# Key Observation
	
	Each user only needs a small amount of state:
	
	```text
	current fixed window
	number of accepted events in that window
	```
	
	So we do not need to store every previous event.
	
	Maintain:
	
	```text
	Map<UserId, WindowState>
	```
	
	where:
	
	```text
	WindowState:
	    windowId
	    count
	```
	
	For each event:
	
	```text
	windowId = timestamp / windowSize
	```
	
	Then:
	
	```text
	if user has no state:
	    create state for this window
	    allow event
	
	else if windowId != user's current window:
	    reset count for the new window
	    allow event
	
	else if count < limit:
	    increment count
	    allow event
	
	else:
	    reject event
	```
	
	Because users are independent, every user's state is stored separately.
	
	---
	
	# Expected Complexity
	
	Let:
	
	```text
	N = number of events
	U = number of users currently stored
	```
	
	With a hash map:
	
	```text
	Time:  O(N) expected
	Space: O(U)
	```
	
	Each event requires only constant expected-time map operations.
	
	---
	
	# Closest LeetCode Problems
	
	There is no exact LeetCode match.
	
	Related problems:
	
	```text
	LC 359 — Logger Rate Limiter
	LC 362 — Design Hit Counter
	```
	
	LC 359 is conceptually related because it tracks state independently by key.
	
	LC 362 becomes particularly relevant when discussing sliding-window counting.
	
	---
	
	# Follow-up 1 — Sliding Window
	
	### Priority: **EXTREMELY HIGH**
	
	The fixed-window behavior has an important weakness.
	
	For example:
	
	```text
	windowSize = 60
	limit = 3
	```
	
	A user could send:
	
	```text
	57
	58
	59
	60
	61
	62
	```
	
	Under fixed windows:
	
	```text
	57,58,59 -> first window
	60,61,62 -> second window
	```
	
	so all six events may be allowed within only five seconds.
	
	Change the policy to:
	
	> At most `limit` events may occur during the previous `windowSize` seconds relative to the current event.
	
	For example:
	
	```text
	windowSize = 60
	limit = 3
	```
	
	Before accepting an event at timestamp:
	
	```text
	100
	```
	
	consider events in the active interval such as:
	
	```text
	(40, 100]
	```
	
	depending on the agreed boundary convention.
	
	Design the data structure so old timestamps can be removed efficiently.
	
	### Likely direction
	
	Maintain a queue or deque of accepted timestamps for each user.
	
	For an incoming event:
	
	```text
	remove timestamps outside the sliding window
	```
	
	Then:
	
	```text
	if queue.size() < limit:
	    allow the event
	    append timestamp
	else:
	    reject
	```
	
	With chronological input:
	
	```text
	amortized O(1) per event
	```
	
	because every timestamp is added and removed at most once.
	
	---
	
	# Follow-up 2 — Events Arrive Out of Timestamp Order
	
	### Priority: **VERY HIGH**
	
	Now events are not guaranteed to arrive chronologically.
	
	For example:
	
	```text
	("A", 100)
	("A", 70)
	("A", 90)
	("A", 65)
	```
	
	You can no longer assume that timestamps at the front of a normal queue are always the oldest relevant events.
	
	How would you redesign the solution?
	
	Things worth discussing include:
	
	```text
	ordered collections
	TreeMap
	balanced BST
	min-heap
	watermarks
	bounded lateness
	event time versus processing time
	```
	
	For a true streaming system, the interviewer may ask how long you wait for potentially late events before finalizing a window.
	
	---
	
	# Follow-up 3 — Millions of Users / Remove Stale State
	
	### Priority: **HIGH**
	
	Suppose millions of users generate events over time.
	
	Many users may become inactive permanently.
	
	The basic solution keeps:
	
	```text
	Map<UserId, WindowState>
	```
	
	forever, which can cause memory growth.
	
	How would you remove stale user state?
	
	Possible approaches include:
	
	```text
	TTL-based expiration
	periodic cleanup
	expiration heap
	timing wheel
	cache with idle expiration
	```
	
	For example, if a user's state has not been accessed for significantly longer than the required window, it may be safe to evict it.
	
	The interviewer may ask you to avoid scanning the entire user map periodically.
	
	---
	
	# Follow-up 4 — Distributed Rate Limiter
	
	### Priority: **MEDIUM**
	
	Now the service runs on many machines.
	
	Requests for the same user may arrive at different servers:
	
	```text
	Request 1 -> Server A
	Request 2 -> Server B
	Request 3 -> Server C
	```
	
	A process-local hash map is no longer sufficient.
	
	Discuss how you would enforce the rate limit consistently.
	
	Topics worth covering:
	
	```text
	shared Redis or another centralized store
	atomic increment
	TTL
	Lua scripts / transactions
	consistent hashing
	partitioning by userId
	race conditions
	availability versus strict correctness
	hot users
	replication
	failure handling
	```
	
	An interviewer may also ask whether approximate rate limiting is acceptable, because the answer can substantially change the system design.
	
	---
	
	# Why This Is a Strong Interview Problem
	
	The initial coding problem is intentionally straightforward:
	
	```text
	HashMap<UserId, WindowState>
	```
	
	but it tests several important fundamentals:
	
	```text
	correct interpretation of time windows
	integer boundary handling
	maintaining state independently by key
	choosing minimal state instead of storing all history
	hash-map based streaming processing
	```
	
	The follow-ups then increase the difficulty naturally:
	
	```text
	fixed window
	    ->
	sliding window
	    ->
	out-of-order events
	    ->
	state lifecycle and memory
	    ->
	distributed coordination
	```
	
	That makes it particularly suitable for an interview because the interviewer can stop at the level appropriate for the candidate.


 */