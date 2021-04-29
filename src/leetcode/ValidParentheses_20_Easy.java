package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses_20_Easy {

	public static void main(String[] args) {
		
		String s = "()";
		System.out.println("Expected: true, Acual: "+isValid(s));
		
		s = "()[]{}";
		System.out.println("Expected: true, Acual: "+isValid(s));
		
		s = "(]";
		System.out.println("Expected: false, Acual: "+isValid(s));
		
		s = "([)]";
		System.out.println("Expected: false, Acual: "+isValid(s));
		
		s = "{[]}";
		System.out.println("Expected: true, Acual: "+isValid(s));
	}
	
	// https://leetcode.com/problems/valid-parentheses/discuss/9178/Short-java-solution
	public static boolean isValid(String s) {
        Stack<Character> stk = new Stack<>();
        
        for(char c : s.toCharArray()) {
            
            if(c == '(')
                stk.push(')');
            else if(c == '{')
                stk.push('}');
            else if(c == '[')
                stk.push(']');
            else if(stk.isEmpty() || stk.pop() != c) return false;
        }
        
        return stk.isEmpty();
    }
	
	public static boolean isValid2(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')','(');
        map.put('}','{');
        map.put(']','[');
        
        Stack<Character> stk = new Stack<>();
        for(char c : s.toCharArray()) {
            
            // If the current character is a closing bracket.
            if(map.containsKey(c)) {
                 // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
                char top = stk.isEmpty() ? '#' : stk.pop();
               
                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if(top != map.get(c)) return false;
            } else {
                stk.push(c);
            }
        }
        return stk.isEmpty();        
    }
}
