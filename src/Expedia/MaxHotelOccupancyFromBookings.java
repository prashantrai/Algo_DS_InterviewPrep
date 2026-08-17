package Expedia;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MaxHotelOccupancyFromBookings {

	public static void main(String[] args) {
	    runTest("Example 1",
	            new int[][]{
	                    {1, 5},
	                    {2, 6},
	                    {4, 8},
	                    {9, 10}
	            },
	            3);
	    runTest("Example 2 - Boundary",
	            new int[][]{
	                    {1, 5},
	                    {5, 10}
	            },
	            1);
	    runTest("Single Booking",
	            new int[][]{
	                    {2, 5}
	            },
	            1);
	    runTest("No Overlap",
	            new int[][]{
	                    {1, 2},
	                    {2, 3},
	                    {3, 4},
	                    {4, 5}
	            },
	            1);
	    runTest("Complete Overlap",
	            new int[][]{
	                    {1, 10},
	                    {2, 9},
	                    {3, 8},
	                    {4, 7}
	            },
	            4);
	    runTest("Partial Overlap",
	            new int[][]{
	                    {1, 4},
	                    {2, 5},
	                    {3, 6},
	                    {4, 7}
	            },
	            3);

	    runTest("Same Check-in",
	            new int[][]{
	                    {1, 5},
	                    {1, 4},
	                    {1, 3},
	                    {1, 2}
	            },
	            4);

	    runTest("Same Check-out",
	            new int[][]{
	                    {1, 5},
	                    {2, 5},
	                    {3, 5},
	                    {4, 5}
	            },
	            4);

	    runTest("Nested Bookings",
	            new int[][]{
	                    {1, 10},
	                    {2, 8},
	                    {3, 7},
	                    {4, 6}
	            },
	            4);

	    runTest("Duplicate Bookings",
	            new int[][]{
	                    {1, 5},
	                    {1, 5},
	                    {1, 5}
	            },
	            3);

	    runTest("Large Gap",
	            new int[][]{
	                    {1, 5},
	                    {2, 6},
	                    {3, 7},
	                    {100, 101}
	            },
	            3);

	    runTest("Many Finished Before Next Starts",
	            new int[][]{
	                    {1, 2},
	                    {2, 3},
	                    {3, 4},
	                    {100, 101}
	            },
	            1);

	    runTest("Empty Input",
	            new int[][]{},
	            0);

	    runTest("Null Input",
	            null,
	            0);
	}

	private static void runTest(String testName, int[][] bookings, int expected) {
	    int actual = maxOccupancyFromBookings(bookings);
	    System.out.printf( "%-35s Expected: %-2d Actual: %-2d %s%n",
	            testName, expected, actual, expected == actual ? "✅ PASS" : "❌ FAIL"
	    );
	}
	
	/* Time: 
		Sorting: O(n log n)
		Heap operations: O(n log n)
	Space: O(n) */
	
	// Solution returns the maximum number of guests staying at the hotel at the same time.
	private static int maxOccupancyFromBookings(int[][] bookings) {
		if(bookings == null || bookings.length == 0) {
			return 0;
		}
		// Sort bookings by start time
		Arrays.sort(bookings, (a, b) -> Integer.compare(a[0], b[0]));
		
		// Min-heap to store end times of meetings currently using rooms
		PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
		
		int maxGuests = 0;
		for(int[] booking : bookings) {
			// Size of heap represents minimum rooms needed
			while(!minPQ.isEmpty() && booking[0] >= minPQ.peek()) {
				minPQ.poll(); // Remove the checked out room
			}
			// Add current booking's end/checkout time to heap
			minPQ.offer(booking[1]);
			
			// maxGuests tracks the highest number of active bookings at any point
			maxGuests = Math.max(maxGuests, minPQ.size());
		}
		// Size of heap represents minimum rooms needed
		return maxGuests;
	}
}

/*
  Follow-up 1 (Very Likely *****)
  ================================
  Return Maximum Occupancy Time Range
  
  Current solution only returns: 3
  
  Now return:
  1. Maximum number of guests.
  2. The time range where this maximum occurs.
  
  Example:
  Input:
  [[1,5], [2,6], [4,8]]
  
  Output:
  maxGuests = 3
  range = [4,5]
  
  Explanation:
  1:       Guest A
  2-4:     A + B
  4-5:     A + B + C  (max here)
  
  What interviewer evaluates:
  Can you maintain currentCount, previousTime, maxStart, maxEnd instead of only counting.
  
  ---
  
 * Follow-up 2 (Very Likely *****)
 * ================================
 * Given Available Rooms, Can Hotel Accept All Bookings?
 * 
 * The hotel has k rooms. Given future bookings, determine whether all reservations
 * can be accepted.
 * 
 * Example:
 * rooms = 2
 * bookings: [[1,5], [2,6], [4,8]]
 * Output: false
 * 
 * Because at day 4, there are 3 guests but only 2 rooms available.
 * 
 * This becomes LC253 Meeting Rooms II.
 * 
 * ---
 * 
 * Follow-up 3 (Very Likely ****)
 * ==============================
 * Find Minimum Number of Rooms Required
 * 
 * Instead of returning true/false, return the required number of rooms.
 * 
 * Example:
 * Input: [[1,5], [2,6], [4,8]]
 * Output: 3
 * 
 * Exact mapping: LC253 Meeting Rooms II.
 */




/* Full problem with all the follow-ups
	# Expedia Interview Problem: Maximum Hotel Occupancy From Bookings - Generated by ChatGPT

	A hotel receives a list of customer bookings.
	Each booking contains:
	```
	[checkIn, checkOut]
	```
	where:
	* `checkIn` is the day the guest arrives.
	* `checkOut` is the day the guest leaves.
	* A guest occupies a room from `checkIn` (inclusive) until `checkOut` (exclusive).
	
	Given all bookings, return the **maximum number of guests staying at the hotel at the same time**.
	---
	
	## Example 1
	
	### Input
	```
	bookings = [
	    [1, 5],
	    [2, 6],
	    [4, 8],
	    [9, 10]
	]
	```
	### Timeline
	```
	Day:
	1       2       4       5       6       8       9
	A:
	|---------------|
	B:
	        |---------------|
	C:
	                |---------------|
	D:
	                                        |-------|
	```
	
	Occupancy:
	```
	Day 1:
	A = 1
	
	Day 2-3:
	A + B = 2
	
	Day 4:
	A + B + C = 3  <-- maximum
	
	Day 5:
	B + C = 2
	
	Day 9:
	D = 1
	```
	
	### Output
	
	```
	3
	```
	
	---
	
	## Example 2 (Boundary Case)
	
	### Input
	
	```
	[
	 [1,5],
	 [5,10]
	]
	```
	
	### Output
	
	```
	1
	```
	
	Explanation:
	
	The first guest leaves before the second guest arrives.
	
	```
	Guest A:
	1 -------- 5
	
	Guest B:
	          5 -------- 10
	```
	
	They do not overlap.
	
	---
	
	# Expected Solution Discussion
	
	Candidate should identify:
	
	```
	Intervals
	    |
	    |
	Maximum overlap
	    |
	    |
	Sweep line / Priority Queue
	```
	
	Possible approaches:
	
	### Approach 1
	
	Sort all events:
	
	```
	(checkIn, +1)
	(checkOut, -1)
	```
	
	Then sweep.
	
	Time:
	
	```
	O(n log n)
	```
	
	Space:
	
	```
	O(n)
	```
	
	---
	
	### Approach 2
	
	Sort bookings by start time + min heap.
	
	Similar to:
	
	LC253 Meeting Rooms II
	
	---
	
	# Senior Engineer Follow-ups
	
	---
	
	# Follow-up 1 (Very Likely ⭐⭐⭐⭐⭐)
	
	## Return Maximum Occupancy Time Range
	
	Current solution only returns:
	
	```
	3
	```
	
	Now return:
	
	1. Maximum number of guests.
	2. The time range where this maximum occurs.
	
	---
	
	Example:
	
	Input:
	
	```
	[
	 [1,5],
	 [2,6],
	 [4,8]
	]
	```
	
	Output:
	
	```
	maxGuests = 3
	
	range = [4,5]
	```
	
	Explanation:
	
	```
	1:
	Guest A
	
	2-4:
	A+B
	
	4-5:
	A+B+C
	      ^
	      max
	```
	
	---
	
	### What interviewer evaluates
	
	Can you maintain:
	
	```
	currentCount
	previousTime
	maxStart
	maxEnd
	```
	
	instead of only counting.
	
	---
	
	# Follow-up 2 (Very Likely ⭐⭐⭐⭐⭐)
	
	## Given Available Rooms, Can Hotel Accept All Bookings?
	
	The hotel has:
	
	```
	k rooms
	```
	
	Given future bookings, determine whether all reservations can be accepted.
	
	---
	
	Example:
	
	Input:
	
	```
	rooms = 2
	
	bookings:
	
	[
	 [1,5],
	 [2,6],
	 [4,8]
	]
	```
	
	Output:
	
	```
	false
	```
	
	Because:
	
	At day 4:
	
	```
	3 guests
	```
	
	but only:
	
	```
	2 rooms
	```
	
	available.
	
	---
	
	This becomes:
	
	```
	LC253 Meeting Rooms II
	```
	
	---
	
	# Follow-up 3 (Very Likely ⭐⭐⭐⭐)
	
	## Find Minimum Number of Rooms Required
	
	Instead of true/false:
	
	Return required rooms.
	
	Example:
	
	Input:
	
	```
	[
	 [1,5],
	 [2,6],
	 [4,8]
	]
	```
	
	Output:
	
	```
	3
	```
	
	---
	
	Exact mapping:
	
	```
	LC253 Meeting Rooms II
	```
	
	---
	
	# Follow-up 4 (Likely ⭐⭐⭐⭐)
	
	## Support Hotel Room Types
	
	Hotel has room categories:
	
	```
	Standard
	Deluxe
	Suite
	```
	
	Each booking has:
	
	```
	[
	 checkIn,
	 checkOut,
	 roomType
	]
	```
	
	Find maximum occupancy per room type.
	
	---
	
	Example:
	
	Input:
	
	```
	[
	 [1,5,"Suite"],
	 [2,6,"Suite"],
	 [3,7,"Standard"]
	]
	```
	
	Output:
	
	```
	Suite:
	2
	
	Standard:
	1
	```
	
	---
	
	Expected extension:
	
	Use:
	
	```
	HashMap<RoomType, sweep line events>
	```
	
	---
	
	# Follow-up 5 (Likely ⭐⭐⭐⭐)
	
	## Cancel a Booking
	
	Initially:
	
	```
	100000 bookings
	```
	
	New operations:
	
	```
	ADD booking
	REMOVE booking
	QUERY maximum occupancy
	```
	
	---
	
	Example:
	
	Operations:
	
	```
	ADD [1,5]
	
	ADD [2,6]
	
	QUERY
	```
	
	Output:
	
	```
	2
	```
	
	Then:
	
	```
	REMOVE [2,6]
	
	QUERY
	```
	
	Output:
	
	```
	1
	```
	
	---
	
	This moves from:
	
	Static sweep line
	
	to:
	
	Dynamic interval counting.
	
	Possible solutions:
	
	* TreeMap
	* Segment Tree
	* Interval Tree
	
	---
	
	# Follow-up 6 (Medium Probability ⭐⭐⭐)
	
	## Find Dates With Overbooking
	
	Instead of maximum occupancy:
	
	Return all dates where occupancy exceeds capacity.
	
	---
	
	Example:
	
	Hotel capacity:
	
	```
	2
	```
	
	Bookings:
	
	```
	[
	[1,5],
	[2,6],
	[3,8]
	]
	```
	
	Output:
	
	```
	Days:
	3,4,5
	```
	
	because:
	
	```
	occupancy = 3
	```
	
	---
	
	Pattern:
	
	Sweep line + collect ranges.
	
	---
	
	# Follow-up 7 (Medium Probability ⭐⭐⭐)
	
	## Streaming Bookings
	
	Bookings arrive continuously.
	
	Example:
	
	```
	Booking received:
	
	[10,20]
	```
	
	Need:
	
	```
	current occupancy
	```
	
	after every update.
	
	Constraints:
	
	```
	millions of bookings
	```
	
	---
	
	Discussion:
	
	Need:
	
	* ordered map
	* distributed counters
	* partitioning by date
	* eventual consistency
	
	This is more system-design oriented.
	
	---
	
	# Follow-up 8 (Low-Medium Probability ⭐⭐)
	
	## Multiple Hotels
	
	Input:
	
	```
	hotelId
	checkIn
	checkOut
	```
	
	Find:
	
	```
	hotel with highest occupancy
	```
	
	---
	
	Example:
	
	```
	Hotel A:
	maximum = 100
	
	Hotel B:
	maximum = 200
	```
	
	Output:
	
	```
	Hotel B
	```
	
	---
	
	Pattern:
	
	Group by hotel.
	
	---
	
	# Follow-up 9 (Low Probability ⭐⭐)
	
	## Find Guests Staying Together The Longest
	
	Given bookings:
	
	Return the pair of guests with maximum overlapping stay.
	
	Example:
	
	```
	A:
	[1,10]
	
	B:
	[5,8]
	
	C:
	[9,12]
	```
	
	Output:
	
	```
	A and B
	
	Overlap:
	3 days
	```
	
	---
	
	Pattern:
	
	Interval intersection.
	
	---
	
	# Follow-up 10 (Low Probability ⭐)
	
	## Pricing / Revenue Extension
	
	Each booking:
	
	```
	[
	checkIn,
	checkOut,
	pricePerNight
	]
	```
	
	Find:
	
	```
	maximum revenue day
	```
	
	---
	
	Example:
	
	```
	Booking A:
	1-5
	$100/night
	
	Booking B:
	3-6
	$200/night
	```
	
	Output:
	
	```
	Day 3-5
	
	Revenue:
	$300/night
	```
	
	---
	
	Pattern:
	
	Sweep line with weighted intervals.
	
	---
	
	# Interview Priority Ranking
	
	For Expedia Senior Engineer, I would prepare in this order:
	
	| Rank | Follow-up                              | Probability | Pattern               |
	| ---- | -------------------------------------- | ----------- | --------------------- |
	| 1    | Return max occupancy count             | ⭐⭐⭐⭐⭐       | Sweep line            |
	| 2    | Minimum rooms required                 | ⭐⭐⭐⭐⭐       | LC253                 |
	| 3    | Can hotel accept bookings with K rooms | ⭐⭐⭐⭐⭐       | LC253                 |
	| 4    | Return peak occupancy interval         | ⭐⭐⭐⭐        | Sweep line            |
	| 5    | Room types                             | ⭐⭐⭐⭐        | HashMap + sweep       |
	| 6    | Booking cancellation/update            | ⭐⭐⭐         | TreeMap/Segment Tree  |
	| 7    | Overbooking dates                      | ⭐⭐⭐         | Sweep line            |
	| 8    | Multiple hotels                        | ⭐⭐          | Grouping              |
	| 9    | Guest pair overlap                     | ⭐⭐          | Interval intersection |
	| 10   | Revenue optimization                   | ⭐           | Weighted sweep        |
	
	For an Expedia **Senior Backend** phone screen, I would expect the interviewer to start with the base problem and most likely move into **Follow-up #1, #2, or #3**. Those are the strongest preparation targets.
  
 * */
