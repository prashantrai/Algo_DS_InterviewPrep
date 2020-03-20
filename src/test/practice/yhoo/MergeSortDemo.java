package test.practice.yhoo;

import java.util.Arrays;

public class MergeSortDemo {

	
	public static Integer[] arr = {5, 1, 12, -5, 20, 16, -1};
	public static Integer[] helper = new Integer[arr.length];
	
	public static void main(String[] args) {

		//doMergeSort(0, arr.length-1);
		doMergeSort_2(0, arr.length-1);
//		doMergeSort_3(arr, helper, 0, arr.length-1);
		
		System.out.println(Arrays.deepToString(arr));
	}

	
	public static void doMergeSort_3(Integer[] arr, Integer[] helper, int low, int high) {
		
		if(low<high) {
			int mid = (low+high)/2;
			
			doMergeSort_3(arr, helper, low, mid);
			doMergeSort_3(arr, helper, mid+1, high);
			merge_3(arr, helper, low, mid, high);
		}
		
	} 
	
	//--needs to be fixed
	private static void merge_3(Integer[] arr, Integer[] helper, int low, int mid, int high) {
	
		mid = (low+high)/2;
		int left = low; //--left pointer
		int right = mid+1; //--right pointer
		int current = low;
		
		while(left <= mid && right <= high) {
			
			if(arr[left] > arr[right]) {
				helper[current] = arr[right];
				right--;
			} else {
				helper[current] = arr[left];
				left++;
			}
			current++;
		}
		
		System.arraycopy(arr, left, helper, current, mid-left+1);
		System.arraycopy(arr, right, helper, current, high-right+1);
		System.arraycopy(helper, low, arr, low, high-low+1);
	}


	public static void doMergeSort_2(int low, int high) {
		
		if(low<high) {
		
			int mid = (low+high)/2;
			
			doMergeSort_2(low,mid);
			doMergeSort_2(mid+1, high);
			doMerge(low, mid, high);
		}
		
	}
	
	
	public static void doMerge(int low, int mid, int high) {
		
		for(int i=0; i<=high; i++) {
			helper[i] = arr[i];
		}

		int helperLeft = low;
		int helperRight = mid+1;
		int current = low;
		
		
		while(helperLeft <= mid && helperRight <= high ) {
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
		
		/*for(int i=0; i<=remaining; i++) {
			arr[current+i] = helper[helperLeft+i];
		}*/
		
		while(helperLeft <= mid) {
			arr[current] = helper[helperLeft];
			current++;
			helperLeft++;
		}
		
	}
	
	
	
	
	
	public static void doMergeSort(int low, int high) {
		if(low < high) {
			
			int mid = (low+high)/2;
			
			doMergeSort(low, mid);
			doMergeSort(mid+1, high);
			merge(low, mid, high);
			
		}
	}
	
	public static void merge(int low, int mid, int high) {
		
		for(int i=0; i<=high; i++) {
			helper[i] = arr[i];
		}
		
		int i = low; //--helper left
		int j = mid+1; //--helper right
		int k = low; //--current
		
		while(i<=mid && j<= high) {
			
			if(helper[i] > helper[j]) {
				arr[k] = helper[j];
				j++;
			} else {
				arr[k] = helper[i];
				i++;
			}
			k++;
		}
		
		int remaining = mid - i;
		for(int n=0; n<=remaining; n++) {
			arr[k+n] = helper[i+n];
		}
		
		/*while(i<=mid) {
			arr[k] = helper[i];
			i++;
			k++;
		}*/
		
	}
}
