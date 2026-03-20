package Apple;

public class BestTimeToBuyAndSellStockWithCooldown_309_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* 
    Interview Explanation Script (Very Important)

    A clean way to explain this in interviews:

    - "Since selling introduces a cooldown day, I model the problem with three states per day:

        hold – currently holding stock
        sold – just sold today
        rest – not holding and not in cooldown

    - Each state transitions from previous states:
        hold comes from either continuing to hold or buying from rest
        sold comes from selling a held stock
        rest comes from either continuing rest or cooldown after sell

    Since each state depends only on the previous day, I compress the DP to constant space."
    
    
    "How do you track the cooldown?"
	Answer:
	I model three states: hold, sold, and rest. After selling, 
	the next state becomes rest, and buying is only allowed 
	from rest. Since selling transitions to rest first, it 
	guarantees a one-day cooldown before another buy.
	
	The cooldown is enforced implicitly by the state transitions.
	A buy can only occur from the rest state, and after selling we 
	first transition to sold and then to rest the next day. This 
	guarantees at least one cooldown day before another buy.
    
    */

    // Time: O(N) Space: O(1)
    private static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // hold -> max profit while holding a stock
        int hold = -prices[0];

        // sold -> max profit when we just sold today
        int sold = 0;

        // rest -> max profit when we are doing nothing
        int rest = 0;

        for(int i=1; i<prices.length; i++) {
            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            // either keep holding OR buy today
            hold = Math.max(prevHold, prevRest - prices[i]);
            
            // sell today
            sold = prevHold + prices[i];

            // stay resting OR cooldown finished
            rest = Math.max(prevRest, prevSold);
        }
        // cannot end holding a stock
        return Math.max(rest, sold);
    }


    // Time: O(N) Space: O(1)
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // hold, sold rest are profits, not actions.
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
