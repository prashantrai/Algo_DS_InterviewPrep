package test.lyft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://www.programcreek.com/2014/04/leetcode-letter-combinations-of-a-phone-number-java/

public class LetterCombinationOfPhoneNumDemo_17_Medium {

	public static void main(String[] args) {

		letterCombinations("23");
		
		System.out.println(res);
	}

	// https://leetcode.com/problems/letter-combinations-of-a-phone-number/

	/*
	 * Complexity Analysis (Leetcode)
	 * 
	 * Time complexity : O(3^N * 4^M) where N is the
	 * number of digits in the input that maps to 3 letters (e.g. 2, 3, 4, 5, 6, 8)
	 * and M is the number of digits in the input that maps to 4 letters (e.g. 7,
	 * 9), and N+M is the total number digits in the input.
	 * 
	 * Space complexity : O(3^N * 4^M)  since one has to keep O(3^N * 4^M) solutions.
	 */
	
	private static Map<String, String> phone = new HashMap<String, String>() {
        {
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
          }};
    
    private static List<String> res = new ArrayList<String>();

	/*
	 * 2=ab 3=cd
	 * 
	 * ab -> a b / \ / \ c d c d
	 * 
	 */

	/*
	 * Time complexity - 4^n : this would be UPPER BOUND of algorithm here has num 7
	 * on key pad has 4 chars (i.e. 'pqprs') it's exponential coz we have to go all
	 * the combination for each char. (e.g. if there are 2 digits 23 then complexity
	 * will be 2^2 i.e. (number of digits)^(max number of alphabets among all the
	 * digits)) For example: 23 char combination will be [ac, ad, bc, bd] which 4
	 * (i.e. 2^2)
	 */
	
	public static List<String> letterCombinations(String digits) {
        if(digits != null && digits.length() != 0) {
            
            helper(digits, "", 0);
        }  
        return res;
    }
	
	private static void helper(String digits, String combination, int idx) {
        
        if(idx >= digits.length()) {
            res.add(combination);
            return;
        }
        
        String letters = phone.get(""+digits.charAt(idx));
        
        for(int i=0; i<letters.length(); i++) {
            String curr = ""+letters.charAt(i);
            
            helper(digits, combination+curr, idx+1);
            
        }
    }

}
