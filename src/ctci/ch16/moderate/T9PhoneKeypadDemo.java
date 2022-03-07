package ctci.ch16.moderate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		// another approach
		System.out.println(">> " + letterCombinations("8733"));
		
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
	
	
	/* Look into leetcode solution in below file for solution and time/space complexity
	 * 
	 * LetterCombinationOfPhoneNumDemo_17_Medium.java
	 * 
	 * */
	
	// -- Another approach 
	
	// From : https://www.interviewbit.com/blog/letter-combinations-of-a-phone-number/

	  private static List <String> combinations = new ArrayList<>();
	  
	  // works with Java 9
	  /*private Map <Character, String> letters = Map.of(
	    '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
	    '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz");
	  */
	  private static Map <Character, String> letters = createLetterMap();
	  private static String phoneDigits;
	 
	  public static List < String > letterCombinations(String digits) {
	    if (digits.length() == 0) {
	      return combinations;
	    }
	    phoneDigits = digits;
	    backtrack(0, new StringBuilder());
	    return combinations;
	  }
	 
	  private static void backtrack(int index, StringBuilder path) {
	    if (path.length() == phoneDigits.length()) {
	      combinations.add(path.toString());
	      return;
	    }
	    String possibleLetters = letters.get(phoneDigits.charAt(index));
	    for (char letter: possibleLetters.toCharArray()) {
	      path.append(letter);
	      backtrack(index + 1, path);
	      path.deleteCharAt(path.length() - 1);
	    }
	  }
	
	  private static Map <Character, String> createLetterMap() {
		  Map <Character, String> letters = new HashMap<>();
		  letters.put('2', "abc"); 
		  letters.put('3', "def");
		  letters.put('4', "ghi"); 
		  letters.put('5', "jkl");
		  letters.put('6', "mno"); 
		  letters.put('7', "pqrs");
		  letters.put('8', "tuv");
		  letters.put('9', "wxyz");
		  
		  return letters;
	  }
}


	
