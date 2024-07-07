package Facebook;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ShortestPathInBinaryMatrix_1091_Medium {

	public static void main(String[] args) {
		int[][] grid = {{0,0,0},{1,1,0},{1,1,0}};
		System.out.println("Expected: 4, Actual: " + shortestPathBinaryMatrix(grid));
		
		System.out.println("Expected Path: [[0, 0], [0, 1], [1, 2], [2, 2]], "
				+ "Actual Path: " + Arrays.deepToString(path.toArray()) );
		
		
		
		int[][] grid2 = {{1,0,0},{1,1,0},{1,1,0}};
		System.out.println("Expected: -1, Actual: " + shortestPathBinaryMatrix(grid2));
		
		path.clear(); // clearing to remove the entry from previous test
		System.out.println("Expected Path: [], "
				+ "Actual Path: " + Arrays.deepToString(path.toArray()) );

	}

	
	
	/*
    Time complexity: O(N).
        Processing a cell is O(1), and each of 
        the N cells is processed at most once, 
        giving a total of O(N).


    Space complexity: O(N).
        The visited set also requires O(N) space; 
        in the worst case, it will hold the row 
        and column of each of the N cells.
    */
    
    
    private static final int[][] directions = 
    {{-1,-1}, {-1,0}, {-1,1},
     {0,-1}, {0,1},
     {1,-1}, {1,0}, {1,1} };
    
    // Capture shortest path between top left point to bottom right point
    private static List<int[]> path = new ArrayList<>();
    
    public static int shortestPathBinaryMatrix(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // check if both start and target cells are open, 
        // i.e. if either is 1, return -1.
        if(grid[0][0] != 0 || grid[rows-1][cols-1] != 0) 
            return -1;
        
        //Set up BFS
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0, 0});
        
        boolean[][] visited = new boolean[rows][cols];
        visited[0][0] = true;
        int currentDistance = 1;
        
        // Carry out the BFS
        while(!q.isEmpty()) {
            int size = q.size();
            
            // initialzed outside for to record the 
            // path to the shortest distance
            int r=0; 
            int c=0;
            
            for(int i=0; i<size; i++) {
                int[] currCell = q.poll();
//                int r = currCell[0]; // this works too, but capture path declared before this loop
//                int c = currCell[1];
                r = currCell[0]; //
                c = currCell[1];
                
                // check if are the the target cell
                if(r == rows-1 && c == cols-1) {
                	path.add(new int[]{r, c});
                    return currentDistance;
                }
                
                // visit neighbour and add to queue
                for(int[] neigh : getNeighbours(r, c, grid)) {
                    int n_row = neigh[0];
                    int n_col = neigh[1];
                    if(visited[n_row][n_col]) {
                        continue;
                    }
                    visited[n_row][n_col] = true;
                    q.offer(new int[]{n_row, n_col});
                }
            } // for closed
            
            // We'll now be processing all nodes at current_distance + 1
            currentDistance++;  // distance of the shortest path
            // capture shortest path between top left point to bottom right point
            path.add(new int[]{r, c}); // 
            
        } // while closed
        
        return -1;
    }
    
    private static List<int[]> getNeighbours(int row, int col, int[][] grid) {
        List<int[]> neighList = new ArrayList<>();
        for(int i=0; i<directions.length; i++) {
            int newRow = row + directions[i][0];
            int newCol = col + directions[i][1];
            
            // if out of bound OR cell value is not '0'
            if(newRow < 0 || newRow >= grid.length 
               || newCol <0 || newCol >= grid[0].length
               || grid[newRow][newCol] != 0) {
                
                continue;
            }
            neighList.add(new int[]{newRow, newCol});
        }
        return neighList;
    } 
	
	
}
