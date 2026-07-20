package Ripple;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class StockPricesWaitingDaysForHigherPrices {

	public static void main(String[] args) {

        // Example from problem statement
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{4, 4, 6, 12, 5, 8})));

        // Strictly increasing prices
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{1, 2, 3, 4, 5})));

        // Strictly decreasing prices
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{5, 4, 3, 2, 1})));

        // All equal prices
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{7, 7, 7, 7})));

        // Single element
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{10})));

        // Empty array
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{})));

        // Duplicate values
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{2, 2, 3})));

        // Higher price appears much later
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{8, 7, 6, 9})));

        // Multiple rises and falls
        System.out.println(Arrays.toString(
                daysUntilHigher(new int[]{3, 8, 4, 5, 2, 9, 1})));
    }
	
	
	/* Step-by-Step Algorithm (Interview Explanation)
	1. Create an answer array initialized to 0.
	2. Create a stack storing indices.
	3. Traverse the array from left to right.
	4. While the current price is higher than the price at the stack's top:
		- Pop the previous index.
		- Store the waiting days.
	5. Push the current index.
	6. After traversal, remaining indices have no higher future price.
	7. Return the answer.
	 */
	
	/**
     * For each day returns number of days to wait until a strictly higher price occurs.
     * If none, 0 for that day.
     *
     * Time: O(n) — each index pushed and popped at most once.
     * Space: O(n) — result array + stack of indices.
     */
	public static int[] daysUntilHigher(int[] prices) {
        if (prices == null) {
            return null;
        }

        int n = prices.length;
        int[] ans = new int[n];

        // Stores indices of unresolved days.
        // Prices corresponding to these indices are in non-increasing order.
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {

            // Current price resolves all previous smaller prices.
            while (!stack.isEmpty() && prices[i] > prices[stack.peek()]) {
                int prev = stack.pop();
                ans[prev] = i - prev;
            }

            // Current day waits for a future higher price.
            stack.push(i);
        }

        // Remaining indices have no higher future price.
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
