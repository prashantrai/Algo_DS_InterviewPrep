package test.practice.misc;

import java.util.Arrays;

public class LargestSquareInMatrix {

	/*
	 * Leet code submission of similar prob: 
	 * 	https://leetcode.com/problems/maximal-square/submissions/
	 * 	https://leetcode.com/problems/maximal-square/
	 *  https://leetcode.com/articles/maximal-square/
	 */
	
	public static void main(String[] args) {

		/*int[][] mat = {
						{1, 1, 0, 1, 0},
						{0, 1, 1, 1, 0},
						{1, 1, 1, 1, 0},
						{0, 1, 1, 1, 1},
					   };
*/
		int[][] mat = { 
				{ 0, 1, 1, 0, 1 }, 
				{ 1, 1, 1, 1, 0 }, 
				{ 1, 1, 1, 1, 0 }, 
				{ 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1 }, 
				{ 0, 0, 0, 0, 0 } 
			};
		
		
		int result = findLargestSquare(mat);
		System.out.println(">>"+result);
	}
	
	//--looks good
	public static int findLargestSquare (int[][] m) {
		
		if(m == null || m.length == 0) return 0;
		
		int[][] cache = new int[m.length][m[0].length];
		int result = 0;
		
		for(int r=0; r<m.length; r++) {
			for(int c=0; c<m[0].length; c++) {
				
				if(r==0 || c==0) {
					cache[r][c] = m[r][c];
				} else {
					int minValue = min(cache[r-1][c], cache[r-1][c-1], cache[r][c-1]);
					cache[r][c] = m[r][c] + minValue;
					result = result > cache[r][c] ? result : cache[r][c];
					//print(cache);
				}
			}
		}
		print(cache);
		return result;
	}
	
	private static int min (int v1, int v2, int v3) {
		
		int minValue = 0;
		minValue = Math.min(Math.min(v1, v2), v3);
		return minValue;	
	}
	
	private static void print(int[][] m) {
		
		for (int[] arr : m) {
			
			System.out.println(Arrays.toString(arr));
		}
	}
	
	

}
