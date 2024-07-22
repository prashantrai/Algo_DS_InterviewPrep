package doordash;

public class CountSubIslands_1905_Medium {

	public static void main(String[] args) {
		int[][] grid1 = {{1,1,1,0,0},{0,1,1,1,1},{0,0,0,0,0},{1,0,0,0,0},{1,1,0,1,1}}; 
		int[][] grid2 = {{1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}};
		
		System.out.println("Expected: 3, Actual: " + countSubIslands(grid1, grid2));
	}
	
	/*
    Refer: https://leetcode.com/problems/count-sub-islands/discuss/1284319/JavaC%2B%2BPython-DFS-Solution
    
    Time Complexity: O(m×n)
    Space Complexity: O(m×n) in the worst case due to the recursion stack.
    */
    
    public static int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        int count = 0;
        
        // Iterate through each cell in grid2
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                // If we find a land cell in grid2, we start a DFS
                if(grid2[i][j] == 1) {
                    // If the DFS returns true, it means the island 
                    // is a sub-island
                    if(dfs(grid1, grid2, i, j)) 
                        count++;
                    
                }
            }
        }
        
        return count;
    }

    private static boolean dfs(int[][] grid1, int[][] grid2, int i, int j) {
        int m = grid1.length; 
        int n = grid1[0].length;

        // Boundary checks or if the cell is water in grid2
        if(i<0 || i>=m || j<0 || j>=n || grid2[i][j] == 0)
            return true;
        
        // Mark the cell as visited in grid2
        grid2[i][j] = 0;
        
        // Start assuming this is a valid sub-island if 
        // the corresponding cell in grid1 is land
        boolean isSubIsland = grid1[i][j] == 1;
        
        // Recursively visit all four adjacent cells and combine results
        boolean up    = dfs(grid1, grid2, i-1, j);
        boolean down  = dfs(grid1, grid2, i+1, j);
        boolean left  = dfs(grid1, grid2, i, j-1);
        boolean right = dfs(grid1, grid2, i, j+1);
        
        // The island is a sub-island if the current cell is land in grid1
        // and all adjacent cells return true
        return isSubIsland && up && down && left && right;
        
    }
    
    
    
    
    /* Similar approach just little dfs implementation  
     * Refer: https://leetcode.com/problems/count-sub-islands/discuss/1284319/JavaC%2B%2BPython-DFS-Solution
     * */
    public int countSubIslands2(int[][] B, int[][] A) {
        int m = A.length, n = A[0].length, res = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (A[i][j] == 1)
                    res += dfs2(B, A, i, j);
        return res;
    }


    private int dfs2(int[][] B, int[][] A, int i, int j) {
        int m = A.length; 
        int n = A[0].length;
        int res = 1;
        
        if (i < 0 || i == m || j < 0 || j == n || A[i][j] == 0) return 1;
        
        A[i][j] = 0; // mark visited
        
        res &= dfs2(B, A, i - 1, j);
        res &= dfs2(B, A, i + 1, j);
        res &= dfs2(B, A, i, j - 1);
        res &= dfs2(B, A, i, j + 1);
        
        return res & B[i][j];
    }
    

}
