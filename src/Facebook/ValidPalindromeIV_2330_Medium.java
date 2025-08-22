package Facebook;

public class ValidPalindromeIV_2330_Medium {

	public static void main(String[] args) {
		String s = "abcdba";
		System.out.println("Expected: true, Actual: " + makePalindrome(s));

	}

	/*
	 * You are given a 0-indexed string s consisting of only lowercase English
	 * letters. In one operation, you can change any character of s to any other
	 * character.
	 * 
	 * Return true if you can make s a palindrome after performing exactly ONE or
	 * TWO operations, or return false otherwise.
	 * 
	 * Example 1: Input: s = "abcdba" Output: true Explanation: One way to make s a
	 * palindrome using 1 operation is: - Change s[2] to 'd'. Now, s = "abddba".
	 */

	// time: O(n)
	// space: O(1)
	public static boolean makePalindrome(String s) {
		int i = 0;
		int j = s.length() - 1;
		int miss = 0;

		while (i < j) {
			if (s.charAt(i) != s.charAt(j)) {
				miss++;
				if (miss > 2)
					return false;
			}
			i++;
			j--;
		}
		return miss < 3;
	}

}
