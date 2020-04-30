package leetcode;

public class MissingElementInSortedArray_1060_Medium {

	public static void main(String[] args) {

		int[] nums = { 4, 7, 9, 10 };
		int k = 1;

		int res = missingElement(nums, k);
		System.out.println(res);

	}

	public static int missingElement(int[] A, int k) {
		int left = 1, right = A.length;
		int target = A[0] + k;

		while (left < right) {
			int mid = left + (right - left) / 2;
			if (A[mid] <= target + mid - 1) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return target + left - 1;
	}

	// --Leetcode article - solution

	// Return how many numbers are missing until nums[idx]
	int missing(int idx, int[] nums) {
		return nums[idx] - nums[0] - idx;
	}

	public int missingElement2(int[] nums, int k) {
		int n = nums.length;
		// If kth missing number is larger than
		// the last element of the array
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

//https://leetcode.com/problems/missing-element-in-sorted-array/discuss/303444/Java-O(logN)-solution-Binary-Search
//https://leetcode.com/problems/missing-element-in-sorted-array/discuss/536752/java-binary-search-beats-100100-with-clear-explanation
//https://leetcode.com/problems/missing-element-in-sorted-array/discuss/454192/Java-Binary-Search-Solution-Explained

//--Look in the comment:: https://leetcode.com/problems/missing-element-in-sorted-array/discuss/348481/Java-Concise-Binary-Search

/*
 * Given a sorted array A of unique numbers, find the K-th missing number
 * starting from the leftmost number of the array.
 * 
 * Example 1:
 * 
 * Input: A = [4,7,9,10], K = 1 Output: 5 Explanation: The first missing number
 * is 5. 
 * 
 * Example 2:
 * 
 * Input: A = [4,7,9,10], K = 3 Output: 8 Explanation: The missing numbers are
 * [5,6,8,...], hence the third missing number is 8. 
 * 
 * Example 3:
 * 
 * Input: A = [1,2,4], K = 3 Output: 6 Explanation: The missing numbers are
 * [3,5,6,7,...], hence the third missing number is 6.
 */
