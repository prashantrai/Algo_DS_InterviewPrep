/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

 /*
 4 direction : up, down, left, right
 grid = [
  [1, 1, 1, 1, 0],
  {1, 1, 0, 1, 0},
  {1, 1, 0, 0, 0},
  {0, 0, 0, 0, 0}
]

result -> 1

grid = [
  {1, 1, 0, 0, 0},
  {1, 1, 0, 0, 0},
  {0, 0, 1, 0, 0},
  {0, 0, 0, 1, 1}
]
result -> 3

grid = [
  {4, 2, 0, 0, 0},
  {1, 3, 0, 0, 0},
  {0, 0, 8, 0, 0},
  {0, 0, 0, 2, 9}
]

1: 10
2: 8
3: 11

return 11;
 */

class OkCoin {
  public static void main(String[] args) {
    
    int[][] test1 = new int[][]{{1, 1, 1, 1, 0},{1, 1, 0, 1, 0},{1, 1, 0, 0, 0},{0, 0, 0, 0, 0}};
    
    //System.out.println("Expected: 1, Actual: "+ numberOfIsland(test1));
    
    
    int[][] test2 = new int[][]{{1, 1, 0, 0, 0},{1, 1, 0, 0, 0},{0, 0, 1, 0, 0},{0, 0, 0, 1, 1}};

    //System.out.println("Expected: 3, Actual: "+ numberOfIsland(test2));
    
    int[][] test3 = new int[][]{
    							{4, 2, 0, 0, 0},
    							{1, 3, 0, 0, 0},
    							{0, 0, 8, 0, 0}, 
    							{0, 0, 0, 2, 9}
    						};

    System.out.println("Expected: 11, Actual: "+ numberOfIsland(test3));
  }

  // [LC 1219]:https://leetcode.com/problems/path-with-maximum-gold/
  // Time: O(N^2)
  //
  private static int numberOfIsland(int[][] grid) {
    
    if(grid == null || grid.length == 0) return Integer.MIN_VALUE;
    
    int max = 0;
    
    for(int r=0; r<grid.length; r++) {
      for(int c=0; c<grid[0].length; c++) {
        //if(grid[r][c] == 1) {
        if(grid[r][c] >= 1) {
          int currMax = helper(grid, r , c, 0);
          max = Math.max(currMax, max);
          //count++;
        }
      }
    }
    
    //System.out.println("maxx: " + maxx);
    return max;
  }
  
  //static int maxx;
  private static int helper(int[][] grid, int r, int c, int curr) {
    
    if(r >= grid.length || r < 0 
       || c >= grid[0].length || c < 0 
       || grid[r][c] <= 0) {
    
      return curr;
    }
    
    curr += grid[r][c];
    
    grid[r][c] = 0; //visited
    
    curr = helper(grid, r, c+1, curr);
    curr = helper(grid, r, c-1, curr);
    curr = helper(grid, r+1, c, curr);
    curr = helper(grid, r-1, c, curr);
    
    //maxx = Math.max(curr, maxx);
    return curr;
    
  }
  
  
}

