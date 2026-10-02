package Motive;

import java.util.HashMap;
import java.util.Map;

public class ContiguousArray_525_Medium {

	/* Interview Script: 
	 * 
	 * “I’m going to convert the problem into a prefix-balance
	 * problem. I’ll treat every 1 as +1 and every 0 as -1. Then any subarray with
	 * an equal number of zeros and ones has a total balance of zero. As I scan the
	 * array, I’ll maintain the running balance. If I see the same balance again at
	 * index i, that means everything after the previous occurrence of that balance
	 * through i has a net balance of zero, so it contains equal zeros and ones.
	 * 
	 * I’ll keep a HashMap from balance to the first index where that balance
	 * appeared. I specifically keep the first occurrence because that gives me the
	 * longest possible subarray when the balance repeats. I’ll initialize balance 0
	 * at index -1, which handles cases where a valid subarray starts at index 0.
	 * Then on every repeated balance, I calculate the distance from its first
	 * occurrence and update the maximum.”
	 */
	/* Step-by-Step Algorithm
	    1. Create a HashMap<Integer, Integer> storing:
	    balance -> earliest index
	
	    2. Initialize:
	    balance = 0
	    firstSeen.put(0, -1)
	
	    3. Iterate through the array.
	    4. For each number:
	    1 => balance++
	    0 => balance--
	
	    5. If balance has appeared before:
	    length = currentIndex - firstSeen.get(balance)
	
	    Update the maximum length.
	    6. Otherwise, store the current index as the first occurrence.
	    7. Return the maximum length.
	*/
	
	// Time and Space: O(n)
	public static int findMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Interview script:
        // Store the earliest index where each running balance appeared.
        Map<Integer, Integer> firstSeen = new HashMap<>();

        // Interview script:
        // Balance 0 exists before the array starts.
        // This lets us detect a valid subarray starting at index 0.
        firstSeen.put(0, -1);

        int balance = 0;
        int maxLength = 0;

        // Interview script:
        // Treat 1 as +1 and 0 as -1.
        // If the same balance appears again, the subarray between
        // those positions has equal numbers of 0s and 1s.
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 1) {
                balance++;
            } else {
                balance--;
            }

            // Interview script:
            // If we've seen this balance before, calculate the
            // zero-sum subarray length from its earliest occurrence.
            if (firstSeen.containsKey(balance)) {
                int length = i - firstSeen.get(balance);
                maxLength = Math.max(maxLength, length);
            } else {
                // Interview script:
                // Keep only the first occurrence because it gives
                // the longest possible subarray later.
                firstSeen.put(balance, i);
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {

        // Test 1 - Normal
        test(
            new int[]{0, 1},
            2,
            "Test 1 - Simple balanced array"
        );

        // Test 2 - Normal
        test(
            new int[]{0, 1, 0},
            2,
            "Test 2 - Multiple balanced choices"
        );

        // Test 3 - Normal / tricky
        test(
            new int[]{0, 0, 1, 0, 0, 0, 1, 1},
            6,
            "Test 3 - Long balanced middle/prefix"
        );

        // Test 4 - Edge
        test(
            new int[]{1, 1, 1},
            0,
            "Test 4 - No balanced subarray"
        );

        // Test 5 - Edge
        test(
            new int[]{},
            0,
            "Test 5 - Empty array"
        );

        // Test 6 - Entire array balanced
        test(
            new int[]{0, 1, 1, 0, 1, 0},
            6,
            "Test 6 - Entire array balanced"
        );

        // Test 7 - Single element
        test(
            new int[]{0},
            0,
            "Test 7 - Single element"
        );
    }

    private static void test(int[] nums, int expected, String name) {
        int actual = findMaxLength(nums);

        System.out.println(name);
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println();
    }

}
