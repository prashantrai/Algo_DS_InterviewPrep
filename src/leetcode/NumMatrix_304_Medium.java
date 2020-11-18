package leetcode;

public class NumMatrix_304_Medium {

	public static void main(String[] args) {
		
		int[][] matrix = {
		          {3, 0, 1, 4, 2},
		          {5, 6, 3, 2, 1},
		          {1, 2, 0, 1, 5},
		          {4, 1, 0, 1, 7},
		          {1, 0, 3, 0, 5}
		        };

		
		NumMatrix numMatrix = new NumMatrix(matrix);
		numMatrix.sumRegion(2, 1, 4, 3); //-> 8
		numMatrix.sumRegion(1, 1, 2, 2); //-> 11
		numMatrix.sumRegion(1, 2, 2, 4); //-> 12

	}
	
	static class NumMatrix {

	    public NumMatrix(int[][] matrix) {
	        
	    }
	    
	    public int sumRegion(int row1, int col1, int row2, int col2) {
	        
	    }
	}

}
