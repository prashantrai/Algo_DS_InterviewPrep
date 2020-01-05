package leetcode;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {

	public static void main(String[] args) {
		
		char[][] board = { 
								{ '5', '3', '.', '.', '7', '.', '.', '.', '.' }, 
				                { '6', '.', '.', '1', '9', '5', '.', '.', '.' }, 
				                { '.', '9', '8', '.', '.', '.', '.', '6', '.' }, 
				                { '8', '.', '.', '.', '6', '.', '.', '.', '3' }, 
				                { '4', '.', '.', '8', '.', '3', '.', '.', '1' }, 
				                { '7', '.', '.', '.', '2', '.', '.', '.', '6' }, 
				                { '.', '6', '.', '.', '.', '.', '2', '8', '.' }, 
				                { '.', '.', '.', '4', '1', '9', '.', '.', '5' }, 
				                { '.', '.', '.', '.', '8', '.', '.', '7', '9' } 
                			};

		
		System.out.println(isValidSudoku(board));
		
	}

	
	/*
	 * https://www.youtube.com/watch?v=Pl7mMcBm2b8
	 * https://leetcode.com/problems/valid-sudoku/discuss/15472/
	 */
	public static boolean isValidSudoku(char[][] board) {
		
		HashSet<String> seen = new HashSet<>();
		
		for(int r=0; r<board.length; r++) {
			for(int c=0; c<board[0].length; c++) {
				String current_val = ""+board[r][c];
				if(board[r][c] != '.') {
					if(!seen.add(current_val +"found in row " + r)
							|| !seen.add(current_val +"found in col " + c)
							|| !seen.add(current_val +"found in grid " + r/3 + "-" + c/3)) {
						
						return false;
					} 
				}
			}
		}
		
		return true;
	}
	
}
