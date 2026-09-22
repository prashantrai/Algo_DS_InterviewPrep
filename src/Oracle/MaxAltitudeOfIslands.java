package Oracle;

import java.util.*;

public class MaxAltitudeOfIslands {

	public static void main(String[] args) {

        int[][] grid1 = {
                {1, 1, 1, 1, 0},
                {1, 8, 0, 10, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        System.out.println("Test 1");
        System.out.println("Expected: [10]");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid1));

        System.out.println();
        
        int[][] grid_1 = {
                {1, 1, 1, 0, 0},
                {1, 8, 0, 10, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        System.out.println("Test 1.1");
        System.out.println("Expected: [8, 10]");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid_1));

        System.out.println();
        

        int[][] grid2 = {
                {1, 0, 5},
                {2, 0, 6},
                {0, 0, 0},
                {7, 8, 0}
        };

        System.out.println("Test 2");
        System.out.println("Expected: [2, 6, 8]");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid2));

        System.out.println();

        int[][] grid3 = {
                {0, 0, 0},
                {0, 0, 0}
        };

        System.out.println("Test 3 - All water");
        System.out.println("Expected: []");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid3));

        System.out.println();

        int[][] grid4 = {
                {9}
        };

        System.out.println("Test 4 - Single land cell");
        System.out.println("Expected: [9]");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid4));

        System.out.println();

        int[][] grid5 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Test 5 - One large island");
        System.out.println("Expected: [9]");
        System.out.println("Actual:   " + maxAltitudeOfIslands(grid5));
    }
	
	/* Interview Script: 
	 * 
	 * I'll treat every group of horizontally or vertically connected positive cells
	 * as one connected component.
	 * 
	 * I'll scan the entire grid. Whenever I find a positive cell that hasn't been
	 * visited, that means I've found a new island.
	 * 
	 * From that cell I'll run BFS. During BFS, I'll mark every cell in that island
	 * visited and maintain the maximum elevation seen.
	 * 
	 * When BFS finishes, I know I've processed the entire island, so I'll add that
	 * maximum elevation to my result.
	 * 
	 * Since every grid cell is processed at most once, the overall time complexity
	 * is O(m times n).
	 */
	
	/*Step-by-Step Algorithm
	 * 
	 * Given: int[][] grid 
	 * 
	 * Step 1
	 * 	Create: boolean[][] visited, with the same dimensions as the grid.
	 * 
	 * Step 2
	 * 	Create the four possible movement directions: up down left right 
	 * 	int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
	 * 
	 * Step 3
	 * 	Scan every cell.
	 * 	For each: grid[r][c]
	 * 	skip it when: grid[r][c] == 0 because it is water.
	 * 	Also skip when: visited[r][c] == true because that island was already processed.
	 * 
	 * Step 4 
	 * 	If the cell is: land + not visited start BFS.
	 * 	This represents a new island.
	 * 
	 * Step 5
	 * 	Inside BFS:
	 * 		1. Put starting cell into queue. 
	 * 		2. Mark it visited. 
	 * 		3. Initialize max elevation. 
	 * 		4. Remove cells from queue one at a time. 
	 * 		5. Update maximum elevation. 
	 * 		6. Check four neighbors. 
	 * 		7. Add unvisited land neighbors to queue.
	 * 
	 * Step 6
	 * 	Once BFS finishes: result.add(maxElevation);
	 * 
	 * Continue scanning the grid.
	 */ 
	
	 /* Complexity Analysis Before Coding
	 * 
	 * Let:
	 * m = number of rows 
	 * n = number of columns 
	 * 
	 * Time Complexity:  O(m * n)
	 * 
	 * We scan every cell once.
	 * 
	 * During BFS, each land cell is added to and removed from the queue at most
	 * once.
	 * 
	 * For every cell we check exactly four neighbors, which is constant work.
	 * 
	 * Therefore: O(m * n) 
	 * 
	 * Space Complexity: O(m * n) for the visited matrix.
	 * 
	 * In the worst case, the BFS queue can also contain O(m * n) cells.
	 * 
	 * So overall auxiliary space is: O(m * n)
	 */
	
	/**
     * "I scan the grid looking for unvisited land cells.
     * Each such cell starts a new island.
     *
     * I run BFS to visit the entire island and track the
     * maximum elevation seen inside that connected component."
     *
     * Time:  O(m * n)
     * Space: O(m * n)
     */
    
	public static List<Integer> maxAltitudeOfIslands(int[][] grid) {
    	
    	List<Integer> result = new ArrayList<>();
    	
    	if(grid == null || grid.length == 0 || grid[0].length == 0) {
    		return result;
    	}
    	
    	int rows = grid.length;
        int cols = grid[0].length;
    	
    	boolean[][] visited = new boolean[rows][cols];
    	
    	// Four-directional adjacency: up, down, left, right.
    	final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    	
    	// Follow-up: Largest island by area
        int largestArea = 0;
    	
    	for(int r = 0; r < rows; r++) {
    		for(int c = 0; c < cols; c++) {
    			if(grid[r][c] == 0 || visited[r][c]) { 
    				continue;
    			}
    			
    			// Follow-up: Largest island by area
                int[] area = new int[1];
    			
    			int maxElevation = bfs(grid, r, c, DIRS, visited, area);
    			// int maxElevation_dfs = dfs( grid, r, c, visited, area); // working
    				
    			result.add(maxElevation);
    			
    			// Follow-up: Update largest island seen so far.
                largestArea = Math.max(largestArea, area[0]);
    		}
    	}
    	
    	// Follow-up: Just print; do not change return type.
        System.out.println("Largest island area: " + largestArea);

    	
    	return result;
    }
    
    private static int bfs(int[][] grid, int r, int c, 
    						int[][] directions,  boolean[][] visited, int[] area) {
    	
    	int rows = grid.length;
        int cols = grid[0].length;
    	
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {r, c});
        
        visited[r][c] = true;
        
        int maxElevation = grid[r][c];
        
        while(!q.isEmpty()) {
        	
        	int[] current = q.poll();
        	
        	int curRow = current[0];
        	int curCol = current[1];
        	
        	// Follow-up: Every dequeued cell belongs to this island.
            area[0]++;
        	
        	// Track the highest elevation found in this island.
        	maxElevation = Math.max(maxElevation, grid[curRow][curCol]);
        	
        	// Explore all horizontally and vertically connected land.
        	for (int[] direction : directions) {
        		
        		int nr = curRow + direction[0];
                int nc = curCol + direction[1];

                if (nr < 0 || nr >= rows ||
                        nc < 0 || nc >= cols) {
                    continue;
                }
        		
                // Only unvisited positive cells belong to the island.
                if (grid[nr][nc] == 0 || visited[nr][nc]) {
                    continue;
                }

                visited[nr][nc] = true;
                q.offer(new int[] {nr, nc});
        	}
        	
        }
        
        return maxElevation;
    } 

    private static int dfs(
            int[][] grid,
            int r,
            int c,
            boolean[][] visited,
            int[] area) { // follow-up: largest area

        // Outside grid, water, or already visited.
        if (r < 0 || r >= grid.length ||
                c < 0 || c >= grid[0].length ||
                grid[r][c] == 0 ||
                visited[r][c]) {

            return 0;
        }

        visited[r][c] = true;
        
        // Follow-up: This cell belongs to the current island.
        area[0]++;

        // Start with the elevation of the current cell.
        int max = grid[r][c];

        // Explore all four connected directions.
        max = Math.max(max, dfs(grid, r - 1, c, visited, area));
        max = Math.max(max, dfs(grid, r + 1, c, visited, area));
        max = Math.max(max, dfs(grid, r, c - 1, visited, area));
        max = Math.max(max, dfs(grid, r, c + 1, visited, area));

        return max;
    }
    
    
    /** Follow-up: Very large grid */
	/*
	 * Interview Script
	 * 
	 * For a very large grid, I would first avoid recursive DFS because if the grid
	 * contains one very large island, the recursion depth could approach the number
	 * of cells and cause a stack overflow in Java.
	 * 
	 * I would switch to an iterative DFS or BFS using an ArrayDeque.
	 * 
	 * The second concern is the visited matrix, which requires O(m * n) additional
	 * memory. If I'm allowed to modify the input, I can remove the visited matrix
	 * completely and mark a cell visited by changing its value to zero when I add
	 * it to the queue or stack.
	 * 
	 * That keeps the time complexity O(m * n), but removes the O(m * n) visited
	 * matrix. The remaining extra memory is proportional to the active BFS queue or
	 * DFS stack.
	 * 
	 * If modifying the input is not allowed, then I still need some visited state,
	 * although I could use a more compact representation such as a BitSet if memory
	 * is important.
	 * 
	 * If the interviewer asks:
	 * 
	 * Which would you use, BFS or iterative DFS?
	 * 
	 * Say:
	 * 
	 * Both have the same asymptotic complexity. I would probably use BFS but iterative DFS
	 * with ArrayDeque is equally valid.
	 */   
    
    // DFS Iterative
    private static int dfsIterative(int[][] grid, int startRow, int startCol) {

        int rows = grid.length;
        int cols = grid[0].length;

        Deque<int[]> stack = new ArrayDeque<>();

        // Save starting elevation before marking it visited.
        int max = grid[startRow][startCol];

        stack.push(new int[]{startRow, startCol});

        // Very large grid:
        // Mark directly in input to avoid O(m * n) visited[][] memory.
        grid[startRow][startCol] = 0;

        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        while (!stack.isEmpty()) {

            int[] current = stack.pop();

            int r = current[0];
            int c = current[1];

            for (int[] direction : directions) {

                int nr = r + direction[0];
                int nc = c + direction[1];

                if (nr < 0 || nr >= rows ||
                        nc < 0 || nc >= cols ||
                        grid[nr][nc] == 0) {
                    continue;
                }

                // Read elevation before overwriting the cell.
                int elevation = grid[nr][nc];

                max = Math.max(max, elevation);

                // Mark visited before pushing so the cell
                // cannot be added multiple times.
                grid[nr][nc] = 0;

                stack.push(new int[]{nr, nc});
            }
        }

        return max;
    }
    
	
    
    /** Follow-up 3: Grid does not fit into memory
    	Streaming/chunking, row-by-row processing, connected components across boundaries
    */
	/* Interview script to say
	 * 
	 * If the grid itself does not fit into memory, I would no longer try to keep
	 * the entire grid or a full visited matrix.
	 * 
	 * Since connectivity is only horizontal and vertical, when I'm processing row
	 * r, future rows can only connect to components that are still present in row
	 * r.
	 * 
	 * So I can process the input one row at a time and keep only the previous row,
	 * the current row, and metadata for the connected components that touch this
	 * active frontier.
	 * 
	 * For every positive cell in the current row, I look at its left neighbor and
	 * its upper neighbor. If neither is land, this cell starts a new component. If
	 * exactly one belongs to an existing component, I assign this cell to that
	 * component. If both belong to different components, this cell connects those
	 * components, so I merge them using Union-Find.
	 * 
	 * For each component, I also maintain the maximum elevation seen so far.
	 * 
	 * After finishing a row, if a component appeared in the previous frontier but
	 * no longer appears in the current frontier, then it can never reconnect in a
	 * later row because vertical connectivity requires a continuous path through
	 * adjacent rows. At that point, that island is complete and I can output its
	 * maximum elevation and discard its state.
	 * 
	 * This lets me process the grid as a stream instead of loading the entire grid
	 * into memory.
	 */
    
}

/* Max Altitude of Islands

	You are given an m x n grid. Positive numbers represent land elevation 
	and 0 represents water. Land cells connected horizontally or vertically 
	form an island. For each island, return/find its highest elevation.
	
	Example from the report:
	
	1  1  1  1   0
	1  8  0 10   0
	1  1  0  0   0
	0  0  0  0   0
	
	The underlying problem is:
	
	Find every connected component of positive cells
	        +
	track the maximum value inside each component.
	
	Your natural interview solution should be:
	
	Scan every cell
	    |
	    +-- water / visited -> skip
	    |
	    +-- unvisited land
	            |
	            v
	          BFS/DFS
	            |
	            +-- mark entire island visited
	            +-- track max elevation
	
	Complexity:
	
	Time:  O(m * n)
	Space: O(m * n) worst case
	
	I rank this EXTREMELY HIGH because it is the freshest distinct algorithmic 
	Oracle problem on VOPrep, and it opens many highly plausible follow-ups for 
	a Principal-level coding round:
	
	Return maximum for every island
	Return coordinates of each peak
	Largest island by area
	8-direction adjacency
	Don't modify input
	Very large grid
	Grid does not fit into memory
	Parallel processing
	Streaming rows
	Multiple queries
 */