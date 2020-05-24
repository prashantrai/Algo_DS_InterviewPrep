package leetcode;

public class SearchInRotatedSortedArr_33_Medium {

	public static void main(String[] args) {

//		Integer[] arr = {4,5,6,7,0,1,2};
//		Scanner in = new Scanner(System.in);
//		int v = in.nextInt();
//		Integer[] arr2 = {4,5,6,7,8,1,2,3,4};
//		Integer[] arr2 = {5,6,7,0,1,2,3,4};
//		Integer[] arr2 = {2,3,4,5,6,7,8,1};
//		Integer[] arr2 = {4,5,6,7,1,1,1,3,4};
		
		int[] arr = {4,5,6,7,0,1,2};
		int target = 0; //element to be searched
		int res = search(arr, target);
		System.out.println("Expected: 4, Actual: "+res);
		
		/*Could be a follow-up to the problem
		Find min and max element in a rotated sorted array */
		int[] arr2 = {3,1,2};
		int idx = findRotationPoint(arr2); //returns lowest element index
		int min_idx = idx;
		int max_idx = min_idx == 0 ? arr2.length-1 : min_idx-1;
		System.out.println("min="+ arr2[min_idx] +", max="+arr2[max_idx]);
		
		//--When array has duplicates
		int[] arr3 = {3,3,1,3}; //--handle dups, refer FindMinimumInRotatedSortedArray_II_154_Hard
		idx = findRotationPoint2(arr3); //returns lowest element index
		min_idx = idx;
		max_idx = min_idx == 0 ? arr3.length-1 : min_idx-1;
		System.out.println("DUPS: min="+ arr3[min_idx] +", max="+arr3[max_idx]);
		
	}
	
	public static int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        
        //--rotatation point/smallest element index
        int smallest = findRotationPoint(nums); //--perform modified binary search
        
        int left = 0;
        int right = nums.length-1;
        
        if(target == nums[smallest]) 
            return smallest;
        
        if(target >= nums[smallest] && target <= nums[right]) {
            left = smallest;
        } else {
            right = smallest;
        }
        
        //--perform normal binary search
        while (left <= right) {
            int mid = left + (right - left)/2;
            
            if(nums[mid] == target) 
                return mid;
            
            if(target > nums[mid]) {
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        
        return -1;
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
            } else { 
                right = mid; //smallest must be on left
            }
        }
        return left;
    }
    
    //--To handle dups - in interview if the dups is not the case implement the above simple then extend to handle dups
    private static int findRotationPoint2(int[] nums) {
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
