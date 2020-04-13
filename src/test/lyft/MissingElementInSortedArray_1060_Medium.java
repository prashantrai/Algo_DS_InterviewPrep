package test.lyft;

public class MissingElementInSortedArray_1060_Medium {

	// https://leetcode.com/problems/missing-element-in-sorted-array/
	public static void main(String[] args) {
		//int[] nums = { 4, 7, 9, 10 };
		//int k = 3;
		
		int[] nums = {1,2,4};
		int k = 3;

		// int res = missingElement(nums, k);
		int res = missingElementBinarySearch(nums, k);
		System.out.println(res);
	}

	public static int missingElement(int[] nums, int k) {
		int n = nums.length;
		// If kth missing number is larger than
		// the last element of the array
		if (k > missing(n - 1, nums))
			return nums[n - 1] + k - missing(n - 1, nums);

		int idx = 1;
		// find idx such that
		// missing(idx - 1) < k <= missing(idx)
		while (missing(idx, nums) < k)
			idx++;

		// kth missing number is greater than nums[idx - 1]
		// and less than nums[idx]
		return nums[idx - 1] + k - missing(idx - 1, nums);
	}
	
	public static int missing(int idx, int[] nums) {
		int m = nums[idx] - nums[0] - idx;
		return m;
	}

	/*
	 * Algorithm
	 * 
	 * Implement missing(idx) function that returns how many numbers are missing
	 * until array element with index idx. Function returns 
	 * 		nums[idx] - nums[0] - idx
	 * 
	 * Find an index such that missing(idx - 1) < k <= missing(idx) by a binary
	 * search.
	 * 
	 * Return kth smallest 
	 * 			nums[idx - 1] + k - missing(idx - 1).
	 */

	// --using binary search
	public static int missingElementBinarySearch(int[] nums, int k) {
		int n = nums.length;
		// If kth missing number is larger than the last element of the array
		if (k > missing(n - 1, nums))
			return nums[n - 1] + k - missing(n - 1, nums);

		int left = 0, right = n - 1, pivot;
		// find left = right index such that
		// missing(left - 1) < k <= missing(left)
		while (left != right) {
			pivot = left + (right - left) / 2;

			if (missing(pivot, nums) < k)
				left = pivot + 1;
			else
				right = pivot;
		}

		// kth missing number is greater than nums[idx - 1]
		// and less than nums[idx]
		return nums[left - 1] + k - missing(left - 1, nums);
	}

}
