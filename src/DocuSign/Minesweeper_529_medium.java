package DocuSign;

import java.util.Arrays;

public class Minesweeper_529_medium {

	public static void main(String[] args) {
		char[][] board = {
						{'E','E','E','E','E'},
						{'E','E','M','E','E'},
						{'E','E','E','E','E'},
						{'E','E','E','E','E'}};
		
		int[] click = {3,0};

		char[][] res = updateBoard(board, click);
		System.out.println(Arrays.deepToString(res));
	}

	
	

	/**
	 * Depth-first search (DFS)
	 *
	 * Time Complexity: O(8 * M * N) = O(M * N)
	 *
	 * Space Complexity: O(M * N)
	 *
	 * M = Number of rows. N = Number columns.
	 * 
	 * https://leetcode.com/problems/minesweeper/discuss/1524772/Java-or-TC%3A-O(MN)-or-SC%3A-O(M%2BN)-or-BFS-%2B-DFS-Solutions
	 */
	
	private static int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {1, -1}, {1, 1}, {-1, -1} };
	
	// dfs
	public static char[][] updateBoard(char[][] board, int[] click) {
        
		int rLen = board.length;
		int cLen = board[0].length;
		
		if(rLen == 0 || cLen == 0 || click.length != 2) return board;
		
		int x = click[0];
		int y = click[1];
		
		// If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
		if(board[x][y] == 'M') {
			board[x][y] = 'X';
		} else {
			dfs(board, x, y, rLen, cLen);
		}
		return board;
    }

	private static void dfs(char[][] board, int x, int y, int rLen, int cLen) {
		if(x < 0|| x >= rLen || y < 0 || y >= cLen || board[x][y] != 'E') 
			return;
		
		int mine = countMine(board, x, y, rLen, cLen);
		
		if(mine > 0) {
			board[x][y] = (char) ('0' + mine);
			
		} else {
			// If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed 
			// blank 'B' and all of its adjacent unrevealed squares should be revealed recursively.
			board[x][y] = 'B';
			for(int[] d : dirs) {
				dfs(board, x+d[0], y+d[1], rLen, cLen);
			}
		}
	}

	private static int countMine(char[][] board, int x, int y, int rLen, int cLen) {
		int count = 0;
		
		for(int[] dir : dirs){
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (nextX >= 0 && nextX < rLen && nextY >= 0 && nextY < cLen && board[nextX][nextY] == 'M')
                    count++;
        }
		
		return count;
	}
}
