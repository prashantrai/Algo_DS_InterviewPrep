package Facebook;

public class Pow_x_n_50_Medium {

	public static void main(String[] args) {

		double x = 2.00000; 
		int n = 10;
				
		System.out.println("Eexpected: 1024.00000, Actual: " + myPow(x, n));
		System.out.println("Eexpected: 1024.00000, Actual: " + myPow2(x, n));
		
	}
	/** Algo - iterative
	Create a method helper, which takes x and n as parameters.
    - If n is 0, we return 1.
	- If n is negative, we change n as -n, and x as 1 / x. 
		(also, -n can exceed the integer range thus to handle it n 
		 should be a 64-bit integer variable).
	
	- Initialize a variable result as 1 to store the result of all 
		the multiplications we will perform.
	
	- In a while loop until n becomes 0:
		> If n is odd, we multiply one x with result and reduce n by 1.
		> Then, we square x and reduce n by half.
	
	- At the end, we return result.
	*/
	
	
	/*
	 * Time: O(log N), At each iteration we reduce n by half, 
	 * 	so we will make only log*N number iterations.
	 * 
	 * Space: O(1), no extra space used
	 */
    
    public static double myPow(double x, int n) {
        return helper(x, n);        
    }
    
    private static double helper(double x, long n) {
        if(n == 0) 
            return 1;
        
        // Handle case where, n < 0. If n is negative,
        // we change n as -n, and x as 1 / x. (also, -n 
        // can exceed the integer range thus to handle it 
        // n should be a 64-bit integer variable).
        if(n<0) {
            n = -1 * n;
            x = 1.0/x;
        }
        
        // Perform Binary Exponentiation.
        double result = 1;
        while (n!=0) {
            // If 'n' is odd we multiply result with 
            // 'x' and reduce 'n' by '1'.
            if(n%2 == 1) {
                result = result * x;
                n -= 1;
            }
            
            // We square 'x' and reduce 'n' by half, 
            // x^n => (x^2)^(n/2).
            x = x*x;
            n = n/2;
        } 
        return result;
    }
	
	/*
	 * Time: O(log N), At each recursive call we reduce n by half, 
	 * 	so we will make only log*N number of calls , and the multiplication 
	 * 	of two numbers is considered as a O(1) time operation.
	 * 
	 * Space: O(logn), 
	 * 	The recursive stack can use at most O(logn) space at any time.
	 */
	
	// Recursive
	public static double myPow2(double x, int n) {
	    return pow_util(x, n);
	}
	
	private static double pow_util(double x, long n){
	    if(n == 0) return 1.0;
	    if(n == 1) return x;
	    if(n < 0) return pow_util(1/x, -n);
	    double result = pow_util(x*x, n/2);
	    if(n%2 == 1) result *= x;
	    return result;
	}
	
}
