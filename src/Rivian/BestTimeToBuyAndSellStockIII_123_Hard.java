package Rivian;

public class BestTimeToBuyAndSellStockIII_123_Hard {

	public static void main(String[] args) {

		int[] prices = {3,3,5,0,0,3,1,4};
		System.out.println("1. Expected: 6, Actual: "+ maxProfit(prices));
		System.out.println("2. Expected: 6, Actual: "+ maxProfit2(prices));
		System.out.println("3. Expected: 6, Actual: "+ maxProfit3(prices));
		
		int[] prices2 = {1,2,3,4,5};
		System.out.println("4. Expected: 4, Actual: "+ maxProfit(prices2));
		System.out.println("5. Expected: 4, Actual: "+ maxProfit2(prices2));
		System.out.println("6. Expected: 4, Actual: "+ maxProfit3(prices2));
	}
	
	/*
	 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
	 * 
	 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/submissions/
	 * 
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
    
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/39611/Is-it-Best-Solution-with-O(n)-O(1).
    
    
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
    
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/149383/Easy-DP-solution-using-state-machine-O(n)-time-complexity-O(1)-space-complexity
    public static int maxProfit2(int[] prices) {
        int buy1 = Integer.MIN_VALUE, sell1 = 0, buy2 = Integer.MIN_VALUE, sell2 = 0;
        for (int price : prices) {
            buy1 = Math.max(buy1, -price);  
            sell1 = Math.max(sell1, buy1 + price);
            buy2 = Math.max(buy2, sell1 - price);
            sell2 = Math.max(sell2, buy2 + price);
        }
        return sell2;
    }
    
    /*
     * O(n) time complexity, O(1) space solution using State Machine and DP
     https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/149383/Easy-DP-solution-using-state-machine-O(n)-time-complexity-O(1)-space-complexity
    
    * Also look at this: https://discuss.leetcode.com/topic/30680/share-my-dp-solution-by-state-machine-thinking
    */
    public static int maxProfit3(int[] prices) {
    	if(prices.length < 2) { 
            return 0;
        }
        int s1 = -prices[0]; //s1 is the cost to purchase a stock, so since currently we dont have any money yet, the cost should be negative.
        int s2 = Integer.MIN_VALUE;
        int s3 = Integer.MIN_VALUE; 
        int s4 = Integer.MIN_VALUE;
            
    	for(int i=1;i<prices.length; ++i) {            
    		s1 = Math.max(s1, -prices[i]);
    		s2 = Math.max(s2, s1+prices[i]);
    		s3 = Math.max(s3, s2-prices[i]);
    		s4 = Math.max(s4, s3+prices[i]);
    	}
    	return Math.max(0, s4);
    }

}
