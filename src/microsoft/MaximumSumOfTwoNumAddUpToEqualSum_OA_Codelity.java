package microsoft;

import java.util.HashMap;
import java.util.Map;

public class MaximumSumOfTwoNumAddUpToEqualSum_OA_Codelity {

	public static void main(String[] args) {
		
		int[] a1 = {51, 71, 17, 42};
		System.out.println("Expected: 92, Actual: " + maximumSum(a1));
		
		int[] a2 = {42, 33, 60};
		System.out.println("Expected: 102, Actual: " + maximumSum(a2));
		
		int[] a3 = {51, 32, 43};
		System.out.println("Expected: -1, Actual: " + maximumSum(a3));

	}
	
	/* https://www.lintcode.com/problem/maximum-sum-of-two-numbers/description
	 * Solution: https://codeantenna.com/a/bUepikEC9H
	 * 
	 Question1: Given an array A consisting of N integers, returns the maximum sum of two numbers whose digits add up to an equal sum. if there are not two numbers whose digits have an equal sum, the function should return -1.
		Constraints: N is integer within the range [1, 200000]
		each element of array A is an integer within the range [1, 1000
		000000]
		
		Example1:
		Input:
		A = [51, 71, 17, 42]
		Output: 93
		Explanation: There are two pairs of numbers whose digits add up to an equal sum: (51, 42) and (17, 71), The first pair sums up  to 93
		
		Example2:
		Input:
		A = [42, 33, 60]
		Output: 102
		Explanation: The digits of all numbers in A add up the same sum, and choosing to add 42 and 60 gives the result 102
		
		Example3:
		Input:
		A = [51, 32, 43]
		Output: -1
		Explanation: All numbers in A have digits that add up to different, unique sums 
	 * */
	
	public static int maximumSum(int[] A) {
        // write your code here
        Map<Integer, int[]> map = new HashMap<>();
        for (int a : A) {
            int s = 0, tmp = a;
            while (tmp > 0) {
                s += tmp % 10;
                tmp /= 10;
            }
            
            map.putIfAbsent(s, new int[2]);
            int[] nums = map.get(s);
            if (a > nums[0]) {
                nums[1] = nums[0];
                nums[0] = a;
            } else if (a > nums[1]) {
                nums[1] = a;
            }
        }
    
        int res = -1;
        for (int[] nums : map.values()) {
            if (nums[1] == 0) {
                continue;
            }         
            res = Math.max(res, nums[0] + nums[1]);
        }
        
        return res;
    }

}
