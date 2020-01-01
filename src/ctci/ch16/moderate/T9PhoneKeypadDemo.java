package ctci.ch16.moderate;

import java.util.ArrayList;
import java.util.Set;

import CtCILibrary.AssortedMethods;


/**
 * Another implementation : 
 * test.practice.misc.LetterCombinationOfPhoneNumDemo.java
 * 
 * https://www.programcreek.com/2014/04/leetcode-letter-combinations-of-a-phone-number-java/
 * 
 * Below code is CTCI implementation
 * */



public class T9PhoneKeypadDemo {

	public static char[][] t9Letters = {
			null, 					// 0
			null, 					// 1
			{'a', 'b', 'c'}, 		// 2
			{'d', 'e', 'f'}, 		// 3
			{'g', 'h', 'i'}, 		// 4
			{'j', 'k', 'l'},		// 5
			{'m', 'n', 'o'},		// 6
			{'p', 'q', 'r', 's'}, 	// 7
			{'t', 'u', 'v'},		// 8
			{'w', 'x', 'y', 'z'} 	// 9
	};
	
	public static void main(String[] args) {

		
		String num = "8733";
		Set<String> wordSet = AssortedMethods.getWordListAsHashSet();
		ArrayList<String> result = new ArrayList<>();
		getValidWords(num, wordSet, result);
		
	}

	private static void getValidWords(String num, Set<String> wordSet, ArrayList<String> result) {

		helper(num, 0, "", wordSet, result);
		System.out.println(result);
		
	}

	private static void helper(String num, int index, String prefix, Set<String> wordSet, ArrayList<String> result) {

		if(index == num.length()) {
			if(wordSet.contains(prefix))
				result.add(prefix);
			return;
		}
		
		char digit = num.charAt(index);
		char[] t9chars = getT9Chars(digit);
		
		for(char c : t9chars) {
			helper(num, index+1, prefix+c, wordSet, result);
		}
		
		
	}
	
	private static char[] getT9Chars(char digit) {
		
		if(!Character.isDigit(digit)) {
			return null;
		}
		
		int idx = Character.getNumericValue(digit) - Character.getNumericValue('0');
		
		return t9Letters[idx];
	}

}
