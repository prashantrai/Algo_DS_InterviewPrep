package asana;

public class HungryRabbit {

	/* Problem: https://github.com/vildevev/Carrot_Search
	 * 
	 * Hungry Rabbit
		This is a pretty interesting problem, but it takes a bit longer than most interviews would allow. 
		It is not a difficult algorithm, but it is a great question for determining code hygiene, 
		since there is a lot of opportunity for code deduplication and that sort of thing.
		
		Problem Statement:
		A very hungry rabbit is placed in the center of a garden, represented by a rectangular N x M 2D matrix.
		
		The values of the matrix will represent numbers of carrots available to the rabbit in each square of the garden. 
		If the garden does not have an exact center, the rabbit should start in the square closest to the center with 
		the highest carrot count.
		
		On a given turn, the rabbit will eat the carrots available on the square that it is on, 
		and then move up, down, left, or right, choosing the the square that has the most carrots. 
		
		If there are no carrots left on any of the adjacent squares, the rabbit will go to sleep. 
		You may assume that the rabbit will never have to choose between two squares with the same number of carrots.
		
		Write a function which takes a garden matrix and returns the number of carrots the rabbit eats eg.
		
		    [[5, 7, 8, 6, 3],
		     [0, 0, 7, 0, 4],
		     [4, 6, 3, 4, 9],
		     [3, 1, 0, 5, 8]]
		
		Expected: Should return 27
		
		Key components:
		Deduplicates code for enumerating possible moves for both starting and next moves.
		is able to parse the matrix
		has an intuitive approach for dealing with the problem.

	 * 
	 */
	
	
	public static void main(String[] args) {

		int[][] mat = {	{5, 7, 8, 6, 3},
						{0, 0, 7, 0, 4},
						{4, 6, 3, 4, 9},
						{3, 1, 0, 5, 8}};
		
		int res = hungryRabbit(mat);
		System.out.println("Expected: 27, Actual: "+res);
	}
	
	public static int hungryRabbit(int[][] mat) {
		
		int startingX = (int) Math.ceil(mat.length/2);
		int startingY = (int)Math.ceil(mat[0].length/2);
		int[] startingSquare = findNextSquareWithMaxCarrots(mat, startingX, startingY);
		
		//System.out.println(startingSquare[0] +", "+startingSquare[1]);
		
		return helper(mat, startingSquare[0], startingSquare[1]);
	}

	private static int helper(int[][] mat, int row, int col) {
		
		int sum = 0;
		sum += mat[row][col];
		
		mat[row][col] = 0;
		
		int left   = !isOutOfBound(mat, row, col-1) ? mat[row][col-1] : 0;
		int right  = !isOutOfBound(mat, row, col+1) ? mat[row][col+1] : 0;
		int top    = !isOutOfBound(mat, row-1, col) ? mat[row-1][col] : 0;
		int bottom = !isOutOfBound(mat, row+1, col) ? mat[row+1][col] : 0;
		
		while (left > 0 || right > 0 || top > 0 || bottom > 0) {
			
			//int[] nextSquare = findNextSquareWithMaxCarrots(row, col, left, right, top, bottom);
			int[] nextSquare = findNextSquareWithMaxCarrots(mat, row, col);
			int currMax = mat[nextSquare[0]][nextSquare[1]];
			
			if(currMax == 0) return sum;
			
			row = nextSquare[0];
			col = nextSquare[1];
			
			sum += currMax;
			mat[row][col] = 0; //set the current index value as ZERO coz rabbit has eaten the carrots
			
			//find max among up, down, left, or right neighbors
			left   = !isOutOfBound(mat, row, col-1) ? mat[row][col-1] : 0;
			right  = !isOutOfBound(mat, row, col+1) ? mat[row][col+1] : 0;
			top    = !isOutOfBound(mat, row-1, col) ? mat[row-1][col] : 0;
			bottom = !isOutOfBound(mat, row+1, col) ? mat[row+1][col] : 0;
		
		}
		return sum;
		
	}
	
//	private static int[] findNextSquareWithMaxCarrots(int row, int col, int left, int right, int top, int bottom) {
	private static int[] findNextSquareWithMaxCarrots(int[][] mat, int row, int col) {
		
		int left   = !isOutOfBound(mat, row, col-1) ? mat[row][col-1] : 0;
		int right  = !isOutOfBound(mat, row, col+1) ? mat[row][col+1] : 0;
		int top    = !isOutOfBound(mat, row-1, col) ? mat[row-1][col] : 0;
		int bottom = !isOutOfBound(mat, row+1, col) ? mat[row+1][col] : 0;
		
		int currMax = 0;
		int curr_row = 0;
		int curr_col = 0;
		
		if(left > 0 && left >= right) {
			currMax = left;
			curr_row = row;
			curr_col = col-1;
		} else if (right > 0) {
			currMax = right;
			curr_row = row;
			curr_col = col+1;
		}
		
		if(top > 0 && top >= currMax) {
			currMax = top;
			curr_row = row-1;
			curr_col = col;
		} else if (bottom > 0 && bottom >= currMax){
			currMax = bottom;
			curr_row = row+1;
			curr_col = col;
		}
		
		return new int[]{curr_row, curr_col};
	}
	
	private static boolean isOutOfBound(int[][] mat, int row, int col) {
		return row >= mat.length || row < 0 || col >= mat[0].length || col < 0;
	}
}
