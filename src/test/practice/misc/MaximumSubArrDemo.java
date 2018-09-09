package test.practice.misc;

public class MaximumSubArrDemo {

	public static void main(String[] args) {

		int[] arr = {4, -3, -2, 2, 3, 1, -2, -3, 4, 2, -6, -3, -1, 3, 1, 2};
		findMaxSubArr(arr);
	}
	
	//--Kadane's Algorithm. Runtime: O(n)
	public static void findMaxSubArr (int[] arr) {
		
		int max_so_far = arr[0];
		int max_end_here = 0;
		int start = 0;
		int end = 0;
		int s = 0;
		
		for(int i=0; i< arr.length; i++) {
			
			max_end_here += arr[i];
			
			if(max_end_here > max_so_far) {
				max_so_far = max_end_here;
				start = s;
				end = i;
				
			} else if (max_end_here < 0) {
				max_end_here = 0;
				s = i+1;
			}
		}
		
		System.out.println("Result:: start="+start+", end="+end);
	}

}
