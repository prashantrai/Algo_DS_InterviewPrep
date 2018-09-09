package test.practice.misc;

import java.util.Arrays;

public class ConnectedIslandsDemo {

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
	
            System.out.println("2nd:: "+countIslands2(M));
            
         
            
            
	}
	
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
