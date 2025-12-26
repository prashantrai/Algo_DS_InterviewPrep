package TikTok;

import java.util.Arrays;

public class FindFirstAndLastPositionOfElementInSortedArray_34_Medium {

	public static void main(String[] args) {

		int[] nums = {5,7,7,8,8,10}; 
		int target = 8;
		int[] res = searchRange(nums, target);
		System.out.println("Expected: [3,4], Actual: " + Arrays.toString(res));
		
		target = 6;
		int[] res2 = searchRange(nums, target);
		System.out.println("Expected: [-1,-1], Actual: " + Arrays.toString(res2));
	}
	
	/*
	 * Refer: //https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/discuss/14699/Clean-iterative-solution-with-two-binary-searches-(with-explanation)
	 * 
	 * Time: O(log N)
	 * Space: O(1)
	 */

	public static int[] searchRange(int[] nums, int target) {
		
		int[] res = {-1, -1};
		
		if(nums.length == 0) return res;
		
		res[0] = getLeftIndex(nums, target);
		res[1] = getRightIndex(nums, target);
		
		return res;
		
	}

	private static int getLeftIndex(int[] nums, int target) {
		int left = 0;
		int right = nums.length-1;
		
		while(left < right) {
			int mid = left + (right - left)/2; // lower mid
			
			if(target > nums[mid]) {
				left = mid+1;
			} else {
				right = mid;
			}
		}
		
		return nums[left] == target ? left : -1;
	}
	
	/* 
    Why we need +1 in "int mid = left + (right - left + 1) / 2"? 
    Key idea, avoiding infinite loops in Binary Search.

    In binary search, how you compute mid determines which half you bias toward 
    when the search space has two elements. This becomes critical when your 
    update rule is:
        left = mid;   // not mid + 1

    Because if mid == left, and we set left = mid, we get no progress → infinite loop!

    Example: 
    Find Last Position of target = 8 in [5, 7, 7, 8, 8, 10]
    We want to find index 4.

    Suppose during getRightIndex, we reach:
    left = 3, right = 4 → subarray [8, 8]
    ❌ Using lower mid:
    mid = 3 + (4 - 3)/2 = 3 + 0 = 3
    
    Now, since nums[mid] = 8 == target, we do:
    left = mid;  // left = 3 → no change!
    Next iteration: left=3, right=4 → same state → infinite loop!

    ✅ Using upper mid:
    mid = 3 + (4 - 3 + 1)/2 = 3 + (2)/2 = 3 + 1 = 4
    Now, nums[mid] = 8 == target, so:
    left = mid;  // left = 4
    
    Now left == right == 4 → loop ends. Return 4 → correct!
	*/
	
	private static int getRightIndex(int[] nums, int target) {
		int left = 0;
		int right = nums.length-1;
		
		while(left < right) {
			// upper mid to bias right, Finding last occurrence
            // The +1 ensures that when the window has 2 elements, 
            // mid picks the right one, so left = mid actually moves forward.
			int mid = left + (right - left + 1) / 2;
			
			if(target < nums[mid]) {
				right = mid - 1;
				
			} else {
				left = mid;
			}
		}
		
		return nums[right] == target ? right : -1;
	}
	
}

