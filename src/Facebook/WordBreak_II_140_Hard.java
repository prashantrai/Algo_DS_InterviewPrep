package Facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak_II_140_Hard {

	public static void main(String[] args) {
		String s = "catsanddog";
		List<String> wordDict = Arrays.asList("cats","dog","sand","and","cat");
		System.out.println("Expected: [cats and dog, cat sand dog], Actual: " + wordBreak(s, wordDict));
		
		s = "catsandog";
		System.out.println("Expected: [], Actual: " + wordBreak(s, wordDict));
		
		s = "pineapplepenapple";
		wordDict = Arrays.asList("apple","pen","applepen","pine","pineapple");
		System.out.println("Expected: [pine apple pen apple, pineapple pen apple, pine applepen apple]");
		System.out.println("Actual: " + wordBreak(s, wordDict));
		
	}
	
	
	/* Question:
	 * Given a string s and a dictionary of strings wordDict, 
	 * add spaces in s to construct a sentence where each word 
	 * is a valid dictionary word. Return all such possible sentences in any order.

		Note that the same word in the dictionary may be reused multiple times in the segmentation.

		Example 1:	
		
		Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
		Output: ["cats and dog","cat sand dog"]
	 * 
	 */
	
	/* For time complexity refer below links: 
	 * https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS/215095
	 * https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS/43414
	 * 
	 * 
	 * 
	 * Time: O(n*2^n) where n is the number of characters. In a worst case scenario
	 * Space: O(n*2^n)
	 * Refer https://salonikaurone.medium.com/leetcode-word-break-ii-explained-d41ecfbe8fc5 for details 
	 * 
	 * For Space: Say
	 * 	s = aaaaaa
		wordDict = [a, aa, aaa, aaaa, aaaaa, aaaaaa]
		
		Our memo would look something like: 
		{
		    "a": ["a"] ----------------------------------------- 2^0 items,
		    "aa": ["a a","aa"] --------------------------------- 2^1 items,
		    "aaa": ["a a a", "aa a", "a aa", "aaa"] ------------ 2^2 items,
		    ...
		}
		That explains the space.
		
		The time could be explained by the number of executions of the subproblems. 
		Namely, in the worst case we'd have to make n calls of the helper function, 
		and each one of those calls would have 2^n append calls made, leaving us with O(n * 2^n)
	 * 
	 */
	
	public static List<String> wordBreak(String s, List<String> wordDict) {
        Map<String, List<String>> cache = new HashMap<>();
        return helper(s, new HashSet<>(wordDict), cache);
    }
    
    private static List<String> helper(String s, Set<String> dict, Map<String, List<String>> cache) {
        List<String> res = new ArrayList<>();
        
        if(s.length() == 0) { 
            res.add("");
            return res;
        }
        
        if(cache.containsKey(s)) return cache.get(s);
        
        for(int i=1; i<=s.length(); i++) {
            String temp = s.substring(0, i);
            
            if(dict.contains(temp)) {
                List<String> tempList = helper(s.substring(i), dict, cache);
            
                for(String str : tempList) {
                	if(str.isEmpty()) 
                        res.add(temp);
                    else 
                    	res.add(temp + " " + str);
                }
            }
        }
        
        cache.put(s, res);
        return res;
    }

}
