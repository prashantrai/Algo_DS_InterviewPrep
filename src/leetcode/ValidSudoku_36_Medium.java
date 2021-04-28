package leetcode;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku_36_Medium {

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
	
	/* Algorithm:: https://leetcode.com/problems/valid-sudoku/discuss/15472/Short%2BSimple-Java-using-Strings
	 *  
	 * Collect the set of things we see, encoded as strings. 
	 * For example:

		'4' in row 7 is encoded as "(4)7".
		'4' in column 7 is encoded as "7(4)".
		'4' in the top-right block is encoded as "0(4)2".
	
	   Scream FALSE if we ever fail to add something because it was already added (i.e., seen before).
	*/
	
	public static boolean isValidSudoku(char[][] board) {
	    Set<String> seen = new HashSet<>();
	    
	    for (int i=0; i<9; ++i) {
	        for (int j=0; j<9; ++j) {
	            
	        	if (board[i][j] != '.') {
	                
	        		String b = "(" + board[i][j] + ")"; // creating value string, e.g. 4 will be (4)
	                
	        		if (!seen.add(b + i) 
	        				|| !seen.add(j + b) 
	        				|| !seen.add(i/3 + b + j/3))
	                    return false;
	            }
	        }
	    }
	    return true;
	}

	
	/* https://leetcode.com/problems/valid-sudoku/discuss/15472/Short%2BSimple-Java-using-Strings
	 * https://www.youtube.com/watch?v=Pl7mMcBm2b8
	 * https://leetcode.com/problems/valid-sudoku/discuss/15472/
	 * 
	 * https://leetcode.com/problems/valid-sudoku/
	 */
	public static boolean isValidSudoku2(char[][] board) {
		
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
