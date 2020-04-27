package leetcode;

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
	
	// https://leetcode.com/problems/decode-string/discuss/87534/Simple-Java-Solution-using-Stack
	// O(n) - Space and Runtime 
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
