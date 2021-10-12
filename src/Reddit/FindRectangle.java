package Reddit;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FindRectangle {

	public static void main(String[] args) {
		
		int[][] image1 = {
				  {1, 1, 1, 1, 1, 1, 1},
				  {1, 1, 1, 1, 1, 1, 1},
				  {1, 1, 1, 0, 0, 0, 1},
				  {1, 1, 1, 0, 0, 0, 1},
				  {1, 1, 1, 1, 1, 1, 1}
				};
		
		List<List<int[]>> res = findRectangles(image1);
		System.out.println("res:: "+res);
		
	}
	
	/* For Question: 	https://leetcode.com/discuss/interview-question/929979/wayfair-sde-oa
	 * 					https://leetcode.com/discuss/interview-question/835609/robinhood-karat-interview-swe-new-grad-2021-us
	 * 
	 * 
	 * 
	 * */
	
	
	private static List<List<int[]>> findRectangles(int[][] grid) {
	    if (grid == null)
	        return null;
	    List<List<int[]>> rectangles = new ArrayList<List<int[]>>();
	    for (int i = 0; i < grid.length; i++)
	        for (int j = 0; j < grid[0].length; j++)
	            if (grid[i][j] == 0) {
	                int[] end = new int[]{i, j};
	                findRectangles(grid, i, j, end);
	                rectangles.add(List.of(new int[]{i, j}, end));
	            }
	    return rectangles;
	}

	private static void findRectangles(int[][] grid, int i, int j, int[] end) {
	    if (i >= grid.length || j >= grid[0].length || grid[i][j] == 1)
	        return;
	    grid[i][j] = 1;
	    end[0] = Math.max(end[0], i);
	    end[1] = Math.max(end[1], j);
	    findRectangles(grid, i + 1, j, end);
	    findRectangles(grid, i, j + 1, end);
	}
	
	
	
	public static void findRectangle2(int[][] mat) {
		boolean[][] visited = new boolean[mat.length][mat[0].length];
		Stack<int[]> st = new Stack<int[]>();
		for(int i=0; i<mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if(mat[i][j]==0) {
					dfs(mat, i, j, visited, st);
	                break;
				}
			}
		}
		int minR=Integer.MAX_VALUE;
		int minC=Integer.MAX_VALUE;
		int maxR=-1;
		int maxC=-1;
		while(!st.isEmpty()) {
			int[] top = st.pop();
			minR = Math.min(minR, top[0]);
			minC = Math.min(minC, top[1]);
			maxR = Math.max(maxR, top[0]);
			maxC = Math.max(maxC, top[1]);
		}
		
		System.out.println(minR+","+minC+" "+maxR+","+maxC);
		System.out.println("x: "+ minC +" y: "+minR +" width: "+(maxC-minC+1)+" height: "+(maxR-minR+1));
	}


	private static void dfs(int[][] mat, int i, int j, boolean[][] visited, Stack<int[]> st) {
		if(i<0 || j<0 || i>=mat.length || j>=mat[0].length || visited[i][j] || mat[i][j]!=0) return;
		
		visited[i][j]= true;
		
		dfs(mat, i+1,j ,visited, st);
		dfs(mat, i+1,j ,visited, st);
		dfs(mat, i+1,j ,visited, st);
		dfs(mat, i+1,j ,visited, st);
		st.add(new int[] {i,j});
	}

}
