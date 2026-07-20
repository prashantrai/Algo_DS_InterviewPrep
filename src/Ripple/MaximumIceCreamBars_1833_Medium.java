package Ripple;

import java.util.Arrays;

public class MaximumIceCreamBars_1833_Medium {
	public static void main(String[] args) {
        // Problem examples
        test(new int[]{1,3,2,4,1}, 7, 4);
        test(new int[]{10,6,8,7,7,8}, 5, 0);
        test(new int[]{1,6,3,1,2,5}, 20, 6);

        // Edge cases
        test(new int[]{5}, 5, 1);               // single item, exact coins
        test(new int[]{5}, 4, 0);               // single item, cannot afford
        test(new int[]{2,2,2,2}, 3, 1);         // can buy only one
        test(new int[]{2,2,2,2}, 8, 4);         // can buy all
        test(new int[]{1,1,1,1}, 2, 2);         // many cheap duplicates
        test(new int[]{100000}, 100000, 1);     // max cost exact
        test(new int[]{100000, 1, 99999}, 100000, 2); // mixed large values
    }
	private static void test(int[] costs, int coins, int expected) {
        int actual = maxIceCream(costs, coins);
        System.out.println(
            "costs=" + Arrays.toString(costs) +
            ", coins=" + coins +
            ", expected=" + expected +
            ", actual=" + actual +
            (actual == expected ? " ✅" : " ❌")
        );
    }
	
	/* 
    Time: O(n + k)
        n = costs.length
        k = maxCost
    Space: O(k)
    */
    // counting sort
    public static int maxIceCream(int[] costs, int coins) {
        int maxCost = 0;
        for(int cost : costs) {
            maxCost = Math.max(maxCost, cost);
        }

        // +1, because cost is always greater than 0 
        int[] freq = new int[maxCost+1]; 
        for(int cost : costs) {
            freq[cost]++;
        }

        int bars = 0;
        for (int cost = 1; cost <= maxCost; cost++) {
            if (freq[cost] == 0) continue;

            /* What each part means: 
                freq[cost] = how many bars exist with this exact price
                coins / cost = how many such bars you can afford
                
                Math.min(...) = actual number you can buy:
                    you cannot buy more than available
                    you cannot buy more than you can afford
            */
            /*
            Example:: 
                Suppose: cost = 3,  freq[3] = 4, coins = 10
                Then: coins / cost = 10 / 3 = 3
                So: 4 bars are available, but you can only afford 3
                Thus: canBuy = Math.min(4, 3) = 3
                Then update:
                    bars += 3;        // bought 3 more bars
                    coins -= 3 * 3;   // spent 9 coins
                Now:
                bars increases by 3
                coins becomes 1
            */
            /* Why this helps::
                Instead of buying one bar at a time in a loop, you buy a whole 
                group of same-cost bars in one step.
                That makes the counting-sort approach efficient:
                - process by price
                - use frequency
                - batch-buy as many as possible at that price
            */
            int canBuy = Math.min(freq[cost], coins/cost);
            bars += canBuy;
            coins -= canBuy * cost;
        }
        return bars;
    }

    /*
    Time: O(n log n)
    - Arrays.sort(costs) dominates
    - the loop is O(n)
    Space: O(log n) in Java for sorting primitive arrays, due to recursion/stack usage in Arrays.sort
     */
    public int maxIceCream2(int[] costs, int coins) {
        Arrays.sort(costs);
        int pendingCoins = coins;
        int iceCreamCount = 0;
        for(int cost : costs) {
            
            if(pendingCoins < cost) return iceCreamCount;
            pendingCoins -= cost;
            iceCreamCount++;
        }
        
        return iceCreamCount;
    }

}
