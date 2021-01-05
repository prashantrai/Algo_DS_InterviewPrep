package leetcode;

import java.util.Arrays;

public class NonOverlappingIntervals_435_Medium {

	public static void main(String[] args) {
		int[][] intervals = { {1, 2}, {2, 3}, {3, 4}, {1, 3} };

		int output = eraseOverlapIntervals(intervals);
		System.out.println("Expected: 1,  Actual: " + output);

		int[][] intervals2 = { {1, 2}, {1, 2}, {1, 2}, {1, 2} };

		output = eraseOverlapIntervals(intervals2);
		System.out.println("Expected: 3,  Actual: " + output);

		int[][] intervals3 = {{1,100},{11,22},{1,11},{2,12}};
		
		output = eraseOverlapIntervals(intervals3);
		System.out.println("Expected: 2,  Actual: " + output);
		
		output = eraseOverlapIntervals2(intervals3);
		System.out.println("Expected: 2,  Actual: " + output);

	
	}
	// https://leetcode.com/problems/non-overlapping-intervals/
	// Time : N LogN
	
	
	//https://leetcode.com/problems/non-overlapping-intervals/discuss/481758/Easy-to-Understand-Java-Solution-Beats-100
	public static int eraseOverlapIntervals(int[][] intervals) {
        
        if(intervals == null || intervals.length == 0) 
            return 0;
        
        Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[0], arr2[0]));
        
        System.out.println("Sorted: " + Arrays.deepToString(intervals));
        
        int prevEnd = intervals[0][1];
        int count = 0;
        for(int i=1; i<intervals.length; i++) {
            if(prevEnd > intervals[i][0]) {
                count++;
                prevEnd = Math.min(prevEnd, intervals[i][1]);
            }
            else {
                prevEnd = intervals[i][1];                
            }
        }
        
        return count;
    }
	
	public static int eraseOverlapIntervals2(int[][] intervals) {
        
        if(intervals == null || intervals.length == 0) 
            return 0;
        
        // Sort by endtime i.e. 2nd index of each array
        Arrays.sort(intervals, (arr1, arr2) -> Integer.compare(arr1[1], arr2[1]));
        System.out.println("Sorted: " + Arrays.deepToString(intervals));
        
        int prevEnd = intervals[0][1];
        int count = 0;
        for(int i=1; i<intervals.length; i++) {
            if(prevEnd > intervals[i][0]) {
                count++;
            }
            else {
                prevEnd = intervals[i][1];                
            }
        }
        
        return count;
    }
    
    
	
	/*class myComparator implements Comparator<Interval> {
        public int compare(Interval a, Interval b) {
            return a.end - b.end;
        }
    }*/

}
