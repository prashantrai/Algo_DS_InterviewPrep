package test.practice.atlassian;

import java.util.Arrays;

public class QuickSortDemo {

	public static void main(String[] args) {
		
		Integer [] arr = {12,14,9,11,3,7,2,6};
		
		doQuickSort(arr, 0, arr.length-1);
		System.out.println(Arrays.deepToString(arr));

	}
	
	private static void doQuickSort(Integer[] arr, int left, int right) {
		
		int index = doPartition(arr, left, right);
		
		if(left < index-1) {
			doQuickSort(arr, left, index-1);
		}
		if(right > index) {
			doQuickSort(arr, index, right);
		}
		
	}

	private static int doPartition(Integer[] arr, int left, int right) {

		int pivot = (left+right)/2;
		
		while (left <= right) {
			
			while(arr[left] < arr[pivot]) left++;
			while(arr[right] > arr[pivot]) right--;
			
			if(left <= right) {
				swap(arr, left, right);
				left++;
				right--;
			}
			
		}
		
		return left;
	}

	private static void swap(Integer[] arr, int left, int right) {
		int temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}
}
