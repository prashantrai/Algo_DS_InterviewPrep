package leetcode;

import java.util.Arrays;

public class MeetingRoomsII_253_Medium {

	public static void main(String[] args) {
		int[][] intervals = {{0, 30},{5, 10},{15, 20}};
		int res = minMeetingRooms(intervals);
		System.out.println(res);
	}

	
	/* https://leetcode.com/problems/meeting-rooms-ii/
	 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.

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
