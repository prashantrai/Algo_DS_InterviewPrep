package test.lyft;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
 """
Given two words (beginWord and endWord), and a dictionary's word list, 
find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

Note:
Return -1 if there is no such transformation sequence.
All words in the dictionary contain only lowercase alphabetic characters.
Words in the dictionary may or may not be the same length.

"""

"""
wordList = ["hot","dot","dog","lot","log","cog","cat","hat"]

Input:
beginWord = "hot",
endWord = "dog",
Output: 2
Explanation: As one shortest transformation is "hot" -> "dot" -> "dog",
return its length 2.

Input:
beginWord = "cat",
endWord = "dog",
Output: 4
Explanation: As one shortest transformation is "cat" -> "hat" -> "hot" -> "dot" -> "dog",
return its length 4.

"""
 */

public class WordLadder_Lyft_127_Medium {
	public static void main(String[] args) {
		Set<String> dict = new HashSet<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog", "cat", "hat"));

		int res = findEndWord(dict, "hot", "dog");
		System.out.println("Expected: 2, Actual: " + res);

		int res2 = findEndWord(dict, "cat", "dog");
		System.out.println("Expected: 2, Actual: " + res2);

		int res3 = findEndWord(dict, "cat", "");
		System.out.println("Expected: -1, Actual: " + res3);

		int res4 = findEndWord(dict, "cat", "xxx");
		System.out.println("Expected: -1, Actual: " + res4);
	}

	private static class Word {
		String word;
		int step;

		public Word(String w, int step) {
			this.word = w;
			this.step = step;
		}

	}

	// BFS
	public static int findEndWord(Set<String> dict, String beginWord, String endWord) {

		// --sanity check
		if (beginWord == null || endWord == null || beginWord.isEmpty() || endWord.isEmpty() || dict.isEmpty()) {

			return -1;
		}

		LinkedList<Word> q = new LinkedList<>();
		q.addLast(new Word(beginWord, 0));

		HashSet<String> visited = new HashSet<>();

		while (!q.isEmpty()) {

			Word top = q.removeFirst();
			String currWord = top.word;

			if (currWord.equals(endWord)) {
				return top.step;
			}

			char[] arr = currWord.toCharArray();

			visited.add(currWord);
			for (int i = 0; i < arr.length; i++) {
				for (char c = 'a'; c <= 'z'; c++) {

					char temp = arr[i];
					if (arr[i] != c) {
						arr[i] = c;
					}

					String newWord = new String(arr);
					
					if (visited.contains(currWord)) {
						continue;
					}

					if (dict.contains(newWord)) {
						q.addLast(new Word(newWord, top.step + 1));
					}
					arr[i] = temp;
				}

			}

		}

		return -1;

	}

}
