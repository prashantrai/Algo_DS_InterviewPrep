package Snowflake;

public class MaximumProfitFromTradingStocks_2291_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// For approach and explanation: 
	// 	https://algo.monster/liteproblems/2291
	
	// for recursive solution: https://walkccc.me/LeetCode/problems/2291/ 
	
	
	// Time: O(n * budget), where n is the length of the lists.
	// Space: O(budget)
	
	public int maximumProfit(int[] present, int[] future, int budget) {
        
        int numberOfItems = present.length; 
        // dp array to store the maximum profit for each budget upto the given budget
        // why budget+1? because e.g. if budget=5 then w'll store the result 
        // at dp[5] which is is 6th index in the dp array
        int[] dp = new int[budget + 1]; 
        
        // Iterate over each item
        for(int i=0; i<numberOfItems; i++) {
            int presentValue = present[i];
            int futureValue = future[i];
            
            // Iterate over all possible remaining budgets in reverse
            // to avoid using same item's profit more than once
            for(int currentBudget = budget; currentBudget >= presentValue; currentBudget--) {
                // Update dp array with the maximum profit achievable with the current budget
                dp[currentBudget] = Math.max(dp[currentBudget], 
                        dp[currentBudget - presentValue] + futureValue - presentValue);
            }
        }
        // Return the maximum profit that can be achieved with the given budget
        return dp[budget];
    }
}
