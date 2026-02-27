package Geico;
public class StringTransformer {

	
	/*
	 * For a given number n, write a function that takes a string containing 
	 * only "a"s given n number of times. Any "aa" will be transformed to "b" 
	 * and any "bb" will be transformed to "c". 
	 * 
	 * For example, String "aaaaaa" -> "bbb" -> "cb". "zz" cannot be transformed 
	 * to anything. For any given number, generate the transformed string.
	 * 
	 * 
	 * Interview Script::
	 * 	I treat this problem like carry-forward counting.
		I count how many of each character I have.
		Every two same characters produce one next character.
		So I propagate pairs forward using division and modulo.
		Finally, I rebuild the string from remaining counts.
		This avoids repeated string rebuilding and runs in O(n).
	 */
	
    /**
     * Transforms n 'a's according to rules:
     * aa -> b, bb -> c, cc -> d, ...
     * zz cannot be transformed further.
     */
	
	// Time: O(26 + result length) ≈ O(N) 
	// Space:  O(26) = O(1)
    public static String transform(int n) {

        // Edge case
        if (n <= 0) {
            return "";
        }

        // Count of characters from 'a' to 'z'
        int[] count = new int[26];

        // Initially we have n 'a's
        count[0] = n;

        // Propagate pairs forward
        for (int i = 0; i < 25; i++) {

            int pairs = count[i] / 2;   // How many pairs
            count[i] = count[i] % 2;    // Remaining single chars

            // Add pairs to next character
            count[i + 1] += pairs;
        }

        // Build final result
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 26; i++) {

            char ch = (char) ('a' + i);

            // Append remaining characters
            for (int j = 0; j < count[i]; j++) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // Test the solution
    public static void main(String[] args) {

        // Basic test
        System.out.println(transform(6)); // Expected: cb

        // Edge cases
        System.out.println(transform(1)); // a
        System.out.println(transform(0)); // empty

        // More tests
        System.out.println(transform(2)); // b
        System.out.println(transform(3)); // ba
        System.out.println(transform(4)); // c
        System.out.println(transform(5)); // ca

        // Large input
        System.out.println(transform(1000000).length());
    }
}