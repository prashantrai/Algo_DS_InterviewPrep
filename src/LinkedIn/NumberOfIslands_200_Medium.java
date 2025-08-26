package LinkedIn;

public class NumberOfIslands_200_Medium {

	public static void main(String[] args) {

		 char M[][]=  new char[][] {
			 {'1', '1', '0', '0', '0'},
             {'0', '1', '0', '0', '1'},
             {'1', '0', '0', '1', '1'},
             {'0', '0', '0', '0', '0'},
             {'1', '0', '1', '0', '1'}
            };
          System.out.println("Expected: 6, Actual: "+numIslands(M));   
           
         char M2[][] =  new char[][] {
   			 	{'1', '1', '0', '0'},
                {'0', '1', '0', '0'},
                {'0', '0', '0', '1'},
                {'1', '0', '1', '1'}
               };   

               System.out.println("Expected: 3, Actual: "+numIslands(M2));
            
	}
	
	
	/*
	 * Complexity Analysis
    	Time complexity : O(M×N) where M is the number of rows and N is the number of columns.
    	Space complexity : worst case O(M×N) in case that the grid map is filled with lands where 
    					DFS goes by M×N deep.
	 * 
	 * Runtime complexity Explanation: 
	 * The time complexity is O(cells). Every cell is inspected at least once, due to the nested for loops. Any single cell is inspected at most 5 times. We know this because there are 5 ways a cell (i, j) can be inspected:

		inspected in the nested for loop, before dfs is called
		dfs from cell (i + 1, j)
		dfs from cell (i - 1, j)
		dfs from cell (i, j + 1)
		dfs from cell (i, j - 1)
		The nested for loops obviously inspect each cell exactly once.
		
		dfs(i, j) exits immediately if (i, j) has been inspected already, which implies (i, j) can only be visited from dfs(i + 1, j) once, visited from dfs(i - 1, j) once, visited from dfs(i, j + 1) once, and visited from (i, j - 1) once.
	*/
	
	public static int numIslands(char[][] m) {
        
        if(m == null || m.length == 0) return 0;
        
        int count = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                if(m[r][c] == '1') {
                    helper(m, r, c);
                    count++;
                }
            }
        }
        return count;
    }
    
    private static void helper(char[][] m, int r, int c) {
        
        if(r >= m.length || r < 0 || c >= m[0].length || c<0 || m[r][c] != '1') {
            return;
        }
        m[r][c] = '2'; // visited
        helper(m, r, c+1);
        helper(m, r, c-1);
        helper(m, r+1, c);
        helper(m, r-1, c);
    }

}
