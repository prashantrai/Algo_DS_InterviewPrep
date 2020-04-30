package leetcode;

import java.util.Arrays;

public class FindFirstAndLastPositionOfElementInSortedArray {

	public static void main(String[] args) {

		int[] nums = {5,7,7,8,8,10};
		int[] res = searchRange(nums, 8);
		System.out.println(Arrays.toString(res));
		
		res = searchRange_2(nums, 8);
		System.out.println(Arrays.toString(res));
		
	}

	// /https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/discuss/14699/Clean-iterative-solution-with-two-binary-searches-(with-explanation)
	// log N - bcause binary search
	public static int[] searchRange(int[] nums, int target) {

		int[] res = { -1, -1 };
		if (nums == null || nums.length == 0)
			return res;

		res[0] = getLeftIndex(nums, target);
		res[1] = getRightIndex(nums, target);

		return res;
	}
	
	/*
	 * Doing int mid = (lo + hi) / 2; is prone to overflow. 
	 * Instead int mid = lo + (hi - lo) / 2. 
	 * */

	private static int getLeftIndex(int[] nums, int target) {

		int left = 0;
		int right = nums.length - 1;

		while (left < right) {
			int mid = left + (right - left) / 2;
			if (target > nums[mid]) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}

		return nums[left] == target ? left : -1;

	}

	private static int getRightIndex(int[] nums, int target) {

		int left = 0;
		int right = nums.length - 1;

		while (left < right) {
			int mid = (left + (right - left) / 2) + 1;
			if (target < nums[mid]) {
				right = mid - 1;
			} else {
				left = mid;
			}
		}

		return nums[right] == target ? right : -1;

	}

	// From Article: https://leetcode.com/articles/find-first-and-last-position-element-sorted-array/
	public static int[] searchRange_2(int[] nums, int target) {
		int[] targetRange = { -1, -1 };

		int leftIdx = extremeInsertionIndex(nums, target, true);

		// assert that `leftIdx` is within the array bounds and that `target`
		// is actually in `nums`.
		if (leftIdx == nums.length || nums[leftIdx] != target) {
			return targetRange;
		}

		targetRange[0] = leftIdx;
		targetRange[1] = extremeInsertionIndex(nums, target, false) - 1;

		return targetRange;
	}
	
	
	// returns leftmost (or rightmost) index at which `target` should be
	// inserted in sorted array `nums` via binary search.
	private static int extremeInsertionIndex(int[] nums, int target, boolean left) {
		int lo = 0;
		int hi = nums.length;

		while (lo < hi) {
			int mid = (lo + hi) / 2;
			if (nums[mid] > target || (left && target == nums[mid])) {
				hi = mid;
			} else {
				lo = mid + 1;
			}
		}

		return lo;
	}

	

}

/*
 * Given an array of integers nums sorted in ascending order, find the starting
 * and ending position of a given target value.
 * 
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * 
 * If the target is not found in the array, return [-1, -1].
 * 
 * Example 1:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 8 Output: [3,4] Example 2:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 6 Output: [-1,-1]
 *
 */