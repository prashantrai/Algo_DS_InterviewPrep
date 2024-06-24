package Facebook;

import java.util.HashMap;
import java.util.Map;

public class CustomSortString_791_Medium {

	public static void main(String[] args) {
		String order = "bcafg", s = "abcd";
		String res = customSortString(order, s);
		System.out.println("1. Expected: bcad, Actual: " + res);
		
		res = customSortString2(order, s);
		System.out.println("2. Expected: bcad, Actual: " + res);
	}
	
	
	/* Ref: Leetcode solutions
	 * 
    Time Complexity: O(N), It takes O(N) time to populate the frequency table, 
    and all other hashmap operations performed take O(1) time in the average case. 
    Building the result string also takes O(N) time because each letter from s is 
    appended to the result in the custom order, making the 
    overall time complexity O(N).

    Space Complexity: O(N), A hash map and a result string are created, 
    which results in an additional space complexity of O(N).
    */
    
	
	// Solution 1: Using array as frequency table
    public static String customSortString (String order, String s) {
        
        // Frequency array to count occurrences of each character in s
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        // Add characters from 'order' in the order they appear
        StringBuilder res = new StringBuilder();
        for(char c : order.toCharArray()) {
            // Append the character freq[c - 'a'] times to the result
            while(freq[c - 'a'] > 0) {
                res.append(c);
                freq[c - 'a']--;
            }    
        }
        
        // Append the rest of the characters that were not in 'order'
        for(int i=0; i<26; i++) {
            while (freq[i] > 0) {
                res.append((char) (i+'a'));
                freq[i]--;
            }
        }
        
        return res.toString();
    }
    
    
    // Solution 2: Using HashMap
    public static String customSortString2(String order, String s) {
     
        // Create a frequency table
        Map<Character, Integer> freq = new HashMap<>();
        
        // Initialize frequencies of letters
        // freq[c] = frequency of char c in s
        for(char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        // Iterate order string to append to result
        StringBuilder result = new StringBuilder();
        for(char c : order.toCharArray()) {
            while(freq.getOrDefault(c, 0) > 0) {
                result.append(c);
                freq.put(c, freq.get(c) - 1);
            }
        }
        
         // Iterate through freq and append remaining letters
        // This is necessary because some letters may not appear in `order`
        for(char c : freq.keySet()) {
            int count = freq.get(c);
            while(count > 0) {
                result.append(c);
                count--;
            }
        }
        
        // Return the result
        return result.toString();
    }

}
