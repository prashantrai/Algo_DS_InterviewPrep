package Facebook;

public class MaxConsecutiveOnes_III_1004_Medium {

	public static void main(String[] args) {
		int[] nums = {1,1,1,0,0,0,1,1,1,1,0}; 
		int k = 2;
		
		int res = longestOnes(nums, k);
		System.out.println("Expected: 6, Actual: " + res);
	}
	
	
	/*
	 * Ref: https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC++Python-Sliding-Window
	
	Explanation [sliding window]
	- For each A[j], try to find the longest subarray.
	- If A[i] ~ A[j] has zeros <= K, we continue to increment j.
	- If A[i] ~ A[j] has zeros > K, we increment i (as well as j).
	
	Time Complexity: O(N), where N is the number of elements in the array. 
	Space Complexity: O(1). We do not use any extra space.
	 * */
	
	public static int longestOnes(int[] nums, int k) {
		int i = 0;
		int j;
		for(j=0; j<nums.length; j++) {
            // If we included a zero in the window we reduce the value of k.
            // Since k is the maximum zeros allowed in a window.
			if(nums[j] == 0) {
				k--;
			}
            // A negative k denotes we have consumed all allowed 
            // flips and window has more than allowed zeros, 
            // thus increment left pointer by 1 to keep the window size same.
			if(k<0) {
				// If the left element to be thrown out is zero we increase k.
				if(nums[i] == 0) {
					k++;
				}
				i++;
			}
		}
		return j-i;
	}
	
	// same as above just second if is one liner
	public static int longestOnes2(int[] nums, int k) {
        int i = 0, j;
        for (j = 0; j < nums.length; ++j) {
            if (nums[j] == 0) 
            	k--;
            if (k < 0 && nums[i++] == 0) 
            	k++;
        }
        return j - i;
    }

}
