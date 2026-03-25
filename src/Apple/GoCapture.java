package Apple;

import java.util.HashSet;
import java.util.Set;

public class GoCapture {

	public static void main(String[] args) {
		char e='e', b='b', w='w';
        char[][] board = {
            {e, e, e, e, b, b, b},
            {e, e, e, b, w, w, b},
            {e, e, e, e, b, e, b},
            {e, e, e, e, e, e, e}
        };
        System.out.println("Expacted: 2, Actual: " + numCaptured(board, 2, 5)); // Output: 2
        
        // Uncomment below for follow-up, i.e. suicidal move
        
        // follow-up: added to detect illegal/suicidal move
        // expected output: -1 if move is suicidal
        char[][] board2 = {
                {b,b,b},
                {b,e,b},
                {b,w,b}};
        //System.out.println("Expacted: -1, Actual: " + numCaptured(board2,1,1));
        
	}
	
	/** Approach:: 
	1.Place the black stone.
	2.Check the 4 neighboring cells.
	3.For each neighbor that is white and not visited:
		- Run DFS to explore the white group.
		- Count stones in the group.
		- Track whether a liberty exists.
	7.If the group has no liberty, add its size to captured stones. 
	 * */

	/** Step-by-Step Algorithm::
	1.Place the black stone at (row, col).
	2.Create a visited matrix.
	3.Check the 4 directions around (row, col).
	4.If a neighbor is white and not visited:
		- Run DFS to explore the group.
		- Count the stones in that group.
		- Track if any liberty ('e') is found.
	5.If no liberty is found, add the group size to the result.
	6.Return the total captured stones.
	 * */

	/** Follow-up: Suicidal/Illegal Move
	Interview Explanation (Short): 
	- After placing the black stone, we first capture adjacent white groups.
	- If no capture occurs, we run DFS on the newly placed black stone to see 
	  if its group has any liberties.
	- If it doesn't, the move is suicidal and therefore illegal.
	 * */
	
	/*
	Time: O(m * n), Each cell is visited at most once.
	Space: O(m * n), For the visited array and recursion stack.
	*/
    
    static class Result {
        int count;
        boolean hasLiberty;
    }
    
    // Directions: up, down, left, right
    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};
    
    public static int numCaptured(char[][] board, int row, int col) {
    	int m = board.length; 
    	int n = board[0].length;

    	boolean[][] visited = new boolean[m][n];
    	
    	board[row][col] = 'b';
    	
    	int capture = 0;
    	
    	for (int[] dir : DIRS) {
			int r = row + dir[0];
			int c = col + dir[1];
			
			if(isValid(r, c, m, n) && board[r][c] == 'w' && !visited[r][c]) {
				
				Result res = new Result();
				dfs(board, r, c, visited, res);
				
				if(!res.hasLiberty)
					capture += res.count;
			}
    	}
    	
    	// follow-up: added to detect illegal/suicidal move
        // If we did not capture anything, check whether the new black stone's group has a liberty
//    	if(isMoveSuicidal(board, row, col)) {
//    		return -1;
//    	}
    	
    	return capture;
    }
    
    // follow-up: added to detect illegal/suicidal move
    // If we did not capture anything, check whether the new black stone's group has a liberty
    private static boolean isMoveSuicidal(char[][] board, int r, int c) {
    	int m = board.length; 
    	int n = board[0].length;
    	
    	boolean[][] visited = new boolean[m][n]; // visited to explore black group
    	Result res = new Result();
    	dfs(board, r, c, visited, res);	// explore black group
    	
    	if(!res.hasLiberty) {
    		// Move is suicidal → illegal in Go
    		return true;
    	}
    	return false;
    }
    
    private static void dfs(char[][] board, int r, int c, 
    		boolean[][] visited, Result res) {
    	
    	int m = board.length; 
    	int n = board[0].length;
    	
    	visited[r][c] = true;
    	res.count++;
    	
    	for (int[] dir : DIRS) {
			int nr = r + dir[0];
			int nc = c + dir[1];
			
			if(!isValid(nr, nc, m, n)) continue;
			
			if(board[nr][nc] == 'e') 
				res.hasLiberty = true;
			
			// follow-up: added to detect illegal/suicidal move
            // allow DFS to traverse both white and black stones
			// uncomment below for follow-up, i.e., suicidal move
//			if ((board[nr][nc] == board[r][c]) && !visited[nr][nc]) { 
			if(board[nr][nc] == 'w' && !visited[nr][nc]) { 
				dfs(board, nr, nc, visited, res);
			}
    	}
    }
    
    private static boolean isValid(int r,int c,int m,int n){
        return r>=0 && c>=0 && r<m && c<n;
    }

}


/** Question - Go Capture
	You play a game of Go. You are given a board with some stones placed on it.
	'w' is white stone, 'b' is black stone, 'e' is an empty spot, and you are given 
	a new black stone to be placed on an empty spot. You have to return the number 
	of enemy stones that this move will capture.
	
	Example 1:
	
	Input: board = [[e, e, e, e, b, b, b], row = 2, col = 5
				    [e, e, e, e, b, w, b],
				    [e, e, e, e, b, e, b],
				    [e, e, e, e, e, e, e]]
	
	Output: 1
	Explanation: If you place a black stone on (2, 5) then you capture 1 white stone from the enemy.
	
	Example 2:
	
	Input: board = [[e, e, e, e, b, b, b], row = 2, col = 5
				    [e, e, e, b, w, w, b],
				    [e, e, e, e, b, e, b],
				    [e, e, e, e, e, e, e]]
	
	Output: 2
	Explanation: As u can see, 2 whites are enclosed.
	
	Example 3:
	
	Input: board = [[e, e, e, e, b, b, b], row = 2, col = 5
				    [e, e, e, e, w, w, b],
				    [e, e, e, e, b, e, b],
				    [e, e, e, e, e, e, e]]
	
	Output: 0
	Explanation: Because the enclosure is not complete, white can escape from the left side.
	
	Example 4:
	
	Input: board = [[e, e, b, b, b, b, b], row = 2, col = 5
				    [e, e, b, w, e, w, b],
				    [e, e, b, b, b, e, b],
				    [e, e, e, e, e, e, e]]
	
	Output: 0
	Explanation: This one does not qualify as an enclosure, it is an 'eventual enclosure', 
	meaning that the white regions still have scope for expansion.
	
	
	Follow-up (suggested by ChatGPT): How would you modify the solution to detect a suicidal move (illegal move in Go)?
	
	Rule (Suicide in Go)
	A move is illegal if:
	- After placing the stone, its own group has no liberties, and
	- The move does not capture any opponent stones.
	
	Algorithm:: 
		1. Place the new black stone at (row, col).
		2. Look at 4 directions:
			-	up
			-	down
			-	left
			-	right
		3. If a neighbor is white (w):
			-	Run DFS/BFS to explore the entire white group.
			-	While exploring:
				-	Count group stones
				-	Check if any neighbor is e (liberty).
		4. If no liberty found:
			-	Add the group size to captured stones.
		5. Use a visited matrix so the same group is not processed twice.

 
 * */

