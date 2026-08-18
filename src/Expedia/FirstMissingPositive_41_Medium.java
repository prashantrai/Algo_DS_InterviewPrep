package Expedia;

import java.util.Arrays;

public class FirstMissingPositive_41_Medium {

	public static void main(String[] args) {

        runTest(new int[]{1, 2, 0}, 3);
        runTest(new int[]{3, 4, -1, 1}, 2);
        runTest(new int[]{7, 8, 9, 11, 12}, 1);

        // Edge cases
        runTest(new int[]{1}, 2);
        runTest(new int[]{2}, 1);
        runTest(new int[]{-1, -2, -3}, 1);
        runTest(new int[]{0, 0, 0}, 1);

        // Duplicates
        runTest(new int[]{1, 1}, 2);
        runTest(new int[]{2, 2, 2, 1}, 3);

        // Already arranged
        runTest(new int[]{1, 2, 3, 4, 5}, 6);

        // Mixed / complex
        runTest(new int[]{2, 3, 4, 5, 1}, 6);
        runTest(new int[]{5, 4, 3, 2, 1}, 6);
        runTest(new int[]{10, 1, 2, 3, 5}, 4);
        runTest(new int[]{2, -1, 3, 1, 5, 6}, 4);
    }

    private static void runTest(int[] input, int expected) {
        int[] arr = Arrays.copyOf(input, input.length); // preserve original input for printing
        int actual = firstMissingPositive(arr);

        System.out.println("Input: " + Arrays.toString(input));
        System.out.println("Output: " + actual);
        System.out.println("Expected: " + expected);
        System.out.println(actual == expected ? "PASS" : "FAIL");
        System.out.println("----------------------------------");
    }

	/* Interview Script: 
    “Since we need the first missing positive in O(n) time and O(1) extra space, 
    sorting is not allowed.
    The key observation is that the answer must lie in [1, n+1].
    So I try to place every value x in the range [1, n] at index x-1.
    I do this in-place using swaps, similar to cyclic sort.
    After placement, the first index i where nums[i] != i+1 gives the missing 
    positive i+1.
    If every position is correct, then the answer is n+1.”
	*/
	
	/* Step-by-step algorithm: 
	    - Let n = nums.length
	    - For each index i:
	        While nums[i] is in range 1..n
	        And nums[i] is not already in its correct place
	        Swap nums[i] with nums[nums[i] - 1]
	    - Then scan the array:
	        If nums[i] != i + 1, return i + 1
	    - If all positions are correct, return n + 1 
	*/

	// Time: O(n)
	// Space: O(1) 
	public static int firstMissingPositive(int[] nums) {
	    int n = nums.length;
	
	    // Place each number x at index x - 1, if possible
	    for(int i=0; i<n; i++) {
	        // Keep swapping until:
	        // 1) nums[i] is out of range, or
	        // 2) nums[i] is already in correct position, or
	        // 3) duplicate prevents further progress
	        while(nums[i] >= 1 && nums[i] <=n && nums[i] != nums[nums[i]-1]) {
	            swap(nums, i, nums[i]-1);
	        }
	    }
	    // First place where value is not i + 1 is the answer
	    for(int i=0; i<n; i++) {
	        if(nums[i] != i+1) {
	            return i+1;
	        }
	    }
	
	    // If all 1..n are present, answer is n + 1
	    return n+1;
	}
	
	// Helper method to swap two elements
	private static void swap(int[] nums, int i, int j) {
	    int temp = nums[i];
	    nums[i] = nums[j];
	    nums[j] = temp;
	}
	
}
