package Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadder_126_Hard {

	public static void main(String[] args) {

		String beginWord = "hit", endWord = "cog";
		List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

		List<List<String>> res = findLadders(beginWord, endWord, wordList);

		System.out.println("Expected: [[hit, hot, dot, dog, cog ],[ hit , hot, lot, log, cog]]");
		System.out.println("Actual: " + res);

	}

	/**
	 * Leetcode Premium Silution: Breadth-First Search (BFS) + Backtracking
	 * 
	 * Time complexity: O(NK^2 + α)
		Here N is the number of words in wordList, K is the maximum length of a word, α is the number 
		of possible paths from beginWord to endWord in the directed graph we have.
		
		Copying the wordList into the set will take O(N).
		In BFS, every word will be traversed and for each word, we will find the neighbors using the 
		function findNeighbors which has a time complexity of O(K^2). Therefore the total complexity 
		for all the N words will be O(NK^2). Also, each word will be enqueued and will be removed from 
		the set hence it will take O(N). The total time complexity of BFS will therefore be equal to 
		O(NK^2 ).

		While backtracking, we will essentially be finding all the paths from beginWord to endWord. 
		Thus the time complexity will be equal to O(α).
		
		We can estimate the upper bound for α by assuming that every layer except the first and the 
		last layer in the Directed Acyclic Graph (DAG). has x number of words and is fully connected to the next layer. 
		
		Let h represent the height of the Directed Acyclic Graph (DAG), so the total number of paths will be x^h
  		(because we can choose any one word out of x words in each layer and each choice will be 
  		part of a valid shortest path that leads to the endWord). Here, h equals (N-2)/x(N−2)/x. 
  		This would result in x^(N-2)/x total paths, which is maximized when x = 2.718, 
  		which we will round to 3 because x must be an integer. Thus the upper bound for 
  		α is 3^(N/3), however, this is a very loose bound because the nature of this problem 
  		precludes the possibility of a DAG where every layer is fully connected to the next layer.

		The total time complexity is therefore equal to O(NK^2 + α).
		
		
		Space complexity: O(NK).

		Here N is the Number of words in wordList, K is the Maximum length of a word.
		Storing the words in a set will take O(NK) space.
		To build the adjacency list O(N) space is required as the BFS will produce a directed graph 
		and hence there will be at max (N - 1) edges.
		
		In backtracking, stack space will be consumed which will be equal to the maximum number of 
		active functions in the stack which is equal to the N as the path can have all the words in 
		the wordList. Hence space required is O(N).
		
		The total space complexity is therefore equal to O(NK).
		
	 */
	Map<String, List<String>> adjList = new HashMap<String, List<String>>();
	List<String> currPath = new ArrayList<String>();
	List<List<String>> shortestPaths = new ArrayList<List<String>>();

	public List<List<String>> findLadders_LCPremium(String beginWord, String endWord, List<String> wordList) {
		// copying the words into the set for efficient deletion in BFS
		Set<String> copiedWordList = new HashSet<>(wordList);
		// build the DAG using BFS
		bfs(beginWord, endWord, copiedWordList);

		// every path will start from the beginWord
		currPath.add(beginWord);
		// traverse the DAG to find all the paths between beginWord and endWord
		backtrack(beginWord, endWord);

		return shortestPaths;
	}

	private void bfs(String beginWord, String endWord, Set<String> wordList) {
		Queue<String> q = new LinkedList<>();
		q.add(beginWord);

		// remove the root word which is the first layer in the BFS
		if (wordList.contains(beginWord)) {
			wordList.remove(beginWord);
		}

		Map<String, Integer> isEnqueued = new HashMap<String, Integer>();
		isEnqueued.put(beginWord, 1);

		while (q.size() > 0) {
			// visited will store the words of current layer
			List<String> visited = new ArrayList<String>();

			for (int i = q.size() - 1; i >= 0; i--) {
				String currWord = q.peek();
				q.remove();

				// findNeighbors will have the adjacent words of the currWord
				List<String> neighbors = findNeighbors(currWord, wordList);
				for (String word : neighbors) {
					visited.add(word);

					if (!adjList.containsKey(currWord)) {
						adjList.put(currWord, new ArrayList<String>());
					}

					// add the edge from currWord to word in the list
					adjList.get(currWord).add(word);
					if (!isEnqueued.containsKey(word)) {
						q.add(word);
						isEnqueued.put(word, 1);
					}
				}
			}
			// removing the words of the previous layer
			for (int i = 0; i < visited.size(); i++) {
				if (wordList.contains(visited.get(i))) {
					wordList.remove(visited.get(i));
				}
			}
		}
	}// bfs closed

	private List<String> findNeighbors(String word, Set<String> wordList) {
		List<String> neighbors = new ArrayList<String>();
		char charList[] = word.toCharArray();

		for (int i = 0; i < word.length(); i++) {
			char oldChar = charList[i];

			// replace the i-th character with all letters from a to z except the original
			// character
			for (char c = 'a'; c <= 'z'; c++) {
				charList[i] = c;

				// skip if the character is same as original or if the word is not present in
				// the wordList
				if (c == oldChar || !wordList.contains(String.valueOf(charList))) {
					continue;
				}
				neighbors.add(String.valueOf(charList));
			}
			charList[i] = oldChar;
		}
		return neighbors;
	}

	private void backtrack(String source, String destination) {
		// store the path if we reached the endWord
		if (source.equals(destination)) {
			List<String> tempPath = new ArrayList<String>(currPath);
			shortestPaths.add(tempPath);
		}

		if (!adjList.containsKey(source)) {
			return;
		}

		for (int i = 0; i < adjList.get(source).size(); i++) {
			currPath.add(adjList.get(source).get(i));
			backtrack(adjList.get(source).get(i), destination);
			currPath.remove(currPath.size() - 1);
		}
	}

	/**
	 * Reference: https://leetcode.wang/leetCode-126-Word-LadderII.html 2 BFS
	 * Solution (not bi directional):
	 * https://leetcode.wang/leetCode-126-Word-LadderII.html#%E8%A7%A3%E6%B3%95%E4%BA%8C--bfs
	 * 
	 * Wokring but getting TLE in leetcode
	 */

	public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		List<List<String>> ans = new ArrayList<>();
		if (!wordList.contains(endWord)) {
			return ans;
		}
		bfs(beginWord, endWord, wordList, ans);
		return ans;
	}

	public static void bfs(String beginWord, String endWord, List<String> wordList, List<List<String>> ans) {
		Queue<List<String>> queue = new LinkedList<>();
		List<String> path = new ArrayList<>();
		path.add(beginWord);
		queue.offer(path);
		boolean isFound = false;
		Set<String> dict = new HashSet<>(wordList);
		Set<String> visited = new HashSet<>();
		visited.add(beginWord);
		while (!queue.isEmpty()) {
			int size = queue.size();
			Set<String> subVisited = new HashSet<>();
			for (int j = 0; j < size; j++) {
				List<String> p = queue.poll();
				String temp = p.get(p.size() - 1);
				ArrayList<String> neighbors = getNeighbors(temp, dict);
				for (String neighbor : neighbors) {
					if (!visited.contains(neighbor)) {
						if (neighbor.equals(endWord)) {
							isFound = true;
							p.add(neighbor);
							ans.add(new ArrayList<String>(p));
							p.remove(p.size() - 1);
						}
						p.add(neighbor);
						queue.offer(new ArrayList<String>(p));
						p.remove(p.size() - 1);
						subVisited.add(neighbor);
					}
				}
			}
			visited.addAll(subVisited);
			if (isFound) {
				break;
			}
		}
	}

	private static ArrayList<String> getNeighbors(String node, Set<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char chs[] = node.toCharArray();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] == ch)
					continue;
				char old_ch = chs[i];
				chs[i] = ch;
				if (dict.contains(String.valueOf(chs))) {
					res.add(String.valueOf(chs));
				}
				chs[i] = old_ch;
			}

		}
		return res;
	}

}
