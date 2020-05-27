package leetcode;

public class RemoveDuplicatesFromSortedArray_II_80_Medium {

	public static void main(String[] args) {
		int[] nums1 = {1,1,1,2,2,3};
		System.out.println("Expected: 5, Actual: "+removeDuplicates(nums1));
		
		int[] nums2 = {0,0,1,1,1,1,2,3,3};
		System.out.println("Expected: 7, Actual: "+removeDuplicates(nums2));

	}

	// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
	public static int removeDuplicates (int[] nums) {
        int i=0;
        for(int n : nums) {
            if(i < 2 || n > nums[i-2]) {
                nums[i] = n;
                i++;
            }
        }
       return i; 
    }
	
}
