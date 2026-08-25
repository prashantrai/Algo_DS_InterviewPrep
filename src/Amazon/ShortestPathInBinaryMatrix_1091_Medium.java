package Amazon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	 * Interview Explanation Before Coding
	 * 
	 * “I’m going to model each open cell as a node in an unweighted graph. From
	 * each cell, I can move to any of its 8 neighboring cells as long as the
	 * neighbor is inside the matrix and contains 0.
	 * 
	 * Since every move costs exactly one step, BFS is a good fit because it
	 * explores nodes in increasing order of distance from the source. So the first
	 * time we reach the bottom-right cell, that distance is guaranteed to be the
	 * shortest path.
	 * 
	 * I’ll first handle the case where either the start or destination is blocked.
	 * Then I’ll put the starting cell into a queue and process BFS level by level.
	 * Each BFS level represents one additional cell in the path.
	 * 
	 * To avoid processing a cell more than once, I’ll mark it visited as soon as I
	 * add it to the queue. Since modifying the input is allowed for this
	 * implementation, I can change a visited 0 to 1, which avoids allocating a
	 * separate visited matrix.”
	 */
	
	/* Step-by-Step Algorithm
		1. Let n = grid.length.
		2. If the starting cell or destination cell is 1, return -1.
		3. Create a queue and add (0,0).
		4. Mark (0,0) visited by changing it to 1.
		5. Initialize distance = 1 because the path includes the starting cell.
		6. While the queue is not empty:
			i. Get the number of cells currently in the queue.
			ii. Process exactly those cells as one BFS level.
			iii. If a cell is (n-1,n-1), return distance.
			iv. Check all 8 neighboring cells.
			v. For every valid unvisited 0:
					a. mark it visited
					b. add it to the queue.
			vi. Increment distance.
		7. 	If BFS finishes without reaching the destination, return -1.
	 * */
	
	/*
    Time: O(n^2), Each cell is inserted into the queue at most once, 
    	and for each cell we examine at most 8 neighbors.


    Space: O(n^2), In the worst case, the BFS queue can contain O(n²) cells.
		We reuse the input matrix for visited tracking, so there is no additional visited[][].
    */
    
    
    // Capture shortest path between top left point to bottom right point
    private static List<int[]> path = new ArrayList<>();
    
    public static int shortestPathBinaryMatrix(int[][] grid) {
    	
    	int n = grid.length;
    	
    	// Start or destination is blocked.
    	if(grid[0][0] == 1 || grid[n-1][n-1] == 1) {
    		return -1;
    	}
    	
    	int[][] directions = {
    			{-1, -1}, {-1, 0}, {-1, 1}, // upper row
    			{0, -1,}, {0, 1},			// current row
    			{1, -1}, {1, 0}, {1, 1}		// next row
    	};
    	
    	
    	Queue<int[]> q = new ArrayDeque<int[]>();
    	q.offer(new int[] {0, 0});
    	
    	// Mark visited when adding to the queue.
    	grid[0][0] = 1;
    	
    	int distance = 1;
    	
    	while (!q.isEmpty()) {
    		int size = q.size();
    		
    		// Process one BFS level.
    		for(int i=0; i<size; i++) {
    			int[] cell = q.poll();
    			int row = cell[0];
    			int col = cell[1];
    			
    			if(row == n-1 && col == n-1) {
    				return distance;
    			}
    			
    			for(int[] dir : directions) {
    				int newRow = row + dir[0];
    				int newCol = col + dir[1];
    				
    				if(newRow < 0 || newRow >= n || 
    					newCol < 0 || newCol >= n || 
    					grid[newRow][newCol] != 0) {
    					
    					continue;
    				}
    				// Important implementation detail
    				// Mark a cell visited when you enqueue it, not when you dequeue it:
    				// Mark before enqueueing to prevent duplicates.
    				grid[newRow][newCol] = 1;
    				q.offer(new int[] {newRow, newCol});
    			}
    		}
    		distance++;
    	}
    	return -1;	
    	
    }
    
	
    
    // Follow-up: What if you had to return the actual shortest path itself?
    
    public List<int[]> shortestPathBinaryMatrix_with_follow_up(int[][] grid) {
        int n = grid.length;

        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return new ArrayList<>();
        }

        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0}, {1, 1}
        };

        Queue<int[]> queue = new ArrayDeque<>();

        // parent[r][c] stores the previous cell used to reach (r, c)
        int[][][] parent = new int[n][n][];

        queue.offer(new int[]{0, 0});
        grid[0][0] = 1;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            // Destination reached
            if (row == n - 1 && col == n - 1) {
                return buildPath(parent, n);
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow < 0 || newRow >= n ||
                    newCol < 0 || newCol >= n ||
                    grid[newRow][newCol] != 0) {
                    continue;
                }

                // Mark visited
                grid[newRow][newCol] = 1;

                // Remember how we reached this cell
                parent[newRow][newCol] = new int[]{row, col};

                queue.offer(new int[]{newRow, newCol});
            }
        }

        return new ArrayList<>();
    }

    private List<int[]> buildPath(int[][][] parent, int n) {
        List<int[]> path = new ArrayList<>();

        int row = n - 1;
        int col = n - 1;

        while (true) {
            path.add(new int[]{row, col});

            if (row == 0 && col == 0) {
                break;
            }

            int[] prev = parent[row][col];
            row = prev[0];
            col = prev[1];
        }

        Collections.reverse(path);
        return path;
    }
	
}
