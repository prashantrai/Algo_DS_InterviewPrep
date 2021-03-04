package leetcode;

public class LongestPalindromicSubstring_5_Medium {

	public static void main(String[] args) {

		String s = "babad";
		System.out.println("Expected: bab, Actual: " + longestPalindrome(s));
		
		String s2 = "cbbd";
		start = 0 ; maxLen = 0; //reset
		System.out.println("Expected: bb, Actual: " + longestPalindrome(s2));
		
		System.out.println("Expected: bab, Actual: " + longestPalindrome2(s)); // copy of leetcode solution
		System.out.println("Expected: bb, Actual: " + longestPalindrome2(s2)); 
	}
	
	
	// https://www.youtube.com/watch?v=ZJUGtWObroc&ab_channel=KnowledgeCenter
	
	// Time: O(N^2) Space: O(1)
	
	private static int start = 0;
	private static int maxLen = 0; 
	public static String longestPalindrome(String s) {
		if (s == null || s.length() <= 1) 
			return s;
		
		for(int i=0; i<s.length()-1; i++) {
			checkPalindrom(s, i, i); //when odd length
			checkPalindrom(s, i, i+1); //when even length
		}
		
		return s.substring(start, start+maxLen);
	}
	
	private static void checkPalindrom(String s, int left, int right) {
		int n = s.length();
		
		while(left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}
		int len = right - left -1;
		if(len > maxLen) {
			maxLen = len;
			start = left + 1; //becuase in while loop we have left--
			// end = right - 1;//becuase in while loop we have right++
		}
	}
	
	
	
	//Leetcode solution
	public static String longestPalindrome2(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;

        for(int i=0; i<s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i); //when odd length
            int len2 = expandAroundCenter(s, i, i+1); //when even length
            int len = Math.max(len1, len2);
            
            if(len > end - start) {
                start = i - (len-1)/2;
                end = i+len/2;
            }
        }
        
        return s.substring(start, end+1);
    }
    
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

}
