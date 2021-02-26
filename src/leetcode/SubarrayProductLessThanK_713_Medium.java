package leetcode;

public class SubarrayProductLessThanK_713_Medium {

	public static void main(String[] args) {
		int[] nums = {10, 5, 2, 6}; 
		int k = 100;
		
		System.out.println("Expected: 8, Actual: " + numSubarrayProductLessThanK(nums, k));
	}

	
	/* https://leetcode.com/problems/subarray-product-less-than-k/
	 * 
	 * Time: O(N)
	 * Space: O(1)
	 */
	public static int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k <= 1) return 0;
        int prod = 1;
        int left = 0;
        int ans = 0;
        
        for (int right = 0; right < nums.length; right++) {
            prod *= nums[right];
            
            while(prod >= k) {
                prod /= nums[left];
                left++;
            }
            ans += right - left + 1;            
        }
        
        return ans;
    }
	
}
