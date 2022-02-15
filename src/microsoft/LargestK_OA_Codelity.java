package microsoft;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LargestK_OA_Codelity {

	public static void main(String[] args) {
		int res = largestK(Arrays.asList(3, 2, -2, 5, -3));
        System.out.println("Expected: 3, Actual: " + res);
        
        res = largestK(Arrays.asList(1, 2, 3, -4));
        System.out.println("Expected: 0, Actual: " + res);
        
        
	}
	
	/* https://algo.monster/problems/largest_k_positive_and_negative
	 * 
	 * Given an array A of N integers, returns the largest integer K > 0 such that both values K and -K exist in array A. If there is no such integer, the function should return 0.

		Example 1:
			Input:[3, 2, -2, 5, -3]
			Output: 3
		Example 2:
			Input:[1, 2, 3, -4]
			Output: 0
	 
	  
	  
	 */
	
	public static int largestK(List<Integer> nums) {
        HashSet<Integer> set = new HashSet<>();
        int curMax = 0;
        for (int a : nums) {
            if (set.contains(-a))
                curMax = Math.max(curMax, Math.abs(a));
            else
                set.add(a);
        }
        return curMax;
    }

}
