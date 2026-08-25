package Amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordSearch_II_212_Hard {

	public static void main(String[] args) {

	    char[][] board1 = {
	        {'o', 'a', 'a', 'n'},
	        {'e', 't', 'a', 'e'},
	        {'i', 'h', 'k', 'r'},
	        {'i', 'f', 'l', 'v'}
	    };
	    String[] words1 = {"oath", "pea", "eat", "rain"};

	    List<String> result1 = findWords(board1, words1);
	    Collections.sort(result1); // optional, for stable printing
	    System.out.println("Example 1: " + result1);

	    char[][] board2 = {
	        {'a', 'b'},
	        {'c', 'd'}
	    };
	    String[] words2 = {"abcb"};

	    List<String> result2 = findWords(board2, words2);
	    Collections.sort(result2);
	    System.out.println("Example 2: " + result2);
	}
	
	// solution: Trie + DFS/backtracking
	
	/* Step-by-Step Algorithm
		1. Build a Trie from all input words.
		2. Store the complete word at the terminal Trie node.
		3. Iterate through every board cell.
		4. Start DFS using the Trie root.
		5. During DFS:
			- Get the Trie child corresponding to the board character.
			- If no child exists, stop.
			- If the Trie node contains a word, add it to the result.
			- Temporarily mark the board cell as visited.
			- Explore up, down, left, and right.
			- Restore the board cell.
		6. Clear the stored word after adding it so duplicates aren't returned.
		7. Return the result. */
	
    /* 2-minute interview explanation script:

        I’m going to build a Trie containing all the input words, and then 
        run DFS from every cell in the board.

		During DFS, instead of blindly exploring every possible path, 
		I’ll use the Trie to check whether the characters formed so far 
		are still a prefix of any word.
		
		If the current board character doesn’t exist as a child of the 
		current Trie node, I can stop that DFS branch immediately because 
		no word can be formed from it.
		
		When I reach a Trie node that stores a complete word, I’ll 
		add it to the result.
		
		To prevent using the same board cell twice in one word, 
		I’ll temporarily mark the current cell as visited and restore 
		it during backtracking.
		
		I’ll also clear the word from the Trie node after finding it. 
		That prevents the same word from being added multiple times 
		if there are multiple paths that form it.
    */

    /* Complexity 
     Let, 
     	m = number of rows
		n = number of columns
		W = total number of characters across all words
		L = maximum word length
    
    Time Complexity: 
        Build Trie: O(W), because each character of each word is inserted once.
        
        DFS Search: O(m * n * 4^L), because we can start DFS from every cell 
        	and potentially explore four directions.

        In practice, it is significantly better because the Trie stops DFS 
        as soon as the current path is not a prefix of any word.

    Space Complexity: O(W + L)
        Trie: O(W)
        Recursion stack: O(L)
        Extra visited space: O(1)
        because we mark the board in-place with '#'
    */

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;    // non-null means a full word ends here
    }

    private static int rows;
    private static int cols;

    private static List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);

        rows = board.length;
        cols = board[0].length;

        List<String> result = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                dfs(board, r, c, root, result);
            }
        }

        return result;
    }

    private static void dfs(char[][] board, int r, int c,
            TrieNode parent,
            List<String> result) {

		// Out of bounds or already visited.
		if (r < 0 || r >= rows ||
		   c < 0 || c >= cols ||
		   board[r][c] == '#') {
		   return;
		}
		
		char ch = board[r][c];
		TrieNode node = parent.children[ch - 'a'];
		
		// Current path is not a prefix of any word.
		if (node == null) {
		   return;
		}
		
		// Found a complete word.
		if (node.word != null) {
		   result.add(node.word);
		
		   // Avoid adding the same word again.
		   node.word = null;
		}
		
		board[r][c] = '#';
		
		dfs(board, r + 1, c, node, result);
		dfs(board, r - 1, c, node, result);
		dfs(board, r, c + 1, node, result);
		dfs(board, r, c - 1, node, result);
		
		board[r][c] = ch;
	}


    private static TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();

        for (String word : words) {
            TrieNode current = root;

            for (char ch : word.toCharArray()) {
                int index = ch - 'a';

                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }

                current = current.children[index];
            }

            current.word = word;
        }

        return root;
    }

}
