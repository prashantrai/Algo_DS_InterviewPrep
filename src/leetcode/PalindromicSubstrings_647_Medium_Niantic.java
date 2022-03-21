package leetcode;

public class PalindromicSubstrings_647_Medium_Niantic {

	public static void main(String[] args) {
		String s = "abc";
		System.out.println("Expected: 3, Actual: " + countSubstrings(s));
		System.out.println("Expected: 3, Actual: " + countSubstrings2(s));
		
		s = "aaa";
		System.out.println("Expected: 6, Actual: " + countSubstrings(s));
		System.out.println("Expected: 6, Actual: " + countSubstrings2(s));
		
	}

	//another approach - Time: O(n^2) Space: O(1) :: Faster than DP solution
	// https://leetcode.com/problems/palindromic-substrings/discuss/105689/Java-solution-8-lines-extendPalindrome
	// For better explanation: https://leetcode.com/problems/palindromic-substrings/discuss/105688/Very-Simple-Java-Solution-with-Detail-Explanation
	public static int countSubstrings2(String s) {
        int count=0;
        for(int i=0;i<s.length();i++){
            count+=extractPalindrome(s,i,i);//odd length
            count+=extractPalindrome(s,i,i+1);//even length
        }
        return count;
    }
    private static int extractPalindrome(String s, int left, int right){
        int count=0;
        while(left>=0 && right<s.length()&& (s.charAt(left)==s.charAt(right))){
            left--;
            right++;
            count++;
        }
        return count;
    }
	
	
	
	//Time and space : O(n^2)
    // https://leetcode.com/problems/palindromic-substrings/discuss/1777088/Java-DP
    // https://www.youtube.com/watch?v=pp8K5C2hMr4&ab_channel=AlgorithmsMadeEasy
	public static int countSubstrings(String s) {

		boolean[][] dp = new boolean[s.length()][s.length()];
		int res = 0;

		for (int i = s.length() - 1; i >= 0; i--) {
			dp[i][i] = true;
			res++;
			for (int j = i + 1; j < s.length(); j++) {
				dp[i][j] = false;
				if (s.charAt(i) == s.charAt(j)) {
					if (j == i + 1) {
						dp[i][j] = true;
					} else {
						dp[i][j] = dp[i + 1][j - 1];
					}
				}
				if (dp[i][j])
					res++;
			}
		}

		return res;
	}

}
