package Opendoor;

import java.util.Arrays;

public class GameOfLife_289_Medium {

	static int[][] b = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };

	static int[][] b2 = { { 0, 1, 0 }, { 1, 0, 0 }, { 1, 0, 1 }, { 0, 1, 0 } };

	public static void main(String[] args) {

		int i = 5;

		gameOfLife(b, i);
	}

	/* Opendoor
	 * 
	 * Implement GameOfLife and simulat to run for mulitple iteration
	 * 
	 * Working solution
	 */

	public static void gameOfLife(int[][] board, int iteration) {

		if (board == null || board.length == 0)
			return;

		for (int i = 0; i < iteration; i++) {
			helper(board, iteration);
			System.out.println("itration:" + i + "\nborad: " + Arrays.deepToString(board));

		}
	}

	final static int dirs[][] = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }, { -1, -1 }, { 1, -1 }, { 1, 1 }, { -1, 1 }

	};

	private static int[][] helper(int[][] board, int iter) {

		int rows = board.length;
		int cols = board[0].length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				if (board[i][j] == 1) {
					// count live neighbours
					int lives = count(board, i, j);
					if (lives < 2 || lives > 3) {
						board[i][j] = 2;
					}
				}

				if (board[i][j] == 0) {
					// count live neighbours
					int lives = count(board, i, j);
					if (lives == 3) {
						board[i][j] = -1;
					}
				}

			}

		} // for closed

		update(board);

		return board;

	}

	private static void update(int[][] b) {
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				if (b[i][j] == -1) {
					b[i][j] = 1;

				} else if (b[i][j] == 2) {
					b[i][j] = 0;
				}
			}
		}

	}

	private static int count(int[][] b, int r, int c) {

		int lives = 0;
		for (int[] dir : dirs) {
			int newRow = r + dir[0];
			int newCol = c + dir[1];

			if (newRow >= 0 && newRow < b.length && newCol >= 0 && newCol < b[0].length
					&& (b[newRow][newCol] == 1 || b[newRow][newCol] == 2)) {

				lives++;
			}

		}

		return lives;

	}

}
