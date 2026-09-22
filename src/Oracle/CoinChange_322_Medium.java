package Oracle;

import java.util.Arrays;

public class CoinChange_322_Medium {

	public static void main(String[] args) {

		int[] coins1 = { 1, 2, 5 };
		int amount1 = 11;

		System.out.println("Test 1");
		System.out.println("Expected: 3");
		System.out.println("Actual:   " + coinChange(coins1, amount1));

		System.out.println();

		int[] coins2 = { 2 };
		int amount2 = 3;

		System.out.println("Test 2");
		System.out.println("Expected: -1");
		System.out.println("Actual:   " + coinChange(coins2, amount2));

		System.out.println();

		int[] coins3 = { 1 };
		int amount3 = 0;

		System.out.println("Test 3");
		System.out.println("Expected: 0");
		System.out.println("Actual:   " + coinChange(coins3, amount3));

		System.out.println();

		int[] coins4 = { 2, 5, 10, 1 };
		int amount4 = 27;

		System.out.println("Test 4");
		System.out.println("Expected: 4");
		System.out.println("Actual:   " + coinChange(coins4, amount4));
	}

	/*
	 * Interview Script
	 * 
	 * We’ll use one-dimensional dynamic programming. We’ll define dp[x] as the
	 * minimum number of coins needed to make exactly amount x.
	 * 
	 * The base case is dp[0] = 0, because no coins are needed to make zero.
	 * 
	 * Initially, I’ll mark every other amount as unreachable using a sentinel value
	 * larger than any possible valid answer.
	 * 
	 * Then for every amount from 1 through the target, I’ll try each coin. If the
	 * coin is not larger than the current amount and the remaining amount current -
	 * coin is reachable, I can form the current amount by taking that previous
	 * solution and adding one coin.
	 * 
	 * So the transition is dp[current] = min(dp[current], dp[current - coin] + 1).
	 * 
	 * At the end, if the target is still unreachable, I return -1; otherwise I
	 * return dp[amount].”
	 * 
	 * 
	 * Why this is correct
	 * 
	 * “For each amount, I consider every possible denomination that could be the
	 * last coin. Since dp[current - coin] already contains the minimum solution for
	 * the remaining amount, taking the minimum across all possible last coins gives
	 * the minimum solution for the current amount.”
	 * 
	 */
	
	/* Complexity Analysis
	 *
	 * Let:
	 * A = target amount
	 * C = number of coin denominations
	 *
	 * Time Complexity: O(A * C)
	 * - Compute the minimum coins needed for every amount from 1 to A.
	 * - For each amount, iterate through all C coin denominations.
	 *
	 * Space Complexity: O(A)
	 * - DP array stores one value for every amount from 0 to A.
	 */

	/*
	 * Algorithm
	 *
	 * 1. Create a DP array of size (amount + 1).
	 * 2. Initialize every entry to (amount + 1), representing an unreachable state.
	 * 3. Set dp[0] = 0 since zero coins are needed to make amount 0.
	 * 4. For each current amount from 1 to amount:
	 *      - Iterate through every coin.
	 *      - Skip the coin if it is larger than the current amount.
	 *      - Otherwise, try using this coin:
	 *            dp[current] = Math.min(dp[current],
	 *                                   dp[current - coin] + 1);
	 * 5. If dp[amount] is still (amount + 1), no solution exists; return -1.
	 * 6. Otherwise, return dp[amount] as the minimum number of coins.
	 */

	// DP - Bottom-up
	/**
	 * Interview script: "dp[x] stores the minimum number of coins needed to make
	 * amount x.
	 *
	 * For every amount, I try every coin as the last coin. If current - coin is
	 * reachable, I extend that solution by one coin and keep the minimum."
	 *
	 * Time: O(amount * coins.length) Space: O(amount)
	 */
	public static int coinChange(int[] coins, int amount) {

		// amount + 1 is safely larger than any valid answer.
		// In the worst case, using coin 1 requires 'amount' coins.
		int unreachable = amount + 1;

		int[] dp = new int[amount + 1];
		Arrays.fill(dp, unreachable);

		// Base case: zero coins are needed to make amount 0.
		dp[0] = 0;

		// Build answers from smaller amounts to larger amounts.
		for (int current = 1; current <= amount; current++) {

			// Try every coin as the last coin used.
			for (int coin : coins) {

				if (coin > current) {
					continue;
				}

				int remaining = current - coin;

				// Only extend a solution if the remaining amount is reachable.
				/*
				 * Min coins for current amount = Min coins for remaining amount 
				 * 									+ The current coin we just picked
				 * 
				 * or mathematically: dp[current] = dp[current - coin] + 1
				 * 
				 * Why do we add +1?
				 * 
				 * dp[remaining] already represents the minimum number of coins needed to make
				 * the remaining amount. To make the current amount, I use one additional
				 * coin—the coin I'm currently considering. So the total becomes dp[remaining] +
				 * 1. I evaluate this for every possible last coin and keep the minimum.
				 */
				if (dp[remaining] != unreachable) {
					dp[current] = Math.min(dp[current], dp[remaining] + 1);
				}
			}
		}

		return dp[amount] == unreachable ? -1 : dp[amount];
	}

}
