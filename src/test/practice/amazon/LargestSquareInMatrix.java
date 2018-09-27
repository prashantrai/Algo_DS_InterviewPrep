package test.practice.amazon;
import java.util.Arrays;

public class LargestSquareInMatrix {

	public static void main(String[] args) {

		int[][] mat = {
						{1, 1, 0, 1, 0},
						{0, 1, 1, 1, 0},
						{1, 1, 1, 1, 0},
						{0, 1, 1, 1, 1},
					   };

		/*int[][] mat = { 
				{ 0, 1, 1, 0, 1 }, 
				{ 1, 1, 1, 1, 0 }, 
				{ 1, 1, 1, 1, 0 }, 
				{ 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1 }, 
				{ 0, 0, 0, 0, 0 } 
			};*/
		
		
		int result = findLargestSquare(mat);
		System.out.println(">>"+result);
	}
	
	//--looks good
	public static int findLargestSquare (int[][] m) {
		
		int[][] result = m.clone();
		int max = 0;
		
		for (int r=1; r<m.length; r++) {
			for (int c=1; c<m[r].length; c++) {
				
				if(m[r][c] == 0) { 
					//do nothing
				}
				else {
					result[r][c] = 1 + min(m[r][c-1], m[r-1][c-1], m[r-1][c]);
					max = max > result[r][c] ? max : result[r][c];
				}
			}
		}
		print(result);
		return max;
	}
	
	private static int min (int v1, int v2, int v3) {
		
		return Math.min(v1, Math.min(v2, v3));
		
		
	}
	
	private static void print(int[][] m) {
		
		for (int[] arr : m) {
			
			System.out.println(Arrays.toString(arr));
		}
	}
	
	

}