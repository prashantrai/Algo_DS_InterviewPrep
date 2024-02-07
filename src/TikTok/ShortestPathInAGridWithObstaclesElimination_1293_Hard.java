package TikTok;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInAGridWithObstaclesElimination_1293_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    References: 
    GitHub: (used this in submiting the code)
        https://github.com/Algorithms-Made-Easy/Leetcode-Challenge/blob/main/1293.%20Shortest%20Path%20in%20a%20Grid%20with%20Obstacles%20Elimination
    YouTube: https://www.youtube.com/watch?v=ID9YJXy3OJk&ab_channel=AlgorithmsMadeEasy
    
    An Other Approach: 
        https://github.com/Sunchit/Coding-Decoded/blob/master/September2021/ShortestPathinaGridwithObstaclesElimination.java
        
    YouTube: https://www.youtube.com/watch?v=ywljsnzUS1w&ab_channel=CodingDecoded
    
    
    Time and Space: O(MNK)
    */
    // BFS
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length ;

        int[][] DIR = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        // visited array
        boolean[][][] visited = new boolean[m][n][k+1];
        Queue<int[]> q = new LinkedList<>();
        //Queue<int[]> q = new ArrayDeque<>();
        
        int steps = 0;
        
        q.offer(new int[]{0,0,k});
        
        while (!q.isEmpty()) {
            int size = q.size();
            while(size-- > 0) {
                int[] curr = q.poll();
                
                //If curr is the destination; return steps
                if(curr[0] == m-1 && curr[1] == n-1) 
                    return steps;
                
                //Else go in all valid directions
                for(int[] d : DIR) {
                    int i = curr[0] + d[0];
                    int j = curr[1] + d[1];
                    int obs = curr[2];
                    
                    //Traverse through the valid cells
                    if(i >=0 && i < m && j >= 0 && j < n) { // if cell/grid is valid
                        // if cell is empty (i.e. 0) visit an add in queue
                        if(grid[i][j] == 0 && !visited[i][j][obs]) {
                            q.offer(new int[]{i,j,obs});
                            visited[i][j][obs] = true;
                        }
                        // when grid is 1 and we can cross that obstacle, and we have
                        // obstacle balance left and also position is not visited
                        else if(grid[i][j] == 1 && obs > 0 && !visited[i][j][obs-1]) {
                            q.offer(new int[]{i,j,obs-1});
                            visited[i][j][obs-1] = true;
                        }
                    }
                }
                
            }
            steps++;
        }
        return -1;
        
    }

}
