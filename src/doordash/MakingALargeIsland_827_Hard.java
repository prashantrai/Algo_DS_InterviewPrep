package doordash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MakingALargeIsland_827_Hard {

	public static void main(String[] args) {
		int[][] grid = {{1,0},{0,1}};
		System.out.println("Expected: 3, Actual: " + largestIsland(grid));
		
	}
	
	// Refer: https://leetcode.com/problems/making-a-large-island/discuss/127015/Paint-the-picture/134932
	// Time: O(n^2)
    // Space: O(n^2)
    
    public static int largestIsland(int[][] grid) {
        Map<Integer, Integer> map = paintTheGrid(grid);
        int n = grid.length;
        
        // If there is no island 0 from grid, 
        // res should be the size of islands of first color
        // If there is no island 1 from grid, res should be 0 
        int res = map.getOrDefault(2, 0); 
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                if (grid[r][c] == 0) {
                    //We use a set to avoid repeatly adding islands with the same color
                    Set<Integer> set = new HashSet<>();
                    // If current island is at the boundary, we add 0 to the set, 
                    // whose value is 0 in the map
                    set.add(r > 0 ? grid[r-1][c] : 0);
                    set.add(r < n-1 ? grid[r+1][c] : 0);
                    set.add(c > 0 ? grid[r][c-1] : 0);
                    set.add(c < n-1 ? grid[r][c+1] : 0);
                    
                    //We need to count current island as well, hence we init newSize with 1
                    int newSize = 1; 
                    for(int color : set) {
                        newSize += map.get(color);
                    }
                    res = Math.max(res, newSize);
                }
            }
        }

        return res;
    }
    
    // paint the grid, i.e., assign a color (number 2 and above) to each cell we visit 
    // for each iterarion in all directions (top, left, bottom, right) by performing DFS
    // similar to floodFill, algoritm that we folllow for problems like find the 
    // largest island
    private static Map<Integer, Integer> paintTheGrid(int[][] grid) {
        //Key: color, Val: size of island painted of that color
        Map<Integer, Integer> map = new HashMap<>(); 
        //We won't paint island 0, hence make its size 0, we will use this value later
        map.put(0,0); 
        
        int n = grid.length; 
        int color = 2; //0 and 1 is already used in grid, hence we start colorIndex from 2 
        
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                if(grid[r][c] == 1) {
                    // travers, paint and return the area
                    int size = paint(grid, r, c, color); // dfs
                    map.put(color, size);
                    color++;
                }
            }
        }
        
        return map;
    }
    
    //DFS, Helper method to paint current island and all its connected neighbors
    //Return the size of all painted islands at the end
    private static int paint(int[][] grid, int i, int j, int color) {
        if (i < 0 || i >= grid.length 
            || j < 0 || j >= grid[0].length 
            || grid[i][j] != 1) return 0;
        
        grid[i][j] = color;
        return 1 + paint(grid, i + 1, j, color) 
            + paint(grid, i - 1, j, color) 
            + paint(grid, i, j + 1, color) 
            + paint(grid, i, j - 1, color);
    }

}
