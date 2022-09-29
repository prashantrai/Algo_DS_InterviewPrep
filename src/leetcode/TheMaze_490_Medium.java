package leetcode;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class TheMaze_490_Medium {

	public static void main(String[] args) {
		int[][] maze = {{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},{1,1,0,1,1},{0,0,0,0,0}}; 
		int[] start = {0,4}; 
		int[] destination = {4,4};
		
		System.out.println("Expected: true, Actual: " + hasPath(maze, start, destination));
	}
	
	/*
	 * For question: https://cheonhyangzhang.gitbooks.io/leetcode-solutions/content/solutions-451-500/490-the-maze.html
	 * 
    Time: O(m*n) Complete traversal of maze will be done in the worst case. 
    Here, m and n refers to the number of rows and columns of the maze.

	Space: O(m*n). visited array of size m∗n is used and queue size can grow up to m*n in worst case.
    */
    public static boolean hasPath(int[][] maze, int[] start, int[] destination) {
     
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int m = maze.length;
        int n = maze[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        
        q.offer(start);
        visited[start[0]][start[1]] = true;
        
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            
            //check for destination
            if(curr[0] == destination[0] && curr[1] == destination[1])
                return true;
            
            for(int[] dir : dirs) {
                int x = curr[0];
                int y = curr[1];
                
                // while empty space i.e. '0' keep rolling till it hits the wall
                while(isValid(maze, x + dir[0], y + dir[1])) {
                    x += dir[0];
                    y += dir[1];
                }
                
                if(!visited[x][y]) {
                    q.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
                
            }
        }
        
        return false;
    }
    
    private static boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && x<maze.length 
            && y >= 0 && y<maze[0].length 
            && maze[x][y] == 0;
    }
    
    // working but getting TLE
    // https://stackoverflow.com/questions/2627889/java-hashmap-with-int-array
    public boolean hasPath2(int[][] maze, int[] start, int[] destination) {
     
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int m = maze.length;
        int n = maze[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        Set<int[]> visited = new HashSet<>();
        
        q.offer(start);
        visited.add(start);
        
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            
            //check for destination
            if(curr[0] == destination[0] && curr[1] == destination[1])
                return true;
            
            for(int[] dir : dirs) {
                int x = curr[0];
                int y = curr[1];
                
                // while empty space i.e. '0' keep rolling
                while(isValid(maze, x, y)) {
                    x += dir[0];
                    y += dir[1];
                }
                x -= dir[0];
                y -= dir[1];
                
                if(visited.add(new int[]{x, y})) {
                    q.offer(new int[]{x, y});
                }
            }
        }
        
        return false;
    }

}
