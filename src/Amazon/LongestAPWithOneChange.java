package Amazon;

public class LongestAPWithOneChange {

    /**
     * **Input:** A list of integers `deviation`.

		**Task:** Find the length of the longest contiguous subarray that forms 
		a`n` arithmetic progression (AP), where the difference between consecutive 
		elements is constant. We are allowed at most one element change** in the 
		array to maximize this length.
		
		**Example:** Given the array `[8, 5, 2, 1, 100]`, if you change the 1 to `-1`, 
		you get the progression `[8, 5, 2, -1]` with a constant difference of `-3`, 
		giving a maximum length of 4.

     * 
     * 
     * Finds the length of the longest AP subarray allowing at most one element change.
     *
     * Strategy:
     *  - Build a difference array: diff[i] = arr[i+1] - arr[i]
     *  - An AP subarray = all diffs in that window are equal
     *  - One element change can repair AT MOST 2 adjacent diffs
     *    (changing arr[i] affects diff[i-1] and diff[i])
     *  - Use a sliding window on the diff array:
     *      * Allow at most one "bad" diff group (consecutive unequal diffs = one break)
     *      * When a second distinct diff value appears, shrink from left
     *
     * Time:  O(n)
     * Space: O(n) for diff array  →  can be O(1) with index tracking (shown below)
     */
    public static int longestAP(int[] arr) {
        int n = arr.length;

        // Edge cases: 0, 1, or 2 elements always form an AP
        if (n <= 2) return n;

        // Build difference array
        // diff[i] = arr[i+1] - arr[i], length = n-1
        int[] diff = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            diff[i] = arr[i + 1] - arr[i];
        }

        /*
         * Sliding window over diff[]:
         *
         *  For an AP subarray arr[left..right] (length = right-left+1),
         *  the corresponding diffs are diff[left..right-1].
         *
         *  A window of diffs is "fixable with one change" if:
         *    - All diffs are equal (0 changes needed), OR
         *    - Exactly one "break" exists and it involves at most 2 adjacent
         *      diffs (one element fix repairs both at once).
         *
         *  We track:
         *    windowStart  : left boundary of current diff window
         *    firstVal     : the dominant diff value in the window
         *    breakStart   : index where the break begins (-1 if none)
         */
        int maxLen = 2;       // minimum answer is 2 (any pair)
        int windowStart = 0;  // left of diff window
        int firstVal = diff[0];
        int breakStart = -1;  // where the first break occurred in diff[]

        for (int i = 1; i < diff.length; i++) {

            if (diff[i] == firstVal) {
                // Current diff matches: window stays healthy (or break is healing)
                // No action needed; window extends naturally.

            } else if (breakStart == -1) {
                // First break encountered
                breakStart = i;

            } else {
                /*
                 * Second break encountered.
                 * Can we fix it by changing a single element?
                 *
                 * Case A: The break is a SINGLE diff (breakStart == i-1 NOT adjacent to prior)
                 *   diff: [3, 3, 5, 3] → diff[2]=5 is one bad diff; change arr[3] fixes it.
                 *   This is fine — slide window to breakStart (keep firstVal).
                 *
                 * Case B: Break spans 2+ diffs with a new "run" starting
                 *   diff: [3, 5, 5, 3] → diffs 5,5 can't both be fixed by one element change.
                 *   Slide window past the OLD firstVal run; new firstVal = diff[breakStart].
                 *
                 * In all second-break cases, we move windowStart forward and reset breakStart.
                 */
                if (diff[i] == diff[breakStart]) {
                    // The new value matches the break value → the "bad" segment is diff[breakStart-1]
                    // Change arr[breakStart] repairs diff[breakStart-1] and diff[breakStart]
                    // Move window start to breakStart (drop the old first segment)
                    windowStart = breakStart;
                    firstVal = diff[i];
                    breakStart = -1; // healed
                } else {
                    // Genuinely a second distinct break — can't fix with one change
                    // New window starts from breakStart, firstVal becomes diff[breakStart]
                    windowStart = breakStart;
                    firstVal = diff[breakStart];
                    // Now check if diff[i] breaks this new window
                    if (diff[i] != firstVal) {
                        breakStart = i;
                    } else {
                        breakStart = -1;
                    }
                }
            }

            // arr subarray length = diff window length + 1
            // diff window = [windowStart .. i], length = i - windowStart + 1
            // arr window length = i - windowStart + 2
            maxLen = Math.max(maxLen, i - windowStart + 2);
        }

        return maxLen;
    }

    // ─────────────────────────── Test Harness ───────────────────────────

    public static void main(String[] args) {
        runTests();
    }

    static void runTests() {
        Object[][] tests = {
            // { arr,                         expected, description }

            // ── Provided example ──
            { new int[]{8, 5, 2, 1, 100},    4,  "Example: change 1→-1 gives [8,5,2,-1]" },

            // ── No change needed ──
            { new int[]{1, 3, 5, 7, 9},      5,  "Perfect AP, no change needed" },
            { new int[]{5, 5, 5, 5},         4,  "Constant array (diff=0)" },
            { new int[]{1, 2},               2,  "Two elements" },
            { new int[]{7},                  1,  "Single element" },
            { new int[]{},                   0,  "Empty array" },

            // ── One change in the middle ──
            { new int[]{1, 3, 5, 8, 9, 11}, 4,  "Change 8→7, gives [3,5,7,9]" },
            { new int[]{1, 2, 3, 10, 5, 6}, 3,  "Two breaks; best subarray len 3" },

            // ── One change at a boundary ──
            { new int[]{100, 1, 3, 5, 7},   4,  "Change 100→-1, gives [-1,1,3,5]" },
            { new int[]{1, 3, 5, 7, 100},   4,  "Change 100→9, gives [1,3,5,7]" },

            // ── All same elements ──
            { new int[]{4, 4, 4, 4, 4},     5,  "All same" },

            // ── Two-element edge ──
            { new int[]{1, 5},               2,  "Two elements, trivially AP" },

            // ── Large run with one bad element ──
            { new int[]{2, 4, 6, 8, 5, 12, 14, 16}, 4, "Bad element in middle splits runs" },

            // ── Entire array fixable ──
            { new int[]{1, 3, 5, 100, 9, 11}, 4, "Change 100→7 gives [1,3,5,7]" },

            // ── Negative differences ──
            { new int[]{10, 7, 4, 5, -2},   4,  "Change 5→1 gives [10,7,4,1]" },
        };

        int passed = 0, failed = 0;
        for (Object[] t : tests) {
            int[] arr     = (int[]) t[0];
            int expected  = (int)   t[1];
            String desc   = (String) t[2];
            int got = longestAP(arr);
            boolean ok = got == expected;
            System.out.printf("[%s] %-55s expected=%d got=%d%n",
                ok ? "PASS" : "FAIL", desc, expected, got);
            if (ok) passed++; else failed++;
        }
        System.out.printf("%nResult: %d passed, %d failed%n", passed, failed);
    }
}