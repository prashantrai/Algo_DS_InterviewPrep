package Geico;

public class BestTimeToBuyAndSellStockWithCooldown_309_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time: O(N) Space: O(1)
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int hold = Integer.MIN_VALUE; // max profit when holding a stock
        int sold = 0;                 // max profit when just sold today
        int rest = 0;                 // max profit when in rest (no stock)

        for(int price : prices) {

	        int prevHold = hold;
	        int prevSold = sold;
	        int prevRest = rest;
	
	        // Track/calculate each state (hold, sold, rest) for everyday price
	
	        // Sell today: must have been holding yesterday
	        // best profit if you end the day having just sold today
	        sold = prevHold + price;
	
	        // Hold today: either keep holding or buy today from rest
	        // best profit if you end the day holding a stock
	        // prevRest here is the amount we have in wallet as we didn't spend
	        hold = Math.max(prevHold, prevRest - price);
	        
	        // Rest today: either keep resting or cooldown from a sale
	        // best profit if you end the day with no stock and not in cooldown (free to buy)
	        rest = Math.max(prevRest, prevSold);

        }

        // Best result: not holding stock at the end
        return Math.max(sold, rest);
    }

}
