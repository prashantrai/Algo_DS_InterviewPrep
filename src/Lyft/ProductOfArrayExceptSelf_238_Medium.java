package Lyft;

import java.util.Arrays;

public class ProductOfArrayExceptSelf_238_Medium {

	public static void main(String[] args) {

		int[] arr = { 1, 2, 3, 4 };
		multiplyExceptSelf(arr);

	}

	// --Lyft
	// --https://leetcode.com/problems/product-of-array-except-self/

	/*
	 * Left iteration: 1. Take a value prod and inititalise with 1. 2. update the
	 * output array for the current index by prod * output[current]. remember output
	 * is an output array initilised with 1 3. Now recalculate the prod value by
	 * doint prod+arr[currIndex] for next iteration
	 * 
	 * Example: output: {1,1,1,1}
	 * 
	 * i=0 : {1,...} , prod = 1 i=1 : {1,1,..} , prod = 1*1 i=2 : {1,1,2,.} , prod =
	 * 1*2 i=3 : {1,1,2,6} , prod = 2*3
	 * 
	 * 
	 * Right Iteration: 1. Reset the prod to 1. 2. Start from end of the array and
	 * perform above steps from 2 to 3 in loop.
	 * 
	 * Exampel: Updated output: {1,1,2,6} i=3 : {...,6} , prod = 1 i=2 : {..8,6} ,
	 * prod = 1*4 i=1 : {.12,8,6} , prod = 4*3 i=0 : {24,12,8,6} , prod = 12*2
	 * 
	 * 
	 */

	/*
	 * Complexity analysis
	 * 
	 * Time complexity : O(N) where N represents the number of elements in the input
	 * array. We use one iteration to construct the array LL, one to update the
	 * array answer. 
	 * 
	 * Space complexity : O(1) since don't use any additional
	 * array for our computations. "The problem statement mentions that using the
	 * answer array doesn't add to the space complexity."
	 */

	public static void multiplyExceptSelf(int[] arr) {
		int n = arr.length;
		int prod = 1;
		int[] output = new int[arr.length];
		Arrays.fill(output, 1);

		for (int i = 0; i < n; i++) {
			output[i] = output[i] * prod;
			prod = prod * arr[i];
		}
		System.out.println(Arrays.toString(output));

		prod = 1;
		for (int i = n - 1; i >= 0; i--) {
			output[i] = output[i] * prod;
			prod = prod * arr[i];
		}

		System.out.println(Arrays.toString(output));
	}

}
