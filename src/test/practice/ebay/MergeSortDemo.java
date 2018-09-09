package test.practice.ebay;

import java.util.Arrays;

public class MergeSortDemo {

	public static void main(String[] args) {
		
//		Integer [] arr = {12,14,9,11,3,7,2,6};
		int [] arr = {2, 3, 6, 7, 9, 11, 12, 14};
		int[] helper = new int[arr.length];
		
//		mergeSort_2(arr, helper, 0, arr.length-1);
//		mergeSort(arr, helper, 0, arr.length-1);
		mergeSort(arr, 0, arr.length-1, helper);
		
		/*helper = Arrays.copyOf(arr, arr.length);
		System.out.println("helper= "+Arrays.deepToString(helper));*/
		System.out.println("helper= "+Arrays.toString(arr));
		
		mergeSort_2(arr);
		System.out.println(">>ARR= "+Arrays.toString(arr));
	}
	
	public static void mergeSort(int[] arr, int low, int high, int[] helper) {
		
		if(low < high) {
			int mid = (low+high)/2;
			
			mergeSort(arr, low, mid, helper);
			mergeSort(arr, mid+1, high, helper);
			merge(arr, low, mid, high, helper);
		}
		
	} 
	
	public static void merge(int[] arr, int low, int mid, int high, int[] helper) {
		
		for(int i=low; i<=high; i++) {
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
		
		int remaining = mid - curr;
		for(int i=0; i<remaining; i++) {
			arr[curr+i] = helper[left+i];
		}
	}
	
	
	public static void mergeSort_2(int arr[]) {
		
		int[] temp = new int[arr.length];
		mergeSort_2(arr, temp, 0, arr.length-1);
	}
	
	public static void mergeSort_2(int[] arr, int[] temp, int left, int right) {
		
		if(left >= right) return;
		
		int mid = (left+right)/2;
		
		mergeSort_2(arr, temp, left, mid-1);
		mergeSort_2(arr, temp, mid+1, right);
		merge_2(arr, temp, left, right);
		
	}
	
	public static void merge_2(int[] arr, int[] temp, int leftStart, int rightEnd) {
		int leftEnd = (leftStart + rightEnd)/2;
		int rightStart = leftEnd + 1;
		int size = rightEnd - leftStart + 1;
		
		int leftIndex = leftStart;
		int rightIndex = rightStart;
		int currIndex = leftIndex; 
		
		while(leftIndex <= leftEnd && rightIndex <= rightEnd) {
			
			if(arr[leftIndex] <=  arr[leftEnd]) {
				temp[currIndex] = arr[leftIndex];
				leftIndex++;
			} else {
				temp[currIndex] = arr[rightIndex];
				rightIndex++;
			}
			currIndex++;
		}
		
		System.arraycopy(arr, leftIndex, temp, currIndex, leftEnd-leftIndex+1);
		System.arraycopy(arr, rightIndex, temp, currIndex, rightEnd-rightIndex+1);
		System.arraycopy(temp, leftStart, arr, leftStart, size);
		
	}

}
