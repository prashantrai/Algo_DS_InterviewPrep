package doordash;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class WallsAndGates_286_Medium {

	public static void main(String[] args) {
		
		/*int[][] rooms  = {
							{2147483647, -1, 0, 2147483647},
							{2147483647, 2147483647, 2147483647, -1},
							{2147483647, -1, 2147483647, -1},
							{0, -1, 2147483647, 2147483647}
							};*/
		
		int[][] rooms  = {
				{EMPTY, -1, GATE, EMPTY},
				{EMPTY, EMPTY, EMPTY, -1},
				{EMPTY, -1, EMPTY, -1},
				{GATE, -1, EMPTY, EMPTY}
				};
		
		
		// Expected:  [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
		wallsAndGates(rooms);
		
		for(int[] room : rooms) { 
			System.out.println(Arrays.toString(room));
		}

	}
	
	private static final int EMPTY = Integer.MAX_VALUE;
    private static final int GATE = 0;
    static int[][] DIRECTIONS = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    /*private static final List<int[]> DIRECTIONS = Arrays.asList(
        new int[] { 1,  0},
        new int[] {-1,  0},
        new int[] { 0,  1},
        new int[] { 0, -1}
    );*/
    
    
    //https://leetcode.com/problems/walls-and-gates/
    
    public static void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        if (m == 0) return;
        int n = rooms[0].length;
        
        //Queue<int[]> dq = new ArrayDeque<>();
         Queue<int[]> dq = new LinkedList<>();
        
        for (int r = 0; r < m; r++) {
			for (int c = 0; c < n; c++) {
                if(rooms[r][c] == GATE) 
                    //dq.offer(new int[]{r,c});
                    dq.add(new int[]{r,c});
            }
        }
        
        while (!dq.isEmpty()) {
            int[] point = dq.poll();
            int row = point[0];
            int col = point[1];
            for (int[] dir : DIRECTIONS) {
                int r = row + dir[0];
				int c = col + dir[1];
                
                if(r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                 continue;   
                }
                rooms[r][c] = rooms[row][col] + 1;
                dq.add(new int[]{r,c});
            }
        }
        
    }

}
