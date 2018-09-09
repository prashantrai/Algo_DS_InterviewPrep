package test.practice.ebay;

public class MagicIndexDemo {

	public static void main(String[] args) {
		
		int arr[] = {-10,-5,2,2,2,3,4,7,9,12,13};

		System.out.println(">>> "+magicFast(arr));
		
	}
	
	public static int magicFast(int[] arr) {
		
		if(arr == null) return -1;
		
		return magicFast(arr, 0, arr.length-1);
	}
	
	public static int magicFast(int[] arr, int start, int end) {
		
		if(start > end) return -1;
		
		int mid = (start+end)/2;
		
		if(arr[mid] == mid) return mid;
		
		int leftIndex = Math.min(mid-1, arr[mid]);
		int left = magicFast(arr, start, leftIndex);
		if(left >= 0) {
			return left;
		}
		
		int rightIndex = Math.max(mid+1, arr[mid]);
		int right = magicFast(arr, mid+1, rightIndex);
		return right;
		
	} 

}
