package leetcode;

import java.util.Arrays;

public class MeetingRooms_252_Easy {

	/*
	 * Given an array of meeting time intervals consisting of start and end times 
	 * [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.

		Example 1:
		
		Input: [[0,30],[5,10],[15,20]]
		Output: false
		
		Example 2:
		
		Input: [[7,10],[2,4]]
		Output: true
	 * */
	
	
	
	public static void main(String[] args) {
		
		int[][] intervals = {{0, 30},{5, 10},{15, 20}};
	
		boolean res = canAttendMeetings(intervals);
		System.out.println("Expacted: false, Actual: "+res);
		
		int[][] intervals2 = {{7,10},{2,4}};
		
		res = canAttendMeetings(intervals2);
		System.out.println("Expacted: true, Actual: "+res);
	
	}

	
	/*
	 * References: 
	 * https://www.programcreek.com/2014/07/leetcode-meeting-rooms-java/
	 * 
	 * Time : N logN : Because of sorting
	 * 
	 * */
	
	public static boolean canAttendMeetings(int[][] intervals) {
		
		if(intervals == null || intervals.length == 0) {
			return false;
		}
		
		Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
		
		System.out.println(Arrays.deepToString(intervals));
		
		for(int i=1; i<intervals.length; i++) {
			int prevEnd = intervals[i-1][1];
			int currStart = intervals[i][0];
			if(prevEnd > currStart) {
				return false;
			}
		}
		return true;
 	}
	
}
