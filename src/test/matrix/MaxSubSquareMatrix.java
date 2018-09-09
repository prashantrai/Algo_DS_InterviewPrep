package test.matrix;

import java.util.Arrays;
import java.util.Scanner;

public class MaxSubSquareMatrix {

	public static void main(String[] args) {

		Integer[][] mat = { 
							{ 0, 1, 1, 0, 1 }, 
							{ 1, 1, 0, 1, 0 }, 
							{ 0, 1, 1, 1, 0 }, 
							{ 1, 1, 1, 1, 0 },
							{ 1, 1, 1, 1, 1 }, 
							{ 0, 0, 0, 0, 0 } 
						};

		// System.out.println(Arrays.deepToString(mat));
		//fill();
		printMaxSubSquare(mat);
	}

	/**
	 * http://www.geeksforgeeks.org/maximum-size-sub-matrix-with-all-1s-in-a-binary-matrix/
	 * http://edusagar.com/questions/dynamic-programming/find-largest-sub-square-matrix-with-all-0s
	 * 
	 * 1) Construct a sum matrix S[R][C] for the given M[R][C]. a) Copy first
	 * row and first columns as it is from M[][] to S[][] b) For other entries,
	 * use following expressions to construct S[][] If M[i][j] is 1 then S[i][j]
	 * = min(S[i][j-1], S[i-1][j], S[i-1][j-1]) + 1 Else ///If M[i][j] is 0
	 * S[i][j] = 0
	 * 
	 * 2) Find the maximum entry in S[R][C] 3) Using the value and coordinates
	 * of maximum entry in S[i], print sub-matrix of M[][]
	 * 
	 */

	private static void printMaxSubSquare(Integer[][] mat) {

		Integer[][] sub = new Integer[mat.length][mat[0].length];

		// --copy row to sub matrix
		for (int r = 0; r < mat.length; r++) {
			sub[r][0] = mat[r][0];
		}

		// --copy col to sub matrix
		for (int c = 0; c < mat[0].length; c++) {
			sub[0][c] = mat[0][c];
		}

		int max = 1;
		int maxRow = 0;
		int maxCol = 0;
		for (int r = 1; r < mat.length; r++) {

			for (int c = 1; c < mat[r].length; c++) {

				if (mat[r][c] == 1) {

					sub[r][c] = 1 + min(sub[r][c - 1], sub[r - 1][c], sub[r - 1][c - 1]);

					if (max < sub[r][c]) {
						max = sub[r][c];
						maxRow = r;
						maxCol = c;
					}

				} else {
					sub[r][c] = 0;
				}
			}
		}

		// System.out.println("sub:: "+Arrays.deepToString(sub));

		System.out.println("max: " + max + ", maxRow=" + maxRow + ", maxCol=" + maxCol);

		displaySubSquare(mat, max, maxRow, maxCol);

	}

	private static void displaySubSquare(Integer[][] mat, int max, int maxRow, int maxCol) {

		int i = (maxRow + 1 - max); // --(4+1)-3 = 2 : col or row in array start
									// with zero, i.e. 4 is for 5th row so added
									// 1
		int j = (maxCol + 1 - max); // --(3+1)-3+1 = 1

		// Integer[][] subMat = new Integer[max][max];

		for (int r = i; r <= maxRow; r++) {

			for (int c = j; c <= maxCol; c++) {
				System.out.print(mat[r][c]);
			}
			System.out.println("");
		}
		// System.out.println("subMat:: "+Arrays.deepToString(subMat));

	}

	private static int min(int i, int j, int k) {

		int min;

		min = i < j ? i : j;
		min = min < k ? min : k;

		return min;
	}

	public static Integer[][] fill(){ 
        
		int row = 0;
		int col = 0;
		
		Scanner in = new Scanner(System.in);
        System.out.println("Enter rows count: ");
        row = in.nextInt();

        System.out.println("Enter cols count: ");
        col = in.nextInt();

        Integer[][] data = new Integer[row][col]; 

        for(int r = 0; r< row; r++){
        	for(int c = 0 ;c< col; c++){ 
        		System.out.println("Enter the elements:: ");
        		data[r][c] = in.nextInt();
        	} 
        	System.out.println(); 
        }
        
        System.out.println(Arrays.deepToString(data));
        return data;
	}
	
	

}
