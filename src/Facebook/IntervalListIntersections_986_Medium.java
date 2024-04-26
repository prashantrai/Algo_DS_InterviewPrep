package Facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalListIntersections_986_Medium {

	public static void main(String[] args) {

		int[][] A = {{0,2},{5,10},{13,23},{24,25}}; 
		int[][] B = {{1,5},{8,12},{15,24},{25,26}};
		
		int[][] res = intervalIntersection(A, B);
		System.out.println("Expected: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]    Actual: " + Arrays.deepToString(res));
	}
	
	/*
    Time Complexity: O(M+N), where M,N are the lengths of A and B respectively.

    Space Complexity: O(M+N), the maximum size of the answer.
    */
    
    public static int[][] intervalIntersection(int[][] A, int[][] B) {
        
        List<int[]> ans = new ArrayList<>();
        int i=0;
        int j=0;
        
        while (i<A.length && j<B.length) {
            // Let's check if A[i] intersects B[j].
            // lo - the startpoint of the intersection between 2 arrays
            // hi - the endpoint of the intersection between 2 arrays
            int lo = Math.max(A[i][0], B[j][0]);
            int hi = Math.min(A[i][1], B[j][1]);
            
            if(lo <= hi) {
                ans.add(new int[]{lo, hi});
            }
            
            // Remove the interval with the smallest endpoint
            if(A[i][1] < B[j][1])
                i++;
            else
                j++;
            
        }
        
        return ans.toArray(new int[ans.size()][]);
    }
}
