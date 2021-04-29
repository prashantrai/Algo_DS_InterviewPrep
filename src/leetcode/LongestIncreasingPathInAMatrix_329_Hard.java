package leetcode;

public class LongestIncreasingPathInAMatrix_329_Hard {

	public static void main(String[] args) {

	}

	// Taken from: https://leetcode.com/problems/longest-increasing-path-in-a-matrix/discuss/78308/15ms-Concise-Java-Solution
	
	/*
	 [Complexity Analysis] - https://medium.com/@bill800227/leetcode-329-longest-increasing-path-in-a-matrix-ea64f5cf14ad
		Time complexity：O(n * m)
			The main point here is to consider the complexity of each recursive call after the 
			two-layer for loop. If memoization is not optimized, it is O(n * m *(4^(n * m) )), 
			no wonder it will exceed However, after optimization, basically we only need to fill 
			in the memo (it will cost O(m * n) in total, but use amortized analysis to 
			divide m*n numbers into O(1)), but multiply it by The result of the original 
			two-layer for loop is still O(n*m).
		
		Space complexity：O(n * m)
			The space complexity of recursion mainly depends on how deep the recursion depth can be, 
			so the worst case here is O(n * m), and at the same time O(n * m) memory is used to store 
			the result, so space complexity It is O(n * m). 
	 * */
	public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

	public int longestIncreasingPath(int[][] matrix) {
	    if(matrix.length == 0) return 0;
	    
	    int m = matrix.length; 
	    int n = matrix[0].length;
	    
	    int[][] cache = new int[m][n];
	    
	    int max = 1;
	    for(int i = 0; i < m; i++) {
	        for(int j = 0; j < n; j++) {
	            int len = dfs(matrix, i, j, m, n, cache);
	            max = Math.max(max, len);
	        }
	    }   
	    return max;
	}

	public int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
	    if(cache[i][j] != 0) return cache[i][j];
	    
	    int max = 1;
	    
	    for(int[] dir: dirs) {
	        int x = i + dir[0];
	        int y = j + dir[1];
	        
	        if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) continue;
	        
	        int len = 1 + dfs(matrix, x, y, m, n, cache);
	        
	        max = Math.max(max, len);
	    }
	    cache[i][j] = max;
	    return max;
	}
	
}
