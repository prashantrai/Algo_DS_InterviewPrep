package test.practice.amazon;

public class WordSearch_79_Medium {

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
	// https://leetcode.com/problems/word-search/discuss/27658/Accepted-very-short-Java-solution.-No-additional-space.
	
	
	/* For Time Complexity: 
	 * 		https://leetcode.com/problems/word-search/discuss/27754/what-is-the-time-complexity-for-the-dfs-solution/26775
	 * 		https://leetcode.com/problems/word-search/discuss/27795/what-is-the-time-complexity-i-think-it-is-on2-4k-where-k-is-the-length-of-word
	 * 		a nice expaination : 
	 * 			https://cs.stackexchange.com/questions/96626/whats-the-big-o-runtime-of-a-dfs-word-search-through-a-matrix
	
	*/
	/* Time Complexity: O(n*m * 4^L) , n*m for nested loop and 4^L i.e. O(4^length_of_word)
	 * 
	 * How O(4^length_of_word)? : 
	 * 		The DFS solution is to search 4 directions beside the current cell.
	 * 		Imagine there's a tree, every node in this tree is a move on the board. 
	 * 		The root node in the tree is board(e.g.[0,0]="A"), and its four children(toward 4 directions)
	 * 
	 *  	So technically, this quadtree's height is L(where L is length of word) and its total node 
	 *  	number is 4^0 + 4^1 + ... + 4^L. So the time complexity of this dfs solution is O(4^L).
	 *  
	 *  
	 *  Refer: For Time Complexity: https://leetcode.com/problems/word-search/discuss/27754/what-is-the-time-complexity-for-the-dfs-solution/26775	
	 * 		
	 * 
	 * Space Complexity: O(4L) :  space complexity would be O(4L) as implicit recursive function calls 
	 * 							  on the stack.
	 * ---------------------------------------
	 * 
	 * https://cs.stackexchange.com/questions/96626/whats-the-big-o-runtime-of-a-dfs-word-search-through-a-matrix
	 * 
	 * 	The complexity will be 𝑂(𝑚∗𝑛∗4^s) where m is the no. of rows and n is the no. of columns in the 
	 *  2D matrix and s is the length of the input string. 
	 *  When we start searching from a character we have 4 choices of neighbors for the first character 
	 *  and subsequent characters have only 3 or less than 3 choices but we can take it 
	 *  as 4 (permissible slopiness in upper bound). This slopiness would be fine in large matrices. 
	 *  So for each character we have 4 choices. Total no. of characters are 𝑠 where s is the length 
	 *  of the input string. So one invocation of search function of your implementation would take 𝑂(4^s) 
	 *  time. Also in worst case the search is invoked for 𝑚∗𝑛 times. So an upper bound would be 𝑂(𝑚∗𝑛∗4^s).
	 * 
	 * */
	
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
