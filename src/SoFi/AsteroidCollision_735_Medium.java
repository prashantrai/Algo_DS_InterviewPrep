package SoFi;

import java.util.Arrays;
import java.util.Stack;

public class AsteroidCollision_735_Medium {

	public static void main(String[] args) {
		
		int[] asteroids = {5,10,-5};
		int[] res = asteroidCollision(asteroids);
		System.out.println("1. Expected: [5,10], Actual: "+Arrays.toString(res));

		int[] asteroids2 = {-2, -1, 1, 2};
		res = asteroidCollision(asteroids2);
		System.out.println("2. Expected: [-2, -1, 1, 2], Actual: "+Arrays.toString(res));
		
		int[] asteroids3 = {8, -8};
		res = asteroidCollision(asteroids3);
		System.out.println("3. Expected: [], Actual: "+Arrays.toString(res));
		
		int[] asteroids4 = {1, -5, 7, -3};
		res = asteroidCollision(asteroids4);
		System.out.println("3. Expected: [], Actual: "+Arrays.toString(res));
		
	}

	// Time and Space: O(N)
    public static int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for(int a : asteroids) {
            boolean destroyed = false;

            // Only possible collision: stack top > 0 and current a < 0
            while (!stack.isEmpty() && a < 0 && stack.peek() > 0) {
                int top = stack.peek();
                if(Math.abs(top) < Math.abs(a)) {
                    // Top asteroid is smaller, it explodes
                    stack.pop();
                }
                else if(Math.abs(top) == Math.abs(a)) {
                    // Both explode
                    // pop and break while to get next asteroid
                    stack.pop();
                    destroyed = true;
                    break; 
                }
                else {
                    // Incoming asteroid is smaller than peek element
                    // it will explode
                    destroyed = true;
                    break;
                }
            }

            if (!destroyed) {
                stack.push(a);
            }
        }

        int[] result = new int[stack.size()];
        for(int i=result.length-1; i>=0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}
