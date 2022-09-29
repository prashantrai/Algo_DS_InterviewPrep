package Square;

import java.util.HashSet;
import java.util.Set;

public class UniqueMorseCodeWords_804_Easy {

	public static void main(String[] args) {
		String[] words = {"gin","zen","gig","msg"};
		System.out.println("Expected: 2, Actual: " + uniqueMorseRepresentations(words));

	}

	/*
    Time Complexity: O(S), where S is the sum of the lengths of words in words. 
    We iterate through each character of each word in words.

    Space Complexity: O(S).
    */
    
    public static int uniqueMorseRepresentations(String[] words) {
     
        String[] MORSE = new String[]{".-","-...","-.-.","-..",".","..-.","--.",
                         "....","..",".---","-.-",".-..","--","-.",
                         "---",".--.","--.-",".-.","...","-","..-",
                         "...-",".--","-..-","-.--","--.."};
        
        Set<String> seen = new HashSet<>();
        for(String word : words) {
            StringBuilder sb = new StringBuilder();
            
            for(char c : word.toCharArray()) {
                sb.append(MORSE[c - 'a']);
            }
            seen.add(sb.toString());
        }
        
        return seen.size();
    }
	
}
