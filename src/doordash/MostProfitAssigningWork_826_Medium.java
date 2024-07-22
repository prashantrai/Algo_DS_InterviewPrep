package doordash;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MostProfitAssigningWork_826_Medium {

	public static void main(String[] args) {
		int[] difficulty = {2,4,6,8,10};
		int[] profit = {10,20,30,40,50}; 
		int[] worker = {4,5,6,7};

		System.out.println("Expected: 100, Actual: " + maxProfitAssignment(difficulty, profit, worker));
		
		// PQ
		System.out.println("Expected: 100, Actual: " + maxProfitAssignment2(difficulty, profit, worker));
		
	}
	
	/* Also refer for explanation: https://leetcode.com/problems/most-profit-assigning-work/discuss/5329650/Beats-100-Explained-with-Video-C%2B%2BJavaPythonJS-DP-Interview-Solution
	 * 
	Leetcode solution:  
	Time complexity: O(n + m + maxAbility)
    Space: O(maxAbility)
    */
    public static int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        // Find max ability in the worker array.
        int maxAbility = Arrays.stream(worker).max().getAsInt();
        
        int[] jobs = new int[maxAbility + 1];
        
        for(int i=0; i<difficulty.length; i++) {
            // If the difficulty at the current index i is less than 
            // or equal to the worker's ability: Store the profit at 
            // index i at the difficulty[i] index of jobs array. If 
            // a value already exists, take the maximum of both values.
            if(difficulty[i] <= maxAbility) {
                jobs[difficulty[i]] = Math.max(jobs[difficulty[i]], profit[i]);
            }
        }
        
        // Store the maximum of current and previous jobs values 
        // in the current jobs index.
        for(int i=1; i<=maxAbility; i++) {
            jobs[i] = Math.max(jobs[i-1], jobs[i]);
        }
        
        // Iterate through all abilities in the worker array:
        // Store maxProfit as jobs[ability] where ability denotes 
        // the ability of the current worker.
        int netProfit = 0;
        for(int ability : worker) {
            netProfit += jobs[ability];
        }
        
        return netProfit;
    }
    
    
    /* Using Priority Queue: https://leetcode.com/problems/most-profit-assigning-work/solution/2463288
    
    Time: Time complexity: O(n⋅logn+m⋅log(m)), 2 priority queues.
    Space: O(n)
 
     */
 
	 public static int maxProfitAssignment2(int[] difficulty, int[] profit, int[] worker) {
	     int res = 0;
	     int max = 0;
	     
	     // difficulty min-heap
		 PriorityQueue<int[]> dpq = new PriorityQueue<>((a, b) -> a[0] - b[0]); 
		 
		 // Worker min-heap
		 PriorityQueue<Integer> wq = new PriorityQueue<>();
		 
		 // put the profit/difficulty as a pair in the min-heap
		 for (int i = 0; i < difficulty.length; i++) {
		     dpq.offer(new int [] {difficulty[i], profit[i]});
		 }
		 
		 for (int w : worker) {
		     wq.offer(w);
		 }
		 
		 // Loop through each worker and pop from the heap 
		 // if the difficulty is higher than the current worker
		     while (!wq.isEmpty()) {
		         if (dpq.isEmpty() || wq.peek() < dpq.peek()[0]) {
		             wq.poll();
		             res += max;
		         } else {
		             max = Math.max(max, dpq.poll()[1]);
		         }
		     }
		     
		     return res;
	 }

}
