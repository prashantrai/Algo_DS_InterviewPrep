import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		
		checkOddEven(3);
		//checkOddEven(2);
		
		int[] A = counterExample(4);
		System.out.println(Arrays.toString(A));
		//result suppose to be wrong as requirement is to generate an array and pass to findMin so that find will return wrong ans
		System.out.println("Expected: 1, Actual: " + findMin(A)); 
		
	}
	
	public static int[] counterExample(int n) {
		// https://leetcode.com/discuss/interview-question/555702/Microsoft-or-Online-Codility-Assessment-or-Counterexample-(Task-1)/486118
	  int[] result = new int[n];
	  for (int i = 0; i < n; i++) 
		  result[i] = i+1;
	  return result;

	}
	
	//wrong imlementation of findMin() for counterExample-- Microsoft OA
	public static int findMin(int[] A) {
		int ans = 0;
		for(int i=1; i<A.length; i++) {
			if(ans > A[i]) {
				ans = A[i];
			}
		}
		return ans;
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
