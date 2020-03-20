package leetcode;

public class HouseRobber_198_Easy {

	public static void main(String[] args) {

		int[] nums = {1,2,3,1};
		int res = rob(nums);
		System.out.println(res);
	}

	/*
	 * https://leetcode.com/problems/house-robber/
	 * https://leetcode.com/problems/house-robber/submissions/
	 * https://leetcode.com/problems/house-robber/discuss/480575/Java-DP-100.-Comments-for-you-to-easy-understanding...-%3A)
	 * 
	 * */

	
	public static int rob(int[] nums) {
		 
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        
        int[] dp = new int[nums.length];
        
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], nums[0]);
        
        for(int i=2; i<nums.length; i++) {
            dp[i] = Math.max((nums[i]+dp[i-2]), dp[i-1]);
        }
        
        return dp[dp.length-1];
    }
	
}
