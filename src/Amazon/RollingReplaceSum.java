package Amazon;
import java.util.*;

/**
 * Problem: [Batch Value Swap with Running Sum / Rolling Replace Sum]
 * 
 	Input: A list of integers entries and a 2D integer list transactions 
	( so a list of lists of integers).

	Every transaction inside transactions is a pair of two numbers [old_v, new_v]. For each pair:
	
	Find all the indexes in entries where entries[idx] == old_v
	Replace the value at those indexes with new_v
	Calculate the sum of all the numbers in entries after the update
	**Output:** Return a list of sums after each transaction.
 * 
 * 
 * Problem: Process transactions on an entries list.
 * Each transaction [old_v, new_v] replaces all occurrences of old_v with new_v in entries,
 * then records the running sum.
 *
 * Key Insight: Instead of iterating over all entries for each transaction (O(n) per transaction),
 * we maintain:
 *   - A frequency map: value -> count of occurrences in entries
 *   - A running sum that we update incrementally
 *
 * This reduces each transaction to O(1) instead of O(n).
 */
public class RollingReplaceSum {

    public static List<Long> processTransactions(List<Integer> entries, List<List<Integer>> transactions) {
        List<Long> results = new ArrayList<>();

        // freq: maps each unique value -> how many times it appears in entries
        Map<Integer, Integer> freq = new HashMap<>();

        // Compute initial sum and populate frequency map
        long currentSum = 0;
        for (int val : entries) {
            freq.put(val, freq.getOrDefault(val, 0) + 1);
            currentSum += val;
        }

        for (List<Integer> transaction : transactions) {
            int oldVal = transaction.get(0);
            int newVal = transaction.get(1);

            // How many entries currently hold oldVal?
            int count = freq.getOrDefault(oldVal, 0);

            if (count > 0 && oldVal != newVal) {
                // Adjust sum: remove contribution of oldVal, add contribution of newVal
                // sum change = count * (newVal - oldVal)
                currentSum += (long) count * (newVal - oldVal);

                // Update frequency map:
                // oldVal count drops to 0
                freq.remove(oldVal);
                // newVal count increases by `count`
                freq.put(newVal, freq.getOrDefault(newVal, 0) + count);
            }
            // If count == 0: oldVal not present, nothing changes
            // If oldVal == newVal: no change needed

            results.add(currentSum);
        }

        return results;
    }

    // ─── Test Harness ──────────────────────────────────────────────────────────

    public static void main(String[] args) {
        runTest("Basic example",
                Arrays.asList(1, 2, 2, 3),
                Arrays.asList(
                        Arrays.asList(2, 5),   // replace 2→5: [1,5,5,3], sum=14
                        Arrays.asList(1, 3)    // replace 1→3: [3,5,5,3], sum=16
                ),
                Arrays.asList(14L, 16L));

        runTest("old_v not present in entries",
                Arrays.asList(4, 5, 6),
                Arrays.asList(
                        Arrays.asList(9, 1),   // 9 not present, sum stays 15
                        Arrays.asList(4, 10)   // replace 4→10: [10,5,6], sum=21
                ),
                Arrays.asList(15L, 21L));

        runTest("old_v == new_v (no-op)",
                Arrays.asList(3, 3, 3),
                Arrays.asList(
                        Arrays.asList(3, 3)   // replace 3→3: no change, sum=9
                ),
                Arrays.asList(9L));

        runTest("All same values",
                Arrays.asList(7, 7, 7, 7),
                Arrays.asList(
                        Arrays.asList(7, 2)   // replace all 7→2: [2,2,2,2], sum=8
                ),
                Arrays.asList(8L));

        runTest("Single element",
                Arrays.asList(42),
                Arrays.asList(
                        Arrays.asList(42, 0),  // replace 42→0: [0], sum=0
                        Arrays.asList(0, -5)   // replace 0→-5: [-5], sum=-5
                ),
                Arrays.asList(0L, -5L));

        runTest("Negative values",
                Arrays.asList(-1, -2, -3),
                Arrays.asList(
                        Arrays.asList(-1, 1),  // replace -1→1: [1,-2,-3], sum=-4
                        Arrays.asList(-2, 0)   // replace -2→0: [1,0,-3], sum=-2
                ),
                Arrays.asList(-4L, -2L));

        runTest("Large values (overflow check)",
                Arrays.asList(1_000_000_000, 1_000_000_000),
                Arrays.asList(
                        Arrays.asList(1_000_000_000, 500_000_000)
                        // replace: [500M, 500M], sum = 1_000_000_000 (fits in long)
                ),
                Arrays.asList(1_000_000_000L));

        runTest("Chained replacements (new_v becomes next old_v)",
                Arrays.asList(1, 2, 3),
                Arrays.asList(
                        Arrays.asList(1, 2),  // replace 1→2: [2,2,3], sum=7
                        Arrays.asList(2, 3),  // replace 2→3: [3,3,3], sum=9
                        Arrays.asList(3, 4)   // replace 3→4: [4,4,4], sum=12
                ),
                Arrays.asList(7L, 9L, 12L));

        runTest("Empty transactions",
                Arrays.asList(1, 2, 3),
                Collections.emptyList(),
                Collections.emptyList());

        runTest("Multiple replacements converging to same value",
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(
                        Arrays.asList(1, 10),  // [10,2,3,4],  sum=19
                        Arrays.asList(2, 10),  // [10,10,3,4], sum=27
                        Arrays.asList(3, 10),  // [10,10,10,4],sum=34
                        Arrays.asList(4, 10)   // [10,10,10,10],sum=40
                ),
                Arrays.asList(19L, 27L, 34L, 40L));
    }

    private static void runTest(String name,
                                List<Integer> entries,
                                List<List<Integer>> transactions,
                                List<Long> expected) {
        List<Long> actual = processTransactions(new ArrayList<>(entries), transactions);
        boolean passed = actual.equals(expected);
        System.out.printf("[%s] %s%n", passed ? "PASS" : "FAIL", name);
        if (!passed) {
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual:   " + actual);
        }
    }
}