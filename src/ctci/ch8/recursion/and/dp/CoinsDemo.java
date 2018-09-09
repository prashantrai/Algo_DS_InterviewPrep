package ctci.ch8.recursion.and.dp;

import com.sun.corba.se.spi.ior.MakeImmutable;

public class CoinsDemo {

	/*
	 * Coins: Given an infinite number of quarters (25 cents), dimes (1 O cents), nickels (5 cents), and 
	 * pennies (1 cent), write code to calculate the number of ways of representing n cents.
	 */ 
	
	public static void main(String[] args) {

		System.out.println(makeChange(10));
	}
	
	public static int makeChange(int n) {
		
		int[] denoms = {25, 10, 5, 1};
		int[][] map = new int[n+1][denoms.length];
		
		return makeChange(n, denoms, 0, map);
	}

	private static int makeChange(int amount, int[] denoms, int index, int[][] map) {

		if(map[amount][index] > 0) {
			return map[amount][index];
		}
		
		if(index >= denoms.length-1) {
			return 1;
		}
		
		int denomAmt = denoms[index];
		int ways = 0;
		
		for(int i=0; i*denomAmt <= amount; i++) {  //while amount is less than denom
			int remainingAmt = amount - i*denomAmt;
			ways += makeChange(remainingAmt, denoms, index+1, map);
		}
		
		map[amount][index] = ways;
		
		
		return ways;
	}

}
