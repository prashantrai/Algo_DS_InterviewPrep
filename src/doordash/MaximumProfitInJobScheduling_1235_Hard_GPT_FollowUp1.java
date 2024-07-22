package doordash;
import java.util.*;

public class MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp1 {
	
	public static void main(String[] args) {
        MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp1 solution = new MaximumProfitInJobScheduling_1235_Hard_GPT_FollowUp1();
        
        int[] startTime = {1, 2, 3, 3};
        int[] endTime = {3, 4, 5, 6};
        int[] profit = {50, 10, 40, 70};
//        System.out.println("Expected: 120, Actual: " + solution.jobScheduling(startTime, endTime, profit)); // Output: 120
		
        // Using JobState class instead of Object[]
        System.out.println("Expected: 120, Actual: " + solution.jobScheduling2(startTime, endTime, profit)); // Output: 120
        
        int[] startTime2 = {1,2,3, 4,6};
		int[] endTime2 =   {3,5,10,6,9};
		int[] profit2 = {20,20,100,70,60};
//		System.out.println("Expected: 150, Actual: " + solution.jobScheduling(startTime2, endTime2, profit2)); // Output: 150

		// Using JobState class instead of Object[]		
		System.out.println("Expected: 150, Actual: " + solution.jobScheduling2(startTime2, endTime2, profit2)); // Output: 150
//		
		int[] startTime3 = {2, 3, 5, 7}; 
		int[] endTime3 =   {6, 5, 10, 11}; 
		int[] profit3 =    {5, 2, 4, 1};
//		System.out.println("Expected: 6, Actual: " + solution.jobScheduling(startTime3, endTime3, profit3));
		
		// Using JobState class instead of Object[]
		System.out.println("Expected: 6, Actual: " + solution.jobScheduling2(startTime3, endTime3, profit3));
    }
	
	
	// Another approach: Using JobState class instead of Object[]
    // JobState is used for Follow-up: 1 wheren we need to print the list of all jobs led to max profit
    public int jobScheduling2(int[] startTime, int[] endTime, int[] profit) { // Follow-up 3: Added N parameter
    	int n = startTime.length;
        Job[] jobs = new Job[n];

        // Create Job objects and sort by start time
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        Arrays.sort(jobs, (a, b) -> a.start - b.start);

        // Priority queue to keep track of the maximum profit up to each job
        // Follow-up 1: Use JobState to include job sequences leading to max profit
        PriorityQueue<JobState> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));

        
        int maxProfit = 0;
        List<Job> maxProfitJobs = new ArrayList<>();  // Follow-up 1: Track jobs leading to max profit

        for (Job job : jobs) {
            // Remove jobs that end before the current job's start time
            while (!pq.isEmpty() && (int) pq.peek().end <= job.start) {
            	JobState state = pq.poll();
                if (state.profit > maxProfit) {  // Follow-up 1: Update max profit and job list
                    maxProfit = (int) state.profit;
                    maxProfitJobs = new ArrayList<>((List<Job>) state.jobList);
                }
            }
            // Add the current job's end time and cumulative profit to the priority queue
            // Follow-up 1: Include the current job in the job sequence
            List<Job> currentJobList = new ArrayList<>(maxProfitJobs);
            currentJobList.add(job);
            
            pq.offer(new JobState(job.end, maxProfit + job.profit, currentJobList));
            
        }

        // Process remaining jobs in the priority queue
        while (!pq.isEmpty()) {
        	JobState state = pq.poll();
            if ((int) state.profit > maxProfit) {  // Follow-up 1: Update max profit and job list
                maxProfit = (int) state.profit;
                maxProfitJobs = new ArrayList<>(state.jobList);
            }
        }

        // Follow-up 1: Print jobs that led to the max profit
        System.out.println("Jobs leading to maximum profit: " + maxProfitJobs);
        return maxProfit;
    }
	
    
	// With Object array instean of JobState class
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];

        // Create Job objects and sort by start time
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        Arrays.sort(jobs, (a, b) -> a.start - b.start);

        // Priority queue to keep track of the maximum profit up to each job
        // Follow-up 1: Use Object[] to include job sequences leading to max profit
        PriorityQueue<Object[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> (int) a[0]));
        int maxProfit = 0;
        List<Job> maxProfitJobs = new ArrayList<>();  // Follow-up 1: Track jobs leading to max profit

        for (Job job : jobs) {
            // Remove jobs that end before the current job's start time
            while (!pq.isEmpty() && (int) pq.peek()[0] <= job.start) {
                Object[] entry = pq.poll();
                if ((int) entry[1] > maxProfit) {  // Follow-up 1: Update max profit and job list
                    maxProfit = (int) entry[1];
                    maxProfitJobs = new ArrayList<>((List<Job>) entry[2]);
                }
            }
            // Add the current job's end time and cumulative profit to the priority queue
            // Follow-up 1: Include the current job in the job sequence
            List<Job> currentJobList = new ArrayList<>(maxProfitJobs);
            currentJobList.add(job);
            
            pq.offer(new Object[]{job.end, maxProfit + job.profit, currentJobList});
            
            //above can be replaced with below
            //pq.offer(new JobState(job.end, maxProfit + job.profit, currentJobList));
            
        }

        // Process remaining jobs in the priority queue
        while (!pq.isEmpty()) {
            Object[] entry = pq.poll();
            if ((int) entry[1] > maxProfit) {  // Follow-up 1: Update max profit and job list
                maxProfit = (int) entry[1];
                maxProfitJobs = new ArrayList<>((List<Job>) entry[2]);
            }
        }

        // Follow-up 1: Print jobs that led to the max profit
        System.out.println("Jobs leading to maximum profit: " + maxProfitJobs);
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
    
    // Follow-up 1: to hold the state of a Job
    private static class JobState {
        int end;
        int profit;
        List<Job> jobList;

        JobState(int end, int profit, List<Job> jobList) {
            this.end = end;
            this.profit = profit;
            this.jobList = new ArrayList<>(jobList);
        }
    }
    
}
