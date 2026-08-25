package Amazon;

import java.util.ArrayDeque;
import java.util.Queue;

public class Matrix_01_542_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Interview Explanation Before Coding
	 * 
	 * “I’m going to model the matrix as an unweighted graph where each cell is
	 * connected to its four adjacent cells.
	 * 
	 * Instead of running a BFS from every 1 to search for the closest 0, which
	 * would repeat a lot of work, I’ll use multi-source BFS.
	 * 
	 * I’ll initially add every 0 cell to the queue with distance 0. For the 1
	 * cells, I’ll initialize their distance to -1, which also tells me they haven’t
	 * been visited yet.
	 * 
	 * Then I run BFS from all zeros simultaneously. Whenever I reach an unvisited
	 * neighbor, its shortest distance is the current cell’s distance plus one, so I
	 * assign that value and add it to the queue.
	 * 
	 * BFS processes cells in increasing distance order, so the first time a cell is
	 * reached is guaranteed to be through its nearest zero.”
	 */
	
	/* Step-by-Step Algorithm
		1. Create a dist matrix.
		2. Scan every cell:
			If the cell is 0, set dist[r][c] = 0 and add it to the queue.
			Otherwise set dist[r][c] = -1 to mark it unvisited.
		3. Run BFS.
		4. For every cell removed from the queue, check its four neighbors.
		
		5. If a neighbor is valid and unvisited: dist[nr][nc] = dist[r][c] + 1;
		6. Add that neighbor to the queue.
		7. Return dist.
	 * */
	
	
	/* Complexity: 
	 	Time Complexity: O(m * n), Every cell enters the queue at most once, and from 
	 	each cell we check at most four neighbors.
		
		Therefore: O(m * n)
		
		Space Complexity: O(m * n), We create the output distance matrix, and the BFS queue 
		can contain up to m * n cells.
		
		If the returned output matrix isn't counted as auxiliary space, 
		the queue itself is still: O(m * n)
	 * */
	
	
	public static int[][] updateMatrix(int[][] mat) {
		
		int rows = mat.length;
		int cols = mat[0].length;
		
		int[][] dist = new int[rows][cols];
		Queue<int[]> q = new ArrayDeque<>();
		
		// Add all 0s as BFS starting points.
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(mat[r][c] == 0) {
					dist[r][c] = 0;
					q.offer(new int[]{r, c});
				} else {
					dist[r][c] = -1;
				}
			}
			
		}
		
		int[][] dirs = {
	            {1, 0}, {-1, 0},
	            {0, 1}, {0, -1} };
		
		while(!q.isEmpty()) {
			int[] cell = q.poll();
			int r = cell[0];
			int c = cell[1];
			
			for(int[] dir : dirs) {
				int nr = r + dir[0];
				int nc = c + dir[1];
				
				if (nr < 0 || nr >= rows ||
	                nc < 0 || nc >= cols ||
	                dist[nr][nc] != -1) {
					
					continue;
				}
				dist[nr][nc] = dist[r][c] + 1;
				q.offer(new int[]{nr, nc});
			}
		}
		return dist;
		
	}
	

}
