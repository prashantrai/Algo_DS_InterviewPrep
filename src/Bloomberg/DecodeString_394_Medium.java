package Bloomberg;

import java.util.Stack;

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
		
		Time Complexity: O(maxK⋅n), where maxK is the maximum value of k and n is the length of a 
			given string s. We traverse a string of size n and iterate k times to decode each pattern 
			of form k[string]. This gives us worst case time complexity as O(maxK⋅n).
		
		Space Complexity: O(m+n), where m is the number of letters(a-z) and n is the number of digits(0-9) 
			in string s. In worst case, the maximum size of stringStack and countStack could be m and n 
			respectively.
	 */
	
	public static String decodeString(String s) {
        
        Stack<Integer> stk_freq = new Stack<>();
        Stack<StringBuilder> stk_str = new Stack<>();
        StringBuilder curr = new StringBuilder();
        int k = 0;
        
        for(char c : s.toCharArray()) {
            
            if (Character.isDigit(c)) {
                //when freq is more than 1 digit
                k = k * 10 + (c - '0');
            }
            else if(c == '[') {
                stk_freq.push(k);
                stk_str.push(curr);
                curr = new StringBuilder();
                k = 0;
            } else if(c == ']') {
                StringBuilder tmp = curr;
                curr = stk_str.pop();
                for(k = stk_freq.pop(); k >0; k--) {
                    curr.append(tmp);
                }
            } else {
                curr.append(c);
            }
        }
        
        return curr.toString();
    }

}
