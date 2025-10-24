package Ripple;

public class MaximumSubarray_53_Easy {

	public static void main(String[] args) {
		int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
		int[] nums2 = {5,4,-1,7,8};

		System.out.println("Expected: 6, Actual:"+maxSubArray(nums));
		System.out.println("Expected: 23, Actual:"+maxSubArray(nums2));
		
		System.out.println("Expected: 6, Actual:"+maxSubArray2(nums));
		System.out.println("Expected: 23, Actual:"+maxSubArray2(nums2));
	}

	// Kadane's Algorithm
    public static int maxSubArray(int[] nums) {
        int sum = nums[0];
        int max = sum;
        for(int i=1; i<nums.length; i++) {
            sum = Math.max(nums[i], sum+nums[i]);
            max = Math.max(max, sum);
        }
        return max;
    }
    
    public static int maxSubArray2(int[] nums) {
        int sum = nums[0];
        int max = sum;
        for(int i=1; i<nums.length; i++) {
            
            // can be replaced with sum = Math.max(nums[i], sum+nums[i])
            if(sum < 0)
                sum = nums[i];
            else 
                sum += nums[i];
            
            max = Math.max(max, sum);
        }
        
        return max;
    }
	
}
