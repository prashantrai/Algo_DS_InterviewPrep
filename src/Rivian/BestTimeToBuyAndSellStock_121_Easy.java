package Rivian;

public class BestTimeToBuyAndSellStock_121_Easy {

	public static void main(String[] args) {
		int[] prices = {7,1,5,3,6,4};
		System.out.println("Expected: 5, Actual: " + maxProfit(prices));
		
		int[] prices2 = {7,6,4,3,1};
		System.out.println("Expected: 0, Actual: " + maxProfit(prices2));
	}
	
	// Time: O(N), Space: O(1)
    public static int maxProfit(int[] prices) {
        int maxSoFar = 0;
        int maxCur = 0;
        
        for(int i=1; i<prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        
        return maxSoFar;
    }
    
    // Time: O(N), Space: O(1)
    public static int maxProfit2(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for(int i=0; i<prices.length; i++) {
            if(minPrice > prices[i]) {
                minPrice = prices[i];
            } else if(prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        
        return maxProfit;
    }

}
