package TikTok;

import java.util.HashSet;
import java.util.Set;

public class NumberOfDistinctIslands_694_Medium {

	public static void main(String[] args) {
		
		int[][] grid = {{1,1,0,0,0},
						{1,1,0,0,0},
						{0,0,0,1,1},
						{0,0,0,1,1}};
		System.out.println("Expected: 1, Actual: " + numDistinctIslands(grid));
		
		int[][] grid2 = {
				{1,1,1,0,0},
				{0,1,0,0,0},
				{0,0,1,1,0},
				{0,0,0,1,1}};
		
		System.out.println("Expected: 1, Actual: " + numDistinctIslands(grid2));


		
	}
	
	/*
    Refer: https://leetcode.com/problems/number-of-distinct-islands/discuss/2040516/Java-or-DFS-or-Simple
    
    Time: O(M.N)
    Space: O(M.N), recursion
    */
	
	private static final int[][] DIRS = {{0, 1, 'R'}, {0, -1, 'L'}, {1, 0, 'U'}, {-1, 0, 'D'}};

	public static int numDistinctIslands(int[][] grid) {
	    Set<String> distinct = new HashSet<>();
	    // var distinct = new HashSet<>(); //another way to initialize
	    StringBuilder island = new StringBuilder();
	    
	    for(int r=0; r<grid.length; r++) {
	        for(int c=0; c<grid[0].length; c++) {
	            
	            if(grid[r][c] ==1) {
	                island.append('S'); // start
	                dfs(grid, r, c, island);
	                distinct.add(island.toString());
	                island.setLength(0);
	                
	            }
	        }
	    }
	    return distinct.size();
	}
	    
	private static void dfs(int[][] grid, int r, int c, StringBuilder island) {
	    if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1)
	        return;
	    
	    grid[r][c] = 2; // mark visited
	    
	    //for (var dir : DIRS) // visit neighbors
	    
	    for(int[] dir : DIRS) { // visit neighbors
	        island.append((char) dir[2]);
	        dfs(grid, r+dir[0], c+dir[1], island);
	    }
	    
	    /** 
	     * Reference: https://leetcode.com/problems/number-of-distinct-islands/discuss/108475/Java-very-Elegant-and-concise-DFS-Solution(Beats-100)/252839
	     * 
	     * WARNING: DO NOT FORGET to add path for backtracking, otherwise, 
	     * we may have same result when we count two 
	     * distinct islands in some cases
	     * 
	     * eg:              1 1 1   and    1 1 0
	     *                  0 1 0          0 1 1
	     * with b:          rdbr           rdr
	     * without b:       rdr            rdr
	     * */
	    island.append('b'); // mark backtracking , NOTE: code is passing for all cases without this too
	}
	
}
