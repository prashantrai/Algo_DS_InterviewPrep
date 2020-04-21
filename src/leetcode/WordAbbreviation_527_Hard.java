package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordAbbreviation_527_Hard {

	public static void main(String[] args) {
		
		/*
		 Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
		Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
		 * */

		//List<String> dict = Arrays.asList("like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion");
		List<String> dict = Arrays.asList("internal", "internet", "interval", "intension", "intrusion");

		List<String> res = wordsAbbreviation(dict);
		
		System.out.println(res);
		
		
	}

	/*
	 * https://leetcode.com/problems/word-abbreviation/discuss/99792/HashMap-%2B-Trie-greater-O(nL)-solution
	 * 
	 * 1. Iterate the dict and create the abbreviation for each word and group them
	 * together for similar abbreviation For example: "internal", "interval" =>
	 * grouped by "i6l" and "intension", "intrusion" => grouped by "i7n" "god" =>
	 * grouped by "god"
	 * 
	 * we can notice that only words with the same length and the same start and end
	 * letter could be grouped together.
	 * 
	 * 2. Resolve conflict/collision: To resolve conflicts in a group, we could
	 * 
	 * build a trie for all the words in the group. The trie node will contain the
	 * count of words that has the same prefix. Then we could use this trie to
	 * determine when we could resolve a conflict by identifying that the count of
	 * words in that trie node will only have one word has the prefix.
	 * 
	 */
	public static List<String> wordsAbbreviation(List<String> dict) {

		// setting initial capacity equal to dict size and initializing with NULL
		List<String> res = new ArrayList<>(Collections.nCopies(dict.size(), null));  

		Map<String, List<Integer>> abbrMap = new HashMap<>();

		// --add abbreviation to map and save all the position of dict index for the
		// words with common prefix
		for (int i = 0; i < dict.size(); i++) {
			String word = dict.get(i);
			String abbr = getAbbreviation(word);
			if (!abbrMap.containsKey(abbr)) {
				abbrMap.put(abbr, new ArrayList<Integer>());
			}
			abbrMap.get(abbr).add(i); // --add the index for current word i.e. position index in dict
		}

		// Resolve conflict/collision
		for (Map.Entry<String, List<Integer>> entrySet : abbrMap.entrySet()) {
			String abbr = entrySet.getKey();
			List<Integer> pos = entrySet.getValue();
			resolve(dict, abbr, pos, res);
		}

		return res;
	}

	/**
	 * To resolve conflicts in a group, we could build a trie for all the words in
	 * the group. The trie node will contain the count of words that has the same
	 * prefix. Then we could use this trie to determine when we could resolve a
	 * conflict by identifying that the count of words in that trie node will only
	 * have one word has the prefix.
	 */
	
	private static void resolve(List<String> dict, String abbr, List<Integer> pos, List<String> res) {

		if (pos.size() == 1) {
			res.set(pos.get(0), abbr);
			return;
		} else {
			Trie trie = buildTrie(dict, pos);

			for (int p : pos) {
				String word = dict.get(p);
				int wordLen = word.length();

				Trie curr = trie;
				int i = 0;

				/*
				 * While i < n and count of each node > 1 i.e. if the count is not 1 that means
				 * the char is common between more words at same index (i.e. collision) so we
				 * have to iterate further till we get a count 1 that means the if the count is
				 * 1 then that char is unique to the current word only.
				 */
				while (i < wordLen && curr.children.get(word.charAt(i)).count > 1) {
					curr = curr.children.get(word.charAt(i));
					i++;
				}

				if (i >= wordLen - 3) { // 3 char word, so add the same word i.e. not the abbr
					res.set(p, word);
				} else {
					String pre = word.substring(0, i + 1);
					String last = "" + word.charAt(wordLen - 1);

					String s = pre + (wordLen - i - 2) + last;
					res.set(p, s);
				}

			}

		}
	}

	//--build trie
	private static Trie buildTrie(List<String> dict, List<Integer> pos) {
		Trie root = new Trie();
		
		for(int p : pos) {
			String word = dict.get(p);
			Trie curr = root;

			for (char c : word.toCharArray()) {
				if(!curr.children.containsKey(c)) { // --if not available then add child
					curr.children.put(c, new Trie());
				}
				curr = curr.children.get(c); // child is now current node
				curr.count++;
			}
		}
		return root;
	}

	private static String getAbbreviation(String word) {

		if (word.length() <= 3) {
			return word;
		}

		String abbr = word.charAt(0) + "" + (word.length() - 2) + "" + word.charAt(word.length() - 1);

		return abbr;
	}

	// --Trie node
	static class Trie {
		int count = 0;
		Map<Character, Trie> children = new HashMap<>();
	}
}

/*
 * Question: 527. Word Abbreviation - Hard
 * 
 * Given an array of n distinct non-empty strings, you need to generate minimal
 * possible abbreviations for every word following rules below.
 * 
 * 1. Begin with the first character and then the number of characters abbreviated,
 * which followed by the last character. 
 * 
 * 2. If there are any conflict, that is more
 * than one words share the same abbreviation, a longer prefix is used instead
 * of only the first character until making the map from word to abbreviation
 * become unique. In other words, a final abbreviation cannot map to more than
 * one original words. 
 * 
 * 3. If the abbreviation doesn't make the word shorter, then
 * keep it as original. 
 * 
 * Example: Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"] 
 * 
 * Output:
 * ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
 * 
 */



