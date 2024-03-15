package Facebook;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class MinimumRemoveToMakeValidParentheses_1249_Medium {

	public static void main(String[] args) {

		String s = "lee(t(c)o)de)";
		System.out.println("Expected: lee(t(c)o)de Actual: " + minRemoveToMakeValid(s));
		
	}
	
	/*
    Time & Space: O(N)
    */
    
    public static String minRemoveToMakeValid(String s) {
        Set<Integer> indexToRemove = new HashSet<>();
        // Stack<Integer> stack = new Stack<>(); // stack can be used too
        Deque<Integer> stack = new ArrayDeque<>();
        
        for(int i=0; i<s.length(); i++) {
            if( s.charAt(i) == '(' ) {
                stack.push(i);
                
            } else if( s.charAt(i) == ')' ) {
                if(stack.isEmpty()) {
                    indexToRemove.add(i);
                } else {
                    stack.pop();
                }
            }
        }
        
        // Put any indexes remaining on stack into the set.
        while(!stack.isEmpty()) 
            indexToRemove.add(stack.pop());
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<s.length(); i++) {
            if(!indexToRemove.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }

}
