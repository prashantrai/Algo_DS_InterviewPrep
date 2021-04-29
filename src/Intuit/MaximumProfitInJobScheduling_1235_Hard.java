package Intuit;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class MaximumProfitInJobScheduling_1235_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//time: N*logN
	//From comments of : https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
	//Array based solution:
	public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = startTime[i];
            jobs[i][1] = endTime[i];
            jobs[i][2] = profit[i];
        }
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Math.max(dp[i], dp[i + 1]);
            int lo = 0, hi = i;
            while(lo < hi - 1) {
                int m = lo + (hi - lo)/ 2;
                if (jobs[m][1] <= jobs[i][0]) {
                    lo = m;
                } else {
                    hi = m;
                }
            }
            dp[i + 1] = Math.max(dp[i + 1], ((jobs[lo][1] <= jobs[i][0]) ? dp[lo + 1] : 0) + jobs[i][2]);
        }
        return dp[n];
    }

	
	
	//Time : n logn
	//https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
	//Using TreeMap - https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409229/Java-DP-with-TreeMap-20-lines-O(nlogn)
	class Solution_TreeMap {
	    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
	        int[][] combine = new int [startTime.length][3];
	        for (int i=0; i<startTime.length; i++) {
	            combine[i] = new int[] {startTime[i], endTime[i], profit[i]};
	        }
	        Arrays.sort(combine, (a,b)->a[1]-b[1]); // sort by endtime
	        
	        TreeMap<Integer, Integer> map = new TreeMap<>();
	        int ans = 0;
	        
	        for (int[] curr: combine) {
	            Integer prev = map.floorKey(curr[0]);
	            int prevSum = prev==null?0:map.get(prev);
	            ans = Math.max(ans, prevSum+curr[2]);
	            map.put(curr[1], ans);
	        }              
	        return ans;
	    }
	}

}
