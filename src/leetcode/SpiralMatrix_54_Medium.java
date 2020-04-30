package leetcode;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix_54_Medium {

	public static void main(String args[]) throws Exception {
		int[][] mat = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		// int[][]mat = {{123}};
		// int[][]mat = {{1}};
//	        int[][]mat = {
//	                        {1},
//	                        {2},
//	                        {3}
//	                    };
		List<Integer> res = spiralOrder(mat);
		System.out.println("1.>>" + res);
		
		res = spiralOrder_2(mat); //--simplified implementation
		System.out.println("2.>>" + res);
	}

	
	// SNAP
	/* https://leetcode.com/problems/spiral-matrix/
	Complexity Analysis::

	Time Complexity: O(N), where N is the total number of elements in the input matrix. 
	We add every element in the matrix to our final answer.

	Space Complexity: O(N), the information stored in ans.

	*/
	
	public static List<Integer> spiralOrder(int[][] mat) {
		if (mat == null || mat.length == 0)
			return null;

		List<Integer> res = new ArrayList<>();

		int rStrt = 0;
		int rEnd = mat.length - 1;

		int cStrt = 0;
		int cEnd = mat[0].length - 1;

		// 1 2 3
		// 4 5 6
		// 7 8 9
		// -> 123 698 745

		while (rStrt <= rEnd && cStrt <= cEnd) {
			// -move col wise
			for (int c = cStrt; c <= cEnd; c++) {
				res.add(mat[rStrt][c]);
			}
			// --row wise
			for (int r = rStrt + 1; r <= rEnd; r++) {
				res.add(mat[r][cEnd]);
			}

			if (rStrt < rEnd && cStrt < cEnd) {

				for (int c = cEnd - 1; c > cStrt; c--) {
					res.add(mat[rEnd][c]);
				}

				for (int r = rEnd; r > rStrt; r--) {
					res.add(mat[r][cStrt]);
				}
			}
			rStrt++;
			rEnd--;
			cStrt++;
			cEnd--;

		}

		return res;
	}
	
	
	//--simplified version - working
	public static List<Integer> spiralOrder_2(int[][] matrix) {
        
        List<Integer> res = new ArrayList<>();
        
        if(matrix == null || matrix.length == 0) {
            return res;            
        }
        
        int row_start = 0;
        int row_end = matrix.length-1;
        int col_start = 0;
        int col_end = matrix[0].length-1;
        
        while(row_start <= row_end && col_start <= col_end) {
            
            // traverse right
            for (int i=col_start; i<=col_end; i++) {
                res.add(matrix[row_start][i]);
            }
            row_start++;
            
            // traverse down
            for (int i=row_start; i<=row_end; i++) {
                res.add(matrix[i][col_end]);
            }
            col_end--;
            
            // traverse left
            if(row_start <= row_end) {
                for (int i=col_end; i>=col_start; i--) {
                    res.add(matrix[row_end][i]);
                }
            }
            row_end--;
            
            // taverse up
            if(col_start <= col_end) {
                for (int i=row_end; i>=row_start; i--) {
                    res.add(matrix[i][col_start]);
                }
            }
            col_start++;
        }

        return res;
    }
}

/*
 * 54. Spiral Matrix:: Given a matrix of m x n elements (m rows, n columns),
 * return all elements of the matrix in spiral order.
 * 
 * Example 1:
 * 
 * Input: [ [ 1, 2, 3 ], [ 4, 5, 6 ], [ 7, 8, 9 ] ] 
 * Output: [1,2,3,6,9,8,7,4,5]
 * 
 * Example 2:
 * 
 * Input: [ [1, 2, 3, 4], [5, 6, 7, 8], [9,10,11,12] ] 
 * Output:
 * [1,2,3,4,8,12,11,10,9,5,6,7]
 */
