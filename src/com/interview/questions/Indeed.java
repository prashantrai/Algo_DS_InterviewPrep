package com.interview.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Indeed {

	/*
	 * You are building an educational website and want to create a simple
	 * calculator for students to use. The calculator will only allow addition
	 * and subtraction of positive integers.
	 * 
	 * Given an expression string using the "+" and "-" operators like "5+16-2",
	 * write a function to find the total.
	 * 
	 * 
	 * Sample input/output: "6+9-12" => 3 "1+2-3+4-5+6-7" => -2
	 * 
	 * 
	 */

	public static void main(String[] args) {

		String expression1 = "6+9-12"; // = 3
		String expression2 = "1+2-3+4-5+6-7"; // = -2
								        
		//int res = helper2(expression1);
		//System.out.println(res);
		
		int res = helper2(expression2);

		System.out.println(res);

	}

	/*
	 * Alternate approach could be to push everthing in stack and then pop,
	 * O(n+n) runtime and O(n) space
	 */
	
	//--no working, incorrect result for second input
	
	public static int helper2(String s) {
		
		if (s == null || s.length() == 0)
			return Integer.MIN_VALUE;
		
		int res = 0;
		int i=0;
		Stack<String> stk = new Stack<String>();
		List<String> list = new ArrayList<>();
		StringBuilder sb  = new StringBuilder();
		//--Prepare stack
		while (i<s.length()) {
			
			if(s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				sb.append(s.charAt(i));
				
			} else {
				stk.push(sb.toString());
				stk.push(""+s.charAt(i));
				list.add(sb.toString());
				list.add(""+s.charAt(i));
				sb.delete(0, sb.length());
				
			}
			i++;
		}
		//--for end values (e.g in first expression else will not execute because after sb=12 we will reach the length of str and control will come out of loop)
		stk.push(sb.toString());
		list.add(sb.toString());
		sb.delete(0, sb.length());
		
		System.out.println(stk);
		System.out.println("list: "+list);
		
		while(!stk.isEmpty() && stk.size() > 1) {
			int v1 = Integer.parseInt(stk.pop());
			String op = stk.pop();
			int v2 = Integer.parseInt(stk.pop());
			
			switch(op) {
				
				case "+":
					res = v1 + v2;
					stk.push(""+res);
					break;
				case "-":
					res = v1 - v2;
					stk.push(""+res);
			}
		}
		
		System.out.println(stk);
		
		add(list);
		
		return res;
		
	}
	
	private static int add(List<String> lst) {
		int res = 0;
		int i=0;
		while(i<lst.size()) {
			int v1 = Integer.parseInt(lst.get(i++));
			String op = lst.get(i++);
			int v2 = Integer.parseInt(lst.get(i++));
			
			switch(op) {
				case "+":
					res += v1 + v2;
					break;
				case "-":
					res += v1 - v2;
					//break;
			}
			System.out.println(">>res:: "+res);
		}
		return res;
	}

	public static int helper(String s) {

		if (s == null || s.length() == 0)
			return Integer.MIN_VALUE;

		int result = 0;
		boolean isOperator = false;

		Stack<String> stk = new Stack<String>();
		String temp = ""; //--this can be replaced with StringBuilder

		for (int i = 0; i < s.length(); i++) {

			while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
				temp += "" + s.charAt(i);
				System.out.println("temp. " + temp);
				i++;
				continue;
			}
			
			
			if(!temp.isEmpty()) {
				stk.push(temp);
				temp="";
			}

			if(i < s.length()) 
				stk.push("" + s.charAt(i));

			System.out.println("4. " + stk);

			if (i < s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
				isOperator = true;
				continue;
			}

			if (isOperator) {

				int val1 = Integer.parseInt(stk.pop());
				String opr = stk.pop();
				int val2 = Integer.parseInt(stk.pop());

				if ("+".equals(opr)) {
					result = val1 + val2;
					stk.push("" + result);

					System.out.println("1. " + result);
				} else if ("-".equals(opr)) {
					System.out.println("1. " + result);
					result = val2 - val1;
					stk.push("" + result);
					System.out.println("2. " + result);
				}
				isOperator = false;
			}

			System.out.println("3. " + stk);

		}

		return result;

	}

	public static boolean isNum(String s) {
		try {

			Integer.parseInt(s);

		} catch (Exception e) {

			return false;
		}

		return true;
	}

}
