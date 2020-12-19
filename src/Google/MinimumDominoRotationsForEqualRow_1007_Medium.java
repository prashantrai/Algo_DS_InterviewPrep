package Google;

import java.util.Arrays;
import java.util.List;

public class MinimumDominoRotationsForEqualRow_1007_Medium {

	 public static void main(String[] args) {

		 int[] A = {2,1,2,4,2,2};
		 int[] B = {5,2,6,2,3,2};
		 
		 int[] A1 = {3,5,1,2,3}, B1 = {3,6,3,3,4};
		 
		 int[] A2 = {1,2,1,1,1,2,2,2};
		 int[] B2 = {2,1,2,2,2,2,2,2};

		 
		 
		 System.out.println("Expected: 2, Actual: " + minDominoRotations(A, B));
		 System.out.println("Expected: -1, Actual: " + minDominoRotations(A1, B1));
		 System.out.println("Expected: 1, Actual: " + minDominoRotations(A2, B2));
		 
		 
		 System.out.println("2nd Approach:: Expected: 2, Actual: " + minDominoRotations2(A, B));
		 System.out.println("2nd Approach:: Expected: -1, Actual: " + minDominoRotations2(A1, B1));
		 System.out.println("2nd Approach:: Expected: 1, Actual: " + minDominoRotations2(A2, B2));
	 }

	/*
	 * https://www.youtube.com/watch?v=9Q73ScVu2GI&ab_channel=KevinNaughtonJr.
	 * 
	 * https://www.youtube.com/watch?v=mbHGA3uydMk&ab_channel=CodingBarista
	 */

	/*
	 * We will take the first index value (or any other index) from each array as
	 * target to match, why? 
	 * Because the as per problem statement all the values
	 * should be same after rotation (either in A or B) and if the first value (or the any picked index) is
	 * not matching (after rotation) then we are saying that "It can not be done"
	 * and return -1.
	 * 
	 * In addition to above all values in the array should be equal to A[i] or B[i]
	 * 
	 * Time : O(n) Space: O(1)
	 * 
	 * For another approach:  https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/discuss/252802/Thinking-Process
	 */

	public static int minDominoRotations(int[] A, int[] B) {
		int minSwap = Math.min(
								numSwaps(A[0], A, B), 
								numSwaps(B[0], A, B));
		
		minSwap = Math.min(minSwap, numSwaps(A[0],B, A));
		minSwap = Math.min(minSwap, numSwaps(B[0],B, A));
		
		return minSwap == Integer.MAX_VALUE ? -1 : minSwap;
	}
	
	private static int numSwaps(int target, int[] A, int[] B) {
		int numSwap = 0;
		
		for(int i=0; i<A.length; i++) {
			if(target != A[i] && target != B[i]) {
				return Integer.MAX_VALUE;
				
			} else if (target != A[i]) { // i.e. target == B[i] and we need to swap A[i] i.e. numWaps++
				numSwap++;
			}
		}
		return numSwap;
	}
	
	// 2nd Approach :: https://www.youtube.com/watch?v=BFtsYTl3KP4&ab_channel=QualityCode
	public static int minDominoRotations2(int[] A, int[] B) {
		if(A[0] == B[0]) {
			return minRoatationCount(A[0], A, B);
		}
		
		return Math.max(minRoatationCount(A[0], A, B), minRoatationCount(B[0], A, B)); // need to understand this why max?
	}
	
	public static int minRoatationCount(int target, int[] A, int[] B) {
		int countTopArr = 0; // A is top arr
		int countBottomArr = 0; // B is bottom arr
		
		for(int i=0; i<A.length; i++) {
			if(target != A[i] && target != B[i]) {
				return -1;
			} else if (target != A[i]) {
				countTopArr++;
			}  else if (target != B[i]) {
				countBottomArr++;
			}
		}
		
		return  Math.min(countTopArr, countBottomArr);
	}

}
