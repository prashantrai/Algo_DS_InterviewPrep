package Confluent;

public class WildcardMatching_44_Hard {

	public static void main(String[] args) {
		//String p = "******";
		String p = "a**b***c";
//		System.out.println("->"+removeMultipleStars(p) + "<-");
		
//		System.out.println("Expected: true, Actual: " + isMatch("abc", p));
//		System.out.println("Expected: true, Actual: " + isMatch2("abc", p));
//		System.out.println("Expected: true, Actual: " + isMatch_BackTrack("abc", p));
		System.out.println("Expected: true, Actual: " + isMatch_BackTrack("abc", "a*c"));
		
	}
	
	// dp
	// Time & Space: O(m*n)
	public static boolean isMatch(String s, String p) {
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();

        //replace multiple * with one *
        //e.g a**b***c --> a*b*c
        int writeIndex = 0;
        boolean isFirst = true;
        for ( int i = 0 ; i < pattern.length; i++) {
            if (pattern[i] == '*') {
                if (isFirst) {
                    pattern[writeIndex++] = pattern[i];
                    isFirst = false;
                }
            } else {
                pattern[writeIndex++] = pattern[i];
                isFirst = true;
            }
        }

        System.out.println("Updated Pattern: " + String.valueOf(pattern).substring(0, writeIndex));
        
        boolean T[][] = new boolean[str.length + 1][writeIndex + 1];

        if (writeIndex > 0 && pattern[0] == '*') {
            T[0][1] = true;
        }

        T[0][0] = true;

        for (int i = 1; i < T.length; i++) {
            for (int j = 1; j < T[0].length; j++) {
                if (pattern[j-1] == '?' || str[i-1] == pattern[j-1]) {
                    T[i][j] = T[i-1][j-1];
                } else if (pattern[j-1] == '*'){
                    T[i][j] = T[i-1][j] || T[i][j-1];
                }
            }
        }

        return T[str.length][writeIndex];
    }

	
	// Solution 2
	// Reference: https://leetcode.com/problems/wildcard-matching/discuss/895500/DP-bottom-up-Java-Solution-with-detailed-explanation
	
	// Bottom Up DP
    public static boolean isMatch2(String s, String p) {
        // corner case
        if (s == null || p == null)
            return false;

        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        // 1. dp[0][0] = true, since empty string matches empty pattern
        dp[0][0] = true;

        // 2. dp[0][i] = false
        // since empty pattern cannot match non-empty string

        // 3. dp[j][0]
        // for any continuative '*' will match empty string
        // e.g s='aasffdasda' p='*'/'**'/'***'....
        for (int j = 1; j < n + 1; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[j][0] = dp[j - 1][0];
            }
        }

        // 1. if p.charAt(j) == s.charAt(i), match single character
        // =>>> dp[i][j] = dp[i - 1][j - 1]
        // 2. if p.charAt(j) == '?', '?' match single character
        // =>>> dp[i][j] = dp[i - 1][j - 1]

        // 3. if p.charAt(j) == '*', dp[i][j]=dp[i-1][j]||dp[i][j-1]
        // =>>> a. '*' match empty: dp[i][j]=dp[i-1][j]
        // =>>> b. '*' match multiple characters: dp[i][j]=dp[i][j-1]

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                char charS = s.charAt(i - 1);
                char charP = p.charAt(j - 1);
                if (charS == charP || charP == '?')
                    dp[j][i] = dp[j - 1][i - 1];
                else if (charP == '*')
                    dp[j][i] = dp[j - 1][i] || dp[j][i - 1];

            }
        }

        return dp[n][m];
    }
	
    
    // Back tracking - Leetcode Premium Solution
    public static boolean isMatch_BackTrack(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0;
        int starIdx = -1, sTmpIdx = -1;

        while (sIdx < sLen) {
            // If the pattern character = string character
            // or pattern character = '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                ++sIdx;
                ++pIdx;
    
            // If pattern character = '*'
            } else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                // Check the situation
                // when '*' matches no characters
                starIdx = pIdx;
                sTmpIdx = sIdx;
                ++pIdx;
                          
            // If pattern character != string character
            // or pattern is used up
            // and there was no '*' character in pattern 
            } else if (starIdx == -1) {
                return false;
                          
            // If pattern character != string character
            // or pattern is used up
            // and there was '*' character in pattern before
            } else {
                // Backtrack: check the situation
                // when '*' matches one more character
                pIdx = starIdx + 1;
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
            }
        }

        // The remaining characters in the pattern should all be '*' characters
        for (int i = pIdx; i < pLen; i++) {
            if (p.charAt(i) != '*') {
                return false;
            }
   
        }
        return true;
    }
    
    
}
