package Oracle;

import java.util.LinkedHashMap;
import java.util.Map;

public class CountTermsWithoutRegex_TermFinder {

	public static void main(String[] args) {

        String[] terms = {"fizz", "buzz", "fizzbuzz"};

        // Test 1: Normal input
        String text1 = "fizz buzz fizzbuzz";

        System.out.println("Test 1");
        System.out.println("Expected: {fizz=2, buzz=2, fizzbuzz=1}");
        System.out.println("Actual:   " + countTerms(terms, text1));

        System.out.println();

        // Test 2: Multiple occurrences
        String text2 = "fizzfizz fizz buzz fizzbuzz";

        System.out.println("Test 2");
        System.out.println("Expected: {fizz=4, buzz=2, fizzbuzz=1}");
        System.out.println("Actual:   " + countTerms(terms, text2));

        System.out.println();

        // Test 3: Empty text
        String text3 = "";

        System.out.println("Test 3");
        System.out.println("Expected: {fizz=0, buzz=0, fizzbuzz=0}");
        System.out.println("Actual:   " + countTerms(terms, text3));

        System.out.println();

        // Test 4: Null text
        String text4 = null;

        System.out.println("Test 4");
        System.out.println("Expected: {fizz=0, buzz=0, fizzbuzz=0}");
        System.out.println("Actual:   " + countTerms(terms, text4));

        System.out.println();

        // Test 5: Null terms
        System.out.println("Test 5");
        System.out.println("Expected: {}");
        System.out.println("Actual:   " + countTerms(null, "fizz buzz"));

        System.out.println();

        // Test 6: No matches
        String text6 = "hello world";

        System.out.println("Test 6");
        System.out.println("Expected: {fizz=0, buzz=0, fizzbuzz=0}");
        System.out.println("Actual:   " + countTerms(terms, text6));

        System.out.println();

        // Test 7: Overlapping matches
        String[] terms7 = {"aaa"};
        String text7 = "aaaaa";

        System.out.println("Test 7");
        System.out.println("Expected: {aaa=3}");
        System.out.println("Actual:   " + countTerms(terms7, text7));
    }
	
	/*
	 * Interview Explanation Before Coding:
	 *
	 * "Since regex isn't allowed, I'll solve this using direct substring comparison.
	 *
	 * I'll iterate through the terms in their given order and store the counts in
	 * a LinkedHashMap, which preserves that same order in the output.
	 *
	 * For each term, I'll scan every valid starting position in the target text.
	 * At each position, I'll compare the term with the text character by character.
	 * If every character matches, I'll increment that term's count.
	 *
	 * I'll move the starting position one character at a time, so overlapping
	 * occurrences are counted as well.
	 *
	 * I'll also handle null and empty inputs so the method does not throw
	 * exceptions.
	 *
	 * Matching assumption:
	 * I'm assuming substring matches count. For example, "fizz" inside "fizzbuzz"
	 * counts as an occurrence. If only whole-word matches should count, I would
	 * add boundary checks around each match."
	 */
	 
	 /* Step-by-Step Algorithm:
	 *
	 * 1. Create a LinkedHashMap<String, Integer> to preserve the input term order.
	 *
	 * 2. If terms == null, return the empty result map.
	 *
	 * 3. For each term:
	 *      - Initialize count = 0.
	 *
	 * 4. If:
	 *      - text == null
	 *      - text is empty
	 *      - term == null
	 *      - term is empty
	 *      - term.length() > text.length()
	 *
	 *    store term -> 0 and continue to the next term.
	 *
	 * 5. Otherwise, scan every valid starting index:
	 *
	 *      start = 0 to text.length() - term.length()
	 *
	 * 6. At each starting index, compare:
	 *
	 *      text.charAt(start + j)
	 *      term.charAt(j)
	 *
	 *    for every character in the term.
	 *
	 *    If all characters match:
	 *      count++
	 *
	 * 7. Store:
	 *
	 *      term -> count
	 *
	 *    in the LinkedHashMap.
	 */
	 
	 /* Complexity Analysis:
	 *
	 * Let:
	 *      T = number of terms
	 *      N = length of the target text
	 *      L = average term length
	 *
	 * For one term:
	 *      We may examine O(N) starting positions.
	 *      At each position, we may compare O(L) characters.
	 *
	 * Time per term:
	 *      O(N * L)
	 *
	 * Time for all terms:
	 *      O(T * N * L)
	 *
	 * More precisely, if the terms have different lengths:
	 *      O(N * sum of all term lengths)
	 *
	 * Space:
	 *      O(T) for the result map.
	 *
	 * Extra working space excluding the returned map:
	 *      O(1)
	 */
	
	/**
     * Interview script:
     * "I'll process the terms in their original order and store
     * the result in a LinkedHashMap so that order is preserved.
     *
     * For each term, I'll scan every possible starting position
     * in the target and compare characters directly without regex.
     *
     * Moving one position at a time also allows overlapping matches."
     *
     * Time:  O(T * N * L)
     * Space: O(T) for the result map
     */
    public static Map<String, Integer> countTerms(
            String[] terms,
            String text) {

        // LinkedHashMap preserves the same order as the input terms.
        Map<String, Integer> result = new LinkedHashMap<>();

        // Requirement: handle null safely and do not throw exceptions.
        if (terms == null) {
            return result;
        }

        for (String term : terms) {

            int count = 0;

            // Invalid or impossible matches simply have count 0.
            if (text == null || text.isEmpty() ||
                    term == null || term.isEmpty() ||
                    term.length() > text.length()) {

                result.put(term, 0);
                continue;
            }

            /*
             * Interview script:
             * "I only check positions where the complete term can
             * still fit inside the remaining target string."
             * 
             * Why start <= text.length() - term.length()?
             * Suppose:
				text = "abcdef"
				term = "def"
				
				Lengths:
					text = 6
					term = 3
				
				The last valid starting position is: 6 - 3 = 3
				
				At index 3:
				a b c d e f
				      3 4 5
				
				      d e f
				
				So: start <= text.length() - term.length()
				
				prevents us from starting at a position where the term would run past the end of the target.
				
				The main benefit is that instead of doing:

					for (int start = 0; start < text.length(); start++)
				
				which would try all 6 positions: 0 1 2 3 4 5
				we only try: 0 1 2 3
				because only those positions can possibly fit a 3-character term.
				
				A useful way to remember it is:
					last valid start = text length - term length
				
				6 - 3 = 3
				So index 3 is the last place where a 3-character term can start.
             */
            // for (int start = 0; start < text.length(); start++) // below is optimized
            for (int start = 0; start<= text.length() - term.length(); start++) {

                boolean match = true;

                /*
                 * Interview script:
                 * "Instead of regex or creating substrings,
                 * I compare characters directly."
                 */
                for (int j = 0; j < term.length(); j++) {

                    if (text.charAt(start + j) != term.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    count++;
                }
            }

            result.put(term, count);
        }

        return result;
    }

    // ########################################
    
    /** 1. Follow-up: Millions of Terms 
     * terms = millions of strings, text  = one document
     * */
    /*
     * Build structure containing ALL terms then scan text: 
     	1. Build Trie from all terms.

		2. For every starting position in text:
		       walk forward through the Trie
		
		3. Whenever we reach a Trie node representing a complete term:
		       increment that term's count
		       
		       
		Is Trie enough for millions of terms?

			It is much better because:
			
			prefixes are shared
			terms are preprocessed
			we can detect multiple terms simultaneously
			
			But a plain Trie can still revisit portions of the text 
			from many starting positions.
			
			
			The stronger multi-pattern solution is: 
			Aho-Corasick
			
			which effectively adds failure links to the Trie and allows us 
			to process the text in one continuous pass.
			
			Interview Script: 
			The main issue with millions of terms is that the base solution 
			scans the same document separately for every term. That repeated 
			work dominates the runtime.

			I would preprocess the terms into a Trie so common prefixes are shared. 
			Then instead of asking whether every term occurs at every location, 
			I let characters from the text drive traversal through the term structure.

			For very large numbers of terms, I would likely extend that to Aho-Corasick. 
			It adds failure links to the Trie so I don't restart matching from scratch 
			at every position, allowing a single scan of the document while detecting 
			multiple terms.
			
     * */
    
    /** 2. Follow-up: Repeated Queries */
    
    
    
    
    
    
    
    
    boolean temp;

}


/*
 Term Finder / Count Terms Without Regex

	Given:
	
	terms = ["fizz", "buzz", "fizzbuzz"]
	
	and a target string, count the number of occurrences of each term.
	
	Important requirements reported:
	
	Preserve the order of terms in output
	No regex
	terms contains no duplicates
	Handle null
	Handle empty string
	Do not throw exceptions
	
	A LinkedHashMap<String, Integer> is particularly relevant because the output must preserve the input-term order.
	
	Example idea:
	
	terms:
	["fizz", "buzz", "fizzbuzz"]
	
	text:
	"fizz buzz fizzbuzz"
	
	result:
	fizz     -> ?
	buzz     -> ?
	fizzbuzz -> ?
	
	There is an important subtlety here: what exactly constitutes an occurrence? For example, does "fizz" inside "fizzbuzz" count? The reports' examples imply matching semantics matter, so that is something I would explicitly clarify with the interviewer before coding. The older version of the same question shows examples such as "fizz buzz fizzbuzz" and "fizzfizz fizz buzz fizzbuzz".
	
	Basic approach:
	
	for each term
	    scan target
	    compare characters
	    count matches
	
	Straightforward complexity:
	
	O(T * N * L)
	
	where:
	
	T = number of terms
	N = target length
	L = average term length
	
	A Principal-level follow-up could naturally move toward:
	
	Millions of terms
	Very large document
	Streaming text
	Overlapping matches
	Case-insensitive matching
	Whole-word matching
	Repeated queries over same term set
	Trie
	Aho-Corasick multi-pattern matching
	Parallel processing
 */