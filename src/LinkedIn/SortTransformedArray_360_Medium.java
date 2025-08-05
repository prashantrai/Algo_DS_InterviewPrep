package LinkedIn;

import java.util.Arrays;

public class SortTransformedArray_360_Medium {

	public static void main(String[] args) {
		// Test case 1
        int[] nums1 = {-4, -2, 2, 4};
        System.out.println(Arrays.toString(sortTransformedArray(nums1, 1, 3, 5))); // [3, 9, 15, 33]

        // Test case 2
        int[] nums2 = {-4, -2, 2, 4};
        System.out.println(Arrays.toString(sortTransformedArray(nums2, -1, 3, 5))); // [-23, -5, 1, 7]

        // Test case 3 (linear function)
        int[] nums3 = {-3, -1, 0, 2, 4};
        System.out.println(Arrays.toString(sortTransformedArray(nums3, 0, 2, 1))); // [-5, -1, 1, 5, 9]

        // Edge case: empty array
        System.out.println(Arrays.toString(sortTransformedArray(new int[]{}, 1, 2, 3))); // []
  
	}
	
	// Time & Space: O(N)
    public static int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int n = nums.length;
        int res[] = new int[n];

        // 2 pointers
        int left = 0;
        int right = n-1;

        int index = (a >= 0) ? n-1 : 0;

        while (left <= right) {
            int leftVal = compute(nums[left], a, b, c);
            int rightVal = compute(nums[right], a, b, c);

            /* 
            f(x) = ax² + bx + c

            Depending on the sign of a, the parabola opens upward or downward:
            If a > 0: parabola opens upwards (like a U). Min value is in the middle.
            If a < 0: parabola opens downwards (like an ∩). Max value is in the middle.
            If a == 0: it becomes a linear function: f(x) = bx + c.
            */

            if(a >= 0) {
                // Fill from the end to start
                if(leftVal > rightVal) {
                    res[index--] = leftVal;
                    left++;
                } else {
                    res[index--] = rightVal;
                    right--;
                }
            }
            else {
                // Fill from start to end
                if(leftVal < rightVal) {
                    res[index++] = leftVal;
                    left++;
                } else {
                    res[index++] = rightVal;
                    right--;
                }
            }
        }
        return res;
    }

    private static int compute(int x, int a, int b, int c) {
        return a*x*x + b*x + c;
    }

}
