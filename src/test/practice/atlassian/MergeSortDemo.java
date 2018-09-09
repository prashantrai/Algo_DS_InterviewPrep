package test.practice.atlassian;

import java.util.Arrays;

public class MergeSortDemo {

	
	public static void main(String[] args) {
		
		Integer [] arr = {12,14,9,11,3,7,2,6};
		Integer[] helper = new Integer[arr.length];
		
//		mergeSort_2(arr, helper, 0, arr.length-1);
//		mergeSort(arr, helper, 0, arr.length-1);
		mergeSort(arr, 0, arr.length-1, helper);
		
		/*helper = Arrays.copyOf(arr, arr.length);
		System.out.println("helper= "+Arrays.deepToString(helper));*/

		
		System.out.println("helper= "+Arrays.deepToString(arr));
	}

	public static void mergeSort(Integer[] arr, int low, int high, Integer[] helper) {
		
		if(low<high) {
			int mid = (low+high)/2;
			
			mergeSort(arr, low, mid, helper);
			mergeSort(arr, mid+1, high, helper);
			merge(arr, low, mid, high, helper);
		}
		
	}
	
	private static void merge(Integer[] arr, int low, int mid, int high, Integer[] helper) {
		
		//copy all the values to helper
		for(int i=low; i<=high; i++) {
			helper[i] = arr[i];
		} 
		
		int helperLeft = low;
		int helperRight = mid+1;
		int current = low;
		
		while(helperLeft <= mid && helperRight <= high) {
			if(helper[helperLeft] <= helper[helperRight]) {
				arr[current] = helper[helperLeft];
				helperLeft++;
				
			} else {
				arr[current] = helper[helperRight];
				helperRight++;
			}
			current++;
		}
		
		int remaining = mid - helperLeft;
		
		for(int i=0; i<=remaining; i++) {
			arr[current+i] = helper[helperLeft+i];
		}
		
	}
	//------
	
	/*public static void mergeSort_2 (Integer[] arr, Integer[] helper, int low, int high) {
	
		int mid = (low + high)/2;
		
		if(low < high) {
			
			mergeSort_2(arr, helper, low, mid);
			mergeSort_2(arr, helper, mid+1, high);

			merge_2(arr, helper, low, mid, high);
		
		}
	}
	

	private static void merge_2(Integer[] arr, Integer[] helper, int low, int mid, int high) {
		
		//--copy all elements from arr to helper
		for(int i=0; i<arr.length; i++) {
			helper[i] = arr[i];
		}
		
		int helperLeft = low;
		int helperRight = mid+1;
		int current = low;
		
		while(helperLeft <= mid && helperRight <= high) {
			
			if(helper[helperLeft] > helper[helperRight]) {
				arr[current] = helper[helperRight];
				helperRight++;
			} else {
				arr[current] = helper[helperLeft];
				helperLeft++;
			}
			current++;
		}
		
		int remaining = mid - helperLeft;
		
		for(int i=0; i<=remaining; i++) {
			
			arr[current+i] = helper[i+helperLeft];
		}
		
	}
	
	
	public static void mergeSort(Integer[] arr, Integer[] helper, int low, int high) {
		
		int mid = (low+high)/2;
		
		if(low < high) {
			mergeSort(arr, helper, low, mid);
			mergeSort(arr, helper, mid+1, high);
			
			merge(arr, helper, low, mid, high);
		}
		
	}	

	private static void merge(Integer[] arr, Integer[] helper, int low, int mid, int high) {

		for (int i=low; i<=high; i++) {
			helper[i] = arr[i];
		}
		
		int helperLeft = low;
		int helperRight = mid+1;
		int current = low;
		
		while(helperLeft <= mid && helperRight <= high) {
			
			if(helper[helperLeft] <= helper[helperRight]) {
				arr[current] = helper[helperLeft];
				helperLeft++;
			} else {
				arr[current] = helper[helperRight];
				helperRight++;
			}
			current++;
		}

		int remaining = mid - helperLeft;
		
		for(int i=0; i<=remaining; i++) {
			arr[current+i] = helper[helperLeft+i];
		}
		
	}*/
}
