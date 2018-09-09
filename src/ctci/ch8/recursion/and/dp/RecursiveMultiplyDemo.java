package ctci.ch8.recursion.and.dp;

public class RecursiveMultiplyDemo {

	public static void main(String[] args) {

		System.out.println(minProduct(31,35));
		System.out.println(minProduct(2,3));
		
	}
	
	public static int minProduct(int a, int b) {
		
		int bigger = a < b ? b : a;
		int smaller = a < b ? a : b;
		
		return minProductHelper(bigger, smaller);
	}

	private static int minProductHelper(int bigger, int smaller) {

		if(smaller == 0) {
			return 0;
		}
		if(smaller == 1) {
			return bigger;
		}
		
		int s = smaller >> 1; //divided by 2
		
		int prod = minProductHelper(bigger, s);
		
		if(prod % 2 == 0) {
			prod = prod+prod;
			
		} else {
			prod = prod+prod+bigger;
			System.out.println("prod=" + prod+ ", prod=" + prod + ",bigger=" + bigger);
		}
		
		return prod;
	}

}
