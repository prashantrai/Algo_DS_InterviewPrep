package Lyft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#'). For each character they type except '#', you need to return the top 3 historical hot sentences that have prefix the same as the part of sentence already typed. 

Here are the specific rules:
1. The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
2. The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first).
3. If less than 3 hot sentences exist, then just return as many as you can.
4. When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.

Your job is to implement the following functions:

The constructor function:

AutocompleteSystem(String[] sentences, int[] times): This is the constructor. The input is historical data. Sentences is a string array consists of previously typed sentences. Times is the corresponding times a sentence has been typed. Your system should record these historical data.

Now, the user wants to input a new sentence. The following function will provide the next character 
the user types:
List<String> input(char c): The input c is the next character typed by the user. The character will only be lower-case letters ('a' to 'z'), blank space (' ') or a special character ('#'). Also, the previously typed sentence should be recorded in your system. The output will be the top 3 historical hot sentences that have prefix the same as the part of sentence already typed.

Example:
Operation: AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2])
The system have already tracked down the following sentences and their corresponding times:
"i love you" : 5 times
"island" : 3 times
"ironman" : 2 times
"i love leetcode" : 2 times
Now, the user begins another search:

Operation: input('i')
Output: ["i love you", "island","i love leetcode"]
Explanation:
There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.

Operation: input(' ')
Output: ["i love you","i love leetcode"]
Explanation:
There are only two sentences that have prefix "i ".

Operation: input('a')
Output: []
Explanation:
There are no sentences that have prefix "i a".

Operation: input('#')
Output: []
Explanation:
The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.

Note:
The input sentence will always start with a letter and end with '#', and only one blank space will exist between two words.
The number of complete sentences that to be searched won't exceed 100. The length of each sentence including those in the historical data won't exceed 100.
Please use double-quote instead of single-quote when you write test cases even for a character input.
Please remember to RESET your class variables declared in class AutocompleteSystem, as static/class variables are persisted across multiple test cases.  
 * */

/*
 * See for question and solutions with different approach : 
 * 		https://github.com/awangdev/LintCode/blob/master/Java/Design%20Search%20Autocomplete%20System.java
 * 		https://cheonhyangzhang.gitbooks.io/leetcode-solutions/642-design-search-autocomplete-system.html
 * 		https://segmentfault.com/a/1190000016975256
 * 		https://massivealgorithms.blogspot.com/2017/07/leetcode-642-design-search-autocomplete.html
 * 		https://www.acwing.com/solution/LeetCode/content/9992/
 *                     
 * 
 * Below solution taken from: 
 * 			https://happygirlzt.com/code/642.html
 * More Soulitions:  https://happygirlzt.com/codelist.html
 * https://leetfree.com/problems/design-search-autocomplete-system.html
 * 
 * One more video tutorial: https://www.lavivienpost.com/autocomplete-with-trie-video/
 * 
 * */

public class AutocompleteSystem_642_Hard {

	public static void main(String[] args) {
		String[] sentences = { "i love you", "island", "ironman", "i love leetcode" };
		int[] times = { 5, 3, 2, 2 };
		char c = 'i';

		AutocompleteSystem_642_Hard obj = new AutocompleteSystem_642_Hard(sentences, times);
		List param_1 = obj.input(c);

		System.out.println("Res:: " + param_1);
	}

	TrieNode root;
	TrieNode cur;
	StringBuilder sb;

	public AutocompleteSystem_642_Hard(String[] sentences, int[] times) {

		root = new TrieNode();
		cur = root;
		sb = new StringBuilder();

		for (int i = 0; i < times.length; i++) {
			add(sentences[i], times[i]);
		}
	}

	public void add(String sentence, int t) {
		TrieNode tmp = root;

		List<TrieNode> visited = new ArrayList<>();
		for (char c : sentence.toCharArray()) {
			if (tmp.children[c] == null) {
				tmp.children[c] = new TrieNode();
			}
			tmp = tmp.children[c];
			visited.add(tmp);
		}

		tmp.s = sentence; // --set the sentence at the end node
		tmp.times += t; // --update the count at the end of sentence

		// --update hot list
		for (TrieNode node : visited) {
			node.update(tmp);
		}
	}

	public List<String> input(char c) {
		List<String> res = new ArrayList<>(); // --to contain list hot words/sentences
		if (c == '#') {
			add(sb.toString(), 1);
			sb = new StringBuilder();
			cur = root;
			return res;
		}

		sb.append(c);
		if (cur != null) {
			cur = cur.children[c];
		}

		if (cur == null)
			return res;

		// --Get the hot list of sentences and return
		for (TrieNode node : cur.hot) {
			res.add(node.s);
		}

		return res;
	}

	// --Trie Node
	class TrieNode implements Comparable {

		TrieNode[] children;
		String s;
		int times;
		List<TrieNode> hot;

		public TrieNode() {
			children = new TrieNode[128];
			s = null;
			times = 0;
			hot = new ArrayList<>();
		}

		@Override
		public int compareTo(Object o) {
			TrieNode node = (TrieNode) o;
			if (node.times == this.times) {
				return this.s.compareTo(node.s);
			}

			return node.times - this.times;
		}

		public void update(TrieNode node) {

			// --Node is not part of hot list of word then add it
			if (!this.hot.contains(node)) {
				this.hot.add(node);
			}

			// --sort the hot list and if the list size is greater than 3 then remove the
			// last element
			Collections.sort(hot);

			if (hot.size() > 3) {
				hot.remove(hot.size() - 1);
			}
		}

	}

}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times); List
 * param_1 = obj.input(c);
 */
