package ctci.ch6.math.and.puzzles;

import java.util.Arrays;
import java.util.Scanner;

public class PrimeNumberDemo {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int max = in.nextInt();
		sieveOfEratosthenes(max);
	}
	
	//--Sieve of Eratosthenes
	public static void sieveOfEratosthenes (int max) {
		
		Boolean[] flags = new Boolean[max+1];
		init(flags);
		
		//System.out.println(">> "+Arrays.deepToString(flags));
		
		int prime = 2;
		
		while(prime <= Math.sqrt(max)) {
			
			crossNonPrimes(flags, prime);
			prime = getNextPrime(flags, prime);
			
		}
		
		for(int i=1; i<flags.length; i++) {
			if(flags[i]) {
				System.out.print(i+" ");
			}
		}
		
	}
	
	private static void crossNonPrimes(Boolean[] flags, int prime) {
		
		for(int i=prime*prime; i<flags.length; i+=prime) {
			flags[i] = false;
		}
		
	}
	
	private static int getNextPrime(Boolean[] flags, int prime) {
		
		int next = prime+1;
		while(next < flags.length && !flags[next]) {
			next++;
		}
		return next;
	}
	
	private static void init(Boolean[] flags) {
		Arrays.fill(flags, true);
	}
}
