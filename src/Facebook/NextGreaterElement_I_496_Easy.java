package Facebook;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NextGreaterElement_I_496_Easy {

	public static void main(String[] args) {

		int[] nums1 = {4,1,2}; 
		int[] nums2 = {1,3,4,2};

		int[] res = nextGreaterElement(nums1, nums2);
		System.out.println("Expected: [-1, 3, -1], Actual: " + Arrays.toString(res));
	}

	/*
    Iterate through the nums2 (bigger array) and create map of values
    where key is smaller num and value is the next big num. Use stack
    to achieve this.
    
    Time: O(n)
    Space: O(n)
    
    */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int i=0; i<nums2.length; i++) {
            while(!stack.isEmpty() && nums2[i] > stack.peek()) {
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]); // push the num in stack
        }
        
        // for the element there is no bigger num, add them in 
        // map with value as -1
        while(!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }
        
        // build result array
        int[] res = new int[nums1.length];
        for(int i=0; i<nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        
        return res;
    }
	
}
