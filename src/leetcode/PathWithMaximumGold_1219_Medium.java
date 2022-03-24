package leetcode;

public class PathWithMaximumGold_1219_Medium {

	public static void main(String[] args) {

	}
	
	/* For time and space look at:  
	 * https://leetcode.com/problems/path-with-maximum-gold/discuss/398282/JavaPython-3-DFS-and-BFS-w-comment-brief-explanation-and-analysis.
	 * 
	 * 
	 * Each of the k gold cells can at most have 4 neighbors. Therefore,
	 * 
	 * Time: O(k * 4 ^ k + m * n - k), 
	 * Space: O(m * n), 
	 * 
	 * where k = number of gold cells, m = grid.length, n = grid[0].length.
	 * 
	 * When exploring a gold cell, time complexity is O(4^k), 
	 * hence the time for traversal of k gold cell is O(k * 4 ^ k).
	 * Together with those non-gold cell, O(M * N - k), we have O(M * N - k + k * 4 ^ k).
	 */
	
	// with only one max variable
	public static int getMaximumGold(int[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        
        int maxGold = 0;
        
        for(int r=0; r<grid.length; r++) {
            for(int c=0; c<grid[0].length; c++) {
                int currMaxGold = helper(grid, r, c);
                maxGold = Math.max(maxGold, currMaxGold);
            }
        }
        return maxGold;
    }
    
    private static int helper(int[][] grid, int r, int c) {
        if(r >= grid.length || r < 0 || c >= grid[0].length || c < 0 || grid[r][c] == 0) {
            return 0;
        }
        
        int currVal = grid[r][c];
        
        grid[r][c] = 0; //visited
        
        int left = helper(grid, r, c-1);
        int right = helper(grid, r, c+1);
        int up = helper(grid, r-1, c);
        int down = helper(grid, r+1, c);
        
        grid[r][c] = currVal; //resetting the visited value for next iteration
        
        return currVal + Math.max(left, Math.max(right, Math.max(up, down)));
    }

}
