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
			int mid = left + (right - left)/2;
			
			if(target > nums[mid]) {
				left = mid+1;
			} else {
				right = mid;
			}
		}
		
		return nums[left] == target ? left : -1;
	}
	
	private static int getRightIndex(int[] nums, int target) {
		int left = 0;
		int right = nums.length-1;
		
		while(left < right) {
			int mid = (left + (right - left)/2)+1;
			
			if(target < nums[mid]) {
				right = mid - 1;
				
			} else {
				left = mid;
			}
		}
		
		return nums[right] == target ? right : -1;
	}
	
}

