package doordash;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class MinimumKnightMoves_1197_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * BFS & DFS: https://wentao-shao.gitbook.io/leetcode/graph-search/1197.minimum-knight-moves
	 * BFS With Point class: https://neo82.tistory.com/26
	 * BFS: https://www.programmersought.com/article/63865491006/
	 * BFS: https://leetcode.jp/leetcode-1197-minimum-knight-moves-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	 * BFS (with time and space): https://www.cnblogs.com/Dylan-Java-NYC/p/11934828.html
	 * 
	 * BFS (Time and Space mentioned in the post):  https://shineboy2013.github.io/2020/04/20/lee-1197/
	 * Another BFS with 2d array as visited:
	 * 			https://shineboy2013.github.io/2020/04/20/lee-1197/
	 * 
	 * Look here for explanation: https://blog.csdn.net/qq_17550379/article/details/101195668
	 * 
	 * BFS with boolean 2d arr:
	 * https://shineboy2013.github.io/2020/04/20/lee-1197/
	 * 
	 * DFS: https://walkccc.me/LeetCode/problems/1197/
	 * 
	 * 
	 * For better explanation must watch: https://www.youtube.com/watch?v=UqFXSGeFHTI 
	 * 
	 * */

	/** Reference Leetcode Premium Answer: 
	 * 
	 * Time: O(n^2) :: O( (MAX(|x|, |y|))^2 ) 
	 *  		, Due to nature of BFS before reaching the target we will have covered all the neighborhood
	 * that are closer to the start point. The aggregate of these neighborhoods forms a circle and the area can 
	 * be approximated by area of a square with an edge length of MAX(|x|, |y|). The number of cells wihin the 
	 * square would be (MAX(|x|, |y|))^2  
	 * 
	 * Space: O(n^2) :: O( (MAX(|x|, |y|))^2 )
	 * 		> We have used 2 data structures i.e. Queue and Set
	 * 		> For Queue: O(n)
	 * 		> For Set: O(n^2) :: O( (MAX(|x|, |y|))^2 )
	 * 					As for the Set, it will contain every element that visited 
	 * 					which is O( (MAX(|x|, |y|))^2 ) as we estimated in Time Complexity analysis
	 * 					and as a result the space complexity for Set is O( (MAX(|x|, |y|))^2 ).
	 * 	 
	 * */
	
	
	// Directions
	private final int [][] DIRS = {
											{-1, -2}, 
											{-1, 2}, 
											{1, -2}, 
											{1, 2}, 
											
											{-2, -1}, 
											{-2, 1}, 
											{2, -1}, 
											{2, 1}
										};
								
	public int minKnightMoves(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		
		Queue<int[]> q = new ArrayDeque<>(); // Record the nodes of each layer
		q.offer(new int[] {0, 0});
		
		//Whether the record has been visited
		Set<String> visited = new HashSet<>();
		visited.add("0,0");
		
		int result = 0;
		
		while(!q.isEmpty()) {
			int size = q.size(); //get the number of node in each layer
			
			for(int i=0; i<size; i++) {
				int[] cur = q.poll();
				int curX = cur[0];
				int curY = cur[1];
				
				if(curX == x & curY == y) return result; // found answer
				
				for(int[] dir : DIRS) {
					int newX = curX + dir[0];
					int newY = curY + dir[1];
					
					/** https://www.programmersought.com/article/63865491006/
					
					Why newX >= -1 && newY >= -1) instead of newX >= 0 && newY >= 0)?

					The co-or in general to compute the knight moves are: (x-2, y-1) (x-2, y+1), (x-1, y-2)
					where for all x,y>=2 the next “move” will always be >=0 (i.e. in the first quadrant). 
					
					Only for x=1/y=1, the next move may fall in the negative quad, 
					for example (x-2,y-1) or (x-1, y-2), and hence x=-1 y=-1 boundary is considered.
					*/
					
					if(!visited.contains(newX + "," + newY) && newX >= -1 && newY >= -1) {
						q.offer(new int[] {newX, newY});
						visited.add(newX + "," + newY);
						
					}
				}
				result++;
			}
		}
		
		return -1;
		
	}
	
	/** https://www.programmersought.com/article/63865491006/
	
	Why newX >= -1 && newY >= -1) instead of newX >= 0 && newY >= 0)?

	The co-or in general to compute the knight moves are: (x-2, y-1) (x-2, y+1), (x-1, y-2)
	where for all x,y>=2 the next “move” will always be >=0 (i.e. in the first quadrant). 
	
	Only for x=1/y=1, the next move may fall in the negative quad, 
	for example (x-2,y-1) or (x-1, y-2), and hence x=-1 y=-1 boundary is considered.
	*/
	
	
	private static class Point {
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			
			if (obj == null || getClass() != obj.getClass())
				return false;
			
			Point other = (Point) obj;
			if (x != other.x || y != other.y)
				return false;
			
			return true;
		}
		
	}
}

// Pai class is not part of Java8 create this if you need it
// https://walkccc.me/LeetCode/problems/1197/
/*class DFS_Solution {
	  public int minKnightMoves(int x, int y) {
	    return dp(Math.abs(x), Math.abs(y));
	  }

	  private Map<Pair<Integer, Integer>, Integer> memo = new HashMap<>();

	  private int dp(int x, int y) {
	    if (x + y == 0) // (0, 0)
	      return 0;
	    if (x + y == 2) // (0, 2), (1, 1), (2, 0)
	      return 2;
	    Pair<Integer, Integer> key = new Pair<>(x, y);
	    if (memo.containsKey(key))
	      return memo.get(key);

	    final int minMove = Math.min(
	        dp(Math.abs(x - 2), Math.abs(y - 1)),
	        dp(Math.abs(x - 1), Math.abs(y - 2))) + 1;
	    memo.put(key, minMove);
	    return minMove;
	  }
	}*/
