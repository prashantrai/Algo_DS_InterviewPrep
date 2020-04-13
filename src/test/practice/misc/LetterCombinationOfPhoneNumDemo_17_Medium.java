package test.practice.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//https://www.programcreek.com/2014/04/leetcode-letter-combinations-of-a-phone-number-java/

public class LetterCombinationOfPhoneNumDemo {

	public static void main(String[] args) {

		letterCombination("23");
	}
	
	public static void letterCombination(String digit) {
		
		HashMap<String, String> map = new HashMap<String, String>();
	    map.put("2", "abc");
	    map.put("3", "def");
	    map.put("4", "ghi");
	    map.put("5", "jkl");
	    map.put("6", "mno");
	    map.put("7", "pqrs");
	    map.put("8", "tuv");
	    map.put("9", "wxyz");
	    map.put("0", "");
	    
	    List<String> res = new ArrayList<String>();
	    StringBuilder sb = new StringBuilder();
	    
	    helper(digit, 0, sb, res, map);
	    System.out.println(">> "+res);
		
	}
	
	/* 2=ab
	 * 3=cd
	 * 
	 * ab -> a				b
	 * 		/ \			   / \	
	 *     c	d         c	  d
	 * 
	 * */
	
	/*
	 * Time complexity - 4^n : this would be UPPER BOUND of algorithm here has num 7 on key pad has 4 chars (i.e. 'pqprs')
	 * it's exponential coz we have to go all the combination for each char. 
	 * (e.g. if there are 2 digits 23 then complexity will be 2^2 
	 * i.e. (number of digits)^(max number of alphabets among all the digits))
	 * For example: 23 char combination will be [ac, ad, bc, bd] which 4 (i.e. 2^2)
	*/
	
	public static void helper(String digit, int idx, StringBuilder sb, List<String> res, HashMap<String, String> map) {
		
		if(idx >= digit.length()) {
			res.add(sb.toString());
			return;
		}
		

		String s = map.get(""+digit.charAt(idx));
		
		for(int i=0; i< s.length(); i++) {
			
			sb.append(s.charAt(i));
			helper(digit, idx+1, sb, res, map);
			
			/*
			 * After branching left (as shown in above comment/tree) left side will return "ac" to root 
			 * we add that in result and update the stringBuilder by removing the last char (i.e. 'c' here)
			 * now we need to generate another combination (i.e. "ad") 
			 * */
			sb.deleteCharAt(sb.length()-1);
		}
		
	}

}
