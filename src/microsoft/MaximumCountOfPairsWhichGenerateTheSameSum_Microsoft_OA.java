package microsoft;

import java.util.Arrays;

public class MaximumCountOfPairsWhichGenerateTheSameSum_Microsoft_OA {

	public static void main(String[] args) {
		int arr[] = { 1, 8, 3, 11, 4, 9, 2, 7 };
	    System.out.println("Expected: 3, Actual: " + maxCountSameSUM(arr));

	    int arr_2[] = {1, 2, 3, 4};
	    System.out.println("Expected: 2, Actual: " + maxCountSameSUM(arr_2));
	}

	/* https://www.geeksforgeeks.org/maximum-count-of-pairs-which-generate-the-same-sum/
	 * 
	 * 1. Find the max value in the input array
	 * 2. HashTable/Freq arr : Declare an array to track the frequency
	 * 			a. add 2 values i and i+1 and increment and store the count 
	 * 				at the index equals to sum/result (i.e. arr[i] + arr[i+1])
	 * 
	 * 3. Iterate the frequency array and return the max count
	 * */
	
	private static int maxCountSameSUM(int[] arr) {
		if(arr == null || arr.length == 0) return 0;
		
		int max = Max(arr);
		
		// Create a map to store frequency
		//int[] freqMap = new int[max * 2]; // works for used input
		int[] freqMap = new int[max * 2 + 1];
		Arrays.fill(freqMap, 0);
		
		int maxCount = 0;
		for(int i=0; i<arr.length-1; i++) {
			for(int j=i+1; j<arr.length; j++) {
				int idx = arr[i] + arr[j]; 
				freqMap[idx] += 1;
				maxCount = Math.max(maxCount, freqMap[idx]);
			}
		}
		
		return maxCount;
	}
	
	private static int Max(int[] arr) {
		int max = arr[0];
		for(int n : arr) {
			max = Math.max(max, n);
		}
		return max;
	}

}
