package Box;

import java.math.BigInteger;

public class NumberOf_1_Bits_191_Easy {

	public static void main(String[] args) {
		int n = 00000000000000000000000000001011;
		System.out.println("Expected: 3, Actual: " + hammingWeight(n));
		System.out.println("with Java inbuild fun: Expected: 3, Actual: " + hammingWeight2(n)); // with Java inbuild fun
		
		n = 00000000000000000000000010000000;
		System.out.println("Expected: 1, Actual: " + hammingWeight(n));
		System.out.println("with Java inbuild fun: Expected: 1, Actual: " + hammingWeight2(n)); // with Java inbuild fun
		
//		long n2 = 11111111111111111111111111111101L;
		//System.out.println("Expected: 1, Actual: " + hammingWeight(n));
		//System.out.println("with Java inbuild fun: Expected: 1, Actual: " + hammingWeight2(n)); // with Java inbuild fun
		
		
	}

	/**
	 * Refer comment section in below page:
	 * https://leetcode.com/problems/number-of-1-bits/discuss/2074108/Daily-LeetCoding-Challenge-May-Day-26
	 * 
	 * Imagine you are a binary number n (32 bits) each time you go through this
	 * operation : n & (n-1) your rightmost set bit will become 0 . 
	 * 
	 * This pattern of n & n-1 was first observed by Brian Kernighan,coauthor of first C language.
	 * 
	 * Book Example : 
	 * 		n = 01011100 
	 * 		after 1st operation : n=01011000 
	 *      after 2nd operation : n=01010000 
	 *      after 3rd operation : n=01000000 
	 *      after 4th operation : n=00000000 
	 *      
	 *      Now the number of operations it take to make your number
	 *  	completely 0 is our answer. In this case 4 .
	 *  
	 *  
	 *  Time: O(1), the run time depends on the number of 11-bits in n. 
	 *  		In the worst case, all bits in n are 1-bits. In case of a 32-bit integer, 
	 *  		the run time is O(1).
	 *  
	 *  Space: The space complexity is O(1), since no additional space is allocated.  
	 */

	public static int hammingWeight(int n) {
        int ans = 0;
        
        while(n != 0) {
        	n = n&(n-1);
        	ans++;
        }
        return ans;
    }
	
	// with Java inbuild function
	public static int hammingWeight2(int n) {
		return Integer.bitCount(n);
	}
	
	
}
