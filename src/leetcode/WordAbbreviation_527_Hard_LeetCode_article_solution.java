package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordAbbreviation_527_Hard_LeetCode_article_solution {

	public static void main(String[] args) {

		/*
		 * Input: ["like", "god", "internal", "me", "internet", "interval", "intension",
		 * "face", "intrusion"] Output:
		 * ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
		 */

		// List<String> dict = Arrays.asList("like", "god", "internal", "me",
		// "internet", "interval", "intension", "face", "intrusion");
		List<String> dict = Arrays.asList("internal", "internet", "interval", "intension", "intrusion");

		List<String> res = wordsAbbreviation(dict);

		System.out.println(res);

	}

	public static List<String> wordsAbbreviation(List<String> words) {
		Map<String, List<IndexedWord>> groups = new HashMap();
		String[] ans = new String[words.size()];

		int index = 0;
		for (String word : words) {
			String ab = abbrev(word, 0);
			if (!groups.containsKey(ab))
				groups.put(ab, new ArrayList());
			groups.get(ab).add(new IndexedWord(word, index));
			index++;
		}

		for (List<IndexedWord> group : groups.values()) {
			TrieNode trie = new TrieNode();
			for (IndexedWord iw : group) {
				TrieNode cur = trie;
				for (char letter : iw.word.substring(1).toCharArray()) {
					if (cur.children[letter - 'a'] == null)
						cur.children[letter - 'a'] = new TrieNode();
					cur.count++;
					cur = cur.children[letter - 'a'];
				}
			}

			for (IndexedWord iw : group) {
				TrieNode cur = trie;
				int i = 1;
				for (char letter : iw.word.substring(1).toCharArray()) {
					if (cur.count == 1)
						break;
					cur = cur.children[letter - 'a'];
					i++;
				}
				ans[iw.index] = abbrev(iw.word, i - 1);
			}
		}

		return Arrays.asList(ans);
	}

	public static String abbrev(String word, int i) {
		int N = word.length();
		if (N - i <= 3)
			return word;
		return word.substring(0, i + 1) + (N - i - 2) + word.charAt(N - 1);
	}

	public static int longestCommonPrefix(String word1, String word2) {
		int i = 0;
		while (i < word1.length() && i < word2.length() && word1.charAt(i) == word2.charAt(i))
			i++;
		return i;
	}
}

class TrieNode {
	TrieNode[] children;
	int count;

	TrieNode() {
		children = new TrieNode[26];
		count = 0;
	}
}

class IndexedWord {
	String word;
	int index;

	IndexedWord(String w, int i) {
		word = w;
		index = i;
	}
}

/*
 * Question: 527. Word Abbreviation - Hard
 * 
 * Given an array of n distinct non-empty strings, you need to generate minimal
 * possible abbreviations for every word following rules below.
 * 
 * 1. Begin with the first character and then the number of characters
 * abbreviated, which followed by the last character.
 * 
 * 2. If there are any conflict, that is more than one words share the same
 * abbreviation, a longer prefix is used instead of only the first character
 * until making the map from word to abbreviation become unique. In other words,
 * a final abbreviation cannot map to more than one original words.
 * 
 * 3. If the abbreviation doesn't make the word shorter, then keep it as
 * original.
 * 
 * Example: Input: ["like", "god", "internal", "me", "internet", "interval",
 * "intension", "face", "intrusion"]
 * 
 * Output:
 * ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
 * 
 */
