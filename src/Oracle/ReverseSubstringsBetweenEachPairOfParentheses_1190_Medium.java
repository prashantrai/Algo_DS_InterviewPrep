package Oracle;

import java.util.Stack;

public class ReverseSubstringsBetweenEachPairOfParentheses_1190_Medium {

	public static void main(String[] args) {
		
		String s = "(u(love)i)";
		String res = reverseParentheses(s);
		System.out.println("Expected: iloveu Actual: " + res);
	}
	
	/* Solution from: https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/383670/JavaC%2B%2BPython-Tenet-O(N)-Solution
	 * 
	 *  Time and space: O(N)
	 *  
	 *  
	 *  Solution can be broken down into 3 steps:

		1. track matching braces in map
		2. use matching braces as wormholes, i.e., jump to the matching brace using the map
		3. change direction every time we hit a brace
		
		Ex: lee(ct)ode
		When i is at index 3, 'res' is ['l', 'e', 'e']. Using the map, we jump to 6 and change 
		direction. Add 't' and 'c' to 'res'. Now i is back at 3 again. We again jump to 6 and 
		change direction one more time. Now add rest of the characters to 'res'.
	 * */

	public static String reverseParentheses(String s) {
        int n = s.length();
        Stack<Integer> opened = new Stack<>();
        int[] pair = new int[n];
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '(')
                opened.push(i);
            if (s.charAt(i) == ')') {
                int j = opened.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, d = 1; i < n; i += d) { // d = direction
            if (s.charAt(i) == '(' || s.charAt(i) == ')') {
                i = pair[i];
                d = -d;
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
	
}
