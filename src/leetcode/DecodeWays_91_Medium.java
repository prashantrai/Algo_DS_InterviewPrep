package leetcode;

import java.util.HashMap;

public class DecodeWays_91_Medium {

	public static void main(String[] args) {

		//--All solution have been just copied from Article [as it's premium and won't be accessible later]
		//--Recursive
		Solution_Recursive sr = new Solution_Recursive();
		String s1 = "12";
		int res = sr.numDecodings(s1);
		System.out.println("Expected: 2, Actual: "+res);
		
		String s2 = "226";
		res = sr.numDecodings(s2);
		System.out.println("Expected: 3, Actual: "+res); //--need to debug, result is not right
		
		
		//--Iterative
		Solution_Iterative si = new Solution_Iterative();
		res = si.numDecodings(s1);
		System.out.println("Expected: 2, Actual: "+res);
		
		res = si.numDecodings(s2);
		System.out.println("Expected: 3, Actual: "+res);
		
		
		res = numDecodings(s1);
		System.out.println("Expected: 2, Actual: "+res);
		
	}
	
	/**
	 Follow up : Optimized Iterative Approach, O(1) Space
	 In Approach 2 we are using an array dp to save the results for future. 
	 As we move ahead character by character of the given string, we look back only two steps. 
	 
	 For calculating dp[i] we need to know dp[i-1] and dp[i-2] only. 
	 Thus, we can easily cut down our O(N) space requirement to O(1) by using only two 
	 variables to store the last two results.
	 * */
	
	public static int numDecodings(String s) {
	      
        if(s == null || s.length() == 0) 
            return 0;
        
        // DP array to store the subproblem results
        int[] dp = new int[s.length()+1];
        dp[0] = 1;
        
        // Ways to decode a string of size 1 is 1. Unless the string is '0'.
        // '0' doesn't have a single digit decode.
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        
        for(int i=2; i<dp.length; i++) {
            
            // single digit
            if(s.charAt(i-1) != '0') {
                dp[i] += dp[i-1];
            }
            
            // two digit
            int twoDigit = Integer.parseInt(s.substring(i-2, i));
            if(twoDigit >= 10 && twoDigit <=26) {
                dp[i] += dp[i-2];
            }
        }
        
        return dp[s.length()];
    }
	
	

	/*
	 * Algorithm - From Article
	 * 
	 * Enter recursion with the given string i.e. start with index 0.
	 * 
	 * For the terminating case of the recursion we check for the end of the string.
	 * If we have reached the end of the string we return 1.
	 * 
	 * Every time we enter recursion it's for a substring of the original string.
	 * For any recursion if the first character is 0 then terminate that path by
	 * returning 0. Thus this path won't contribute to the number of ways.
	 * 
	 * Memoization helps to reduce the complexity which would otherwise be
	 * exponential. We check the dictionary memo to see if the result for the given
	 * substring already exists.
	 * 
	 * If the result is already in memo we return the result. Otherwise the number
	 * of ways for the given string is determined by making a recursive call to the
	 * function with index + 1 for next substring string and index + 2 after
	 * checking for valid 2-digit decode. The result is also stored in memo with key
	 * as current index, for saving for future overlapping subproblems.
	 */

	/*
	 * Complexity Analysis::
	 * 
	 * Time Complexity: O(N), where N is length of the string. Memoization helps in
	 * pruning the recursion tree and hence decoding for an index only once. Thus
	 * this solution is linear time complexity.
	 * 
	 * Space Complexity: O(N). The dictionary used for memoization would take the
	 * space equal to the length of the string. There would be an entry for each
	 * index value. The recursion stack would also be equal to the length of the
	 * string.
	 */

	// --From Article
	static class Solution_Recursive {

		HashMap<Integer, Integer> memo = new HashMap<>();

		private int recursiveWithMemo(int index, String str) {

			// If you reach the end of the string
			// Return 1 for success.
			if (index == str.length()) {
				return 1;
			}

			// If the string starts with a zero, it can't be decoded
			if (str.charAt(index) == '0') {
				return 0;
			}

			if (index == str.length() - 1) {
				return 1;
			}

			// Memoization is needed since we might encounter the same sub-string.
			if (memo.containsKey(index)) {
				return memo.get(index);
			}

			int ans = recursiveWithMemo(index + 1, str);
			if (Integer.parseInt(str.substring(index, index + 2)) <= 26) {
				ans += recursiveWithMemo(index + 2, str);
			}

			// Save for memoization
			memo.put(index, ans);

			return ans;
		}

		public int numDecodings(String s) {
			if (s == null || s.length() == 0) {
				return 0;
			}
			return recursiveWithMemo(0, s);
		}
	}

	// ------- Iterative Approach

	/*
	 * Algorithm
	 * 
	 * If the string s is empty or null we return the result as 0.
	 * 
	 * Initialize dp array. dp[0] = 1 to provide the baton to be passed.
	 * 
	 * If the first character of the string is zero then no decode is possible hence
	 * initialize dp[1] to 0, otherwise the first character is valid to pass on the
	 * baton, dp[1] = 1.
	 * 
	 * Iterate the dp array starting at index 2. The index i of dp is the i-th
	 * character of the string s, that is character at index i-1 of s.
	 * 
	 * We check if valid single digit decode is possible. This just means the
	 * character at index s[i-1] is non-zero. Since we do not have a decoding for
	 * zero. If the valid single digit decoding is possible then we add dp[i-1] to
	 * dp[i]. Since all the ways up to (i-1)-th character now lead up to i-th
	 * character too.
	 * 
	 * We check if valid two digit decode is possible. This means the substring
	 * s[i-2]s[i-1] is between 10 to 26. If the valid two digit decoding is possible
	 * then we add dp[i-2] to dp[i].
	 * 
	 * Once we reach the end of the dp array we would have the number of ways of
	 * decoding string s.
	 */

	/*
	 * Complexity Analysis
	 * 
	 * Time Complexity: O(N), where N is length of the string. We iterate the length
	 * of dp array which is N+1.
	 * 
	 * Space Complexity: O(N). The length of the DP array.
	 * 
	 */

	// --From Article
	static class Solution_Iterative {

		public int numDecodings(String s) {

			if (s == null || s.length() == 0) {
				return 0;
			}

			// DP array to store the subproblem results
			int[] dp = new int[s.length() + 1];
			dp[0] = 1;
			// Ways to decode a string of size 1 is 1. Unless the string is '0'.
			// '0' doesn't have a single digit decode.
			dp[1] = s.charAt(0) == '0' ? 0 : 1;

			for (int i = 2; i < dp.length; i += 1) {

				// Check if successful single digit decode is possible.
				if (s.charAt(i - 1) != '0') {
					dp[i] += dp[i - 1];
				}

				// Check if successful two digit decode is possible.
				int twoDigit = Integer.valueOf(s.substring(i - 2, i));
				if (twoDigit >= 10 && twoDigit <= 26) {
					dp[i] += dp[i - 2];
				}
			}
			return dp[s.length()];

		}
	}

}
