package doordash;
import java.util.*;


public class MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp2 {
	
	public static void main(String[] args) {
        MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp2 solution = new MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp2();
        
        int N = 3;  // Number of simultaneous orders
        
        int[] startTime = {1, 2, 3, 3};
        int[] endTime = {3, 4, 5, 6};
        int[] profit = {50, 10, 40, 70};
        System.out.println("Expected: 120, Actual: " 
        		+ solution.jobScheduling(startTime, endTime, profit, N)); // Output: 120
        
        int[] startTime2 = {1,2,3, 4,6};
		int[] endTime2 =   {3,5,10,6,9};
		int[] profit2 = {20,20,100,70,60};
		System.out.println("Expected: 150, Actual: " 
				+ solution.jobScheduling(startTime2, endTime2, profit2, N)); // Output: 150
    }
	
	//Follow-up 2  handle up to N orders concurrently and return the max profit
	
	public int jobScheduling(int[] startTime, int[] endTime, int[] profit, int N) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        
        // Create an array of jobs
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        // Sort jobs by their start time
        Arrays.sort(jobs, (a, b) -> a.start - b.start);
        
        // Priority queue (min-heap) to keep track of ongoing jobs
        // job with the smallest end time is always at the head of the queue
        PriorityQueue<Job> minPQ = new PriorityQueue<>((a, b) -> a.end - b.end);
        int maxProfit = 0;

        for (Job job : jobs) {
            // Remove all jobs from the queue that have ended before the current job starts
            while (!minPQ.isEmpty() && minPQ.peek().end <= job.start) {
                minPQ.poll();
            }

            // Add current job to the priority queue
            minPQ.add(job);

            // If we have more than N jobs in the queue, remove the job with the smallest end time
            if (minPQ.size() > N) {
                minPQ.poll();
            }

            // Calculate the current total profit
            int currentProfit = 0;
            for (Job j : minPQ) {
                currentProfit += j.profit;
            }
            maxProfit = Math.max(maxProfit, currentProfit);
        }

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
