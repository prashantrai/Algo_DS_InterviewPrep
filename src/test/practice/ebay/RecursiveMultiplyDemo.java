package test.practice.ebay;

public class RecursiveMultiplyDemo {

	public static void main(String[] args) {

		System.out.println(recursiveMultiply(2,3));
		System.out.println(recursiveMultiply(31,35));
		System.out.println("recursiveMultiply_2:: " + recursiveMultiply_2(2,3));
		System.out.println("recursiveMultiply_2:: " + recursiveMultiply_2(31,35));
	}
	
	
	public static int recursiveMultiply (int x, int y) {
		
		if(x == 0) return x;
		if(x == 1) return y;
		
		int smaller = x < y ? x : y;
		int bigger = x < y ? y : x;
		
		// 31x35 = (15*2+1) x 35 = 15*2*35 + 35
		int s = smaller >> 1;
		
		int halfProduct = recursiveMultiply(x/2, bigger);
		
		if(halfProduct % 2 == 0) {
			return halfProduct + halfProduct;
		} else {
			return halfProduct + halfProduct + bigger;
		}
		
		
	}

	
	
	public static int recursiveMultiply_2 (int x, int y) {
		
		int smaller = x > y ? y : x;
		int bigger  = x > y ? x : Y;
		
		return recursiveMultiplyHelper_2 (smaller, bigger);
	}


	private static int recursiveMultiplyHelper_2(int smaller, int bigger) {

		if(smaller == 0) return 0;
		if(smaller == 1) return bigger;
		
		int s = smaller >> 1; //--devide by 2
		int product = recursiveMultiplyHelper_2(s, bigger);
		
		if(s%2 == 0) {
			return product + product;
		} else {
			return product + product + bigger;
		}
		
	}
	
	
	
}
