package Intuit;

import java.util.HashSet;
import java.util.Set;

public class NumberOfDistinctSubstringsInAString_1698_Medium {

	public static void main(String[] args) {
		System.out.println("Expected: 21, Actual: " + distinctSubstring("aabbaba"));
		System.out.println("Expected: 28, Actual: " + distinctSubstring("abcdefg"));
		
		System.out.println("Expected: 21, Actual: " + distinctSubstring_REC("aabbaba"));
		seen.clear();
		System.out.println("Expected: 28, Actual: " + distinctSubstring_REC("abcdefg"));
		
	}
	
	/* Leetcode Premium
	 * https://leetcode.ca/all/1698.html
	 1698. Number of Distinct Substrings in a String
		Given a string s, return the number of distinct substrings of s.
		
		A substring of a string is obtained by deleting any number of characters (possibly zero) from the front of the string and any number (possibly zero) from the back of the string.
		
		Example 1:
		
		Input: s = "aabbaba"
		Output: 21
		Explanation: The set of distinct strings is ["a","b","aa","bb","ab","ba","aab","abb","bba","aba","aabb","abba","bbab","baba","aabba","abbab","bbaba","aabbab","abbaba","aabbaba"]
		
		Example 2:
		
		Input: s = "abcdefg"
		Output: 28
		 
		
		Constraints:
		
		1 <= s.length <= 500
		s consists of lowercase English letters.
		 
		
		Follow up: Can you solve this problem in O(n) time complexity? 
		
		
		N(N+1)/2 : Use this to derive the number of substrings. N is length of string. In this problem we our result will have to dups count.
	 * */

	
	
	// backtrack
	public static Set<String> seen = new HashSet();
	
	public static int distinctSubstring_REC(String s) {
		return helper(s, 0);
	}
    
    public static int helper(String s, int cnt){
        if(seen.contains(s) || s.length() == 0)
            return 0;
        
        if(!seen.contains(s))
            seen.add(s.toString());
        
        if(s.length() == 1)
            return 1;
        
       return (1 + helper(s.substring(0, s.length()-1), cnt)    +
    		   helper(s.substring(1, s.length()), cnt))     + 
    		   helper(s.substring(1, s.length()-1), cnt);

    }
	
	
	
	/*
	 * https://www.geeksforgeeks.org/count-number-of-distinct-substring-in-a-string/
	 * 
	 * Time complexity is O(n^3) (an O(n^2) nested loop with a nested O(n) substring operation)
	 * */
	
	public static int distinctSubstring(String str) { 
		// Put all distinct substring in a HashSet 
		Set<String> result = new HashSet<String>(); 

		// List All Substrings 
		for (int i = 0; i <= str.length(); i++) { 
			for (int j = i + 1; j <= str.length(); j++) { 

				// Add each substring in Set 
				result.add(str.substring(i, j)); 
			} 
		} 

		// Return size of the HashSet 
		return result.size(); 
	}
	
}
