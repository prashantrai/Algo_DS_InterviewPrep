package Facebook;

public class FindPeakElement_162_Medium {

	public static void main(String[] args) {

	}
	
	/* Perform binary search and compare mid with mid+1 
	 * (not left or right with mid like usual binary search)
	 * 
	 * i.e. if (nums[mid] > nums[mid + 1]) 
	 * 
	 * Time: O(log n)
	 * Space:  O(1)
	 */
	
	// Binary Search: https://leetcode.com/problems/find-peak-element/solution/
	public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            
        	int mid = left + (right - left) / 2;
            
            // NOTE: we are comparing mid with mid+1.
            // DON'T DO left and right comparison like usual binary search
            if( nums[mid] > nums[mid + 1] ) 
                right = mid;
            else
                left = mid+1;
        }
        return left;
    }

}
