package Cohesity;

public class FindPeakElement_162_Medium {

	public static void main(String[] args) {

	}
	
	/* Time: O(log n)
	 * Space:  O(1)
	 * 
	 */
	
	// Binary Search: https://leetcode.com/problems/find-peak-element/solution/
	public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if( nums[mid] > nums[mid + 1] ) 
                right = mid;
            else
                left = mid+1;
        }
        return left;
    }

}
