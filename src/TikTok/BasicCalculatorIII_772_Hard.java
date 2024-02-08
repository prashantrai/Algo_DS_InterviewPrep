package TikTok;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BasicCalculatorIII_772_Hard {

	public static void main(String[] args) {
		String s = "6-4/2"; // =4
		
		int res = calculate(s);
		System.out.println("Expected: 4  Actual: "+res);

		s = "1 + 1"; // = 2
		 res = calculate(s);
		 System.out.println("Expected: 2  Actual: "+res);

		s = "2*(5+5*2)/3+(6/2+8)"; // = 21
		res = calculate(s);
		System.out.println("Expected: 21  Actual: "+res);
		
		s = "2*(5-5*2)/3"; // = -3
		res = calculate(s);
		System.out.println("Expected: -3  Actual: "+res);
		
		s = "(1+(4+5+2)-3)+(6+8)"; // = 23
		res = calculate(s);
		System.out.println("Expected: 23  Actual: "+res);


	}
	
	
	//--WORKING Solution
	//https://leetcode.com/problems/basic-calculator-iii/discuss/202979/A-generic-solution-for-Basic-Calculator-I-II-III

	//--Working
	// --https://leetcode.com/problems/basic-calculator-iii/discuss/152092/O(n)-Java-Recursive-Simple-Solution
	// recursive
	
	// Time: O(N) 
	// Space: O(N) 
	public static int calculate(String s) {
		Queue<Character> tokens = new ArrayDeque<Character>();

		for (char c : s.toCharArray()) {
			if (c != ' ')
				tokens.offer(c);
		}

		tokens.offer('+');
		return calculate(tokens);
	}

	private static int calculate(Queue<Character> tokens) {

		char preOp = '+';
		int num = 0, sum = 0, prev = 0;

		while (!tokens.isEmpty()) {
			char c = tokens.poll();

			if ('0' <= c && c <= '9') {
				num = num * 10 + c - '0';
			} else if (c == '(') {
				num = calculate(tokens);
			} else {
				switch (preOp) {
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

				if (c == ')')
					break;

				preOp = c;
				num = 0;
			}
		}

		return sum + prev;
	}
	
	

	/*
	 * Question:: 772. Basic Calculator III ::
	 * 
	 * https://leetcode.com/problems/basic-calculator-iii/
	 * 
	 * 
	 * Implement a basic calculator to evaluate a simple expression string.
	 * 
	 * The expression string may contain open ( and closing parentheses ), the plus
	 * + or minus sign -, non-negative integers and empty spaces .
	 * 
	 * The expression string contains only non-negative integers, +, -, *, /
	 * operators , open ( and closing parentheses ) and empty spaces . The integer
	 * division should truncate toward zero.
	 * 
	 * You may assume that the given expression is always valid. All intermediate
	 * results will be in the range of [-2147483648, 2147483647].
	 * 
	 * Some examples:
	 * 
	 * "1 + 1" = 2 
	 * " 6-4 / 2 " = 4 
	 * "2*(5+5*2)/3+(6/2+8)" = 21
	 * "(2+6* 3+5- (3*14/7+2)*5)+3"=-12
	 * 
	 */

}
