package Ripple;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class StockPricesWaitingDaysForHigherPrices {

	public static void main(String[] args) {
		int[][] tests = {
	            {4,4,6,12,5,8},      // example
	            {5,4,3,2,1},         // strictly decreasing -> all zeros
	            {1,2,3,4,5},         // strictly increasing -> [1,1,1,1,0]
	            {},                  // empty
	            {7},                 // single element
	            {3,3,3,3},           // all equal -> all zeros
	            {2,1,2,1,2,1,3}      // complex with later higher jump
	        };
		
		for (int[] t : tests) {
            int[] res = daysUntilHigher(t);
            System.out.println("prices: " + Arrays.toString(t) + " -> waits: " + Arrays.toString(res));
        }
    }
	
	/**
     * For each day returns number of days to wait until a strictly higher price occurs.
     * If none, 0 for that day.
     *
     * Time: O(n) — each index pushed and popped at most once.
     * Space: O(n) — result array + stack of indices.
     */
    public static int[] daysUntilHigher(int[] prices) {
        if (prices == null) return null;
        int n = prices.length;
        int[] ans = new int[n];
        
        // Stack holds indices of days; prices[stack] is strictly decreasing top-to-bottom.
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i<n; i++) {
        	
        	// Resolve any previous days that have a lower price than prices[i]
        	while(!stack.isEmpty() && prices[i] > prices[stack.peek()]) {
        		int prev = stack.pop();
        		ans[i] = i - prev;
        	}
        	// Current day might wait for future higher price
        	stack.push(i);
        }
        
        // Remaining indices in stack have no higher future price -> ans[x] stays 0
        return ans;
    }

}

/*
Given an array of stock prices, for each day we need to find how many days 
we have to wait until we see a price that is strictly higher than the current 
day's price. If no higher price appears in the future, we put 0.

Example:

Input: [4, 4, 6, 12, 5, 8]
Output: [2, 1, 1, 0, 1, 0]

Explanation:
Day 0 (price 4): Wait 2 days to reach price 6 (which is > 4)
Day 1 (price 4): Wait 1 day to reach price 6 (which is > 4)
Day 2 (price 6): Wait 1 day to reach price 12 (which is > 6)
Day 3 (price 12): No higher price in future → 0
Day 4 (price 5): Wait 1 day to reach price 8 (which is > 5)
Day 5 (price 8): No future days → 0 
 */
