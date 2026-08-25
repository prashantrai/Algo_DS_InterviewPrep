package Amazon;

public class MinimumNumberOfDaysToMake_m_Bouquets_1482_Medium {

	public static void main(String[] args) {
        // Test 1
        int[] bloomDay1 = {1, 10, 3, 10, 2};
        int m1 = 3;
        int k1 = 1;
        System.out.println("Test 1: " + minDays(bloomDay1, m1, k1)
                        + " | Expected: 3");

        // Test 2
        int[] bloomDay2 = {1, 10, 3, 10, 2};
        int m2 = 3;
        int k2 = 2;

        System.out.println("Test 2: " + minDays(bloomDay2, m2, k2)
                        + " | Expected: -1");

        // Test 3
        int[] bloomDay3 = {7, 7, 7, 7, 12, 7, 7};
        int m3 = 2;
        int k3 = 3;

        System.out.println("Test 3: " + minDays(bloomDay3, m3, k3)
                        + " | Expected: 12");


        // Test 4: all flowers bloom on same day
        int[] bloomDay4 = {5, 5, 5, 5};
        int m4 = 2;
        int k4 = 2;
        System.out.println("Test 4: " + minDays(bloomDay4, m4, k4)
                        + " | Expected: 5");


        // Test 5: adjacency matters
        int[] bloomDay5 = {1, 10, 3, 10, 2, 4, 5};
        int m5 = 1;
        int k5 = 2;
        System.out.println("Test 5: " + minDays(bloomDay5, m5, k5)
                        + " | Expected: 4");
    }

	
	/* Interview Script / Idea:

	Each bloomDay[i] tells me the day when that specific flower blooms.

	For any candidate day D:
	- A flower is usable if bloomDay[i] <= D.
	- I need k adjacent usable flowers to make one bouquet.
	- I need at least m bouquets.

	The key observation is monotonicity:
	- If I can make m bouquets on day D, then I can also make them on any later day.
	- So the answers look like:
	  false false false true true true ...
	- That means I can binary search for the first day that works.

	For a given candidate day:
	- Scan bloomDay from left to right.
	- Count consecutive bloomed flowers.
	- If bloomDay[i] <= day, increment the consecutive count.
	- If consecutive == k, form one bouquet and reset consecutive to 0
	  because those flowers are already used.
	- If bloomDay[i] > day, reset consecutive to 0 because adjacency is broken.

	Before binary search:
	- If m * k > number of flowers, it is impossible, so return -1.

	Binary Search:
	- left = minimum bloom day
	- right = maximum bloom day
	- mid represents a candidate day.

	If mid can make at least m bouquets:
	- mid is feasible.
	- Try to find an earlier feasible day.
	- right = mid.

	Otherwise:
	- mid is too early.
	- left = mid + 1.

	When left == right, that is the minimum feasible day.

	*/
	
	/* Step-by-Step Algorithm:

	1. If (long) m * k > bloomDay.length, return -1.

	2. Find:
	   left  = minimum value in bloomDay
	   right = maximum value in bloomDay

	3. While left < right:
	   a. mid = left + (right - left) / 2

	   b. Check whether we can make m bouquets by day mid:
	      - bouquets = 0
	      - consecutive = 0

	      - For every bloom value:
	          If bloom <= mid:
	              consecutive++

	              If consecutive == k:
	                  bouquets++
	                  consecutive = 0

	          Else:
	              consecutive = 0

	      - If bouquets >= m, mid works.

	   c. If mid works:
	          right = mid
	      Else:
	          left = mid + 1

	4. Return left.


	Why reset consecutive when bloom > mid?

	Example:
	    ✓ ✓ X ✓ ✓

	The X flower has not bloomed yet, so flowers on the left and right
	cannot be combined into one adjacent group.


	Why reset consecutive after making a bouquet?

	Example with k = 2:
	    ✓ ✓ ✓ ✓

	After using the first two flowers:
	    [✓ ✓] ✓ ✓

	Those flowers cannot be reused, so start counting again.

	*/

	// Time:  O(n * log R), n = bloomDay.length; R = maxBloomDay - minBloomDay
	// Space: O(1)
	public static int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;

        // Not enough flowers to ever make m bouquets.
        // cast to long to avoid overflow (when numbers are large)
        if ((long) m * k > n) {
           return -1;
        }

        int left = bloomDay[0];
        int right = bloomDay[1];

        for(int day : bloomDay) {
            left = Math.min(left, day);
            right = Math.max(right, day);
        }

        while(left < right) {
            int mid = left + (right - left)/2;
            
            if(canMakeBouquets(bloomDay, m, k, mid)) {
                // mid works, but maybe an earlier day also works.
                right = mid;
            } else {
                // mid does not work, so we need a later day.
                left = mid+1;
            }

        }
        return left;
    }

    private static boolean canMakeBouquets(int[] bloomDay, int m, int k, int day) {

        int bouquets = 0;
        int consecutiveFlowers = 0;

        for(int bloom : bloomDay) {

            if(bloom <= day) {
                consecutiveFlowers++;

                // We have k adjacent bloomed flowers.
                if(consecutiveFlowers == k) {
                    bouquets++;
                    consecutiveFlowers = 0;

                    if(bouquets == m) 
                        return true;
                }
            } else {
                // This flower has not bloomed yet,
                // so adjacency is broken.
                consecutiveFlowers = 0;
            }
        }
        return false;
    }
	
}
