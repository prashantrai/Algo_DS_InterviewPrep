package leetcode;

public class DiceRollsWithTargetSum_1155_Medium {

	public static void main(String[] args) {
		/*
		 * Input: d = 1, f = 6, target = 3 Output: 1
		 */
		int d = 1, f = 6, target = 3;
		int res = numRollsToTarget(d,f,target);
		System.out.println("Expected: 1, Actual: "+res);

		d = 2;
		f = 6;
		target = 3;
		res = numRollsToTarget(d, f, target);
		System.out.println("Expected: 2, Actual: " + res);
	}

	// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
	
	/*
	 * Complexity Analysis Runtime: O(d * f * target). Memory: O(d * target) for the
	 * memoization
	 */

	/*
	 * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/
	 * 355940/C%2B%2B-Coin-Change-2
	 * 
	 * Refer below link for approach and explaination:
	 * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/356057/Java-O(d-*-f-*-target)-dp-straightforward-and-fast
	 * 
	 */

	private static final int MOD = 1000_000_000 + 7; // or (int)Math.pow(10, 9) + 7;

	public static int numRollsToTarget(int d, int f, int target) {
		int[][] dp = new int[d + 1][target + 1];

		dp[0][0] = 1;
		// how many possibility can i dices sum up to j;
		for (int i = 1; i <= d; i++) {
			for (int j = 1; j <= target; j++) {

				// If j is larger than largest possible sum of i dices, there is no possible
				// ways.
				if (j > f * i)
					break;
				for (int k = 1; k <= f && k <= j; k++) {
					dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
				}
			}
		}
		return dp[d][target];
	}

}
