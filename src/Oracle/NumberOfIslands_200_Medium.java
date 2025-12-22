package Oracle;

import java.util.LinkedList;
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
	
	
	/*
	 * Complexity Analysis
    	Time complexity : O(M×N) where M is the number of rows and N is the number of columns.
    	Space complexity : worst case O(M×N) in case that the grid map is filled with lands where 
    					DFS goes by M×N deep.
	 * 
	 * Runtime complexity Explanation: 
	 * The time complexity is O(cells). Every cell is inspected at least once, due to the nested for loops. Any single cell is inspected at most 5 times. We know this because there are 5 ways a cell (i, j) can be inspected:

		inspected in the nested for loop, before dfs is called
		dfs from cell (i + 1, j)
		dfs from cell (i - 1, j)
		dfs from cell (i, j + 1)
		dfs from cell (i, j - 1)
		The nested for loops obviously inspect each cell exactly once.
		
		dfs(i, j) exits immediately if (i, j) has been inspected already, which implies (i, j) can only be visited from dfs(i + 1, j) once, visited from dfs(i - 1, j) once, visited from dfs(i, j + 1) once, and visited from (i, j - 1) once.
	*/
	
	public static int numIslands(char[][] m) {
        
        if (m == null || m.length == 0 || m[0].length == 0) return 0;
        
        int count = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                if(m[r][c] == '1') {
                    helper(m, r, c);
                    count++;
                }
            }
        }
        return count;
    }
    
    private static void helper(char[][] m, int r, int c) {
        
        if(r >= m.length || r < 0 || c >= m[0].length || c<0 || m[r][c] != '1') {
            return;
        }
        m[r][c] = '2'; // visited
        helper(m, r, c+1);
        helper(m, r, c-1);
        helper(m, r+1, c);
        helper(m, r-1, c);
    }
    
    
    /* Time & Space Complexity:
    Time: O(m * n) → each cell visited once
    Space: O(min(m,n)) → queue stores one island at a time
	*/
	private void bfs(char[][] grid, int r, int c) {
	    int rows = grid.length, cols = grid[0].length;
	    Queue<int[]> queue = new LinkedList<>();
	    queue.offer(new int[]{r, c});
	    grid[r][c] = '2'; // mark visited, '0' can be used too
	
	    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	
	    while (!queue.isEmpty()) {
	        int[] cell = queue.poll();
	        for (int[] dir : directions) {
	            int nr = cell[0] + dir[0];
	            int nc = cell[1] + dir[1];
	            if (nr >= 0 && nr < rows && nc >= 0 
	            		&& nc < cols && grid[nr][nc] == '1') {
	                queue.offer(new int[]{nr, nc});
	                grid[nr][nc] = '0'; // mark visited
	            }
	        }
	    }
	}
	
	
	
	// Iterative version for DFS
	/*
	 Time: O(M × N), each cell in the M × N grid is visited at most once.
	 Space: O(M × N) in the worst case
		-The stack (used instead of recursion) can grow large depending 
		 on the island shape.
		-Worst case: The entire grid is filled with '1's forming one big island. 
		  In this scenario, the stack may store up to M × N cells during traversal 
		  (e.g., if DFS goes deep in a snake-like path).
		-This matches the worst-case space of recursive DFS (which uses the call stack).
	*/

	
	public int numIslands_DFS_Iter(char[][] grid) {
	    if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
	    
	    int count = 0;
	    int rows = grid.length, cols = grid[0].length;
	    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
	    
	    for (int r = 0; r < rows; r++) {
	        for (int c = 0; c < cols; c++) {
	            if (grid[r][c] == '1') {
	                count++;
	                Stack<int[]> stack = new Stack<>();
	                stack.push(new int[]{r, c});
	                grid[r][c] = '2'; // Mark as visited
	                
	                while (!stack.isEmpty()) {
	                    int[] cell = stack.pop();
	                    int currR = cell[0], currC = cell[1];
	                    
	                    for (int[] dir : directions) {
	                        int nr = currR + dir[0];
	                        int nc = currC + dir[1];
	                        
	                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1') {
	                            stack.push(new int[]{nr, nc});
	                            grid[nr][nc] = '2'; // Mark immediately to avoid re-adding
	                        }
	                    }
	                }
	            }
	        }
	    }
	    return count;
	}
	

}
