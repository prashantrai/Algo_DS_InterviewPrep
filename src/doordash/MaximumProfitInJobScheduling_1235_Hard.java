package doordash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MaximumProfitInJobScheduling_1235_Hard {

	public static void main(String[] args) {
		int[] startTime = {1,2,3,3}; 
		int[] endTime = {3,4,5,6}; 
		int[] profit = {50,10,40,70};
		
		System.out.println("Expected: 120, Actual: " + jobScheduling(startTime, endTime, profit));
		
		int[] startTime2 = {1,2,3,4,6};
		int[] endTime2 = {3,5,10,6,9};
		int[] profit2 = {20,20,100,70,60};
		
		System.out.println("Expected: 150, Actual: " + jobScheduling(startTime2, endTime2, profit2));
	}

	/* 	Follow-up 1: Print the all jobs that led to max profit
        Follow-up 2: SCALE FOR N ORDERS. If the dasher is allowed to handle up to 
        			 N orders at the same time, what will be the max profit?
        Refer: https://leetcode.com/discuss/interview-question/1320711/doordash-phone-screen
         
	 * 
	 * 
	Taken from Leetcode premium - Using priority queue
    Time: O(nlogn)
    Space: O(N), Each of the N jobs will be pushed into the heap. In the 
        worst-case scenario, when all N jobs end later than the last job starts, 
        the heap will reach size N.
        
    Algorithm: 
	    1. Store the startTime, endTime, and profit of each job in jobs.
		2. Sort the jobs according to their starting time.

		3. Iterate over jobs from left to right, where i is the index of the current job. 
			For each job,
			i. While the job chain at the top of the priority queue does not conflict 
			   with the current job, pop from the priority queue.
			   
			ii. For each popped job chain compare its total profit with the maxProfit 
			    and update maxProfit accordingly.
		
		4. 	Push the pair of ending time and the combined profit (maxProfit + profit of this job) 
			into the heap. This item represents the chain created by adding the current job to the 
			most profitable job chain.
		
		5. 	After iterating over all the jobs, compare the profit of the remaining chains in the 
			priority queue with the maxProfit. Return the value of maxProfit.
    */
    public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        List<List<Integer>> jobs = jobListByStartTime(startTime, endTime, profit);
        System.out.println("jobs: "+jobs);        
        return findMaxProfit(jobs);
    }
    
    private static int findMaxProfit(List<List<Integer>> jobs) {
        //int n = jobs.size();
        int maxProfit = 0;
        
        // min heap having {endTime, profit}
        PriorityQueue<ArrayList<Integer>> pq 
            = new PriorityQueue<>((list1, list2) -> list1.get(0) - list2.get(0));
        
        //added for Follow-up 1: Print the all jobs that led to max profit
        //List<List<Integer>> jobsLedToMaxProfits = new ArrayList<>();
        List<Integer> jobsIdxLedToMaxProfits = new ArrayList<>(); //store index of jobs
        int idx = 0;
        
        for(List<Integer> job : jobs) {
            int start = job.get(0);
            int end = job.get(1);
            int profit = job.get(2);
            
            // keep popping while the heap is not empty and
            // jobs are not conflicting
            // update the value of maxProfit
            while(!pq.isEmpty() && start >= pq.peek().get(0)) {
            	
            	// added for Follow-up 1: Print all jobs that led to max profit
                if(pq.peek().get(1) > maxProfit) 
                	jobsIdxLedToMaxProfits.add(pq.peek().get(2)); 
            	
                maxProfit = Math.max(maxProfit, pq.peek().get(1));
                
                // as we only need to keep track of maxProfit so once that's calculated 
                // remove the entry from PQ
                pq.poll();
            }
            
            //Combine the Job, and push end with profit+maxprofit
            //why end only? as we have to only make sure that time is not overlapping
            // and for that we have to compare prev endTime (on peek of PQ)
            //with the curr startTime.
            ArrayList<Integer> combinedJob = new ArrayList<>();
            combinedJob.add(end);
            combinedJob.add(profit + maxProfit);
            combinedJob.add(idx++); //idx of each job : added for Follow-up 1: Print all jobs that led to max profit
            
            // push the job with combined profit
            // if no non-conflicting job is present maxProfit will be 0
            pq.offer(combinedJob);
        }
        
        int maxProfitIdx = -1; // added for Follow-up 1: Print all jobs that led to max profit
        
        // update the value of maxProfit by comparing with
        // profit of jobs that exits in the heap
        while(!pq.isEmpty()) {
        	// added for Follow-up 1: Print all jobs that led to max profit
            if(pq.peek().get(1) > maxProfit) 
            	maxProfitIdx = pq.peek().get(2);
        	
            maxProfit = Math.max(maxProfit, pq.peek().get(1));
            
            pq.poll();
        }
        
        // added for Follow-up 1: Print all jobs that led to max profit
        if(maxProfitIdx >= 0)
        	jobsIdxLedToMaxProfits.add(maxProfitIdx);
        System.out.println("jobsIdxLedToMaxProfits:: "+jobsIdxLedToMaxProfits);
        
        return maxProfit;
    }
    
    private static List<List<Integer>> jobListByStartTime(int[] startTime, int[] endTime, int[] profit) {
        List<List<Integer>> jobs = new ArrayList<>();
        for (int i=0; i<startTime.length; i++) {
            List<Integer> currJob = new ArrayList<>();
            currJob.add(startTime[i]);
            currJob.add(endTime[i]);
            currJob.add(profit[i]);;
            jobs.add(currJob);
        }
        jobs.sort(Comparator.comparingInt(a -> a.get(0))); //sort by startTime
        return jobs;
    }

    //NOT IN USE: Alternatively we can also use 2d array to store jobs
    private static int[][] merge(int[] startTime, int[] endTime, int[] profit) {
        int[][] combine = new int [startTime.length][3];
        for (int i=0; i<startTime.length; i++) {
            combine[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }
        
        //sort by start time
         Arrays.sort(combine, (a,b)->a[0]-b[0]); // sort by start time
        
        return combine;
    }
	
	
	
	/* 	Time : O(nlogn)
	 	Space: O(N), use of TreeMap
		https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
		Using TreeMap - https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409229/Java-DP-with-TreeMap-20-lines-O(nlogn)
		https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
	*/
	public static int jobScheduling2(int[] startTime, int[] endTime, int[] profit) {
		int n = startTime.length;
	  	int[][] jobs = new int[n][3];
	  	
	  	for (int i = 0; i < n; i++) {
	     jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
	    }
		
	  	Arrays.sort(jobs, (a, b)->a[1] - b[1]); //sort by endTime
		
		TreeMap<Integer, Integer> dp = new TreeMap<>();
		dp.put(0, 0); // put a default entry 
		for (int[] job : jobs) {
			// floorEntry: a key-value mapping associated with the greatest key less than 
			// or equal to the given key, or null if there is no such key
			// in or case it will return 0 first time
		    int curMaxProfit = dp.floorEntry(job[0]).getValue() + job[2]; // current maxProfit  
		    
		    // lastEntry: key-value mapping associated with the greatest key in this map, 
		    // or null if the map is empty
		    
		    // compare maxProfit 
		    if (curMaxProfit > dp.lastEntry().getValue())
		        dp.put(job[1], curMaxProfit);
		}
		return dp.lastEntry().getValue();
	}
	
	//time: O(N*logN), Space O(N)
	//From comments of : https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
	//Array based solution: dp + binary search
	public static int jobScheduling3(int[] startTime, int[] endTime, int[] profit) {
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

}


/**

1235. Maximum Profit in Job Scheduling - Hard

We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], 
obtaining a profit of profit[i].

You're given the startTime, endTime and profit arrays, return the maximum profit you can 
take such that there are no two jobs in the subset with overlapping time range.

If you choose a job that ends at time X you will be able to start another job that starts at time X.

Example 1:
	Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
	Output: 120
	Explanation: The subset chosen is the first and fourth job. 
		Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
  
Example 2:
	Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
	Output: 150
	Explanation: The subset chosen is the first, fourth and fifth job. 
	Profit obtained 150 = 20 + 70 + 60. 
	
Example 3:
	Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
	Output: 6
  
 */
