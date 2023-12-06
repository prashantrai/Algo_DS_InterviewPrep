package Snowflake;

public class PaintingTheWalls_2742_Hard {

	public static void main(String[] args) {
		int[] cost = {1,2,3,2};
		int[] time = {1,2,3,2};
		
		int res = paintWalls(cost, time);
		System.out.println("Expected: 3, Actual: "+ res);
	}
	
	/*
    Time and Space: O(n^2)
    */
    
    private static int[][] memo;
    private static int n;
    
    public static int paintWalls(int[] cost, int[] time) {
        n = cost.length;
        memo = new int[n][n+1];
        return helper(0, n, cost, time);
    }
    
    public static int helper(int i, int remain, int[] cost, int[] time) {
        if(remain <=0) 
            return 0;
        
        if(i == n) 
            return (int) 1e9; // or any larger number as solution not possible
        
        if(memo[i][remain] != 0) {
            return memo[i][remain];
        }
        
        int paint = cost[i] + helper(i+1, remain - 1 - time[i], cost, time);
        int dontPaint = helper(i+1, remain, cost, time);
        memo[i][remain] = Math.min(paint, dontPaint);
        return memo[i][remain];
    }
}
