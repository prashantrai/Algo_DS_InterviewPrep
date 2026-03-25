package Apple;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class I_PrettyOddStatementsIterative {

    public static void main(String[] args) {

        String[][] words = {
                {"The", "A"},
                {"dog", "cat"},
                {"runs", "walks"},
                {"quickly", "slowly"},
                {".", "!"}
        };

        // Generates start with "The" and ends with '.' for all sentences 
        // use this in interview, interative solution
        // Also contains follow-up, when list is large we need K sentences
        // Below signature can be updated to pass k, currently it's hard-coded in method
        generateSentences(words); // update signature to pass k for follow-up
        
        // including "A" and "!", no exclusion
        generateSentences_AllGroups(words); //
        
        
//        generateSentence_REC(words);
//        generateSentences_Recursion(words);
//        generateSentence_BFS(words);
//        generateSentences_Interative(words);
    }
    
    /* Question
     * Given below input string array , print the pretty odd statements
			{
			  {"The", "A"},
			  {"dog","cat"},
			  {"runs","walks"},
			  {"quickly","slowly"},
			  {".","!"}
			}
			
			Expected Output:
			
			The dog runs quickly.
			The dog runs slowly.
			The dog walks quickly.
			The dog walks slowly.
			The cat runs quickly.
			The cat runs slowly.
			The cat walks quickly.
			The cat walks slowly.
     * */
    
    
    /* Interview Script: 
    - I treat each word group as a digit in a multi-base number system.
	- Each digit represents the index of the word chosen from that group.
	- After printing a sentence, I increment the last digit.
	  If it exceeds the number of words in that group, 
	- I reset it to zero and carry to the left.
	- This approach generates sentences one by one using only O(n) space 
	  and works for any number of word groups.
	 */
    
    /* The idea is:
		Treat each word group as a digit in a multi-base number system and 
		increment it like an odometer.
		
	Visual Representaton: 
		indexes     sentence
		------------------------------
		[0,0,0]  →  The dog runs quickly
		[0,0,1]  →  The dog runs slowly
		[0,1,0]  →  The dog walks quickly
		[0,1,1]  →  The dog walks slowly
		[1,0,0]  →  The cat runs quickly
		[1,0,1]  →  The cat runs slowly
		[1,1,0]  →  The cat walks quickly
		[1,1,1]  →  The cat walks slowly
     
    */ 
    
    /*
    Time:  O(k^n), where k is choices per position and n is number of positions.
    		Why exponential?: we generate all sentences
	Space: O(n)
	
	Follow-up: What if each word group had thousands of words and we only want the first K sentences?
	For follow-up add lines with comment as "follow-up" in the below solution.
	
	For Follow-Up solution, 
		Time: O(K * n), We generate K sentences and each takes O(n) time to build.
		Space: O(n)
		
	Interview Script::
    In the original problem, we must generate all combinations, which leads 
    to O(k^n) time complexity. In the follow-up, since we only need the 
    first K sentences, we stop early after generating K outputs.
	Each sentence takes O(n) time to construct, so the total time 
	becomes O(K * n), which is significantly smaller when K << k^n.

    * */
    // Use this in Interview
    private static void generateSentences(String[][] words) {
    	if (words == null || words.length == 0) {
            return;
        }
    	
    	int k = 5; // for follow-up only (pass this as parameter in interview)
    	
    	int n = words.length;
    	
    	// Fix first word and last punctuation as per expected output
    	String firstWord = words[0][0];
    	String punctuation = words[n-1][0];
    	
    	/* Middle groups are:
			dog/cat
			runs/walks
			quickly/slowly
		
		No of middle groups: words.length - 2; Here, '-2' because of firstWord and lastPunctuation
		*/    	
    	int groups = n - 2;
    	
    	/* We are iterating middle groups and each index in indexes is for 
    	 each group and will be updated as we iterate that group 
    	Example: [0,0,0] 
    	*/
    	int[] indexes = new int[groups];
    	
    	int count = 0; // for follow-up only
    	
    	while (true) {
    		
    		if (count == k) { // for follow-up only
                break; // for follow-up only
            }
    		
        	// Build sentence using indexes, dynamically
        	// The + words[1][0] + words[2][0] + words[3][0] + .
            StringBuilder sentence = new StringBuilder();
            sentence.append(firstWord);
            
            for(int i=0; i<groups; i++) {
            	sentence.append(" ");
            	sentence.append(words[i+1][indexes[i]]); // e.g. words[1][0] => dog
            }
            sentence.append(punctuation);
            System.out.println(sentence);
            
            count++; // for follow-up only 
            
            /*Increment indexes like Odometer, 
            Start incrementing from the rightmost group.
            indexes[last]++

            If overflow happens:
            reset to 0
            carry to left
            */
            // Increment indexes (odometer logic)
            int pos = groups - 1;
            
            while (pos >= 0) {
            	indexes[pos]++;
            	
            	/* Why pos + 1?
					Our indexes array stores only middle groups:
						indexes[0] → words[1]  (dog/cat)
						indexes[1] → words[2]  (runs/walks)
						indexes[2] → words[3]  (quickly/slowly)

					So the mapping is:
						indexes[i] → words[i+1]
					
					Therefore we check:
						indexes[pos] < words[pos + 1].length
            	 * */
            	if(indexes[pos] < words[pos + 1].length) {
            		break;
            	}
            	
            	indexes[pos] = 0; // reset
            	pos--;
            }
            if(pos < 0) break;
            
    	} // while(true) closed
    	
    	
    }
    
    
    /* Generate using 'The', 'A', '.', '!'. Above is limited to 'The' and '.'
     * 
     * When generating all sentences: 
     * Time:  O(k^n), where k is choices per position and n is number of positions.
    		Why exponential?: we generate all sentences
		Space: O(n)
     * 
     * 
     * When generating only K sentences: 
     * Time: O(K * n), K*n because we generate K sentences and Stops early → most scalable
     * Space: O(n)
     * 
     * Interview Script::
    In the original problem, we must generate all combinations, which leads 
    to O(k^n) time complexity. In the follow-up, since we only need the 
    first K sentences, we stop early after generating K outputs.
	Each sentence takes O(n) time to construct, so the total time 
	becomes O(K * n), which is significantly smaller when K << k^n.
     * 
     * */
    
    public static void generateSentences_AllGroups(String[][] words) { // follow-up

        if (words == null || words.length == 0) return;

        int groups = words.length; // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.

        int[] indexes = new int[groups]; // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.

        int count = 0; // follow-up
        
        int k = 5; //hard code, pass this as parameter when asked

        while (true) {

            if (count == k) { // follow-up
                break; // follow-up
            }

            StringBuilder sentence = new StringBuilder();

            // Build full sentence dynamically (including first & last groups)
            for (int i = 0; i < groups; i++) { // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.

                if (i > 0 && i != groups - 1) { // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.
                    sentence.append(" "); // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.
                }

                sentence.append(words[i][indexes[i]]); // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.
            }

            System.out.println(sentence.toString());

            count++; // follow-up

            int pos = groups - 1;

            while (pos >= 0) {

                indexes[pos]++;

                if (indexes[pos] < words[pos].length) { // follow-up 2: when both 'The' and 'A', '.' and '!' allowed.
                    break;
                }

                indexes[pos] = 0;
                pos--;
            }

            if (pos < 0) {
                break;
            }
        }
    }
    
    
    
    
/****** Recursive

Interview Script: 
- This problem is essentially generating the Cartesian product of 
   multiple word lists.
- I solve it using recursion where each recursive level represents 
  choosing a word from one group.
- At each step, I append the chosen word and recurse to the next group.
- When I reach the final group, I print the constructed sentence.
- This approach is simple, scalable, and works for any number of 
  word groups.

  Recursion Tree: 
     The
	 ├── dog
	 │     ├── runs
	 │     │      ├── quickly
	 │     │      └── slowly
	 │     └── walks
	 │            ├── quickly
	 │            └── slowly
	 └── cat
	       ├── runs
	       │      ├── quickly
	       │      └── slowly
	       └── walks
	              ├── quickly
	              └── slowly
  
 * */
    
    /*
    Time:  O(k^n) where k is choices per position and n is number of positions.
	Space: O(n)
    * */
    
    public static void generateSentence_REC(String[][] words) {
    	String firstWord = words[0][0];
        String punctuation = words[words.length - 1][0];

        generateSentenceHelper(words, 1, firstWord, punctuation);
    }
    private static void generateSentenceHelper(String[][] words, int index, String sentence, String punctuation) {
    	
    	// Base case → reached punctuation group
    	if(index == words.length - 1) {
    		System.out.println(sentence + punctuation);
            return;
    	}
    	
    	// Try every word in current group
    	for (int i = 0; i < words[index].length; i++) {

            String nextSentence = sentence + " " + words[index][i];

            generateSentenceHelper(words, index + 1, nextSentence, punctuation);
        }
    }
    
    /* Generate all combinations: 
    	Generate using 'The', 'A', '.', '!'. Above is limited to 'The' and '.'
    	
    	Time: O(k^n)
    	Space: O(n)
    */
    public static void generateSentence_REC_AllGroups(String[][] words) {

        if (words == null || words.length == 0) {
            return;
        }

        generateSentenceHelper(words, 0, ""); // start from index 0
    }

    private static void generateSentenceHelper(String[][] words, int index, String sentence) {

        // Base case → all groups processed
        if (index == words.length) {
            System.out.println(sentence);
            return;
        }

        // Try every word in current group
        for (int i = 0; i < words[index].length; i++) {

            String word = words[index][i];

            String nextSentence;

            // Add space only between words (not before punctuation)
            if (index == 0) {
                nextSentence = word;
            } else if (index == words.length - 1) {
                nextSentence = sentence + word; // punctuation
            } else {
                nextSentence = sentence + " " + word;
            }

            generateSentenceHelper(words, index + 1, nextSentence);
        }
    }
    
    
    
    // Follow-up: For K sentences only
    // Time: O(K * n), because it stops early 
    // Space: O(n)
    
    static int count = 0; // follow-up: track number of printed sentences

    public static void generateSentence_REC(String[][] words, int k) { // follow-up: added k

        count = 0; // follow-up: reset counter

        String firstWord = words[0][0];
        String punctuation = words[words.length - 1][0];

        generateSentenceHelper(words, 1, firstWord, punctuation, k); // follow-up: pass k
    }

    private static void generateSentenceHelper(String[][] words, int index, String sentence, String punctuation, int k) { // follow-up

        // follow-up: stop early if K sentences already generated
        if (count == k) {
            return;
        }

        // Base case → reached punctuation group
        if (index == words.length - 1) {
            System.out.println(sentence + punctuation);

            count++; // follow-up: increment count

            return;
        }

        // Try every word in current group
        for (int i = 0; i < words[index].length; i++) {

            // follow-up: prune recursion if already reached K
            if (count == k) {
                return;
            }

            String nextSentence = sentence + " " + words[index][i];

            generateSentenceHelper(words, index + 1, nextSentence, punctuation, k);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //------------------------------------------------------------------------
    
    /* WARNING::  Above Solution are GOOD for interview, AVOID below solutions */ 
    
    /* Recursive
     * 
     * Interview Script (How to Explain)

		The problem asks us to generate all valid sentences by 
		choosing one word from each group.
		
		This is essentially a Cartesian product problem where we 
		combine words from multiple lists.
		
		Since each position has multiple choices, the cleanest approach is Backtracking (DFS).
		
		Steps:
		1. Fix the first word "The" and last punctuation "." based on expected output.
		2. Recursively choose words from the remaining lists.
		3. When we reach the last position, we print the constructed sentence.
		4. Backtracking automatically explores all combinations.

		Time complexity:  O(k^n) where k is choices per position and n is number of positions.
		Space: O(n) recursion stack
     * 
     * */
    
    public static void generateSentences_Recursion(String[][] words) {
    	
    	int n = words.length;
    	
    	// Fix first word and last punctuation as per expected output
    	String firstWord = words[0][0];
    	String lastPunctuation = words[n-1][0];
    
    	// middle words (dog/cat, runs/walks, quickly/slowly)
    	String[] current = new String[3];
    	
    	int index = 1;
    	backtrack(words, index, firstWord, lastPunctuation, current);
    	
    }
 
    private static void backtrack(String[][] words, int index, String firstWord, 
    		String lastPunctuation, String[] current) {
    	// Base condition → selected all middle words
    	if(index == 4) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(firstWord);
    		for(int i=0; i< current.length; i++) {
    			sb.append(" ");
    			sb.append(current[i]);
    		}
    		sb.append(lastPunctuation);
    		
    		System.out.println(sb);
    		return;
    	}
    	
    	
    	// Explore all choices at current position
    	for(int i=0; i<words[index].length; i++) {
    		current[index - 1] = words[index][i];
    		backtrack(words, index+1, firstWord, lastPunctuation, current);
    	}
		
	}
    
    
    
    /** Intrative BFS 
     * 
     	1. We start with an empty sentence in a queue.
		2. Then for each word group:
		3. Take existing partial sentences from the queue.
		4. Expand them by adding every word from the current group.
		5. Push the new sentences back into the queue.
		6. This gradually builds all combinations.
     * */
    
    /* Time: O(O(k^n)), We cannot reduce the O(k^n) complexity because we must generate every possible sentence. 
     * Space: O(k^n)
     * */
    public static void generateSentence_BFS(String[][] words) {

        Queue<String> queue = new LinkedList<>();

        // Fix first word
        queue.offer(words[0][0]);

        // Process middle groups
        for (int i = 1; i < words.length - 1; i++) {

            int size = queue.size();

            for (int j = 0; j < size; j++) {

                String sentence = queue.poll();

                for (int k = 0; k < words[i].length; k++) {

                    queue.offer(sentence + " " + words[i][k]);
                }
            }
        }

        // Print sentences with punctuation
        while (!queue.isEmpty()) {

            String sentence = queue.poll();
            System.out.println(sentence + words[words.length - 1][0]);
        }
    }
    
    
    
    
    /* Iterative
     * 
     * Interview Script::

		This problem is essentially generating all possible sentences by 
		choosing one word from each list.
		
		Instead of recursion, I use an iterative approach similar to BFS.
		
		I start with a list containing the first word "The".
		Then for each next word group, I expand the current sentences by 
		appending every possible word.
		
		At each step, the number of sentences doubles.
		
		After processing all groups, I append the punctuation "." and print the results.
		
		Time complexity is O(k^n) where k is number of choices and n is number of positions.
     * 
     * Time: O(O(k^n)), We cannot reduce the O(k^n) complexity because we must generate every possible sentence. 
     * Space: O(k^n)
     * */

	public static void generateSentences_Interative (String[][] words) {

        List<String> sentences = new ArrayList<>();

        // Fix first word "The"
        sentences.add(words[0][0]);

        // Build sentences for middle words
        for(int i=1; i<words.length-1; i++) {

        	List<String> next = new ArrayList<>();
        	
        	for(String sentence : sentences) {
        		for(int j=0; j<words[i].length; j++) {
        			next.add(sentence + " " + words[i][j]);
        		}
        	}
        	sentences = next;
        }

        // Add punctuation "."
        for(String sentence : sentences) {
        	System.out.println(sentence + words[words.length - 1][0]);
        }
        
    }
    
}