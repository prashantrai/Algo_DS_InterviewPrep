
public class Test {

	public static void main(String[] args) {
		
		checkOddEven(3);
		//checkOddEven(2);
	}
	
	public static void checkOddEven(int n) {
		
		if((n & 1) == 1) 
			System.out.println("odd");
		else 
			System.out.println("even");
		
		if((n >> 1) == 1) 
			System.out.println("odd");
		else 
			System.out.println("even");
	}

	public static int[] fun(int[] A) {
		for(int i = 0; i < A.length - 1; i++) {
			if (A[i] > A[i + 1]) {
				int temp = A[i];
				A[i] = A[i+1];
				A[i+1] = temp;
			}
		}
		return A;
				
	}
	
	
}
