package Lyft;

public class MagicIndexDemo {

	public static void main(String[] args) {
		
		int arr[] = {-10,-5,2,2,2,3,4,7,9,12,13};

		System.out.println(">>> "+magicFast(arr));
		
	}

	private static int magicFast(int[] arr) {

		if(arr == null) 
			return -1;
		
		int idx = magicFast(arr, 0, arr.length-1);
		
		return idx;
	}

	private static int magicFast(int[] arr, int start, int end) {

		if(start > end) 
			return -1;
		
		int mid = (start+end)/2;
		
		if(arr[mid] == mid) 
			return mid;
		
		int leftIndex = Math.min(arr[mid], mid-1);
		int left = magicFast(arr, start, leftIndex);
		if(left > 0) {
			return left;
		}
		
		int rightIndex = Math.max(arr[mid], mid+1);
		int right = magicFast(arr, rightIndex, end);
		
		return right;
	}
	
	
	
}
