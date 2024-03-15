package Apple;

import java.util.HashSet;
import java.util.Set;

public class CountDistinctPassword {

	public static void main(String[] args) {
        String str = "abc";
        int length = str.length();

        int count = countDistinctPassword(str);

        System.out.println("The number of distinct strings of length " + length + " that can be formed from the given string is: " + count);
    }
	
	
	private static int countDistinctPassword(String password) {
		int result = 0;
		int length = password.length();
		if (length <= 1) {
			return result;
		}
		result++;
		
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        sb.append(password.charAt(i));
	        if (length - i > 1) {
	            sb.append('_');
	        }
	    }
	    String newPassword = sb.toString();
	    length = newPassword.length();
	
	    for (int i = 1; i < length - 1; i++) {
	        int j = 2;
	        if (newPassword.charAt(i)=='_') {
	            j = 1;
	        }
	
	
	        while (canContinue(length,i,j)) {
	            char leftChar = newPassword.charAt(i-j);
	            char rightChar = newPassword.charAt(i+j);
	            if (leftChar!=rightChar) {
	                result++;
	            }
	            j+=2;
	        }
	
	    }
	
	    return result;
	}
	
	private static boolean canContinue(int length, int i, int j) {
	    return (i-j >= 0) && (i+j < length);
	}

}


/*
	https://leetcode.com/company/apple/discuss/4820390/MAANG-Substring-problem
	
	Weak passwords are likely to be hacked and misused. Due to this, developers at Amazon 
	regularly come up with new algorithms to check the health of user passwords. A new algorithm 
	estimates the variability of a password as the number of distinct password strings that can 
	be obtained by reversing any one substring of the original password. Given the original 
	password that consists of lowercase English characters, find its variability.
	
	Note: A substring is a contiguous sequence of characters within a string. 
	For example 'bcd', 'a', 'abcd' are substrings of the string 'abcd' 
	whereas the strings 'bd', 'acd' are not.
	
	Example
	
	The following strings can be formed from password = 'abc':
	
	• Reversing any substring of length 1 gives the original string "abc".
	• Reversing the substring "ab" gives a new string "bac". 
	• Reversing the substring "bc" gives a new string "acb".
	• Reversing the substring "abc" gives a new string "cba".
	
	There are 4 distinct password strings that can be obtained from password. Return 4.
	
	Function Description
	
	Write a function countDistinctPasswords that has the following parameters:
	
	string password: the original password
	Returns
	
	long_int: the number of distinct password strings that can be formed
	Constraints
	
	All characters are lowercase English letters ascii[a-z]
	1 <= length of password <= 105
	Please note: Less than O(n^3) time and space is expected.

 
 
  
 * */
