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

           System.out.println("Expected: 9, Actual: "+maxAreaOfIsland(M));
           System.out.println("Expected: 5, Actual: "+maxAreaOfIsland(M2));
		
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
                helper(m, r, c);
                max = max<area? area : max;
                area = 0;
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
	
}
