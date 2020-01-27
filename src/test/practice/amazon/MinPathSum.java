package test.practice.amazon;

import java.util.Arrays;

public class MinPathSum {

	// --https://leetcode.com/problems/minimum-path-sum/
	public static void main(String[] args) {
		int[][] grid = { 	{ 1, 3, 1 }, 
							{ 1, 5, 1 }, 
							{ 4, 2, 1 } };

		int expected = 7;
		int res = minPathSum(grid);
		System.out.println("Expected: " + expected);
		System.out.println("Actual: " + res);
	}

	//--A dp problem
	// --https://www.youtube.com/watch?v=ItjZdu6jEMs
	public static int minPathSum(int[][] grid) {
		if (grid == null || grid.length == 0) {
			return 0;
		}

		int[][] dp = new int[grid.length][grid[0].length];

		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {

				dp[i][j] += grid[i][j];
				if (i > 0 && j > 0) {
					dp[i][j] += Math.min(dp[i - 1][j], dp[i][j - 1]);
				}
				else if (i > 0) { // --if it's in first col i.e. c == 0
					dp[i][j] += dp[i - 1][j];
				}
				else if (j > 0) { // --if it's in first col i.e. r == 0
					dp[i][j] += dp[i][j - 1];
				}
			}
		}
		System.out.println(Arrays.deepToString(dp));
		return dp[dp.length - 1][dp[0].length - 1]; // --return last index of dp array
	}
}
