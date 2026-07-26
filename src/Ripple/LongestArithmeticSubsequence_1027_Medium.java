package Ripple;

import java.util.HashMap;

public class LongestArithmeticSubsequence_1027_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    Approach: 
    For every element, look at all previous elements.
    - Compute the difference.
    - If a sequence with the same difference already ends at the previous index, extend it.
    - Otherwise, start a new arithmetic sequence of length 2.
    -  Keep updating the global maximum.
    
    Why it works: 
    An arithmetic subsequence is completely determined by:
    - its last element
    - its common difference

    Therefore storing DP by (index, difference) captures all required state.


	 Time: O(n^2), We have a nested iteration over nums
	 Space: O(n^2), We create dp as memory where dp[right][diff] stores the length of the longest subsequence ending at index right and with a common difference of diff.
	 */
	public int longestArithSeqLength(int[] nums) {
	
	    if(nums.length < 2) return nums.length; 
	    
	    // minmum lenght of the nums should be > 2
	    int maxLen = 2; 
	
	    HashMap<Integer, Integer>[] dp = new HashMap[nums.length];
	
	    for(int right=0; right<nums.length; right++) {
	        dp[right] = new HashMap<>();
	        for(int left=0; left<right; left++) {
	            int diff = nums[left] - nums[right];
	            dp[right].put(diff, dp[left].getOrDefault(diff, 1) + 1);
	            maxLen = Math.max(maxLen, dp[right].get(diff));
	        }
	    }
	
	    return maxLen;
	    
	}

}
