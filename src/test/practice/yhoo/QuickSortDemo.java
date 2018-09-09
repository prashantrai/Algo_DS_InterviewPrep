package test.practice.yhoo;

import java.util.Arrays;

public class QuickSortDemo {

	public static void main(String[] args) {

		Integer[] arr = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
		
		int left = 0;
		int right = arr.length-1;
		
		System.out.println("Input: "+ Arrays.deepToString(arr));
//		doQuickSort(arr, left, right);
//		doQuickSort_2(arr, left, right);
		doQuickSort_3(arr, left, right);
		System.out.println("Result: "+ Arrays.deepToString(arr));
		
	}

	
	public static void doQuickSort_3(Integer[] arr, int left, int right) {
		
		if(left >= right) return;
		
		int index = doPartition_3(arr, left, right);
		
		if(left < index-1)
			doQuickSort_3(arr, left, index-1);
		
		if(index < right)
			doQuickSort_3(arr, index, right);
		
	}
	
	
	private static int doPartition_3(Integer[] arr, int left, int right) {
			
		int pivot = arr[(left+right)/2];
		
		while (left <= right) {
			
			while (arr[left] < pivot) {
				left++;
			}
			while (arr[right] > pivot) {
				right--;
			}
			
			if(left <= right) {
				swap(arr, left, right);
				left++;
				right--;
			} 
 		}
		
		return left;
	}


	//--partition the array based on pivot
	public static void doQuickSort_2(Integer[] arr, int left, int right) {
		
		int index = doPartition(arr, left, right);
		
		if(left < index-1) {
			doQuickSort_2(arr, left, index-1);
		}
		if(index < right) {
			doQuickSort_2(arr, index, right);
		}
		
	}
	
	public static int doPartition(Integer[] arr, int left, int right) {
		
		int pivot = arr[(left+right)/2];
		
		while(left <= right) {
			
			while(arr[left] < pivot) left++;
			while(arr[right] > pivot) right--;
			
			if(left<=right) {
				swap(arr, left, right);
				left++;
				right--;
			}
		}
		
		return left;
	}
	
	
	
	public static void doQuickSort(Integer[] arr, int left, int right) {
		
		int index = partition(arr, left, right);
		
		if(left < index-1) {
			doQuickSort(arr, left, index-1);
		}
		if(index < right) {
			doQuickSort(arr, index, right);
		}
	}
		
	public static int partition(Integer[] arr, int left, int right) {
		
		int pivot = arr[(left+right)/2];
		
		while(left<=right) {
			while (arr[left] < pivot) left++; 
			while (arr[right] > pivot) right--;
			if(left <= right) {
				swap(arr, left, right);
				left++;
				right--;
			}
		}
		return left;
	}
	
	public static void swap(Integer[] arr, int left, int right) {
		int temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}
}
