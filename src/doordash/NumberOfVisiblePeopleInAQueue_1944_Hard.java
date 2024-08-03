package doordash;

import java.util.Arrays;
import java.util.Stack;

public class NumberOfVisiblePeopleInAQueue_1944_Hard {

	public static void main(String[] args) {
		int[] heights = {10,6,8,5,11,9};
		int[] res = canSeePersonsCount(heights);
		System.out.println("Expected: [3,1,2,1,1,0] \nActual: " + Arrays.toString(res));
		
		int[] heights2 = {5,1,2,3,10};
		int[] res2 = canSeePersonsCount(heights2);
		System.out.println("\nExpected: [4,1,1,1,0] \nActual: " + Arrays.toString(res2));
	}
	
	// Time: O(N)
    // Space: O(N)
    
    public static int[] canSeePersonsCount(int[] heights) {
        int[] res = new int[heights.length];
        Stack<Integer> stk = new Stack<>();
        
        for(int i=0; i<heights.length; i++) {
            // if top of the stack is <= to the current array element
            // means it can't see beyond that so increae the count
            // in result array for peek element and pop from the stack
            while(!stk.isEmpty() && heights[stk.peek()] <= heights[i]) 
                res[stk.pop()]++;
            
            // if peek element is greater than the current index element
            // means it can see beyond it, so, increase the count in result
            // array for the peek element
            if(!stk.isEmpty())
                res[stk.peek()]++;
            
            // push the current index to stack
            stk.push(i);
        }
        
        return res;
    }

}
