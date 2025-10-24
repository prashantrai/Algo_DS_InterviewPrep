package Ripple;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class StockPriceFluctuation_2034_Medium {

	public static void main(String[] args) {
		StockPrice stockPrice = new StockPrice();
		stockPrice.update(1, 10); // Timestamps are [1] with corresponding prices [10].
		stockPrice.update(2, 5);  // Timestamps are [1,2] with corresponding prices [10,5].
		stockPrice.current();     // return 5, the latest timestamp is 2 with the price being 5.
		stockPrice.maximum();     // return 10, the maximum price is 10 at timestamp 1.
		stockPrice.update(1, 3);  // The previous timestamp 1 had the wrong price, so it is updated to 3.
		                          // Timestamps are [1,2] with corresponding prices [3,5].
		stockPrice.maximum();     // return 5, the maximum price is 5 after the correction.
		stockPrice.update(4, 2);  // Timestamps are [1,2,4] with corresponding prices [3,5,2].
		stockPrice.minimum();     // return 2, the minimum price is 2 at timestamp 4.

	}
	
	static class StockPrice {

	    /*
	    Time: O(NlogN)
	        - In the update(), adding the record to the hashmap takes constant time.
	        However, for a heap, each push operation takes O(logN) time. 
	        So for N update calls, it will take O(NlogN) worst-case time.
	        
	        - In the maximum and minimum functions, we pop any outdated records that 
	        are at the top of the heap. In the worst-case scenario, we might pop (N−1) 
	        elements and each pop takes O(logN) time, so it might seem for one 
	        function call the time complexity is NlogN, so for N functions calls it 
	        could be N^2*log*N.

	        However, when we pop a record from the heap, it's gone and won't be popped 
	        again. So overall, if we push N elements into a heap, we cannot pop more 
	        than N elements, taking into account all function calls. Thus, calls to 
	        maximum and minimum will at most require O(NlogN) time.

	    Space complexity: O(N)
	    */

	    private int latestTime;

	    // Store price of each stock at each timestamp.
	    private Map<Integer, Integer> timestampPriceMap; 

	    // Store stock prices in sorted order to get min and max price.
	    private PriorityQueue<int[]> minHeap, maxHeap;

	    public StockPrice() {
	        latestTime = 0;
	        timestampPriceMap = new HashMap<>();
	        minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
	        maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
	    }
	    
	    public void update(int timestamp, int price) {
	        // Update latestTime to latest timestamp.
	        latestTime = Math.max(timestamp, latestTime);

	        // Add latest price for timestamp.
	        timestampPriceMap.put(timestamp, price);

	        minHeap.offer(new int[]{price, timestamp});
	        maxHeap.offer(new int[]{price, timestamp});
	    }
	    
	    public int current() {
	        // Return latest price of the stock.
	        return timestampPriceMap.get(latestTime);
	    }
	    
	    // Returns the maximum price of the stock.
	    public int maximum() {
	        int[] top = maxHeap.peek();

	        // Pop pairs from heap with if the price 
	        // doesn't match with hashmap value of the same timestamp.
	        while (timestampPriceMap.get(top[1]) != top[0] ) {
	            maxHeap.remove();
	            top = maxHeap.peek();
	        }
	        return top[0];
	    }
	    
	    // Returns the minimum price of the stock.
	    public int minimum() {
	        int[] top = minHeap.peek();

	        // Pop pairs from heap with if the price 
	        // doesn't match with hashmap value of the same timestamp.
	        while (timestampPriceMap.get(top[1]) != top[0] ) {
	            minHeap.remove();
	            top = minHeap.peek();
	        }
	        return top[0];
	    }
	}

}
