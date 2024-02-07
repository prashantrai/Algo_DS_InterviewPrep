package TikTok;

public class MergeOperationsToTurnArrayIntoAPalindrome_2422_Medium {

	public static void main(String[] args) {

		int[] nums = {4,3,2,1,2,3,1};
		System.out.println("Expected: 2, Actual: " + minimumOperations(nums));
		
		int[] nums2 = {1,2,3,4};
		System.out.println("Expected: 3, Actual: " + minimumOperations(nums2));
		
	}

	/*
    Refer: https://leetcode.com/problems/merge-operations-to-turn-array-into-a-palindrome/discuss/3673330/JAVA-Beats-100-or-O(n)-Time-or-Two-Pointers-or-Very-Easy-Solution-or
    
    Time: O(N)
    Space: O(1)
    */
    
    public static int minimumOperations(int[] nums) {
        if(nums.length == 1) 
            return 0;
        
        int steps = 0;
        int l = 0; // left pointer
        int r = nums.length-1; // right pointer
        int leftSum = nums[l];
        int rightSum = nums[r];
        
        while(l < r) {
            // when value at both l and r index in array are same move pointer 
            // i.e. l-- and r++
            if(leftSum == rightSum) {
                l++;
                r--;
                leftSum  = nums[l];
                rightSum = nums[r];
            } else if(leftSum < rightSum) {
                l++;
                leftSum += nums[l];
                steps++;
            } else if(leftSum > rightSum) {
                r--;
                rightSum += nums[r];
                steps++;
            }
        }
        return steps;
    }
	
}
