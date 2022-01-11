package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.HashSet;

public class RobotInAGridDemo {

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
	// Look here: Leetcode 63 : https://leetcode.com/problems/unique-paths-ii/solution/
	// 
	
	public static ArrayList<Point> getPath(boolean[][] maze) {
		
		if(maze == null || maze.length == 0) return null;
		
		ArrayList<Point> path = new ArrayList<Point>();
		HashSet<Point> failedPoints = new HashSet<Point> ();
		
		//--e.g. row and col value should one step ahead/less because we can't consider the current position
		int r = maze.length-1;    
		int c = maze[0].length-1;  
		
		boolean isPathFound = getPath(maze, r, c, path, failedPoints);
		if(isPathFound && path.size() > 0) {
			return path;
		}
		
		return null;
	}

	private static boolean getPath(boolean[][] maze, int r, int c, ArrayList<Point> path, HashSet<Point> failedPoints) {

		if(r<0 || c<0 || !maze[r][c])
			return false;
		
		Point point = new Point(r,c);
		
		if(failedPoints.contains(point)) {
			return false;
		}
		
		//--is it end (0,0)
		boolean isOrigin = (r == 0 && c==0);
		
		if(isOrigin || getPath(maze, r-1, c, path, failedPoints) 
				|| getPath(maze, r, c-1, path, failedPoints)) {
			
			path.add(point);
			return true;
		}
		
		failedPoints.add(point);
		return false;
	}
	
}

class Point {
	
	int r;
	int c;
	public Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
	public String toString() {
		
		return "(r=" + r +", c=" + c +") :: hasCode="+(Integer.valueOf(r).hashCode()*3
				+Integer.valueOf(c).hashCode()*5);
	}
	public int hashCode() {
		
		int code = Integer.valueOf(r).hashCode()*3
						+Integer.valueOf(c).hashCode()*5;
		return code;
	}
	public boolean equals (Object o) {
		if(!(o instanceof Point)) 
			return false;
		
		Point p =  (Point) o;
		return (this.r == p.r && this.c == p.c);
		
	}
}
