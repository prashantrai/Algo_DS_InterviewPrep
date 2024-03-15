package Facebook;

import java.util.HashMap;
import java.util.Map;

public class ContinuousSubarraySum_523_Medium {

	public static void main(String[] args) {
		int[] nums = {23,2,4,6,7}; 
		int k = 6;
		System.out.println("Expected: true,  Actual: " + checkSubarraySum(nums, k));
		
		int[] nums2 = {23,2,6,4,7}; 
		k = 6;
		System.out.println("Expected: true,  Actual: " + checkSubarraySum(nums2, k));
		
		int[] nums3 = {23,2,6,4,7}; 
		k = 13;
		System.out.println("Expected: false,  Actual: " + checkSubarraySum(nums3, k));

	}

	/*
    Key point: if we can find any two subarray of prefix sum have same 
    mod value, then their difference MUST be divisible by k. So we can 
    use a map to store mod value of each prefix sum in map, with its index. 
    Then check if map contains the same mod value with size > 2 when we have 
    new mod value in every iteration 
    
    For e.g. in case of the array [23,2,6,4,7] the running sum is [23,25,31,35,42] 
    and the remainders are [5,1,1,5,0]. We got remainder 5 at index 0 and at index 3. 
    That means, in between these two indexes we must have added a number which is 
    multiple of the k.
    */
    // Time: O(N)
    // Space: O(N)
	
    public static boolean checkSubarraySum(int[] nums, int k) {
        
        // another way to initialise mao, for details refer: https://stackoverflow.com/questions/1958636/what-is-double-brace-initialization-in-java
        // Map<Integer, Integer> map = new HashMap<>(){{put(0, -1);}};
        
        Map<Integer, Integer> map = new HashMap<>();
        // corner case: if the very first subarray with first two numbers 
        // in array could form the result, we need to 
        // put mod value 0 and index -1 to make it as a true case
        map.put(0, -1); 
        
        int runningSum = 0;
        
        for(int i=0; i < nums.length; i++) {
            runningSum += nums[i];
            
            // corner case: k CANNOT be 0 when we use a number mod k
            if(k != 0) {
                runningSum %= k;
            }
            
            Integer prev = map.get(runningSum);
            if(prev != null) {
                if(i - prev > 1) return true;
                
            } else {
                map.put(runningSum, i);
            }
        }
        return false;
    }
	
}
