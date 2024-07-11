package Facebook;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK_560_Medium {

	public static void main(String[] args) {

		int[] nums = {1,1,1}; int k = 2;
		//System.out.println("Expected: 2, Actual: " + subarraySum(nums, k));
		
		int[] nums2 = {1,2,3}; k = 3;
		//System.out.println("Expected: 2, Actual: " + subarraySum(nums2, k));
		
		int[] nums3 = {3, 4, 7, -2, 2, 1, 4, 2}; k=7;
		System.out.println("Expected: 6, Actual: " + subarraySum(nums3, k));
	}
	
	/*
	 * 	
	 * Desc:  https://leetcode.com/problems/subarray-sum-equals-k/discuss/535507/Explanation-to-why-map.get(sum-k)-is-done-than-count%2B%2B
	 * 
	 * Video: to understand: https://www.youtube.com/watch?v=HbbYPQc-Oo4
	 * 
	 * Q: Why sum-k?
	 * A: Our target is: current sum - previous sum = k
	 *      so sum - k = previous sum (which is stored in the map)
	 * 
	 * Q:Why map.put(0, 1)?
	 * 
	 * The algorithm uses a prefix sum approach where sum represents 
	 * the cumulative sum of elements up to the current index.
	 * 
	 * The map (map) keeps track of how many times each prefix sum 
	 * has been seen so far. For each element, the algorithm checks 
	 * if (sum - k) exists in the map. If it does, it means there 
	 * is a subarray ending at the current index which sums to k.
	 * 
	 * By adding map.put(0, 1) at the beginning, the algorithm accounts 
	 * for cases where the subarray that sums to k starts from index 0.
	 * 
	 * Thus, map.put(0, 1) is essential for correctly initializing the 
	 * map to handle cases where subarrays start from the beginning of the array.
	 * 
	 * Time and space: O(N)
	 */

	
	public static int subarraySum(int[] nums, int k) {
        // here key=sum and value=freqOfSumWeSeen
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int count = 0;
        
        // By adding map.put(0, 1) at the beginning, the algorithm accounts 
   	 	// for cases where the subarray that sums to k starts from index 0.
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
