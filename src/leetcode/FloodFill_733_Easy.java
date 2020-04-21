package leetcode;

import java.util.Arrays;

public class FloodFill_733_Easy {

	public static void main(String[] args) {

		int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
		
		// Expected: [[2,2,2],[2,2,0],[2,0,1]]
		
		int[][] res = floodFill(image, 1, 1, 2);
		for(int i=0; i<res.length; i++) 
			System.out.println(Arrays.toString(res[i]));
		
	}
	
	/*Complexity Analysis

	Time Complexity: O(N), where N is the number of pixels in the image. We might process every pixel.

	Space Complexity: O(N), the size of the implicit call stack when calling dfs.
	*/

	public static int[][] floodFill(int[][] image, int r, int c, int newColor) {
        if(image[r][c] != newColor) 
            helper(image, r, c, image[r][c], newColor);
        
		return image;
    }
    
    private static void helper(int[][] img, int r, int c, int color, int newColor) {
        if(r < 0 || r >= img.length || c < 0 || c >= img[0].length ) {
            return;
        }
        
        if(img[r][c] == color) {
            img[r][c] = newColor;
            
            helper(img, r-1, c, color, newColor);
            helper(img, r, c-1, color, newColor);
            helper(img, r+1, c, color, newColor);
            helper(img, r, c+1, color, newColor);
        }        
    }
	
}
