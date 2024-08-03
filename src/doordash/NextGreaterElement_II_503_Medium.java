package doordash;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElement_II_503_Medium {

	public static void main(String[] args) {
		int[] nums = {1,2,1};
		int[] res = nextGreaterElements(nums);
		System.out.println("Expected: [2,-1,2] \nActual: " + Arrays.toString(res)); 
	}
	
	/*
    Refer: https://leetcode.com/problems/next-greater-element-ii/discuss/98270/JavaC%2B%2BPython-Loop-Twice
    Tips:
     - Loop once, we can get the Next Greater Number of a normal array.
     - Loop twice, we can get the Next Greater Number of a circular array
    
    Time: O(N)
    Space: O(N)
    */
    
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> stk = new Stack<>();
        
        for(int i=0; i<n*2; i++) {
            // why i%n? Because, we are traversing the array 2 times 
            // and this will keep us in bound
            while(!stk.isEmpty() && nums[stk.peek()] < nums[i%n]) 
               res[stk.pop()] = nums[i%n]; 
                
            stk.push(i%n);
        }
        return res;
    }

}
