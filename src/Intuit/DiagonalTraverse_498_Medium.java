package Intuit;

import java.util.Arrays;
import java.util.List;

public class DiagonalTraverse_498_Medium {

	public static void main(String[] args) {

		int[][] mat = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

		int[] out = findDiagonalOrder(mat);
		System.out.println("Expacted: [1, 2, 4, 7, 5, 3, 6, 8, 9] \nActual" + Arrays.toString(out));
	}

	/*
	 * https://leetcode.com/problems/diagonal-traverse/
	 * Reference:  https://leetcode.com/problems/diagonal-traverse/discuss/97711/Java-15-lines-without-using-boolean
	 * 
	 * Time and Space: O(M*N)
	 * */
	
	public static int[] findDiagonalOrder(int[][] mat) {
	    if(mat == null || mat.length == 0) return null;
	    
	    int r=0;
	    int c=0;
	    int m  =  mat.length;
	    int n  =  mat[0].length;
	    int[] out = new int[m*n];
	    
	    for(int i=0; i<m*n; i++ ) {
	        out[i] = mat[r][c];
	        if((r+c) % 2 == 0) { //Up
	            if(c==n-1) {
	                r++;
	            } else if(r==0) {
	                c++;
	            }
	            else {
	                r--;
	                c++;
	            }
	        } else { //down
	            if(r == m-1) {
	                c++;
	            } else if(c == 0) {
	                r++;
	            } else {
	                r++;
	                c--;
	            }
	        }
	    }
	   return out; 
	}
	
}
