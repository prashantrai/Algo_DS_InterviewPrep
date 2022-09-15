package leetcode;

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

/**
Follow up 2 : Infinite Board

So far we've only addressed one of the follow-up questions for this problem statement. 
We saw how to perform the simulation according to the four rules in-place i.e. without 
using any additional memory. The problem statement also mentions another follow-up statement 
which is a bit open ended. We will look at two possible solutions to address it. Essentially, 
the second follow-up asks us to address the scalability aspect of the problem. 

What would happen if the board is infinitely large? Can we still use the same solution that 
we saw earlier or is there something else we will have to do different? If the board becomes 
infinitely large, there are multiple problems our current solution would run into:

1. It would be computationally impossible to iterate a matrix that large.
2. It would not be possible to store that big a matrix entirely in memory. 
   We have huge memory capacities these days i.e. of the order of hundreds of GBs. However, 
   it still wouldn't be enough to store such a large matrix in memory.
3. We would be wasting a lot of space if such a huge board only has a few live cells and the 
   rest of them are all dead. In such a case, we have an extremely sparse matrix and it wouldn't 
   make sense to save the board as a "matrix".
 

One aspect of the problem is addressed by a great solution provided by 
Stefan Pochmann (https://leetcode.com/problems/game-of-life/discuss/73217/Infinite-board-solution). 
So as mentioned before, it's quite possible that we have a gigantic matrix with a very 
few live cells. In that case it would be stupidity to save the entire board as is.

If we have an extremely sparse matrix, it would make much more sense to actually save the 
location of only the live cells and then apply the 4 rules accordingly using only these live cells.



Essentially, we obtain only the live cells from the entire board and then apply the different 
rules using only the live cells and finally we update the board in-place. 
The only problem with this solution would be when the entire board cannot fit into memory. 
If that is indeed the case, then we would have to approach this problem in a different way. 

When the entire board cannot fit into memory: 
For that scenario, we assume that the contents of the matrix are stored in a file, one row at a time.
In order for us to update a particular cell, we only have to look at its 8 neighbors which essentially 
lie in the row above and below it. So, for updating the cells of a row, we just need the row above 
and the row below. Thus, we read one row at a time from the file and at max we will have 
3 rows in memory. We will keep discarding rows that are processed and then we will keep 
reading new rows from the file, one at a time.

 
 * */
