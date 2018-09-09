package test.practice.ebay;

public class BinarySearchDemo {

	public static void main(String[] args) {
		
		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = -5;

		//--Iterative implementation
		binarySearch(intArr, value);
		
		binarySearchREC(intArr, value, 0, intArr.length-1);
		
	}

	private static void binarySearchREC(int[] intArr, int value, int start, int end) {

		int mid = (start + end) / 2;
 		
		if(start == end && intArr[start] != value) {
			System.err.println(">>Not found");
			return;
		}
		
		if(value < intArr[mid]) {
			binarySearchREC(intArr, value, start, mid-1);
			
		} else if(value > intArr[mid] ){
			binarySearchREC(intArr, value, mid+1, end);
			
		} else if(intArr[mid] == value) {
			System.out.println(">>found");
			start = end + 1;
			return;
		} 
	
	}
	
	private static void binarySearch(int[] arr, int value) {

		int low = 0;
		int high = arr.length-1;
		
		int mid;
		
		while (low <= high) {
			mid = (low+high)/2;
			
			if(value < arr[mid]) {
				high = mid-1;
			} else if(value > arr[mid]) {
				low=mid+1;
			} else if(arr[mid] == value) {
				System.out.println(">> Found");
				low = high + 1;
				return;
			}
			
		}
		
		System.err.println("Not found");
	}
	
	
	

}
