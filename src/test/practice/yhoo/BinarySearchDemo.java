package test.practice.yhoo;

public class BinarySearchDemo {

	public static void main(String[] args) {
		
		int[] intArr = {-5, -1, 1, 2, 5, 12, 16, 20};
		int value = -5;

		//--Iterative implementation
		//binarySearch(intArr, value);
		//binarySrchITR(intArr, value);1
		
		//binarySearchREC(intArr, value, 0, intArr.length);
		//binarySrchREC_2(intArr, 0, intArr.length, value);
		System.out.println(">>> "+binarySerachREC_3(intArr, 0, intArr.length-1, value));
		
	}
	
	
	public static boolean binarySerachREC_3 (int[] arr, int low, int high, int v) {
		
		if(low > high) {
			return false;
		}
		
		int mid = (low+high)/2;
		
		if(v < arr[mid]) {
			return binarySerachREC_3(arr, low, mid-1, v);
		}
		if(v > arr[mid]) {
			return binarySerachREC_3(arr, mid+1, high, v);
		}
		if(arr[mid] == v) {
			return true;
		}
		return false;
	}
	
	
	
	
	public static void binarySrchREC_2(int[] arr, int start, int end, int v) {
		
		int low = start;
		int high = end;
		int mid = 0;
		
		if(low > high) return;
		
		mid = (low+high)/2; 
		
		if(arr[mid] > v) { 
			binarySrchREC_2(arr, low, mid-1, v);
		}
		else if(arr[mid] < v) { 
			binarySrchREC_2(arr, mid+1, high, v);
		} 
		else if(arr[mid] == v) {
			System.out.println("Found");
			low = high+1;
		}else if(mid < arr.length && arr[mid] != v) {
			System.out.println("Not Found");
			//low = high+1;
		}
		
	}
	
	public static void binarySrchITR(int[] arr, int v) {
		
		int low=0;
		int high = arr.length;
		int mid = 0;
				
		while(low <= high) {
			mid = (low+high)/2;
			
			if(mid == arr.length) break; 
			if(arr[mid] > v) {
				high = mid-1;
				
			} else if(arr[mid] < v) {
				low = mid+1;
				
			}
			if(arr[mid] == v) {
				System.out.println("Found");
				low = high+1;
				return;
			}
		}
		
		//if(arr[mid] != v) {
			System.out.println("Not Found");
		//}

		
		
	}
	
	public static void binarySearchREC(int[] arr, int v, int start, int end) {
		
		int low = start;
		int high = end;
		int mid = 0;
		
		if(low > high) return;
				
		mid = (low+high)/2;
		
		if(arr[mid] > v) {
			binarySearchREC(arr, v, low, mid-1);
		} else if(arr[mid] < v) {
			binarySearchREC(arr, v, mid+1, high);
		} else if(arr[mid] == v) {
			System.out.println("Element found at " + mid);
			low = high+1;
		}
		
		
	}
	
	public static void binarySearch(int[] arr, int v) {
		
		int low = 0;
		int high = arr.length;
		int mid=0;
		
		while (low <= high) {
			
			mid = (low + high)/2 ;
			if(arr[mid] > v) {
				high = mid-1;
				
			} else if(arr[mid] < v) {
				low = mid + 1;
				
			} else if(arr[mid] == v) {
				System.out.println("Element found at " + mid);
				low = high+1; //trmination of loop
			}
		}
		
		if(arr[mid] != v) {
			System.out.println("Not found");
		}
	}
	
	

}
