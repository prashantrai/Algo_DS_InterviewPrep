package test.practice.ebay;

import java.util.ArrayList;
import java.util.HashSet;

import ctci.ch8.recursion.and.dp.Point;

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

	private static ArrayList<Point> getPath(boolean[][] maze) {
		
		if(maze == null || maze.length == 0) {
			return null;
		}
		ArrayList<Point> path = new ArrayList<Point>();
		HashSet<Point> failedPoints = new HashSet<Point>();
		
		getPath(maze, maze.length-1, maze[0].length-1, path, failedPoints);
		
		return path;
	}
	
	
	private static boolean getPath(boolean[][] maze, int r, int c, ArrayList<Point> path, HashSet<Point> failedPoints) {

		if(c < 0 || r < 0 || !maze[r][c]) {
			return false;
		}
		
		
		Point point = new Point(r,c);
		if(failedPoints.contains(point)) {
			return false;
		}
		
		boolean isOrigin = (r==0 && c==0);
		
		if(isOrigin || getPath(maze, r-1, c, path, failedPoints) || getPath(maze, r, c-1, path, failedPoints)) {
			path.add(point);
			return true;
		}
		
		failedPoints.add(point);
		return false;
		
	}


	static class Point {
		
		int r, c;
		
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
		
		public String toString() {
			return "(r=" + r +", c=" + c +")";
		}
		public int hashCode() {
			
			/*
			 * Multiplying the values by 3 and 5 (prime numbers) and adding the value will reduce the collision same could have been 
			 * implemented as,
			 *  int code = Integer.valueOf(r).hashCode()
							*Integer.valueOf(c).hashCode();
			 *  
			 *  but this will give the same hash code if either of r or c is 0.
			 * */ 
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

}
