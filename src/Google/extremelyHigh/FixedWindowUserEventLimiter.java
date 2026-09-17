package Google.extremelyHigh;

import java.util.*;

public class FixedWindowUserEventLimiter {

	public static void main(String[] args) {
		//main_FixedWin(); // Base/main problem - working
		main_FollowUp_1_SlidingWin(); // working
	}
	
	private static void main_FixedWin() {

        // Test 1: Basic single-user example.
        List<Event> events1 = Arrays.asList(
                new Event("A", 5),
                new Event("A", 10),
                new Event("A", 20),
                new Event("A", 30),
                new Event("A", 61)
        );

        List<Boolean> expected1 =
                Arrays.asList(true, true, true, false, true);

        List<Boolean> actual1 =
                limitEvents(events1, 60, 3);

        System.out.println("Test 1");
        System.out.println("Expected: " + expected1);
        System.out.println("Actual:   " + actual1);
        System.out.println();


        // Test 2: Different users must have independent limits.
        List<Event> events2 = Arrays.asList(
                new Event("A", 10),
                new Event("A", 20),
                new Event("B", 25),
                new Event("A", 30),
                new Event("B", 40),
                new Event("B", 50)
        );

        List<Boolean> expected2 =
                Arrays.asList(true, true, true, false, true, false);

        List<Boolean> actual2 =
                limitEvents(events2, 60, 2);

        System.out.println("Test 2");
        System.out.println("Expected: " + expected2);
        System.out.println("Actual:   " + actual2);
        System.out.println();


        // Test 3: Exact window boundary.
        List<Event> events3 = Arrays.asList(
                new Event("A", 58),
                new Event("A", 59),
                new Event("A", 60),
                new Event("A", 61)
        );

        List<Boolean> expected3 =
                Arrays.asList(true, true, true, true);

        List<Boolean> actual3 =
                limitEvents(events3, 60, 2);

        System.out.println("Test 3");
        System.out.println("Expected: " + expected3);
        System.out.println("Actual:   " + actual3);
        System.out.println();


        // Test 4: Rejected events must not increase the count.
        List<Event> events4 = Arrays.asList(
                new Event("A", 1),
                new Event("A", 2),
                new Event("A", 3),
                new Event("A", 4),
                new Event("A", 10)
        );

        List<Boolean> expected4 =
                Arrays.asList(true, true, false, false, true);

        List<Boolean> actual4 =
                limitEvents(events4, 10, 2);

        System.out.println("Test 4");
        System.out.println("Expected: " + expected4);
        System.out.println("Actual:   " + actual4);
        System.out.println();


        // Test 5: Interleaved users across multiple windows.
        List<Event> events5 = Arrays.asList(
                new Event("A", 1),
                new Event("B", 2),
                new Event("A", 3),
                new Event("B", 4),
                new Event("A", 4),
                new Event("B", 5),
                new Event("A", 6)
        );

        List<Boolean> expected5 =
                Arrays.asList(true, true, true, true, false, true, true);

        List<Boolean> actual5 =
                limitEvents(events5, 5, 2);

        System.out.println("Test 5");
        System.out.println("Expected: " + expected5);
        System.out.println("Actual:   " + actual5);
        System.out.println();


        // Test 6: Limit of one event per window.
        List<Event> events6 = Arrays.asList(
                new Event("A", 0),
                new Event("A", 1),
                new Event("A", 9),
                new Event("A", 10),
                new Event("A", 19),
                new Event("A", 20)
        );

        List<Boolean> expected6 =
                Arrays.asList(true, false, false, true, false, true);

        List<Boolean> actual6 =
                limitEvents(events6, 10, 1);

        System.out.println("Test 6");
        System.out.println("Expected: " + expected6);
        System.out.println("Actual:   " + actual6);
    }
	
	
	private static void main_FollowUp_1_SlidingWin() {

        // Follow-up 1 — Sliding Window
        /*
         * Test 1:
         * Shows the important difference from fixed windows.
         *
         * At timestamp 60, timestamps 57, 58 and 59 are all
         * still inside the previous 60 seconds.
         */
        List<Event> events1 = Arrays.asList(
                new Event("A", 57),
                new Event("A", 58),
                new Event("A", 59),
                new Event("A", 60),
                new Event("A", 61),
                new Event("A", 62)
        );

        List<Boolean> expected1 =
                Arrays.asList(true, true, true, false, false, false);

        List<Boolean> actual1 =
        		limitEvents_SlidingWin(events1, 60, 3);

        System.out.println("Test 1 - Sliding window vs fixed window");
        System.out.println("Expected: " + expected1);
        System.out.println("Actual:   " + actual1);
        System.out.println();


        /*
         * Test 2:
         * Old events eventually expire and create capacity again.
         */
        List<Event> events2 = Arrays.asList(
                new Event("A", 10),
                new Event("A", 20),
                new Event("A", 30),
                new Event("A", 40),
                new Event("A", 70),
                new Event("A", 71)
        );

        /*
         * windowSize = 60, limit = 3
         *
         * 10 -> allowed
         * 20 -> allowed
         * 30 -> allowed
         * 40 -> rejected
         *
         * At 70:
         * window is (10, 70]
         * timestamp 10 expires.
         *
         * Active accepted events = [20, 30]
         * so 70 is allowed.
         *
         * At 71:
         * active = [20, 30, 70]
         * so rejected.
         */
        List<Boolean> expected2 =
                Arrays.asList(true, true, true, false, true, false);

        List<Boolean> actual2 =
        		limitEvents_SlidingWin(events2, 60, 3);

        System.out.println("Test 2 - Expired timestamp creates capacity");
        System.out.println("Expected: " + expected2);
        System.out.println("Actual:   " + actual2);
        System.out.println();


        /*
         * Test 3:
         * Multiple users have completely independent sliding windows.
         */
        List<Event> events3 = Arrays.asList(
                new Event("A", 1),
                new Event("B", 2),
                new Event("A", 3),
                new Event("B", 4),
                new Event("A", 5),
                new Event("B", 6),
                new Event("A", 11),
                new Event("B", 12)
        );

        List<Boolean> expected3 =
                Arrays.asList(
                        true,
                        true,
                        true,
                        true,
                        false,
                        false,
                        true,
                        true
                );

        List<Boolean> actual3 =
        		limitEvents_SlidingWin(events3, 10, 2);

        System.out.println("Test 3 - Independent users");
        System.out.println("Expected: " + expected3);
        System.out.println("Actual:   " + actual3);
        System.out.println();


        /*
         * Test 4:
         * Exact sliding-window boundary.
         *
         * At t = 60 with windowSize = 60:
         *
         * active interval = (0, 60]
         *
         * Therefore timestamp 0 expires exactly at timestamp 60.
         */
        List<Event> events4 = Arrays.asList(
                new Event("A", 0),
                new Event("A", 59),
                new Event("A", 60),
                new Event("A", 61)
        );

        List<Boolean> expected4 =
                Arrays.asList(true, true, true, false);

        List<Boolean> actual4 =
        		limitEvents_SlidingWin(events4, 60, 2);

        System.out.println("Test 4 - Exact expiration boundary");
        System.out.println("Expected: " + expected4);
        System.out.println("Actual:   " + actual4);
        System.out.println();


        /*
         * Test 5:
         * Rejected events do not consume future capacity.
         */
        List<Event> events5 = Arrays.asList(
                new Event("A", 1),
                new Event("A", 2),
                new Event("A", 3),
                new Event("A", 4),
                new Event("A", 11),
                new Event("A", 12)
        );

        /*
         * windowSize = 10
         * limit = 2
         *
         * 1 -> allowed
         * 2 -> allowed
         * 3 -> rejected
         * 4 -> rejected
         *
         * At 11:
         * active interval = (1,11]
         * timestamp 1 expires.
         * 3 and 4 never entered the deque because they were rejected.
         *
         * Remaining = [2]
         * 11 -> allowed
         *
         * At 12:
         * timestamp 2 expires.
         * 12 -> allowed
         */
        List<Boolean> expected5 =
                Arrays.asList(true, true, false, false, true, true);

        List<Boolean> actual5 =
        		limitEvents_SlidingWin(events5, 10, 2);

        System.out.println("Test 5 - Rejected events do not consume capacity");
        System.out.println("Expected: " + expected5);
        System.out.println("Actual:   " + actual5);
        System.out.println();


        /*
         * Test 6:
         * limit = 1.
         */
        List<Event> events6 = Arrays.asList(
                new Event("A", 0),
                new Event("A", 5),
                new Event("A", 9),
                new Event("A", 10),
                new Event("A", 19),
                new Event("A", 20)
        );

        List<Boolean> expected6 =
                Arrays.asList(true, false, false, true, false, true);

        List<Boolean> actual6 =
        		limitEvents_SlidingWin(events6, 10, 1);

        System.out.println("Test 6 - Limit of one");
        System.out.println("Expected: " + expected6);
        System.out.println("Actual:   " + actual6);
    }
	
	
	/*
	 * Interview Explanation Before Coding
	 * 
	 * You can say this naturally during the interview:
	 * 
	 * Since this is a fixed-window limiter, I don't need to store all previous
	 * timestamps. I can identify the fixed window for an event using timestamp /
	 * windowSize.
	 * 
	 * I'll maintain a hash map from user ID to that user's current window ID and
	 * the number of events already accepted in that window.
	 * 
	 * When an event arrives, if the user hasn't been seen before, I'll create their
	 * state and allow the event. If the event belongs to a newer window, I'll reset
	 * their count for that window. Otherwise, if we're still in the same window,
	 * I'll allow the event only if the count is below the limit.
	 * 
	 * Since the users are independent, each user gets their own state in the map.
	 * 
	 * 
	 * If the interviewer asks about the timestamp boundary:
	 * 
	 * With integer division, timestamps 0 through 59 belong to window 0, and
	 * timestamp 60 starts window 1, so the boundary behavior comes naturally from
	 * the window ID calculation.
	 */
	
	/* Step-by-Step Algorithm:

	For every event:

	1. Calculate the fixed window for the event.
	   long windowId = event.timestamp / windowSize;

	2. Look up the current state for this user.
	   WindowState state = userState.get(event.userId);

	3. If the user has no existing state:
	   - Create a new WindowState.
	   - Store the current windowId.
	   - Set count = 1 because this event is allowed.
	   - Allow the event.

	4. If the stored windowId is different from the event's windowId:
	   - The user has entered a new fixed window.
	   - Update the stored windowId.
	   - Reset count = 1 for the current event.
	   - Allow the event.

	5. Otherwise, the event belongs to the same fixed window:
	   - If count < limit:
	       increment count
	       allow the event
	   - Otherwise:
	       the user has already reached the limit
	       reject the event.
	*/


	/* Complexity Analysis:

	Let:
	N = total number of events
	U = number of distinct users

	For every event, we perform an expected O(1) HashMap
	lookup and possibly an O(1) update.

	Time Complexity:
	O(N) expected

	Space Complexity:
	O(U)

	For a streaming API where events are processed one at a time:

	Time per event:
	O(1) expected

	Space:
	O(U)

	We need to maintain at least one small amount of state
	for every distinct/active user, so O(U) space is optimal
	for this approach.
	*/
	
	
	static class Event {
		String userId; 
		long timestamp;
		
		Event(String userId, long timestamp) {
			this.userId = userId;
			this.timestamp = timestamp;
		}
	}
	static class WindowState {
		long windowId;
		int count;
		
		WindowState(long windowId, int count) {
            this.windowId = windowId;
            this.count = count;
        }
	}
	
	/* "For each user, I only keep the current fixed window
     * and how many events have already been accepted in it."
     *
     * Time:  O(N) expected
     * Space: O(U), where U is the number of distinct users.
     */
	public static List<Boolean> limitEvents(List<Event> events, long windowSize, int limit) {
		
		List<Boolean> result = new ArrayList<>();
		
		// Each user has an independent window and accepted-event count.
		Map<String, WindowState> userState = new HashMap<>();
		
		for(Event event : events) {
			/*"Integer division gives me the fixed window directly.
             * For windowSize = 60, timestamps 0-59 are window 0,
             * 60-119 are window 1, and so on."
             */
			long windowId = event.timestamp / windowSize;
			WindowState state = userState.get(event.userId);
			
			/* "This is the user's first event, so I create the state
             * for this window and consume one slot."
             */
			if(state == null) {
				WindowState winState = new WindowState(windowId, 1);
				userState.put(event.userId, winState);
				
				result.add(true); // ALLOW
				continue;
			}
			
            /* "If the window changed, the previous count no longer
             * matters. I reset this user's state for the new window."
             */
			if(windowId != state.windowId) {
				state.windowId = windowId;
				state.count = 1;
				
				result.add(true); // ALLOW
				continue;
			}
			
			/* "We're still in the same window.
             * If the user has capacity left, allow the event
             * and increase the accepted-event count." */
			if(state.count < limit) {
				state.count++;
				result.add(true); // ALLOW
				continue;
			}
			
			/* "The user already reached the limit.
             * Reject the event and do not increase the count." */
			result.add(false); // REJECT
		}
		
		return result;
	}
	
	
	/** Follow-up 1 — Sliding Window */
	
	/* Interview Explanation Before Coding:

	In the fixed-window solution, I only needed to store a
	window ID and a count because the window boundaries were
	predetermined.

	With a sliding window, every incoming event effectively
	creates a window ending at its own timestamp. Therefore,
	I need to know when the user's previous accepted events
	occurred.

	Since events arrive in chronological order, I can maintain
	a deque of accepted timestamps for each user.

	For an event arriving at timestamp t:

	1. Remove timestamps from the front of the deque that are
	   no longer inside the last windowSize seconds.

	2. After removing expired timestamps, the deque size tells
	   me how many accepted events are currently inside the
	   sliding window.

	3. If deque.size() < limit:
	   - Allow the event.
	   - Add its timestamp to the back of the deque.

	4. Otherwise:
	   - Reject the event.
	   - Do not add its timestamp to the deque.

	The deque works well because timestamps are chronological,
	so expired events will always be at the front.

	Even though removing expired timestamps uses a while loop,
	this does NOT make the overall solution O(N^2).

	Each accepted timestamp is:
	- added to the deque exactly once
	- removed from the deque at most once

	Therefore, across all N events, the total deque work is O(N),
	which gives O(1) amortized processing time per event.
	*/
	
	/* Step-by-Step Algorithm:

	For every event:

	1. Get this user's deque of accepted timestamps.
	   Deque<Long> timestamps = map.get(userId);

	   If the user does not exist yet, create a new empty deque.

	2. Calculate the expiration boundary.

	   long cutoff = currentTimestamp - windowSize;

	3. Remove expired events from the front of the deque.

	   While:
	   oldestTimestamp <= cutoff

	   remove that timestamp from the front.

	   After this cleanup, every timestamp remaining in the deque
	   belongs to the active sliding window:

	   (currentTimestamp - windowSize, currentTimestamp]

	4. Check whether the user is still below the rate limit.

	   If:
	   timestamps.size() < limit

	   then there is capacity, so allow the event.

	5. For an allowed event, append the current timestamp
	   to the back of the deque.

	   timestamps.addLast(currentTimestamp);

	6. Otherwise, if:

	   timestamps.size() == limit

	   reject the event.

	   Do not add the rejected event's timestamp to the deque.
	*/

	/* Complexity Analysis:

	Let:
		N = total number of events
		U = number of users

	Every accepted timestamp is:
		- added to a deque exactly once
		- removed from a deque at most once

	Even though there is a while loop inside the event-processing
	loop, the same timestamp cannot be removed multiple times.

	Therefore, across all N events, the total number of deque
	insertions and removals is O(N).

	Time Complexity: O(N) expected

	HashMap lookup/update is expected O(1), and deque operations are O(1).

	Amortized time per event: O(1)

	
	Space Complexity:

	At any moment, we only keep accepted timestamps that have
	not yet expired from the sliding window.

	Since each user can have at most 'limit' accepted timestamps
	inside the active window, the worst-case space is:

	O(U * limit)

	More generally: O(A)

	where A is the total number of currently active accepted
	timestamps across all users.

	This is more precise than saying O(N) space because expired
	timestamps are continuously removed instead of being kept
	for the lifetime of the input.
	*/
	
	/**
	 * Interview script:
     * "For each user, I keep a deque containing only accepted
     * timestamps that are still inside the user's sliding window.
     *
     * Before processing a new event, I remove expired timestamps.
     * Then the deque size tells me how many events currently
     * consume this user's limit."
     *
     * Time:  O(N) expected, amortized O(1) per event
     * Space: O(U * limit) in the worst case
     */
    public static List<Boolean> limitEvents_SlidingWin(
            List<Event> events, long windowSize, int limit) {
    	
    	List<Boolean> result = new ArrayList<>();

        // Follow-up 1 — Sliding Window
        // Each user now keeps accepted timestamps in the active sliding window.
    	Map<String, Deque<Long>> userState = new HashMap<>();
    	
    	for(Event event : events) {
    		
    		// Follow-up 1 — Sliding Window
            /* "Since each event creates its own sliding window,
             * I first get this user's timestamp deque, remove
             * expired timestamps, and then use its size to decide
             * whether the current event can be accepted." */
    		Deque<Long> timestamps = userState.computeIfAbsent(
                    event.userId,
                    key -> new ArrayDeque<>());
    		
    		long cutOff = event.timestamp - windowSize; // cutoff timestamp, remove everything older than this
    		
    		/* "Our active interval is (t - windowSize, t].
             * Since timestamps arrive in order, expired events
             * are always at the front of the deque." */
    		while(!timestamps.isEmpty() && timestamps.peekFirst() <= cutOff) {
    			timestamps.removeFirst();
    		}
    		
    		/*"After removing expired events, the deque size is
             * exactly the number of accepted events still consuming
             * this user's limit." */
    		if(timestamps.size() < limit) {
    			// Accepted events consume capacity.
    			timestamps.addLast(event.timestamp);
    			result.add(true);
    		}
    		else {
                result.add(false);
    		}
    		
    	}
    	return result;
    }
 
    
    /** Follow-up 2 — Events Arrive Out of Timestamp Order 
     * refer: FixedWindowUserEventLimiter_EventsArriveOutOfTimestampOrder_FollowUp2.java
     * for explanation and implementation 
     * */
    
    
    /** Follow-up 3 — Millions of Users / Remove Stale State */
	/*
	 * Short Interview Script to Memorize
	 * 
	 * The issue is that inactive users can remain in the hash map forever. I don't
	 * want to periodically scan millions of entries, so I'll maintain a min-heap
	 * ordered by user-state expiration time.
	 * 
	 * Each user state stores its current expiration time. Whenever an accepted
	 * event changes the user's latest relevant timestamp, I compute expireAt =
	 * latestAcceptedTimestamp + windowSize, update the state, and push a new
	 * expiration entry into the heap.
	 * 
	 * Before processing an event at time t, I pop heap entries with expireAt <= t.
	 * Because a user can have multiple old heap entries, I remove the user only if
	 * the popped expiration still matches the expiration stored in their current
	 * state. Otherwise I ignore it as stale.
	 * 
	 * This avoids an O(U) map scan and instead makes expiration work O(log H) per
	 * heap entry. At much larger scale, I'd consider a TTL-enabled cache or a
	 * timing wheel.
	 */
    
    /* *** Recommended Data Structures ***

		For the sliding-window version:
			Map<String, UserState> userState;
			PriorityQueue<ExpiryEntry> expiryHeap;
		
		Where:
			UserState:
			    deque of accepted timestamps
			    expireAt
		And:
			ExpiryEntry:
			    userId
			    expireAt
		
		Conceptually:
		                    +-------------------------+
		event ------------> | Map<UserId, UserState> |
		                    +-------------------------+
		                         |
		                         | update expireAt
		                         v
		                    +------------------+
		                    | expiration heap  |
		                    | earliest first   |
		                    +------------------+
		                         |
		                         | expireAt <= now
		                         v
		                    remove stale users
     * */
    
    
    /** Follow-up 4 — Distributed Rate Limiter */
	/* Interview Script
	 * 
	 * With multiple application servers, the local hash map is no longer
	 * authoritative because requests for one user can hit different machines. I
	 * would move the rate-limit state into shared Redis and key it by user ID.
	 * 
	 * For a fixed window, I'd maintain an atomic counter with TTL. The check,
	 * increment, and expiration setup should happen atomically, for example with a
	 * Redis Lua script, so two servers can't both consume the final available slot.
	 * 
	 * For the sliding-window version, I'd use a Redis sorted set of accepted
	 * timestamps and atomically remove expired entries, count the remaining
	 * requests, and insert the new timestamp if we're below the limit.
	 * 
	 * To scale, I'd partition Redis by user ID so all requests for one user go to
	 * the same shard. I'd also clarify whether the limiter needs to be strict or
	 * approximate, because if small overshoot is acceptable, we can allocate local
	 * token batches to servers and reduce coordination significantly.
	 * 
	 * Finally, I'd define whether we fail open or fail closed if Redis becomes
	 * unavailable based on how critical the rate limit is.
	 */
    
    
    boolean temp; // not in use
    
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