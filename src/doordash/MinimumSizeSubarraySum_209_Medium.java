package doordash;

public class MinimumSizeSubarraySum_209_Medium {

	public static void main(String[] args) {
		int target = 7; 
		int[] nums = {2,3,1,2,4,3};
		System.out.println("Expected: 2, Actual: " + minSubArrayLen(target, nums));

	}
	
	// Time: O(n)
    // Space: O(1)
    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        
        int left = 0;
        int right = 0;
        int sum = 0;
        int res = Integer.MAX_VALUE;
        
        while(right < nums.length) {
            sum += nums[right];
            right++;
            while(sum >= target) {
                res = Math.min(res, right - left);
                sum -= nums[left];
                left++; // update the window size by moving left pointer
            }
            
        }
        return res == Integer.MAX_VALUE ? 0 : res;
        
    }

}
