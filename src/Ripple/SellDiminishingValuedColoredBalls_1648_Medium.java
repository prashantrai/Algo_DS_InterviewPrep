package Ripple;

import java.util.Arrays;

public class SellDiminishingValuedColoredBalls_1648_Medium {

	
	/* Step-by-Step Algorithm (Interview Explanation)
		1. Sort inventory in descending order.
		2. Initialize:
			- current index
			- number of colors at highest level (colors)
		3. Compare current value with next distinct value.
		4. Calculate how many balls are required to reduce all highest colors to the next level.
			`ballsNeeded = (currentValue - nextValue) * colors`
		5. If orders ≥ ballsNeeded
			- sell entire levels
			- add arithmetic sum
			- decrease orders
		6. Otherwise
			- calculate
			`fullLevels = orders / colors`
			`remaining = orders % colors`
		7. Sell only those levels.
		8. Return answer modulo `1_000_000_007`.
	 * */
	
	public static void main(String[] args) {
        // Example 1
        int[] inventory1 = {2, 5};
        int orders1 = 4;
        System.out.println("Example 1");
        System.out.println("Inventory: " + Arrays.toString(inventory1));
        System.out.println("Orders: " + orders1);
        System.out.println("Profit: " + maxProfit(inventory1, orders1));
        System.out.println();

        // Example 2
        int[] inventory2 = {3, 5};
        int orders2 = 6;
        System.out.println("Example 2");
        System.out.println("Inventory: " + Arrays.toString(inventory2));
        System.out.println("Orders: " + orders2);
        System.out.println("Profit: " + maxProfit(inventory2, orders2));
        System.out.println();

        // Example 3
        int[] inventory3 = {7, 7, 7, 7};
        int orders3 = 6;
        System.out.println("Example 3");
        System.out.println("Inventory: " + Arrays.toString(inventory3));
        System.out.println("Orders: " + orders3);
        System.out.println("Profit: " + maxProfit(inventory3, orders3));
        System.out.println();

        // Example 4
        int[] inventory4 = {10, 10, 10, 7, 5};
        int orders4 = 15;
        System.out.println("Example 4");
        System.out.println("Inventory: " + Arrays.toString(inventory4));
        System.out.println("Orders: " + orders4);
        System.out.println("Profit: " + maxProfit(inventory4, orders4));
        System.out.println();

        // Example 5
        int[] inventory5 = {10, 10, 10};
        int orders5 = 5;
        System.out.println("Example 5");
        System.out.println("Inventory: " + Arrays.toString(inventory5));
        System.out.println("Orders: " + orders5);
        System.out.println("Profit: " + maxProfit(inventory5, orders5));
    }
	
	/* Time:  Sorting: O(n log n), Single traversal: O(n)
			Overall:O(n log n)
		Space: O(1) extra space.
	 * */
	
	private static final int MOD = 1_000_000_007;
	
	
	public static int maxProfit(int[] inventory, int orders) {

		// Sort in ascending order.
		Arrays.sort(inventory);
		
		long profit = 0;
		int n = inventory.length;
		
		// Number of colors currently at the highest level.
		int colors = 1;
		
		// Start from the largest inventory.
		for(int i = n-1; i>=0 && orders > 0; i--, colors++) {
			int current = inventory[i];
			
			int next = (i == 0) ? 0 : inventory[i-1]; // next element in the inventory
			
			// `ballsNeeded` means "How many balls must I sell to bring all 
			// highest colors down to the next level?"
			// Every highest color sells the same sequence of values, so 
			// compute the profit for one color once using sum(), then multiply 
			// by the number of colors.
			long ballsNeeded = (long) (current - next) * colors;
			
			if(orders >= ballsNeeded) {
				// Sell all levels down to 'next'.
				profit += (long) colors * sum(next + 1, current);
				orders -= ballsNeeded;
				
			} else { 
				// We can't remove all the levels needed to reach the next distinct inventory value.
				// Cannot reach next level completely.
				
				/* fullLevels = orders / colors => 6 / 4 = 1
				Meaning - We can completely remove 1 entire level from all 4 colors.
				 * */
				// Number of complete value levels we can sell from ALL highest colors.
				// Example: inventory = [7, 7, 7, 7], colors = 4, orders = 6
				// We can remove one complete level: 7 7 7 7
				// before running out of orders.
				int fullLevels = orders / colors;
				
				/* remaining=6%4=2
				 Meaning - After removing one complete level, we still have 
				 2 extra balls to sell.
				 * */
				// Extra balls left after removing complete levels; these are sold at the current stop value
				// Orders left after removing complete levels.
				// Example: Orders = 6
				// Sold first level = 4 balls
				// Remaining = 2 balls.
				int remaining = orders % colors;
				
				// Inventory value after removing all complete levels.
				// Example: Started at 7
				// Removed one complete level
				// New inventory becomes 6.
				int stop = current - fullLevels;
				
				// Profit from all COMPLETE levels.
				// Example:
				// Sold:
				// 7
				// 7
				// 7
				// 7
				// sum(stop + 1, current) gives profit for ONE color.
				// Multiply by number of colors.
				// Sell complete levels.
				profit += (long) colors * sum(stop + 1, current);
				
				// Sell remaining balls.
				//
				// After removing complete levels, every highest color
				// now has value 'stop'.
				// Example: Inventories: 6 6 6 6
				// Remaining orders = 2
				// Sell two balls worth 6.
				// Sell remaining balls.
				profit += (long) remaining * stop;
				
				orders = 0;
			}
			profit %= MOD;
			
		}
		return (int) (profit % MOD);
	}
	
	/*
	 The arithmetic progression is never the inventory array.
	❌ Inventory: 10, 10, 10, 7, 5 → not an arithmetic progression.
	✅ Balls sold from one color: 10, 9, 8 → arithmetic progression with common difference -1.
	
	PS: The arithmetic progression is not formed by the inventory values (10, 10, 10, 7, 5).
		It's formed by the values of the balls sold from a single color.
	 * */
	// Sum from low to high (inclusive).
	// This is the profit earned by selling one color from value high down to value low.
	private static long sum(long low, long high) {
		long count = high - low + 1;
		
		// arithmetic progression formula
		return (low + high) * count/2;
	}
	

}



/*
# The mental picture that makes this click - for else block

Think of the inventory as floors in a building.

```
Level 7   █ █ █ █
Level 6   █ █ █ █
Level 5   █ █ █ █
```

The `if` branch means:

> "I have enough budget to demolish **all** floors down to level 5."

The `else` branch means:

> "I can't demolish everything."

But that doesn't mean you demolish **nothing**.

Instead:

1. Demolish as many **entire floors** as your budget allows.
2. Then partially demolish the next floor.

That's exactly why the `else` block first adds the profit from **complete levels** and then adds the profit from the **remaining balls**.

So the key misunderstanding is this:

* ❌ **Incorrect interpretation:** "`else` means I can't remove a complete level."
* ✅ **Correct interpretation:** "`else` means I can't remove **all the complete levels needed to reach `next`**, but I may still be able to remove **some** complete levels."

That distinction is what makes the algorithm both correct and optimal.
 
 * */
