package Reddit;

import java.util.Arrays;

public class GameOfLife_289_Medium {

	public static void main(String[] args) {
		int[][] board = {
							{0,1,0},
							{0,0,1},
							{1,1,1},
							{0,0,0}
						};
		
		
		gameOfLife(board);
		
		// Expected [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
		
		for(int[] b : board) {
			System.out.println(">> Result:: " + Arrays.toString(b));
		}
		
	}	
	
	/**
	 * https://leetcode.com/problems/game-of-life/
	 * Solution: 
	 * 			https://happygirlzt.com/code/289.html
	 * 			https://www.youtube.com/watch?v=sUqYZvfZ9UE
	 * 
	 * Article: https://leetcode.com/articles/game-of-life/
	 * 
	 * Complexity Analysis:
	 * 			Time Complexity: O(M×N), where M is the number of rows and N is the number of columns of the Board.
	 * 			Space Complexity: O(1)
	 * 
	 * 
	 * For Follow-up part refer: https://www.youtube.com/watch?v=K9zRM-ttaDs
	 *      and https://leetcode.com/problems/game-of-life/discuss/784839/Infinite-board-solution-in-Java
	 *      https://shanzi.gitbooks.io/algorithm-notes/content/problem_solutions/conways_game_of_life.html
	 */
	
	
	
	
	// mark die -> live: -1
    // marke live -> die: 2
    // count(board, i, j)
    //update: -1 => 1 and 2 => 0
    
    final static int dirs[][] = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, 
                             {-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
    
    public static void gameOfLife(int[][] board) {
        if(board == null || board.length == 0) return;
        
        int rows = board.length; int cols = board[0].length;
        
        
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                
                if(board[i][j] == 1) {
                    //count live neighbors
                    int lives = count(board, i, j);
                    if(lives < 2 || lives > 3) {
                        board[i][j] = 2;
                    }
                }
                
                if(board[i][j] == 0) {
                    //count live neighbors
                    int lives = count(board, i, j);
                    if(lives == 3) {
                        board[i][j] = -1;
                    }
                }
            }
        }
        
        update(board);
        
    }
    
    //update: -1 => 1 and 2 => 0
    private static void update (int[][] board) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                if(board[i][j] == -1) {
                    board[i][j] = 1;
                }
                else if(board[i][j] == 2) {
                    board[i][j] = 0;
                }
            }
        }
    }
    
    private static int count (int[][] board, int row, int col) {
        int lives = 0;
        for (int[] dir : dirs) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if(newRow >=0 && newRow < board.length 
               && newCol >=0 && newCol < board[0].length
                && (board[newRow][newCol] == 1 || board[newRow][newCol] == 2)) {
                
                lives++;
            }
        }
        return lives;
    }

}
