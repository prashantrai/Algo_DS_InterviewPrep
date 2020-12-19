package Oracle;

public class UniquePaths_62_Medium {

	public static void main(String[] args) {
		
		System.out.println("Expected: 28, Actual: " + uniquePaths(3, 7));
		System.out.println("Expected: 3, Actual: " + uniquePaths(3, 2));
		System.out.println("Expected: 28, Actual: " + uniquePaths(7, 3));
		System.out.println("Expected: 6, Actual: " + uniquePaths(3, 3));
		
		System.out.println("Expected: 28, Actual: " + uniquePaths_UsingCombinationFormula(3, 7));
		System.out.println("Expected: 3, Actual: " + uniquePaths_UsingCombinationFormula(3, 2));
		
	}
	
	/*
	 * https://leetcode.com/problems/unique-paths/
	 * Time: O(m*n) Space: O(m*n) 
	 * 
	 * Other solutions with O(n) time and O(1) space: 
         https://leetcode.com/problems/unique-paths/discuss/22958/Math-solution-O(1)-space
         https://leetcode.com/problems/unique-paths/discuss/22981/My-AC-solution-using-formula
	 * */
	
	public static int uniquePaths(int m, int n) {
        int[][] grid = new int[m][n];
        
        for(int r = m-1; r>=0; r--) {
            for(int c = n-1; c>=0; c--) {
                
                if(r == m-1 && c == n-1) { //finish cell
                    grid[r][c] = 1;
                    
                } else {
                    if(r < m-1) 
                        grid[r][c] += grid[r+1][c];
                    
                    if(c < n-1) 
                        grid[r][c] += grid[r][c+1];
                }
            }
        }
        
        return grid[0][0];
    }
    
    /* 
    Refer below to solve the problem using formula of Combination 
    e.g. for m=3 and n=7 the formula is (m+n)Cm OR (m+n)Cn i.e  10!/7!*(10-7)! OR 10!/3!*(10-3)!
    (they both same) and resul will be 
    https://www.youtube.com/watch?v=fpnNaAU0iPk&ab_channel=Don%27tMemorise
    */

	// https://leetcode.com/problems/unique-paths/discuss/22958/Math-solution-O(1)-space
	public static int uniquePaths_UsingCombinationFormula (int m, int n) {
        if(m == 1 || n == 1)
            return 1;
        m--;
        n--;
        long res = 1;
        int j = 1;
        
        /*
         Why i started from m+1, see below
         (m+n)!/(m!*n!)
			= 1*2*...*m*(m+1)*...*(m+n)/(1*2*...*m*n!)
			= (m+1)*(m+2)*...*(m+n)/(1*2*...*n)
			
			Because m! has been cancelled we have (m+1)! left on top and n! on bottom
			
			Within this loop, i is from m+1 to m+n (which is n numbers), j is from 1 to n.
			So what the loop doing is calculating this formula accumativly: 
				res=1, res=res*(m+1)/1, res=res*(m+2)/2...
			
			If m and n are very big number, calculating (m+n)! may cause overflow, 
			which can be avoided by skipping compute (m+n)!
			By the way, I don't think it's necessary to ensure m is the smaller number. 
			The following code is enough.
         * */
        
        for(int i = m+1; i <= m+n; i++, j++){       // Instead of taking factorial, keep on multiply & divide
            res *= i;
            res /= j;
        }
            
        return (int)res;
    }
	
}
