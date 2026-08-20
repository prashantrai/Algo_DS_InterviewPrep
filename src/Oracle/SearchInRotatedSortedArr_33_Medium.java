package Oracle;

public class SearchInRotatedSortedArr_33_Medium {

	public static void main(String[] args) {
        // Basic cases
        runTest(new int[]{4,5,6,7,0,1,2}, 0, 4);
        runTest(new int[]{4,5,6,7,0,1,2}, 3, -1);

        // Edge cases
        runTest(new int[]{1}, 1, 0);
        runTest(new int[]{1}, 0, -1);
        runTest(new int[]{3,1}, 1, 1);
        runTest(new int[]{3,1}, 3, 0);

        // Not rotated
        runTest(new int[]{1,2,3,4,5,6}, 4, 3);
        runTest(new int[]{1,2,3,4,5,6}, 7, -1);

        // Small rotated arrays
        runTest(new int[]{5,1,3}, 5, 0);
        runTest(new int[]{5,1,3}, 3, 2);
        runTest(new int[]{5,1,3}, 1, 1);

        // Larger rotated cases
        runTest(new int[]{6,7,8,1,2,3,4,5}, 2, 4);
        runTest(new int[]{6,7,8,1,2,3,4,5}, 8, 2);
        runTest(new int[]{6,7,8,1,2,3,4,5}, 9, -1);

        // Complex edge-style case
        runTest(new int[]{30,40,50,60,10,20}, 10, 4);
        runTest(new int[]{30,40,50,60,10,20}, 35, -1);
    }
    private static void runTest(int[] nums, int target, int expected) {
        int actual = search(nums, target);
        System.out.println("target = " + target + ", result = " + actual +
                ", expected = " + expected);
    }
	
	/* 
    - Use binary search
    - At every step, one half is always sorted
    - Check:
        if nums[left] <= nums[mid], then left half is sorted
        else right half is sorted
    - Then decide whether target lies in that sorted half
    - Move pointers accordingly
	*/
	
	// Time: O(log n), Space: (1)
	
	private static int search(int[] nums, int target) {
	    int left = 0, right = nums.length-1;
	    while (left <= right) {
	        int mid = left + (right - left)/2;
	
	        // Found target
	        if(nums[mid] == target) {
	            return mid;
	        }
	
	        // Left half is sorted
	        if(nums[left] <= nums[mid]) {
	            if(nums[left] <= target && nums[mid] > target) {
	                right = mid-1; // Search in left half
	            } else {
	                left = mid+1; // Search in right half
	            }
	        }
	        // Right half is sorted
	        else {
	            if(nums[mid] < target && nums[right] >= target) {
	                left = mid+1; // Search in right half
	            } else {
	                right = mid-1; // Search in left half
	            }
	        }
	
	    }
	    return -1;
	}
	
}
