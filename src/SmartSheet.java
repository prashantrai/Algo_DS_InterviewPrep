
public class SmartSheet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


/*
 // Question: Given a width and height and a list of existing tiles, compute
//           the first available position a new tile can fit into.
//
// ---------------------------------------------------------------------------------------------------
// Here is an example scenario, from the perspective of a user:
// 
//         0   1   2   3   4   5   6  
//       -----------------------------
//     0 |   | a | a |   |   |   |   |
//       -----------------------------
//     1 |   | a | a |   |   |   |   |
//       -----------------------------
//     2 |   |   |   |   |   |   |   |
//       -----------------------------
//     3 |   | b | b | b | b | b |   |
//       -----------------------------
//     4 |   | b | b | b | b | b |   |
//       -----------------------------
//     5 |   |   |   |   |   |   |   |
//       -----------------------------
//     6 |   |   | c | c | c |   |   |
//       -----------------------------
//
//   NOTE: You only have access to the data BELOW this line.
//   The above diagram is for illustrative pruposes only
//
//   Parameters to this specific example:
//   gridWidth: 7
//   gridHeight: 7
//
//   Tiles present:
//   a = 2x2 at (1, 0)
//   b = 5x2 at (1, 3)
//   c = 3x1 at (2, 6)
//
// Example tile to add: d = 2x2





import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
    
    List<Tile> tiles =new ArrayLst<>();
     
    int[][] grid = getGrid(tiles, gridWidth, gridHeight);
    canFit(grid, gridWidth, gridHeight);
  }
  
  private int[][] getGrid(List<Tile> tiles, int gridWidth, int gridHeight) {
        int[][] grid =new int[gridWidth][gridHeight];
    
        for(Tile tile : tiles) {
            
            for(int i=0; i<h; i++) {
                
              if(i < x) continue;
              
               for(int j=0; j<w; j++) {
                 
                  if(i >= x && j>=y) {
                    grid[i][j] = 77; // relpace with a
                  }
                 
               }
            }
          
        }
  }
  
  public static boolean canFit(int[][] grid, int gridWidth, int gridHeight) {
        
  }
  
  
  class Tile {
    int h=0;
    int w=0;
    int x=0;
    int y=0;
    
  }
  
  
}

 */