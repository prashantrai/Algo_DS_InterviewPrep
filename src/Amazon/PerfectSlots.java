package Amazon;

/**
 * You are given an array where each element represents a product ID in a slot. 
 * A slot is considered “perfect” if inventory[i] == i (1-based index).You can 
 * remove elements, and after removal, everything to the right shifts left.

	Goal: Maximize the number of perfect slots after any number of removals.
 * 
 * 
 * 
 * 
 * Perfect Slots Problem
 *
 * A slot i is "perfect" if inventory[i] == i (1-based index).
 * We can remove any elements (shifting the rest left).
 * Goal: maximize the number of perfect slots.
 *
 * KEY INSIGHT:
 * After removals, we keep a subsequence. If we keep elements in their
 * original order, element inventory[i] sits at position = its rank
 * among kept elements. It becomes perfect if that rank == its value.
 *
 * Greedy strategy: scan left-to-right. Track `count` = number of
 * perfect slots confirmed so far. Whenever inventory[i] == count+1,
 * we greedily include it as the next perfect slot (count++).
 *
 * Why greedy works:
 * - We never gain by skipping a match: any element that "fits" at the
 *   next position is optimal to take — taking it cannot block a better
 *   future element (future elements need count+2, count+3, ... anyway).
 * - We never gain by taking a non-match: a non-matching element can
 *   only "shift" the count, making subsequent matches harder.
 *
 * TIME COMPLEXITY:  O(n) — single pass through the array.
 * SPACE COMPLEXITY: O(1) — only a counter variable is used.
 */
public class PerfectSlots {

    /**
     * Returns the maximum number of perfect slots achievable
     * via any sequence of element removals.
     *
     * @param inventory array of product IDs (1-based slot values)
     * @return maximum perfect slot count
     */
    public static int maxPerfectSlots(int[] inventory) {
        // Edge case: null or empty array → 0 perfect slots
        if (inventory == null || inventory.length == 0) return 0;

        // `count` tracks how many perfect slots we've locked in so far.
        // The NEXT slot position we're looking to fill is count+1.
        int count = 0;

        for (int i = 0; i < inventory.length; i++) {
            // If this element's value equals the next position we need,
            // greedily take it as a perfect slot.
            if (inventory[i] == count + 1) {
                count++;
            }
            // Otherwise, we implicitly "remove" this element (skip it).
            // Skipping costs nothing — remaining elements shift left for free.
        }

        return count;
    }

    // ─────────────────────────────────────────────
    // TEST HARNESS
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        runTests();
    }

    static void runTests() {
        int passed = 0, total = 0;

        // ── Standard cases ──────────────────────────────────────────────

        // Already perfect: [1,2,3] → all 3 perfect
        total++; if (check("[1,2,3]", maxPerfectSlots(new int[]{1,2,3}), 3)) passed++;

        // Shift-based: [2,1,3,6,5,4]
        // Keep 1→pos1 perfect. Result = 1
        total++; if (check("[2,1,3,6,5,4]", maxPerfectSlots(new int[]{2,1,3,6,5,4}), 1)) passed++;

        // Multiple hits: [1,3,2,3]
        // 1→pos1✓, skip 3, 2→pos2✓, 3→pos3✓. Result = 3
        total++; if (check("[1,3,2,3]", maxPerfectSlots(new int[]{1,3,2,3}), 3)) passed++;

        // No perfect slots possible: [5,5,5,5]
        total++; if (check("[5,5,5,5]", maxPerfectSlots(new int[]{5,5,5,5}), 0)) passed++;

        // Single element perfect: [1]
        total++; if (check("[1]", maxPerfectSlots(new int[]{1}), 1)) passed++;

        // Single element not perfect: [3]
        total++; if (check("[3]", maxPerfectSlots(new int[]{3}), 0)) passed++;

        // Reverse sorted: [4,3,2,1]
        // skip 4, skip 3, skip 2, 1→pos1. Result = 1
        total++; if (check("[4,3,2,1]", maxPerfectSlots(new int[]{4,3,2,1}), 1)) passed++;

        // ── Edge cases ───────────────────────────────────────────────────

        // Empty array
        total++; if (check("[]", maxPerfectSlots(new int[]{}), 0)) passed++;

        // Null array
        total++; if (check("null", maxPerfectSlots(null), 0)) passed++;

        // All duplicates of 1: [1,1,1]
        // First 1→pos1✓, remaining 1s can't fill pos2 (need 2). Result = 1
        total++; if (check("[1,1,1]", maxPerfectSlots(new int[]{1,1,1}), 1)) passed++;

        // Gaps: [1,3,5] → 1→pos1✓, need 2 but see 3 (skip), need 2 see 5 (skip). Result = 1
        total++; if (check("[1,3,5]", maxPerfectSlots(new int[]{1,3,5}), 1)) passed++;

        // All values beyond array length: [10,20,30]
        total++; if (check("[10,20,30]", maxPerfectSlots(new int[]{10,20,30}), 0)) passed++;

        // Large sequential: [1,2,3,...,1000]
        int[] large = new int[1000];
        for (int i = 0; i < 1000; i++) large[i] = i + 1;
        total++; if (check("[1..1000]", maxPerfectSlots(large), 1000)) passed++;

        // Mixed with extras: [1, 99, 2, 99, 3]
        // 1→pos1✓, skip 99, 2→pos2✓, skip 99, 3→pos3✓. Result = 3
        total++; if (check("[1,99,2,99,3]", maxPerfectSlots(new int[]{1,99,2,99,3}), 3)) passed++;

        // Two valid paths — greedy picks best: [2,1,2,3]
        // skip 2, 1→pos1✓, 2→pos2✓, 3→pos3✓. Result = 3
        total++; if (check("[2,1,2,3]", maxPerfectSlots(new int[]{2,1,2,3}), 3)) passed++;

        System.out.println("\n── Summary: " + passed + "/" + total + " tests passed ──");
    }

    /**
     * Helper: prints result and returns whether it matches expected.
     */
    static boolean check(String label, int actual, int expected) {
        boolean ok = actual == expected;
        System.out.printf("%s  Input=%-18s  Expected=%-4d  Got=%-4d  %s%n",
                ok ? "✓" : "✗", label, expected, actual, ok ? "" : "<-- FAIL");
        return ok;
    }
}