package leetcode;

import java.util.HashMap;
import java.util.Map;

public class DesignFileSystem_1166_Medium_Premium {

	public static void main(String[] args) {
		
		FileSystem fileSystem = new FileSystem();

		System.out.println("Expected: true, Actual: " + fileSystem.createPath("/leet", 1)); // return true
		System.out.println("Expected: true, Actual: " + fileSystem.createPath("/leet/code", 2)); // return true
		System.out.println("Expected: 2, Actual: " + fileSystem.get("/leet/code")); // return 2
		System.out.println("Expected: false, Actual: " + fileSystem.createPath("/c/d", 1)); // return false because the parent path "/c" doesn't exist.
		System.out.println("Expected: -1, Actual: " + fileSystem.get("/c")); // return -1 because this path doesn't exist.

	}
	
	private static class FileSystem {
	    
	    /* 
	    Time: O(N), where N is the length of the path. ll the time is actually consumed 
	    by the operation that gives us the parent path.
	    
	    Space: O(K), K is the number of unique paths that we add
	    
	    */
	    Map<String, Integer> paths;

	    public FileSystem() {
	        paths = new HashMap<>();
	    }
	    
	    //Time: O(N)
	    public boolean createPath(String path, int value) {
	        // Step-1: basic path validations
	        if(path == null || path.isEmpty() 
	           || path.equals("/")
	           || paths.containsKey(path)) {
	            return false;
	        }
	        
	        // Step-2: Get parent of inout and if the parent doesn't exist in Map 
	        // then return FALSE. Note that "/" is a valid parent.
	        int idx = path.lastIndexOf("/");
	        String parent = path.substring(0, idx); //will give parent
	       
	        if(parent.length() > 1 && !paths.containsKey(parent)) 
	            return false;
	        
	        // Step-3: add this new path and return true.
	        // If the input path doesn't exist then PUT will NULL
	        return paths.put(path, value) == null;
	    }
	    
	    //Time: O(1)
	    public int get(String path) {
	        return paths.getOrDefault(path, -1);
	    }
	}
	
	
	// ------------- Approach 2: Using Trie ------------------------
	// https://leetcode.com/problems/design-file-system/discuss/464718/Java-TrieTree-Solution-Easy-to-Understand
	
	class TrieNode {
		String name;
		int value;
		Map<String, TrieNode> children;

		TrieNode(String name, int value) {
			this.name = name;
			this.value = value;
			this.children = new HashMap<>();
		}
	}

	class FileSystem2 {
		private TrieNode root;

	    public FileSystem2() {
	        this.root = new TrieNode("/", -1);
	    }
	    
	    public boolean createPath(String path, int value) {
	        String[] folders = path.split("/");

	        TrieNode currNode = this.root;
	        String name = folders[folders.length - 1];

	        for (int i = 0; i < folders.length - 1; i++) {
	            if (folders[i].equals("")) {
	                continue;
	            }
	        	if (currNode.children.containsKey(folders[i])) {
	        		currNode = currNode.children.get(folders[i]);
	        	} else {
	        		return false;
	        	}
	        }
	        
	        // check if the path exists
	        if (currNode.children.containsKey(name)) {
	            return false;
	        }

	        currNode.children.put(name, new TrieNode(name, value));
	        return true;
	    }
	    
	    public int get(String path) {
	        String[] folders = path.split("/");
	        TrieNode currNode = this.root;

	        for (String folder: folders) {
	            if (folder.equals("")) {
	                continue;
	            }
	        	if (currNode.children.containsKey(folder)) {
	        		currNode = currNode.children.get(folder);
	        	} else {
	        		return -1;
	        	}
	        }

	        return currNode.value;
	    }
	}

}


/*
Question: 

You are asked to design a file system that allows you to create new paths and associate 
them with different values.

The format of a path is one or more concatenated strings of the form: / followed by one 
or more lowercase English letters. 

For example, "/leetcode" and "/leetcode/problems" are valid paths while an empty 
string "" and "/" are not.

Implement the FileSystem class:

 - bool createPath(string path, int value) Creates a new path and associates a value to it 
 	if possible and returns true. Returns false if the path already exists or its parent 
 	path doesn't exist.
 
 - int get(string path) Returns the value associated with path or returns -1 if the path 
 	doesn't exist.
 

Example 1:

Input: 
	["FileSystem","createPath","get"]
	[[],["/a",1],["/a"]]
	Output: 
	[null,true,1]
Explanation: 
	FileSystem fileSystem = new FileSystem();

	fileSystem.createPath("/a", 1); // return true
	fileSystem.get("/a"); // return 1
	
Example 2:

Input: 
	["FileSystem","createPath","createPath","get","createPath","get"]
	[[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
Output: 
	[null,true,true,2,false,-1]
Explanation: 
	FileSystem fileSystem = new FileSystem();
	
	fileSystem.createPath("/leet", 1); // return true
	fileSystem.createPath("/leet/code", 2); // return true
	fileSystem.get("/leet/code"); // return 2
	fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
	fileSystem.get("/c"); // return -1 because this path doesn't exist.
 */ 
