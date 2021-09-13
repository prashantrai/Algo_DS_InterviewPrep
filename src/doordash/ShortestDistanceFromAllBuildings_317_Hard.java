package doordash;

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

	}
	
	/* Refered below links for solution: 
	 * 
	 * https://leetcode.com/problems/shortest-distance-from-all-buildings/discuss/76934/10ms-BFS-Java-solution-with-explanation
	 * https://leetcode.com/problems/shortest-distance-from-all-buildings/discuss/76891/Java-solution-with-explanation-and-time-complexity-analysis
	 * */
	
	int[][] distance; //count sum of distance from '0' to all building
	int[][] reach; //how many building each '0' can be reached;
	//int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
	private final int[] directions_X = {-1,1,0,0};
	private final int[] directions_Y = {0,0,-1,1};
	
	public int shortestDistance(int[][] grid) {
		
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

