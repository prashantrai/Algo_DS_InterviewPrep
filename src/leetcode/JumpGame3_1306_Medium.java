package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class JumpGame3_1306_Medium {

	public static void main(String[] args) {

		int[] arr = {4,2,3,0,3,1,2};
		System.out.println(canReach(arr, 5));
	}
	//--Microsoft
	//--https://leetcode.com/problems/jump-game-iii/
	//-- https://leetcode.com/problems/jump-game-iii/discuss/510698/JAVA-BFS-beats-100-in-time-and-space\
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
	
}
