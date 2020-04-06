package test.lyft;

import java.util.ArrayList;
import java.util.HashSet;

public class RobotInAGrid {

	public static void main(String[] args) {

		boolean[][] maze = {
							{true, false, true, true},
							{true, false, true, true},
							{true, true, true, true},
							{true, false, false, true}
						};
		
		System.out.println(">>>>RESULT:: "+getPath(maze));
		
	}
	
	//--Runtime O(r * c)

	private static ArrayList<Point> getPath(boolean[][] maze) {
		ArrayList<Point> path = new ArrayList<>();
		HashSet<Point> failedPoint = new HashSet<>();
		
		int r = maze.length-1;
		int c = maze[0].length-1;
		boolean hasPathFound = getPath(maze, path, r, c, failedPoint);
		if(hasPathFound) {
			return path;
		}
		
		return null;
		
	}

	private static boolean getPath(boolean[][] maze, ArrayList<Point> path, int r, int c, HashSet<Point> failedPoint) {
		
		if(r < 0 || c < 0 || !maze[r][c]) {
			return false;
		}
		
		Point p = new Point(r, c);
		if(failedPoint.contains(p)) {
			return false;
		}
		
		boolean isOrigin = r == 0 || c == 0;
		
		if(isOrigin 
				|| getPath(maze, path, r-1, c, failedPoint)
				|| getPath(maze, path, r, c-1, failedPoint)) {
			
			path.add(p);
			return true;
		}
		failedPoint.add(p);
		return false;
	}

	public static class Point {
		int r;
		int c;
		
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		} 
		
		public int hashCode() {
			return Integer.hashCode(r)*3
					+Integer.hashCode(c)*5;
		}
		public boolean equals(Object o) {
			Point p = (Point) o;
			return this.r == p.r && this.c == p.c;
		}
		public String toString() {
			
			return "(r=" + r +", c=" + c +") :: hashCode="+(Integer.valueOf(r).hashCode()*3
					+Integer.valueOf(c).hashCode()*5);
		}
	}
}
