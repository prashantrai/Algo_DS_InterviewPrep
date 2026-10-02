package Motive;
public class Search2DMatrixII_240_Medium {
	
	/*
	 * “Since both the rows and columns are sorted, I’ll start from the top-right
	 * corner. From there, if the current value equals the target, I’m done. If the
	 * current value is greater than the target, everything below it in that column
	 * is also greater, so I can safely move left. If the current value is smaller
	 * than the target, everything to its left in that row is also smaller, so I can
	 * safely move down. Each step eliminates either one row or one column, so the
	 * total time is O(m + n), with O(1) extra space.”
	 */
	
	/* Step-by-Step Algorithm
		1. Handle null or empty matrix.
		2. Start at the top-right cell: 
			row = 0
		   	col = cols - 1
		3. While row and col remain inside the matrix:
		   - if matrix[row][col] == target, return true
		   - if current value is greater than target, col--
		   - otherwise, row++
		4. If we leave the matrix, return false.
	 * */
	
	/*
	 Time:  O(m + n)
	 	where:
		- m = number of rows
		- n = number of columns
		Why?
		We can move:
		down at most m times
		left at most n times
		
		So total movements are at most: m + n
		
	 Space: O(1)
	 	
	 * */

    public static boolean searchMatrix(int[][] matrix, int target) {

        // Validate the input matrix before starting the search.
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        // Start from top-right because we can eliminate
        // either one row or one column after every comparison.
        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {

            int current = matrix[row][col];

            // Target found.
            if (current == target) {
                return true;
            }

            // Current is too large, so move left.
            if (current > target) {
                col--;
            }

            // Current is too small, so move down.
            else {
                row++;
            }
        }

        // We exhausted all possible candidates.
        return false;
    }

    public static void main(String[] args) {

        int[][] matrix = {
            {1,  4,  7, 11, 15},
            {2,  5,  8, 12, 19},
            {3,  6,  9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
        };

        // Test 1: Normal - target exists
        System.out.println(
            "Test 1 | Expected: true | Actual: "
            + searchMatrix(matrix, 5)
        );

        // Test 2: Normal - target does not exist
        System.out.println(
            "Test 2 | Expected: false | Actual: "
            + searchMatrix(matrix, 20)
        );

        // Test 3: Edge - first / smallest value
        System.out.println(
            "Test 3 | Expected: true | Actual: "
            + searchMatrix(matrix, 1)
        );

        // Test 4: Edge - largest value
        System.out.println(
            "Test 4 | Expected: true | Actual: "
            + searchMatrix(matrix, 30)
        );

        // Test 5: Invalid / empty matrix
        System.out.println(
            "Test 5 | Expected: false | Actual: "
            + searchMatrix(new int[][]{}, 5)
        );

        // Test 6: Single cell
        System.out.println(
            "Test 6 | Expected: true | Actual: "
            + searchMatrix(new int[][]{{7}}, 7)
        );
    }
}