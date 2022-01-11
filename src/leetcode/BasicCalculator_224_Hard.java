package leetcode;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BasicCalculator_224_Hard {

	public static void main(String[] args) {
		String s = "1 + 1"; 
		System.out.println("Expected: 2, Actual: "+ calculate(s));
		System.out.println("REC: Expected: 2, Actual: "+ calculateREC(s));
		
		s = " 2-1 + 2 ";
		System.out.println("\nExpected: 3, Actual: "+ calculate(s));
		System.out.println("REC: Expected: 3, Actual: "+ calculateREC(s));
		
		s = "(1+(4+5+2)-3)+(6+8)";
		System.out.println("\nExpected: 23, Actual: "+ calculate(s));
		System.out.println("REC: Expected: 23, Actual: "+ calculateREC(s));
	}
	
	/*
	 * https://leetcode.com/problems/basic-calculator
	 * 
	 * Reference (from comment by yavinci) : https://leetcode.com/problems/basic-calculator/discuss/62361/Iterative-Java-solution-with-stack
	 * 
	 * Time and space : O(N)
	 * */
	/*
		Principle:
		
			(Sign before '+'/'-') = (This context sign);
			(Sign after '+'/'-') = (This context sign) * (1 or -1);

		Algorithm:
		
			Start from +1 sign and scan s from left to right;
			if c == digit: This number = Last digit * 10 + This digit;
			if c == '+': Add num to result before this sign; This sign = Last context sign * 1; clear num;
			if c == '-': Add num to result before this sign; This sign = Last context sign * -1; clear num;
			if c == '(': Push this context sign to stack;
			if c == ')': Pop this context and we come back to last context;
			Add the last num. This is because we only add number after '+' / '-'.
	 */
	
	
	// iterative
    public static int calculate(String s) {
        if(s == null || s.length() == 0) return 0;
        
        int len = s.length();
        int result = 0;
        int num = 0;
        int sign = 1;
        
        Stack<Integer> stk = new Stack<>();
        stk.push(sign);
        
        for(int i=0; i<len; i++) {
            char currChar = s.charAt(i);
            
            if(Character.isDigit(currChar)) {
                num = num * 10 + currChar - '0';
            }
            
            // if currChar is '+' or '-' or '(' or ')'
            //if(!Character.isDigit(currChar) && Character.isWhitespace(currChar) || i == len -1) {
            if(currChar == '+' || currChar == '-') {
                result += (sign * num); // apply current sign on num and then add to result
                sign = stk.peek() * (currChar == '+' ? 1 : -1);
                num = 0;
            
            } else if(currChar == '(') {
                stk.push(sign);
                
            }  else if(currChar == ')') {
                stk.pop();
            }

        }
        
        result += (sign * num);
        
        return result;
    }
	
	
	
	// Recursive - this works for BasicCalculatorIII_772_Hard too
	public static int calculateREC(String s) {
		Queue<Character> tokens = new ArrayDeque<Character>();

		for (char c : s.toCharArray()) {
			if (c != ' ')
				tokens.offer(c);
		}

		tokens.offer('+');
		return helper(tokens);
    }
    
    private static int helper(Queue<Character> tokens) {
        
        int preOp = '+';
        int num = 0;
        int sum = 0;
        int prev = 0;
        
        while (!tokens.isEmpty()) {
            char c = tokens.poll();
            
            if(Character.isDigit(c)) {
                num = num*10 + c - '0';    
            } else if(c == '(') {
                num = helper(tokens);
            } else {
                switch(preOp) {
                    case '+':
                        sum += prev;
                        prev = num;
                        break;
                    case '-':
                        sum += prev;
                        prev = -num;
                        break;
                    case '*':
                        prev *= num;
                        break;
                    case '/':
                        prev /= num;
                        break;
                }
                if (c == ')') {
                    break;
                }
                preOp = c;
                num = 0;
            }
        }
        return (sum + prev);

	}
	
}
