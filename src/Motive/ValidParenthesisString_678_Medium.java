package Motive;
public class ValidParenthesisString_678_Medium {

	/* I validate both directions: left-to-right ensures every 
	 closing parenthesis has a possible opener before it, 
	 while right-to-left ensures every opening parenthesis has 
	 a possible closer after it; I let * help in either role.
	 * */
	
	// Time: O(n)
	// Space: O(1)
    public boolean checkValidString_LeetCode(String s) {
        int openCount = 0;
        int closeCount = 0;
        int length = s.length() - 1;
        
        // Traverse the string from both ends simultaneously
        for (int i = 0; i <= length; i++) {
        	// Scan from left
            // Count open parentheses or asterisks
            if (s.charAt(i) == '(' || s.charAt(i) == '*') {
                openCount++;
            } else {
                openCount--;
            }
            
            // Scan from right
            // Count close parentheses or asterisks
            if (s.charAt(length - i) == ')' || s.charAt(length - i) == '*') {
                closeCount++;
            } else {
                closeCount--;
            }
            
            // If at any point open count or close count goes negative, the string is invalid
            if (openCount < 0 || closeCount < 0) {
                return false;
            }
        }
        
        // If open count and close count are both non-negative, the string is valid
        return true;
    }
	
	
	/* Interview Explanation Before Coding: 
	 * 
	 * “I'll scan the string once while
	 * maintaining two counters: minOpen and maxOpen. minOpen represents the minimum
	 * number of unmatched opening parentheses we could currently have, and maxOpen
	 * represents the maximum. 
	 * 
	 * For '(', both increase. 
	 * For ')', both decrease, although minOpen can't stay below zero because 
	 * we're tracking a feasible minimum. 
	 * 
	 * For '*', I'll treat it pessimistically as ')' for minOpen, so
	 * minOpen decreases, and optimistically as '(' for maxOpen, so maxOpen
	 * increases. 
	 * 
	 * If maxOpen ever becomes negative, we've encountered more closing
	 * parentheses than can possibly be matched, even if every previous star were an
	 * opening parenthesis, so I can immediately return false. 
	 * 
	 * I'll clamp minOpen to zero because a negative minimum just means 
	 * there's some interpretation where a star is empty instead. 
	 * 
	 * At the end, the string is valid exactly when minOpen is zero, meaning there's at 
	 * least one interpretation where no opening parentheses remain unmatched.”
	 */
	
	/* Step-by-Step Algorithm
		1. Initialize:
		minOpen = 0
		maxOpen = 0
		
		2. For every character:
		If '(':
		minOpen++
		maxOpen++
		
		If ')':
		minOpen--
		maxOpen--
		
		If '*':
		minOpen--    // treat * as ')'
		maxOpen++    // treat * as '('
		
		3. If:
		maxOpen < 0
		
		return false.
		4. Clamp:
		minOpen = Math.max(minOpen, 0);
		
		5. After processing everything, return:
		minOpen == 0
	 
	 * */
	
	// Time: O(n)
	// Space: O(1)
    public static boolean checkValidString(String s) {
        if (s == null) {
            return false;
        }

        int minOpen = 0;
        int maxOpen = 0;

        // Track the minimum and maximum possible number
        // of unmatched opening parentheses.
        for (char c : s.toCharArray()) {

            if (c == '(') {
                minOpen++;
                maxOpen++;

            } else if (c == ')') {
                minOpen--;
                maxOpen--;

            } else if (c == '*') {
                // For the minimum, '*' acts like ')'.
                // For the maximum, '*' acts like '('.
                minOpen--;
                maxOpen++;

            } else {
                return false;
            }

            // Even the maximum possible opens cannot match
            // all closing parentheses seen so far.
            if (maxOpen < 0) {
                return false;
            }

            // A negative minimum just means we can choose
            // '*' as empty instead.
            minOpen = Math.max(minOpen, 0);
        }

        // Valid if at least one interpretation closes everything.
        return minOpen == 0;
    }

    public static void main(String[] args) {
        runTest("Test 1 - Simple valid",
                "()",
                true);

        runTest("Test 2 - Star as closing parenthesis",
                "(*)",
                true);

        runTest("Test 3 - Star fills missing close",
                "(*))",
                true);

        runTest("Test 4 - Too many closing parentheses",
                ")*(",
                false);

        runTest("Test 5 - Single star",
                "*",
                true);

        runTest("Test 6 - Empty string",
                "",
                true);

        runTest("Test 7 - Impossible leftover opens",
                "((*)",
                true);

        runTest("Test 8 - Invalid characters",
                "(a)",
                false);
    }

    private static void runTest(String name, String input, boolean expected) {
        boolean actual = checkValidString(input);

        System.out.println(name);
        System.out.println("Input:    \"" + input + "\"");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println();
    }
}