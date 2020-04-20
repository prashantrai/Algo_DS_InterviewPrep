package leetcode;

public class MaximumSumOfTwoNonOverlappingSubarrays {

	// https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/discuss/355352/Full-Explanation-and-idea-formation.-JAVA-beat-99

	//https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/submissions/
	public static void main(String[] args) {

		int[] arr = { 0, 6, 5, 2, 2, 5, 1, 9, 4 };
		int[] LpreSum = new int[arr.length];

		int L = 1, M = 2;

		/*for (int i = 1; i < arr.length; i++) {
			LpreSum[i] = Math.max(LpreSum[i - 1], LpreSum[i] - LpreSum[i - L]);

		}

		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(LpreSum));*/
		
		int res = maxSumTwoNoOverlap(arr, L, M);
		
		System.out.println(res);

	}

	public static int maxSumTwoNoOverlap(int[] nums, int L, int M) {

		if (null == nums || nums.length == 0 || nums.length < L + M)
			return -1;

		return maxSum(nums, L, M);

	}

	private static int maxSum(int[] nums, int L, int M) {

		for (int i = 1; i < nums.length; ++i)
			nums[i] += nums[i - 1];

		int res = nums[L + M - 1], Lmax = nums[L - 1], Mmax = nums[M - 1];

		for (int i = L + M; i < nums.length; ++i) {

			// Lmax is the case when L contiguous elements are taken first
			Lmax = Math.max(Lmax, nums[i - M] - nums[i - L - M]);

			// Mmax is the case when M contiguous elements are taken first
			Mmax = Math.max(Mmax, nums[i - L] - nums[i - L - M]);
			int x = Lmax + nums[i] - nums[i - M];
			int y = Mmax + nums[i] - nums[i - L];

			res = Math.max(res, Math.max(x, y));
		}
		return res;
	}

}
