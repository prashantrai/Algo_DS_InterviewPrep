package ctci.ch10.sorting.and.searching;

import java.util.Scanner;

public class SearchInRotatedArrayDemo {

	public static void main(String[] args) {

//		int[] arr = {4,5,6,7,0,1,2};
		int[] arr = { 2, 2, 2, 3, 4, 2};
		
		Scanner in = new Scanner(System.in);
		int v = in.nextInt();
		System.out.println(">>>Result:: "+binarySearch(arr, 0, arr.length-1, v));
	}
	
	public static int binarySearch(int[] arr, int left, int right, int v) {
		
		if(arr == null) 
			return -1;
		
		if(left > right) {
			return -1;
		}
		
		int mid = (left+right)/2;
		
		if(arr[mid] == v) {
			return mid;
		}
		
		if( arr[left] < arr[mid]) {  //--Left side is ordered
			
			if(v >= arr[left] && v < arr[mid]) {
				return binarySearch(arr, left, mid-1, v);
			} 
			else {
				return binarySearch(arr, mid+1, right, v);
			} 
		} 
		else if(arr[mid] < arr[right]) {  //--right side is ordered
			
			if(v > arr[mid] && v <= arr[right]) {
				return binarySearch(arr, mid+1, right, v);
			} 
			else {
				return binarySearch(arr, left, mid-1, v);
			}
			
		}
		else if(arr[left] == arr[mid]) {  //--left or right half is all reapeats
			
			if(arr[right] != arr[mid]) {
				return binarySearch(arr, mid+1, right, v);
				
			} else {
				
				int index = binarySearch(arr, left, mid-1, v);
				
				if(index == -1) {
					return binarySearch(arr, mid+1, right, v);
				}
				else {
					return index;
				}
			}
			
		}
		
		
		return -1;
		
	}

	
	
}
