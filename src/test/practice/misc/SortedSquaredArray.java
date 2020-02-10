package test.practice.misc;

import java.util.Arrays;

public class SortedSquaredArray {

	public static void main(String[] args) {

		int[] a = {-4,-1,0,3,10};
		//--Exepected Result: 0,1,9,16,100
		//System.out.println(Arrays.toString(sortedSquares(a)));
		
		int[] a1 = {-7,-3,2,3,11};
		//--Exepected Result: [4,9,9,49,121]
		System.out.println(Arrays.toString(sortedSquares(a1)));
	}
	
	//--https://www.youtube.com/watch?v=4eWKHLSRHPY&t=40s
	//--https://leetcode.com/problems/squares-of-a-sorted-array/
	
	public static int[] sortedSquares(int[] A) {
        
        int[] output = new int[A.length];
        
        int left = 0;
        int right = A.length - 1;
        //for(int i=A.length-1; i>=0; i++) {
        int i=A.length - 1;    
        while(left <= right && i>=0) {    
            int temp_left = A[left] * A[left];
            int temp_right = A[right] * A[right];
            if(temp_left > temp_right) {
                output[i--] = temp_left;
                left++;
            } else if (temp_left < temp_right){
            	output[i--] = temp_right;
                right--;
            } else { //--equal
            	output[i--] = temp_right;
            	right--;
            }
        }
            
        return output;
    }
}
