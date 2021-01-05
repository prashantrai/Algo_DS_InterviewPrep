package test.practice.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

	public static void main(String[] args) {
		int[][] intervals = {
								{1,3},
								{2,6},
								{8,10},
								{15,18}
								};

		int[][] output = merge(intervals);
		System.out.println("Expected: [[1,6],[8,10],[15,18]] \n Actual: "+Arrays.deepToString(output));
		
	}
	//--https://leetcode.com/problems/merge-intervals/
	public static int[][] merge(int[][] intervals) {
		
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
