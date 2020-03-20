package test.practice.misc;

public class RotationCount {

	public static void main(String[] args) {
			
		//int[] arr = {8, 9, 10, 2, 5, 6};
		int[] arr = {6, 8, 9, 10, 2, 5};
		int count = getRotationCount(arr, 0, arr.length-1);
		System.out.println("Rotation count:: "+count);
		findRotationPoint(arr);
	}
	//--http://techieme.in/find-the-point-of-rotation-in-sorted-array/
	public static int getRotationCount(int[] arr, int start, int end) {
		
		if(arr[start] < arr[end]) return start;
		
		int mid = start + (end-start)/2;
		int last = arr[end];
		while (start <= end) {
			
			if(arr[mid] > last) { //--search right
				start = mid+1;
				
			} else if(arr[mid] < last) {
				end = mid-1;
			} else 
				break;
			
			//int test_mid = (start+end)/2;
			mid = start + (end-start)/2;
			//mid = (start+end)/2;  //--we can't use this as this will change the index, e.g. in case when start=4 and end=3 and for this case 'mid = (start+end)/2' will give us mid=3 but 'start + (end-start)/2' will give mid=4 which is the right result.
			
			//System.out.println("test_mid="+test_mid+", mid="+mid);
		}
		return mid;
	}

	
	public static void findRotationPoint(int[] A) {
		int start = 0, end = A.length - 1, mid;
		mid = start + (end - start) / 2;
		int last = A[A.length - 1];
		while (start <= end) {
			if (A[mid] > last) {
				start = mid + 1;
			} else if (A[mid] < last) {
				end = mid - 1;
			} else
				break;
			mid = start + (end - start) / 2;
		}
		//System.out.println(A[mid]);
		System.out.println(mid);
		
	}
}
