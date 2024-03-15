package Apple;

import java.util.HashMap;
import java.util.Map;

public class LongestPalindromeByConcatenatingTwoLetterWords_2131_Medium {

	public static void main(String[] args) {
		String[] words = {"lc","cl","gg"};
		String[] words2 = {"ab","ty","yt","lc","cl","ab"};
		
		// HashMap
		System.out.println("1. Expected: 6,  Actual: " + longestPalindrome2(words));
		
		System.out.println("2. Expected: 8,  Actual: " + longestPalindrome2(words2));
		
		
		// 2-D Array
		System.out.println("3. Expected: 6,  Actual: " + longestPalindrome(words));
		
		System.out.println("4. Expected: 8,  Actual: " + longestPalindrome(words2));

	}

	/* 
	 * https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/discuss/1675343/Python3-Java-C%2B%2B-Counting-Mirror-Words-O(n)
    
	    2 letter words can be of 2 types:
	        - Where both letters are same
	        - Where both letters are different
	    
	    Based on the above information:
	        -If we are able to find the mirror of a word, ans += 4
	        - The variable unpaired is used to store the number of unpaired words 
	            with both letters same.
	        - Unpaired here means a word that has not found its mirror word.
	        - At the end if unpaired same letter words are > 0, 
	          we can use one of them as the center of the palindromic string.
    */
    
    
    /*
    Approach: Using HashMap
    
    Time: O(N)
    Space: O(N)
    */
    public static int longestPalindrome2(String[] words) {
        
        Map<String, Integer> map = new HashMap<>();
        int unpaired = 0; // word that has not found it's mirror word
        int ans = 0;
        
        for(String w : words) {
            if(!map.containsKey(w))
                    map.put(w, 0);

            // both letters are same
            if(w.charAt(0) == w.charAt(1)) {
                
                if(map.get(w) > 0) {
                    unpaired--;
                    map.put(w, map.get(w)-1);
                    ans += 4;
                } else {
                    map.put(w, map.get(w)+1);
                    unpaired++;
                }
            
            } else {
                // both letters are diffrent
                // reverse and check if map contains
                String reverse = Character.toString(w.charAt(1)) 
                    + Character.toString(w.charAt(0));
                
                if(map.containsKey(reverse) && map.get(reverse) > 0) {
                    ans += 4;
                    map.put(reverse, map.get(reverse)-1);
                    
                } else {
                    map.put(w, map.get(w) + 1);
                }
                
            }
        }
        if(unpaired > 0) ans += 2;
        
        return ans;
    }
    
    
    /*
    Approach: 2-D Array
    
    Time: O(N)
    Space: O(N)
    */
    public static int longestPalindrome(String[] words) {
        int[][] counter = new int[26][26];
        int ans = 0;
        
        for(String w : words) {
            int a = w.charAt(0) - 'a';
            int b = w.charAt(1) - 'a';
            
            if(counter[b][a] > 0) {
                ans += 4;
                counter[b][a]--;
            } else {
                counter[a][b]++;
            }
        }
        for(int i=0; i<26; i++) {
            if(counter[i][i] > 0) {
                ans += 2;
                break;
            }
        }
        return ans;
    }
    
}
	

