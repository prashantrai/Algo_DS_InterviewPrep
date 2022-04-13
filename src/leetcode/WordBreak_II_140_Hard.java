package leetcode;

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
