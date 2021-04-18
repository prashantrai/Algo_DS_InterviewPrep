package doordash;
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

//           System.out.println("1. Expected: 9, Actual: "+maxAreaOfIsland(M));
//           System.out.println("1. Expected: 5, Actual: "+maxAreaOfIsland(M2));
           
           System.out.println("2. Expected: 9, Actual: "+maxAreaOfIsland2(M));
           System.out.println("2. Expected: 5, Actual: "+maxAreaOfIsland2(M2));
           
//           System.out.println("3. Expected: 9, Actual: "+maxAreaOfIsland3(M));
//           System.out.println("3. Expected: 5, Actual: "+maxAreaOfIsland3(M2));
		
	}

	
	/*
	 * https://leetcode.com/problems/max-area-of-island/
	 * 
	 * Complexity Analysis
	 * 	Time Complexity: O(R*C), where R is the number of rows in the given grid, and C is the number of columns. We visit every square once.
	 * 	Space complexity: O(R*C), the space used by seen to keep track of visited squares, and the space used by the call stack during our recursion.
	 */
	
	private static int area;
    public static int maxAreaOfIsland(int[][] m) {
        if(m == null || m.length == 0) return 0;
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                if(m[r][c] == 1) {
	            	helper(m, r, c);
	                max = max<area? area : max;
	                area = 0;
                }
            }
        }
        return max;
    }
    
    private static void helper(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return;
        }
        
        area++;
        m[r][c] = 99; // mark visited
        
        helper(m, r, c+1);
        helper(m, r, c-1);
        helper(m, r+1, c);
        helper(m, r-1, c);
    }
	
    
    // 2nd approach DFS - without instance variable
    public static int maxAreaOfIsland2(int[][] m) {
        if(m == null || m.length == 0) return 0;
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
                int area = helper2(m, r, c);
                max = max<area? area : max;
                area = 0;
            }
        }
        return max;
    }
    
    private static int helper2(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return 0;
        }
        
        m[r][c] = 99; // mark visited
        
        return 1 + helper2(m, r, c+1) 
        		 + helper2(m, r, c-1) 
        		 + helper2(m, r+1, c) 
        		 + helper2(m, r-1, c);
    }
    
    
    //approach 3
    public static int maxAreaOfIsland3(int[][] m) {
        int max = 0;
        for(int r=0; r<m.length; r++) {
            for(int c=0; c<m[0].length; c++) {
            	if(m[r][c] == 1) {
            		max = Math.max(max, helper3(m, r, c));
            	}
            }
        }
        return max;
    }
    
    private static int helper3(int[][] m, int r, int c) {
        
        if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] != 1) {
            return 0;
        }
        
        m[r][c] = 99; // mark visited
        int count = 1;
        
        count += helper3(m, r, c+1); 
        count += helper3(m, r, c-1); 
        count += helper3(m, r+1, c); 
        count += helper3(m, r-1, c);
        
        return count;
    }
}