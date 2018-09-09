package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


	/*
		Eight Queens: Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
		so that none of them share the same row, column, or diagonal. In this case, "diagonal" means all
		diagonals, not just the two that bisect the board.
		
		Input: GRID_SIZE=4 and row=0
		Output: 
				1st Possible arrangement : [1, 3, 0, 2]
						Here each index of array is row index and value is column index 
						(e.g r=0, c=1; r=1, c=3; r=2, c=0 ad r=3, c=2)
						
				2nd Possible arrangement : [2, 0, 3, 1]
	* */

public class EightQueensDemo {

	public static void main(String[] args) {

		int row = 0;
		
		ArrayList<Integer[]> result = new ArrayList<Integer[]>();
		//for(int i=0; i<GRID_SIZE; i++) {
		
		Integer[] columns = Collections.nCopies(GRID_SIZE, 0).toArray(new Integer[0]);  //--Initializing with ZERO
		placeQueens(row, columns, result);
		
		
		System.out.println(">>RESULT:: "+result);
		
		for (Integer[] arr : result) {
			System.out.println(">> "+ Arrays.deepToString(arr));
		}
		
	}
	
	final static int GRID_SIZE=4;
	
	public static void placeQueens(int row, Integer[] columns, ArrayList<Integer[]> result) {
		
		if(row == GRID_SIZE) {
			result.add(columns.clone());
		} else {
			
			for(int col = 0; col<GRID_SIZE; col++) {
				
				if(isValidColumn(row, col, columns)) {
					columns[row] = col; //--Place queen : adding valid col num to array
					placeQueens(row+1, columns, result); //check for next row
				}
			}
			
		}
		
	}

	/* Check if (row1, column1) is a valid spot for a queen by checking if there is a
	* queen in the same column or diagonal. We don't need to check it for queens in
	* the same row because the calling placeQueen only attempts to place one queen at
	* a time. We know this row is empty. 
	*/
	private static boolean isValidColumn(int row1, int column1, Integer[] columns) {
		
		for(int row2=0; row2<row1; row2++) {
			
			int column2 = columns[row2];
			
			if(column1 == column2) { //--when same column return
				return false;
			}
			
			
			/* Check diagonals: if the distance between the columns equals the distance
			 * between the rows, then they're in the same diagonal. */
			
			int colDistance = Math.abs(column2 - column1);
			int rowDistance = row1 - row2; 
			
			if(rowDistance == colDistance) {
				return false;
			}
			
		}
		
		return true;
	}

	
	
}
