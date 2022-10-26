package Bloomberg;

import java.util.Stack;

public class RemoveAllAdjacentDuplicatesInString_1047_Easy {

	public static void main(String[] args) {
		String s = "abbaca";
		System.out.println("Expected: ca, Actual: " + removeDuplicates(s));
		System.out.println("Expected: ca, Actual: " + removeDuplicates2(s));
	}
	
	/*
    Time: O(N)
    Space: O(N-D), where D is total length of all the duplicates
    */
    
    // Using StringBuilder as stack
    // remove the last added char from StringBuilder if it matches the current
    public static String removeDuplicates(String S) {
        StringBuilder sb = new StringBuilder();
        for(char c : S.toCharArray()) {
            int len = sb.length();
            
            if(len != 0 && sb.charAt(len-1) == c) {
                sb.deleteCharAt(len-1);
            } else {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
    
    // with stack
    public static String removeDuplicates2(String S) {
        
        Stack<Character> stk = new Stack<>();
        
        // abbaca=> ca
        for(char c : S.toCharArray()) {
            if(stk.isEmpty()) {
                stk.push(c);
                continue;
            }

            if(stk.peek() == c) {
                stk.pop();
                continue;
            } 
            stk.push(c);
        }
        
        StringBuilder sb = new StringBuilder();
        while(!stk.isEmpty()) {
            sb.append(stk.pop());
        }
        return sb.reverse().toString();
    }

}
