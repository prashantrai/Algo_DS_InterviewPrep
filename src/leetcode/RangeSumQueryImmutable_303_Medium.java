package leetcode;

public class RangeSumQueryImmutable_303_Medium {

	public static void main(String[] args) {

		int[] nums = {-2, 0, 3, -5, 2, -1};

		NumArray numArray = new NumArray(nums);
		numArray.sumRange(0, 2); //-> 1
		numArray.sumRange(2, 5); //-> -1
		numArray.sumRange(0, 5); //-> -3
		
	}

	/*
	 * https://leetcode.com/articles/range-sum-query-immutable/
	 * 
	 * Complexity analysis
	 * 
	 * Time complexity : O(1) time per query, O(n) time pre-computation.
	 * Since the cumulative sum is cached, each sumRange query can be calculated in
	 * O(1) time.
	 * 
	 * Space complexity : O(n)
	 */

	static class NumArray {
		private int[] sum;

		public NumArray(int[] nums) {
			sum = new int[nums.length + 1];
			for (int i = 0; i < nums.length; i++) {
				sum[i + 1] = sum[i] + nums[i];
			}
		}

		public int sumRange(int i, int j) {
			return sum[j + 1] - sum[i];
		}
	}

}
