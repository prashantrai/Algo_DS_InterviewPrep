package interview;

public class FibDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		fib(5);
	}

	public static int fib(int n) {
		
		if(n < 2 ) return n;
		
		//System.out.println(">> "+n);
		
		return fib(n-1) + fib(n-2); 
	}
	
	
}
