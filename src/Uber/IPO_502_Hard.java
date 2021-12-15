package Uber;

import java.util.PriorityQueue;

public class IPO_502_Hard {

	public static void main(String[] args) {
		int k = 2, w = 0;
		int[] profits = {1,2,3}; 
		int[] capital = {0,1,1};
		
		System.out.println("Expected: 4, Acual: "+ findMaximizedCapital(k, w, profits, capital));
		
		k = 3; w = 0;
		int[] profits2 = {1,2,3}; 
		int[] capital2 = {0,1,2};
		System.out.println("Expected: 6, Acual: "+ findMaximizedCapital(k, w, profits2, capital2));
		
	}
	
     /*
     	Reference: 
     	https://leetcode.com/problems/ipo/discuss/1070292/Java-%2B-Intuition-%2B-2-Heaps-%2B-Greedy-%2B-Explanation
     	https://leetcode.com/problems/ipo/discuss/98210/Very-Simple-(Greedy)-Java-Solution-using-two-PriorityQueues
    
    	Time Complexity: For worst case, each project will be inserted and polled from both PriorityQueues once, so the overall runtime complexity should be O(NlgN), N is number of projects.
    	Space: O(N)
     */
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        
        /* 	maintain a minHeap of projects (pair of capital & profit), 
         *  sorted based in ascending order on capital.
    	*/
        PriorityQueue<int[]> minCapitalProjectsPQ = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        
        /* 
         * We also maintain a cummulative sum of profits, which is updated whenever the maximum 
         * profit project is selected with the current available capital (sum of profits).
         */
        //PriorityQueue<int[]> maxProfitPQ = new PriorityQueue<>((a,b) -> b[1] - a[1]); // or like below
        PriorityQueue<Integer> maxProfitPQ = new PriorityQueue<>((a,b) -> b - a);
        
        // Building the minHeap pair of capital and profits, sorted in ascending order of capital
        for(int i=0; i<capital.length; i++) {
            minCapitalProjectsPQ.offer(new int[]{capital[i], profits[i]});
        }
        
        // Check until k projects are completed
        for(int i=0; i<k; i++) {
            /* Pick up all projects from minCapitalProjectsPQ that meets current cummulative 
             * capital and store it in a maxHeap of profits
             */
            while(!minCapitalProjectsPQ.isEmpty() && minCapitalProjectsPQ.peek()[0] <= w) {
                maxProfitPQ.offer(minCapitalProjectsPQ.poll()[1]);
            }
            
            if(maxProfitPQ.isEmpty()) return w;
            
            // Update the capital by polling the highest profit element from the maxHeap of profits
            w += maxProfitPQ.poll();
        }
        
        return w;
    }

}
