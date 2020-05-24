package leetcode;

public class FindMinimumInRotatedSortedArray_II_154_Hard {

	public static void main(String[] args) {
		
		int[] arr = {3,3,1,3}; //--need to handle dups, check CTCI implementation
		int idx = findRotationPoint(arr); //returns lowest element index
		int min_idx = idx;
		System.out.println("Expected: 1, Actual: "+arr[min_idx]);
		
		int max_idx = min_idx == 0 ? arr.length-1 : min_idx-1;
		System.out.println("min="+ arr[min_idx] +", max="+arr[max_idx]);
	}

	//https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
	//Handle dups
	
	// Runtime : Worst case is O(n) i.e. when array is all dups
	public int findMin(int[] nums) {
        if(nums == null || nums.length == 0) return -1;
        
        //--rotatation point/smallest element index
        int smallest = findRotationPoint(nums); //--perform modified binary search
        return nums[smallest];
    }
    
    /*When sorted array is rotation the then the rotation point is the smallest element
    in the array. 
    */
    //--modiied binary search
    private static int findRotationPoint(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        while (left < right) {
            int mid = left + (right - left)/2;
            if(nums[mid] > nums[right]) { //smallest must be on right
                left = mid+1;
            } else if (nums[mid] < nums[right]) { 
                right = mid; //smallest must be on left
            } else {
                right--;
            }
        }
        return left;
    }
	
}
