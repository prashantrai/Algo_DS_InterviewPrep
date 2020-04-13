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
	 * Time Complexity: O(N\log N)O(NlogN) because all we are doing is sorting the
	 * two arrays for start timings and end timings individually and each of them
	 * would contain NN elements considering there are NN intervals.
	 * 
	 * Space Complexity: O(N)O(N) because we create two separate arrays of size NN,
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
