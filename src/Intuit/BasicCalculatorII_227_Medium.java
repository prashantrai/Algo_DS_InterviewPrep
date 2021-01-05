package Intuit;

import java.util.Stack;

public class BasicCalculatorII_227_Medium {

	public static void main(String[] args) {
		String s = "3+2*2";
		System.out.println("Expected: 7, Actual: "+ calculate(s));
		System.out.println("Expected: 7, Actual: "+ calculate2(s)); // with stack
		
		s = "3/2";
		System.out.println("Expected: 1, Actual: "+ calculate(s));
		System.out.println("Expected: 1, Actual: "+ calculate2(s)); // with stack
		
		s = "3+5 / 2";
		System.out.println("Expected: 5, Actual: "+ calculate(s));
		System.out.println("Expected: 5, Actual: "+ calculate2(s)); // with stack
	}
	
	// Time: O(N), Space: O(1) because no stack used
    public static int calculate(String s) {
        if(s == null || s.length() == 0) return 0;
        
        int len = s.length();
        int currNum = 0;
        int lastNum = 0;
        int result = 0;
        char sign = '+'; //operator

        for(int i=0; i<len; i++) {
            char curr_char = s.charAt(i);
            if(Character.isDigit(curr_char)) {
                currNum = (currNum * 10) + (curr_char - '0');
            } 
            
            if(!Character.isDigit(curr_char) && !Character.isWhitespace(curr_char) || i == len-1) {
                if(sign == '+' || sign == '-') {
                    result += lastNum;
                    lastNum = (sign == '+') ? currNum : -currNum;
                } else if(sign == '*') {
                    lastNum = lastNum * currNum;
                } else if(sign == '/') {
                    lastNum = lastNum / currNum;
                }
                sign = curr_char;
                currNum = 0; // reset
            }
        }
        
        result += lastNum;
        return result;        
    }
    
    // Time: O(N), Space: O(N) because of stack
    public static int calculate2(String s) {
        if(s == null || s.length() == 0) return 0;

        Stack<Integer> stk = new Stack<>();
        
        int len = s.length();
        int currNum = 0;
        char sign = '+'; //operator
        
        for(int i=0; i<len; i++) {
            char curr_char = s.charAt(i);
            if(Character.isDigit(curr_char)) {
                currNum  = (currNum*10) + (curr_char - '0');
            }
            
            if(!Character.isDigit(curr_char) && curr_char != ' ' || i == len-1) {
                
                if(sign == '-') {
                    stk.push(-currNum);
                } else if(sign == '+') {
                    stk.push(currNum);
                } else if(sign == '*') {
                    stk.push(stk.pop() * currNum);
                } else if(sign == '/') {
                    stk.push(stk.pop() / currNum);
                } 
                
                sign = curr_char;
                currNum = 0; // reset for next iteration
            }
        }
        
        int result = 0;
        while(!stk.isEmpty()) {
            result += stk.pop();
        }
        
        return result;
    }

}
