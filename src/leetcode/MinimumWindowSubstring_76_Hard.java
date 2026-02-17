package leetcode;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring_76_Hard {

	public static void main(String[] args) {

		String s = "ADOBECODEBANC", t = "ABC";
		
		System.out.println("Expected: BANC, Actual: " + minWindow(s, t));
	}
	
	/**
	 * Both solutions are working and can be used during the interview.
	 * Although, 2 Map solution is more readable 
	 * */
	
	// 2-Map solution
	/* Time: O(N)
    or, O(∣S∣+∣T∣), where |S| and |T| represent the lengths of strings S and T. 

    Space: O(1), Alphabets are fixed (letters), so this is O(1) auxiliary space 
    in practice, or O(K) where K = number of distinct chars.
    */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0) {
            return "";
        }

        // Map of required characters and their counts/freq from t
        // this will give us the unique chars we need to search for min window
        Map<Character, Integer> need = new HashMap<>();
        for(char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // number/count of distinct chars we need to satisfy
        int required = need.size();

        // Map of current window character counts
        Map<Character, Integer> window = new HashMap<>();

        // how many distinct chars currently satisfy their required count
        int formed = 0;

        int left = 0, right = 0; // left right pointer for sliding win

        // record the min size of window with all chars
        int minLen = Integer.MAX_VALUE; 

        int minStart = 0;

        // expand with right ptr record the freq of chars in 's' 
        // update formed 
        while (right < s.length()) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);

            // If this char is needed and now its count matches 
            // the required count, increase formed
            if(need.containsKey(c) 
                && window.get(c).intValue() == need.get(c).intValue()) {
                
                formed++;
            }

            // Try to shrink the window from the left while it's valid
            while(left <= right && formed == required) {
                // shrink from left while window is valid
                // Update answer if this window is smaller than current minLen
                if(right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                // to start reducing the win size we have to reduce the count 
                // in the Map window for that char (at left ptr) as we'll move 
                // to next position and will update count for the 
                // char at new position
                char d = s.charAt(left);
                window.put(d, window.get(d) - 1);

                // if 'd' was required (i.e. in Map need with same count/freq 
                // as of current window), and we have removed that from the 
                // window then current window is invalid and we have to 
                // reduce the formed by 1
                if(need.containsKey(d) && window.get(d) < need.get(d)) {
                    formed--;
                }
                
                // move left pointer
                left++;
            }

            right++; 
        }

        /* 
        Inner loop shrinks it to exactly "BANC" and updates minLen = 4, 
        minStart = 9.
        Result: s.substring(9, 9+4) = "BANC". */

        return minLen == Integer.MAX_VALUE ? "" 
            : s.substring(minStart, minStart + minLen); 

    }
	
	
	/* To understand the algorithm follow below videos: 
	 * 		https://www.youtube.com/watch?v=PzAHfUY6GGk&ab_channel=EdRoh
	 * 		https://www.youtube.com/watch?v=jSto0O4AJbM&ab_channel=NeetCode
	 * 		https://www.youtube.com/watch?v=0GOyCIJ2ajQ&ab_channel=AmellPeralta
	 * 
	 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
	 * 
	 * Time: O(N), Space: O(1)
	 * */
	
	public static String minWindow2(String s, String t) {
        
        if(s == null || t == null || s.length() == 0) return "";
        
        //record char freq
        int[] charCount = new int[128];
        int left = 0, right = 0;
        int minStart = 0; //to record min substring start index
        int minLen = Integer.MAX_VALUE;
        int count = 0;
        
        for(char c : t.toCharArray()) {
            charCount[c]++; //increament the count/freq
        }
        
        for(right=0; right<s.length(); right++) {
            char c1 = s.charAt(right);
            if(charCount[c1] > 0) {    
                count++;
            }
            charCount[c1]--;
            while(count == t.length()) { // we have found all the 
                if(minLen > right - left) {
                    minLen = right-left+1;
                    minStart = left;
                }
                char c2 = s.charAt(left);
                charCount[c2]++;
                if(charCount[c2] > 0) {
                    count--;
                }
                left++;            
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart+minLen);
        
    }

}
