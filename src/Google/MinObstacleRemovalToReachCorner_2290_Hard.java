package Google;

import java.util.*;

public class MinObstacleRemovalToReachCorner_2290_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Interview Script: 
	    “I’ll model each grid cell as a graph node. Moving into a neighbor has a cost 
	    equal to the neighbor’s value: 0 if it's empty and 1 if I need to remove an 
	    obstacle.
	
	    Since all edge costs are only 0 or 1, instead of regular Dijkstra with a 
	    priority queue, I can use 0-1 BFS with a deque.
	
	    I’ll maintain a dist matrix where dist[r][c] is the minimum number of 
	    obstacles removed to reach that cell.
	
	    When I find a better path to a neighbor, if that neighbor costs 0, I add it to 
	    the front of the deque because the total cost didn’t increase. If it costs 1, 
	    I add it to the back.
	
	    This ensures that cells with smaller removal cost are processed before cells 
	    with larger cost, so once we've computed the shortest distances, dist[m-1]
	    [n-1] is the minimum number of obstacles we need to remove.”
	*/
	
	/* Complexity
	    Time: O(m*n), Each of the m⋅n cells in the grid is visited exactly once because we only process unvisited cells. The deque operations are all O(1).
	
	    Thus, the total time complexity is O(m*n).
	
	    Space : O(m*n), The minObstacles array and the deque both take O(m⋅n) space. 
	    All other variables take constant space.
	
	    Thus, the space complexity remains O(m⋅n).
	*/
	public int minimumObstacles(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        int[][] dist = new int[rows][cols];
        
        for(int r = 0; r<rows; r++) {
        	Arrays.fill(dist[r], Integer.MAX_VALUE);
        }
        
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        Deque<int[]> dq = new ArrayDeque<>();
        dq.offerFirst(new int[] {0, 0});
        
        dist[0][0] = 0;
        
        while (!dq.isEmpty()) {
        	int[] current = dq.pollFirst();
        	
        	int r = current[0];
        	int c = current[1];
        	
        	for (int[] dir : directions) {
        		int nr = dir[0] + r;
        		int nc = dir[1] + c;
        		
        		if(nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
        			continue;
        		}
        		
        		// Cost is 0 for an empty cell,
                // 1 if we must remove an obstacle.
        		int moveCost = grid[nr][nc];
        		
        		int newDist = dist[r][c] + moveCost;
        		
        		// Relax the edge only if we found a cheaper path.
        		if(newDist < dist[nr][nc]) {
        			dist[nr][nc] = newDist;
        			
        			// Cost 0 should be processed immediately.
        			if(moveCost == 0) {
        				dq.offerFirst(new int[] {nr, nc});
        			} else {
        				// Cost 1 goes behind all current cheaper states.
        				dq.offerLast(new int[] {nr, nc});
        			}
        		}
        		
        	}
        }// while closed
        
        return dist[rows-1][cols-1];	
	
	}
	

}
