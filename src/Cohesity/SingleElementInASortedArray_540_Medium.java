package Cohesity;

public class SingleElementInASortedArray_540_Medium {

	public static void main(String[] args) {
		
		int[] nums = {1, 1, 2, 2, 3, 3, 4, 5, 5};
		int res = singleNonDuplicate(nums);
		System.out.println("Expected: 4, Actual: "+ res);
		
		int[] nums2 = {1,1,2,3,3,4,4,8,8};
		res = singleNonDuplicate(nums2);
		System.out.println("Expected: 2, Actual: "+ res);
	}
	
	/**
	 * EXPLANATION:-
		Suppose array is [1, 1, 2, 2, 3, 3, 4, 5, 5]
		
		we can observe that for each pair, 
		first element takes even position and second element takes odd position
		
		for example, 1 is appeared as a pair,
		so it takes 0 and 1 positions. similarly for all the pairs also.
		
		this pattern will be missed when single element is appeared in the array.
		
		From these points, we can implement algorithm.
		1. Take left and right pointers . 
		    left points to start of list. right points to end of the list.
		2. find mid.
		   	If mid is even, 
		   		then it's duplicate should be in next index.
			
			or, if mid is odd, 
				then it's duplicate  should be in previous index.
				
			check these two conditions, 
			
			if any of the conditions is satisfied,
			then pattern is not missed, 
			Why? 
			
				Because if an array has each element twice then the 2nd
				Occurrence will always be an odd index. 
				
				e.g. [3, 3, 4, 4, 5, 5, 6, 6] here when every value is 2 times,
				3 is at 0 and 1 index and 4 is at 2 and 3 index (and so on). 
				In both the cases 2nd occurrence is an odd index. 
				
				So, if the mid is even then 2nd occurrence of number at mid will be at odd index i.e. mid+1
				and similarly if the mid is odd then 2nd occurrence of number will be an even index i.e. mid-1
			
			
			so check in next half of the array. i.e, left = mid + 1
			if condition is not satisfied, then the pattern is missed.
			so, single number must be before mid.
			so, update end to mid.
			
		3. At last return the nums[left]
		
		
	 * Time: - O(logN) space:- O(1)
	 */

	public static int singleNonDuplicate(int[] nums) {
        
		// take left and right pointer
        int left = 0;
        int right = nums.length-1;
        
        while(left < right) {
        	int mid = left + (right - left)/2; // find mid
        
        	/*
        	If mid is even, 
		   		then it's duplicate should be in next index. 
			
			or, if mid is odd, 
				then it's duplicate  should be in previous index.
				
			if any of the conditions is satisfied,
			then pattern is not missed. Why? 
			
			Because if an array has each element twice then the 2nd
			Occurrence will always be an odd index. 
			
			e.g. [3, 3, 4, 4, 5, 5, 6, 6] here when every value is 2 times,
			3 is at 0 and 1 index and 4 is at 2 and 3 index (and so on). 
			In both the cases 2nd occurrence is an odd index. 
			
			So, if the mid is even then 2nd occurrence of number at mid will be at odd index i.e. mid+1
			and similarly if the mid is odd then 2nd occurrence of number will be an even index i.e. mid-1
			
        	 */
        	
        	 boolean isMidEven = mid%2 == 0;
             
             if( (isMidEven && nums[mid] == nums[mid+1]) 
                     || (!isMidEven && nums[mid] == nums[mid-1])  ) {
                 // pattern not missed i.e. search right
                 left = mid+1;                
                 
             } else {
                 right = mid;
             }
        }
        
        return nums[left];
        
    }
	

}