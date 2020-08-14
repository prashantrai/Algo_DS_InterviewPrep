package Dropbox;

import java.util.HashMap;
import java.util.Map;

public class WordPattern_290_Easy {

	public static void main(String[] args) {

		/*
		 * Input: pattern = "abba", str = "dog cat cat dog"
		 * Output: true
		 * */
		
		String pattern = "abba", str = "dog cat cat dog";
		System.out.println(wordPattern(pattern, str));
		
	}
	
	//https://leetcode.com/problems/word-pattern/
	// Reference: https://www.youtube.com/watch?v=dnlB0lvz5LY
	/*
	Complexity Analysis

	Time complexity : O(N) where N represents the number of words in the str or the number of characters in the pattern.

	Space complexity : O(M) where M is the number of unique characters in pattern and words in str.
	*/
	public static boolean wordPattern(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        String[] words = str.split(" ");
        if(pattern.length() != words.length) return false;
        
        for(int i=0; i<pattern.length(); i++) {
            char c = pattern.charAt(i);
            String w = words[i];
            
            if(map.containsKey(c)) {
                if(!map.get(c).equals(w)) 
                    return false;
            } else {
                if(map.containsValue(w)) // when current word is mapped to another key
                    return false;
                map.put(c, w);
            }
        }
        return true;
    }
	
}
