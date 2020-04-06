package ctci.ch8.recursion.and.dp;

import java.util.Arrays;

public class PaintFillDemo {


	enum Color {Red, Green, Black, White, Yellow}
	
	public static void main(String[] args) {
		
		Color[][] screen = {
								{Color.Black, Color.White, Color.White},
								{Color.White, Color.White, Color.White},
								{Color.White, Color.White, Color.White}
							}; 
		
		paintFill(screen, 1, 1, Color.Green);
		
		System.out.println(">>>Result: "+ Arrays.deepToString(screen));

	}
	
	
	public static boolean paintFill(Color[][] screen, int r, int c, Color ncolor) {
		
		if(screen[r][c] == ncolor) {
			return false;
		}
		
		return paintFill(screen, r, c, screen[r][c], ncolor);
		
	}


	/* Refer : https://www.hackerearth.com/practice/algorithms/graphs/flood-fill-algorithm/tutorial/
	 * 
	 * Time Complexity: O(n*m)
	 */
	
	
	private static boolean paintFill(Color[][] screen, int r, int c, Color ocolor, Color ncolor) {

		if(r<0 || r >= screen.length || c < 0 || c >= screen[0].length) {
			return false;
		}
		
		if(screen[r][c] == ocolor) {
			
			screen[r][c] = ncolor;
			
			paintFill(screen, r-1, c, ocolor, ncolor); //up
			paintFill(screen, r+1, c, ocolor, ncolor); //down
			paintFill(screen, r, c-1, ocolor, ncolor); //left
			paintFill(screen, r, c+1, ocolor, ncolor); //right
			
		}
		
		return true;
	}

}
