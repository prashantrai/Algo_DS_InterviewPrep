package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentWords_692_Medium {

	public static void main(String[] args) {
		
		String[] words = {"i","love","leetcode","i","love","coding"}; 
		int k = 2;
		System.out.println("Expected: [i, love], Actual: " + topKFrequent(words, k));
		System.out.println("Expected: [i, love], Actual: " + topKFrequent2(words, k));
		
		k = 3;
		System.out.println("Expected: [i, love, coding], Actual: " + topKFrequent(words, k));

	}
	
	/* Also look at TopKFrequentElements_347_Medium.Java
	 * 
	 * for simpler way (as that's only on Int) to understand Bucket Sort
	 * */
	
	/* Bucket Sort - Using Trie
	 * https://leetcode.com/problems/top-k-frequent-words/discuss/431008/Summary-of-all-the-methods-you-can-imagine-of-this-problem
	 * 
	 * Time: O(N)
	 * Space: O(N)
	 */
	public static List<String> topKFrequent(String[] words, int k) {
		 
		Map<String, Integer> freq = buildFreqMap(words);
		
		//For bucket sort create an bucket (array) of Trie
		Trie[] bucket = new Trie[words.length];
		
		for(Map.Entry<String, Integer> entry : freq.entrySet()) {
			String word = entry.getKey();
			int idx = entry.getValue(); //freq
			
			// for each word, add it into trie at its bucket
			if(bucket[idx] == null) {
				bucket[idx] = new Trie();
			}
			bucket[idx].addWord(word);
		}
		
		List<String> res = new ArrayList<>();
		
		// for trie in each bucket, get all the words with same frequency in lexicographic order. 
		// Compare with k and get the result
		for(int i=bucket.length-1; i>=0; i--) {
			if(bucket[i] != null) {
				List<String> l = new ArrayList<>();
				bucket[i].getWords(bucket[i].root, l);
				if(l.size() < k) {
					res.addAll(l);
					k = k - l.size();
				} else {
					for(int j=0; j<k; j++) {
						res.add(l.get(j));
					}
					break; //all k words have been added to res
				}
			}
		}
		
		return res;
		
	}
	
	private static class Trie {
		TrieNode root = new TrieNode();
		
		public void addWord(String word) {
			TrieNode curr = root;
			for(char c : word.toCharArray()) {
				if(curr.children[c - 'a'] == null) {
					curr.children[c - 'a'] = new TrieNode();
				} 
				curr = curr.children[c - 'a'];
			}
			curr.word = word;
		}
		
		// use DFS to get lexicograpic order of all the words with same frequency
		public void getWords(TrieNode node, List<String> res) {
			if(node == null) return;
			
			//check if the word exist at current and add that to res
			if(node.word != null) 
				res.add(node.word);
			
			for(int i=0; i<26; i++) {
				if(node.children[i] == null) continue;
				
				getWords(node.children[i], res); //recursive
			}
			
		}
	}
	
	private static class TrieNode {
		TrieNode[] children = new TrieNode[26];
		String word;
	}
	
	/* Using Priority Queue
    https://leetcode.com/problems/top-k-frequent-words/discuss/176865/O(nlogk)-time-to-O(n)-time
    https://leetcode.com/problems/top-k-frequent-words/discuss/431008/Summary-of-all-the-methods-you-can-imagine-of-this-problem
    
    Time: O(NlogN), because for PriorityQueue/Heap
    Space: O(N)
    */
    public static List<String> topKFrequent2(String[] words, int k) {
        Map<String, Integer> freq = buildFreqMap(words);
        
        //Max heap
        PriorityQueue<String> pq 
            = new PriorityQueue<>(
            (a,b) -> ( freq.get(a) == freq.get(b)
                      ? b.compareTo(a)
                      : freq.get(a) - freq.get(b) //if not same then by freq diff 
                     )
        );
        
        // add words in PriorityQueue
        for(String word : freq.keySet()) {
            pq.offer(word);
            if(pq.size() > k) pq.poll();
        }
        
        // with ArrayList 'res' with this we have to use 
        //Collections.reverse(res) before returning
        //List<String> res = new ArrayList<>(); 
        LinkedList<String> res = new LinkedList<>();
        while(!pq.isEmpty()) {
            //res.add(minHeap.poll());
            res.addFirst(pq.poll());
        }
        
        return res;
    }
    
    private static Map<String, Integer> buildFreqMap(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for(String word : words) {
            map.put(word, map.getOrDefault(word, 0)+1);
        }
        return map;
    }

}
