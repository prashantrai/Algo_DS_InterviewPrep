package test.practice.misc;

import java.util.Arrays;

public class ConnectedIslandsDemo {
	
	//--Look at this for non-recursive solutions:: https://leetcode.com/articles/maximal-square/

	public static void main(String[] args) {

		//--Count=6 as if don't consider diagonal elements, count=5 if diagonal elements are considered as connected lands
		 int M[][]=  new int[][] {
			 {1, 1, 0, 0, 0},
             {0, 1, 0, 0, 1},
             {1, 0, 0, 1, 1},
             {0, 0, 0, 0, 0},
             {1, 0, 1, 0, 1}
            };
            
         int M2[][] =  new int[][] {
   			 	{1, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 1, 1}
               };   

             M = M2;  
            /*System.out.println(countIslands(M));
            
            for (int r=0; r<M.length; r++) {
            	System.out.println(Arrays.toString(M[r]));
    		}*/
	
            System.out.println("2nd:: "+countIslands(M));
            
         
            
            
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
	
	//--Runtime : O(row*col)
	public static int countIslands(int[][] m) {
		
		if(m.length == 0) return 0;
		
		int i = m.length;
		int j = m[0].length;
		int count = 0;
		
		for (int r=0; r<i; r++) {
			for (int c=0; c<j; c++) {
				
				if(m[r][c] == 1) {
					count++;
					countIslandHelper(m, r, c);
				}
			}
		}
		return count;
	}
	
	public static void countIslandHelper (int[][] m, int r, int c) {
		
		int i = m.length;
		int j = m[0].length;
		
		if(r<0 || r>=i || c<0 || c>=j || m[r][c] != 1) {
			return;
		}
		
		m[r][c] = 99;  //--This is needed change the 1 to some other value to identify already visited cell
				
		countIslandHelper(m, r, c-1);
		countIslandHelper(m, r, c+1);
		countIslandHelper(m, r-1, c);
		countIslandHelper(m, r+1, c);
		
		//--If diagonals are considered connected lands
		countIslandHelper(m, r-1, c-1);
		countIslandHelper(m, r-1, c+1);
		countIslandHelper(m, r+1, c-1);
		countIslandHelper(m, r+1, c+1);
		
	}
	

}
