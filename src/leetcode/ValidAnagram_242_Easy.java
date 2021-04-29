package leetcode;

import java.util.Arrays;

public class ValidAnagram_242_Easy {

	public static void main(String[] args) {
		String s = "anagram", t = "nagaram";
		System.out.println("Expected: true, Acual: "+isAnagram(s, t));
		
		s = "rat"; 
		t = "car";
		System.out.println("Expected: false, Acual: "+isAnagram(s, t));
		
	}

	public static boolean isAnagram(String s, String t) {
        return buildAndGetKey(s)
                    .equals(buildAndGetKey(t));
    }
    
    private static String buildAndGetKey(String s) {
        int[] count = new int[26];
        for(char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        return Arrays.toString(count);
    }
}
