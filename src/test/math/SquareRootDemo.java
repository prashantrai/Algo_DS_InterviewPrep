package test.math;

public class SquareRootDemo {

	public static void main(String[] args) {

//		String s = sqrt(-256);
		String s = sqrt(9);
		System.out.println("1. Squrt = "+ s);
		
	}

	
	public static String sqrt(double num) {
		
		boolean isNegative = false;
		
		if(num < 0) {
			isNegative = true;
			num = -num; //--converting to positive and appending i while returning. i=sqrt(-1)
		}
		
		if(num == 1 ){
			return num + (isNegative ? "i" : "");
		}
		
		double start = 0;
		System.out.println("num = "+num);
		double end = num;
		System.out.println("end = "+end);
		double mid = (start+end)/2;
		
		System.out.println("mid = "+mid);
		
		double prevMid = 0;
		double precision = 0.0001;
		double diff = Math.abs(mid - prevMid);
		
		System.out.println("diff = "+diff);
		
		while (mid*mid != num && diff > precision) {
			
			if(mid*mid > num) {
				end = mid;
			} else {
				start = mid;
			}
			
			System.out.println("start = "+start);
			System.out.println("mid = "+mid);
			System.out.println("end = "+end);
			
			prevMid = mid;
			mid = (start+end)/2;
			diff = Math.abs(mid - prevMid);
			
			System.out.println("-->mid = "+mid);
			System.out.println("-->prevMid = "+prevMid);
			System.out.println("-->diff = "+diff);
		}
		
		return mid + (isNegative ? "i" : "");
		
	}
	
	
}
