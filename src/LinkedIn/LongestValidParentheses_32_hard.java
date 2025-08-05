package LinkedIn;

import java.util.Stack;

public class LongestValidParentheses_32_hard {

	public static void main(String[] args) {
		System.out.println(longestValidParentheses("(()"));        // 2
        System.out.println(longestValidParentheses(")()())"));     // 4
        System.out.println(longestValidParentheses(""));           // 0
        System.out.println(longestValidParentheses("()(()"));      // 2
        System.out.println(longestValidParentheses("(()())"));     // 6
        System.out.println(longestValidParentheses("()(()))"));    // 6
        System.out.println(longestValidParentheses("((((((("));    // 0
        
        // 2 pointer approach
        System.out.println(longestValidParentheses("(()"));          // "()"
        System.out.println(longestValidParentheses(")()())"));       // "()()"
        System.out.println(longestValidParentheses(""));             // ""
        System.out.println(longestValidParentheses("(()())"));       // "(()())"
        System.out.println(longestValidParentheses("((()))"));       // "((()))"
        System.out.println(longestValidParentheses("())(())"));      // "(())"
        System.out.println(longestValidParentheses("()(()))"));      // "()(()))"
        System.out.println(longestValidParentheses("(()(((()"));     // "()"


	}
	
	// Time & Space: O(N)
	// Using Stack
    public static int longestValidParentheses(String s) {
        Stack<Integer> stk = new Stack<>();
        stk.push(-1);
        int maxLen = 0;
        
        // if we want to print the the valid substring
        // instead of only returning it's length
        int startIdx = 0;

        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stk.push(i);
            } else {
                // pop the last unmatched '(' or base
                stk.pop();

                if(!stk.isEmpty()) {
                    // valid substring: i - index of last unmatched '('
                    maxLen = Math.max(maxLen, i - stk.peek());
                    
                    startIdx = stk.peek() + 1; // new start of valid substring
                } else {
                    // unmatched ')', push its index as new base
                    stk.push(i);
                }
            }
        }
        
        String validSubStr = s.substring(startIdx, startIdx + maxLen);
        System.out.print("validSubStr: " + validSubStr + " Len: ");
        
        return maxLen;
    }
    
    // Using Stack, extension of above solution
    // Return valid substring instead len
    public String longestValidParentheses4(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // base index for valid substring
        int maxLen = 0;           // maximum length of valid substring
        int startIndex = 0;       // start index of longest valid substring

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                // push index of '('
                stack.push(i);
            } else {
                // pop the last unmatched '(' or base
                stack.pop();
                if (!stack.isEmpty()) {
                    int len = i - stack.peek(); // current valid length
                    // 🔄 UPDATE: track start index and maxLen
                    if (len > maxLen) {
                        maxLen = len;
                        startIndex = stack.peek() + 1; // new start of valid substring
                    }
                } else {
                    // unmatched ')', set new base
                    stack.push(i);
                }
            }
        }
        // 🔄 RETURN the longest valid substring instead of length
        return s.substring(startIndex, startIndex + maxLen);
    }
    
    
    // Tow Pointes Approach
    // Time: O(N), Space: O(1)
    public int longestValidParentheses2(String s) {
        int left = 0, right = 0;
        int maxLen = 0;

        // Left to right scan
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') left++;
            else right++;
            if(left == right) {
                maxLen = Math.max(maxLen, 2*right);
            } else if(right > left) {
                // Invalid state: too many ')'
                left = right = 0; //reset
            }
        }

        // Right to left scan (for cases like "(()")
        left = right = 0;
        for(int i=s.length()-1; i>=0; i--) {
            char c = s.charAt(i);
            if(c == '(') left++;
            else right++;
            if(left == right) {
                maxLen = Math.max(maxLen, 2*left);
            } else if(left > right) {
                // Invalid state: too many '('
                left = right = 0; //reset
            }
        }
        return maxLen;
    }
    
    // When we need to return the string instead of len
    public String longestValidParentheses3(String s) {
        int left = 0, right = 0;
        int maxLen = 0;
        int endIndex = -1; // 🔄 NEW: Store the end index of the longest valid substring

        // 🔁 Left to Right scan
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                int len = 2 * right;
                if (len > maxLen) {
                    maxLen = len;
                    endIndex = i; // 🔄 Track end of longest valid substring
                }
            } else if (right > left) {
                left = right = 0; // Reset on imbalance
            }
        }

        // 🔁 Right to Left scan
        left = right = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);

            if (c == ')') {
                right++;
            } else {
                left++;
            }

            if (left == right) {
                int len = 2 * left;
                if (len > maxLen) {
                    maxLen = len;
                    endIndex = i + len - 1; // 🔄 Update end index in reverse pass
                }
            } else if (left > right) {
                left = right = 0; // Reset on imbalance
            }
        }

        // 🔚 Return the substring from calculated start index
        if (maxLen > 0 && endIndex != -1) {
            int startIndex = endIndex - maxLen + 1; // 🔄 NEW: calculate start index
            return s.substring(startIndex, endIndex + 1); // 🔄 Extract and return valid substring
        }

        return ""; // 🔄 No valid substring found
    }

}
