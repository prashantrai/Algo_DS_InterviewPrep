package Dropbox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordPattern_II_291_Hard {

	/**
	 * Leetcode: Word Pattern II - 291
	 * 
	 * Given a pattern and a string str, find if str follows the same pattern. 
	 * Here follow means a full match, such that there is a bijection between a letter 
	 * in pattern and a non-empty substring in str.
	 * 
	 * Examples: pattern = "abab", str = "redblueredblue" should return true.
	 * 
	 * pattern = "aaaa", str = "asdasdasdasd" should return true. 
	 * 
	 * pattern = "aabb", str = "xyzabcxzyabc" should return false. 
	 * 
	 * Notes: You may assume both pattern and str contains only lowercase letters.
	 * 
	 * 
	 * Refrences: 
	 * Question: http://buttercola.blogspot.com/2015/10/leetcode-word-pattern-ii.html
	 * Solution: 
	 * 		https://jogchat.com/uber/291.%20Word%20Pattern%20II%20(Uber%20Hard).php
	 * 		https://massivealgorithms.blogspot.com/2015/10/leetcode-291-word-pattern-ii.html
	 * 		http://buttercola.blogspot.com/2015/10/leetcode-word-pattern-ii.html
	 * 		https://www.programcreek.com/2014/07/leetcode-word-pattern-ii-java/
	 * 
	 * 
	 */

	public static void main(String[] args) {

		String pattern = "abab", str = "redblueredblue"; // should return true.
		System.out.println("Expected:: true, Actual:: " + wordPatternMatch(pattern, str));
		
		pattern = "aaaa"; str = "asdasdasdasd"; // should return true.
		System.out.println("Expected:: true, Actual:: " + wordPatternMatch(pattern, str));
		
		pattern = "aabb"; str = "xyzabcxzyabc"; // should return false.
		System.out.println("Expected:: false, Actual:: " + wordPatternMatch(pattern, str));
		
	}
	
	/** 
	 * Big-O Analysis:: 
	 * 		Pattern length:m. String length:n
	 * 		Running time: O (n^m)
	 * 		Space: O(m + n)
	 */
	
	
	public static boolean wordPatternMatch(String pattern, String str) {
		
		Map<Character, String> map = new HashMap<>();
		Set<String> set = new HashSet<>();
		
		return isMatch(str, 0, pattern, 0, map, set);
		
	}

	private static boolean isMatch(String str, int i, String pattern, int j, Map<Character, String> map, Set<String> set) {
		// base case
		
		if(str.length() == i && pattern.length() == j) return true;
		if(str.length() == i || pattern.length() == j) return false;
		
		// get current pattern character
		char c = pattern.charAt(j);
		
		// if pattern character exist in map
		if(map.containsKey(c)) {
			
			String s = map.get(c);
			
			// check if s is part of from i till s.length()
			if(!str.startsWith(s, i)) {
				return false;
			}
			
			// if matched then contunue to match the rest
			return isMatch(str, i+s.length(), pattern, j+1, map, set);
		}
		// if pattern character doesn't exist in map
		for(int k=i; k<str.length(); k++) {
			String p = str.substring(i, k+1);
			
			if(set.contains(p)) continue;
			
			// create or update it
			map.put(c, p);
			set.add(p);
			
			// continue to match the rest
			if(isMatch(str, k+1, pattern, j+1, map, set)) {
				return true;
			}
			
			// backtracking
			map.remove(c);
			set.remove(p);
		}
		
		return false;
	}
	
	

}
