package test.practice.ebay;

import java.util.Scanner;

public class SearchInRotatedArrDemo {

	public static void main(String[] args) {

		//int[] arr = {4,5,6,7,0,1,2};
//		int[] arr = { 2, 2, 2, 3, 4, 2};
		int[] arr = {3, 4, 6, 7, 8 , 1 , 2};
		
		Scanner in = new Scanner(System.in);
		int v = in.nextInt();
		System.out.println(">>>Result:: "+binarySearch(arr, 0, arr.length-1, v));
		System.out.println(">>>2. Result:: "+binarySearch_2(arr, 0, arr.length-1, v));
	}

	private static int binarySearch(int[] arr, int left, int right, int v) {

		if(left > right) {
			return -1;
		}
		
		int mid = (left+right)/2;
		
		if (arr[mid] == v) {
			return mid;
		}
		
		if(arr[left] < arr[mid]) { //--left side is ordered
			
			if(v >= arr[left] && v < arr[mid]) {
				return binarySearch(arr, left, mid-1, v);
			} else {
				return binarySearch(arr, mid+1, right, v);
			}
			
		} else if(arr[left] > arr[mid]) { //--right side is ordered
			if(v <= arr[right] && v > arr[mid]) {
				return binarySearch(arr, mid+1, right, v);
			} else {
				return binarySearch(arr, left, mid-1, v);
			}
			
		} else if(arr[left] == arr[mid]) {
			if(v <= arr[right] && v > arr[mid]) {
				return binarySearch(arr, mid+1, right, v);
				
			} else { //--search both the halves
				int i = binarySearch(arr, left, mid-1, v);
				if(i == -1) {
					 return binarySearch(arr, mid+1, right, v);
				}
				return i;
			}
		}
		return -1;
	}
	
	
	private static int binarySearch_2(int[] arr, int left, int right, int v) {
		
		if(left > right) {
			return -1;
		}
		
		int mid = (left+right)/2;
		
		if(arr[mid] == v) {
			return mid;
		}
		
		//--left in order
		if(arr[left] < arr[mid]) {
			
			if(v >= arr[left] && v < arr[mid]) {
				return binarySearch_2(arr, left, mid-1, v);
			} else {
				return binarySearch_2(arr, mid+1, right, v);
			}
			
		} else if (arr[mid] < arr[right]) { //--right in order
			
			if(v <= arr[right] && v > arr[mid]) {
				return binarySearch_2(arr, mid+1, right, v);
			} else {
				return binarySearch_2(arr, left, mid-1, v);
			}
		
		} /*else if(arr[mid] == arr[left] || arr[mid] == arr[right]) {
		
			if(arr[left] <= v && arr[left] == arr[mid]) {
				return binarySearch_2(arr, left, mid-1, v);
			} else if(arr[right] >= v && arr[right] == arr[mid]) {
				return binarySearch_2(arr, mid+1, right, v);
			}
		}*/
		
		else if(arr[left] == arr[mid]) {
			
			//search for right if value is that range
			if(v <= arr[right] && v > arr[mid]) {
				return binarySearch_2(arr, mid+1, right, v);
			} else { //--search both the sides
				
				int i = binarySearch_2(arr, left, mid-1, v);
				
				if(i != -1) {
					return binarySearch_2(arr, mid+1, right, v);
				}
			}
		}
		
 		
		return -1;
	}
	
	
	
	

}
