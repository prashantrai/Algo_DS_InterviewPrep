package Rivian;

public class BestTimeToBuyAndSellStockIV_188_Hard {

	public static void main(String[] args) {

		int k = 2;
		int[] prices = {3,2,6,5,0,3};
		System.out.println("Expected: 6, Actual: "+ maxProfit(prices));
		
		k = 2;
		int[] prices2 = {2,4,1};
		System.out.println("Expected: 4, Actual: "+ maxProfit(prices2));
	}
	
	/*
	 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
	 * 
	 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv//submissions/
	 * 
	 * Leetcode 309- Best Time to Buy and Sell Stock with Cooldown:: https://discuss.leetcode.com/topic/30680/share-my-dp-solution-by-state-machine-thinking
	 * 
    
    Complexity: 
       Time : O(n)
       Space : O(1)
    
    */
    
    public static int maxProfit(int[] prices) {
        int buy_1 = Integer.MAX_VALUE;
        int buy_2 = Integer.MAX_VALUE;
        int sell_1 = 0, sell_2 = 0;
        
        for(int price : prices) {
            buy_1 = Math.min(buy_1, price);
            sell_1 = Math.max(sell_1, price - buy_1);
            
            buy_2 = Math.min(buy_2, price - sell_1);
            sell_2 = Math.max(sell_2, price - buy_2);
        }
        
        return sell_2;
    }

}
