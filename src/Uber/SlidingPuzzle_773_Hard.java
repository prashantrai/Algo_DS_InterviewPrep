package Uber;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class SlidingPuzzle_773_Hard {

	public static void main(String[] args) {
		
		int[][] board = {
							{1,2,3},
							{4,0,5}
						};
		
		System.out.println("Expected: 1, Acual: "+ slidingPuzzle(board));
		
		int[][] board2 = {
							{1,2,3},
							{5,4,0}
						};
		System.out.println("Expected: -1, Acual: "+ slidingPuzzle(board2));
		
		int[][] board3 = {
							{4,1,2},
							{5,0,3}
						};
		System.out.println("Expected: 5, Acual: "+ slidingPuzzle(board3));
		

	}
	
	/* Reference: 
        https://leetcode.com/problems/sliding-puzzle/discuss/146652/Java-8ms-BFS-with-algorithm-explained
        
	 * 
	 * 
	 * why we should use this as direction pad: PUT 2X3 BOARD AS MATRIX instead of string ,
	 * even though we use indices in string as position. 
	 * For instance, for 2x3 board [[1,0,2], [5,4,6]] it should be like

		1 0 2
		5 4 6
		
		and as I quote,
		
		A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
		
		Now you see where little ZERO could move? Yep, for 4-directions it could only swaps 
		with 1, 2 or 4, indicating their indices is {0, 2, 4} in the string "102546", and so on.
	 * 
	 * */
	
	/*
	 * https://leetcode.com/problems/sliding-puzzle/discuss/1524764/Java-or-TC%3A-O((MN)!)-or-SC-%3A-O((MN)!)-or-Bit-Manipulation-%2B-Double-Ended-BFS
	 * 
	 * Time Complexity: O(V + E).
	 * 	V = Number of possible states = (M*N)!.
	 * 	E = 3 * V, because there can be a maximum of 3 edges from each vertex.
	 * Total TC = O((MN)! + 3*(MN)!) = O((MN)!) = O(4 * 6!)
	 * 
	 * FINAL time Complexity: O((MN)!)
	 *
	 * Space Complexity: O((MN)!) to save each state in begin, end and visited
	 * collections = O(6!)
	 *
	 * M = Number of rows. N = Number of cols.
	 * 
	 * 
	 * Analysis:
		There are at most 6! permutation of the 6 numbers: 0~5. For each permutation, 
		cost space O(6); String.indexOf() and String.equals() cost time O(6). 
		Therefore, space and time both cost 6 * 6! = 4320.
	 * 
	 */
	
	public static int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = "";  //can be replaced with string builder as well
        
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                start += board[i][j];
            }
        }
        
        // all the positions 0 can be swapped to
        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };
        
        Set<String> visited = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();
        q.offer(start);
        visited.add(start);
        
        int res = 0;
        
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                String cur = q.poll();                
                
                if(target.equals(cur)) {
                    return res;
                }
                
                int zero = cur.indexOf('0');
                
                // swap if poissible
                for(int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if(visited.contains(next))
                        continue;
                    
                    visited.add(next);
                    q.offer(next);
                    
                }
            }
            res++;
            
        }
       return -1; 
    }
    
    private static String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        
        return sb.toString();
    }

}
