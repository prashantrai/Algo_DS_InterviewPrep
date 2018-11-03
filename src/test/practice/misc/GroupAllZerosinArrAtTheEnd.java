package test.practice.misc;

import java.util.Arrays;

public class GroupAllZerosinArrAtTheEnd {

	
	/*
	 * https://www.youtube.com/watch?v=VzQ2KacyDLw
	 * 
	 * input: {0,1,2,0,3,0};
	 * output: {1,2,3,0,0,0}
	 * 
	 * To solve use in place manipulation i.e. no extra space
	 * 
	 * Note: order of non-zero element has not changed
	 * 
	 * */
	public static void main(String[] args) {

		int[] arr = {0,1,2,0,3,0};
		
		helper(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void helper(int[] arr) {

		int j=1;
		
		for(int i=0; i<arr.length-1; i++) {
			
			if(arr[i] == 0) {
				while(j<arr.length && arr[j] == 0) j++;
				
				if(j==arr.length) break;
				
				swap(arr, i, j);
			}
		}
		
	}
	
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
}
