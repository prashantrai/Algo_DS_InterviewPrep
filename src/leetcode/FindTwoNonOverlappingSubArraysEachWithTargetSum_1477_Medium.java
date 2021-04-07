package leetcode;

import java.util.Arrays;

public class FindTwoNonOverlappingSubArraysEachWithTargetSum_1477_Medium {

	public static void main(String[] args) {

		int[] arr = {3,1,1,1,5,1,2,1};
		int target = 3;
		
		int res = minSumOfLengths(arr, target);
		
		System.out.println("Expected: 3, Actual: " + res);
	}
	
	
	 static   int solution(int[] A) {
	        int ans = 0;
	        for (int i = 0; i < A.length; i++) {
	            if (ans > A[i]) {
	                ans = A[i];
	            }
	        }
	        return ans;
	    }
	
	
	/* https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/discuss/686105/JAVA-or-Sliding-window-with-only-one-array-or-No-HasMap
	 * for some expalination about how we have used the arr 'best' watch (not much but enough to get an idea)
	 * 	https://www.youtube.com/watch?v=63K9MYDfEEc
	 * 
	 * 
	 * Time and Space : O(N)
	 * */
	public static int minSumOfLengths(int[] arr, int target) {
		int n = arr.length;
		int[] best = new int[n];
		Arrays.fill(best, Integer.MAX_VALUE);
		
		int sum = 0; // current sum
		int start = 0; // start pointer to hold the starting point of sliding window approach
		int ans = Integer.MAX_VALUE; // final result i.e. sum of 2 minimum non-overlapping sub arrays
		int bestSoFar = Integer.MAX_VALUE; //	minimum sub-array length found so far
		
		for(int i=0; i<n; i++) {
			sum += arr[i];
			
			while(sum > target) { 
				sum -= arr[start]; //	move the pointer by one index and update the current sum
				start++;
			}
			
			if(sum == target) {
				
				if(start > 0 && best[start-1] != Integer.MAX_VALUE) {
					/*  best[start-1] : last best value 
					 *  i-start+1 : current best value i.e. lenght of the sub array
					 * */
					ans = Math.min(ans, best[start-1] + i-start+1);
				}
				
				/* i-start+1 here is length of sub array e.g. if we start is at index=1 and i/currenIndex is 
				 * at index=4 and then the length of sub array is i-start+1 = 4-1+1 = 4 */
				bestSoFar = Math.min(bestSoFar, i-start+1); //  
			}
			best[i] = bestSoFar; // update the current index with bestSoFar, this will be updated in every iteration (even when sum != target)
		}
		
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

}
