package Expedia;

import java.util.ArrayDeque;
import java.util.Deque;

public class UsingARobotToPrintTheLexicographicallySmallestString_2434_Medium {

	public static void main(String[] args) {
        run("zza", "azz");     // example
        run("bac", "abc");     // example
        run("bdda", "addb");   // example

        run("a", "a");         // edge: single char
        run("aaaa", "aaaa");   // edge: all same
        run("abc", "abc");     // already increasing
        run("cba", "abc");     // decreasing
        run("azb", "abz");     // mixed
        run("baca", "aacb");   // good tricky case
    }

    private static void run(String s, String expected) {
        String actual = robotWithString(s);
        System.out.println(
            "Input: " + s +
            " | Output: " + actual +
            " | Expected: " + expected +
            " | " + (actual.equals(expected) ? "PASS" : "FAIL")
        );
    }
	
	/* Interview Script:

    "Since the robot uses a stack, once a character is pushed, I can't access 
    anything below it. The decision is only whether to pop now or wait. If a 
    smaller character still exists in the remaining string, printing a larger 
    character now would hurt the lexicographical order, so I should wait. 
    
    Therefore I'll precompute the smallest remaining character from every index 
    using a suffix minimum array. While scanning the string, I push each character, 
    and I keep popping while the stack top is not greater than the smallest remaining character. 
    Every character is pushed and popped exactly once, giving O(n) time."
    */

    // Time: O(n)
    // Space: O(n)

    public static String robotWithString(String s) {
        int n = s.length();

        /* minSuffix prepare: 
            Index : 0 1 2 3
            Chars : b a c d

            minSuffix
                i=3 -> d
                i=2 -> min(c,d)=c
                i=1 -> min(a,c)=a
                i=0 -> min(b,a)=a

            Result: [a a c d]
            Now while processing index i, we know exactly the smallest character that still exists after this point.

            This array takes only O(n).
        */
        // minSuffix[i] = smallest character from i to end
        char[] minSuffix = new char[n];
        minSuffix[n-1] = s.charAt(n-1);

        // populate minSuffix with min char at each index
        for (int i = n - 2; i >= 0; i--) {
            minSuffix[i] = (char) Math.min(s.charAt(i), minSuffix[i + 1]);
        }

        StringBuilder ans = new StringBuilder();
        Deque<Character> stk = new ArrayDeque<>();

        // Process every character except the last one
        for(int i=0; i<n; i++) {
            stk.push(s.charAt(i));

            // Pop only if there are remaining characters
            while(i < n-1 && !stk.isEmpty() && stk.peek() <= minSuffix[i+1] ) {
                ans.append(stk.pop());
            }
        }

        // After processing all input,
        // nothing remains, so pop everything.
        while (!stk.isEmpty()) {
            ans.append(stk.pop());
        }

        return ans.toString();
    }

}
