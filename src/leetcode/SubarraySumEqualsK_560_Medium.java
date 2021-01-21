package leetcode;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK_560_Medium {

	public static void main(String[] args) {

		int[] nums = {1,1,1}; int k = 2;
		System.out.println("Expected: 2, Actual: " + subarraySum(nums, k));
		
		int[] nums2 = {1,2,3}; k = 3;
		System.out.println("Expected: 2, Actual: " + subarraySum(nums2, k));
		
		int[] nums3 = {3, 4, 7, -2, 2, 1, 4, 2}; k=7;
		System.out.println("Expected: 6, Actual: " + subarraySum(nums3, k));
	}
	
	/*
	 * 	
	 * Desc:  https://leetcode.com/problems/subarray-sum-equals-k/discuss/535507/Explanation-to-why-map.get(sum-k)-is-done-than-count%2B%2B
	 * 
	 * Q: Why sum-k?
	 * A: Our target is: current sum - previous sum = k
	 *      so sum - k = previous sum (which is stored in the map)
	 * 
	 * Time and space: O(N)
	 */

	public static int subarraySum(int[] nums, int k) {
        // here key=sum and value=freqOfSumWeSeen
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int count = 0;
        map.put(0, 1);
        for(int num : nums) {
            sum += num;
            System.out.println("sum - k :: " + (sum - k));
            if(map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0)+1);
        }
        return count;
    }
}
