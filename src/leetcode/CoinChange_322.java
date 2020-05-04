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
	
	/* https://leetcode.com/problems/coin-change/
	 * 
	 * 
	 * 
	 * Complexity Analysis

    Time complexity : O(S*n). On each step the algorithm finds the next 
    F(i) in n iterations, where 1<=i<=S. Therefore in total the 
    iterations are S∗n.
    
    Space complexity : O(S). We use extra space for the memoization 
    table.
	 */


	//--working
	public static int coinChange(int[] coins, int amount) {
        int max = amount + 1;
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
	
	//--working
	public static int coinChange2(int[] coins, int amount) {
		int max = amount + 1;
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, max);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= i) {
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
				}
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}
	
	

}
