package doordash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MaximumProfitInJobScheduling_1235_Hard_PhoneScreenVersion_with_FollowUps {

	public static void main(String[] args) {
		int[] startTime = {1,2,3,3}; 
		int[] endTime = {3,4,5,6}; 
		int[] profit = {50,10,40,70};
		
		System.out.println("Expected: 120, Actual: " + jobScheduling(startTime, endTime, profit));
		
		int[] startTime2 = {1,2,3,4,6};
		int[] endTime2 = {3,5,10,6,9};
		int[] profit2 = {20,20,100,70,60};
		
		System.out.println("Expected: 150, Actual: " + jobScheduling(startTime2, endTime2, profit2));
		
		
		int[] startTime3 = {2, 3, 5, 7}; 
		int[] endTime3 = {6, 5, 10, 11}; 
		int[] profit3 = {5, 2, 4, 1};
		
		System.out.println("Expected: 6, Actual: " + jobScheduling(startTime3, endTime3, profit3));
	}

	/* 	Follow-up 1: Print all the jobs that led to max profit
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
    
	
	
	// sort by start time
	public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        
        // Create Job objects and sort by start time
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        
        // sort by start time
        Arrays.sort(jobs, (a, b) -> a.start - b.start);
        
        // sort by end time: can also be done by sorting by end time
        // rest of the code will remain same only this line is the change.
//        Arrays.sort(jobs, (a, b) -> a.end - b.end);

        // Priority queue (min-heap) to keep track of the maximum profit up to each job
        PriorityQueue<int[]> minPQ = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int maxProfit = 0;
        
        // Follow-up 1: Track jobs leading to max profit
        //List<Job> maxProfitJobs = new ArrayList<>();  
        List<Integer> maxProfitJobs = new ArrayList<>();  

        
        //for (Job job : jobs) {
        for (int i=0; i<jobs.length; i++) {
        	Job job = jobs[i];
        	
            // Remove jobs that end before the current job's start time
//            while (!minPQ.isEmpty() && minPQ.peek()[0] <= job.start) {
//                maxProfit = Math.max(maxProfit, minPQ.poll()[1]);
//            }
        	
        	// Above updated as below for Follow-up 1
        	while (!minPQ.isEmpty() && minPQ.peek()[0] <= job.start) {
            	int[] entry = minPQ.poll();
            	int currProfit = entry[1];
            	int idx = entry[2]; // job index in input array
            	
            	if(currProfit > maxProfit) {
            		maxProfitJobs.add(idx);
            		maxProfit = currProfit;
            	}
            	
            }
        	
            // Add the current job's end time and cumulative profit to the priority queue
            //minPQ.offer(new int[] { job.end, maxProfit + job.profit });
            
            // Follow-up 1: Print all the jobs that led to max profit
            // Code will be update to below
            //List<Job> currentJobList  = new ArrayList<>(maxProfitJobs);
            //currentJobList.add(job);
            minPQ.offer(new int[] { job.end, maxProfit + job.profit, i});
            
        }

        int maxProfitIdx = -1;
        
        // Process remaining jobs in the priority queue
        while (!minPQ.isEmpty()) {
            //maxProfit = Math.max(maxProfit, minPQ.poll()[1]);
        	int[] entry = minPQ.poll();
        	int currProfit = entry[1];
        	if(currProfit > maxProfit) {
        		maxProfit = currProfit;
        		maxProfitIdx = entry[2];
        	}
        }
        
        if(maxProfitIdx >= 0)
        	maxProfitJobs.add(maxProfitIdx);
        
        System.out.println("maxProfitJobs:: " + maxProfitJobs);

        return maxProfit;
    }
	
	
	
	private static class Job {
	    int start, end, profit;

	    Job(int start, int end, int profit) {
	        this.start = start;
	        this.end = end;
	        this.profit = profit;
	    }
	    
	    @Override
	    public String toString() {
	        return "[" + start + ", " + end + ", " + profit + "]";
	    }
	}

}


/**

>Phone Screen Question [https://leetcode.com/discuss/interview-question/1320711/doordash-phone-screen]	

	You're a dasher, and you want to try planning out your schedule. You can 
	view a list of deliveries along with their associated start time, end time, 
	and dollar amount for completing the order. Assuming dashers can only deliver 
	one order at a time, determine the maximum amount of money you can make from 
	the given deliveries.
	
	The inputs are as follows:
	
	int start_time: when you plan to start your schedule
	int end_time: when you plan to end your schedule
	int d_starts[n]: the start times of each delivery[i]
	int d_ends[n]: the end times of each delivery[i]
	int d_pays[n]: the pay for each delivery[i]
	
	The output should be an integer representing the maximum amount of money 
	you can make by forming a schedule with the given deliveries.
	
	Example #1
	start_time = 0
	end_time = 10
	d_starts = [2, 3, 5, 7]
	d_ends = [6, 5, 10, 11]
	d_pays = [5, 2, 4, 1]
	Expected output: 6

		
  
 */
