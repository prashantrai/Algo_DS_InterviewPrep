package ctci.ch8.recursion.and.dp;

import java.util.Arrays;

public class TripleStepDemo {

	public static void main(String[] args) {
		
		int n = 5;
		System.out.println(">>>Result:: "+ countWays(n));
	}
	
	public static int countWays(int n) {
		Integer[] memo = new Integer[n+1];
		Arrays.fill(memo, -1);
		return countWays(n, memo);
	}
	
	public static int countWays(int n, Integer[] memo) {
		if(n < 0) {
			return 0;
		}
		
		if(n == 0) {
			return 1;
		}
		
		if(memo[n] == -1) {
			memo[n] =  countWays(n-1, memo) + countWays(n-2, memo) + countWays (n-3, memo);
		}
		
		return memo[n];
	}
	
	

}
