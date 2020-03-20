package leetcode;

public class HouseRobberII_213_Medium {

	public static void main(String[] args) {
		int[] nums = {1,2,3,1};
		int res = rob(nums);
		System.out.println(res);
	}
	
	
	/*
	 * https://leetcode.com/problems/house-robber-ii/
	 * https://leetcode.com/problems/house-robber-ii/submissions/
	 * https://leetcode.com/problems/house-robber-ii/discuss/480611/Java-100-DP-with-comments
	 * */

	public static int rob(int[] nums) {
        
        if(nums == null || nums.length == 0) {
            return 0;
        }
        
        if(nums.length == 1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0], nums[1]);
        
        /*
         * If robbing first house then can't rob the last as they are neighbors
         * If robbing the second house then can rob the last
         * Compare result form both the cases and return the max         
        */
        
        return Math.max(helper(nums, 0, nums.length-2),
                       helper(nums, 1, nums.length-1));
    }
	
    
    public static int helper(int[] nums, int l, int r) {
        
    	
    	// for the size of dp: if nums.length = 5, then when it's 0-3 (rob last house)
        // dp size should be 3 + 1 = 4, r = 3
        // when it's 1-4; dp size is 4 + 1 = 5 because 1-4 are the indexs to nums
        // hence in dp we need to keep in consistent with nums' index
        // in other words, when we rob house 0, dp actually starts filling value with 
        // its index 1, nothing in index 0 or it's initialized as 0, but we dont worry
    	
        int[] dp = new int[r+1];
        
        dp[l] = nums[l];
        dp[l+1] = Math.max(nums[l+1], dp[l]);
        
        for(int i=l+2; i<=r; i++) {
            dp[i] = Math.max(nums[i]+dp[i-2], dp[i-1]);
        }
        return dp[dp.length - 1];
    }
}
