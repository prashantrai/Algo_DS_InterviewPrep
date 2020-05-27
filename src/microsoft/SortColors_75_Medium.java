package microsoft;

import java.util.Arrays;

public class SortColors_75_Medium {

	public static void main(String[] args) {
		//int[] arr = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
		int[] arr = {2,0,2,1,1,0};
		sortColors(arr);
		//dnp(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	
	/*
	 *  DutchNationalFlagProblem  
	 *  	https://leetcode.com/problems/sort-colors/
	 *  
	 *  Time Complexity: O(n)
	 *  space : O(1)
	 */
	public static void sortColors(int[] nums) {
        if(nums == null || nums.length == 0) return;
        
        int left=0, right=nums.length-1, mid=0;
        
        while(mid <= right) {
            
            if(nums[mid] == 0 ) {
                swap(left, mid, nums);
                left++;
                mid++;
            } else if(nums[mid] == 2) {
                swap(mid, right, nums);
                right--;
            } else {
                mid++;
            }
        }
    }
    
    public static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    
    
    // working
    public static void dnp(int[] arr){
		
		if(arr == null || arr.length == 0) return;
		
		int low = 0, high = arr.length-1, mid = 0;
		
		//--we'll take 1 as pole i.e. we'll arrange 0's and 2's around 1's (0's on left and 2's on right)
		while (mid <= high) {
			
			switch (arr[mid]) {
			
				case 0:
					swap(low, mid, arr);
					low++;
					mid++;
					break;
					
				case 1:
					mid++; //--this is pole, so no change is required
					break;
					
				case 2:
					swap(mid, high, arr);
					high--;
					break;
			
			}
		}
	}

}
