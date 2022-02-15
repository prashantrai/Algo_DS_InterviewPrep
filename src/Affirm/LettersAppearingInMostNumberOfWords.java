package Affirm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LettersAppearingInMostNumberOfWords {

	/**
	 * https://leetcode.com/discuss/interview-question/381353/affirm-phone-screen-letters-appearing-most-number-of-words
	 * 
	Given an input list of strings, for each letter appearing anywhere 
	in the list, find the other letter(s) that appear in the most 
	number of words with that letter.

	Example: 
	Input: ['abc', 'bcd', 'cde']
	
	Output:
	  {
		a: [b, c],	# b appears in 1 word with a, c appears in 1 word with a
		b: [c], 	# c appears in 2 words with b, a and d each appear in only 1 word with b
		c: [b, d], 	# b appears in 2 words with c, d appears in 2 words with c. But a and e each 
						  appear in only 1 word with c.
		d: [c],		# c appears in 2 words with d. But b and e each appear in only 1 word with d
		e: [c, d], 	# c appears in 1 word with e, d appears in 1 word with e
			
	  }
	  
	
	Input: ["abc", "bcd", "cde"]

	Output:
	a -> [b,c] //a only appears with b and c
	b -> [c] // c appears twice where b is present
	c -> [b or d] //since both have same count where 'c' appears
	d -> [c] //c has the highest count of 2
	e -> [c, d] //only c and d appear with e
	
	Input: ["abef", "bcd", "bde", "cadf"]

	Output:
	[
	 a: {f} 	// f occurs 2 times with a
	 b: {d,e} 	// d and e occur 2 times with b
	 c: {d} 	// d occurs 2 times with c
	 d: {b,c} 	// b and c occur 2 times with d 
	 e: {b} 	// b occurs 2 times with e 
	 f: {a} 	// a occurs 1 time with f
	]  
	
	  
	Other URLs for same question: 
	https://leetcode.com/discuss/interview-question/358776/letters-that-occur-most-with-other-letters-in-a-list-of-multiple-words
	https://leetcode.com/discuss/interview-question/331117/
	
	
	Just an idea: Try to create adjacency  matrix like below then iterate over to find the adjacent word max count for each letter
	
	For input : Input: ["abef", "bcd", "bde", "cadf"]
	
	adjacency  matrix: 
	
	  a b c d e f
	a 0 1 1 1 1 2
	b 1 0 1 2 2 1
	c 1 1 0 2 0 1
	d 1 2 2 0 1 1
	e 1 2 0 1 0 1
	f 2 1 1 1 1 0
	*/
	
	public static void main(String[] args) {
		String[] strs  = {"abc", "bcd", "cde"};
		//System.out.println(getLettersAppears(strs)); //working
		
		String[] strs2 = {"abef", "bcd", "bde", "cadf"};
		helper(strs2); //working
		//getMaxNeighbor(strs2); //working
	}
	
	/* https://leetcode.com/discuss/interview-question/358776/letters-that-occur-most-with-other-letters-in-a-list-of-multiple-words
	 * 
	 * Using adjacency matrix: 
	 * 	For each character pair in a string (O(n^2)) increment that row/column in an 
	 * 	adjacency matrix, then in a second pass loop over the rows and return the columns
	 * 	with the largest number(s).
	 * 
	 * 
		adjacency  matrix: 
		
		  a b c d e f
		a 0 1 1 1 1 2
		b 1 0 1 2 2 1
		c 1 1 0 2 0 1
		d 1 2 2 0 1 1
		e 1 2 0 1 0 1
		f 2 1 1 1 1 0
	 * 
	 */
	public static void helper(String[] strs) {
		
		Map<Character, Map<Character, Integer>> map = new HashMap<>();
		
		for(String str : strs) {
			for(int i=0; i<str.length(); i++) {
				char key1 = str.charAt(i);
				map.putIfAbsent(key1, new HashMap<>());
				
				Map<Character, Integer> neighbors = map.get(key1);
				
				for(int j=0; j<str.length(); j++) {
					if(i == j) continue; //same key/char
					char key2 = str.charAt(j);
					neighbors.put(key2, neighbors.getOrDefault(key2, 0)+1);
				}
			}
		}
		
		//System.out.println("adjacency matrix: "+map);
		
		Map<Character, Set<Character>> result = new HashMap<>();
		
		for(Character key : map.keySet()) {
			
			/* 
			 * Another approach of getting max is to sort he Map by values using comparator
			 * (refer following method getMaxNeighbor()) and Collectios.sort
			 * but that would bring the complexity for getting max to NlogN
			 * 
			 *  However the if we just iterate we can get the max in O(N) time
			 *  like below
			 * */
			
			int max = 0;
			Map<Character, Integer> row = map.get(key);
			for(int i : row.values()) {
				max = Math.max(max, i);
			}
			
			result.putIfAbsent(key, new HashSet<>());
			
			for(Character ch : row.keySet()) {
				if(row.get(ch) == max) {
					result.get(key).add(ch);
				}
			}
		}
		
		System.out.println("result: "+result);
		
	}
	
	
	// working 
	private static Map<Character, Set<Character>> getLettersAppears(String[] strs) {
		Map<Character, Set<Integer>> map = new HashMap<>();
		for(int i=0;i<strs.length;i++) {
			String s = strs[i];
			for(char c : s.toCharArray()) {
				map.putIfAbsent(c, new HashSet<>());
				map.get(c).add(i);
			}
		}

		Map<Character, Set<Character>> res = new HashMap<>();
		for(char key : map.keySet()) {
			Set<Integer> nums = map.get(key);
			Map<Character, Integer> cntMap = new HashMap<>();
			int max = 0;
			for(Integer pos : nums) {
				for(char k : map.keySet()) {
					if(key == k)
						continue;
					Set<Integer> tmp = map.get(k);
					if(tmp.contains(pos)) {
						cntMap.put(k, cntMap.getOrDefault(k, 0) + 1);
						max = Math.max(max, cntMap.get(k));
					}
				}	
			}
			Set<Character> set = new HashSet<>();
			for(Map.Entry<Character, Integer> entry : cntMap.entrySet()) {
				if(entry.getValue() == max)
					set.add(entry.getKey());
			}
			res.put(key, set);
		}
		
		return res; 
	}
	
	
	/*
	 * 	For input : Input: ["abef", "bcd", "bde", "cadf"]
	
		adjacency  matrix: 
		
		  a b c d e f
		a 0 1 1 1 1 2
		b 1 0 1 2 2 1
		c 1 1 0 2 0 1
		d 1 2 2 0 1 1
		e 1 2 0 1 0 1
		f 2 1 1 1 1 0

	 * 
	 * */

	
	public static void getMaxNeighbor(String[] strs) {
        Map<Character, Map<Character, Integer>> map = new HashMap<>();
        
        for(String str : strs) {
            for(int i=0; i<str.length(); i++) {
                map.putIfAbsent(str.charAt(i), new HashMap<>());
                Map<Character, Integer> neighbors = map.get(str.charAt(i));
                for(int j=0; j<str.length(); j++) {
                    if(i == j)
                        continue;
                    neighbors.put(str.charAt(j), neighbors.getOrDefault(str.charAt(j), 0)+1);
                }
            }
        }
        
        System.out.println(map);
        
        Map<Character, Set<Character>> result = new HashMap<>();
        for(Character c : map.keySet()) {
            List<Map.Entry<Character, Integer>> entries = new ArrayList<>(map.get(c).entrySet());
            Collections.sort(entries, (a,b) -> b.getValue()-a.getValue());
            
            result.putIfAbsent(c, new HashSet<>());
            int occ = entries.get(0).getValue();
            for(Map.Entry<Character, Integer> entry : entries) {
                if(entry.getValue() == occ)
                    result.get(c).add(entry.getKey());
            }
        }
        System.out.println(result);
        
    }
	
}
