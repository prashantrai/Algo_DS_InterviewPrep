package Expedia;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MaximumWidthRamp_962_Medium {

    public static void main(String[] args) {
        // Given examples
        runTest(new int[]{6, 0, 8, 2, 1, 5}, 4);
        runTest(new int[]{9, 8, 1, 0, 1, 9, 4, 0, 4, 1}, 7);

        // Edge cases
        runTest(new int[]{1, 1}, 1);          // smallest valid equal pair
        runTest(new int[]{2, 1}, 0);          // no ramp
        runTest(new int[]{1, 2}, 1);          // direct ramp

        // All equal
        runTest(new int[]{5, 5, 5, 5}, 3);

        // Strictly increasing
        runTest(new int[]{1, 2, 3, 4, 5}, 4);

        // Strictly decreasing
        runTest(new int[]{5, 4, 3, 2, 1}, 0);

        // Duplicates and mixed values
        runTest(new int[]{3, 3, 3, 1, 2, 2, 4}, 6);
        runTest(new int[]{4, 1, 2, 3, 1, 5}, 5);

        // More complex
        runTest(new int[]{8, 1, 0, 2, 1, 5, 7, 3, 6}, 7);
        runTest(new int[]{9, 7, 5, 3, 2, 8, 10, 1, 9}, 8);
    }
    // Simple test helper
    private static void runTest(int[] nums, int expected) {
        int actual = maxWidthRamp(nums);
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("Expected = " + expected + ", Actual = " + actual
                + (expected == actual ? "  ✅" : "  ❌"));
        System.out.println();
    }
    
	
	/* Step-by-step algorithm
    - Create a stack of candidate left indices.
    - Traverse from left to right:
        - Push index i only if nums[i] is smaller than the value at the current stack top.
    - Traverse from right to left:
        - While stack is not empty and nums[stackTop] <= nums[j]:
            - update answer with j - stackTop
            - pop the stack
    - Return the maximum width.
	*/
	/* Time and space complexity
	    Time: O(n), Each index is pushed at most once and popped at most once
	    Space: O(n), For the stack
	*/
	private static int maxWidthRamp(int[] nums) {
	    int n = nums.length;
	    Deque<Integer> stk = new ArrayDeque<>();
	
	    // Step 1: Build a decreasing stack of candidate left indices.
	    // We keep an index only if its value is smaller than all previous values.
	    for(int i=0; i<n; i++) {
	        if(stk.isEmpty() || nums[i] < nums[stk.peek()]) {
	            stk.push(i);
	        }
	    }
	
	    int maxWidthRamp = 0;
	    // Step 2: Scan from right to left.
	    // If nums[left] <= nums[right], we found a valid ramp.
	    // Since right is moving from the far end, this is the best width
	    // for that left index, so we can pop it.  
	    for(int i=n-1; i>=0; i--) {
	        // take the value/idx from stack and compare the array value
	        // at that index with current i value in arr
	        // if current value is greater found a ramp, calculate width
	        // by substracting the indexes
	        while(!stk.isEmpty() && nums[stk.peek()] <= nums[i]) {
	            int curWidth = i - stk.pop();
	            maxWidthRamp = Math.max(maxWidthRamp, curWidth);
	        }
	    }
	
	    return maxWidthRamp;
	
	}

}
