package Facebook;

public class ValidPalindrome_125_Easy {

	public static void main(String[] args) {

		String s = "A man, a plan, a canal: Panama";
		System.out.println("Expected: true, Actual: " + isPalindrome(s));
		System.out.println("Expected: true, Actual: " + isPalindrome2(s));
		
		s = "race a car";
		System.out.println("Expected: false, Actual: " + isPalindrome(s));
		System.out.println("Expected: false, Actual: " + isPalindrome2(s));
	}

	/* Question: 
	 A phrase is a palindrome if, after converting all uppercase 
	 letters into lowercase letters and removing all non-alphanumeric 
	 characters, it reads the same forward and backward. 
	 
	 Alphanumeric characters include letters and numbers.

	Given a string s, return true if it is a palindrome, or false otherwise.

	Example 1:
		Input: s = "A man, a plan, a canal: Panama"
		Output: true
		Explanation: "amanaplanacanalpanama" is a palindrome.
	 */
	
	
	// Time: O(n)
    // Space: O(1)
    public static boolean isPalindrome(String s) {
        
        int i = 0;  // left pointer 
        int j = s.length() -1; // right pointer
        while (i<j) {
            while(i<j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while(i<j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if(Character.toLowerCase(s.charAt(i)) 
               != Character.toLowerCase(s.charAt(j))) {
                
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    
    // Working - another approach
    // Time: O(n)
    // Space: O(1)
    public static boolean isPalindrome2(String s) {
        for(int i=0, j=s.length()-1; i<j; i++, j--) {
            while(i<j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while(i<j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if(Character.toLowerCase(s.charAt(i)) 
               != Character.toLowerCase(s.charAt(j))) {
                
                return false;
            }
        }
        return true;
    }
	
}
