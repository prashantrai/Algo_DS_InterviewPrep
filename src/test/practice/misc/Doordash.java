package test.practice.misc;

class Doordash {
  /**
   * @param matrix, a 2-d array (list of lists) of integers
   * @return the size of the largest contiguous block (horizontally/vertically connected) of numbers with the same value
  
      {1,1,1,2,2},
      {5,1,5,2,2}, 
      {3,4,2,1,2}
  
  	Look at this for non-recursive solutions:: https://leetcode.com/articles/maximal-square/  
  
  **/
  public int largestContinuousBlock(int [][] matrix){
    return -1;
  }

  public static void main(String[] args) {  
	  Doordash s = new Doordash();
    
    int expected1 = 2;
    int [][] matrix1 = new int[][]{
      {1,2,3},
      {4,1,6},
      {4,5,1}
    };
    
    int expected2 = 4;
    int [][] matrix2 = new int[][]{
      {1,1,1,2,4},
      {5,1,5,3,1}, 
      {3,4,2,1,1}
    };
    
    int expected3 = 11;
    int [][] matrix3 = new int[][]{
      {3,3,3,3,3,1}, 
      {3,4,4,4,3,4}, 
      {2,4,3,3,3,4},
      {2,4,4,4,4,4}
    };
    
    int expected4 = 24;
    int [][] matrix4 = new int[][]{
      {0,0,0,0,0}, 
      {0,0,0,0,0},
      {0,0,1,0,0},
      {0,0,0,0,0},
      {0,0,0,0,0}
    };

    
    
    s.runTestCase(matrix1,expected1);
    s.runTestCase(matrix2,expected2);
    s.runTestCase(matrix3,expected3);
    s.runTestCase(matrix4,expected4);
    
  }
  
  static int current_num=0;
  public static Integer countBlock(int[][] m) {
      
      if(m == null || m.length ==0) return null;
      int max = 0;
      for(int r=0; r<m.length; r++) {
          for(int c=0; c<m.length; c++) {
              current_num = m[r][c];
              int count  = helper(m, r, c, 0, current_num);
              //System.out.println("count:: "+count);
              max = max < count ? count : max;
          }
      }
      System.out.println("max: "+max);
      return max;
  }
  
 
  public static int helper(int[][] m, int r, int c, int count, int num) {
      
      if(r<0 || r>=m.length || c<0 || c>=m[0].length || m[r][c] == 99) {
          return count;
      }
      
      num  = m[r][c];
      if(num != current_num) {
        return count;
      }
      count++;
      
      m[r][c] = 99;
      
      count = helper(m, r, c+1, count, num);
      count = helper(m, r, c-1, count, num);
      count = helper(m, r+1, c, count, num);
      count = helper(m, r-1, c, count, num);
      
      return count;
      
  }
  
  
  public void runTestCase(int [][] matrix, int expected){
    int result = countBlock(matrix);
    if(result != expected){
      System.out.println("\n--Failed--");
      printMatrix(matrix);
      System.out.print("Expected: ");
      System.out.println(expected);
      System.out.print("Actual: ");
      System.out.println(result);
    } else {
      System.out.println("--PASSED--");
    }
    
  }
  
  private static void printMatrix(int [][] matrix){
    for (int i = 0 ; i < matrix.length ; i++){
      System.out.print('[');
      for (int j = 0 ; j < matrix[0].length ; j++){
        System.out.print(matrix[i][j]);
      }
      System.out.println(']');
    }
    System.out.println();
  }
}