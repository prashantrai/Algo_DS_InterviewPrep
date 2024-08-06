package doordash;

import java.util.Arrays;

public class SubsequenceWithTheMinimumScore_2565_Hard {

	public static void main(String[] args) {
		String s = "abacaba", t = "bzaa";
		System.out.println("Expected: 1, Actual: " + minimumScore(s, t));
		System.out.println("Expected: 1, Actual: " + minimumScore2(s, t));
		
		s = "cde"; t = "xyz";
		System.out.println("Expected: 3, Actual: " + minimumScore(s, t));
		System.out.println("Expected: 3, Actual: " + minimumScore2(s, t));
	}
	
	/* 
    Refer: https://leetcode.com/problems/subsequence-with-the-minimum-score/discuss/3247392/Java-left-and-right

    Time: O(N)
    Space: O(N)
    */
    
    public static int minimumScore(String s, String t) {
        int m = s.length();
        int n = t.length();
        
        // Fill the prefix array with the rightmost positions
        int[] left = new int[m];
        for (int i = 0, j = 0; i < m; i++) {
            if (j < n && s.charAt(i) == t.charAt(j)) {
                ++j;
            }
            left[i] = j;
        }

        // Fill the suffix array with the leftmost positions
        int[] right = new int[m];
        for (int i = m - 1, j = n - 1; i >= 0 ; i--) {
            if (j >= 0 && s.charAt(i) == t.charAt(j)) {
                --j;
            }
            right[i] = j;
        }
        
        /*
         * n - left[m - 1]: 
         * 	# This represents the number of characters in `t` that cannot be 
         *    matched by the prefix of `s` up to its last character.
         *  # Essentially, it gives the number of characters in t that need 
         *    to be removed if only considering the prefix match of s with t.
         *    
         *  right[0] + 1:
         * 	# This represents the number of characters in 't' that cannot be 
         *    matched by the suffix of 's' starting from its first character.
         * 	# It gives the number of characters in t that need to be removed 
         *    if only considering the suffix match of s with t.
         *    
         *  The goal is to find the minimum number of characters to remove 
         *  from t such that the remaining characters can be matched in s.

			n - left[m - 1]: If we remove all characters of t that are not matched 
				by the prefix of s ending at m - 1, this would be the required removal.
	
			right[0] + 1: If we remove all characters of t that are not matched by 
				the suffix of s starting at 0, this would be the required removal.

			Example for clarity:
			For s = "abacaba" and t = "bzaa":
			
			left[m - 1] might be the length of the longest prefix of t matched by s.
			
			right[0] might be the length of the longest suffix of t matched by s.
			
			By calculating both n - left[m - 1] and right[0] + 1, we cover 
			the cases where we match as much as possible from the beginning 
			and the end, then take the minimal required deletions.
			
			Thus, this line of code helps in initializing the minimum score for 
			the number of characters to be removed from t for it to be a subsequence of s.

         *   
         * */
        
        int min = Math.min(n - left[m - 1], right[0] + 1);
        
        for (int i = 0; i + 1 < m; i++) {
        	int score = right[i + 1] - left[i] + 1; // right - left + 1
            min = Math.min(min, Math.max(0, score));
        }
        return min;
    }

    
    
    
    // Another Approach - debug to understand
    // Time and Space: O(N)
    public static int minimumScore2(String s, String t) {
        int ss = s.length();
        int st = t.length();
        int k = st - 1;
        
        int[] dp = new int[st];
        
        Arrays.fill(dp, -1);
        
        for (int i = ss - 1; i >= 0 && k >= 0; --i) {
            if (s.charAt(i) == t.charAt(k)) {
                dp[k--] = i;
            }
        }
        
        int res = k + 1;
        
        for (int i = 0, j = 0; i < ss && j < st && res > 0; ++i) {
            if (s.charAt(i) == t.charAt(j)) {
                
            	for (; k < st && dp[k] <= i; ++k);
                
                res = Math.min(res, k - (++j));
            }
        }
        
        return res;
    }
    
}


/*

Calculation of min:

left[m - 1]: This is the length of the prefix of t that can be matched by the entire string s.

right[0]: This is the length of the suffix of t that can be matched starting from the beginning of s.
Expressions in the min calculation:


n - left[m - 1]:
This represents the number of characters in t that cannot be matched by the prefix of s up to its last character.
Essentially, it gives the number of characters in t that need to be removed if only considering the prefix match of s with t.

right[0] + 1:
This represents the number of characters in t that cannot be matched by the suffix of s starting from its first character.
It gives the number of characters in t that need to be removed if only considering the suffix match of s with t.
 
 
Explanation:

	The line int min = Math.min(n - left[m - 1], right[0] + 1); initializes the minimum score. 
	
	The goal is to find the minimum number 
	of characters to remove from t such that the remaining characters can be matched in s.
	
	n - left[m - 1]: If we remove all characters of t that are not matched 
		by the prefix of s ending at m - 1, this would be the required removal.
		
	right[0] + 1: If we remove all characters of t that are not matched by 
		the suffix of s starting at 0, this would be the required removal.
		
	Why do we need both?
	These two values represent two extreme scenarios:
	
	Matching as much as possible from the start of s.
	Matching as much as possible from the end of s.
	Taking the minimum of these two values ensures that we are considering 
	the scenario where the fewest characters are removed.
	
	Example for clarity:
	For s = "abacaba" and t = "bzaa":
	
	left[m - 1] might be the length of the longest prefix of t matched by s.
	
	right[0] might be the length of the longest suffix of t matched by s.
	
	By calculating both n - left[m - 1] and right[0] + 1, we cover 
	the cases where we match as much as possible from the beginning 
	and the end, then take the minimal required deletions.
	
	Thus, this line of code helps in initializing the minimum score for 
	the number of characters to be removed from t for it to be a subsequence of s.
 * */
