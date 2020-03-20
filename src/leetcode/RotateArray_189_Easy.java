package leetcode;

import java.util.Arrays;

public class RotateArray_189_Easy {

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4, 5, 6, 7 };
		int k = 10;
		rotate(a, k);
	}

	/*
	 * https://leetcode.com/problems/rotate-array/solution/
	 * Input: [1,2,3,4,5,6,7] and k = 3 Output: [5,6,7,1,2,3,4]
	 * 
	 * Original List : 1 2 3 4 5 6 7 
	 * 
	 * After reversing all numbers : 7 6 5 4 3 2 1
	 * After reversing first k numbers : 5 6 7 4 3 2 1 
	 * After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
	 * 
	 */
	public static void rotate(int[] a, int k) {
		k = k % a.length; //--to handle the case when k > arr.length-1

		reverse(a, 0, a.length-1); 
		reverse(a, 0, k-1);
		reverse(a, k, a.length-1);
	}

	private static void reverse(int[] a, int start, int end) {
		while (start < end) {
			int temp = a[start];
			a[start] = a[end];
			a[end] = temp;
			start++;
			end--;
		}
		System.out.println(Arrays.toString(a));
	}

}
