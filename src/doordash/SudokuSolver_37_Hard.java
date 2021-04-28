package doordash;

import java.util.HashSet;
import java.util.Set;

public class SudokuSolver_37_Hard {

	public static void main(String[] args) {

		
	}
	
	public static void solveSudoku(char[][] board) {
		
		if(board == null || board.length == 0)
            return;
		
		solve(board);
	}
	
	/*
	 * Here, the time complexity and branching factor of dfs is 9, 
	 * and the depth is the number of'.' m, so the time complexity is O(9^m) and 
	 * the space complexity is O(9*m) = O(m).
	 * 
	 * Time Complexity - O(9^m)， Space Complexity - O(m), where m is the number of '.'
	 * 
	 * https://www.programcreek.com/2014/05/leetcode-sudoku-solver-java/
	 * https://www.cnblogs.com/yrbbest/p/4436325.html
	 * 
	 * Reference: https://leetcode.com/problems/sudoku-solver/discuss/15752/Straight-Forward-Java-Solution-Using-Backtracking
	 * */
	private static boolean solve(char[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				// if not filled
				if(board[i][j] != '.') continue;
					
                // try putting from 1 to 9 and check if the board is valid
                for(char c='1'; c<='9'; c++) {
                    if(isValid(board, i, j, c)) {
                        board[i][j] = c; //put c in the current cell
                        //recurse
                        if(solve(board)) // dfs
                            return true;
                        else 
                            board[i][j] = '.'; // otherwise go back - backtrack
                    }
                 }
                 return false;
			}
		}
		return true;
	}
	
	private static boolean isValid(char[][] board, int r, int c, char ch) {
		int blockRow = 3 * (r/3); // i is the row. Block no. is i/3, first element is (i/3)*3
		int blockCol = 3 * (c/3); // j is the col
		
		for(int i=0; i<9; i++) {
			if(board[i][c] == ch) return false; // check row
			if(board[r][i] == ch) return false; // check column
			
			if(board[blockRow + i/3][blockCol + i%3] == ch) return false; // check 3*3 block
		}
		return true;
	}
	
	
	/*
	 * Backtracking (easy to understand very straight forward with comments): 
	 * https://leetcode.com/problems/sudoku-solver/discuss/341766/Best-Backtrack-Solution-Java
	 * https://leetcode.com/problems/sudoku-solver/discuss/693002/Java-Easy-to-understandread-code
	 * 
	 * DFS: https://leetcode.com/problems/sudoku-solver/discuss/15911/Less-than-30-line-clean-java-solution-using-DFS
	 * */
	
	// Taken from : https://leetcode.com/problems/sudoku-solver/discuss/693002/Java-Easy-to-understandread-code
	public void solveSudoku2(char[][] board) {
        Set<String> set = new HashSet<>();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] == '.') continue;
                addToSet(set, i, j, board[i][j]);
            }
        }
        backtrack(board, set);
    }

    private boolean backtrack(char[][] board, Set<String> set) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] != '.') continue;
                for(char k = '1'; k <= '9'; k++) {
                    if(!isValid(set, i, j, k)) continue;
                    board[i][j] = k;
                    addToSet(set, i, j, k);
                    if(backtrack(board, set)) return true;
                    removeFromSet(set, i, j, k);
                    board[i][j] = '.';
                }
                return false;
            }
        }
        return true;
    }

    private boolean isValid(Set<String> set, int i, int j, char k) {
        if(set.contains("" + k + "row-" + i)) return false; 
        if(set.contains("" + k + "col-" + j)) return false;           
        if(set.contains("" + k + "block-" + (i / 3) + (j / 3))) return false; 
        return true;

    }
    
    private void addToSet(Set<String> set, int i, int j, char k) {
        set.add("" + k + "row-" + i);
        set.add("" + k + "col-" + j);
        set.add("" + k + "block-" + (i / 3) + (j / 3));
    }
    
    private void removeFromSet(Set<String> set, int i, int j, char k) {
        set.remove("" + k + "row-" + i);
        set.remove("" + k + "col-" + j);
        set.remove("" + k + "block-" + (i / 3) + (j / 3));
    }
	
}
