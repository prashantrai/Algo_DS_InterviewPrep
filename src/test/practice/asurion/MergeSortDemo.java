package test.practice.asurion;

import java.util.Arrays;

public class MergeSortDemo {

	public static void main(String[] args) {
		
//		Integer [] arr = {12,14,9,11,3,7,2,6};
		Integer [] arr = {2, 3, 6, 7, 9, 11, 12, 14};
		Integer[] helper = new Integer[arr.length];
		
//		mergeSort_2(arr, helper, 0, arr.length-1);
//		mergeSort(arr, helper, 0, arr.length-1);
		mergeSort(arr, 0, arr.length-1, helper);
		
		/*helper = Arrays.copyOf(arr, arr.length);
		System.out.println("helper= "+Arrays.deepToString(helper));*/

		
		System.out.println("helper= "+Arrays.deepToString(arr));
	}
	
	public static void mergeSort(Integer[] arr, int low, int high, Integer[] helper) {
		
		int mid = (low+high) / 2;
		if(low < mid) {
			mergeSort(arr, low, mid, helper);
			mergeSort(arr, mid+1, high, helper);
			merge(arr, low, high, mid, helper);
		}
		
	}
	
	public static void merge (Integer[] arr, int low, int high, int mid, Integer[] helper) {
		
		for (int i=0; i<=high; i++) {
			helper[i] = arr[i];
		}
		
		int left = low;
		int right = mid+1;
		int curr = low;
		
		while(left <= mid && right <= high) {
			
			if(helper[left] <= helper[right]) {
				arr[curr] = helper[left];
				left++;
			} else {
				arr[curr] = helper[right];
				right++;
			}
			curr++;
 			
		}
		
		int remaining = mid - left;
		for(int i=0; i<=remaining; i++) {
			arr[curr+i] = helper[left+i];
		}
		
	}

}
