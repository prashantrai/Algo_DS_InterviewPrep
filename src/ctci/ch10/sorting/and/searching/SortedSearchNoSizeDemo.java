package ctci.ch10.sorting.and.searching;

import java.awt.List;

public class SortedSearchNoSizeDemo {

	public static void main(String[] args) {

		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = 20;
		
		//int len = getLength(intArr, 1, 0);
		//System.out.println("aar.lenght="+intArr.length+", len="+len);
		
		search(intArr, value);
	}
	
	public static void search(int[] listy, int v) {
		
		//binarySearchREC(listy, 0, listy.length-1, v);
		binarySearch(listy, 0, listy.length-1, v);  //--iterative
		
	}
	
	
	public static void binarySearch (int[] arr, int low, int high, int v) {
		
		if(arr == null) 
			return;
		
		int mid;
		
		while (low <= high) {
			mid = (low+high)/2;
			
			if(arr[mid] > v) {
				high = mid-1;
			}
			else if(arr[mid] < v) {
				low = mid + 1;
			}
			else if(arr[mid] == v) {
				System.out.println("Element found at "+mid);
				low = high+1;
				return;
			}
		}
		
		System.out.println("Element not found");
	}
	
	
	public static void binarySearchREC(int[] arr, int start, int end, int v) {
		
		if(end < start) {
			return;
		}
		
		if(start == end && v != arr[start]) {
			System.out.println("Element not found.");
			return;
		}
		
		int mid = (start+end)/2;
		
		if(v < arr[mid]) {
			binarySearch(arr, start, mid-1, v);
		} else if(v > arr[mid]) {
			binarySearch(arr, mid+1, end, v);
		} else if(v == arr[mid]) {
			System.out.println("Element found at "+mid);
			start = end+1;
			return;
		} 
		
		
	} 
	
	
	/*public static int getLength(int[] arr, int i, int j) {
		
		if(j == i) {
			return i-1; 
		}
		int ii=0;
		//int i = 1;
		int prev = j;
		try {
			
			while(true) {
				int v = arr[i-1];
				
				prev = i;
				System.out.println("prev="+prev);
				i = i == 0 ? i=1 : i*2;
				System.out.println(">>i="+i);
			}
			
		} catch(Exception e) {
			System.err.println("prev="+prev + ", i="+i);
			
			int mid = (prev+i)/2;
			
			ii = getLength(arr, mid, i);
			
		}
		return ii;
		
	}*/

}
