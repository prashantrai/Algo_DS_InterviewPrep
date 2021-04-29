package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MeetingScheduler_1229_Medium {

	public static void main(String[] args) {
		
		int[][] slots1 = {{10,50},{60,120},{140,210}}; 
		int[][] slots2 = {{0,15},{60,70}}; 
		int duration = 8;
		
		List<Integer> res = minAvailableDuration(slots1, slots2, duration);
		System.out.println("Expected: [60,68], Actual: " + res);
		
		
		
		int[][] slots3 = {{10,50},{60,120},{140,210}};
		int[][] slots4 = {{0,15},{60,70}}; 
		duration = 12;
		
		res = minAvailableDuration(slots3, slots4, duration);
		System.out.println("Expected: [], Actual: " + res); // expected empty i.e. no slot available
		

	}
	
	
	/* Time: O(nlogn), Insertion cost in priorityQueue is log(N) for each element i.e. for N elements it's N Log(N)
	 * Space : O(n), for priority queue/heap
	 * 
	 * Reference : https://www.cnblogs.com/cnoodle/p/12635738.html
	 * */
	
	public static List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0])); // this works too
		// this works too
		//PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparing(a ->a[0])); // Comparator.comparing static function accepts a sort key Function and returns a Comparator for the type which contains the sort key
		
		// employee 1
		for(int[] slot : slots1) {
			// add the slot which equal or greater than the duration because smaller than that is of no use
			if(slot[1] - slot[0] >= duration) {  
				pq.add(slot);
			}
		}
		// employee 2
		for(int[] slot : slots2) {
			// add the slot which equal or greater than the duration because smaller than that is of no use
			if(slot[1] - slot[0] >= duration) {
				pq.add(slot);
			}
		}
		
		int[] prev = pq.poll();
		while(!pq.isEmpty()) {
			int[] curr = pq.poll();
			
			// if the current start time + duration is within the end time of prev end time
			// We found the slot return 
			if(prev[1] >= curr[0] + duration) {   
				return Arrays.asList(curr[0], curr[0] + duration);
			} else {
				prev = curr;
			}
			
		}

		/* this works too
		while(pq.size() > 1) {
			if(pq.poll()[1] >= pq.peek()[0] + duration) {
				return Arrays.asList(pq.peek()[0], pq.peek()[0] + duration);
			}
		}*/
		
		
		return Arrays.asList(); // return empty list
	}	

}


/*
1229. Meeting Scheduler
Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration, 
return the earliest time slot that works for both of them and is of duration duration.

If there is no common time slot that satisfies the requirements, return an empty array.

The format of a time slot is an array of two elements [start, end] representing an inclusive 
time range from start to end.  

It is guaranteed that no two availability slots of the same person intersect with each other. 
That is, for any two time slots [start1, end1] and [start2, end2] of the same person, 
either start1 > end2 or start2 > end1.


Example 1:
Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
Output: [60,68]

Example 2:
Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
Output: []

 * */
