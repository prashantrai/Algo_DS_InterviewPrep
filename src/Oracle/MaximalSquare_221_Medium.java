package Oracle;

public class MaximalSquare_221_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
	
	//With  1-d dp array
	// Time: O(rows * cols), Every cell is processed once.

	// Space: O(cols), We only need the previous row of DP values, 
	// so we can optimize the normal O(rows * cols) DP table to one array.
	
	public int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // dp[c] represents the largest square ending at the
        // current row and column c - 1.
        int[] dp = new int[cols + 1];

        int maxSide = 0;

        for (int r = 1; r <= rows; r++) {
            // Value from previous row, previous column.
            int topLeft = 0;

            for (int c = 1; c <= cols; c++) {
                // Save previous row's dp[c] before overwriting it.
                int top = dp[c];

                if (matrix[r - 1][c - 1] == '1') {
                    dp[c] = 1 + Math.min(
                            Math.min(dp[c], dp[c - 1]),
                            topLeft
                    );

                    maxSide = Math.max(maxSide, dp[c]);
                } else {
                    dp[c] = 0;
                }

                // For the next column, old dp[c] becomes top-left.
                topLeft = top;
            }
        }

        return maxSide * maxSide;
    }
	
	
	// 2nd Approach with 2d dp array
	
	//Time: O(m × n) — we visit each cell exactly once and do O(1) work per cell.

	//Space: O(m × n) for the DP table. This can be optimized to O(n) using a 
	// rolling 1D array since each row only depends on the previous row, but I'll 
	// go with the full 2D table first for clarity — it's safer to implement correctly 
	// under interview time pressure, and I can mention the space optimization as a follow-up.
	public static int maximalSquare_2d_DP_Arr(char[][] m) {
        
        if(m.length == 0 || m[0].length ==0) 
            return 0;
        
        int result = 0;        
        int[][] cache = new int[m.length + 1][m[0].length + 1];
        
        for(int r=1; r<=m.length; r++) {
            for(int c=1; c<=m[0].length; c++) {
                if(m[r-1][c-1] == '1') {
                    int minValue = Math.min (cache[r][c-1], Math.min (cache[r-1][c], cache[r-1][c-1])); 
                    
                    cache[r][c] = minValue + 1;
                    
                    result = Math.max(result,cache[r][c]);
                }
            }
            
        }
        
        return result * result;
    }
}
