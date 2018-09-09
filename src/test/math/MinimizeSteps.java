package test.math;

import java.util.Arrays;
import java.util.Scanner;

public class MinimizeSteps {

	
	/** [DP problem with memoization]
	 * http://www.geeksforgeeks.org/minimum-steps-minimize-n-per-given-condition/
	
	Given a number n, count minimum steps to minimize it to 1 according to the following criteria:

	If n is divisible by 2 then we may reduce n to n/2.
	If n is divisible by 3 then you may reduce n to n/3.
	Decrement n by 1.
	
	Examples:
	
	Input : n = 10
	Output : 3
	
	Input : 6
	Output : 2
	*/
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("RESULT: " + getMinimisedSteps(in.nextInt()));
	}
	
	public static int getMinimisedSteps(int n) {
			
		int[] memo = new int[n+1];
		Arrays.fill(memo, -1);
		
		return getMinimisedSteps(n, memo);
	}

	private static int getMinimisedSteps(int n, int[] memo) {
		
		if(n == 1) 
			return 0;
		
		if(memo[n] != -1) {
			return memo[n];
		}
		
		int res = getMinimisedSteps(n-1, memo);   
		
		if(n%2 == 0) {
			res = Math.min (res, getMinimisedSteps(n/2, memo)); 
		}
		if(n%3 == 0) {
			res = Math.min (res, getMinimisedSteps(n/3, memo)); 
		}
		
		memo[n] = 1+res;
		
		return memo[n];
	}
	

}
