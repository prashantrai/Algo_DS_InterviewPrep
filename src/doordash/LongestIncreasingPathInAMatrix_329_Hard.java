package doordash;

public class LongestIncreasingPathInAMatrix_329_Hard {

	public static void main(String[] args) {
		int[][] matrix = {{9,9,4},{6,6,8},{2,1,1}};
		System.out.println("Expected: 4, Actual: " + longestIncreasingPath(matrix));

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
    private static int m, n;

    public static int longestIncreasingPath(int[][] matrix) {
        if(matrix.length == 0) return 0;
        m = matrix.length;
        n = matrix[0].length;
        
        int[][] cache = new int[m][n];
        int ans = 0;
        
        for(int i=0;i<m; i++) {
            for(int j=0; j<n; j++) {
                ans = Math.max(ans, dfs(matrix, i, j, cache));
            }
        }
        return ans;
    }
    
    private static int dfs(int[][] mat, int i, int j, int[][] cache) {
        if(cache[i][j] != 0) 
            return cache[i][j];
        
        int max = 1;
        
        for(int[] d : dirs) {
            int x = i + d[0];
            int y = j + d[1];
            if(x>=0 && x<m && y>=0 && y<n && mat[x][y] > mat[i][j]) {
                int len = 1 + dfs(mat, x, y, cache);
                max = Math.max(max, len);
            }
        }
        
        cache[i][j]= max;
        return max;
    }
	
}
