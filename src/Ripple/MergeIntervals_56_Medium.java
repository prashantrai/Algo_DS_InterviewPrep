package Ripple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MergeIntervals_56_Medium {

	public static void main(String[] args) {
		int[][] intervals = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };

		int[][] output = merge(intervals);
		System.out.println("Expected: [[1,6],[8,10],[15,18]] \n Actual: " + Arrays.deepToString(output));

	}
	
	/* 
    Time: O(nlogn), Other than the sort invocation, we do a simple linear scan of the list, so 
    the runtime is dominated by the O(nlogn) complexity of sorting.
    
    space: O(nlogn), sorting takes O(nlogn) space
    */
	
	public static int[][] merge(int[][] intervals) {
        
        if(intervals == null || intervals.length == 0) 
            return intervals;
        
        // sort intervals by comapring first value of each array
        Arrays.sort(intervals,  (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
        LinkedList<int[]> result = new LinkedList<>(); // merged
        
        for(int[] interval : intervals) {
            
            if(result.isEmpty() || result.getLast()[1] < interval[0]) {
                result.add(interval);
            } else {
                result.getLast()[1] = Math.max(result.getLast()[1], interval[1]);
            }
        }
        // return result.toArray(new int[result.size()][]); // works
        return result.toArray(int[][] :: new);  
    }
    
    //-- 2nd approach
    //--https://leetcode.com/problems/merge-intervals/
	public static int[][] merge2(int[][] intervals) {
		
		if(intervals == null || intervals.length <=1) {
			return intervals;
		}
		
		//--TODO: sort arrays 
		Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
		
		List<int[]> output = new ArrayList<>();
		int[] current_interval = intervals[0];
		output.add(current_interval);
		
		for(int[] interval : intervals) {
			int curr_begin = current_interval[0];
			int curr_end = current_interval[1];
			
			int next_begin = interval[0];
			int next_end = interval[1];
			
			if(curr_end >= next_begin) {
				current_interval[1] = Math.max(curr_end, next_end);
			
			} else {
				current_interval = interval;
				output.add(current_interval);
			} 
			
		}
		
//		return output.toArray(new int[output.size()][2]);
		return output.toArray(new int[output.size()][]);
	}

}
