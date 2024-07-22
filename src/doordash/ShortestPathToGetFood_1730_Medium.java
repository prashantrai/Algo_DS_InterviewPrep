package doordash;

import java.util.ArrayDeque;
import java.util.Queue;

public class ShortestPathToGetFood_1730_Medium {

	public static void main(String[] args) {
		
		char[][] grid = {{'X','X','X','X','X','X'},
						{'X','*','O','O','O','X'},
						{'X','O','O','#','O','X'},
						{'X','X','X','X','X','X'}};

		System.out.println("Expected: 3, Actual: " + getFood(grid));
		
	}
	
	// Time: O(row * col)
	// Space: O(row * col)
	
	public static int getFood(char[][] grid) {
        int[][] dirs = {{0,-1}, {0,1}, {-1,0}, {1,0}};
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[rows][cols];
        
        int step = 0;

        // get the starting point
        int[] startPoint = new int[1];
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                if(grid[r][c] == '*')
                    startPoint = new int[]{r, c};
            }
        }
        
        q.offer(startPoint);
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                int[] currPos = q.poll();
                int x = currPos[0];
                int y = currPos[1];
                
                if(grid[x][y] == '#')
                    return step;
                
                for(int[] dir : dirs) {
                    int newX = x + dir[0];
				    int newY = y + dir[1];
                    
                    if(newX >=0 && newX < rows 
                       && newY >=0 && newY <cols 
                       && grid[newX][newY] != 'X'
                       && !visited[newX][newY]) {
                        
                        visited[newX][newY] = true;
                        q.offer(new int[]{newX, newY});
                    }
                }// for closed
            }// for closed
            
            step++;
            
        }// while closed
        
        return -1;
    }
    
	// not in use but can be used to find start point
    private int[] findStart(char[][] grid) {
        for(int i=0; i < grid.length; i++){
            for(int j=0; j < grid[0].length; j++){
                if(grid[i][j] == '*'){
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
    

}
