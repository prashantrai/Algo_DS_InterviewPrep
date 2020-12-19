package Oracle;

import java.util.Arrays;
import java.util.Random;

public class ShuffleAnArray_384_Medium {

	public static void main(String[] args) {
		int[] nums = {1, 2, 3};
		
		Solution solution = new Solution(nums);
		System.out.println(Arrays.toString(solution.shuffle()));    // Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must be equally likely to be returned. Example: return [3, 1, 2]
		System.out.println(Arrays.toString(solution.reset()));      // Resets the array back to its original configuration [1,2,3]. Return [1, 2, 3]
		System.out.println(Arrays.toString(solution.shuffle()));    // Returns the random shuffling of array [1,2,3]. Example: return [1, 3, 2]
	}
	
	// Time and space : O(n)
	// https://leetcode.com/problems/shuffle-an-array/
	// Fisher-Yates Algorithm
	// https://leetcode.com/problems/shuffle-an-array/discuss/85958/First-Accepted-Solution-Java
	private static class Solution {

	    private int[] nums;
	    private Random random;
	    
	    public Solution(int[] nums) {
	        this.nums = nums;
	        random = new Random();
	    }
	    
	    /** Resets the array to its original configuration and return it. */
	    public int[] reset() {
	        return nums;
	    }
	    
	    /** Returns a random shuffling of the array. */
	    public int[] shuffle() {
	        int[] a = nums.clone();
	        
	        for(int i=1; i<a.length; i++) {
	            int j = random.nextInt(i+1); // Random().nextInt(int bound) generates a random integer from 0 (inclusive) to bound (exclusive)
	            swap(a, i, j);
	        }
	        return a;
	    }
	    
	    private void swap(int[] a, int i, int j) {
	        int temp = a[i];
	        a[i] = a[j];
	        a[j] = temp;
	    }
	}
	
}
