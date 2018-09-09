package com.interview.questions;

import java.util.Stack;

/* 
"())((())(()" -> "()(())()"

"()" -> "()"
")(" -> ""
"((((())"

 */

public class BitGoPrintValidParenthesis {

	public static void main(String[] args) {
	    
	    String s = "())((())(()";
	    //printParens(s);
	    //s = "((((())";
	    //s = "))((";
	    //s = "(())";
	    
	    boolean b = isBalance(s);
	    System.out.println(b);
	    
	    
	    s = "((())(";
	    //s = "((())(()";
	    s = "())((())(()";
//	    s = ")()";
//	    s = ")(";
	    printBalancedParens(s);
	    
	}

	public static void printBalancedParens(String s) {
		
		int open = 0;
	    int close = 0;
	    String op = "";
	    String res = "";
		Stack<Character> stack = new Stack<Character>();
		
		for(char c : s.toCharArray()) {
			stack.push(c);
		}
		//-- ())((())(()
	    while (!stack.isEmpty()) {
	    	
	    	char c = stack.pop();
	    	
	    	//--When first popped element (last in input string) is OPEN parenthesis continue the loop 
	    	if(c == '(' & close == 0) {  
	    		open = 0;
	    		close = 0;
	    		op = "";
	    		continue;
	    	}
	    	if(c == '(') {
	    		open++;
	    		op = c + op;
	    	}
	    	else if(c == ')') {
	    		close++;
	    		op += c;
	    	}
	    	if(open == close) {
	    		open=0;
	    		close=0;
	    		res = op; 
	    		System.out.print(res);
	    		op = "";
	    	}
	    	
		}
	    
	    if(close > open && open > 0) {
	    	System.out.println(op.substring(0, close));
	    }
		
	}
		
	public static boolean isBalance(String s) {
		  
	    char[] arr = s.toCharArray();
	    String temp = ""; //--can be change StringBulder
	    
	    
	    int open = 0;
	    int close = 0;
	    
	    for(char c : arr) {
	      
	      if(c == '(') {
	        open++;
	        
	      } else if(c == ')') {
	      
	        close++;
	      }
	      temp += ""+c;
	      
	      if(open == close) {
	        System.out.println(temp);
	        open=0;
	        close=0;
	      }  
	      if(close > open) {
	    	  
	        return false;
	      }
	    }
	    
	    if(open > close) return false;
	    
	    return true;
	  }
	
	
	

}

