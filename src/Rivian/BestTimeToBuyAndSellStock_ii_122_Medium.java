package Rivian;

public class BestTimeToBuyAndSellStock_ii_122_Medium {

	public static void main(String[] args) {
		int[] prices = {7,1,5,3,6,4};
		System.out.println("Expected: 7, Actual: " + maxProfit(prices));
		
		int[] prices2 = {7,6,4,3,1};
		System.out.println("Expected: 0, Actual: " + maxProfit(prices2));
		
		int[] prices3 = {1,2,3,4,5};
		System.out.println("Expected: 4, Actual: " + maxProfit(prices3));
	}

	// Time: O(N), Space: O(1)
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i=1; i<prices.length; i++) {
            if(prices[i] > prices[i-1]) {
                maxProfit += prices[i] - prices[i-1];
            }
        }
        
        return maxProfit;
    }
}
