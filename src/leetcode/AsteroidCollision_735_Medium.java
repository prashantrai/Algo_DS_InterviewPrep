package leetcode;

import java.util.Arrays;
import java.util.Stack;

public class AsteroidCollision_735_Medium {

	public static void main(String[] args) {
		
		int[] asteroids = {5,10,-5};
		
		int[] res = asteroidCollision(asteroids);
		
		
		System.out.println("Expected: [5,10], Actual: "+Arrays.toString(res));

	}

	//--https://leetcode.com/problems/asteroid-collision/
	//--https://leetcode.com/problems/asteroid-collision/submissions/
	//--https://leetcode.com/problems/asteroid-collision/discuss/498932/Simple-JAVA-solution-with-detailed-explanation
	//--https://leetcode.com/problems/asteroid-collision/solution/
	
	//--https://www.youtube.com/watch?v=6GGTBM7mwfs
	
	public static int[] asteroidCollision(int[] asteroids) {
        
        Stack<Integer> stk = new Stack<>();
        
        for(int ast : asteroids) {
            collision : {
                while(!stk.isEmpty() && stk.peek() > 0 && ast < 0) {
                    if(stk.peek() < Math.abs(ast)) {
                        stk.pop();
                        continue;
                    } 
                    else if(stk.peek() == Math.abs(ast)) {
                        stk.pop();
                    }
                    break collision;
                }
                stk.push(ast);
            }
            
        }
        
        int[] res = new int[stk.size()];
        for(int i=res.length-1; i >= 0; i--) {
            res[i] = stk.pop();
        }
        return res;
    }
	
	
	//--https://leetcode.com/problems/asteroid-collision/solution/
	public int[] asteroidCollision2(int[] asteroids) {
        Stack<Integer> stack = new Stack();
        for (int ast: asteroids) {
            collision: {
                while (!stack.isEmpty() && ast < 0 && 0 < stack.peek()) {
                    if (stack.peek() < -ast) {
                        stack.pop();
                        continue;
                    } else if (stack.peek() == -ast) {
                        stack.pop();
                    }
                    break collision;
                }
                stack.push(ast);
            }
        }

        int[] ans = new int[stack.size()];
        for (int t = ans.length - 1; t >= 0; --t) {
            ans[t] = stack.pop();
        }
        return ans;
    }
}
