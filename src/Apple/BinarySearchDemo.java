package Apple;

import java.util.Scanner;

public class BinarySearchDemo {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int v = Integer.parseInt(in.nextLine());
		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = v;
//		binarySearchREC(intArr, 0, intArr.length-1, value);
		binarySearch(intArr, 0, intArr.length-1, value);  //--iterative solution
	}
	
	
	public static void binarySearchREC(int[] arr, int low, int high, int val) {
		
		if(arr == null) 
			return;
		
		if(low == high && arr[low] != val) {
			System.out.println(">>> Value not found");
			return;
		}
		
		int mid = (low+high) / 2;

		if (val < arr[mid]) {
			binarySearchREC(arr, low, mid-1, val);
			
		} else if (val > arr[mid]) {
			binarySearchREC(arr, mid+1, high, val);
			
		} else if(arr[mid] == val) {
			System.out.println(">>> Value found at index " + mid);
			low = high+1;
			return;
		}
	}

	public static void binarySearch(int[] arr, int low, int high, int val) {
		
		if(arr == null) return;
		
		int mid = (low+high)/2;
		
		while (low<=high) {
			
			mid = (low+high)/2;
			
			if(arr[mid] > val) {
				high = mid-1;
				
			} else if (arr[mid] < val) {
				low = mid + 1;
			
			} else if (arr[mid] == val) {
				System.out.println("Element found at "+mid);
				low = high+1;
				return;
			}
			
		}
		
		System.err.println(">> Element not found...");
		
	}
	
	
}
