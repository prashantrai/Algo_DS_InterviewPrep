package Oracle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class OccurrencesOfEachTermInAString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* Interview Script:: 
	 Since regex isn't allowed, I'll implement substring matching manually. 
	 For each term, I'll scan the input string from left to right. At every 
	 possible starting index, I'll compare characters one by one with the term. 
	 If every character matches, I'll increment the count.

	 I'll store the results in a LinkedHashMap because it preserves insertion order, 
	 which matches the order of the terms array.

	 This solution is straightforward, easy to verify, and counts overlapping 
	 occurrences as well. The time complexity is proportional to the number of 
	 terms times the input length times the average term length, which is acceptable 
	 unless the number of terms is extremely large. 
	 
	 If that were the case, we could discuss Trie or Aho-Corasick as an optimization.
	 * */
	
	/* Step-by-Step Algorithm
	 	- Create a LinkedHashMap.
		- Initialize every term with count 0.
		- If input string is null or empty, return the map.
		- For every term:
			Scan every possible starting index.
			Compare characters one by one.
			If all characters match, increment the count.
		- Return the map.
	 * */
	
	// Time: O(T × N × L)
	// Space: O(T)
	static class TermCounter_LinkedHashMap {

	    public static Map<String, Integer> count(String text, String[] terms) {

	        // LinkedHashMap preserves the same order as the terms array.
	        Map<String, Integer> result = new LinkedHashMap<>();

	        // Initialize all terms with count 0.
	        for (String term : terms) {
	            result.put(term, 0);
	        }

	        // Problem statement says null/blank input should return all zeros.
	        if (text == null || text.length() == 0) {
	            return result;
	        }

	        // Count each term independently.
	        for (String term : terms) {

	            // Skip invalid terms safely.
	            if (term == null || term.length() == 0) {
	                continue;
	            }

	            int count = 0;
	            int termLength = term.length();

	            // Try every possible starting position where the term can fit.
	            for (int i = 0; i <= text.length() - termLength; i++) {

	                int j = 0;

	                // Compare characters one by one.
	                while (j < termLength &&
	                        text.charAt(i + j) == term.charAt(j)) {
	                    j++;
	                }

	                // If we matched every character, we found one occurrence.
	                if (j == termLength) {
	                    count++;
	                }
	            }

	            // Store the final count for this term.
	            result.put(term, count);
	        }

	        return result;
	    }
	}
	
	/*
	 Aho‑Corasick Implementation Sketch
	 Time: O(n + L + m)	
	 Space: O(L)
	 * */
	
	static class TermCounter_AhoCorasick {

	    // Trie node for Aho-Corasick
	    static class Node {
	        Map<Character, Integer> next = new HashMap<>();
	        int failure = 0;
	        int count = 0;          // accumulated during scan
	        int termIndex = -1;     // -1 if not a terminal
	    }

	    public Map<String, Integer> Count(String input, String[] terms) {
	        Map<String, Integer> result = new LinkedHashMap<>();
	        if (input == null || input.isEmpty()) {
	            for (String t : terms) result.put(t, 0);
	            return result;
	        }

	        // 1. Build Trie
	        List<Node> trie = new ArrayList<>();
	        trie.add(new Node()); // root = 0

	        for (int idx = 0; idx < terms.length; idx++) {
	            String term = terms[idx];
	            int node = 0;
	            for (char c : term.toCharArray()) {
	                trie.get(node).next.putIfAbsent(c, trie.size());
	                node = trie.get(node).next.get(c);
	                if (trie.size() == node) trie.add(new Node());
	            }
	            trie.get(node).termIndex = idx;
	        }

	        // 2. Build failure links using BFS
	        Queue<Integer> queue = new LinkedList<>();
	        for (Map.Entry<Character, Integer> entry : trie.get(0).next.entrySet()) {
	            int child = entry.getValue();
	            trie.get(child).failure = 0;
	            queue.offer(child);
	        }

	        while (!queue.isEmpty()) {
	            int curr = queue.poll();
	            Node currNode = trie.get(curr);
	            for (Map.Entry<Character, Integer> entry : currNode.next.entrySet()) {
	                char ch = entry.getKey();
	                int child = entry.getValue();
	                int fail = currNode.failure;
	                while (fail != 0 && !trie.get(fail).next.containsKey(ch)) {
	                    fail = trie.get(fail).failure;
	                }
	                if (trie.get(fail).next.containsKey(ch)) {
	                    fail = trie.get(fail).next.get(ch);
	                }
	                trie.get(child).failure = fail;
	                queue.offer(child);
	            }
	        }

	        // 3. Scan the input string once
	        int state = 0;
	        for (char c : input.toCharArray()) {
	            while (state != 0 && !trie.get(state).next.containsKey(c)) {
	                state = trie.get(state).failure;
	            }
	            if (trie.get(state).next.containsKey(c)) {
	                state = trie.get(state).next.get(c);
	            }
	            trie.get(state).count++; // accumulate at current state
	        }

	        // 4. Propagate counts along failure links (reverse BFS order)
	        // Process nodes in decreasing depth (BFS order reversed)
	        for (int i = trie.size() - 1; i >= 0; i--) {
	            Node node = trie.get(i);
	            if (node.failure != 0) {
	                trie.get(node.failure).count += node.count;
	            }
	        }

	        // 5. Extract results, preserving input order
	        for (int idx = 0; idx < terms.length; idx++) {
	            int terminalNode = -1;
	            // Find the node that ends with this term (could be stored during build)
	            // For simplicity, we re-traverse the trie (or store node index during build)
	            int node = 0;
	            for (char c : terms[idx].toCharArray()) {
	                node = trie.get(node).next.get(c);
	            }
	            result.put(terms[idx], trie.get(node).count);
	        }

	        return result;
	    }
	}
		

}

/*
 Occurrences of each term in a string
 
 Source: https://voprep.com/oracle-vo-term-finder/


VO : 2026-05-04
Q1 [Build a Trie (prefix tree) from all terms]:  
Given an array of strings (terms), find the number of occurrences of each 
term in a string. You need only fill out the Countmethod.

Parameters:

Use any data structures available to you, including ones you create.
The term count map must be ordered in the order of the terms string array 
(for example, fizzfirst, buzzsecond, fizzbuzzthird).
Duplicate terms in the array are not allowed (assume a process has previously de-duped 
the terms in the array).
Cannot use any regular expression API/libraries.
Blank strings (ie "") and nullshould be handled and returned with all terms set to zero.
No exceptions should be thrown or need to be handled.
Return a map of each term to its occurrence count in the input string.

This problem requires you to count the occurrences of each term in an input string without 
using regular expressions, and to return the results while maintaining the original order of 
the terms array. The most straightforward approach is to iterate through the terms array, 
using character-by-character matching or a two-pointer method to count the occurrences of 
each word, and then store the results in an ordered mapping structure (such as a LinkedHashMap). 

Special handling is needed for empty strings and null values: in these two cases, you should directly 
return a count of 0 for all terms.
 
 
 */