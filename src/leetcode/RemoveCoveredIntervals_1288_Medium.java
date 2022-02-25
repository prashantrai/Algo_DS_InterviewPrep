package leetcode;

import java.util.Arrays;

public class RemoveCoveredIntervals_1288_Medium {

	public static void main(String[] args) {

		int[][] intervals = {{1,4},{3,6},{2,8}};
		System.out.println("Expected: 2, Actual: " + removeCoveredIntervals(intervals));
		
		int[][] intervals2 = {{1,4},{2,3}};
		System.out.println("Expected: 1, Actual: " + removeCoveredIntervals(intervals2));
	}
	
	
	// https://leetcode.com/problems/remove-covered-intervals/discuss/1787452/Easy-Understand-Java-Solution-With-Explanation
	public static int removeCoveredIntervals(int[][] intervals) {
        
        /*
        Arrays.sort(intervals, new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            // Sort by start point.
            // If two intervals share the same start point,
            // put the longer one to be the first.
            return o1[0] == o2[0] ? o2[1] - o1[1]: o1[0] - o2[0];
          }
        });
        */
        
        /*  Sort by start point.
            If two intervals share the same start point,
            put the longer one to be the first.
            
            Algorithm: 
            1. Sort the array in ascending order based on the left bound. If the left bound is the same, sort it based on order of larger right bound comes first.
            2.Initialize count to intervals.length;
            3. Compare the right bound, and decrement count if cur can be included in the interval.
            
        */
        Arrays.sort(intervals, 
                    (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]
                   );
        
        int count = intervals.length;
        int currEnd = 0;
        
        for(int[] arr : intervals) {
            if(currEnd < arr[1]) {
                currEnd = arr[1];
            } else {
                count--;
            }
        }
        return count;
    }

}
