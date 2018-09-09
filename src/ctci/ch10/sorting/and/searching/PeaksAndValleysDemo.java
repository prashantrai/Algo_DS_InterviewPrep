package ctci.ch10.sorting.and.searching;

import java.util.Arrays;

public class PeaksAndValleysDemo {

	public static void main(String[] args) {

		//int[] arr = {5,3,1,2,3}; ///expected result: {5,1,3,2,3}
		int[] arr = {9,1,0,4,8,7};
		sortValleyPeak(arr);
		
		System.out.println(Arrays.toString(arr));
		
	}
	
	public static void sortValleyPeak(int[] arr) {
		
		//Arrays.sort(arr);
		for(int i=1; i < arr.length; i+=2) {
			int bigIndex = maxIndex(arr, i-1, i, i+1);
			swap(arr, i, bigIndex);
			//swap(arr, i-1, i);
		}
		
	}

	private static void swap(int[] arr, int i, int bigIndex) {
		int temp = arr[i];
		arr[i] = arr[bigIndex];
		arr[bigIndex] = temp;
		
	}

	private static int maxIndex(int[] arr, int a, int b, int c) {
		
		int value_a = a >=0 && a < arr.length ? arr[a] : Integer.MIN_VALUE;
		int value_b = b >=0 && b < arr.length ? arr[b] : Integer.MIN_VALUE;
		int value_c = c >=0 && c < arr.length ? arr[c] : Integer.MIN_VALUE;
		
		int max = Math.max(Math.max(value_a, value_b), c);
		
		if(value_a == max) 
			return a;
		else if(value_b == max)
			return b;
		else 
			return c;
		
		
	}

}
