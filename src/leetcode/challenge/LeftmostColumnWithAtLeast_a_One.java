package leetcode.challenge;

import java.util.Arrays;
import java.util.List;

public class LeftmostColumnWithAtLeast_a_One {

	/**
	Question: In a binary matrix (all elements are 0 and 1), every row is sorted in ascending order (0 to the left of 1). Find the leftmost column index with a 1 in it.

	Eg :
	Input:
	[[0, 0, 0, 1],
	 [0, 0, 1, 1],
	 [0, 1, 1, 1],
	 [0, 0, 0, 0]]
	Output: 1

	Input:
	[[0, 0, 0, 0],
	 [0, 0, 0, 0],
	 [0, 0, 0, 0],
	 [0, 0, 0, 0]]
	Output: -1
	
	Other sites for this question and solution: 
		Preferred: 
			https://codenuclear.com/leetcode-leftmost-column-with-at-least-a-one-30days-challenge/
			https://www.youtube.com/watch?v=AS3Hcla3Hm0&ab_channel=KnowledgeCenter
		Others: 
			https://dev.to/akhilpokle/leftmost-column-with-at-least-a-one-a-common-algorithm-question-thought-process-from-brute-force-to-binary-search-4mi8
			https://code.dennyzhang.com/leftmost-column-with-at-least-a-one
			https://leetcode.com/playground/oFDW7DqN
			https://leetcode.com/discuss/interview-question/341247/facebook-leftmost-column-index-of-1
	
	*/
	public static void main(String[] args) {
		int[][] matrix = {{0, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 1, 1, 1},
                {0, 0, 0, 0}};
		test(matrix, 1);
		
		int[][] matrix2 = {{0, 0, 0, 0},
		                {0, 0, 0, 0},
		                {0, 0, 0, 0},
		                {0, 0, 0, 0}};
		
		test(matrix2, -1);
		
		int[][] matrix3 = {{0, 0, 0, 0},
		                {0, 0, 0, 0},
		                {0, 0, 0, 0},
		                {0, 0, 0, 1}};
		
		test(matrix3, 3);
		
		
		//This is working too - cleaner and better implementation
		int result = leftMostColumnWithOne_Interactive(new BinaryMatrix());
		System.out.println("Expected: 1, Actual: " + result);
	}
	
	
	/*
	 * This is an ineractive problem
	 * 
	 * Reference:
	 * 	https://codenuclear.com/leetcode-leftmost-column-with-at-least-a-one-30days-challenge/ 
	 * 	https://www.youtube.com/watch?v=AS3Hcla3Hm0&ab_channel=KnowledgeCenter
	 * 
	 * Time complexity: O(r + c)
	 * Space complexity: O(1)
	 * */	
	
	public static int leftMostColumnWithOne_Interactive(BinaryMatrix binaryMatrix) {
		List<Integer> dimension = binaryMatrix.dimensions();
		int rows = dimension.get(0); // returns num of rows in matrix
		int cols = dimension.get(1); // returns num of cols in matrix
		
		int r = 0, c = cols-1;
		int result = -1;
		
		while(r < rows && c >= 0) {
			int v = binaryMatrix.get(r, c);
			if(v == 1) {
				result = c;
				c--;
			} else {
				r++;
			}
		}
		return result;	
	}
	
	
	/**
	 * // This is the BinaryMatrix's API interface.
	 * // You should not implement it, or speculate about its implementation
	 */ 
	private static class BinaryMatrix {
		int[][] matrix = {
				{0, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 1, 1, 1},
                {0, 0, 0, 0}
               };
		
		public int get(int row, int col) {
			return matrix[row][col];
	    }
	    public List<Integer> dimensions() {
	    	return Arrays.asList(matrix.length, matrix[0].length);
	    }
	}
	
	
	
	/*
	 * Below implementation is just to run here actual problem is a Leetcode interactive problem. 
	 * Refer above method "leftMostColumnWithOne_Interactive()" to to see that implementation
	 * 
	 * Time complexity: O(r + c)
	 * Space : O(1)
	 * */
	public static int findLeftmostIndexOfOne(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int candidate = -1;
        for (int r = 0, c = cols - 1; r < rows && c >= 0; ) {
            if (matrix[r][c] == 1) {
                candidate = c;
                c--;
            } else {
                r++;
            }
        }
        return candidate;
    }
	private static void test(int[][] matrix, int expected) {
        int actual = findLeftmostIndexOfOne(matrix);
        if (actual == expected) {
            System.out.println("PASSED!");
        } else {
            System.out.println(String.format("FAILED! Expected: %d, but got: %d", expected, actual));
        }
    }
	 
	
}
