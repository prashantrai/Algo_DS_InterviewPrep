package Intuit;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoomsII_253_Medium {

	public static void main(String[] args) {
		int[][] intervals = {{0, 30},{5, 10},{15, 20}};
		
		int res = minMeetingRooms(intervals);
		System.out.println("Expacted: 2, Actual: "+res);
		
		res = minMeetingRooms2(intervals);  // with priority queue
		System.out.println("Expacted: 2, Actual: "+res);
		
		
		int[][] intervals2 = {{7,10},{2,4}};
		
		res = minMeetingRooms(intervals2);
		System.out.println("Expacted: 1, Actual: "+res);
		
		res = minMeetingRooms2(intervals2);	// with priority queue
		System.out.println("Expacted: 1, Actual: "+res);
	}

	
	/* https://leetcode.com/problems/meeting-rooms-ii/
	 * Given an array of meeting time intervals consisting of start and end times 
	 * [[s1,e1],[s2,e2],...] (si < ei), 
	 * 
	 * find the minimum number of conference rooms required.

		Example 1:
		
		Input: [[0, 30],[5, 10],[15, 20]]
		Output: 2
		Example 2:
		
		Input: [[7,10],[2,4]]
		Output: 1
	 * */
	
	/*
	 * Complexity Analysis
	 * 
	 * 
	 * Time Complexity: O(NlogN) because all we are doing is sorting the
	 * two arrays for start timings and end timings individually and each of them
	 * would contain N elements considering there are N intervals.
	 * 
	 * Space Complexity: O(N) because we create two separate arrays of size N,
	 * one for recording the start times and one for the end times.
	 * 
	 */

	public static int minMeetingRooms(int[][] intervals) {

		int[] st_time = new int[intervals.length];
		int[] end_time = new int[intervals.length];

		for (int i = 0; i < intervals.length; i++) {
			st_time[i] = intervals[i][0];
			end_time[i] = intervals[i][1];
		}

		Arrays.sort(st_time);
		Arrays.sort(end_time);

		int rooms = 0;

		int s_ptr = 0;
		int e_ptr = 0;

		while (s_ptr < intervals.length) {
			if (st_time[s_ptr] < end_time[e_ptr]) {
				rooms++;
			} else {
				e_ptr++;
			}
			s_ptr++;
		}

		return rooms;
	}
	
	
	// https://www.jianshu.com/p/28475d91d54b?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
	
	//https://www.programcreek.com/2014/05/leetcode-meeting-rooms-ii-java/
	// How to create max heap using priority queue: https://jindongpu.wordpress.com/2015/10/20/implement-max-heap-and-min-heap-using-priorityqueue-in-java/
	
	public static int minMeetingRooms2(int[][] intervals) {
		
		if(intervals == null || intervals.length ==0) {
			return 0;
		}
		
		Arrays.sort(intervals, ((arr1, arr2) -> Integer.compare(arr1[0], arr2[0])));
		
		PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // min heap
		int count = 0;
		for(int[] interval : intervals) {
			if(minHeap.isEmpty()) {
				minHeap.offer(interval[1]);
				count++;
			} else {
				if(interval[0] >= minHeap.peek()) {
					minHeap.poll();
				} else {
					count++;
				}
				minHeap.offer(interval[1]);
			}
			
		}
		return count;
	}

}





/*
// Using Priority Queue  - Code from Article
 * 
 * 
    public int minMeetingRooms(int[][] intervals) {
        
    // Check for the base case. If there are no intervals, return 0
    if (intervals.length == 0) {
      return 0;
    }

    // Min heap
    PriorityQueue<Integer> allocator =
        new PriorityQueue<Integer>(
            intervals.length,
            new Comparator<Integer>() {
              public int compare(Integer a, Integer b) {
                return a - b;
              }
            });

    // Sort the intervals by start time
    Arrays.sort(
        intervals,
        new Comparator<int[]>() {
          public int compare(final int[] a, final int[] b) {
            return a[0] - b[0];
          }
        });

    // Add the first meeting
    allocator.add(intervals[0][1]);

    // Iterate over remaining intervals
    for (int i = 1; i < intervals.length; i++) {

      // If the room due to free up the earliest is free, assign that room to this meeting.
      if (intervals[i][0] >= allocator.peek()) {
        allocator.poll();
      }

      // If a new room is to be assigned, then also we add to the heap,
      // If an old room is allocated, then also we have to add to the heap with updated end time.
      allocator.add(intervals[i][1]);
    }

    // The size of the heap tells us the minimum rooms required for all the meetings.
    return allocator.size();
  } */
