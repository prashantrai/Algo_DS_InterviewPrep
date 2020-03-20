package test.practice.amazon;

import java.util.Arrays;

public class RotateImage_48_Medium {

	public static void main(String[] args) {
		int[][] mat = {
		  { 5, 1, 9, 11},
		  { 2, 4, 8, 10},
		  {13, 3, 6,  7},
		  {15,14,12, 16}
		  };
		
		/* After Transpose [Row becomes col in new matrix after transpose]: 
		 * 	[
		 * 		[5, 2, 13, 15], 
		 * 		[1, 4, 3, 14], 
		 * 		[9, 8, 6, 12], 
		 * 		[11, 10, 7, 16]
		 * 	]
		 * 
		 * After Flip: 
		 * [
		 * 		[15, 13,  2,  5], 
		 * 		[14,  3,  4,  1], 
		 * 		[12,  6,  8,  9], 
		 * 		[16,  7, 10, 11]
		 * ]
		 * 
		 * */
		
		//rotate(mat);
		//System.out.println(Arrays.deepToString(mat));
		
		rotate2(mat);  //--transpose and flip
		System.out.println(Arrays.deepToString(mat));
	}
	
	
	//-- https://leetcode.com/problems/rotate-image/
	//-- https://leetcode.com/problems/rotate-image/discuss/526450/JAVA-Solution
	public static void rotate(int[][] matrix) {
        int r1 = 0,r2 = matrix.length-1;
        int c1 = 0,c2 = matrix[0].length-1;
        
        while(r1 < r2){
            for(int i=0;i<(r2-r1);i++){
                swap(matrix, r1+i,c1, r2,c1+i);
                swap(matrix, r2,c1+i, r2-i,c2);
                swap(matrix, r2-i,c2, r1,c2-i);
            }
            r1++;r2--;
            c1++;c2--;
        }
    }
    
    public static void swap(int[][] M,int i1,int j1,int i2,int j2){
        int t = M[i1][j1];
        M[i1][j1] = M[i2][j2];
        M[i2][j2] = t;
    }
    
    
    //--https://leetcode.com/problems/rotate-image/discuss/516871/Java-0ms-Transpose-%2B-Flip
    public static void rotate2(int[][] matrix) {
        int n = matrix.length;
        
        //transpose
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){ 
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        System.out.println(Arrays.deepToString(matrix));
        
        //flip
        for(int i = 0; i < n; i++){ //row
            for(int j = 0; j < n / 2; j++){ //col 
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
        
        System.out.println(Arrays.deepToString(matrix));
        
    }
    
    //https://leetcode.com/problems/rotate-image/discuss/519754/Java-Solution-via-transpose%3A-0ms
    public static void rotate3(int[][] matrix) {

    	//--transpose
        for(int i = 0; i < matrix.length; i++) {
            for(int j = i + 1; j < matrix[0].length; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    	
    	// for the even case we need to swap the columns 
        // for the odd case we leave the middle one alone 
        // hence the n/2 for the loop
        for(int i = 0; i < matrix.length; i++) { 
            int offset = 0;
            for(int j = matrix.length - 1; j >= matrix.length/2 ; j--) {
               int tmp = matrix[i][offset];
               matrix[i][offset] = matrix[i][j];
               matrix[i][j] = tmp;
               offset++;
            }
        }

    }
}
