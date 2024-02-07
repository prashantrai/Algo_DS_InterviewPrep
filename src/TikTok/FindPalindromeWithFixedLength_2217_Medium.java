package TikTok;

import java.util.Arrays;

public class FindPalindromeWithFixedLength_2217_Medium {

	public static void main(String[] args) {
		int[] queries = {1,2,3,4,5,90};
		int intLength = 3;
		long[] ans = kthPalindrome(queries, intLength);
		System.out.println("Expected: [101,111,121,131,141,999]");
		System.out.println("Actual: " + Arrays.toString(ans));
	}
	
	/*
    Reference: 
      explaination: https://www.youtube.com/watch?v=zAB3NMw53WA&ab_channel=CodingDecoded
      Code: https://github.com/Sunchit/Coding-Decoded/blob/master/Contest/FindPalindromeWithFixedLength.java
      
      Time: O(queries.length * intLength) in worst case
      Space: O(1)
    
    */
    
    public static long[] kthPalindrome(int[] queries, int intLength) {
        int n = queries.length;
        long[] ans = new long[n];
        int i=0;
        
        // +1 here to deal with odd lenght number too alongwith even
        int halfPalindromLength = (intLength + 1)/2;
        
        /*
        e.g. for 4 digit palindrom halfPalindromLenght will be 2 and 2 digit range is 
        10 to 99. 
        We take these numbers and append the reverse of these to generate a 
        4 digit palindrom. 
        Like, 10 and it's reverse 01 will give us 1001
        same for 11 -> 1111, 12-> 1221 etc.
        
        Formulae for Left and Right bound: 
            leftBound = 10^(halfPalindromLength-1)
            rightBound = (10^halfPalindromLength)-1
        
        */
        // this will return 10 for intLenght=4
        long leftBound = (long) Math.pow(10, halfPalindromLength-1); 
        // this will return 99 for intLenght=4
        long rightBound = (long) Math.pow(10, halfPalindromLength)-1; 
        
        for (int q :  queries) {
            // Check if generated palindorm are in range
            // if queries[ith] is within the range of 
			// r-l+1 because 10 to 99 we have all the palindrome (in total we have 99-10 +1)
            long totalCount = rightBound - leftBound + 1;
            
            if(q <= totalCount) {
                
                //first half is the minimum value in range (which is leftBound) + query number -1
				//-1 bcz we have leftBound (10) number palindrome also
                String leftHalf = Long.toString(leftBound + q - 1);
                String rightHalf = (new StringBuilder(leftHalf).reverse()).toString();
                
                //now for intLength 4 we have (1001) and 3 we have (1001)
				//we don't need middle value (right half 0 index) if intLength is odd
                ans[i] = Long.parseLong(leftHalf + rightHalf.substring(intLength%2) );
                
            } else {
                ans[i] = -1;
            }
            i++;
        }
        return ans;        
    }

}
