package Apple;

public class MaximumSubarray_53_Easy {

	public static void main(String[] args) {
		int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
		int[] nums2 = {5,4,-1,7,8};

		System.out.println("Expected: 6, Actual:"+maxSubArray(nums));
		System.out.println("Expected: 23, Actual:"+maxSubArray(nums2));
		
		System.out.println("Expected: 6, Actual:"+maxSubArray2(nums));
		System.out.println("Expected: 23, Actual:"+maxSubArray2(nums2));
	}

	// Time: O(n), Space: O(1)
	// Kadane's Algorithm
    public static int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int max = currentSum;
        for(int i=1; i<nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            max = Math.max(max, currentSum);
        }
        return max;
    }
    
    /** Not as efficient as above */
    // Divide and Conquer 
    // Time: O(n log n), Space: O(log n) for recursion stack
    public static int maxSubArray2(int[] nums) {
        return maxSubArray(nums, 0, nums.length - 1);
    }

    private static int maxSubArray(int[] nums, int left, int right) {
        // Base case: only one element
        if(left == right) {
            return nums[left];
        }

        int mid = left + (right - left) / 2;

        // 1) Max subarray entirely in left half
        int leftMax = maxSubArray(nums, left, mid);
        // 2) Max subarray entirely in right half
        int rightMax = maxSubArray(nums, mid + 1, right);
        // 3) Max subarray crossing the middle
        int corossMax = maxCrossingSum(nums, left, mid, right);

        return Math.max(Math.max(leftMax, rightMax), corossMax);
    }

    private static int maxCrossingSum(int[] nums, int left, int mid, int right) {
        // Best sum ending at or before mid (left side)
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;

        for(int i=mid; i>=left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }

        // Best sum starting just after mid (right side)
        int rightSum = Integer.MIN_VALUE;
        sum = 0;

        for(int i=mid+1; i<= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        // Best crossing subarray = best left suffix + best right prefix
        return leftSum + rightSum;
    }
	
}
