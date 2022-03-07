package Flexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://www.programcreek.com/2014/04/leetcode-letter-combinations-of-a-phone-number-java/

public class LetterCombinationOfPhoneNumDemo_17_Medium {

	public static void main(String[] args) {

		//letterCombinations("23");
		//System.out.println(res);
		
		letterCombinations("8733");
		System.out.println(res);
		System.out.println("Total permutations: " + res.size());
		System.out.println("countPermutations: " + countPermutations);
	}

	// https://leetcode.com/problems/letter-combinations-of-a-phone-number/

	/*
	 * Complexity Analysis (Leetcode):: 
	 * 
	 * Time complexity: 
	 * 	O(4^N * N), where N is the length of digits. 
	 * Note that 4 in this expression is referring to the maximum 
	 * value length in the hash map, and not to the length of the input.
	 * 
	 * The worst-case is where the input consists of only 7s and 9s. 
	 * In that case, we have to explore 4 additional paths for every 
	 * extra digit. Then, for each combination, it costs up to N 
	 * to build the combination. This problem can be generalized to 
	 * a scenario where numbers correspond with up to M digits, 
	 * in which case the time complexity would be O(M^N * N). 
	 * 
	 * For the problem constraints, we're given, M=4, because of 
	 * digits 7 and 9 having 4 letters each.
	 * 
	 * 
	 * Space complexity:
	 *  O(N), where N is the length of digits. 
	 * 
	 * Not counting space used for the output, the extra space we 
	 * use relative to input size is the space occupied by the 
	 * recursion call stack. It will only go as deep as the number 
	 * of digits in the input since whenever we reach that depth, 
	 * we backtrack. 
	 * 
	 * As the hash map does not grow as the inputs 
	 * grows, it occupies O(1) space.
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
    
    private static int countPermutations;

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
            countPermutations++;
            return;
        }
        
        String letters = phone.get(""+digits.charAt(idx));
        
        for(int i=0; i<letters.length(); i++) {
            String curr = ""+letters.charAt(i);
            
            helper(digits, combination+curr, idx+1);
            
        }
    }

}
