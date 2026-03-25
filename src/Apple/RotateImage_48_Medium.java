package Apple;
// LeetCode 48 - Rotate Image
// Rotates an n x n matrix 90 degrees clockwise in-place.

public class RotateImage_48_Medium {

    /*
	Rotates the given n x n matrix by 90 degrees clockwise in-place.
    
     Approach:
     - Step 1: Transpose the matrix across the main diagonal:
       swap matrix[i][j] with matrix[j][i] for all i < j.
     - Step 2: Reverse each row in-place using two pointers 
        (left and right)
      
       moving toward the center.
    
     This works because a clockwise 90-degree rotation is 
     equivalent to converting rows into columns (transpose) 
     and then mirroring horizontally (reverse each row).
    
     Interview script (what you can say):
     1. "We’re given an n by n matrix and must rotate it 90 
        degrees clockwise in-place, so I can’t use an extra 
        n by n matrix."
        
     2. "A 90-degree clockwise rotation can be decomposed into
        transpose plus reverse-each-row."
        
     3. "To transpose, I iterate i from 0 to n-1 and j from i+1 
        to n-1 and swap matrix[i][j] with matrix[j][i]."
        
     4. "Then for each row I use two pointers (left, right) and 
        swap while left is less than right, then move them inward."
        
     5. "Every element is visited a constant number of times, 
        so the time complexity is O(n^2) and the extra space 
        is O(1) because I only use a few temporary variables." */
	
	/*
	 Time: O(n²),  “Both transpose and reverse touch each element 
	     a constant number of times, so the time complexity is O(n²).
	     
	 Space: O(1), We only use a few extra variables for swapping, 
	     so space is O(1).”
	 * */

    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }

    // ===== Helpers for testing locally (not needed on LeetCode) =====

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j]);
                if (j + 1 < row.length) System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    private static void runTest(String name, int[][] matrix, RotateImage_48_Medium sol) {
        System.out.println("=== " + name + " ===");
        System.out.println("Original:");
        printMatrix(matrix);
        sol.rotate(matrix);
        System.out.println("Rotated:");
        printMatrix(matrix);
        System.out.println();
    }

    public static void main(String[] args) {
        RotateImage_48_Medium sol = new RotateImage_48_Medium();

        // Edge case: 1x1 matrix
        int[][] m1 = {
            {1}
        };

        // Small 2x2 matrix
        int[][] m2 = {
            {1, 2},
            {3, 4}
        };

        // Classic 3x3 example
        int[][] m3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // 4x4 example
        int[][] m4 = {
            {5,  1,  9, 11},
            {2,  4,  8, 10},
            {13, 3,  6,  7},
            {15, 14, 12, 16}
        };

        // Matrix with negatives, zeros, and duplicates
        int[][] m5 = {
            {0,  -1, -1},
            {2,   0,  2},
            {3,   3,  3}
        };

        runTest("1x1 matrix", m1, sol);
        runTest("2x2 matrix", m2, sol);
        runTest("3x3 matrix", m3, sol);
        runTest("4x4 matrix", m4, sol);
        runTest("3x3 with negatives/zeros/duplicates", m5, sol);
    }
}