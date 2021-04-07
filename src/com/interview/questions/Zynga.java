package com.interview.questions;

public class Zynga {

	private static enum Element {
	    H, HE, LI, BE, B, C, N, O, F, NE
	}
	public static void main(String[] args) {
		
		Element[] e = Element.values();
		
		Element ret = Element.C;
		System.out.println("ordinal: "+ret.ordinal());

		/*Integer[] arr = {4,5,6,7,0,1,2};
		
		Scanner in = new Scanner(System.in);
		int v = in.nextInt();
		System.out.println(">>>Result:: "+binarySearch(arr, 0, arr.length-1, v));*/
		
		
//		Integer[] arr2 = {4,5,6,7,8,1,2,3,4};
		Integer[] arr2 = {4,5,6,7,0,1,2};
		System.out.println("Min is: "+findMin(arr2, 0, arr2.length-1)); //--working
		System.out.println("Max is: "+findMax(arr2, 0, arr2.length-1)); //--working
		
	}

	private static int binarySearch(Integer[] arr, int low, int high, int v) {

		if(arr == null) { 
			return -1;
		}
		/*if(low == high && arr[low] != v) {
			System.out.println(">>>Element not found");
			return;
		}*/
		
		if(low >= high && v != arr[low]) {
			return -1;
		}
		
		int mid = (low+high)/2;
		
		//if(arr[low] <= v) {
		if(v >= arr[low]) {  //-- if(1 >= 4)
			return binarySearch(arr, low, mid, v);
		}
		
		//if(arr[low] > v) {
		if(v < arr[low]) {
			return binarySearch(arr, mid+1, high, v);
		}
		
		if(v == arr[mid]) {
			System.out.println(">>>Element found at "+ mid);
			low = high+1;
			return mid;
		} else if(v != arr[mid]) {
			System.out.println(">>>Element not found");
		}
		return -1;
	}

	
	
	//--Find min and max in a rotated sorted arr
	private static int findMin (Integer[] arr, int low, int high) {
		
		if(low > high) return Integer.MIN_VALUE;
		
		int mid = (low+high)/2;
		
		//--if mid is the minimum
		if(arr[mid] < arr [mid+1] && arr[mid] < arr[mid-1]) {
			return arr[mid];
		}
		
		//--Min is on right side of array
		if(arr[mid] < arr[mid-1] && arr[mid] > arr[mid+1]) {
			return findMin (arr, mid+1, high);
			
		} 
		
		//--min is left side of array
		if(arr[mid] > arr[mid-1] && arr[mid] < arr[mid+1]) {
			return findMin (arr, low, mid-1);
		}
		
		//--mid is bigger than mid-1 and mid+1
		
		if(arr[mid] > arr[mid-1] && arr[mid] > arr[mid+1]) {
			
			if(arr[mid+1] > arr[mid-1]) { //-recurse left
				return findMin (arr, low, mid-1);
				
			} else {
				//--recurse right 
				return findMin (arr, mid+1, high);
			}
		}
		
		return Integer.MIN_VALUE;
		
	}
	
	//--Find min and max in a rotated sorted arr
	private static int findMax (Integer[] arr, int low, int high) {
		
		if(low > high) return Integer.MIN_VALUE;
		
		int mid = (low+high)/2;
		
		//--if mid is the max
		if(arr[mid] > arr [mid+1] && arr[mid] > arr[mid-1]) {
			return arr[mid];
		}
		
		//--Max is on right side of array
		if(arr[mid] > arr[mid-1] && arr[mid] <= arr[mid+1]) {
			return findMax (arr, mid+1, high);
			
		} 
		
		//--mid is left side of array
		if(arr[mid] <= arr[mid-1] && arr[mid] > arr[mid+1]) {
			return findMax (arr, low, mid-1);
		}
		
		//--mid is smaller than mid-1 and mid+1
		
		if(arr[mid] < arr[mid-1] && arr[mid] < arr[mid+1]) {
			
			if(arr[mid+1] > arr[mid-1]) { //-recurse right
				
				return findMin (arr, mid+1, high);
				
			} else {
				//--recurse left
				return findMax (arr, low, mid-1);
			}
		}
		
		return Integer.MIN_VALUE;
		
	}

}
