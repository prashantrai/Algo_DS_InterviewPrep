package TikTok;

import java.util.Stack;

public class ScoreOfParentheses_856_Medium {

	public static void main(String[] args) {

		String s1 = "()";
		System.out.println("Expected: 1, Actual: " + scoreOfParentheses(s1) 
		+ ", scoreOfParentheses2: " + scoreOfParentheses2(s1));

		String s2 = "(())";
		System.out.println("Expected: 2, Actual: " + scoreOfParentheses(s2) 
		+ ", scoreOfParentheses2: " + scoreOfParentheses2(s2));

		String s3 = "()()";
		System.out.println("Expected: 2, Actual: " + scoreOfParentheses(s3) 
		+ ", scoreOfParentheses2: " + scoreOfParentheses2(s3));
		
		
	}

	
	/*** With constant space***
    
    Refer (3rd solution): https://leetcode.com/problems/score-of-parentheses/discuss/1856520/Java-Easy-to-understand-or-Intuition-and-solution-or-O(n)-greater-O(1)
    
    Algo: As the string is balanced, a '(' bracket could be used to mark the start of a new 
    depth (i.e. 2 times the depth) & a ')' bracket would indicate us the end of a valid sub-
    portion of paranthesis. Hence,

    We need to update the depth of a paranthesis.
    If ( - our depth increases
    If ) - our depth decreases
    If at any point, we see a valid balanced pair (), then we need to update the output acc 
    to Rule #3 i.e. (A) has score 2 * A, where A is a balanced parentheses string.
    
    For eg:
    str =    (   (   (   (   )  (   )  (   )   )  )  )
    depth =  1   2   3   4   3  4   3  4   3   2  1  0
    R =                      8      8      8

    Final result = 8 + 8 + 8 = 24

    Why are we decrementing when we find a `)` ? It is because the intermediate `()` lies at 
    d-1 depth. 
    eg: (   (   (  **(   )** - this valid pair lies at depth 3.
    
    
    Time: O(N)
    Space: O(1)
    */
    
    public static int scoreOfParentheses(String s) {
        int n = s.length();
        int depth = 0;
        int res = 0;
        
        for(int i=0; i<n; i++) {
            if(s.charAt(i) == '(') { // if `(` increase the depth
                depth++;
            } else { // if `)` decrease the depth 
                depth--;
                
                // check if a balanced pair is getting formed, if yes update the result
                // i.e. if prev index has '(' then update the result
                if(s.charAt(i-1) == '(') {
                    res += Math.pow(2, depth);
                }
            }
        }
        return res;
    }
    
    
    /*** With O(N) space***
    Refer: https://leetcode.com/problems/score-of-parentheses/discuss/1856519/JavaC%2B%2B-Visually-Explained!!
    
    Time: O(N)
    Space: O(N)
    */
    public static int scoreOfParentheses2(String s) {
        Stack<Integer> stk = new Stack<>();
        int score = 0;
        
        for(char c : s.toCharArray()) {
            if(c == '(') {
                stk.push(score);
                score = 0; //reset
            } else  {
                score = stk.pop() + Math.max(2*score, 1);
            }
        }
        return score;
    }
	
}
