package Coinbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class BuyingAndSellingTextBooks {

	public static void main(String[] args) {
		
		BuyingAndSellingTextBooks obj = new BuyingAndSellingTextBooks();
        int[] buy = {100,100,90,96,98};
        int[] sell = {109,70,160,120,90};
        List<Integer> p = obj.bestMatch(buy,sell,200, 90);
       
        System.out.println("OptimumBuy:"+p.get(0).toString());
        System.out.println("OptimumSell:"+p.get(1).toString());

	}
	
	/* Algorithm: https://leetcode.com/discuss/interview-question/1385903/Coinbase-Text-books-buying-and-selling-offers-problem
	 
	 * Use two heaps. Put all the buy orders in a max heap and put all the sell orders in a min heap.

		Let's say we get a new BUY order for $113. We want to find the best selling price ($109), 
		which is right at the top of the seller min heap. Take that value out and match it with the user. 
		We are done for this case.
		
		Now let's say we got a BUY order for $90, the value at the top of the seller min heap is $110 
		(we already polled the $109 from the seller heap), so no one will sell at this price. 
		So we cannot match it at this point. IMPORTANT: We are asked to save this value if it was 
		unmatched. So put this $90 BUY order in the buy orders max heap.
		
		Do the same logic for SELL orders (poll max buying price from buyers max heap, 
		save the value in the seller min heap if there was no match).
	 * 
	 * 
	 * Time complexity: O(n logn) --> because we put everything in the heaps which are O(log n) per offer/poll operations.
	 * Space complexity: O(n) --> we're putting the entire input lists in the heaps
	 * 
	 * FOLLOW-UP:  question of <price, expiry time> to the buy/sell order. Acc. to me, I would maintain heaps, but continue to disregard first element until I get non-expired offer.
	 * */
	
	public List<Integer> bestMatch(int[] buyOffers, int[] sellOffers, int buyPrice, int sellPrice) {
		PriorityQueue<Integer> buyPQ = new PriorityQueue<>((a,b) -> (b - a)); //max heap
		PriorityQueue<Integer> sellPQ = new PriorityQueue<>((a,b) -> (a - b)); //min heap
		
		/* you can use a simple for loop like below as well
			
			for(int b: buyOffers) {
            	buy.add(b);
        	}
		*/
		//List<Integer> list = Arrays.stream(ints).boxed().toList(); //java 16 and above
		
		buyPQ.addAll(Arrays.stream(buyOffers)	// int stream
							.boxed()			// Stream<Integer>
							.collect(Collectors.toList()));
		
		sellPQ.addAll(Arrays.stream(sellOffers).boxed().collect(Collectors.toList()));
		
		int optimumSell = 0;
		int optimumBuy = 0;
		
		/* If we cannot match the price i.e. IMPORTANT: We are asked to save this value if it was 
		 * unmatched. So put this BUY order in the buy orders max heap.
		 */
		if(buyPrice < sellPQ.peek()) {
			buyPQ.add(buyPrice);
		
		} else {
			optimumBuy = buyPQ.poll();
		} 
		
		// if sellPrice doesn't match with exiting BUY orders
		if(sellPrice > buyPQ.peek()) {
			sellPQ.add(sellPrice);
		} else {
			optimumSell = sellPQ.poll();
		}
		
		List<Integer> res = new ArrayList<>();
        res.add(optimumBuy);
        res.add(optimumSell);
        return res;
		
	}
	
	
	

}

/* https://leetcode.com/discuss/interview-question/1385903/Coinbase-Text-books-buying-and-selling-offers-problem
 
Q.	You operate a market place for buying & selling used textbooks For a giventext book eg“TheoryofCryptography”
there are people who want to buy this textbook and people who want to sell

OfferstoBUY: $100$100$99$99$97$90
OfferstoSELL: $109$110$110$114$115$119

A match occurs when two people agree on a price. Some new offers do not match 
these offers should be added to the active set of offers

For Example: Tim offers to SELL at $150 This will not match but No one is willing to 
buy at that price so we save the offer

OfferstoSELL:: $109$110$110$114$115$119$150

When matching we want to give the customer the “best price”

Example matches: If Jane offers to BUY at $120

she will match and buy a book for $109 (the lowest offer)

FOLLOW-UP:  question of <price, expiry time> to the buy/sell order. Acc. to me, 
I would maintain heaps, but continue to disregard first element until I get non-expired offer.
*/