package leetcode;

public class NumberOfProvinces_or_FriendCircle_547_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* DSF::
	 * Time complexity : O(n^2). The complete matrix of size n^2 is traversed.
	 * Space: O(n), visited array of size N. 
	 * */

	public static int findCircleNum(int[][] M) {
        int[] visited = new int[M.length];
        //Arrays.fill(visited, false);
        int count = 0;
        for(int i=0; i<M.length; i++) {
            if(visited[i] == 0) {
                dsf(M, visited, i);
                count++;
            }
        }
        
        return count;
    }
    
    private static void dsf(int[][] M, int[] visited, int i) {
        
        for(int j=0; j<M.length; j++) {
            if(M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dsf(M, visited, j);
            }
        }
        
    }
    
    /* Reference: https://leetcode.com/problems/number-of-provinces/discuss/101336/Java-solution-Union-Find
     * 
     *  Union Find:: Most efficient implementation of the union-find algorithm should include 
     *  both path compression and union by rank/size.
     *  
     *  
     */
}
