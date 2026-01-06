package PaloAltoNetworks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagramsInAString_438_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* 
    We'll use a sliding window approach with character frequency arrays:

    1. Edge Case Check: If p is longer than s, return empty list immediately
    2. Frequency Arrays: Create two arrays of size 26 to count character frequencies:
        pCount: frequency of characters in pattern p
        sCount: frequency of characters in current window of s
    3. Initialize Window: Fill sCount with first p.length() characters of s
    4. Check First Window: If sCount equals pCount, add index 0 to result
    5. Slide Window:
        - Remove leftmost character from window (decrement its count)
        - Add new right character to window (increment its count)
        - After each slide, check if arrays match and add starting index if they do
    6. Array Comparison: Compare all 26 positions of both arrays
    */

    // Time Complexity: O(n) where n = length of s
    // Space Complexity: O(1) since we use fixed-size arrays (26 elements)
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        
        // Edge case: if p is longer than s, no anagrams possible
        if(s.length() < p.length()) 
            return result;
        
        // Frequency arrays for 26 lowercase letters
        int[] pCount = new int[26];
        int[] sCount = new int[26];
        
        // Build frequency array for pattern p
        for(char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        // Initialize sliding window with first p.length() characters
        int winSize = p.length();
        for(int i=0; i<winSize; i++) {
            sCount[s.charAt(i) - 'a']++;
        }

        // Check if first window is an anagram
        if(Arrays.equals(pCount, sCount)) {
            result.add(0);
        }

        // Slide the window: remove leftmost, add rightmost
        for(int i = winSize; i<s.length(); i++) {
            // Remove the leftmost character (i - windowSize)
            // Current window should be indices [1,2] = "ba"
            // Character at position i - windowSize = 2 - 2 = 0 → 'a'
            sCount[s.charAt(i - winSize) - 'a']--;

            // Add the new rightmost character (i)
            // Add rightmost: Character at position i = 2 → 'a', sCount['a']++
            sCount[s.charAt(i) - 'a']++;

            // Check if current window is an anagram
            if(Arrays.equals(pCount, sCount)) {
                //Add starting index i - windowSize + 1 = 2 - 2 + 1 = 1
                result.add(i - winSize + 1);
            }

        }

        return result;

    }

}
