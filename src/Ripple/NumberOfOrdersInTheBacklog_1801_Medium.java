package Ripple;

import java.util.PriorityQueue;

public class NumberOfOrdersInTheBacklog_1801_Medium {

	
	public static void main(String[] args) {

	    // Test 1: Example 1 from LeetCode
	    int[][] orders1 = {
	            {10, 5, 0},
	            {15, 2, 1},
	            {25, 1, 1},
	            {30, 4, 0}
	    };
	    System.out.println("Test 1: " + getNumberOfBacklogOrders(orders1)); // Expected: 6

	    // Test 2: Example 2 from LeetCode (large amounts and modulo)
	    int[][] orders2 = {
	            {7, 1000000000, 1},
	            {15, 3, 0},
	            {5, 999999995, 0},
	            {5, 1, 1}
	    };
	    System.out.println("Test 2: " + getNumberOfBacklogOrders(orders2)); // Expected: 999999984

	    // Test 3: Buy and sell order match exactly.
	    int[][] orders3 = {
	            {20, 5, 0},
	            {20, 5, 1}
	    };
	    System.out.println("Test 3: " + getNumberOfBacklogOrders(orders3)); // Expected: 0

	    // Test 4: Buy price is lower than sell price, so no matching happens.
	    int[][] orders4 = {
	            {10, 5, 0},
	            {20, 5, 1}
	    };
	    System.out.println("Test 4: " + getNumberOfBacklogOrders(orders4)); // Expected: 10

	    // Test 5: One buy order matches multiple sell orders.
	    int[][] orders5 = {
	            {5, 2, 1},
	            {6, 3, 1},
	            {10, 6, 0}
	    };
	    System.out.println("Test 5: " + getNumberOfBacklogOrders(orders5)); // Expected: 1

	    // Test 6: One sell order matches multiple buy orders.
	    int[][] orders6 = {
	            {15, 2, 0},
	            {14, 4, 0},
	            {13, 5, 1}
	    };
	    System.out.println("Test 6: " + getNumberOfBacklogOrders(orders6)); // Expected: 1

	    // Test 7: Orders match at exactly the same price.
	    int[][] orders7 = {
	            {10, 3, 0},
	            {10, 2, 1}
	    };
	    System.out.println("Test 7: " + getNumberOfBacklogOrders(orders7)); // Expected: 1

	    // Test 8: Incoming buy order is only partially matched.
	    int[][] orders8 = {
	            {8, 3, 1},
	            {10, 5, 0}
	    };
	    System.out.println("Test 8: " + getNumberOfBacklogOrders(orders8)); // Expected: 2

	    // Test 9: Incoming sell order is only partially matched.
	    int[][] orders9 = {
	            {10, 5, 0},
	            {9, 8, 1}
	    };
	    System.out.println("Test 9: " + getNumberOfBacklogOrders(orders9)); // Expected: 3

	    // Test 10: Multiple orders at the same price.
	    int[][] orders10 = {
	            {10, 2, 0},
	            {10, 3, 0},
	            {10, 4, 1}
	    };
	    System.out.println("Test 10: " + getNumberOfBacklogOrders(orders10)); // Expected: 1

	    // Test 11: Multiple buys and sells with different prices.
	    int[][] orders11 = {
	            {20, 5, 0},
	            {18, 4, 0},
	            {19, 3, 1},
	            {17, 4, 1},
	            {21, 2, 1}
	    };
	    System.out.println("Test 11: " + getNumberOfBacklogOrders(orders11)); // Expected: 4

	    // Test 12: All orders are buys, so every order remains in backlog.
	    int[][] orders12 = {
	            {5, 2, 0},
	            {10, 3, 0},
	            {8, 4, 0}
	    };
	    System.out.println("Test 12: " + getNumberOfBacklogOrders(orders12)); // Expected: 9

	    // Test 13: All orders are sells, so every order remains in backlog.
	    int[][] orders13 = {
	            {5, 2, 1},
	            {10, 3, 1},
	            {8, 4, 1}
	    };
	    System.out.println("Test 13: " + getNumberOfBacklogOrders(orders13)); // Expected: 9

	    // Test 14: Single buy order only.
	    int[][] orders14 = {
	            {100, 50, 0}
	    };
	    System.out.println("Test 14: " + getNumberOfBacklogOrders(orders14)); // Expected: 50

	    // Test 15: Single sell order only.
	    int[][] orders15 = {
	            {100, 50, 1}
	    };
	    System.out.println("Test 15: " + getNumberOfBacklogOrders(orders15)); // Expected: 50

	    // Test 16: Buy order consumes all sell orders and still has remaining quantity.
	    int[][] orders16 = {
	            {5, 2, 1},
	            {6, 3, 1},
	            {10, 10, 0}
	    };
	    System.out.println("Test 16: " + getNumberOfBacklogOrders(orders16)); // Expected: 5

	    // Test 17: Sell order consumes all buy orders and still has remaining quantity.
	    int[][] orders17 = {
	            {10, 2, 0},
	            {9, 3, 0},
	            {8, 10, 1}
	    };
	    System.out.println("Test 17: " + getNumberOfBacklogOrders(orders17)); // Expected: 5

	    // Test 18: No orders.
	    int[][] orders18 = {};
	    System.out.println("Test 18: " + getNumberOfBacklogOrders(orders18)); // Expected: 0
	}
	
	
	/* Step-by-Step Algorithm (Interview Explanation)
		1. Create a max heap for buy orders.
		2. Create a min heap for sell orders.
		3. Process every incoming order one by one.
		4. If it is a buy order:
			- Keep matching with the cheapest sell order.
			- Reduce quantities.
			- Remove fully consumed sell orders.
			- Push remaining buy quantity into buy heap.
		5. If it is a sell order:
			- Keep matching with the highest buy order.
			- Reduce quantities.
			- Remove fully consumed buy orders.
			- Push remaining sell quantity into sell heap.
		6. After processing all orders, sum the remaining quantities in both heaps.
		7. Return answer modulo 1,000,000,007.
	 * */
	/* Time: Each order is inserted into a heap once and removed once.
		Heap operations cost: O(log n)
		Overall: O(n log n)
		
	  Space: O(n), Both heaps together store at most all unmatched orders.
	 * */
	
	private static final int MOD = 1_000_000_007;

    private static int getNumberOfBacklogOrders(int[][] orders) {
    	
    	// Max Heap: Highest buy price first
    	PriorityQueue<int[]> buyHeap = new PriorityQueue<>(
    			(a,b) -> Integer.compare(b[0], a[0]));
    	
    	
    	// Min Heap: Lowest sell price first
    	PriorityQueue<int[]> sellHeap = new PriorityQueue<>(
    			(a,b) -> Integer.compare(a[0], b[0]));

    	
    	// Process every incoming order one by one.
    	// orders[i] = [pricei, amounti, orderTypei]
    	for(int[] order : orders) {
    		int price = order[0];
    		int amt = order[1];  // volumn/number of stock
    		int orderType = order[2]; // 0 for buy or 1 for sell
    		
    		// ---------------- BUY ORDER ----------------
    		if(orderType == 0) {
    			while(amt > 0 && 
    					!sellHeap.isEmpty() && 
    					sellHeap.peek()[0] <= price) {
    				
    				// sell = [price, amt]
    				int[] sell = sellHeap.peek();
    				int matched = Math.min(amt, sell[1]);
    				amt -= matched;
    				sell[1] -= matched;
    				
    				// Remove completely fulfilled sell order
    				if(sell[1] == 0) 
    					sellHeap.poll();
    				
    			}
    			// Remaining buy order goes into backlog
    			if(amt > 0) {
    				buyHeap.offer(new int[] {price, amt});
    			}
    		} 
    		// ---------------- SELL ORDER ----------------
    		else {
    			while(amt > 0 && 
    					!buyHeap.isEmpty() && 
    					buyHeap.peek()[0] >= price) {
    				
    				int[] buy = buyHeap.peek();
    				// Check if order amt matched
    				int matched = Math.min(buy[1], amt);
    				
    				amt -= matched;
    				buy[1] -= matched;
    				
    				// Remove completely fulfilled buy order
    				if(buy[1] == 0) 
    					buyHeap.poll();
    			}
    			
    			// Remaining sell order goes into backlog
    			if(amt > 0) {
    				sellHeap.offer(new int[] {price, amt});
    			}
    			
    		}
    		
    	}
    	
    	long backlog = 0;
		while(!buyHeap.isEmpty()) {
			backlog = (backlog + buyHeap.poll()[1]) % MOD;
		}
		
		while(!sellHeap.isEmpty()) {
			backlog = (backlog + sellHeap.poll()[1]) % MOD;
		}
		
		return (int) backlog;
    	
    }

}


/*
Rule to remember
- You always search the opposite heap, because you're trying to match with 
  the opposite order type.

- You always store any unmatched remainder in the same type's heap, because
  the order's type never changes. A buy order remains a buy order, and a 
  sell order remains a sell order, even after partial matching.
 
 
 
 
 Incoming BUY
      |
      v
Search sellHeap
      |
      +----------------+
      |                |
Fully matched?         No
      |                |
Done          Remaining BUY
                     |
                     v
                 add to buyHeap
                 
                 
 
 Incoming SELL
      |
      v
Search buyHeap
      |
      +----------------+
      |                |
Fully matched?         No
      |                |
Done          Remaining SELL
                     |
                     v
                 add to sellHeap
 
 
 * */
