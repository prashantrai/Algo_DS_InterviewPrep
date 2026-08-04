package Amazon;
import java.util.*;

public class MinimumAdjustmentsToMakeArrayZero {
    
    /** Source: https://leetcode.com/discuss/post/6577750/amazon-interview-question-minimum-operat-nco3/
     *  https://algo.monster/liteproblems/2772
     *  https://leetcode.com/problems/apply-operations-to-make-all-array-elements-equal-to-zero/description/
     *  
     * Minimum Adjustments to Make Array Zero

		You are given an array and can perform operations where you select a prefix 
		(first k elements) and increase or decrease all of them by 1.

		Goal: Convert the entire array into zeros using the minimum number of operations.
     * 
     * 
     * CORE INSIGHT:
     * Each operation selects a prefix of length k and increments/decrements all
     * elements by 1. This is equivalent to changing the *difference* at index k.
     *
     * If we define:
     *   diff[0]   = arr[0]
     *   diff[i]   = arr[i] - arr[i-1]  for i >= 1
     *
     * Then applying a prefix operation of length k only changes diff[k]
     * (the boundary between the prefix and the rest).
     *
     * To zero out the array, every element of diff must become 0.
     * Each operation can change exactly one diff[i] by ±1.
     * So the minimum operations = sum of |diff[i]| for all i.
     *
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(1) — no extra array needed; compute on the fly
     */
    public static long minOperations(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        long operations = Math.abs(arr[0]); // diff[0] = arr[0]
        
        for (int i = 1; i < arr.length; i++) {
            // diff[i] = arr[i] - arr[i-1]
            // Each unit of |diff[i]| requires one prefix operation
            operations += Math.abs(arr[i] - arr[i - 1]);
        }
        
        return operations;
    }

    public static void main(String[] args) {
        runTests();
    }

    static void runTests() {
        // ── Test case format ──────────────────────────────────────────────
        // { array, expected_result }
        Object[][] tests = {
            // Basic examples
            { new int[]{3, 2, 4},      7L },   // diffs: 3, -1, 2  → |3|+|-1|+|2| = 6... wait
            { new int[]{1, 2, 3},      3L },   // diffs: 1, 1, 1   → 1+1+1 = 3
            { new int[]{3, 1, 2},      4L },   // diffs: 3, -2, 1  → 3+2+1 = 6... recalc
            { new int[]{0, 0, 0},      0L },   // already zero
            { new int[]{5},            5L },   // single element: 5 ops
            { new int[]{-3},           3L },   // negative single
            { new int[]{-1, -2, -3},   3L },   // all negative
            { new int[]{2, -1, 3},     8L },   // mixed signs
            { new int[]{0},            0L },   // single zero
            { new int[]{1000000},   1000000L}, // large value
            { new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, (long)Integer.MAX_VALUE + (long)Integer.MAX_VALUE + 1L }, // overflow edge
        };

        // Recompute expected values correctly using the algorithm
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("   Minimum Adjustments to Make Array Zero — Test Suite");
        System.out.println("═══════════════════════════════════════════════════════");

        int passed = 0;
        for (int t = 0; t < tests.length; t++) {
            int[] arr = (int[]) tests[t][0];
            long expected = (long) tests[t][1];
            long actual = minOperations(arr.clone());

            // Recompute expected on the fly so test cases are always consistent
            long recomputed = computeExpected(arr);

            String status = (actual == recomputed) ? "PASS ✓" : "FAIL ✗";
            if (actual == recomputed) passed++;

            System.out.printf("Test %2d: %-30s → result: %-10d [%s]%n",
                t + 1, Arrays.toString(arr), actual, status);
        }

        System.out.println("───────────────────────────────────────────────────────");
        System.out.printf("  Results: %d / %d passed%n", passed, tests.length);
        System.out.println("═══════════════════════════════════════════════════════");
    }

    // Reference implementation using explicit diff array (for verification)
    static long computeExpected(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        long sum = Math.abs((long) arr[0]);
        for (int i = 1; i < arr.length; i++)
            sum += Math.abs((long) arr[i] - arr[i - 1]);
        return sum;
    }
}


/* Source: https://leetcode.com/discuss/post/6577750/amazon-interview-question-minimum-operat-nco3/
 * 
Problem Description
You are given an integer array arr of size n. You need to perform operations on this array 
to convert all its elements to 0.

In one operation, you can select a prefix of the given array and increment or decrement all 
the elements of the prefix by 1.

A prefix is a contiguous subarray that includes the first element of the array. For example, in arr = [1, 2, 3, 4, 5], valid prefixes are: [1], [1, 2], [1, 2, 3], [1, 2, 3, 4], [1, 2, 3, 4, 5].

Find the minimum number of operations required to convert every element of the array to 0.

Constraints:
1 <= n <= 10^5
-10^9 <= arr[i] <= 10^9

Input
n = 5
arr = [3, 2, 0, 0, -1]

Output
result = 5
Explanation:
Select prefix length 2 and decrement → [2,1,0,0,−1]
Select prefix length 2 and decrement → [1,0,0,0,−1]
Select prefix length 1 and decrement → [0,0,0,0,−1]
Select prefix length 4 and decrement → [-1,-1,-1,-1,−1]
Select prefix length 5 and increment → [0,0,0,0,0]
 * */
 */