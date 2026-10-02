package Motive;

public class GoatLatin_824_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	/* Complexity: 
	 
	 Time Complexity :: O(N + W²)
	   because the required output itself may contain O(W²) appended as.
	   This isn't an inefficiency in our algorithm—we actually have to generate those characters.

	For LeetCode 824's constraints, this is completely appropriate.

	Space Complexity : O(output size) for the result.
	
	Ignoring the returned output, the additional working space is small 
	aside from split() creating the word array.*/
	
	public static String toGoatLatin(String sentence) {
		
		if (sentence == null || sentence.length() == 0) {
            return "";
        }
		
		String[] words = sentence.split(" ");
		StringBuilder result = new StringBuilder();
		
		// Process every word independently from left to right.
		for(int i=0; i<words.length; i++) {
			String word = words[i];
			
			// Add spaces between words, but not before the first word.
			if(i > 0) {
				result.append(' ');
			}
			
			char first = word.charAt(0);
			
			// Keep vowel-starting words unchanged.
            // For consonants, move the first character to the end.
			if(isVowel(first)) {
				result.append(word);
			} else {
				result.append(word.substring(1));
				result.append(first);
			}
			
			// Every word gets "ma".
			result.append("ma");
			
			// Word i gets i + 1 copies of 'a'.
			for(int j=0; j <= i; j++) {
				result.append('a');
			}
		}
		
		return result.toString();
		
	}
	
	private static boolean isVowel(char ch) {
		ch = Character.toLowerCase(ch);
		
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'; 
	}
	
}
