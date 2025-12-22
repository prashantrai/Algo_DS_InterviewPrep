package Oracle;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters_3_Medium {

	public static void main(String[] args) {

		String s = "abcabcbb";
		System.out.println("Expected: 3, Actual: " + lengthOfLongestSubstring(s));
		System.out.println("Expected: 3, Actual: " + lengthOfLongestSubstring_2(s));
		
		s = "bbbbb";
		System.out.println("\nExpected: 1, Actual: " + lengthOfLongestSubstring(s));
		System.out.println("Expected: 1, Actual: " + lengthOfLongestSubstring_2(s));

		s = "pwwkew";
		System.out.println("\nExpected: 3, Actual: " + lengthOfLongestSubstring(s));
		System.out.println("Expected: 3, Actual: " + lengthOfLongestSubstring_2(s));
	}
	
	/*
	 * Time: O(N)
	 * Space: O(N) 
	 */
	
	public static int lengthOfLongestSubstring(String s) {
        int i=0;
        int j=0;
        int max = 0;
        Set<Character> set = new HashSet<>();
        
        while (j < s.length()) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                max = Math.max(max, set.size());
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        
        return max;
    }
	
	//print the string (same code as above)
	public static int lengthOfLongestSubstring_2(String s) {
        int i=0;
        int j=0;
        int max = 0;
        String maxStr = "";
        Set<Character> set = new HashSet<>();
        
        while (j < s.length()) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                max = Math.max(max, set.size());
            } else {
            	String currStr = s.substring(i,j); // in case we want to print/return the substring as well
            	maxStr = maxStr.length() < currStr.length() ? currStr : maxStr;
                set.remove(s.charAt(i));
                i++;
            }
        }
        
        System.out.println("maxStr: " + maxStr);
        return max;
    }

}
