package Amazon;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MapOfHighestPeak_1765_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Interview Explanation Before Coding

		“Since every water cell must have height zero, I can think of all 
		water cells as starting points.
		
		For any land cell, the largest height I can assign is its shortest 
		distance from a water cell, because neighboring cells can differ by at most one.
		
		So I’ll run a multi-source BFS. I’ll initially add every water cell 
		to the queue with height zero. Then as I expand outward, every unvisited 
		neighbor gets the current cell’s height plus one.
		
		BFS processes cells level by level, so the first time we reach a 
		land cell is through its shortest path from some water cell. 
		That gives us the maximum valid height for that cell.
		
		Each cell is processed once, so this gives us linear time in the size of the grid.”

	 * */
	
	/* Complexity Analysis
		Let: m = number of rows, n = number of columns
		
		Time: O(m × n), Every cell is added to the queue and processed at most once.
		
		Space: O(m × n)
		We need: the result matrix BFS queue, which can contain up to m × n cells
		
		The output matrix itself is required by the problem.
	*/
	
	/* Step-by-Step Algorithm: 
	 
		1. Create a height matrix of the same dimensions.
		2. Initialize every cell to -1 to mean not assigned yet.
		3. Find every water cell:
			- set its height to 0
			- add it to the BFS queue
		4. Start BFS from all water cells simultaneously.
		5. For each cell removed from the queue:
			- check its four neighbors
			- if a neighbor has not been assigned:
		
				height[nr][nc] = height[r][c] + 1;
			
			- add that neighbor to the queue
		7. Return the height matrix.
	 * */

	
	public int[][] highestPeak(int[][] isWater) {
		int rows = isWater.length;
        int cols = isWater[0].length;

        int[][] height = new int[rows][cols];
        
        // -1 means this cell has not been assigned yet.
        for(int r = 0; r<rows; r++) {
        	Arrays.fill(height[r], -1);
        }
        
        Queue<int[]> q = new ArrayDeque<>();
        
        // All water cells are BFS starting points with height 0.
        for(int r = 0; r<rows; r++) {
        	for(int c = 0; c<cols; c++) {
        		if(isWater[r][c] == 1) {
        			height[r][c] = 0;
        			q.offer(new int[]{r, c});
        		}
        		
        	}
        }
        int[][] dirs = {
                {1, 0}, {-1, 0},
                {0, 1}, {0, -1}
            };
        
        while(!q.isEmpty()) {
        	int[] cell = q.poll();
        	int r = cell[0];
        	int c = cell[1];
        	
        	for(int[] dir : dirs) {
        		int nr = r + dir[0];
        		int nc = c + dir[1];
        		
        		if(nr < 0 || nr >= rows ||
                   nc < 0 || nc >= cols ||
                   height[nr][nc] != -1) {
        		
        			continue;
        		}
        		
        		height[nr][nc] = height[r][c] + 1;
        		q.offer(new int[] {nr, nc});
        	}
        	
        }
        
        return height;
	}
	
}
