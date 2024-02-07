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

		// int res = basicCalculatorIII(s);
		//int res = calculate(s);
		//System.out.println(res);

		s = "1 + 1"; // = 2
		// res = basicCalculatorIII(s);
		// res = calculate(s);
		// System.out.println(res);

		//s = "2*(5+5*2)/3+(6/2+8)"; // = 21
		//s = "2*(5-5*2)/3"; // = 10
		s = "(1+(4+5+2)-3)+(6+8)"; // = 23
		//res = basicCalculatorIII(s);
		int res = calculate(s);
		System.out.println(res);
//
//		s = "(2+6* 3+5- (3*14/7+2)*5)+3"; // =-12
//		res = basicCalculatorIII(s);
//		System.out.println(res);

//		s = "-1+4*3/3/3"; // = 0
//		int res = basicCalculatorIII(s);
//		System.out.println(res);
		
		//s = "-1+4*3/3/3"; // = 0
		//s = "2*(5-5*2)/3"; // = 10
		//s = "(1+(4+5+2)-3)+(6+8)"; // = 23
		s = "1+(4+5+2)-3"; // = 23
		//res = calculate_3(s);  //--wroking
		res = calculate_4(s);  //--wroking
		System.out.println(">>"+res);


	}
	
	//--https://leetcode.com/problems/basic-calculator-iii/discuss/202979/A-generic-solution-for-Basic-Calculator-I-II-III
	
	//from article - Working for lmited case only i.e. this problem as it doesn't take carre of '*' and '/'
	//--https://leetcode.com/articles/basic-calculator/
	public static int calculate_4(String s) {

        Stack<Integer> stack = new Stack<Integer>();
        int operand = 0;
        int result = 0; // For the on-going result
        int sign = 1;  // 1 means positive, -1 means negative

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {

                // Forming operand, since it could be more than one digit
                operand = 10 * operand + (int) (ch - '0');

            } else if (ch == '+') {

                // Evaluate the expression to the left,
                // with result, sign, operand
                result += sign * operand;

                // Save the recently encountered '+' sign
                sign = 1;

                // Reset operand
                operand = 0;

            } else if (ch == '-') {

                result += sign * operand;
                sign = -1;
                operand = 0;

            } else if (ch == '(') {

                // Push the result and sign on to the stack, for later
                // We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
                result = 0;

            } else if (ch == ')') {

                // Evaluate the expression to the left
                // with result, sign and operand
                result += sign * operand;

                // ')' marks end of expression within a set of parenthesis
                // Its result is multiplied with sign on top of stack
                // as stack.pop() is the sign before the parenthesis
                result *= stack.pop();

                // Then add to the next operand on the top.
                // as stack.pop() is the result calculated before this parenthesis
                // (operand on stack) + (sign on stack * (result from parenthesis))
                result += stack.pop();

                // Reset the operand
                operand = 0;
            }
        }
        return result + (sign * operand);
    }
	
	
	
	static Map<Character, Integer> precedence;
    
    public static int calculate_3(String s) {
        if (s == null || s.length() == 0) return 0;
        
        precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('(', 0); // !!
        
        return evalExpression(s);
    }
                       
    private static int evalExpression(String s) {
        // Assert s is valid  
        Stack<Character> ops = new Stack<>();
        Stack<Integer> values = new Stack<>();
        
        char[] tokens = s.toCharArray();
        char pre = ' ';
        for (int i = 0; i < tokens.length; i++) {
            char c = tokens[i];
            if (c == ' ') {
                continue;
            }
            
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                     num = 10*num + tokens[i++] - '0';
                }
                
                values.push(num);
                i--;
                
            } else if (c == '(') {
                ops.push(c);
                
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();  // pop out '('
                
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // Dealing with the negative number
                if (i == 0 && c == '-') {
                    values.push(0);
                } else if (pre == '(' && c == '-') {
                    values.push(0);
                }
                
                // While top of 'ops' has same or greater precedence to current 
                // token, which is an operator. Apply operator on top of 'ops' 
                // to top two elements in values stack 
                while (!ops.isEmpty() && precedence.get(c) <= precedence.get(ops.peek())) 
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                ops.push(c);
            }
            pre = c;
        }
        
        while (!ops.isEmpty()) 
             values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        
       return values.pop();
    }
                       
    private static int applyOp(Character op, int b, int a) { // b is before a (!!)
        switch(op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': {
                assert b != 0;
                return a / b;
            }
        }
        return Integer.MIN_VALUE;
    }	
	
	
	//--See this: 
	//https://leetcode.com/problems/basic-calculator-iii/discuss/202979/A-generic-solution-for-Basic-Calculator-I-II-III

	//--Working
	// --https://leetcode.com/problems/basic-calculator-iii/discuss/152092/O(n)-Java-Recursive-Simple-Solution
	// recursive
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

	//--Working
	// --https://leetcode.com/problems/basic-calculator-iii/discuss/113592/Development-of-a-generic-solution-for-the-series-of-the-calculator-problems
	public static int basicCalculatorIII(String s) {
		int l1 = 0, o1 = 1;
		int l2 = 1, o2 = 1;

		Deque<Integer> stack = new ArrayDeque<>(); // stack to simulate recursion

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (Character.isDigit(c)) {
				int num = c - '0';

				// --for 2 or more digit nums
				while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
					num = num * 10 + (s.charAt(++i) - '0');
				}

				l2 = (o2 == 1 ? l2 * num : l2 / num);

			} else if (c == '(') {
				// First preserve current calculation status
				stack.offerFirst(l1);
				stack.offerFirst(o1);
				stack.offerFirst(l2);
				stack.offerFirst(o2);

				// Then reset it for next calculation
				l1 = 0;
				o1 = 1;
				l2 = 1;
				o2 = 1;

			} else if (c == ')') {
				// First preserve the result of current calculation
				int num = l1 + o1 * l2;

				// Then restore previous calculation status
				// poll in the same sequence as it has been pushed - of-course it's a stack
				o2 = stack.poll();
				l2 = stack.poll();
				o1 = stack.poll();
				l1 = stack.poll();

				// Previous calculation status is now in effect
				l2 = (o2 == 1 ? l2 * num : l2 / num);

			} else if (c == '*' || c == '/') {
				o2 = (c == '*' ? 1 : -1);

			} else if (c == '+' || c == '-') {
				if (c == '-' && (i == 0 || s.charAt(i - 1) == '(')) {
					o1 = -1;
					continue;
				}

				l1 = l1 + o1 * l2;
				o1 = (c == '+' ? 1 : -1);

				l2 = 1;
				o2 = 1;
			}
		}

		return (l1 + o1 * l2);
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
