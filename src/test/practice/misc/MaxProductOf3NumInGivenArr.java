package test.practice.misc;

public class MaxProductOf3NumInGivenArr {

	public static void main(String[] args) {
		
		//int[] arr = {10, 3, 5, 6, 20};
//		int[] arr = {-10, -3, -5, -6, -20};
		int[] arr = {1, -4, 3, -6, 7, 0};
		
		
		System.out.println(maxProduct(arr));
		
	}
	
	//--https://www.geeksforgeeks.org/find-maximum-product-of-a-triplet-in-array/
	
	private static long maxProduct(int[] arr) {
		
		if(arr == null || arr.length < 0) return -1;
		
		int max = Integer.MIN_VALUE, mid = Integer.MIN_VALUE, min = Integer.MIN_VALUE;
		
		int minA = Integer.MAX_VALUE, minB = Integer.MAX_VALUE;
		
		for(int i=0; i<arr.length; i++) {
			
			if(max < arr[i]) {
				min = mid;
				mid = max;
				max = arr[i];
			} else if(mid < arr[i]) {
				min = mid;
				mid = arr[i];
			} else if(min < arr[i]) {
				min = arr[i];
			} 
			
			if(arr[i] < minA) {
				minB = minA;
				minA = arr[i];
			} else if(arr[i] < minB) {
				minB = arr[i];
			}
		}
		
		System.out.println("max="+max + ", mid="+mid +", min="+min);
		System.out.println("minA="+minA + ", minB="+minB);
		
		/** Return the maximum of product of Maximum, second maximum and third maximum 
		 * and product of Minimum, second minimum and Maximum element. */
		
		return Math.max(max*min*mid, max*minA*minB);
		
	}
	
	

}
