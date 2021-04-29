package Intuit;
public class MaxAreaOfIsland_695_Medium {

	public static void main(String[] args) {

		//--Count=9 
		int M[][]=  new int[][] {
			 {1, 1, 1, 0, 0},
             {1, 1, 0, 0, 1},
             {1, 1, 0, 1, 1},
             {1, 0, 0, 0, 0},
             {1, 0, 1, 0, 1}
            };
           
        //--Count is 5 as they make the largest island together   
        int M2[][] =  new int[][] {
  			 	{1, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 1, 1}
              };   

           // Implement this in interview or with visited array (check with interviewer)
//           System.out.println("1. Expected: 9, Actual: "+maxAreaOfIsland(M));
//           System.out.println("1. Expected: 5, Actual: "+maxAreaOfIsland(M2));
             
              // with visited array
             System.out.println("2. Expected: 9, Actual: "+maxAreaOfIsland2(M));
             System.out.println("2. Expected: 5, Actual: "+maxAreaOfIsland2(M2));

//            System.out.println("3. Expected: 9, Actual: "+maxAreaOfIsland3(M));
//            System.out.println("3. Expected: 5, Actual: "+maxAreaOfIsland3(M2));
           
//           System.out.println("4. Expected: 9, Actual: "+maxAreaOfIsland4(M));
//           System.out.println("4. Expected: 5, Actual: "+maxAreaOfIsland4(M2));
		
	}

	
	/*
	 * https://leetcode.com/problems/max-area-of-island/
	 * 
	 * Complexity Analysis
	 * 	Time Complexity: O(R*C), where R is the number of rows in the given grid, and C is the number of columns. We visit every square once.
	 * 	Space complexity: O(R*C), the space used by seen to keep track of visited squares, and the space used by the call stack during our recursion.
	 */
	
	
	// implement this one in interview, ask interviewer if it's we need to preserve the matrix value
	// if s/he says yes then create visited matrix and initialize with ZERO or BOOLEAN FALSE
	
	// 2nd approach DFS - without instance variable
    public static int maxAreaOfIsland(int[][] m) {
        if(m == null || m.length == 0) return 0;
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                int area = helper(m, r, c);
                max = Math.max(max, area);
            }
        }
        return max;
    }
    
    private static int helper(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return 0;
        }
        
        m[r][c] = 99; // mark visited
        
        return 1 + helper(m, r, c+1) 
        		 + helper(m, r, c-1) 
        		 + helper(m, r+1, c) 
        		 + helper(m, r-1, c);
    }
    
    
    // with extra space - Visited matrix
    public static int maxAreaOfIsland2(int[][] m) {
        if(m == null || m.length == 0) return 0;
        
        boolean[][] visited = new boolean[m.length][m[0].length];
        
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                int area = helper2(m, r, c, visited);
                max = Math.max(max, area);
            }
        }
        return max;
    }
    
    private static int helper2(int[][] m, int r, int c, boolean[][] visited) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] == 0 || visited[r][c]) {
            return 0;
        }
        visited[r][c] = true;
        
        return 1 + helper2(m, r, c+1, visited) 
        		 + helper2(m, r, c-1, visited) 
        		 + helper2(m, r+1, c, visited) 
        		 + helper2(m, r-1, c, visited);
    }
	
	
	
	private static int area;
    public static int maxAreaOfIsland3(int[][] m) {
        if(m == null || m.length == 0) return 0;
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                if(m[r][c] == 1) {
	            	helper3(m, r, c);
	                max = max<area? area : max;
	                area = 0;
                }
            }
        }
        return max;
    }
    
    private static void helper3(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return;
        }
        
        area++;
        m[r][c] = 99; // mark visited
        
        helper3(m, r, c+1);
        helper3(m, r, c-1);
        helper3(m, r+1, c);
        helper3(m, r-1, c);
    }
    
    //approach 3
    public static int maxAreaOfIsland4(int[][] m) {
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
            	if(m[r][c] == 1) {
            		max = Math.max(max, helper4(m, r, c));
            	}
            }
        }
        return max;
    }
    
    private static int helper4(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return 0;
        }
        
        m[r][c] = 99; // mark visited
        int count = 1;
        
        count += helper4(m, r, c+1); 
        count += helper4(m, r, c-1); 
        count += helper4(m, r+1, c); 
        count += helper4(m, r-1, c);
        
        return count;
    }
}