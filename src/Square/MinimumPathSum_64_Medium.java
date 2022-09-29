package Square;

public class MinimumPathSum_64_Medium {

	public static void main(String[] args) {
		int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
		System.out.println("Expected: 7, Actual: " + minPathSum(grid));
		System.out.println("Expected: 7, Actual: " + minPathSum_withConstantSpace(grid));

	}
	
	/*
    Time: O(m*n). We traverse the entire matrix once.
    Space: O(m*n). Another matrix of the same size is used.
    */
    
    public static int minPathSum(int[][] grid) {
        
        if(grid == null || grid.length == 0) { 
            return 0; 
        }
        
        int m = grid.length, n = grid[0].length;
        
        int[][] dp = new int[m][n];
        
        for(int r=0; r<m; r++) {
            for(int c=0; c<n; c++) {
                
                dp[r][c] += grid[r][c];
                
                // we are not in first row and column
                if(r > 0 && c > 0) {
                    // we can only come to current cell from top or from left
                     dp[r][c] += Math.min(dp[r-1][c], dp[r][c-1]);
                        
                } else if(r > 0) { // if it's in first col i.e. c == 0
                    dp[r][c] += dp[r-1][c];
                } else if(c > 0) { // if it's in first row i.e. r == 0
                    dp[r][c] += dp[r][c-1];
                }
            }
        }
        
        return dp[m-1][n-1];
        
    }

    // Space: O(1)
    
    public static int minPathSum_withConstantSpace(int[][] grid) {
	    int m = grid.length, n = grid[0].length;
	    for(int i = 0; i < m; i++){
	    	for(int j = 0; j < n; j++){
	    	if(i == 0 && j != 0) grid[i][j] += grid[i][j-1];
	    	if(i != 0 && j == 0) grid[i][j] += grid[i-1][j];
	    	if (i != 0 && j != 0) grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
	    	}
	    }
	    return grid[m-1][n-1];
    }
}
