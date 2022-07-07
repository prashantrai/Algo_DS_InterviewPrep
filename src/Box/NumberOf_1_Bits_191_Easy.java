package Box;

import java.math.BigInteger;

public class NumberOf_1_Bits_191_Easy {

	public static void main(String[] args) {
		int n = 00000000000000000000000000001011;
		System.out.println("Expected: 3, Actual: " + hammingWeight(n));
		System.out.println("with Java inbuild fun: Expected: 3, Actual: " + hammingWeight2(n)); // with Java inbuild fun
		System.out.println("3: Expected: 3, Actual: " + hammingWeight3(n));
		
		n = 00000000000000000000000010000000;
		System.out.println("Expected: 1, Actual: " + hammingWeight(n));
		System.out.println("with Java inbuild fun: Expected: 1, Actual: " + hammingWeight2(n)); // with Java inbuild fun
		System.out.println("3: Expected: 1, Actual: " + hammingWeight3(n));
		
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
	
	
	/**
	 * Loop and flip
	 * We check each of the 32 bits of the number. If the bit is 1, we add one to the number of 1-bits.
	 * 
	 * How? 
	 * 1. We'll use a bit mask and start with 1 whose binary is 00000000000000000000000000000001
	 * 2. Clearly, a logical AND between any number and the mask 1 gives us the least significant bit 
	 *    of this number. If the result is '1' (e.g. 1&1 = 1) then add the count.
	 * 3. To check the next bit, we shift the mask to the left by one. That's now after left shift mask
	 *    will be 00000000000000000000000000000010 and this will add with 2nd element in the number.
	 * 4. Repeat 
	 * 
	 * 
	 */
	
	public static int hammingWeight3(int n) {
	    int bits = 0;
	    int mask = 1;
	    for (int i = 0; i < 32; i++) {
	        if ((n & mask) != 0) {
	            bits++;
	        }
	        mask <<= 1;
	    }
	    return bits;
	}
	
}
