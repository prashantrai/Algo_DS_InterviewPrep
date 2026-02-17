package SoFi;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRooms_252_Easy {

	// Main method with test cases
    public static void main(String[] args) {
    	MeetingRooms_252_Easy solution = new MeetingRooms_252_Easy();
        
        // Test Case 1: Example from problem statement
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Test Case 1: " + Arrays.deepToString(intervals1));
        System.out.println("Expected: false, Got: " + solution.canAttendMeetings(intervals1));
        System.out.println();
        
        // Test Case 2: Example from problem statement
        int[][] intervals2 = {{7, 10}, {2, 4}};
        System.out.println("Test Case 2: " + Arrays.deepToString(intervals2));
        System.out.println("Expected: true, Got: " + solution.canAttendMeetings(intervals2));
        System.out.println();
        
        
    }
    
    // Time: O(nlogn)
    // Space: O(logn), In Java, Arrays.sort() is implemented using 
    // a variant of the Quick Sort algorithm which has a space complexity 
    // of O(logn) for sorting an array.

    public static boolean canAttendMeetings(int[][] intervals) {
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        for(int i=0; i<intervals.length-1; i++) {
            if(intervals[i][1] > intervals[i+1][0]) {
                return false;
            }
        }
        return true;
    }

}
