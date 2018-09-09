package test.practice.misc;

import java.util.Arrays;

public class DutchNationalFlagProblem {

	
	//--arrange 0's, 1's and 2's together
	public static void main(String[] args) {

		int[] arr = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
		dnp(arr);
		System.out.println(Arrays.toString(arr));
		
	}
	
	//-- Time complexity: O(n), Space: No extra space used
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
	
	private static void swap (int i, int j, int[] arr) {
		if(arr == null) return;
		
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
