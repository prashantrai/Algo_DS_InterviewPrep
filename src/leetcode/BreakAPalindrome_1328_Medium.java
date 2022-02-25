package leetcode;

public class BreakAPalindrome_1328_Medium {

	public static void main(String[] args) {
		String palindrome = "abccba";
		System.out.println("Expected: aaccba, Actual: " + breakPalindrome(palindrome));
		
	}
	
	// Time: O(N) Space: O(N)
	public static String breakPalindrome(String palindrome) {
        int len = palindrome.length();
        if(len == 1) return "";
        
        char[] arr = palindrome.toCharArray();
        for(int i=0; i<len/2; i++) {
            if(arr[i] != 'a') {
                arr[i] = 'a';
                return String.valueOf(arr);
            }
        }
        
        arr[len-1] = 'b';
        return String.valueOf(arr);
    }
	
}
