package leetcode;

public class DesignTicTacToe_348_Medium {

	/*
	 * http://buttercola.blogspot.com/2016/06/leetcode-348-design-tic-tac-toe.html
	 * https://medium.com/@ojusmilindsave/tutorial-to-implement-tic-tac-toe-in-java-ad639661a9b
	 * https://www.programcreek.com/2014/05/leetcode-tic-tac-toe-java/
	 * 
	 * https://github.com/chihungyu1116/leetcode-javascript/blob/master/348.%20Design%20Tic-Tac-Toe.java
	 */
	
	
	/**
	 * Design a Tic-tac-toe game that is played between two players on a n x n grid.

	 * You may assume the following rules:

	 * A move is guaranteed to be valid and is placed on an empty block.
	 * Once a winning condition is reached, no more moves is allowed.
	 * A player who succeeds in placing n of their marks in a horizontal, vertical, 
	 * or diagonal row wins the game.
	 * 
	 * Example:
	 * Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.

	 * TicTacToe toe = new TicTacToe(3);

	 * toe.move(0, 0, 1); -> Returns 0 (no one wins)
	 * |X| | |
	 * | | | |    	 * Player 1 makes a move at (0, 0).
	 * | | | |

	 * toe.move(0, 2, 2); -> Returns 0 (no one wins)
	 * |X| |O|
	 * | | | |    	 * Player 2 makes a move at (0, 2).
	 * | | | |

	 * toe.move(2, 2, 1); -> Returns 0 (no one wins)
	 * |X| |O|
	 * | | | |    	 * Player 1 makes a move at (2, 2).
	 * | | |X|

	 * toe.move(1, 1, 2); -> Returns 0 (no one wins)
	 * |X| |O|
	 * | |O| |    	 * Player 2 makes a move at (1, 1).
	 * | | |X|

	 * toe.move(2, 0, 1); -> Returns 0 (no one wins)
	 * |X| |O|
	 * | |O| |    	 * Player 1 makes a move at (2, 0).
	 * |X| |X|

	 * toe.move(1, 0, 2); -> Returns 0 (no one wins)
	 * |X| |O|
	 * |O|O| |    	 * Player 2 makes a move at (1, 0).
	 * |X| |X|

	 * toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
	 * |X| |O|
	 * |O|O| |    	 * Player 1 makes a move at (2, 1).
	 * |X|X|X|
	 * Follow up:
	 * Could you do better than O(n^2) per move() operation?
	 * 
	 */
	
	public static void main(String[] args) {
		TicTacToe toe = new TicTacToe(3);
		int result = 0;
		
		result = toe.move(0, 0, 1);
		if(result == 0 )
			result = toe.move(0, 2, 2);
		if(result == 0 )
			result = toe.move(2, 2, 1);
		if(result == 0 )
			result = toe.move(1, 1, 2);
		if(result == 0 )
			result = toe.move(2, 0, 1);
		if(result == 0 )
			result = toe.move(1, 0, 2);
		if(result == 0 )
			result = toe.move(2, 1, 1);
		
		System.out.println(result);
	}
	
	public static class TicTacToe {
		
		int[] rows;
		int[] cols;
		int diag; 	//--diagonal
		int xdiag;	//--forward/2nd diagonal
		int n; //--board size
		
		public TicTacToe(int n) {
			this.n = n;
			rows = new int[n];
			cols = new int[n];
		}
		
		/** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
		public int move(int r, int c, int player) {
			
			int val = player == 1 ? 1 : -1;
			
			rows[r] += val;
			cols[c] += val;
			
			if(r == c) { //--are we in first diagonal
				diag += val;
			}
			
			if(c == n-1-r) { //--are we in 2nd/forward diagonal
				xdiag += val;
			}
			
			if(Math.abs(rows[r]) == 3 
					|| Math.abs(cols[c]) == 3 
					|| Math.abs(diag) == 3
					|| Math.abs(xdiag) == 3) {
				
				return player;
			}
			return 0;
			
		}
	}
	
}