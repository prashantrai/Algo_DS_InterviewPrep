package test.practice.amazon;

import java.util.HashMap;
import java.util.Stack;

public class ValidPrentheses {

	//https://leetcode.com/problems/valid-parentheses/
	public static void main(String[] args) {
		
		String s = "((()(())))";
		s = "()[]{}";
		s = "()";
		System.out.println(isValid2(s));
	}
	

	 public static boolean isValid2(String s) {
	        HashMap<Character, Character> mappings = new HashMap<>();
	        mappings.put(')', '(');
	        mappings.put('}', '{');
	        mappings.put(']', '[');
	        
	        Stack<Character> stk = new Stack<>();
	        for(int i=0; i<s.length(); i++) {
	            char c = s.charAt(i);
	            
	            if(mappings.containsKey(c)) {
	                char top = stk.isEmpty() ? '#' : stk.pop();
	                if(mappings.get(c) != top) {
	                    return false;
	                }               
	            }
	            else {
	                stk.push(c);
	            }
	        }
	        System.out.println(stk);
	        return stk.isEmpty();
	    }
	  public static boolean isValid(String s) {
		   
		  HashMap<Character, Character> mappings = new HashMap<>();
		  mappings.put(')', '(');
		  mappings.put('}', '{');
		  mappings.put(']', '[');


	    // Initialize a stack to be used in the algorithm.
	    Stack<Character> stack = new Stack<Character>();

	    for (int i = 0; i < s.length(); i++) {
	      char c = s.charAt(i);

	      // If the current character is a closing bracket.
	      if (mappings.containsKey(c)) {

	        // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
	        char topElement = stack.empty() ? '#' : stack.pop();

	        // If the mapping for this bracket doesn't match the stack's top element, return false.
	        if (topElement != mappings.get(c)) {
	          return false;
	        }
	      } else {
	        // If it was an opening bracket, push to the stack.
	        stack.push(c);
	      }
	    }

	    // If the stack still contains elements, then it is an invalid expression.
	    return stack.isEmpty();
	  }
}
