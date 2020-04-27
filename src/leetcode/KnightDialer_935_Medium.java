package leetcode;

import java.util.HashMap;
import java.util.Map;

public class KnightDialer_935_Medium {

	public static void main(String[] args) {

		int res = knightDialer(1);
		System.out.println("Expected: 10, Actiual: " + res);
		
		res = knightDialer(2);
		System.out.println("Expected: 20, Actiual: " + res);
		
		res = knightDialer(3);
		System.out.println("Expected: 46, Actiual: " + res);
		
		//--Solution 2
		res = knightDialer_2(1);
		System.out.println("Expected: 10, Actiual: " + res);
		
		res = knightDialer_2(2);
		System.out.println("Expected: 20, Actiual: " + res);
		
		res = knightDialer_2(3);
		System.out.println("Expected: 46, Actiual: " + res);
	}
	
	//Question : https://leetcode.com/problems/knight-dialer/
	
	//--Refer below link for algorithm and explaination of the problem
    //--https://leetcode.com/problems/knight-dialer/discuss/190787/How-to-solve-this-problem-explained-for-noobs!!!
    
    private static final int MOD = 1_000_000_007; // 10^9 + 7
    private static final int[][] directions 
        = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
    
    private static Map<String, Long> cache = new HashMap<>();
    
    public static int knightDialer(int N) {
        
        long res = 0;
        
        for(int r=0; r<4; r++) {
            for(int c=0; c<3; c++) {
                
                res = (res+helper(r, c, N));
                res = res % MOD;
            }
        }
        return (int) res;
    }
    
    private static long helper(int r, int c, int n) {
        if(r<0 || r>=4 || c<0 || c>=3 || (r==3 && c != 1)) {// i.e. if '*' in num pad
            return 0;
        }
        
        if(n == 1) return 1;
        
        String key = r+"_"+c+"_"+n;
        
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        
        long res = 0;        
        for(int dirs=0; dirs<directions.length; dirs++) {
            res += helper(r+directions[dirs][0], c+directions[dirs][1], n-1) % MOD;
        }
        cache.put(key, res);
        
        return res;
    }
    
    
    //--2nd Solution : https://leetcode.com/problems/knight-dialer/discuss/190787/How-to-solve-this-problem-explained-for-noobs!!!
    
    public static final int max = (int) Math.pow(10, 9) + 7;
	
    public static int knightDialer_2(int n) {
       // A 3D array to store the solutions to the subproblems
       long M[][][] = new long[n + 1][4][3];
       long s = 0;
       //do n hops from every i, j index (the very requirement of the problem)
       for(int i = 0; i < 4; i++) {
          for(int j = 0; j < 3; j++) {
             s = (s + paths(M, i, j, n)) % max;
          }
       }
       return (int) s;
    }

    private static long paths(long[][][] M, int i, int j, int n) {
       // if the knight hops outside of the matrix or to * return 0 
       //as there are no unique paths from here
       if(i < 0 || j < 0 || i >= 4 || j >= 3 || (i == 3 && j != 1)) return 0;
       if(n == 1) return 1;
       //if the subproblem's solution is already computed, then return it
       if(M[n][i][j] > 0) return M[n][i][j];
       //else compute the subproblem's solution and save it in memory
       M[n][i][j] = paths(M, i - 1, j - 2, n - 1) % max + // jump to a
                    paths(M, i - 2, j - 1, n - 1) % max + // jump to b
                    paths(M, i - 2, j + 1, n - 1) % max + // jump to c
                    paths(M, i - 1, j + 2, n - 1) % max + // jump to d
                    paths(M, i + 1, j + 2, n - 1) % max + // jump to e
                    paths(M, i + 2, j + 1, n - 1) % max + // jump to f
                    paths(M, i + 2, j - 1, n - 1) % max + // jump to g
                    paths(M, i + 1, j - 2, n - 1) % max; // jump to h
       return M[n][i][j];
    }

}
