package leetcode.challenge;

public class PerformStringShifts_1427 {

	public static void main(String[] args) {

		String s = "abc";
		int[][] shift1 = {{0,1},{1,2}};
		System.out.println("Expected: cab, Actual: " + stringShift(s, shift1));
		
		s = "abcdefg";
		int[][] shift2 = {{1,1},{1,1},{0,2},{1,3}};
		System.out.println("Expected: efgabcd, Actual: " + stringShift(s, shift2));
	}
	
	/* For Question and Solution:
	 * 	https://codenuclear.com/leetcode-perform-string-shifts-30days-challenge/
	 *  Another : https://www.youtube.com/watch?v=mU0Uin1vBGg&ab_channel=SystemDesignInterview
	 *
	 * You are given a string s containing lowercase English letters, and a matrix
	 * shift, where shift[i] = [direction, amount]:
	 * 
	 * direction can be 0 (for left shift) or 1 (for right shift). amount is the
	 * amount by which string s is to be shifted. A left shift by 1 means remove the
	 * first character of s and append it to the end. Similarly, a right shift by 1
	 * means remove the last character of s and add it to the beginning. Return the
	 * final string after all operations.
	 * 
	 * Example 1:
	 * 
	 * Input: s = “abc”, shift = [[0,1],[1,2]] 
	 * Output: “cab” 
	 * Explanation: 
	 * 		[0,1] means shift to left by 1. “abc” -> “bca” 
	 * 		[1,2] means shift to right by 2. “bca” -> “cab”
	 */
	
	/* Solution: 
	 * As we know that one left shift and right shift will cancel each other. We can
	 * take advantage of it and count number of left and right shifts. Just shift
	 * Characters only difference of (LShiftCnt-RShiftCnt)
	 */

	// Time: O(N) where N is num of row
	// Space: O(1)
	
	public static String stringShift(String s, int[][] shift) {
		if(s.length() <= 1) return s;
		
		int leftShift_count = 0;
		int rightShift_count = 0;
		for(int[] sArr : shift) {
			if(sArr[0] == 0) {  //left shift
				leftShift_count += sArr[1];
			} else {
				rightShift_count += sArr[1];
			}
		}
		
		if(leftShift_count > rightShift_count) {
			leftShift_count -= rightShift_count;
			leftShift_count %= s.length(); // in case left shift count more than the string length
			
			//return s.substring(leftShift_count) + s.substring(0, leftShift_count);
			
			String s1 = s.substring(leftShift_count);
			String s2 = s.substring(0, leftShift_count);
			
			return s1 + s2;
			
		} else {
			rightShift_count -= leftShift_count;
			rightShift_count %= s.length();
			
			// abcd efg right = 3 => efg abcd
			String s1 = s.substring(s.length() - rightShift_count);
			String s2 = s.substring(0, s.length() - rightShift_count);
			
			return s1 + s2;
		}
		
	}
	
}
