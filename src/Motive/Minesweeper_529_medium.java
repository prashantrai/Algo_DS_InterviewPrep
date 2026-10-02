package Motive;

import java.util.*;

public class Minesweeper_529_medium {

	// ---------------------------------------------------------
    // Test helpers
    // ---------------------------------------------------------

    private static void runTest(
            int testNumber,
            String name,
            char[][] board,
            int[] click,
            char[][] expected) {

        char[][] actual = updateBoard(board, click);

        System.out.println("Test " + testNumber + " - " + name);
        System.out.println("Expected:");
        printBoard(expected);

        System.out.println("Actual:");
        printBoard(actual);

        System.out.println(
            "Pass: " + Arrays.deepEquals(toObject(expected), toObject(actual))
        );

        System.out.println();
    }

    private static Character[][] toObject(char[][] board) {
        Character[][] result = new Character[board.length][];

        for (int r = 0; r < board.length; r++) {
            result[r] = new Character[board[r].length];

            for (int c = 0; c < board[r].length; c++) {
                result[r][c] = board[r][c];
            }
        }

        return result;
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {

        // Test 1: Standard LeetCode example
        runTest(
            1,
            "Large blank expansion",
            new char[][]{
                {'E','E','E','E','E'},
                {'E','E','M','E','E'},
                {'E','E','E','E','E'},
                {'E','E','E','E','E'}
            },
            new int[]{3, 0},
            new char[][]{
                {'B','1','E','1','B'},
                {'B','1','M','1','B'},
                {'B','1','1','1','B'},
                {'B','B','B','B','B'}
            }
        );

        // Test 2: Click directly on mine
        runTest(
            2,
            "Click mine",
            new char[][]{
                {'M'}
            },
            new int[]{0, 0},
            new char[][]{
                {'X'}
            }
        );

        // Test 3: Single empty cell
        runTest(
            3,
            "Single empty cell",
            new char[][]{
                {'E'}
            },
            new int[]{0, 0},
            new char[][]{
                {'B'}
            }
        );

        // Test 4: Click next to one mine
        runTest(
            4,
            "Adjacent mine",
            new char[][]{
                {'M','E'},
                {'E','E'}
            },
            new int[]{1, 1},
            new char[][]{
                {'M','E'},
                {'E','1'}
            }
        );

        // Test 5: Multiple surrounding mines
        runTest(
            5,
            "Multiple adjacent mines",
            new char[][]{
                {'M','E','M'},
                {'E','E','E'},
                {'M','E','M'}
            },
            new int[]{1, 1},
            new char[][]{
                {'M','E','M'},
                {'E','4','E'},
                {'M','E','M'}
            }
        );
    }

	
	/* Thought Process:  
	 * A normal flood-fill would simply reveal all connected empty
	 * cells, but Minesweeper has an additional stopping condition. Before expanding
	 * from a cell, I need to know whether it touches a mine. So BFS works
	 * naturally: process one cell, count its neighboring mines, and either assign
	 * the number or expand if the count is zero.
	 */
	 
	/*
	 * Interview Explanation : 
	 * 
	 * Before “I’ll use BFS starting from the clicked cell.
	 * First, if the clicked cell is a mine, I can immediately change it to X and
	 * return. Otherwise, I’ll put the clicked cell into a queue. For every cell I
	 * remove from the queue, I’ll inspect all 8 neighbors and count how many mines
	 * surround it. If the count is greater than zero, I’ll write that number into
	 * the board and stop expanding from this cell. If the count is zero, I’ll mark
	 * the cell as B, and then add all neighboring unrevealed E cells to the queue.
	 * I’ll mark those cells as B when I enqueue them so they can’t be added
	 * multiple times. Since each cell is processed at most once and each processing
	 * checks at most 8 neighbors, the overall complexity is linear in the board
	 * size.”
	 */
	
	 /*  Complexity Analysis Before Coding 
	  Let:
		- m = number of rows
		- n = number of columns
		Time Complexity : O(m × n)
			Each cell is processed at most once, and for each cell we inspect at most 8 neighbors.
		
		Space Complexity: O(m × n) worst case
		
		The BFS queue could contain a large portion of the board.
	 */ 

	/* Step-by-Step Algorithm 
	 * 1. Get the clicked row and column. 
	 * 2. If the clicked cell is M: 
	 * 	- change it to X 
	 * 	- return. 
	 * 3. Add the clicked empty cell to a BFS queue. 
	 * 4. Mark it B immediately so it cannot be queued again. 
	 * 5. While the queue is not empty: 
	 * 	- remove a cell. 
	 * 	- count adjacent mines among its 8 neighbors. 
	 * 6. If mine count > 0: 
	 * 	- change the cell to the corresponding digit. 
	 * 	- do not expand further. 
	 * 7. Otherwise: 
	 * 	- keep it as B. 
	 * 	- enqueue every neighboring E. 
	 * 	- mark each one B when enqueued. 
	 * 8. Return the board.
	 */
	
	/** BFS */
	public static char[][] updateBoard(char[][] board, int[] click) {
		int rows = board.length;
		int cols = board[0].length;
		
		int startRow = click[0];
		int startCol = click[1];
		
		// Interview script:
        // If we clicked directly on a mine, the game ends immediately.
		if(board[startRow][startCol] == 'M') {
			board[startRow][startCol] = 'X';
			return board;
		}
		
		int[][] dirs = {
				{-1, -1}, {-1, 0}, 	{-1, 1},
				{ 0, -1},     		{ 0, 1},
				{ 1, -1}, { 1, 0},	{ 1, 1}
		};
		
		Queue<int[]> q = new ArrayDeque<>();
		
		// Interview script:
        // Start BFS from the clicked empty cell.
        // Mark when enqueued so the same cell is never added twice.
		// Mark it B immediately so it cannot be queued again.
		q.offer(new int[] {startRow, startCol});
		board[startRow][startCol] = 'B';
		
		while(!q.isEmpty()) {
			int[] current = q.poll();
			int row = current[0];
			int col = current[1];
			
			int mineCount = 0;
			
			// Interview script:
            // First count mines around this cell.
			for(int[] dir : dirs) {
				int nr = row + dir[0];
				int nc = col + dir[1];
				
				if(nr >= 0 && nr < rows && 
				   nc >= 0 && nc < cols &&
				   board[nr][nc] == 'M') {
					
					mineCount++;
				}
			}
			
			// Interview script:
            // If there is an adjacent mine, show the number
            // and stop expanding from this cell.
			if(mineCount > 0) {
				board[row][col] = (char) ('0' + mineCount);
				continue;
			}
			
			// Interview script:
            // No adjacent mines, so this is a blank cell.
            // Expand BFS to all neighboring unrevealed cells.
			for(int[] dir : dirs) {
				int nr = row + dir[0];
				int nc = col + dir[1];
				
				if(nr >= 0 && nr < rows && 
				   nc >= 0 && nc < cols &&
				   board[nr][nc] == 'E') {
							
					board[nr][nc] = 'B';
					q.offer(new int[] {nr, nc});
				}
			}
			
		}
		
		return board;
		
	}
	
	

	/** Working solution
	 * 
	 * Depth-first search (DFS)
	 *
	 * Time Complexity: O(8 * M * N) = O(M * N)
	 *
	 * Space Complexity: O(M * N)
	 *
	 * M = Number of rows. N = Number columns.
	 * 
	 * https://leetcode.com/problems/minesweeper/discuss/1524772/Java-or-TC%3A-O(MN)-or-SC%3A-O(M%2BN)-or-BFS-%2B-DFS-Solutions
	 */
	
	private static int[][] dirs = {
	            {-1, -1}, {-1, 0}, {-1, 1},
	            { 0, -1},          { 0, 1},
	            { 1, -1}, { 1, 0}, { 1, 1}
	        };
	
	// dfs
	public static char[][] updateBoard_DFS(char[][] board, int[] click) {
        
		int rLen = board.length;
		int cLen = board[0].length;
		
		if(rLen == 0 || cLen == 0 || click.length != 2) return board;
		
		int x = click[0];
		int y = click[1];
		
		// If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
		if(board[x][y] == 'M') {
			board[x][y] = 'X';
		} else {
			dfs(board, x, y, rLen, cLen);
		}
		return board;
    }

	private static void dfs(char[][] board, int x, int y, int rLen, int cLen) {
		if(x < 0|| x >= rLen || y < 0 || y >= cLen || board[x][y] != 'E') 
			return;
		
		int mine = countMine(board, x, y, rLen, cLen);
		
		if(mine > 0) {
			board[x][y] = (char) ('0' + mine);
			
		} else {
			// If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed 
			// blank 'B' and all of its adjacent unrevealed squares should be revealed recursively.
			board[x][y] = 'B';
			for(int[] d : dirs) {
				dfs(board, x+d[0], y+d[1], rLen, cLen);
			}
		}
	}

	private static int countMine(char[][] board, int x, int y, int rLen, int cLen) {
		int count = 0;
		
		for(int[] dir : dirs){
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (nextX >= 0 && nextX < rLen && nextY >= 0 && nextY < cLen && board[nextX][nextY] == 'M')
                    count++;
        }
		
		return count;
	}
}
