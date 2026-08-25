package Amazon;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class NumberOfIslands_200_Medium {

	public static void main(String[] args) {

		 char M[][]=  new char[][] {
			 {'1', '1', '0', '0', '0'},
             {'0', '1', '0', '0', '1'},
             {'1', '0', '0', '1', '1'},
             {'0', '0', '0', '0', '0'},
             {'1', '0', '1', '0', '1'}
            };
          System.out.println("Expected: 6, Actual: "+numIslands(M));   
           
         char M2[][] =  new char[][] {
   			 	{'1', '1', '0', '0'},
                {'0', '1', '0', '0'},
                {'0', '0', '0', '1'},
                {'1', '0', '1', '1'}
               };   

               System.out.println("Expected: 3, Actual: "+numIslands(M2));
            
	}
	
	/* Interview explanation
		"I'll scan the entire grid. Whenever I encounter a 1, I know 
		I've found land belonging to an island that hasn't been visited yet, 
		so I increment the island count. 
		
		Then I run DFS from that cell and mark every horizontally or vertically 
		connected land cell as visited. Because that entire island gets marked 
		during the traversal, I won't count it again later. 
		
		Every cell is visited at most once, so the time complexity 
		is O(rows × cols). 
		
		I'm modifying the grid itself to track visited cells, which avoids 
		an extra visited matrix."
	 
	 * */
	
	/*
	 * Complexity Analysis
    	Time complexity : O(M×N) where M is the number of rows and N is the number of columns.
    	Space complexity : O(M×N) worst case, in case that the grid map is filled with 
    		lands where DFS goes by M×N deep.
	 * 
	 * Runtime complexity Explanation: 
	 * The time complexity is O(cells). Every cell is inspected at least once, 
	 * due to the nested for loops. Any single cell is inspected at most 5 times. 
	 * We know this because there are 5 ways a cell (i, j) can be inspected:

		inspected in the nested for loop, before dfs is called
		dfs from cell (i + 1, j)
		dfs from cell (i - 1, j)
		dfs from cell (i, j + 1)
		dfs from cell (i, j - 1)
		The nested for loops obviously inspect each cell exactly once.
		
		dfs(i, j) exits immediately if (i, j) has been inspected already, 
		which implies (i, j) can only be visited from dfs(i + 1, j) once, 
		visited from dfs(i - 1, j) once, visited from dfs(i, j + 1) once, 
		and visited from (i, j - 1) once.
	*/
	
	public static int numIslands(char[][] grid) {

	    if (grid == null || grid.length == 0 || grid[0].length == 0) {
	        return 0;
	    }

	    int islands = 0;

	    for (int r = 0; r < grid.length; r++) {
	        for (int c = 0; c < grid[0].length; c++) {

	            if (grid[r][c] == '1') {
	                dfs(grid, r, c);
	                //bfs(grid, r, c);   // Working - visit entire island
	                islands++;
	            }
	        }
	    }

	    return islands;
	}
    
	private static void dfs(char[][] grid, int r, int c) {

	    if (r < 0 || r >= grid.length ||
	        c < 0 || c >= grid[0].length ||
	        grid[r][c] != '1') {
	        return;
	    }

	    // Mark current land as visited.
	    grid[r][c] = '2';

	    dfs(grid, r + 1, c);
	    dfs(grid, r - 1, c);
	    dfs(grid, r, c + 1);
	    dfs(grid, r, c - 1);
	}
    
    
    /* Time & Space Complexity:
    Time: O(m * n) → each cell visited once
    Space: Space: O(m * n), but it avoids recursion stack
	*/
	private static void bfs(char[][] grid, int r, int c) {

	    int rows = grid.length;
	    int cols = grid[0].length;

	    Queue<int[]> queue = new ArrayDeque<>();

	    queue.offer(new int[]{r, c});
	    grid[r][c] = '2';

	    int[][] directions = {
	        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
	    };

	    while (!queue.isEmpty()) {

	        int[] cell = queue.poll();

	        for (int[] dir : directions) {

	            int nr = cell[0] + dir[0];
	            int nc = cell[1] + dir[1];

	            if (nr >= 0 && nr < rows &&
	                nc >= 0 && nc < cols &&
	                grid[nr][nc] == '1') {

	                grid[nr][nc] = '2';
	                queue.offer(new int[]{nr, nc});
	            }
	        }
	    }
	}
	

}
