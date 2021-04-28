package doordash;

public class CountAllValidPickupAndDeliveryOptions_1359_Hard {

	public static void main(String[] args) {
		int n = 1;
		System.out.println("Expected: 1, Actual: " + countOrders(n));
		System.out.println("DP : Expected: 1, Actual: " + countOrders2(n));
		
		n = 2;
		System.out.println("\nExpected: 6, Actual: " + countOrders(n));
		System.out.println("DP : Expected: 6, Actual: " + countOrders2(n));
		
		n = 3;
		System.out.println("\nExpected: 90, Actual: " + countOrders(n));
		System.out.println("DP : Expected: 90, Actual: " + countOrders2(n));
				

	}
	
	/*
	 * Problem has 2 different approaches
	 * 1. Math: Nice explanation of formula 
	 * 		Refer https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/discuss/521751/Java-O(n)-time-and-O(1)-space-with-explanation
	 * 
	 * 2. DP - [See below] https://leetcode.jp/leetcode-1359-count-all-valid-pickup-and-delivery-options-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	 * */

	
	/** Mathematically using permutation
	 * Video (This is fine too but leetcode one is better): https://www.youtube.com/watch?v=H0Yl1AlUuK8
	 * 
	 * Reference: https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/discuss/521751/Java-O(n)-time-and-O(1)-space-with-explanation
	 * 
	 * Time: O(N), Space: O(1)
	 * 
	 * Explanation: 
	 * 
	 *  We start by trying to follow what happens if we decide to choose one pickup 
	 * 				to occur right now.
		So, if the number of orders is n, there will be n pickups and n deliveries. 
		This is a total of 2n actions (n pickups and n deliveries). If pickup 1 happens now, 
		we can observe that:
		
			1. What happens for pickups number 2 to N and their corresponding deliveries 
			   is none of my business currently.
			2. If countOrders(n) is our function, the answer for those is countOrders(n-1).
			3. Inside the state of countOrders(n-1), the total of actions for those is 2*(n-1), 
			   for the same reason as stated in the starting paragraph.
			   
		    4. We can choose any opportunity in-between those 2*(n-1) actions to put the delivery 
		       for pickup 1, this gives us 2*(n-1) - 1 possibilities. Additionally, we can also put 
		       it at the beginning of everything, or at the end. That gives us 
		       
		       2*(n-1) - 1 + 2 => 2*(n-1) + 1 => 2n-2+1 => 2n-1 
		       
		       different places where delivery 1 could happen.
		       
			5. Therefore, if pickup 1 is already placed, our answer would be 
			   countOrders(n-1) * (2n-1) configurations,.
				
			   Now, remember that initially, we haven't decided which pickup will happen first. 
			   Hence, given n, we can choose between any of the n pickups to happen first, 
			   which means there are n choices. This concludes our formula. 
			   
			   Given n, our answer is: n * countOrders(n-1) * (2n-1)
	 * 
	 * */
	public static int countOrders(int n) {
		int mod = (int) (1e9+7); //or mode = 1000_000_007
		int sum = 1;
		for(int i=2; i<=n; i++) {
			sum = (sum * i * (2*i - 1)) % mod;  // mod to avoid overflow
		}
		return sum;
	}
	
	
	/**
	 We need to define,
	 
	  the number of orders "n" that are not currently received and 
	  the number of orders "p" that has not been sent yet. 
	  
	  Initially, "n" is equal to the number of orders given in the question, and "p" is equal to 0. 
	 
	 For any point in time, if "n" is greater than 0, it means that we can receive an order. 
	 Any one of the "n" orders is an object that we can receive. 
	 
	 After receiving, the number of "n" orders is reduced by one, 
	 and the number of orders in hand "p" is increased by one. 
	 
	 Therefore, the possibility of receiving an order at the current time point is:
	 	> res = n * helper ( p+ 1 , n- 1 ) ;
	 	
	 
	 In addition, at the current point in time, if the number of orders "p" is greater than 0, 
	 we can also choose to send the order. 
	 
	 When sending the order, you can choose any one of the "p" orders to send. After sending, 
	 the number of p will be reduced by one, and n will not change. 
	 
	 So, the possibility of sending an order at the current time point is:
	 	> res = p * helper ( p- 1 , n ) ;
	 	
	 Adding the above two results is the number of permutations and combinations of all operations 
	 at the current time point.

	 In addition, when recursing, we need to use a memory array/memoization to avoid repeated calculations.
	 
	 * */
	
	// DP - https://leetcode.jp/leetcode-1359-count-all-valid-pickup-and-delivery-options-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
	static int [][] memo; // memory array
	final static long MOD = (long)1e9+7; //or 1000_000_00_7
	public static int countOrders2 ( int n ) {   
	    // Initialize the memory array
	    memo = new int [ n+ 1 ][ n+ 1 ] ; 
	    // Recursive solution
	    return helper ( 0 ,n ) ; 
	}
	/* 
	 p is the number of orders in the hand
	 n is the number of orders not yet received
	 The return value is the number of permutations and combinations when there are p orders in the hand and n orders are not received 
	*/
	private static int helper ( int p, int n ){ 
	    // If both p and n are 0, 1 is returned by default
	    if ( n== 0 && p== 0 ) return 1 ;  
	   
	    // If the current solution exists in the memory array, return directly
	    if ( memo [ p ][ n ]> 0 ) 
	    	return memo [ p ][ n ] ;
	    
	    int res = 0 ;
	    
	    // If there is still an unaccepted order, we can choose to accept an order
	    if ( n > 0 ){
	        // The number of combinations after arbitrarily picking up an order from n missed orders
	        res+= (( long ) n* helper ( p+1 ,n-1 ) % MOD ) ;
	    }
	    
	    // If there are unsent orders, we can choose to send one
	    if ( p > 0 ){
	        // The number of combinations after arbitrarily sending an order from n unsent orders
	        res+= (( long ) p* helper ( p-1 ,n ) % MOD ) ;
	    }
	    // Save the current result to the memory array
	    memo [ p ][ n ] =res;
	    // return the current result
	    return res;
	}
	
}
