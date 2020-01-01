package ctci.ch16.moderate;
import java.util.ArrayList;
import java.util.HashMap;

import CtCILibrary.AssortedMethods;
import CtCILibrary.HashMapList;

public class T9PhoneKeypadDemo2 {
	public static int numLetters = 26;
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
	
	/* Convert from a string to its T9 representation. */
	public static String convertToT9(String word, HashMap<Character, Character> letterToNumberMap) {
		StringBuilder sb = new StringBuilder();
		for (char c : word.toCharArray()) {
			if (letterToNumberMap.containsKey(c)) {
				char digit = letterToNumberMap.get(c);
				sb.append(digit);
			}
		}
		return sb.toString();
	}
	
	/* Convert mapping of number->letters into letter->number 
	 * 
	 * Example : Below map is based on the numbers mapped to char on phone key pad
	 * {a=2, b=2, c=2, d=3, e=3, f=3, g=4, h=4, i=4, j=5, k=5, l=5, m=6, n=6, o=6, p=7, q=7, r=7, s=7, t=8, u=8, v=8, w=9, x=9, y=9, z=9}
	 * */
	public static HashMap<Character, Character> createLetterToNumberMap() {
		HashMap<Character, Character> letterToNumberMap = new HashMap<Character, Character>();
		for (int i = 0; i < t9Letters.length; i++) {
			char[] letters = t9Letters[i];
			if (letters != null) {
				for (char letter : letters) {
					char c = Character.forDigit(i, 10);
					letterToNumberMap.put(letter, c);
				}
			}
		}		
		return letterToNumberMap;
	}
	
	/* Create a hash table that maps from a number to all words that
	 * have this numerical representation. 
	 * 
	 * 
	 * Example: Combination key num mapped to words
	 * {78677=[stops], 6=[I'm], 78679=[story], 7=[Mr, Dr, Jr], 8=[St], 364463=[engine], 3647=[dogs], 5825=[luck], 34787428=[district], 88467489=[Authority], 2328=[beat], 2327=[bear], 77377=[press], 77379=[ssdsy], 56433=[knife], 3653=[fold]}
	 * 
	 * 
	 * */
	public static HashMapList<String, String> initializeDictionary(String[] words) {
		/* Create hash table that maps from a letter to the digit */
		HashMap<Character, Character> letterToNumberMap = createLetterToNumberMap();
		
		/* Create word -> number map */
		HashMapList<String, String> wordsToNumbers = new HashMapList<String, String>(); 
		for (String word : words) {
			String numbers = convertToT9(word, letterToNumberMap);
			wordsToNumbers.put(numbers, word);
		}
		return wordsToNumbers;
	}
	
	public static ArrayList<String> getValidT9Words(String numbers, HashMapList<String, String> dictionary) {
		return dictionary.get(numbers);
	}
	
	public static void main(String[] args) {
		HashMapList<String, String> dictionary = initializeDictionary(AssortedMethods.getListOfWords());
		ArrayList<String> words = getValidT9Words("8733", dictionary);
		for (String w: words) {
			System.out.println(w);
		}	

	}

}