package Facebook;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestDistanceFromAllBuildings_317_Hard {

	public static void main(String[] args) {
		
		ShortestDistanceFromAllBuildings_317_Hard obj = new ShortestDistanceFromAllBuildings_317_Hard();
		
		
		int[][] grid2 = new int[][] {
									{1,0,2,0,1},
									{0,0,0,0,0},
									{0,0,1,0,0}
								};
								
		int[][] grid = new int[][] {
			{1,0},
			{0,0}
			};
		int res = obj.shortestDistance(grid);
		System.out.println("result: "+res);
		
		// 2nd approach, same time and space complexity
		res = obj.shortestDistance2(grid);
		System.out.println("result: "+res);

	}
	
	
	
	/*
	 * Algorithm: 

		1. For each house cell (grid[i][j] equals 1), start a BFS:
			a. For each empty cell we reach, increase the cell's sum 
				of distances by the steps taken to reach the cell.
			b. For each empty cell we reach, also increment the 
				cell's house counter by 1.
				
		2. After traversing all houses, get the minimum distance 
		from all empty cells which have housesReached equal to totalHouses.
		
		3. If it is possible for all houses to reach a specific 
			empty land cell, then return the minimum distance found. 
			Otherwise, return -1.
	 */
	
	/*
	 * Time Complexity: O(N^2 * M^2),
			For each house, we will traverse across all reachable land. 
			This will require O(number of zeros * number of ones) time, 
			and the number of zeros and ones in the matrix is of order N * M. 
			
			Consider that when half of the values in the grid are 0 
			and half of the values are 1, the total elements in the 
			grid will be (M * N) so their counts are (M * N) / 2 
			and (M * N) / 2 respectively, thus giving a 
			time complexity of O(N^2 * M^2).
			
	   Space Complexity: 𝑂(𝑁⋅𝑀),
			We use an extra matrix to track the visited cells and 
			another one to store distance sum along with the house 
			counter for each empty cell, and the queue will store 
			each matrix element at most once during each BFS call. 
			
			Hence, 𝑂(𝑁⋅𝑀) space is required.
	 * */
	public static int shortestDistance(int[][] grid) {
        // Leetcode solution: BFS from Houses to Empty Land
        int minDistance = Integer.MAX_VALUE;
        int rows = grid.length;
        int cols = grid[0].length;
        int totalHouses = 0;

        // Store { total_dist, houses_count } for each cell.
        int[][][] distances = new int[rows][cols][2];

        // Count houses and start bfs from each house.
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if (grid[row][col] == 1) {
                    totalHouses++;
                    bfs(grid, distances, row, col);
                }
            }
        }

        // Check all empty lands with houses count 
        // equal to total houses and find the min ans.
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if(distances[row][col][1] == totalHouses) {
                    minDistance 
                        = Math.min(minDistance, distances[row][col][0]);
                }
            }
        }
        
        // If we haven't found a valid cell, return -1
        if(minDistance == Integer.MAX_VALUE) 
            return -1;

        return minDistance;
    }
    
    private static void bfs(int[][] grid, int[][][] distances, int row, int col) {
        int dirs[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        int rows = grid.length;
        int cols = grid[0].length;

        // Use a queue to do a bfs, starting from each 
        // cell located at (row, col).
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{row, col}); 
        
        // Keep track of visited cells.
        boolean[][] visited = new boolean[rows][cols];
        visited[row][col] = true;
        
        int steps = 0;
        
        while(!q.isEmpty()) {
            for (int i = q.size(); i > 0; --i) {
                int[] curr = q.poll();
                row = curr[0];
                col = curr[1];
                
                // If we reached an empty cell, then add the distance
                // and increment the count of houses reached at this cell.
                if(grid[row][col] == 0) {
                    distances[row][col][0] += steps;
                    distances[row][col][1] += 1;
                }
                
                // Traverse the next cells which is not a blockage.
                for(int[] dir : dirs) {
                    int nextRow = row + dir[0];
                    int nextCol = col + dir[1];
                    
                    //if nextRow and nextCol are out of bound
                    if(nextRow<0 || nextRow>=grid.length || nextCol<0 || nextCol>=grid[0].length) {
                        continue;
                    }
                    
                    if(!visited[nextRow][nextCol] && grid[nextRow][nextCol] == 0) {
                        visited[nextRow][nextCol] = true;
                        q.offer( new int[]{nextRow, nextCol} );
                    }
                    
                } // directions for closed
                
            } // for
            // After traversing one level cells, 
            // increment the steps by 1.
            steps++;
            
        } // while
    }
	
	
	
	
	/* Another approach - Working solution, same time and space complexity
	 * 
	 * Can be used in Interview too
	 * 
	 * Refered below links for solution: 
	 * 
	 * https://leetcode.com/problems/shortest-distance-from-all-buildings/discuss/76934/10ms-BFS-Java-solution-with-explanation
	 * https://leetcode.com/problems/shortest-distance-from-all-buildings/discuss/76891/Java-solution-with-explanation-and-time-complexity-analysis
	 * */
	
	/*
	 * Time Complexity: O(m^2×n^2) because each building triggers 
	 * a BFS over the grid, and there can be O(m×n) buildings.
	 * 
	 * Space Complexity: O(m×n) due to the distance, reach,
	 *  and visited arrays, which all have dimensions m×n.
	 * */
	
	int[][] distance; //count sum of distance from '0' to all building
	int[][] reach; //how many building each '0' can be reached;
	//int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
	private final int[] directions_X = {-1,1,0,0};
	private final int[] directions_Y = {0,0,-1,1};
	
	public int shortestDistance2(int[][] grid) {
		
		if(grid == null || grid.length == 0) return 0;
		
		int rows = grid.length;
		int cols = grid[0].length;
		
		distance = new int[rows][cols];
		reach = new int[rows][cols];
		
		int buildings = 0; //--count the number of buldngs in grid i.e. 1 in the grid means it's building
				
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(grid[r][c] == 1) {
					buildings++;
					bfs(grid, r, c);
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(grid[r][c] == 0 && reach[r][c] == buildings) {

					min = Math.min(min, distance[r][c]);
				}
			}
		}
		
		return min == Integer.MAX_VALUE ? -1 : min;
	}
	
	public void bfs(int[][] grid, int r, int c) {
		int rows = grid.length;
		int cols = grid[0].length;
		boolean[][] visited = new boolean[rows][cols];
		
		Queue<Point> q = new LinkedList<>();
		q.offer(new Point(r, c, 0));
		visited[r][c] = true;
		
		while (!q.isEmpty()) {
			
			Point curr = q.poll();
			
			for(int i=0; i<directions_X.length; i++) {
				int x = directions_X[i] + curr.x;
				int y = directions_Y[i] + curr.y;
				
				//if x and y are out of bound
				if(x<0 || x>=grid.length || y<0 || y>=grid[0].length) {
					continue;
				}
				
				//connect to building
				if(grid[x][y] == 1 && !visited[x][y]) {
					visited[x][y] = true;
				}
				
				//connect to spot
				if(grid[x][y] == 0 && !visited[x][y]) {
					q.offer(new Point(x,y, curr.distance+1));
					distance[x][y] += curr.distance + 1;
					reach[x][y]++;
					visited[x][y] = true;
				}
			}
			
		}
		
	}
	
	private class Point {
		int x;
		int y;
		int distance;
		Point(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
	}

}


/*
 * Question: 
 * 317. Shortest Distance from All Buildings - Hard
 * 
You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

Each 0 marks an empty land which you can pass by freely.
Each 1 marks a building which you cannot pass through.
Each 2 marks an obstacle which you cannot pass through.
Example:

Input: [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]

1 - 0 - 2 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0

Output: 7 

Explanation: Given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2),
             the point (1,2) is an ideal empty land to build a house, as the total 
             travel distance of 3+3+1=7 is minimal. So return 7.
Note:
There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
 * */

