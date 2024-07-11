package Facebook;

public class MaximumSwap_670_Medium {

	public static void main(String[] args) {
		int num = 2736;
		System.out.println("Expected: 7236, Actual: " + maximumSwap(num));
		
		num = 9973;
		System.out.println("Expected: 9973, Actual: " + maximumSwap(num));
	}

	// Time & Space: O(N)
	public static int maximumSwap(int num) {
		
		// convert the input num to char array
        char[] A = Integer.toString(num).toCharArray();
        
        // record the index of each value in array last[]
        // so, index of this array last[] represents the individual digit in nums
        // and value at that index will represent their position in the input num
        int[] last = new int[10];
        for (int i = 0; i < A.length; i++) {
            last[A[i] - '0'] = i;
        }

        // iterate through the the A[]
        for (int i = 0; i < A.length; i++) {
        	// a for loop to read data from last[], this loop 
        	// will start from the right most index which is 
        	// (i.e.) representing the digit in input
        	// now if we find the value for that index i.e. > i then 
        	// perform swap and return the result
            for (int d = 9; d > A[i] - '0'; d--) {
                if (last[d] > i) {
                    char tmp = A[i];
                    A[i] = A[last[d]];
                    A[last[d]] = tmp;
                    return Integer.valueOf(new String(A));
                }
            }
        }
        return num;
    }
}
