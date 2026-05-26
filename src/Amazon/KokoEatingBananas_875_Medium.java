package Amazon;

import java.util.Arrays;

public class KokoEatingBananas_875_Medium {

	public static void main(String[] args) {

        // Test 1: Example from problem
        runTest(new int[]{3, 6, 7, 11}, 8, 4);

        // Test 2: Tight hours (equal to number of piles)
        runTest(new int[]{30, 11, 23, 4, 20}, 5, 30);

        // Test 3: Slightly larger h
        runTest(new int[]{30, 11, 23, 4, 20}, 6, 23);

        // Test 4: Single pile, hours equal to pile size
        runTest(new int[]{5}, 5, 1);

        // Test 5: Single very large pile
        runTest(new int[]{1_000_000_000}, 1, 1_000_000_000);

        // Test 6: Many small piles, small h
        runTest(new int[]{1, 1, 1, 1, 1}, 3, 2);

        // Test 7: Mixed sizes, large h (should allow small speed)
        runTest(new int[]{9, 8, 7, 6, 5}, 20, 1);
    }

    private static void runTest(int[] piles, int h, int expected) {
        int result = minEatingSpeed(piles, h);
        System.out.println("piles = " + Arrays.toString(piles)
                + ", h = " + h
                + ", expected = " + expected
                + ", actual = " + result
                + " -> " + (result == expected ? "PASS" : "FAIL"));
    }

    
    
	// Time complexity: O(n · log(maxPile))
    // Space complexity: O(1)

    private static int minEatingSpeed(int[] piles, int h) {
        // Upper bound for speed is the maximum pile size
        int maxPile = findMaxPile(piles);

        int low = 1;
        int high = maxPile;
        int ans = maxPile; // start with max; we will minimize this

        while (low <= high) {
            // Standard mid calculation to avoid overflow
            int mid = low + (high - low) / 2;

            long hoursNeeded = computeHours(piles, mid);

            if(hoursNeeded <= h) {
                // mid is fast enough; try smaller speed
                ans = Math.min(ans, mid);
                high = mid - 1;
            } else {
                // mid is too slow; need higher speed
                low = mid + 1;
            }
        }
        return ans;
    }

    // Helper: find maximum pile using Math.max
    private static int findMaxPile(int[] piles) {
        int maxPile = 0;
        for(int pile : piles) {
            maxPile = Math.max(maxPile, pile);
        }
        return maxPile;
    } 

    // Helper: compute total hours needed if speed is 'speed'
    // Time: O(n) per call (one pass through piles).
    // Space: O(1) extra space.
    private static long computeHours(int[] piles, int speed) {
        long totalHrs = 0L;
        for(int bananas : piles) {
            // hours for this pile = ceil(bananas / speed)
            // (double) bananas / speed does floating-point division.
            // Math.ceil(...) rounds up to the nearest integer (what we want).
            // We cast back to long before adding to totalHours.
            totalHrs += (long) Math.ceil( (double) bananas / speed );

            // implemented using integer arithmetic:
            // or below, works too
            // totalHrs += (bananas + speed - 1) / speed;

        }
        return totalHrs;
    }

}


/*
Explain below with example: 
totalHours += (bananas + speed - 1) / speed;
	
This line is computing:
hours needed for one pile = ceil(bananas / speed)
and then adding it to totalHours.

Because we use integer division, we can’t get decimal numbers directly, so we use the trick:

ceil(a / b) = (a + b - 1) / b for positive integers.

If Koko eats speed bananas per hour from a pile of size bananas:
 - She needs at least bananas / speed hours.
 - If it’s not an exact division, she still needs a full extra hour to finish the remaining bananas.

So we need the ceiling of the division.

Example 1
	bananas = 10, speed = 3
	
	Real division: 10 / 3 ≈ 3.33 → needs 4 hours.
	Integer division: 10 / 3 = 3 (this is floor, too small).
	Our formula:
	(bananas + speed - 1) / speed
	= (10 + 3 - 1) / 3
	= 12 / 3
	= 4 ✅

Example 2
bananas = 6, speed = 3 (exactly divisible)

	Real division: 6 / 3 = 2 → needs 2 hours.
	Integer division: 6 / 3 = 2.
	Our formula:
	(6 + 3 - 1) / 3
	= 8 / 3
	= 2 (integer division) ✅

Example 3
bananas = 1, speed = 4

	Real division: 1 / 4 = 0.25 → needs 1 hour.
	Integer division: 1 / 4 = 0 (wrong).
	Our formula:
	(1 + 4 - 1) / 4
	= 4 / 4
	= 1 ✅

So:
(bananas + speed - 1) / speed
   
   
   
 * */
