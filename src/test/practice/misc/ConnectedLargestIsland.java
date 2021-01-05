package test.practice.misc;

public class ConnectedLargestIsland {
	
	
	//--Look at this for non-recursive solutions:: https://leetcode.com/articles/maximal-square/

	public static void main(String[] args) {

		//--Count=6 as if don't consider diagonal elements, count=5 if diagonal elements are considered as connected lands
		 int M[][]=  new int[][] {
			 {1, 1, 1, 0, 0},
             {1, 1, 0, 0, 1},
             {1, 1, 0, 1, 1},
             {1, 0, 0, 0, 0},
             {1, 0, 1, 0, 1}
            };
            
         //--Count is 4 as they make the largest island together   
         int M2[][] =  new int[][] {
   			 	{1, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 1, 1}
               };   

             //M = M2;  
            /*System.out.println(countIslands(M));
            
            for (int r=0; r<M.length; r++) {
            	System.out.println(Arrays.toString(M[r]));
    		}*/
	
            //System.out.println("2nd:: "+countIslands(M));
            System.out.println("2nd [countIslands2] :: "+countIslands2(M));
            
	}
	
	/*
	 * Complexity Analysis
	 * 	Time Complexity: O(R*C), where R is the number of rows in the given grid, and C is the number of columns. We visit every square once.
	 * 	Space complexity: O(R*C), the space used by seen to keep track of visited squares, and the space used by the call stack during our recursion.
	 */

	

	
	

	//--Returns the largest connected nuber of islands : Working solution
	private static int countIslands(int[][] m) {

		if(m.length == 0) return 0;
		int max = 0;
		
		for(int r=0; r<m.length; r++) {
			for(int c=0; c<m[0].length; c++) {
				 
				int count = helper(m, r, c, 0);
				max = max < count ? count : max;
				
			}
		}
		
		return max;
	}
	
	
	public static int helper(int[][] m, int r, int c, int count) {
		
		if(r < 0 || r >= m.length || c < 0 || c >= m[0].length || m[r][c] != 1 ) {
			return count;
		}
		
		count++;
		m[r][c] = 99;
		
		count = helper(m,r,c+1, count);
		count = helper(m,r,c-1, count);
		count = helper(m,r-1,c, count);
		count = helper(m,r+1,c, count);
		
		return count;
		
	}
	
	// 2nd approach
	/*
	 * Complexity Analysis
	 * 	Time Complexity: O(R*C), where R is the number of rows in the given grid, and C is the number of columns. We visit every square once.
	 * 	Space complexity: O(R*C), the space used by seen to keep track of visited squares, and the space used by the call stack during our recursion.
	 */
	private static int area;
	private static int countIslands2(int[][] m) {

		if(m.length == 0) return 0;
		int max = 0;
		
		for(int r=0; r<m.length; r++) {
			for(int c=0; c<m[0].length; c++) {
				helper(m, r, c);
				max = max < area ? area : max;
				area = 0;
			}
		}
		
		return max;
	}
	
	public static void helper(int[][] m, int r, int c) {
		
		if(r < 0 || r >= m.length || c < 0 || c >= m[0].length || m[r][c] != 1 ) {
			return;
		}
		area++;
		m[r][c] = 99;
		
		helper(m, r, c+1);
		helper(m, r, c-1);
		helper(m, r+1, c);
		helper(m, r-1, c);
	}
 
}
