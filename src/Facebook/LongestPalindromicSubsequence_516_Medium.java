package Facebook;

public class LongestPalindromicSubsequence_516_Medium {

	public static void main(String[] args) {
		String s = "bbbab"; // Output: 4
				
	}
	
	
	// Dynamic Programming with Space Optimization
	// Leetcode solution
	/*
	 * Time complexity: O(n^2)
	 * 		- Initializing the dp array takesO(n^2) time.
	 * 		- We fill the dp array which takes O(n^2) time.
	 * 
	 * Space complexity:O(n) The dp and dpPrev consumes O(n) space.	 
	 * */
	public static int longestPalindromeSubseq_Iterative_SpaceOptimized(String s) {
		int n = s.length();
        int[] dp = new int[n];
        int[] dpPrev = new int[n];
        
        for(int i=n-1; i>=0; i--) {
        	dp[i] = 1; // sicne all single char in a string is a palindrome, which length is 1.
        	
        	for(int j=i+1; j<n; j++) {
        		if(s.charAt(i) == s.charAt(j)) {
        			
        			/* If the first and the last characters are the same, 
            		 * i.e., s[i] == s[j], we include these two characters 
            		 * in the palindromic subsequence and add it to the longest 
            		 * palindromic subsequence formed using the substring from 
            		 * index i + 1 to j - 1 (inclusive). 
            		 */
        			dp[j] = dpPrev[j-1] + 2;
        		}
        		else {
        			/* Otherwise, if the first and the last characters do not match, 
        			 * we look for the longest palindromic subsequence in both the 
        			 * substrings formed after ignoring the first and last characters. 
        			 * We pick the maximum of these two.  */
        			dp[j] = Math.max(dpPrev[j], dp[j-1]);
        		}
        	}
        	dpPrev = dp.clone();
        }
        return dp[n-1];
	}
	
	// Leetcode solution
	// Iterative Dynamic Programming - bottom-up approach 
	/*
	 * Time complexity: O(n^2)
	 * 		- Initializing the dp array takesO(n^2) time.
	 * 		- We fill the dp array which takes O(n^2) time.
	 * Space complexity:O(n^2) The dp array consumes O(n^2) space.	 
	 * */
	public static int longestPalindromeSubseq_Iterative(String s) {
		int n = s.length();
        int[][] dp = new int[n][n];
        
        for(int i=n-1; i>=0; i--) {
        	
        	dp[i][i] = 1; // sicne all single char in a string is a palindrome, which length is 1.
        	
        	for(int j=i+1; j<n; j++) {
        		/* If the first and the last characters are the same, 
        		 * i.e., s[i] == s[j], we include these two characters 
        		 * in the palindromic subsequence and add it to the longest 
        		 * palindromic subsequence formed using the substring from 
        		 * index i + 1 to j - 1 (inclusive). 
        		 * 
        		 * We perform dp[i][j] = dp[i + 1][j - 1] + 2. 
        		 */
        		
        		if(s.charAt(i) == s.charAt(j)) {
        			dp[i][j] = dp[i+1][j-1] + 2;
        		}
        		else {
        			/* Otherwise, if the first and the last characters do not match, 
        			 * we look for the longest palindromic subsequence in both the 
        			 * substrings formed after ignoring the first and last characters. 
        			 * We pick the maximum of these two.  */
        			dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
        		}
        	}
        }
        return dp[0][s.length()-1];
        
    }

	
	/* Leetcode Solution
	Time complexity: O(n^2)
		Initializing the memo array takes O(n^2) time.
		Since there are 
		O(n^2) states that we need to iterate over, 
		the recursive function is calledO(n^2) times.
	
	Space complexity:O(n^2), The memo array consumesO(n^2)space.
	 * */
	public int longestPalindromeSubseq_Recrusive(String s) {
        int n = s.length();
        int[][] memo = new int[n][n];
        return lps(s, 0, n - 1, memo);
    }

    private int lps(String s, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return 1;
        }

        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = lps(s, i + 1, j - 1, memo) + 2;
        } else {
            memo[i][j] = Math.max(lps(s, i + 1, j, memo), lps(s, i, j - 1, memo));
        }
        return memo[i][j];
    }
	
}
