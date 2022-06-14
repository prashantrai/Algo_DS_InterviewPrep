package leetcode;

public class RegularExpressionMatching_10_Hard {

	public static void main(String[] args) {

        System.out.println(isMatch("aa","a"));
        System.out.println(isMatch("","a*b*"));
        System.out.println(isMatch("abbbbccc","a*ab*bbbbc*"));
        System.out.println(isMatch("abbbbccc","aa*bbb*bbbc*"));
        System.out.println(isMatch("abbbbccc",".*bcc"));
        System.out.println(isMatch("abbbbccc",".*bcc*"));
        System.out.println(isMatch("abbbbccc",".*bcc*"));
        System.out.println(isMatch("aaa","ab*a*c*a"));
		
	}
	
	
	/* Reference : https://www.youtube.com/watch?v=l3hda49XcDE
	 * https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/RegexMatching.java
	 * 
	 * Time and Space : O(m * n)
	 * */
	
	private static boolean isMatch(String text, String pattern) {
		
		if (pattern == null || pattern.length() == 0) 
			return (text == null || text.length() == 0);
		
		int m = text.length();
		int n = pattern.length();
		
		// we add +1 for the case when both text and pattern will be empty
		// in that case our array should be lenght '1' match empty string 
		boolean[][] dp = new boolean[m+1][n+1]; 
		dp[0][0] = true;  // when both text and pattern is empty then value is true
		
		// Deals with pattern like a* or a*b* or a*b*c*
		for(int i=1; i<dp[0].length; i++) {
			if(pattern.charAt(i-1) == '*') {
				
				/* Why i-2? Because in case of * occurrence of value before * could be 0 or more
				 * so we compare string without * and value before that (i.e. 2 char before and that's why i-2) 
				 * and take that value and set in current position.
				 * 
				 *   e.g. if text = ab and pattern = ab*, in this case when we encounter * we can consider
				 *   occurance of 'b' as zero or more which is a match. We remove 'b' from the comparison 
				 *   and go back 2 index and see if rest of the string matches as we would have already 
				 *   calculated that we can copy that value to current position.   
				 */
				dp[0][i] = dp[0][i-2]; 
			}
		}
		
		for(int i = 1; i < dp.length; i++) {  // for text
			for(int j = 1; j < dp[0].length; j++) { // for pattern
				
				// if char matches
				// here remember to use -1 in string.charAt as string starts from 0 index
				if(text.charAt(i-1) == pattern.charAt(j-1) || pattern.charAt(j-1) == '.') {
					dp[i][j] = dp[i-1][j-1];
				}
				else if(pattern.charAt(j-1) == '*') {
					dp[i][j] = dp[i][j-2];
					if(pattern.charAt(j-2) == text.charAt(i-1) || pattern.charAt(j-2) == '.') {
						
						dp[i][j] = dp[i][j] | dp[i-1][j]; // we apply OR to get the true among 2 different indexes
						
						// e.g. if s=xa p=xa* then we take OR of comparision value with 'a' in 'S' and 
						// without. 
						// So here s='x' comparing to p='xa*' will be true (counting 1 or more a) and
						// s=xa will compare to p=x is false (i.e. counting 0 occurance of 'a')
						//and OR of these will be true
						
					}
				}
				else {
					dp[i][j] = false;
				}
			}
		}
		
		return dp[m][n];
		
	}

}
