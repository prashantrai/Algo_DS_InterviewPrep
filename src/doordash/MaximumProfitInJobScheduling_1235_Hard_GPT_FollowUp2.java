package doordash;
import java.util.*;


public class MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp2 {
	
	public static void main(String[] args) {
        int[] startTime1 = {1, 2, 3, 3};
        int[] endTime1 = {3, 4, 5, 6};
        int[] profit1 = {50, 10, 40, 70};
        int N1 = 3; // Example concurrent orders limit
        System.out.println("Expected: 120, Actual: " 
        		+ jobScheduling(startTime1, endTime1, profit1, N1)); // Expected Output: 120

        int[] startTime2 = {1, 2, 3, 4, 6};
        int[] endTime2 = {3, 5, 10, 6, 9};
        int[] profit2 = {20, 20, 100, 70, 60};
        int N2 = 3; // Example concurrent orders limit
        System.out.println("Expected: 150, Actual: " 
        		+ jobScheduling(startTime2, endTime2, profit2, N2)); // Expected Output: 150
    }
	
	
	//Follow-up 2  handle up to N orders concurrently and return the max profit
	
	public static int jobScheduling(int[] startTime, int[] endTime, int[] profit, int N) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        
        // Create an array of jobs
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        // Sort jobs by their start time
        Arrays.sort(jobs, (a, b) -> a.start - b.start);
        
        // Priority queue (min-heap) to keep track of ongoing jobs and store max profit upto current job
        // job with the smallest end time is always at the head of the queue
        PriorityQueue<int[]> minPQ = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int maxProfit = 0;

        for (Job job : jobs) {
            // Remove all jobs from the queue that have ended before the current job starts
        	while (!minPQ.isEmpty() && minPQ.peek()[0] <= job.start) { // minPQ.peek()[0] is end time
                maxProfit = Math.max(maxProfit, minPQ.poll()[1]);
//                maxProfit += Math.max(maxProfit, minPQ.poll()[1]);
            }

            // Add current job to the priority queue
            minPQ.offer(new int[]{job.end, maxProfit + job.profit});
//            minPQ.offer(new int[]{job.end, job.profit});

            // Maintain up to N concurrent jobs in the priority queue
            // If we have more than N jobs in the queue, remove the 
            // job with the smallest end time
            if (minPQ.size() > N) {
                minPQ.poll();
            }
        }

        // Process remaining jobs in the priority queue
        while (!minPQ.isEmpty()) {
        	maxProfit = Math.max(maxProfit, minPQ.poll()[1]);
//        	maxProfit += minPQ.poll()[1];
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
