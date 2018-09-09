package test.practice.leetcode;

import java.util.Scanner;

public class SearchInRotatedSortedArr {

	public static void main(String[] args) {

		/*Integer[] arr = {4,5,6,7,0,1,2};
		
		Scanner in = new Scanner(System.in);
		int v = in.nextInt();
		System.out.println(">>>Result:: "+binarySearch(arr, 0, arr.length-1, v));*/
		
		
//		Integer[] arr2 = {4,5,6,7,8,1,2,3,4};
		Integer[] arr2 = {5,6,7,0,1,2,3,4};
//		Integer[] arr2 = {2,3,4,5,6,7,8,1};
//		Integer[] arr2 = {4,5,6,7,1,1,1,3,4};
		System.out.println("Min is: "+findMin(arr2, 0, arr2.length-1)); //--working
		//System.out.println("Max is: "+findMax(arr2, 0, arr2.length-1)); //--working
		
		System.out.println("Min is: "+findMin2(arr2, 0, arr2.length-1));
		
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

	
	
	private static int findMin2 (Integer[] arr, int low, int high) {
		
		if(low == high) {
			return arr[low];
		}
		
		if(high - low == 1) {
			return Math.min(arr[low], arr[high]);
		}
		
		int mid = low + (high - low)/2;
		
		if(arr[low] < arr[high]) { //--not rotated
			return arr[low];
		
		} else if(arr[mid] > arr[low]) {
			return findMin2(arr, mid, high);  //--recurse right
		} else {
			return findMin2(arr, low, mid);
		}
		
	}
	
	//--Find min and max in a rotated sorted arr  -- This solution too complex, see findMin2
	private static int findMin (Integer[] arr, int low, int high) {
		
		if(low > high) return Integer.MIN_VALUE;
		
		if(low == high) {   
			return arr[low];
		}
		
		int mid = low + (high-low)/2;
		
		
		//--dulpicate elements
		/*if(arr[mid] == arr [mid+1] || arr[mid] == arr[mid-1]) {
			int left = mid-1, right = mid+1;
			while(arr[mid] == arr [mid+1] && right < high) {
				right++;
			}
			while(arr[mid] == arr [mid-1] && left > low) {
				left--;
			}
			
			if(arr[left] > arr[right]) {
				return findMin (arr, right, high);
			} else {
				return findMin (arr, low, left);
			}
		}*/
		
		//--if mid is the minimum
		if(arr[mid] < arr [mid+1] && arr[mid] < arr[mid-1]) {
			return arr[mid];
		}
		
		//{2,3,4,5,6,7,8,1}
		boolean isDriectionLeft = false;
		if(arr[low] < arr[high]) {
			isDriectionLeft = true;
		}
		
		//--Min is on right side of array
		if(arr[mid] < arr[mid-1] && arr[mid] > arr[mid+1]) {
			return findMin (arr, mid+1, high);
			
		} 
		
		//--min is left side of array
		if(arr[mid] > arr[mid-1] && arr[mid] < arr[mid+1]) {
			if(!isDriectionLeft) {
				return findMin (arr, mid+1, high);
			} else {
				return findMin (arr, low, mid-1);
			}
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
	
	//--Find min and max in a rotated sorted arr -- This solution too complex
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
