package Facebook;

public class RemoveDuplicatesFromSortedArray_26_Easy {

	public static void main(String[] args) {
		int[] nums = {0,0,1,1,1,2,2,3,3,4};
		System.out.println("Expected: 5, Actual: " + removeDuplicates(nums));
	}

	/*
    Time: O(n)
    Space: O(1)
    
    Note: It doesn't matter what elements we place after the first k elements.
    We just have to move first k unique elements.
    */
    public static int removeDuplicates(int[] nums) {
        int insertIdx = 1;
        
        for(int i=1; i<nums.length; i++) {
            // We skip the next index if we see
            // a duplicate element
            if(nums[i-1] != nums[i]) {
                /* Storing the unique element at insertIndex 
                index and incrementing the insertIndex by 1 */
                // for first iteration they will be 
                // pointing to same index i.e. 1
                nums[insertIdx] = nums[i];
                insertIdx++;
            }
        }
        return insertIdx;
    }
}
