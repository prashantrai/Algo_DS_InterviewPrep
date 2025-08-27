package LinkedIn;

import java.util.LinkedList;
import java.util.Queue;

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
        
        if(m == null || m.length == 0) return 0;
        
        int count = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                if(m[r][c] == '1') {
//                    dfs(m, r, c);
                    bfs(m, r, c);
                    count++;	
                }
            }
        }
        return count;
    }
    
    
	/* Time & Space Complexity:
    Time: O(m * n) → each cell visited once
    Space: O(min(m,n)) → queue stores one island at a time
	*/
	private static void bfs(char[][] grid, int r, int c) {
	    int rows = grid.length; 
	    int cols = grid[0].length;
	    
	    Queue<int[]> queue = new LinkedList<>();
	    queue.offer(new int[]{r, c});
	    
	    grid[r][c] = '0'; // mark visited
	
	    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	
	    while (!queue.isEmpty()) {
	        int[] cell = queue.poll();
	        
	        for (int[] dir : directions) {
	            int nr = cell[0] + dir[0];
	            int nc = cell[1] + dir[1];
	            
	            if (nr >= 0 && nr < rows && nc >= 0 
	            		&& nc < cols 
	            		&& grid[nr][nc] == '1') {
	            	
	                queue.offer(new int[]{nr, nc});
	                grid[nr][nc] = '0'; // mark visited
	            }
	        }
	        
	    }// while closed
	
	}//bfs closed
	
	private static void dfs(char[][] m, int r, int c) {
        
        if(r >= m.length || r < 0 || c >= m[0].length || c<0 || m[r][c] != '1') {
            return;
        }
        m[r][c] = '2'; // visited
        dfs(m, r, c+1);
        dfs(m, r, c-1);
        dfs(m, r+1, c);
        dfs(m, r-1, c);
    }
    
    

}
