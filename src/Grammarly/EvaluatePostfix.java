package Grammarly;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class EvaluatePostfix {

	public static void main(String[] args) {
//		Set<String> accessCodes = Set.<String>of("XYZ", "ABC", "LMN", "TUV");
		Set<String> accessCodes = new HashSet<String>(Arrays.asList("XYZ", "ABC", "LMN", "TUV"));
		String postfixExpression = "XYZ ABC AND XNN OR LMN NOT AND";
		boolean result = evaluatePostfix(postfixExpression, accessCodes);
		System.out.println("Result: " + result);

	}
	
	/*
	 * Question:
		Given a set of access codes and a boolean postfix expression, write a Java program to 
		evaluate the expression. The access codes determine whether specific elements in the 
		postfix expression are true or false. The postfix expression consists of access codes 
		and boolean operators: AND, OR, and NOT.

		Example Access Codes:
					Set<String> accessCodes = Set.of("XYZ", "ABC", "LMN", "TUV");

		Example Postfix Expression: "XYZ ABC AND XNN OR LMN NOT AND"
	 */
	
	/*
	 * Time Complexity: O(n)
	 * Space Complexity: O(n)
	 */

	public static boolean evaluatePostfix(String expression, Set<String> accessCodes) {
		Stack<Boolean> stack = new Stack<>();
		String[] tokens = expression.split(" ");

		for (String token : tokens) {
			switch (token) {
			case "AND":
				boolean operand2 = stack.pop();
				boolean operand1 = stack.pop();
				stack.push(operand1 && operand2);
				break;
			case "OR":
				operand2 = stack.pop();
				operand1 = stack.pop();
				stack.push(operand1 || operand2);
				break;
			case "NOT":
				operand1 = stack.pop();
				stack.push(!operand1);
				break;
			default:
				stack.push(accessCodes.contains(token));
				break;
			}
		}

		return stack.pop();
	}
}
