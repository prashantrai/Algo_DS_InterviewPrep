import java.util.Stack;

public class Attentive_Onsite {
	
	
	/* Phone Screen: LC 692
	 * 
	 * On-site:
	 * Sys Design: 
	 * 	1. Design Online Catalog System where a user can upload a catalog of products 
	 * 		and also should be able to search products.
	 * 	2. Design a photo upload service. (Follow instagram design only for photo i.e. with followers and news feed generation) 
	 *  
	 * Coding: L
	 * 	3. Lru Cache
	 * 	4. Evaluate Expr (below question)
	 * 
	 * Others: 
	 * Glassdoor: Phone: Determine if dictionary acronyms exist for a list of a strings.
	 * */
	

	/**
	 * Build an expression parser. The format of an individual statement will be: “( CMD VAL VAL )”
	 * 
	 * VAL can be an integer or another statement.
	 * 
	 * You can assume valid inputs for this entire exercise. If you need to, clarify
	 * what you assume are valid inputs.
	 * 
	 * The first two commands to implement are ADD and MULT, which add and multiply
	 * the values, respectively.
	 * 
	 * Example: parse("( ADD 3 4 )") == 7
	 * 
	 * parse("( MULT 3 ( ADD 3 4 ) )") == 21
	 * 
	 */

	public static void main(String[] args) {
		
		String expr = "( ADD 3 4 )";  
		System.out.println("Expected: 7, Actual: " + expressionParser(expr));
		
		expr = "( MULT 3 ( ADD 3 4 ) )";
		System.out.println("Expected: 21, Actual: " + expressionParser(expr));
		
		expr = "( MULT 2 ( MULT 3 ( ADD 3 4 ) ) )";
		System.out.println("Expected: 42, Actual: " + expressionParser(expr));
		
		expr = "( ADD 2 ( MULT 3 ( ADD 3 4 ) ) )";
		System.out.println("Expected: 23, Actual: " + expressionParser(expr));
		
	}

	//	working solution
	/* 	Time: O(N) Space: O(N) + O(N)=> O(N)
		Algo: 
		1. split by whitespace
		2. iterate array and add into stack and push till ')' is not seen
	*/
	private static int expressionParser(String exp) {
		// split by whitespace
		String[] arr = exp.split(" ");
		// iterate array and add into stack and push till ')' is not seen
		Stack<String> stk = new Stack<>();
		int res = 0;
		for (String s : arr) {
			if (!s.equals(")")) {
				stk.push(s);
				continue;
			}
			Stack<String> temp = new Stack<>(); // O(1)
			while (!stk.isEmpty() && !stk.peek().equals("(") ) {
				temp.push(stk.pop());
				//if (!stk.isEmpty() && (stk.peek().equals("ADD") || stk.peek().equals("MULT")))
				//	break;
			}

			//if (!stk.isEmpty() && "ADD".equals(stk.peek())) { // this works too
			if (!temp.isEmpty() && "ADD".equals(temp.peek())) {
				temp.pop(); //pop ADD from temp
				int n1 = Integer.parseInt(temp.pop());
				int n2 = Integer.parseInt(temp.pop());
				res = n2 + n1;
				//stk.pop();  // pop ADD  -- this wroks too
// 			} else if (!stk.isEmpty() && "MULT".equals(stk.peek())) { // this works too, if you un-commenting this then uncomment IF in while as well
 			} else if (!temp.isEmpty() && "MULT".equals(temp.peek())) {
 				temp.pop(); //pop MULT from temp
				int n1 = Integer.parseInt(temp.pop());
				int n2 = Integer.parseInt(temp.pop());
				res = n1 * n2;
				// stk.pop();  // pop MULTI -- this works too
			}
			
			if (!stk.isEmpty()) stk.pop(); // this will pop "("
			stk.push(""+res); // add the new value in stack
		}

		return res;
	}

}
