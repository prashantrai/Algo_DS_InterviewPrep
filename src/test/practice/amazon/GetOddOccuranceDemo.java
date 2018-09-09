package test.practice.amazon;

import java.util.Arrays;


/**
 	Given an array of positive integers. All numbers occur even number of times 
 	except one number which occurs odd number of times. Find the number in O(n) time & constant space.

	Example:
	I/P = [1, 2, 3, 2, 3, 1, 3]
	O/P = 3 
 * */

public class GetOddOccuranceDemo {

	public static void main(String[] args) {

//		int [] arr = {1,2,3,2,3,1,3};
		//int [] arr = {1,1,2,2,3,3,3,4};
		int[] arr = {2, 3, 5, 4, 5, 2, 4, 3, 5, 2, 4, 4, 2};
		
		//getOddOccurance(arr);
		//--Calling metod with XOR implemenation 
		getOddOccurrence_2(arr);
		
	}
	
	//--This is going N(LogN) coz of sorting
	public static void getOddOccurance(int[] arr) {
		Arrays.sort(arr);
		System.out.println("arr:: "+ Arrays.toString(arr));
		
		int count = 1;
		int i;
		for(i=1; i<arr.length; i++) {
			
			if(arr[i] != arr[i-1]) {
				if(count%2 != 0) {
					break;
				}
				count = 1; ///resetting count to 1
			} else {
				count++;
			}
		}
		
		if(count%2 != 0) {
			System.out.println("Number with odd count is "+ (arr[i-1]));
			return;
		}
	
	}
	
	//-- Time Complexity: O(n), Space Complexity:  O(1)
	//--Using XOR :: http://www.geeksforgeeks.org/find-the-number-occurring-odd-number-of-times/
	public static int getOddOccurrence_2 (int ar[]) 
    {
        int i;
        int res = 0;
        for (i = 0; i < ar.length; i++) 
        {
        	/*if the occrance is even for any number XOR will be ZERO for that, 
        	 * that's the reason a number with odd count left at the end which is the result
        	 * 
        	 * Example:  In array {1,1,2,2,3,3,3} (taken here in sorted order just for explaination)
        	 *           we 3 has odd count for first two XOR will be zero as they are even and for the last one condition will be
        	 *           0^3 which results 3 as XOR of zero with any number will be that number 	
        	 * */
        	
        	res = res ^ ar[i];  
        }
        System.out.println("res="+res);
        return res;
    }

}
