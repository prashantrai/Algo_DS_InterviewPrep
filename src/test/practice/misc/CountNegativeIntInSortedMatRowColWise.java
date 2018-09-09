package test.practice.misc;

public class CountNegativeIntInSortedMatRowColWise {

	public static void main(String[] args) {

		int[][] mat = {
						{-3, -2, -1, 1},
						{-2,  2,  3, 4},
						{ 4,  5,  7, 8},
					   };
		
		countNegIntInMat(mat);
	}
	
	//--Count negative integer row and col wise in a sorted matrix
	//--O(r+c)
	public static void countNegIntInMat(int[][] mat) {
		
		if(mat == null || mat.length == 0) return;
		
		int r = 0;
		int c = mat[0].length-1;
		
		while(c >= 0) {
			
			if(mat[r][c] >= 0) {
				c--;
				continue;
			}
			
			/**
			 * Because each row is sorted so when encounter the first negative from back (as we are traversing back in col) 
			 * count will be colNum+1 in current row 
			 * */
			System.out.println("Count of negetive num in row "+r+" is : "+ (c+1));    
			r++;
		}
		
	}

}
