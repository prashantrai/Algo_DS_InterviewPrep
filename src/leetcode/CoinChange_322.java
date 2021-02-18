package leetcode;

import java.util.Arrays;

public class CoinChange_322 {

	public static void main(String[] args) {

		/*Input: coins = [1, 2, 5], amount = 11
		Output: 3
		*/
		int[] coins = {1,2,5};
		int amount = 11;
		
		int res = coinChange(coins, amount);
		System.out.println("res="+res);
		
	}
	
	/*
    Reference: 
        for code: https://www.youtube.com/watch?v=1R0_7HqNaW0&ab_channel=KevinNaughtonJr.
        Video to understand the algorithm: 
            https://www.youtube.com/watch?v=jgiZlGzXMBw&ab_channel=BackToBackSWE
            https://www.youtube.com/watch?v=jaNZ83Q3QGc&ab_channel=StephenO%27Neill
    
    
    Time: O(m*n), where m is each amount and n is each coin
    Space: O(N) for dp arr 
    */
    
    public static int coinChange(int[] coins, int amount) {
        Arrays.sort(coins); //just an optimzation. code works wihout this as well
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        
        for(int i=0; i<=amount; i++) {
            for(int j=0; j<coins.length; j++) {
                if(coins[j] <= i) { //only.when current coin is less or equal to amount. This is an optimization step to avoid extra iteration
                    
                    // dp[i] = min(dp[current_index_value], 1 + dp[current_amt - current_coin]
                    // dp[current_amt - current_coin] : This will give the next amount value we need to complete the sum
                    dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
	
	
	/* https://leetcode.com/problems/coin-change/
	 * 
	 * Complexity Analysis

	    Time complexity : O(S*n). On each step the algorithm finds the next 
	    F(i) in n iterations, where 1<=i<=S. Therefore in total the 
	    iterations are S∗n.
	    
	    Space complexity : O(S). We use extra space for the memoization 
	    table.
	 */

	// For approach explaination : https://www.youtube.com/watch?time_continue=281&v=jgiZlGzXMBw&feature=emb_title
	//--working
	public static int coinChange2(int[] coins, int amount) {
        //int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        
        for(int amt=1; amt<=amount; amt++) {
            for(int j=0; j<coins.length; j++) {
                int coin = coins[j];
                if(coin <= amt) {
                    dp[amt] = Math.min (dp[amt], dp[amt - coin] + 1);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }

}
