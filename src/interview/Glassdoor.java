package interview;

import java.util.HashMap;
import java.util.HashSet;

public class Glassdoor {

	
	/** 
	  	Write a function that takes in 2 strings as input and outputs a string consisting of:
		The symbols that appear in both input strings, in the order that they appear in the 1st input string, 
		w/o repeated symbols in the output string.
		
		e.g. S1 = abcdef S2 = fbexzb

		Output: bef
	 */
	
	
	public static void main(String[] args) {

		String s1 = "eartswerf", s2 = "fart";
		System.out.println("Result>> "+getSubStrSeq(s1, s2));
	}
	
	public static String getSubStrSeq(String s1, String s2) {

		int m = s1.length();
		int n = s2.length();

		return helper2(s1, s2, m, n);

	}

	//--Working solutions
	//--finding s2's chars in s1
	public static String helper2(String s1, String s2, int s1_len, int s2_len) {

		String res = "";
		
		HashSet<Character> set = new HashSet<>();//--to contain each chars from 2nd string
		HashSet<Character> usedChars = new HashSet<>(); //--this will help remove the dups from result
		
		
		//--store the char (of 2nd string) and the position in string (for dups is it's coming then ignore)
		for(int i=0; i<s2_len; i++)  {
			set.add(s2.charAt(i));
		}

		/** now iterate through s1 and look for the chars in map and if available add in result string
		    interating through the master string because we have to maintain the char order as per master string if it's there in 2nd
		
		*	e.g: s1 = eartswerf s2 = fart expected result: artf 
		*/ 
		for (int i=0; i<s1_len; i++) {
			if( set.contains(s1.charAt(i)) && !usedChars.contains(s1.charAt(i)))  {
				res += s1.charAt(i);
				usedChars.add(s1.charAt(i));
			}
		}
		return res;

	}


}
