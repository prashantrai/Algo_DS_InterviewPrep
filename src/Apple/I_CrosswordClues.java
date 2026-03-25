package Apple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class I_CrosswordClues {
	// Test cases
    public static void main(String[] args) {

        // Example 1
        int w1 = 3;
        String p1 = "#.......#";
        printResult(findClues(w1, p1));

        // Example 2
        int w2 = 8;
        String p2 = "...##......#............##...######...##............#......##...";
        printResult(findClues(w2, p2));

        // Edge Case: All barriers
        int w3 = 3;
        String p3 = "#########";
        printResult(findClues(w3, p3));

        // Edge Case: Single row
        int w4 = 5;
        String p4 = "..#..";
        printResult(findClues(w4, p4));

        // Complex mixed case
        int w5 = 4;
        String p5 = ".#..#..#....";
        printResult(findClues(w5, p5));
    }

    
    /*
    Interview Script:: 
	    I first convert the input string into a 2D grid.
		Then I scan the grid left-to-right, top-to-bottom.
		
		For each non-barrier cell, I check if it starts a clue 
		by seeing if its top or left neighbor is a barrier or boundary.
		
		If it is a start, I check whether it forms a valid across or 
		down word by checking the right and bottom cells.
		
		I assign clue numbers sequentially and store them.
		
		Finally, I return both arrays.
		This runs in O(N) time since we scan the grid once."
		
		* Important Rule (This is the key confusion):: 
			We do NOT add because current cell is "." 
			We add only if:
				Across:
				LEFT is # AND RIGHT is .
				Down:
				TOP is boundary OR # AND BOTTOM is .
			We check neighbors, not the current cell.
	
     * */
    
    // Time: O(n), Where N = total cells, we scan once 
    // Space: O(n), For grid + output arrays
    private static int[][] findClues(int width, String puzzle) {

        int total = puzzle.length();
        int height = total / width;

        // Build grid
        char[][] grid = new char[height][width];

        int index = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = puzzle.charAt(index++);
            }
        }

        List<Integer> across = new ArrayList<>();
        List<Integer> down = new ArrayList<>();

        int clueNumber = 1;

        // Scan grid
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {

                // Skip barriers
                if (grid[r][c] == '#') {
                    continue;
                }
                boolean start = false;
                // Check if start position
                if (r == 0 || grid[r - 1][c] == '#') {
                    start = true;
                }

                if (c == 0 || grid[r][c - 1] == '#') {
                    start = true;
                }

                if (!start) {
                    continue;
                }

                boolean isAcross = false;
                boolean isDown = false;

                // Check Across
                if ((c == 0 || grid[r][c - 1] == '#') 
                			&& c + 1 < width && grid[r][c + 1] != '#') {

                    isAcross = true;
                }

                // Check Down
                if ((r == 0 || grid[r - 1][c] == '#') 
                		&& r + 1 < height &&
                    grid[r + 1][c] != '#') {

                    isDown = true;
                }

                // Assign number
                if (isAcross || isDown) {
                    if (isAcross) {
                        across.add(clueNumber);
                    }
                    if (isDown) {
                        down.add(clueNumber);
                    }
                    clueNumber++;
                }
            }
        }

        // Convert to arrays
        int[] acrossArr = new int[across.size()];
        int[] downArr = new int[down.size()];

        for (int i = 0; i < across.size(); i++) {
            acrossArr[i] = across.get(i);
        }

        for (int i = 0; i < down.size(); i++) {
            downArr[i] = down.get(i);
        }

        return new int[][]{acrossArr, downArr};
    }

    
    // Helper to print arrays
    private static void printResult(int[][] res) {
        System.out.println("Across: " + Arrays.toString(res[0]));
        System.out.println("Down  : " + Arrays.toString(res[1]));
        System.out.println();
    }

	 
}



/**
Inputs represent a crossword: the width of the puzzle, and an un-delimited string of the 
contents of the puzzle (left-to-right, top-to-bottom, with barriers represented as "#").
 
Each cell below or to the right of a barrier is the start of a clue; these are numbered 
starting from "1", ascending left-to-right then top-to-bottom.

Horizontal clues ("across") will start to the right of a barrier; vertical clues ("down") 
start below a barrier.

All cells will appear in both a horizontal and a vertical clue, and each clue is a minimum 
of two characters. Assume provided inputs are valid, adhering to these clue requirements as 
well as being a well-formed rectangle. Return 2 arrays, containing the indexes of "across" 
and "down" clues respectively.

Example input 1: 3, "#.......#"
Example output 1: [1, 3, 4], [1, 2, 3]

Reference 1:
#..
...
..#

Numbering example 1:
#  ¹  ² 
³  .  .
⁴  .  #	

Example input 2: 8, "...##......#............##...######...##............#......##..."
Example output 2: 
[1, 4, 7, 8, 9, 11, 12, 14, 19, 20, 21, 22], 
[1, 2, 3, 4, 5, 6, 8, 10, 13, 14, 15, 16, 17, 18]

Reference 2:
...##...
...#....
........
##...###
###...##
........
....#...
...##...

Numbering example 2:
¹  ²  ³  #  #  ⁴  ⁵  ⁶  
⁷  .  .  #  ⁸  .  .  .  
⁹  .  .  ¹⁰ .  .  .  .  
#  #  ¹¹ .  .  #  #  #  
#  #  #  ¹² .  ¹³ #  #  
¹⁴ ¹⁵ ¹⁶ .  .  .  ¹⁷ ¹⁸
¹⁹ .  .  .  #  ²⁰ .  .  
²¹ .  .  #  #  ²² .  . 

 
* */
