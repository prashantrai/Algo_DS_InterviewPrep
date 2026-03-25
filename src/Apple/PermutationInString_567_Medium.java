package Apple;

import java.util.Arrays;

public class PermutationInString_567_Medium {

	public static void main(String[] args) {

        // Basic examples
        runTest("ab", "eidbaooo", true);   // permutation "ba"
        runTest("ab", "eidboaoo", false);
        runTest("a", "ab", true);          // "a" exists
        runTest("ab", "a", false);         // s1 longer than s2

        // Edge cases
        runTest("a", "a", true);           // exact match
        runTest("a", "b", false);          // single char, no match
        runTest("aa", "aa", true);         // same, repeated char
        runTest("aa", "ab", false);        // counts differ
        runTest("abc", "ccccbbbbaaaa", false); // has some but no full perm
        runTest("abc", "bbbca", true);     // "bca" is a perm

        // Multiple occurrences
        runTest("ab", "abab", true);       // multiple windows match
        runTest("xyz", "afdgzyxksldfm", true); // "zyx" is a perm

        // Larger random-like strings
        runTest("abc", "ccccabcbaccc", true);  // multiple permutations

        // No letters from s1 in s2
        runTest("abc", "xxxxxx", false);
    }

    private static void runTest(String s1, String s2, boolean expected) {
        boolean result = checkInclusion(s1, s2);
        System.out.printf("s1 = %-8s s2 = %-15s | expected = %-5s got = %-5s %s%n",
                "\"" + s1 + "\"",
                "\"" + s2 + "\"",
                expected,
                result,
                (result == expected ? "OK" : "FAIL"));
    }
    
	
	// Time: O(n + m), where n = s1.length(), m = s2.length().
    //       O(len1 + len2 * 26) = O(len1 + len2), 
    //Space: O(1) fixed-size arrays of length 26.

    private static boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();

        if(len1 > len2) return false;

        int[] need = new int[26]; // counts of each letter in s1.
        int[] window = new int[26]; // counts in the current window of s2.
        
        // 1) Build counts for s1 and the FIRST window of s2
        for(int i=0; i<len1; i++) {
            need[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(need, window)) 
            return true;
        
        // 2) Now slide the window over s2
        for(int i=len1; i<len2; i++) {
            window[s2.charAt(i) - 'a']++;   // add new char (right side)
            window[s2.charAt(i - len1) - 'a']--;   // remove old char (left side)

            if (Arrays.equals(need, window)) 
                return true;
        }
        return false;

    }

}
