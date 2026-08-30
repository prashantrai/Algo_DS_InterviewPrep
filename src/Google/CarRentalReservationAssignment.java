package Google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class CarRentalReservationAssignment {

	public static void main(String[] args) {

	    // Test 1: Basic overlap + reuse
	    List<Reservation> test1 = Arrays.asList(
	        new Reservation(1, 1, 4),
	        new Reservation(2, 2, 5),
	        new Reservation(3, 4, 7)
	    );

	    System.out.println("Test 1:");
	    System.out.println(assignCars(new ArrayList<>(test1)));
	    // Expected:
	    // R1 -> car 1
	    // R2 -> car 2
	    // R3 -> car 1
	    // Possible map: {1=1, 2=2, 3=1}


	    // Test 2: No overlap - all reservations can use same car
	    List<Reservation> test2 = Arrays.asList(
	        new Reservation(1, 1, 3),
	        new Reservation(2, 3, 5),
	        new Reservation(3, 5, 8)
	    );

	    System.out.println("\nTest 2:");
	    System.out.println(assignCars(new ArrayList<>(test2)));
	    // Expected:
	    // {1=1, 2=1, 3=1}


	    // Test 3: All reservations overlap
	    List<Reservation> test3 = Arrays.asList(
	        new Reservation(1, 1, 10),
	        new Reservation(2, 2, 8),
	        new Reservation(3, 3, 7),
	        new Reservation(4, 4, 6)
	    );

	    System.out.println("\nTest 3:");
	    System.out.println(assignCars(new ArrayList<>(test3)));
	    // Expected:
	    // 4 different cars
	    // Possible: {1=1, 2=2, 3=3, 4=4}


	    // Test 4: Input is not sorted
	    List<Reservation> test4 = Arrays.asList(
	        new Reservation(3, 6, 8),
	        new Reservation(1, 1, 4),
	        new Reservation(4, 8, 10),
	        new Reservation(2, 2, 6)
	    );

	    System.out.println("\nTest 4:");
	    System.out.println(assignCars(new ArrayList<>(test4)));
	    // One valid assignment:
	    // R1 -> car 1
	    // R2 -> car 2
	    // R3 -> car 1
	    // R4 -> car 1


	    // Test 5: Multiple cars become free at the same time
	    List<Reservation> test5 = Arrays.asList(
	        new Reservation(1, 1, 4),
	        new Reservation(2, 1, 4),
	        new Reservation(3, 1, 4),
	        new Reservation(4, 4, 6),
	        new Reservation(5, 4, 7),
	        new Reservation(6, 4, 8)
	    );

	    System.out.println("\nTest 5:");
	    System.out.println(assignCars(new ArrayList<>(test5)));
	    // Expected:
	    // Exactly 3 cars should be used.


	    // Test 6: Single reservation
	    List<Reservation> test6 = Arrays.asList(
	        new Reservation(100, 5, 10)
	    );

	    System.out.println("\nTest 6:");
	    System.out.println(assignCars(new ArrayList<>(test6)));
	    // Expected:
	    // {100=1}


	    // Test 7: Empty input
	    List<Reservation> test7 = new ArrayList<>();

	    System.out.println("\nTest 7:");
	    System.out.println(assignCars(test7));
	    // Expected:
	    // {}


	    // Test 8: Same start time - all need separate cars
	    List<Reservation> test8 = Arrays.asList(
	        new Reservation(1, 1, 5),
	        new Reservation(2, 1, 3),
	        new Reservation(3, 1, 7)
	    );

	    System.out.println("\nTest 8:");
	    System.out.println(assignCars(new ArrayList<>(test8)));
	    // Expected:
	    // 3 different cars


	    // Test 9: Reuse after a gap
	    List<Reservation> test9 = Arrays.asList(
	        new Reservation(1, 1, 2),
	        new Reservation(2, 10, 20),
	        new Reservation(3, 30, 40)
	    );

	    System.out.println("\nTest 9:");
	    System.out.println(assignCars(new ArrayList<>(test9)));
	    // Expected:
	    // {1=1, 2=1, 3=1}
	}
	
	
	/*
	Interview Script:

	1. "I’ll sort all reservations by start time so I process them in the order they begin."

	2. "For cars that are currently in use, I need to know which one becomes available first.
	   So I’ll use a min-heap ordered by reservation end time."

	3. "Each heap entry must store both:
	      - when the car becomes free
	      - which carId it is
	   because when that reservation ends, I want to reuse the same car."

	4. "For each reservation:
	      - If the heap is not empty and the earliest-ending car has end <= current.start,
	        that car is already available, so I reuse its carId.
	      - Otherwise, all existing cars are still busy, so I allocate a new carId."

	5. "After assigning a car, I push that car back into the heap with the current
	   reservation's end time, because that car is now occupied until then."

	6. "The returned map stores reservationId -> carId."

	7. "This minimizes the number of cars because I only create a new car when
	   no currently allocated car is available."

	8. "Sorting takes O(n log n), and each reservation performs at most one heap poll
	   and one heap offer, both O(log n), so the total time is O(n log n).
	   The heap and result map use O(n) space in the worst case."
	*/
	
	/*
	Step-by-Step Algorithm:

	1. Sort reservations by start time.

	2. Create a min-heap of CarUsage objects.
	   The heap is ordered by end time.

	   Each CarUsage contains:
	      end   -> when this car becomes available
	      carId -> which car this is

	3. Create a map:
	      reservationId -> carId

	4. Initialize:
	      nextCarId = 1

	5. For every reservation in sorted order:

	   a. Check the car that becomes available earliest:
	         minPQ.peek()

	   b. If:
	         minPQ.peek().end <= reservation.start

	      then that car is free before or exactly when the current reservation starts.

	      Poll it from the heap and reuse its carId.

	   c. Otherwise:
	      no existing car is currently available.

	      Assign:
	         carId = nextCarId
	         nextCarId++

	   d. Store the assignment:
	         map.put(reservation.id, carId)

	   e. The assigned car is now busy until reservation.end,
	      so add it back to the heap:

	         minPQ.offer(new CarUsage(reservation.end, carId))

	6. After all reservations are processed, return the map.

	Example:

	Reservations:
	   R1 [1,4]
	   R2 [2,5]
	   R3 [4,7]

	Process R1:
	   heap empty
	   assign car 1
	   heap = [(end=4, car=1)]

	Process R2:
	   earliest end = 4
	   4 <= 2 ? false
	   car 1 is busy
	   assign car 2

	   heap =
	      (end=4, car=1)
	      (end=5, car=2)

	Process R3:
	   earliest end = 4
	   4 <= 4 ? true
	   car 1 is available
	   reuse car 1

	Final:
	   R1 -> car 1
	   R2 -> car 2
	   R3 -> car 1

	Minimum cars used = 2
	*/
	
	static class Reservation {
		int id, start, end;
		Reservation(int id, int start, int end) {
			this.id = id;
			this.start = start;
			this.end = end;
		}
	}
	
	static class CarUsage {
	    int end, carId;
	    CarUsage(int end, int carId) {
	        this.end = end;
	        this.carId = carId;
	    }
	}
	
	/*
	 Time:  O(n log n)
	 Space: O(n)
	 * */
	
	public static Map<Integer, Integer> assignCars(List<Reservation> reservations) {
		
		reservations.sort((r1, r2) -> Integer.compare(r1.start, r2.start));
		
		// Heap stores cars currently in use.
	    // The car whose reservation ends earliest stays at the top.
		PriorityQueue<CarUsage> minPQ 
			= new PriorityQueue<>((c1, c2) -> Integer.compare(c1.end, c2.end));
		
		Map<Integer, Integer> assignment = new HashMap<>();
		
		int nextCarId = 1;
		for (Reservation reservation : reservations) {
			int assignedCarId;
			
			// Earliest-used car is already free -> reuse it.
			if(!minPQ.isEmpty() && minPQ.peek().end <= reservation.start) {
				
				CarUsage availableCar = minPQ.poll();
				assignedCarId = availableCar.carId;

			} else {
				// No car is available -> allocate a new one.
				assignedCarId = nextCarId++;
			}
			
			assignment.put(reservation.id, assignedCarId);
			
			// This car is now occupied until reservation.end.
			minPQ.offer(new CarUsage(reservation.end, assignedCarId));
		}
		
		return assignment;
	}
	
	public int minMeetingRooms_PQ(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min-heap to store end times of meetings currently using rooms
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int[] interval : intervals) {
            // If heap is not empty and current meeting starts after 
            // the earliest ending meeting, reuse that room
            if (!minHeap.isEmpty() && interval[0] >= minHeap.peek()) {
                minHeap.poll(); // Remove the finished meeting
            }
            // Add current meeting's end time to heap
            minHeap.offer(interval[1]);
        }
        
        // Size of heap represents minimum rooms needed
        return minHeap.size();
    }
	
	
	
	/** Follow-up 1: When multiple cars are available, always assign the smallest car ID.*/
	/* Example:
		Car 1 becomes free at 4
		Car 2 becomes free at 4
		Car 3 becomes free at 3
		
		Reservation starts at 5
		
		Expected: Use car 1
		
		Your current single heap is not sufficient for this rule.
		
		You would typically maintain:
		
		PriorityQueue<CarUsage> busyCars; // ordered by end
		PriorityQueue<Integer> freeCars;  // ordered by carId
		
		Before assigning a reservation, move all cars with:
		
		end <= reservation.start
		
		from busyCars into freeCars.
		
		Then:
		
		if (!freeCars.isEmpty())
		    carId = freeCars.poll();
		else
		    carId = nextCarId++;
		
		A recently published Google-tagged version of this exact problem includes deterministic assignment using the smallest available vehicle number.
	* */
	
	
	
	/** Can you solve it using a sweep-line instead of a heap? */

}


/* Car Rental Reservation Assignment
	Problem Statement: 
	
	You are given a list of car rental reservations. Each reservation has a unique ID, 
	a start time, and an end time.
	
	Assign every reservation to a car such that a car is never assigned to overlapping reservations.
	
	A car becomes immediately available when its current reservation ends, so a reservation starting 
	at time t may use a car whose previous reservation ends at time t.
	
	Minimize the total number of cars required.
	
	Return a mapping from each reservation ID to the car ID assigned to it.
	
	The reservations are not guaranteed to be sorted.
	
	Method:
	
	public static Map<Integer, Integer> assignCars(
	        List<Reservation> reservations)
	
	You manage a car rental company with a fleet of identical cars.
	
	You are given a list of reservation requests. Each reservation has:
	
	class Reservation {
	    int id;
	    int start;
	    int end;
	}
	
	where:
	id is the unique reservation ID.
	start is the reservation start time.
	end is the reservation end time.
	start < end.
	
	Each reservation must be assigned to exactly one car.
	
	A single car cannot handle two overlapping reservations.
	
	However, if one reservation ends exactly when another reservation starts, 
	the same car can immediately be reused.
	
	For example:
	
	Reservation A: [1, 4]
	Reservation B: [4, 7]
	
	These two reservations can use the same car because the first reservation ends 
	at time 4, exactly when the second begins.
	
	Your goal is to:
	
	Assign every reservation to a car.
	Minimize the total number of cars used.
	Return the assignment:
	reservationId -> carId
	
	Car IDs can be any positive integers such as 1, 2, 3, ....
	
	Method Signature
	public static Map<Integer, Integer> assignCars(List<Reservation> reservations)
	
	Expected Output: 
	
	Return a map where:
	
	key   = reservation ID
	value = assigned car ID
	
	For example:
	
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 1
	}
	
	The exact car IDs do not matter as long as:
	
	no overlapping reservations are assigned to the same car, and
	the number of distinct cars used is minimum.
	Example 1
	Input
	reservations = [
	    R1 [1, 4],
	    R2 [2, 5],
	    R3 [4, 7]
	]
	
	Timeline:
	
	Time: 1   2   3   4   5   6   7
	
	R1:   [-----------)
	R2:       [-----------)
	R3:               [-----------)
	
	R1 and R2 overlap, so they need different cars.
	
	R1 ends at 4, and R3 begins at 4, so R3 can reuse the car previously assigned to R1.
	
	Possible Output
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 1
	}
	
	Cars used:
	Car 1: R1 [1,4] -> R3 [4,7]
	Car 2: R2 [2,5]
	
	Minimum cars required: 2
	Example 2 — No Overlap
	Input
	reservations = [
	    R1 [1, 3],
	    R2 [3, 5],
	    R3 [5, 8]
	]
	Output
	{
	    1 -> 1,
	    2 -> 1,
	    3 -> 1
	}
	
	Explanation:
	
	Car 1:
	R1 [1,3] -> R2 [3,5] -> R3 [5,8]
	
	Since every reservation starts exactly when or after the previous one ends, only one car is needed.
	
	Minimum cars = 1
	Example 3 — All Reservations Overlap
	Input
	reservations = [
	    R1 [1, 10],
	    R2 [2, 8],
	    R3 [3, 7],
	    R4 [4, 6]
	]
	Possible Output
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 3,
	    4 -> 4
	}
	
	At time 4, all four reservations are active.
	
	Therefore:
	
	Minimum cars = 4
	Example 4 — Reusing Multiple Cars
	Input
	reservations = [
	    R1 [1, 4],
	    R2 [2, 6],
	    R3 [4, 5],
	    R4 [5, 8],
	    R5 [6, 9]
	]
	
	One valid assignment is:
	
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 1,
	    4 -> 1,
	    5 -> 2
	}
	
	Schedule:
	
	Car 1:
	R1 [1,4] -> R3 [4,5] -> R4 [5,8]
	
	Car 2:
	R2 [2,6] -> R5 [6,9]
	
	Therefore:
	
	Minimum cars = 2
	Example 5 — Input Not Sorted
	
	The reservations are not guaranteed to be given in chronological order.
	
	Input
	reservations = [
	    R3 [6, 8],
	    R1 [1, 4],
	    R4 [8, 10],
	    R2 [2, 6]
	]
	
	A valid output is:
	
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 1,
	    4 -> 1
	}
	
	Explanation:
	
	Car 1:
	R1 [1,4] -> R3 [6,8] -> R4 [8,10]
	
	Car 2:
	R2 [2,6]
	
	Only R1 and R2 overlap.
	
	Minimum cars = 2
	Example 6 — Several Cars Become Available
	Input
	reservations = [
	    R1 [1, 4],
	    R2 [1, 4],
	    R3 [1, 4],
	    R4 [4, 6],
	    R5 [4, 7],
	    R6 [4, 8]
	]
	
	At time 4, all three cars become available and can immediately be reused.
	
	A valid assignment:
	
	{
	    1 -> 1,
	    2 -> 2,
	    3 -> 3,
	    4 -> 1,
	    5 -> 2,
	    6 -> 3
	}
	Minimum cars = 3
	Constraints
	
	A reasonable interview version could use:
	
	0 <= reservations.length <= 100,000
	
	1 <= reservation.id <= 1,000,000,000
	
	0 <= reservation.start < reservation.end <= 1,000,000,000
	
	All reservation IDs are unique.
	
	Reservations may be provided in any order.
	Important Clarifications
	1. Are intervals inclusive or exclusive?
	
	Treat reservations as:
	
	[start, end)
	
	So:
	
	[1,4]
	[4,7]
	
	do not overlap.
	
	In implementation terms, a car is reusable when:
	
	previousEnd <= currentStart
	
	rather than:
	
	previousEnd < currentStart
	2. Do specific car IDs matter?
	
	No.
	
	For example, these are equivalent:
	
	R1 -> car 1
	R2 -> car 2
	
	and:
	
	R1 -> car 100
	R2 -> car 200
	
	Usually, for simplicity, assign sequential IDs:
	
	1, 2, 3, ...
	3. Does the returned map need to be ordered?
	
	No, unless explicitly requested.
	
	The assignment itself is what matters.
	
	4. What if the input is empty?
	
	Return an empty map:
	
	{}
	5. Are reservations allowed to have the same start time?
	
	Yes.
	
	Example:
	
	R1 [1,5]
	R2 [1,3]
	R3 [1,7]
	
	All three need different cars initially.
	
	Example:
	
	Input:
	R1 [1,4]
	R2 [2,5]
	R3 [4,7]
	
	Output:
	R1 -> 1
	R2 -> 2
	R3 -> 1
	
	because:
	
	Car 1: R1 -> R3
	Car 2: R2
	
	and two is the minimum number of cars required.
	
	What the problem is really asking
	
	A useful 1–2 line interview summary is:
	
	"This is an interval-resource assignment problem. I need to process reservations by start time and reuse the car that becomes available when its previous reservation ends; otherwise, I allocate a new car."
	
	It is essentially:
	
	LC 253 Meeting Rooms II + actual room/car assignment.
	
	LC 2402 is also related because it explicitly tracks room IDs, 
	although LC 2402 has additional rules about delaying meetings that this problem does not have.
	
	The natural optimal solution is sorting + min-heap, giving O(n log n) time.
 */