package SoFi;

import java.util.LinkedList;
import java.util.Queue;

public class JumpGame3_1306_Medium {

	public static void main(String[] args) {

		int[] arr = {4,2,3,0,3,1,2};
		System.out.println(canReach(arr, 5));
		
		System.out.println(canReach(arr, 0));
	}
	//--Microsoft
	//--https://leetcode.com/problems/jump-game-iii/
	//-- https://leetcode.com/problems/jump-game-iii/discuss/510698/JAVA-BFS-beats-100-in-time-and-space\
	
	// Time and Space: O(N)
	public static boolean canReach(int[] arr, int start) {
        
        boolean[] visited = new boolean[arr.length];
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;
        
        while(!q.isEmpty()) {
            int current = q.poll();    
            int idx_left  = current - arr[current];
            int idx_right = current + arr[current];
            
            if((idx_left >= 0 && arr[idx_left] == 0) 
               || (idx_right < arr.length && arr[idx_right] == 0))  {
                
                return true;
            }
            
            if(idx_left >= 0 && !visited[idx_left]) {
                q.offer(idx_left);
                visited[idx_left] = true;
            }
            
            if(idx_right < arr.length && !visited[idx_right]) {
                q.offer(idx_right);
                visited[idx_right] = true;
            }
            
        }
        return false;
    }
	
	//DFS
	/* https://leetcode.com/problems/jump-game-iii/discuss/465602/JavaC%2B%2BPython-1-Line-Recursion
	 * 
	 * Time and space: O(N)
	 * 
	 * 1. Check 0 <= i < A.length
	 * 2. flip the checked number to negative A[i] = -A[i]
	 * 3. If A[i] == 0, get it and return true
	 * 4. Continue to check canReach(A, i + A[i]) and canReach(A, i - A[i])
	 * 
	 * */
	public static boolean canReach_DFS(int[] A, int i) {
        return 0 <= i && i < A.length && A[i] >= 0 
        		&& (
        				(A[i] = -A[i]) == 0 
        					|| canReach(A, i + A[i]) 
        					|| canReach(A, i - A[i])
        			);
    }
	
}
