package microsoft;

public class MinimumDeletionsToMakeStringBalanced_1653_Medium {

	public static void main(String[] args) {

		String s = "aababbab";
		System.out.println("Expected: 2, Actual: " + minimumDeletions_1(s));
		System.out.println("Expected: 2, Actual: " + minimumDeletions_2(s));
		
	}

	//https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/discuss/943968/JAVA-or-DP-or-ACCEPTED-or-EXPLANATION
	public static int minimumDeletions_1(String s) {
		// ideal case : bbbbbbbbb
		int[] dp = new int[s.length() + 1];
		int idx = 1;
		int bCount = 0;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'a') {
				dp[idx] = Math.min(dp[idx - 1] + 1, bCount);
			} else {
				dp[idx] = dp[idx - 1];
				bCount++;
			}

			idx++;
		}
		return dp[s.length()];

	}

	// See Comment:  https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/discuss/943968/JAVA-or-DP-or-ACCEPTED-or-EXPLANATION
	public static int minimumDeletions_2(String s) {
		int bCount = 0, minMoves = 0;
		for (char c : s.toCharArray()) {
			if (c == 'a') {
				minMoves = Math.min(minMoves + 1, bCount);
			} else {
				bCount++;
			}
		}
		return minMoves;
	}

}


///////////////////
class Solution {
    public int solution(String S, int[] C) {
        return minCost(S, C);
    }
    //https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/
    private int minCost(String s, int[] cost) {
        int res = 0, max_cost = 0, sum_cost = 0, n = s.length();
        for (int i = 0; i < n; ++i) {
            if (i > 0 && s.charAt(i) != s.charAt(i - 1)) {
                res += sum_cost - max_cost;
                sum_cost = max_cost = 0;
            }
            sum_cost += cost[i];
            max_cost = Math.max(max_cost, cost[i]);
        }
        res += sum_cost - max_cost;
        return res;
    }
}
