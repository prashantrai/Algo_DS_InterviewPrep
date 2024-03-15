package Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum_15_Medium {

	public static void main(String[] args) {

		int[] nums = {-1,0,1,2,-1,-4};
		System.out.println("Expected: Output: [[-1,-1,2],[-1,0,1]], Actual: " + threeSum(nums));
	}

	/*
	 * https://leetcode.com/problems/3sum/
	 * 
	 * For Explanation: https://www.youtube.com/watch?v=Ca7k53qcTic&ab_channel=KnowledgeCenter
	 * 
    Time : O(N log N) + O(N^2) i.e. O(n^2)
    Space: O(N * 3) i.e. O(N); why 3? because it's 3 sum and length/size of internal list will be 3 always
    */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums.length < 3) return res;
        Arrays.sort(nums);
        
        for(int i=0; i<nums.length-2; i++) {
            if(i == 0 || nums[i] != nums[i-1]) {
                int j = i+1, k = nums.length-1;
                while(j < k) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if(sum == 0) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        // if dups
                        while(j < k && nums[j] == nums[j+1]) j++;
                        while(j < k && nums[k] == nums[k-1]) k--;
                        j++;
                        k--;
                    }
                    else if(sum > 0) k--;
                    else j++;
                }
            }
        }
        return res;
    }
	
}