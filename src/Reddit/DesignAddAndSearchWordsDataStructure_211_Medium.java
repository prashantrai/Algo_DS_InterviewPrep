package Reddit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DesignAddAndSearchWordsDataStructure_211_Medium {

	public static void main(String[] args) {
		WordDictionary obj = new WordDictionary();
		obj.addWord("bad");
		obj.addWord("dad");
		obj.addWord("mad");
		
		System.out.println("Expected: false, Actual: "+obj.search("pad"));
		System.out.println("Expected: true, Actual: "+obj.search("bad"));
		System.out.println("Expected: true, Actual: "+obj.search(".ad"));
		System.out.println("Expected: true, Actual: "+obj.search("b.."));
		
		// Calling HashMap implementation
		WordDictionary2 dict2 = new WordDictionary2();
		dict2.addWord("bad");
		dict2.addWord("dad");
		dict2.addWord("mad");
		
		System.out.println("2. Expected: false, Actual: "+dict2.search("pad"));
		System.out.println("2. Expected: true, Actual: "+dict2.search("bad"));
		System.out.println("2. Expected: true, Actual: "+dict2.search(".ad"));
		System.out.println("2. Expected: true, Actual: "+dict2.search("b.."));
	}

	/*
	 ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
	 [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
	 * */
	
	
	
	/* Time Complexity:
		addWord() - O(n), n = length of the new word
		search() - Worst case: O(m), m = the total number of characters in the Trie
		        O(26^n): in the worst case, the word is comprised of '.', and all characters in a trie node is 
		        mapped to a child. Then search will go through every possible path from root to leaf. 
		        The overall time complexity is O(26^n).
		        
		Space Complexity:
			addWord: O(N), creating N more nodes in the trie, N is the length of the word
			search: No additional space needed

	 * 
	 * */
	
	// Using Trie
	// reference: https://leetcode.com/problems/design-add-and-search-words-data-structure/discuss/59554/My-simple-and-clean-Java-code
	static class WordDictionary {
		
		private TrieNode root;
		
		/** Initialize your data structure here. */
	    public WordDictionary() {
	        root = new TrieNode();
	    }
	    
	    public void addWord(String word) {
	    	TrieNode node = root;
	    	for (char c : word.toCharArray()) {
	    		if(node.children[c - 'a'] == null) {
	    			node.children[c - 'a'] = new TrieNode();
	    		}
	    		node = node.children[c - 'a'];
	    		
	    	}
	    	// end of the loop means the complete word has been scanned, so, set the isWord to true
	    	node.isWord = true;
	    }
	    
	    public boolean search(String word) {
	    	return matches(word.toCharArray(), 0, root);
	    }
	    
	    private boolean matches(char[] chArr, int i, TrieNode node) {
	    	if(i == chArr.length) {
	    		return node.isWord;
	    	}
	    	
	    	if(chArr[i] == '.') {
	    		for(int j=0; j<node.children.length; j++) {
	    			if(node.children[i] != null && matches(chArr, i+1, node.children[j])) {
	    				return true;
	    			}
	    		}
	    	} else {
	    		return node.children[chArr[i] - 'a'] != null 
	    				&& matches(chArr, i+1, node.children[chArr[i] - 'a']);
	    	}
	    	
	    	return false;
	    }
	    
	    //TrieNode class
	    private class TrieNode {
			public TrieNode[] children = new TrieNode[26];
			public boolean isWord;
		}
	}
	
	
	// Working Solution: Using HashMap
	
	static class WordDictionary2 {

	    Map<Integer, Set<String>> map;
	    
	    /** Initialize your data structure here. */
	    public WordDictionary2() {
	        map = new HashMap<>();
	    }
	    
	    public void addWord(String word) {
	        int key = word.length();
	        if(!map.containsKey(key)) {
	            map.put(key, new HashSet<String>());
	        }
	        map.get(key).add(word);
	    }
	    
	    public boolean search(String word) {
	        int key = word.length();
	        if(map.containsKey(key)) {
	        	if(word.contains(".")) {
	        		for(String s : map.get(key)) {
	        			
	        			if(isSameWord(s, word)) 
	        				return true;
	        		}
	        	}
	            return map.get(key).contains(word);
	        }
	        return false;
	    }
	    
	    public boolean isSameWord(String existingStr, String word) {
	    	for(int i=0; i<word.length(); i++) {
    			if(word.charAt(i) != '.' && word.charAt(i) != existingStr.charAt(i)) {
    				return false;
    			}
    		}
	    	return true;
	    }
	}
	
	
}
