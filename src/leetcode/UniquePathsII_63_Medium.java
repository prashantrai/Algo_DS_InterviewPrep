package leetcode;

public class UniquePathsII_63_Medium {

	public static void main(String[] args) {

		int[][] grid = {
		                {0,0,0},
		                {0,1,0},
		                {0,0,0}
						};
		
		int res = uniquePathsWithObstacles(grid);
		
		System.out.println("Expected: 2, Actual: "+res);
	}
	
	
	/* Time: O(M*N)
	 * Space: O(1)
	 * 
	 * Lyft interview
	 * 
	 * https://leetcode.com/problems/unique-paths-ii/
	 * https://leetcode.com/problems/unique-paths-ii/submissions/
	 * https://leetcode.com/problems/unique-paths-ii/discuss/523134/Java-DP-beats-100-speed-and-100-memory
	 * */
	public static int uniquePathsWithObstacles(int[][] grid) {
		
		int row_len = grid.length;
		int col_len = grid[0].length;
		
		for(int r = row_len-1; r >= 0; r--) {
			for(int c = col_len-1; c>=0; c--) {
				
				if(grid[r][c] == 1) { //--if obstacle found then make the cell value ZERO so that it would not be added to path 
					grid[r][c] = 0;
					continue;
				}
				
				if(r == row_len - 1 && c == col_len - 1) { //finish cell
					grid[r][c] = 1;
				} 
				else {
					if(r < row_len -1) {  //--check if row is in bound
						grid[r][c] += grid[r+1][c]; 
					}
					if(c < col_len -1) {  //--check if col is in bound
						grid[r][c] += grid[r][c+1]; 
					}
				}
				
			}
		}
		
		return grid[0][0];
		
	}
	
	

}
