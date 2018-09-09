package ctci.ch16.moderate;

public class IntersectionOfLinesDemo {

	public static void main(String[] args) {

	}

	
	public static Point intersection (Point start1, Point end1, Point start2, Point end2) {
		
		/*Rearranging to have in order: start before end */
		
		if(start1.x > end1.x) swap(start1, end1);
		if(start2.x > end2.x) swap(start2, end2);
		if(start1.x > start2.x) {
			swap(start1, start2);
			swap(end1, end2);
		} 
		
		//--Computes lines for slope and y-intercept
		Line line1 = new Line(start1, end1);
		Line line2 = new Line(start2, end2);
		
		/*
		 * Parallel Lines:  same slop and different y-intercept
		 * Same Line: same slope and same y-intercept
		 * */
		
		if(line1.slope == line2.slope) {
			if(line1.y_intercept == line2.y_intercept && isBetween(start1, start2, end1)) { //--same line
				return start2;
			}
			return null; //--parallel lines
		}
		
		//--Intersection
		/*
		 * x = (y2-intercept - y1-intercept)/(slope1 - slope2)
		 * */
		double x = (line2.y_intercept - line1.y_intercept) / (line1.slope - line2.slope);
		double y = x * line2.slope + line2.y_intercept;
		
		Point intersection =  new Point(x, y);
		
		//--Check if within the segment range
		if(isBetween(start1, intersection, end1) && 
				isBetween(start2, intersection, end2)) {
			return intersection;
		}
		return null;
		
		
	}
	
	public static boolean isBetween(double start, double middle, double end) {
		
		if(start > end) {
			return start >=middle && middle >= end;
			
		} else {
			
			return start <= middle && middle <= end;
		}
	}
	
	public static boolean isBetween(Point start, Point middle, Point end) {
		return isBetween(start.x, middle.x, end.x) && 
				isBetween(start.y, middle.y, end.y) ;
	}
	
	public static void swap(Point one, Point two) {
		
		double x = one.x;
		double y = one.y;
		
		one.x = two.x;
		one.y = two.y;
		
		two.x = x;
		two.y = y;
	}
	
	private static class Line {
		double slope, y_intercept;
		
		public Line (Point start, Point end) {
			slope = (end.y - start.y) / (end.x - start.x);
			y_intercept = end.y - (slope*end.x); 
		}
	}
	
	private static class Point {
		
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public void setLocation(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	
}


