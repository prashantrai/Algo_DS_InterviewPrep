package leetcode;

import java.util.HashSet;
import java.util.Set;

public class FindTheDuplicateNumber_287_Medium {

	public static void main(String[] args) {
		int[] nums = {1,3,4,2,2};
		System.out.println("Expected: 2, Actual: " + findDuplicate(nums));
		System.out.println("Expected: 2, Actual: " + findDuplicate2(nums));
	
	}

	//https://leetcode.com/problems/find-the-duplicate-number/
	//https://leetcode.com/problems/find-the-duplicate-number/discuss/72846/My-easy-understood-solution-with-O(n)-time-and-O(1)-space-without-modifying-the-array.-With-clear-explanation.
	// Similar problem: Detect cycle in a LinkedList
    // Time: O(N) and space: O(1)
    public static int findDuplicate(int[] nums) {
        
        if(nums == null || nums.length == 0) 
            return -1;
        
        int slow = nums[0];
        int fast = nums[nums[0]];
        
        //Find if cycle exist
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        
        fast = 0;
        // Find start of cycle
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        return slow;
    }
    
    // Time and space: O(N)
    public static int findDuplicate2(int[] nums) {
        Set<Integer>seen = new HashSet<>();
        
        for(int num : nums) {
            if(seen.contains(num)) return num;
            seen.add(num);
        }
        return -1;
    }
}
