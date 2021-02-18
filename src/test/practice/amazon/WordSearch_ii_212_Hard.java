package test.practice.amazon;

import java.util.ArrayList;
import java.util.List;

public class WordSearch_ii_212_Hard {

	//-- Hard: https://leetcode.com/problems/word-search-ii/submissions/
	
	public static void main(String[] args) {

		char[][] board = {
				  {'o','a','a','n'},
				  {'e','t','a','e'},
				  {'i','h','k','r'},
				  {'i','f','l','v'}
			  };
			String[] words = {"oath","pea","eat","rain"};

			System.out.println("Expected: [\"eat\",\"oath\"]");
			System.out.println("Actual: " + findWords(board, words));
		
	}
	
	//-- This solution is O(n^3), find a better solution may be use trie to store words and then search
	public static List<String> findWords(char[][] board, String[] words) {
        
        List<String> result = new ArrayList<String>();
        for(String word : words) {
            if(exist(board, word)) {
                result.add(word);
            }
        }
        return result;
    }
    
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
