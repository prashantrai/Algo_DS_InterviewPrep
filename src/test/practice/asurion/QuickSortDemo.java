package test.practice.asurion;

import java.util.Arrays;

public class QuickSortDemo {
	
	public static void main(String[] args) {

		Integer[] arr = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
		
		int left = 0;
		int right = arr.length-1;
		
		System.out.println("Input: "+ Arrays.deepToString(arr));
		quickSort(arr, left, right);
		System.out.println("Result: "+ Arrays.deepToString(arr));
		
	}
	
	
	public static void quickSort(Integer[] arr, int left, int right) {
		
		if(left >= right) return;
		
		int index = partition(arr, left, right);
		
		if(left < index-1) {
			quickSort(arr, left, index-1);
		} 
		
		if (index < right) {
			quickSort(arr, index, right);
		}
	}
	

	/*public static int partition(Integer[] arr, int left, int right) {
		
		int pivot = (left+right)/2;
		
		while(left <= right) {
			
			while(arr[left] < arr[pivot]) left++;
			while(arr[right] > arr[pivot]) right--;
			
			if(left <= right) {
				swap(arr, left, right);
				left++;
				right--;
			}
		}
		return left;
	}*/
	
	public static int partition(Integer[] arr, int left, int right) {
		
		int pivot = arr[(left+right)/2];
		
		while(left <= right) {
			
			while(arr[left] < pivot) left++;
			while(arr[right] > pivot) right--;
			
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
