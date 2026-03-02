package Geico;

import java.util.PriorityQueue;

public class PathWithMaximumMinimumValue_1102_Medium {

	public static void main(String[] args) {

	}

	/* Time: O(m·n·log(m·n)) — each cell is pushed/popped 
    from the heap once, heap ops cost log(m·n)
	Space: O(m·n)
	*/
	public int maximumMinimumPath(int[][] grid) {
	int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
	int m = grid.length; 
	int n = grid[0].length;
	boolean[][] visited = new boolean[m][n];
	
	// Max-heap: {cell value, row, col}
	PriorityQueue<int[]> maxPQ 
	    = new PriorityQueue<>((a,b) -> (b[0] - a[0]));
	
	 maxPQ.offer(new int[]{grid[0][0], 0, 0});
	 visited[0][0] = true;
	
	 int minVal = grid[0][0];
	
	 while(!maxPQ.isEmpty()) {
	    int[] curr = maxPQ.poll();
	    int val = curr[0]; 
	    int r =  curr[1], c = curr[2];
	
	    // Update path score (minimum so far)
	    minVal = Math.min(minVal, val);
	
	    // Reached destination
	    if(r == m-1 && c == n-1) 
	        return minVal;
	
	    // Explore 4 neighbors
	    for(int[] dir : dirs) {
	        int nr = r + dir[0];
	        int nc = c + dir[1];
	
	        if(nr >= 0 && nr < m 
	            && nc >= 0 && nc < n && !visited[nr][nc]) {
	                visited[nr][nc] = true;
	                maxPQ.offer(new int[]{grid[nr][nc], nr, nc});
	        }
	    }
	 }
	
	 return minVal;
	
	}
	
}
