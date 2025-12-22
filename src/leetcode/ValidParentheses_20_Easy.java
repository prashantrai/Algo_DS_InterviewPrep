package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
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
	
	/*
	1. When we see an opening bracket ((, {, [), we push its corresponding 
		closing bracket onto the stack.
	2. When we see a closing bracket, we check if it matches the top of the 
		stack (i.e., what we expect next).
	3. If mismatch or stack is empty when we need to pop → invalid.
	4. At the end, stack must be empty.
	 * */
	// Time and Space: O(n)
	public static boolean isValid(String s) {
		// ArrayDeque is not synchronized, unlike the legacy Stack class, 
		// so it has lower overhead.
        Deque<Character> stack = new ArrayDeque<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
        
        return stack.isEmpty();
    }

	// Time and Space: O(n)
	// 💡Tip: Consider using ArrayDeque<Character> instead of Stack<Character> 
	//	for better performance in Java, as Stack is synchronized and legacy.
	public static boolean isValid_Stk(String s) {
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
	
	
	// Time and Space: O(n)
	// Little slow due to HashMap overhead, even though time and space are same.
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
