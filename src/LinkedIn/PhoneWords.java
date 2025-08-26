package LinkedIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneWords {
	
	// Demo
    public static void main(String[] args) {
        PhoneWords pw = new PhoneWords();

        System.out.println(pw.query("2273377"));   // [careers]
        System.out.println(pw.query("54653346"));  // [linkedin, linkedgo]

        // Edge cases
        System.out.println(pw.query(""));          // []
        System.out.println(pw.query("123"));       // []
        System.out.println(pw.query("447464"));    // [hiring]
    }
	
	
	/*
 	- Build a char → digit mapping using the phone keypad.
	- For each word in KNOWN_WORDS:
		- Lowercase it, skip non-alphabetic.
		- Convert to its digit string (e.g., careers → 2273377).
		- Put the word into a map: digitsToWords.get(digits).add(word).
	
	- For a phone number query:
		- If it contains 0 or 1 (no letters), return [].
		- Return digitsToWords.getOrDefault(number, []).
	
	Complexity: 
		Preprocessing: 
				Time O(S) and Space O(S), where S is the total number 
				of characters across all known words.
	
		Query: Time O(1 + k) where k is the number of matched words 
		(we copy results); Space O(k) for the returned list.
	 * */

    private static final String[] KEYPAD = {
        "",    "",    "abc", "def", "ghi", "jkl",
        "mno", "pqrs","tuv","wxyz"
    };

    private static final List<String> KNOWN_WORDS = Arrays.asList(
        "careers", "linkedin", "hiring", "interview", "linkedgo"
    );

    // Map each known word to its digit string once
    private final Map<String, String> wordToDigits = new HashMap<>();

    public PhoneWords() {
        for (String word : KNOWN_WORDS) {
            wordToDigits.put(word, toDigits(word));
        }
    }

    // Return all words that match the phone number
    public List<String> query(String phoneNumber) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : wordToDigits.entrySet()) {
            if (entry.getValue().equals(phoneNumber)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
    
    
    // Convert a word into digit string using keypad mapping
    private String toDigits(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toLowerCase().toCharArray()) {
            for (int d = 2; d <= 9; d++) {
                if (KEYPAD[d].indexOf(c) != -1) {
                    sb.append(d);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
}

/*
Given the standard mapping from English letters to digits on a phone 
keypad (1 → "" 
2 -> a,b,c 
3 -> d,e,f 
4 -> g,h,i 
5 -> j,k,l 
6 -> m,n,o 
7 -> p,q,r,s 
8 -> t,u,v 
9 -> w,x,y,z),

write a program that outputs all words that can be formed from 
any n-digit phone number from the list of given KNOWN_WORDS 
considering the mapping mentioned above.

KNOWN_WORDS= ['careers', 'linkedin', 'hiring', 'interview', 'linkedgo']
phoneNumber: 2273377
Output: ['careers']
phoneNumber: 54653346
Output: ['linkedin', 'linkedgo']

*/