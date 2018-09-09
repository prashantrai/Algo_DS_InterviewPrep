package test.practice.asurion;

import java.util.Scanner;

public class BinarySearchDemo {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int v = Integer.parseInt(in.nextLine());
		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = v;
		binarySearchREC(intArr, 0, intArr.length-1, value);
//		binarySearch(intArr, 0, intArr.length-1, value);

		
	}

	
	public static void binarySearchREC (int[] arr, int low, int high, int value) {
		
		
		int mid = (low + high) / 2;
		
		if(low == high && arr[low] != value) {
			System.err.println("Not found");
			return;
		}
		
		if(value < arr[mid]) { 
			binarySearchREC(arr, low, mid-1, value);
		}
		else if(value > arr[mid]) {
			binarySearchREC(arr, mid+1, high, value);
		}
		else if(value == arr[mid]) {
			System.out.println("Found");
			low = high+1;
			return;
		}
		
		
	}
	
	public static void binarySearch (int[] arr, int low, int high, int value) {
		
		
		if(arr == null) return;
		
		int mid = (low + high) / 2;
		
		while (low <= high) {
		
			if(value < arr[mid]) {
				high = mid-1;
			}
			
			else if(value > arr[mid]) {
				low = mid+1;
			}
			
			else if(value == arr[mid]) {
				System.out.println("Found");
				low = high + 1;
				return;
			}
			
			mid = (low + high) / 2;
		}
		
		System.out.println("Not found");
	}
	
	
}
