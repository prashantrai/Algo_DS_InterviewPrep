package test.practice.misc;

public class AreaOfOverlappingRectangleDemo {

	//--Calculate area of overlapping rectangle when, given only lower left and upper right coordinates for 2 rectangles
	
	public static void main(String[] args) {

		//--lower left (2,1) and upper right is (5,5)
		int r1x1 = 2;
		int r1y1 = 1;
		int r1x2 = 5;
		int r1y2 = 5;
		
		//--lower left (3,2) and upper right is (6,7)
		int r2x1 = 3;
		int r2y1 = 2;
		int r2x2 = 6;
		int r2y2 = 7;
		
		Rect r1 = new Rect(2, 1, 5, 5);
		Rect r2 = new Rect(3, 2, 6, 7);
		
		int area = getAreaOfOfOverlappingRectangle (r1, r2);
		
		System.out.println("Area is : "+area);
		
	}

	//--Looks good
	public static int getAreaOfOfOverlappingRectangle (Rect r1, Rect r2 ) {
		
		int x_distance = getDistance (r1.x1, r2.x1, r1.x2, r2.x2);
		if(x_distance <= 0 ) return -1;
		
		int y_distance = getDistance (r1.y1, r2.y1, r1.y2, r2.y2);
		if(y_distance <= 0 ) return -1;
		
		return (x_distance * y_distance);
	}
	
	private static int getDistance (int r1c1, int r2c1, int r1c2, int r2c2 ) {
		return ( Math.min(r1c2, r2c2) - Math.max(r1c1, r2c1) );
	}
	
	
	private static class Rect {
		int x1, y1, x2, y2;
		public Rect(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;

		}
	}
}
