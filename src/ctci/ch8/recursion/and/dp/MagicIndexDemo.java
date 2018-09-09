package ctci.ch8.recursion.and.dp;

public class MagicIndexDemo {

	public static void main(String[] args) {
		
		int arr[] = {-10,-5,2,2,2,3,4,7,9,12,13};

		System.out.println(">>> "+magicFast(arr));
		
	}
	
	public static int magicFast(int[] arr) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		return magicFast(arr, 0, arr.length);
	}

	public static int magicFast(int[] arr, int left, int right) {
		
		if(left > right) {
			return -1;
		}
		
		int mid = (left+right)/2;
		if(mid == arr[mid]) {
			return mid;
		}
		
		int leftIndex = Math.min(mid-1, arr[mid]);
		left = magicFast(arr, left, leftIndex);
		if(left > 0) {
			//System.out.println(">>Left: "+left);
			return left;
		}
		
		int rightIndex = Math.max(mid+1, arr[mid]);
		right = magicFast(arr, rightIndex, right);
		
		return right;
		
	}
	
}
