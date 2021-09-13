package microsoft;

import java.io.IOException;
import java.util.*;

public class FairIndexes_OA_Question {

	/**
	You are given two arrays A and B consisting of N integers each.
	
	Index K is named fair if the four sums (A[0] + ... + A[K-1]), (A[k] + ... + A[N-1]), (B[0] + ... + B[k-1]) and (B[K] + ... + B[N-1]) are all equal. In other words, K is the index where the two arrays, A and B, can be split (into two non-empty arrays each) in such a way that the sums of the resulting arrays' elements are equal.
	
	write a function:
	
	int fairIndexes(vector<int> &A, vector<int> &B);
	which, given two arrays of integers A and B, returns the number of fair indexes.
	
	Example 1:
	Input: A = [4, -1, 0, 3], B = [-2, 5, 0, 3]
	Output: 2
	Explanation:
	The fair indexes are 2 and 3. In both cases, the sums of elements the subarrays are equal to 3.
	
	For index = 2;
	
	4 + (-1) = 3; 0 + 3 = 3;
	
	-2 + 5 = 3; 0 + 3 = 3;
	
	Example 2:
	Input: A = [2, -2, -3, 3], B = [0, 0, 4, -4]
	Output: 1
	Explanation:
	The only fair index is 2. 
	* */
	
	public static void main(String[] args) {
		
		int[] A = {4, -1, 0, 3}; int[] B = {-2, 5, 0, 3};

		System.out.println("Expected: 2, Actual: " + fairIndex(A, B));
	}
	
	
	// https://algo.monster/problems/fair_indexes
    public static int fairIndex(int a[], int b[]){
        int sumA = 0;
        int sumB = 0;
        for (int i = 0; i < a.length; i++) {
            sumA += a[i];
            sumB += b[i];
        }
        int count = 0;
        int tempA = a[0];
        int tempB = b[0];
        for (int i = 1; i < a.length; i++) {
            if (i != 1 && tempA == tempB && 2 * tempA == sumA && 2 * tempB == sumB) {
                count++;
            }
            tempA += a[i];
            tempB += b[i];
        }
        return count;
    }
	

}


