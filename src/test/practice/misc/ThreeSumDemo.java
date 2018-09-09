package test.practice.misc;

import java.util.Arrays;

public class ThreeSumDemo {

	//--Watch: https://www.youtube.com/watch?v=jXZDUdHRbhY
	
	public static void main(String[] args) {

		int[] arr = {10,3,7,10,11};
		int target = 20;
		threeSum(arr, target);
		
		int[] arr2 = {4,4,4};
		target = 12;
		threeSum(arr2, target);
		
		int[] arr3 = {10,3,7,10,11};
		target = 18;
		threeSum(arr3, target);
		
		int[] arr4 = {10,3,7,10,11};
		target = 21;
		threeSum(arr3, target);
		
	}
	
	
	public static void threeSum (int[] arr, int target) {
		
		if(arr == null || arr.length ==0 ) return;
		
		/**
		 * Run Time Complexity: O(n^2)
		 * 
		 * We'll follow approach of 2sum (sort the arr and scan from both sides/ends of arr to find the target sum),	
		 * 1. first sorting all the numbers in arr (nlogn)
		 * 2. Take the first index value and substract from the target 
		 * 			e.g. target = 20 at i=0, and arr[0]=10 then look for the remaining sum 10 (i.e. target-arr[i] = remainingSum)
		 * 					in rest of the array i.e. j=i+1 and k=arr.length
		 * 
		 * */
		
		Arrays.sort(arr);
		
		int subTarget;
		int j = 0;
		int k = arr.length-1;
		
		for (int i=0; i<arr.length-2; i++) {
		
			subTarget = Math.abs(arr[i] - target);
			j = i+1;
			while(j < k) {
				int sum = arr[j] + arr[k];
				if(subTarget != sum) {
					if(subTarget<sum) 
						k--; 
					else 
						j++;
				} else {
					System.out.println(">>> 3-sum: " + arr[i] +", "+ arr[j] +", "+ arr[k]);
					return;
				}
				
			}
		
		}
		System.out.println("None found.");
		
	}
	
	
	

}
