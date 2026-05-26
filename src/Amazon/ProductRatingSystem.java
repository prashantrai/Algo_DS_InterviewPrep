package Amazon;
import java.util.Arrays;

public class ProductRatingSystem {
    
    /**
     * Finds maximum bitwise AND of m elements after at most k increment operations
     * @param customerRating array of customer ratings
     * @param k maximum number of increment operations allowed
     * @param m size of subset to select
     * @return maximum possible bitwise AND
     */
    public static int maxBitwiseAND(int[] customerRating, int k, int m) {
        int n = customerRating.length;
        int result = 0;
        
        // Try to set each bit from MSB (bit 30) to LSB (bit 0)
        for (int bit = 30; bit >= 0; bit--) {
            int candidate = result | (1 << bit);
            
            // Check if we can achieve this candidate result
            if (canAchieveTarget(candidate, customerRating, k, m)) {
                result = candidate;
            }
        }
        
        return result;
    }
    
    /**
     * Checks if we can make at least m elements have all bits of target set
     * within k total operations
     */
    private static boolean canAchieveTarget(int target, int[] arr, int k, int m) {
        int n = arr.length;
        int[] operationsNeeded = new int[n];
        
        // Calculate operations needed for each element
        for (int i = 0; i < n; i++) {
            operationsNeeded[i] = getOperationsNeeded(arr[i], target);
        }
        
        // Sort to get m elements with minimum operations
        Arrays.sort(operationsNeeded);
        
        // Sum up operations for m cheapest elements
        long totalOps = 0;
        for (int i = 0; i < m; i++) {
            // If impossible to transform this element
            if (operationsNeeded[i] == Integer.MAX_VALUE) {
                return false;
            }
            totalOps += operationsNeeded[i];
            // Early termination if exceeds k
            if (totalOps > k) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculates minimum operations to transform 'num' to have all bits of 'target' set
     * Returns MAX_VALUE if transformation is impossible
     */
    private static int getOperationsNeeded(int num, int target) {
        // Check if num has bits that target doesn't have
        // If yes, we can't remove them (only increment allowed)
        if ((num | target) != target) {
            return Integer.MAX_VALUE;
        }
        
        // We need to make num >= target while keeping (num | target) == target
        // Minimum such value is target itself
        if (num > target) {
            return Integer.MAX_VALUE;
        }
        
        return target - num;
    }
    
    public static void main(String[] args) {
        // Test Case 1: Given example
        int[] test1 = {1, 2, 4, 8};
        System.out.println("Test 1: " + maxBitwiseAND(test1, 8, 2));
        // Expected: 10 (increase 4->10 with 6 ops, 8->10 with 2 ops, AND(10,10)=10)
        
        // Test Case 2: All same elements, no operations needed
        int[] test2 = {5, 5, 5};
        System.out.println("Test 2: " + maxBitwiseAND(test2, 0, 3));
        // Expected: 5
        
        // Test Case 3: Select all elements
        int[] test3 = {1, 2, 3};
        System.out.println("Test 3: " + maxBitwiseAND(test3, 10, 3));
        // Expected: 3 (make all 3: 1->3 needs 2, 2->3 needs 1, total=3)
        
        // Test Case 4: Single element selection
        int[] test4 = {1, 100, 50};
        System.out.println("Test 4: " + maxBitwiseAND(test4, 10, 1));
        // Expected: 100 (just pick the largest)
        
        // Test Case 5: Edge case - not enough operations
        int[] test5 = {1, 2, 4};
        System.out.println("Test 5: " + maxBitwiseAND(test5, 1, 2));
        // Expected: 0 or small value
        
        // Test Case 6: Complex case with powers of 2
        int[] test6 = {1, 2, 4, 8, 16};
        System.out.println("Test 6: " + maxBitwiseAND(test6, 20, 3));
        // Expected: depends on optimal distribution
        
        // Test Case 7: Large values
        int[] test7 = {1000000, 1000000, 1000000};
        System.out.println("Test 7: " + maxBitwiseAND(test7, 100, 2));
        // Expected: 1000000 or slightly higher
        
        // Test Case 8: m equals array size
        int[] test8 = {3, 5, 7};
        System.out.println("Test 8: " + maxBitwiseAND(test8, 15, 3));
        // Expected: 7 (3->7: 4ops, 5->7: 2ops, total=6, AND=7)
    }
}