package ctci.ch1.arr.and.str;

import java.util.Arrays;

public class ZeroMatrixDemo {


	/*
	  
1 0 1 1 0
0 1 1 1 0
1 1 1 1 1 	  
1 0 1 1 1 	  
1 1 1 1 1	  


0 0 0 0 0
0 0 0 0 0
0 0 1 1 0 	  
0 0 0 0 0 	  
0 0 1 1 0	  

{
{1, 0, 1, 1, 0},
{0, 1, 1, 1, 0},
{1, 1, 1, 1, 1},  
{1, 0, 1, 1, 1}, 	  
{1, 1, 1, 1, 1},
}



 0  2  3  4  
 6  7  7  0  
11 0  13 14
15 16  0 18  


rowHasZoero = true
columnHasZero = true
 0  0   0  0  
 0  0   7  0  
 0  0  13 14
 0 16   0 18   

	  
[	
	[0, 0, 0, 0, 0], 
	[0, 0, 0, 0, 0], 
	[1, 1, 1, 1, 1], 
	[0, 0, 0, 0, 0], 
	[1, 1, 1, 1, 1]
]	  
	  
	  
	  
	 * */
	
	
	public static void main(String[] args) {

		Integer [][] matrix = 	{
				{0, 2, 3},
				{4, 0, 6},
				{7, 8, 0}
			};
		
		Integer[][] matrix_2 = {
					{1, 0, 1, 1, 0},
					{0, 1, 1, 1, 0},
					{1, 1, 1, 1, 1},  
					{1, 0, 1, 1, 1}, 	  
					{1, 1, 1, 1, 1},
				};
		
		System.out.println(">> Input :: " + Arrays.deepToString(matrix_2));
		
		//System.out.println(matrix.length);
		//System.out.println(matrix[0].length);
		
		setZeros_2(matrix_2);
		
	}
	
	
	public static void setZeros_2(Integer[][] mat) {
		
		boolean firstRowHasZero = false;
		boolean firstColHasZero = false;
		
		/** Check if first row and col has zero */
		//--check row
		for(int c = 0; c<mat[0].length; c++) {
			if(mat[0][c] == 0) {
				firstRowHasZero = true;
			}
		}
		
		//--check col
		for(int r=0; r<mat.length; r++) {
			if(mat[r][0] == 0) {
				firstColHasZero = true;
			}
		}
		
		/** Iterate through the rest of matric (except first row and col) and if any zero found mark the element to zero 
		 *  in first row and col at respective index.
		 *  
		 *  i.e. If mat[i][j] has zero then mark mat[0][c] and mat[r][0] to zero
		 * */
		
		
		/*
		 * Input: 
		 			{1, 0, 1, 1, 0},
					{0, 1, 1, 1, 0},
					{1, 1, 1, 1, 1},  
					{1, 0, 1, 1, 1}, 	  
					{1, 1, 1, 1, 1}
		 Output: 
		  
		 * [1, 0, 1, 1, 0], 
		 * [0, 1, 1, 1, 0], 
		 * [1, 1, 1, 1, 1], 
		 * [0, 0, 1, 1, 1], 
		 * [1, 1, 1, 1, 1] */
		
		for(int r=1; r<mat.length; r++) {
			for(int c=1; c<mat[r].length; c++) {
				
				if(mat[r][c] == 0) {
					mat[0][c] = 0;
					mat[r][0] = 0;
				}
			}
		}
		
		System.out.println(">>1.0 :: "+Arrays.deepToString(mat));
		
		
		/** Iterate through the rest of the matrix and mark whole row and col to zero if there is any
		 * zero found in that row or column 
		 * */
		
		
		//--nullify row to zero except first row
		for(int r=1; r<mat.length; r++) {
			if(mat[r][0] == 0)
				nulliyRow_2(mat, r);
		}
		
		System.out.println(">>3.0 Nullify row to zero:: "+Arrays.deepToString(mat));

		
		/*input: 
		 * [1, 0, 1, 1, 0], 
		 * [0, 1, 1, 1, 0], 
		 * [1, 1, 1, 1, 1], 
		 * [0, 0, 1, 1, 1], 
		 * [1, 1, 1, 1, 1] */
		
		
		/* Output:[
		 * [1, 0, 1, 1, 0], 
		 * [0, 0, 1, 1, 0], 
		 * [1, 0, 1, 1, 0], 
		 * [0, 0, 1, 1, 0], 
		 * [1, 0, 1, 1, 0]]
		 * */
		
		//--nullify cols to zero except first col
		for(int c=1; c<mat[0].length; c++) {
			if(mat[0][c] == 0)
				nullifyCol_2(mat, c);
		}
		
		System.out.println(">>2.0 Nullify col to zero:: "+Arrays.deepToString(mat));
		
		//--nullify first row
		if(firstRowHasZero) {
			nulliyRow_2(mat, 0);
		}
		//--nullify first col
		if(firstColHasZero) {
			nullifyCol_2(mat, 0);
		}
		
		/*[[0, 0, 0, 0, 0], 
		  [0, 0, 0, 0, 0], 
		  [0, 0, 1, 0, 1], 
		  [0, 0, 1, 0, 1], 
		  [0, 0, 0, 0, 0]]
		  
		  Result :: [
		  [0, 0, 0, 0, 0], 
		  [0, 0, 0, 0, 0], 
		  [0, 0, 1, 1, 0], 
		  [0, 0, 0, 0, 0], 
		  [0, 0, 1, 1, 0]]
		  */
		
		System.out.println(">> Result :: "+Arrays.deepToString(mat));
		
	}
	
	
	private static void nulliyRow_2(Integer[][] mat, int index) {
		for(int j=0; j<mat[index].length; j++) {
			mat[index][j] = 0;
		}
		
	}


	private static void nullifyCol_2(Integer[][] mat, int index) {
		for(int j=0; j<mat.length; j++) {
			mat[j][index] = 0;
		}
	}


	public static void setZeors(Integer[][] mat) {
		
		boolean colHasZero = false;
		boolean rowHasZero = true;
		
		/**
		 *  Check if the first row and first column have any zeros, and set variables rowHasZero and
			columnHasZero. (We'll nullify the first row and first column later, if necessary.)
		
		{1, 0, 1, 1, 0},
		{0, 1, 1, 1, 0},
		{1, 1, 1, 1, 1},  
		{1, 0, 1, 1, 1}, 	  
		{1, 1, 1, 1, 1},
		
		*
		*/

		//--check if the first column has zero
		for(int row=0; row<mat.length; row++) {
			if(mat[row][0] == 0) {
				colHasZero = true;
				break;
			}
		}
		
		//--check if the first row has zero
		for(int col=0; col<mat[0].length; col++) {
			if(mat[0][col] == 0) {
				rowHasZero = true;
				break;
			}	
		}
		
		//--check for zero in rest of the array and set matrix[row][0] and matrix[0][col] 
		//--to zero whenever there is zero in matrix 
		/*
		 * Input: 
		 	{1, 0, 1, 1, 0},
			{0, 1, 1, 1, 0},
			{1, 1, 1, 1, 1},  
			{1, 0, 1, 1, 1}, 	  
			{1, 1, 1, 1, 1}, 
		 * 
		 *  Output of below for loop (looks good): 
		 *  [1, 0, 1, 1, 0], 
		 	[0, 1, 1, 1, 0], 
		 	[1, 1, 1, 1, 1], 
		 	[0, 0, 1, 1, 1], 
		 	[1, 1, 1, 1, 1]]
		 	
		 	*/
		
		for(int row=1; row<mat.length; row++) {
			for(int col=1; col<mat[row].length; col++) {
				if(mat[row][col] == 0) {
					mat[row][0] = 0;
					mat[0][col] = 0;
				}
			}
		}
		
		System.out.println(">> 1 :: " + Arrays.deepToString(mat));
		
		//--nullify row based on the value in first column
		/*
		 * Input: 
		 	[1, 0, 1, 1, 0], 
		 	[0, 1, 1, 1, 0], 
		 	[1, 1, 1, 1, 1], 
		 	[0, 0, 1, 1, 1], 
		 	[1, 1, 1, 1, 1]]
		 
		 Output : [
		 			[1, 0, 1, 1, 0], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 1, 1, 1, 1], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 1, 1, 1, 1]
		 		]
		 * */
		for(int row=0; row<mat.length; row++) {
			
			if(mat[row][0] == 0) {
				nullifyRow(row, mat);
			}
		}
		
		System.out.println(">> 2 : After nullyfying row  :: " + Arrays.deepToString(mat));
		
		//--nullify column based on the value in first row
		/*
		 * Input: [
		 			[1, 0, 1, 1, 0], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 1, 1, 1, 1], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 1, 1, 1, 1]
		 		]
		 		
		 			[1, 0, 1, 1, 0], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 0, 1, 1, 0], 
		 			[0, 0, 0, 0, 0], 
		 			[1, 0, 1, 1, 0]
		 		
		 		[1, 0, 1, 1, 0], 
		 		[0, 0, 0, 0, 0], 
		 		[1, 1, 1, 1, 1],
		 		[0, 0, 0, 0, 0], 
		 		[0, 0, 0, 0, 0]]
		 		
		 		
		 * */
		for(int col=0; col<mat.length; col++) {
			if(mat[0][col] == 0) {
				nullifyCol(col, mat);
			}
		}
		
		System.out.println(">> 3 : After nullyfying col  :: " + Arrays.deepToString(mat));
		
		
		//--nullify first row
		if(rowHasZero) {
			nullifyRow(0, mat);
		}
		
		//--nullify first col
		if(colHasZero) {
			nullifyCol(0, mat);
		}
		
		
		
		/* RESULT: Looks good
		 [
		 	[0, 0, 0, 0, 0], 
		 	[0, 0, 0, 0, 0], 
		 	[0, 0, 1, 1, 0], 
		 	[0, 0, 0, 0, 0], 
		 	[0, 0, 1, 1, 0]
		 ] 
		 * */
		
		System.out.println(">> Result :: " + Arrays.deepToString(mat));
		
	}
	
	private static void nullifyCol(int curr_col, Integer[][] mat) {
		
		for(int j=0; j<mat.length; j++) {
			mat[j][curr_col] = 0;
		}
	}


	public static void nullifyRow(int curr_row, Integer[][] mat) {
		for(int i=0; i<mat[curr_row].length; i++) {
			mat[curr_row][i] = 0;
		}
	}
	
	
}


