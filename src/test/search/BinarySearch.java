package test.search;

public class BinarySearch {

	public static void main(String[] args) {
		
		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = 21;

		//--Iterative implementation
		//binarySearch(intArr, value);
		
		binarySearchREC(intArr, value, 0, intArr.length-1);
		
	}

	private static void binarySearch(int[] arr, int value) {
		
		int low = 0;
		int high = arr.length-1;
		int mid = 0;
		
		while (low <= high) {
			
			System.out.println("mid="+mid+", low="+low+", high="+high);
			
			mid = (low + high)/2;
			
			if(value < arr[mid]) {
				high = mid-1;
				
			} else if (value > arr[mid]) {
				low = mid+1;
				
			} else if(value == arr[mid]) {
				System.out.println("Value found at " + mid);
				//return;
				low=high+1;
			}
			
		}
		if(arr[mid] != value)
			System.out.println("Element not found...");
		
	}
	
	
	private static void binarySearchREC(int[] arr, int value, int low, int high) {
		
		int mid = (low+high)/2;
		
		if(low == high && arr[low] != value){
			System.out.println("Element not found...");
			return;
		}
		
		if(value < arr[mid]) {
			high = mid-1;
			binarySearchREC(arr, value, low, high);
			
		} else if(value > arr[mid]) {
			low = mid+1;
			binarySearchREC(arr, value, low, high);
			
		} else if (value == arr[mid]) {
			System.out.println("Element found at "+mid);
			low=high+1;
			return;
		}
		
	}
	
}
