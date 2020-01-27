package test.practice.amazon;

public class WordSearch {

	public static void main(String[] args) {

		char[][] board = {
							{'a','b'}
						};
		
		System.out.println("Expected: true, Actual: " + exist(board, "ba"));
		
		char board_2 [][] =	{
				  {'A','B','C','E'},
				  {'S','F','C','S'},
				  {'A','D','E','E'}
				};
		
		
		System.out.println("Expected: true, Actual: " + exist(board_2, "ABCCED"));
		System.out.println("Expected: true, Actual: " + exist(board_2, "SEE"));
		System.out.println("Expected: false, Actual: " + exist(board_2, "ABCB"));
	}

	//-- https://www.youtube.com/watch?v=vYYNp0Jrdv0
	//-- https://leetcode.com/problems/word-search/
	//--Hard : Similar to this question : https://leetcode.com/problems/word-search-ii/
	
	public static boolean exist(char[][] board, String word) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				if (board[i][j] == word.charAt(0) && dfs(board, word, i, j, 0)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean dfs(char[][] board, String word, int i, int j, int count) {
		if (word.length() == count) {
			return true;
		}

		if (i < 0 || i >= board.length || j < 0 || j >= board[i].length || board[i][j] != word.charAt(count)) {
			return false;
		}

		char temp = board[i][j];
		board[i][j] = ' ';
		boolean isFound = dfs(board, word, i + 1, j, count + 1) 
							|| dfs(board, word, i - 1, j, count + 1)
							|| dfs(board, word, i, j + 1, count + 1) 
							|| dfs(board, word, i, j - 1, count + 1);

		board[i][j] = temp;
		return isFound;
	}

}
