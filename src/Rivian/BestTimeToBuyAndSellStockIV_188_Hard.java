package Rivian;

import java.util.Arrays;

public class BestTimeToBuyAndSellStockIV_188_Hard {

	public static void main(String[] args) {

		int k = 2;
		int[] prices = {3,2,6,5,0,3};
		System.out.println("Expected: 7, Actual: "+ maxProfit(k, prices));
		
		k = 2;
		int[] prices2 = {2,4,1};
		System.out.println("Expected: 2, Actual: "+ maxProfit(k, prices2));
	}
	
	/*
    Reference: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/54125/Very-understandable-solution-by-reusing-Problem-III-idea
    
    Time: O(n), when 2k<=n, where n is the length of the prices, since we have two for-loop.
    Space: O(k): O(k+k), 2 arrays buy and sell used to hold the value 
    
    */
    
    public static int maxProfit(int k, int[] prices) {
        
        // if k >= n/2, then you can make maximum number of transactions
        if (k >= prices.length / 2) { // or k*2 >= prices.length
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) 
                    profit += prices[i] - prices[i - 1];
            }
            return profit;
        }
        
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        Arrays.fill(buy, Integer.MIN_VALUE);
        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                buy[i] = Math.max(buy[i], sell[i - 1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        return sell[k];
    }

}
