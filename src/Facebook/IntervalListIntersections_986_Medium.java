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
            // as it has been covered and move to the next index
            // e.g. in ranges {0,2} and {1,5}, range {0,2} is shorter and covered 
            // and the intersection has been added to the answer 
            // now we'll increase the i value to find the next range 
            // and check if that intersect with the current bigger range {1,5} 
            // and if it does, add to the ans
            if(A[i][1] < B[j][1])
                i++;
            else
                j++;
            
        }
        
        return ans.toArray(new int[ans.size()][]);
    }
}
