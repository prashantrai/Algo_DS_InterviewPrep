package ctci.ch4.trees.and.graphs;
import java.util.HashMap;
import java.util.Map;

//--YouTube video : Tushar Roy

public class TrieDemo {
	
	public static void main (String[] args) {
		
		Trie trie = new Trie();
		trie.insert("bat");
		trie.insert("ball");
		trie.insert("cat");
		trie.insert("cast");
		
		System.out.println(trie.search("ball"));
		System.out.println(trie.search("boss"));
		System.out.println(trie.search("cast"));
	}
} 


class Trie {
	
	private final TrieNode root;
	
	public Trie() {
		root = new TrieNode();
	}
	
	
	//--Interative implementation of insert
	public void insert(String word) {
		TrieNode current = root;
		
		for (int i=0; i<word.length(); i++) {
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			
			if(node == null) {
				node = new TrieNode();
				current.children.put(ch, node); //--add new child to map
			} 			
			current = node;
		}
		
		//--mark the current word as end of word
		current.endOfWord = true;
	}
	
	
	//--Recursive implementation of insert
	public void insertRecursive(String word) {
		insertRecursive(root, word, 0);
	}
	
	private void insertRecursive(TrieNode current, String word, int index) {
		
		if(index == word.length()) {
			current.endOfWord = true;
			return;
		}
		
		char ch = word.charAt(index);
		TrieNode node = current.children.get(ch);
		
		//--if node doesn't exists in map then create one and put it into map
		if(node == null) {
			node = new TrieNode();
			current.children.put(ch, node);
		}
		
		insertRecursive(node, word, index+1);
	}

	
	//-- Interative implementation of search
	public boolean search(String word) {
		TrieNode current = root;
		for(int i=0; i < word.length(); i++) {
			char ch = word.charAt(i);
			TrieNode node = current.children.get(ch);
			//--if node doesn't exist for given char then return false
			if(node == null) {
				return false;
			}
			current = node;
		}
		
		//--return true of current's endOfWord is true else return false
		return current.endOfWord;
	}

	//--Recursive implementation of serarch
	public boolean searchRecursive(String word) {
		return searchRecursive(root, word, 0);
	}
	
	private boolean searchRecursive(TrieNode current, String word, int index) {
		if(index == word.length()) {
			return current.endOfWord;
		}
		
		TrieNode node = current.children.get(word.charAt(index));
		if(node == null) 
			return false;
		
		return searchRecursive(current, word, index+1);
	}

	//--delete a word from trie
	public void delete(String word) {
		delete(root, word, 0);
	}
	
	private boolean delete(TrieNode current, String word, int index) {
		
		if(index == word.length()) {
			//--When endOfWord is reached only delete if current.endOfWord is true/
			if(!current.endOfWord) {
				return false;
			}
			current.endOfWord = false;
			
			//-if current has no other mapping then return true
			return current.children.size() == 0;
		}
		TrieNode node = current.children.get(word.charAt(index));
		if(node == null) 
			return false;
		
		boolean shouldDeleteCurrentNode = delete(node, word, index+1);
		
		//--if true is returned then delete the mapping for character and trienode reference from map
		if(shouldDeleteCurrentNode) {
			current.children.remove(word.charAt(index));
			
			//--return true if no mappings are left in the map.
			return current.children.size() == 0;
		}
		return false;
	}


	private class TrieNode {
		Map<Character, TrieNode> children;
		boolean endOfWord;
		public TrieNode() {
			children = new HashMap<Character, TrieNode>();
			endOfWord = false;
		}
	}
	
}