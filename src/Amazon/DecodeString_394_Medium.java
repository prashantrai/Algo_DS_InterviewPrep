package Amazon;

import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString_394_Medium {

	public static void main(String[] args) {

		String s = "3[a]2[bc]";
		String res = decodeString(s);
		System.out.println("Expected: aaabcbc, Actual: "+ res);
		
		s = "3[a2[c]]";
		res = decodeString(s);
		System.out.println("Expected: accaccacc, Actual: "+ res);
		
		s = "2[abc]3[cd]ef";
		res = decodeString(s);
		System.out.println("Expected: abcabccdcdcdef, Actual: "+ res);
		
	}
	

	/* https://leetcode.com/problems/decode-string/discuss/87534/Simple-Java-Solution-using-Stack
	 * 
	 * Complexity Analysis: 
		Assume, n is the length of the string s.
		
		Time Complexity:O(n + outputSize), The runtime is proportional to the size of 
		the decoded string, because we eventually have to construct every output character.
		
		Space Complexity: O(n + outputSize), The stacks hold parsing state for nested brackets, 
		and the StringBuilders hold the decoded result.
	 */
	
	public static String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();

        StringBuilder current = new StringBuilder();
        int number = 0;

        for (char ch : s.toCharArray()) {

            if (Character.isDigit(ch)) {
                // Build the full repeat count.
                // Example: '1', '2' -> 12
                number = number * 10 + (ch - '0');

            } else if (ch == '[') {
                // Save the outer context before decoding
                // the nested substring.
                countStack.push(number);
                stringStack.push(current);

                current = new StringBuilder();
                number = 0;

            } else if (ch == ']') {
                // The current substring is complete.
                // Expand it and attach it back to its parent.
                int repeat = countStack.pop();
                StringBuilder previous = stringStack.pop();

                for (int i = 0; i < repeat; i++) {
                    previous.append(current);
                }

                current = previous;

            } else {
                // Regular character belongs to the current level.
                current.append(ch);
            }
        }

        return current.toString();
    }

}
