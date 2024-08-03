package Facebook;

public class ValidPalindrome_II_680_Easy {

	public static void main(String[] args) {
		String s = "aba";
		System.out.println("Expected: true, Actual: "+ validPalindrome(s));

	}
	
	/** Question: Given a string s, return true if the s can be 
	 * palindrome after deleting at most one character from it.

		Example 1:
			Input: s = "aba"
			Output: true
			
	Follow-Up: Given a string S consisting of lowercase English characters, determine if you can make it a palindrome by removing at most N characters.
	 * 
    Time: O(N), because we are scanning all chars
    Space: O(1)
    */
    public static boolean validPalindrome(String s) {
       int i = 0;
       int j = s.length()-1;

       while(i <= j){
        if(s.charAt(i) == s.charAt(j)){
            i++;
            j--;
        } else {
        	// now check, by calling helper method after deleting/ignoring current left 
            // char (i.e. start=i+1) OR current right (end = j-1), if the 
            // rest of the string is palindrom or not (in either case), 
            // if yes, then return true 
            // i.e. by deleting one char input string will be a valid palindrom 
            return isPalindrome(s, i+1, j) || isPalindrome(s, i, j-1);
        }
       } return true;

    }
    
    public static boolean isPalindrome(String s, int start, int end){
        while(start <= end){
            if(s.charAt(start) == s.charAt(end)){
                start++;
                end--;
            }  else return false;
        } return true;
    }

}





/*Asked during onsite*/ 

// Question 1: Given a string S consisting of lowercase English characters, determine if you can make it a palindrome by removing at most 1 character.

// Question 1 Follow-Up: Given a string S consisting of lowercase English characters, determine if you can make it a palindrome by removing at most N characters.


// tacocats --> True  # tacocats --> tacocat
// racercar --> True  # racercar --> racecar, racrcar
// kbayak --> True  # kbayak --> kayak 
// acbccba --> True # acbccba --> abccba
// abccbca --> True # abccbca --> abccba

// abcd --> False
// btnnure --> False
/* 

tacocats
i=0;
j= 7

Time: O(N)
Space: O(1)

*/




// Question 2: Before recess, the children of a classroom line up. Each child knows two pieces of information: 
// 1. his or her own height and 
// 2. the number of children in front of him/her who are taller. 
// During recess, they scramble and loss this order and after recess, they must reconstruct the original order of the line. Design an algorithm to do so.

// Original Line & Goal Line
// Height:  [front]  155  160  145  150  170  165  140    [back]
// No. Taller:         0    0    2    2    0    1    6

// Example input:   [(145, 2), (170, 0), (140, 6), (165, 1), (150, 2), (155, 0), (160, 0)]

// Expected output: [(155, 0), (160, 0), (145, 2), (150, 2), (170, 0), (165, 1), (140, 6)]

// [(140, 6), (145, 2), (150, 2), (155, 0), (160, 0), (165, 1), (170, 0)]
