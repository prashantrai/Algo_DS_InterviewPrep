package Expedia;

import java.util.ArrayList;
import java.util.List;

public class InsertInterval_57_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N)
    // Space: O(1)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        int i=0;

        List<int[]> res = new ArrayList<>();

        // Step1: Add all the non-overlapping intervals to the result list
        while(i < n && intervals[i][1] < newInterval[0]) {
            res.add(intervals[i]);
            i++;
        }

        // Step2: Process and merge overlapping intervals
        while(i < n && newInterval[1] >= intervals[i][0]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        res.add(newInterval);

        // Step3: Add the remaining intervals to the result list
        while(i < n) {
            res.add(intervals[i]);
            i++;
        }

        // Convert List to array
        return res.toArray(new int[res.size()][]);
     // return res.stream().toArray(int[][]::new); // works

    }

}
