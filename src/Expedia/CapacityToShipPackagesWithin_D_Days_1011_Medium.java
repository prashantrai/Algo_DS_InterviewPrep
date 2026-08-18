package Expedia;

import java.util.Arrays;

public class CapacityToShipPackagesWithin_D_Days_1011_Medium {

	// Simple test runner
    public static void main(String[] args) {

        test(new int[]{1,2,3,4,5,6,7,8,9,10}, 5, 15); // example 1
        test(new int[]{3,2,2,4,1,4}, 3, 6);           // example 2
        test(new int[]{1,2,3,1,1}, 4, 3);             // example 3

        test(new int[]{10}, 1, 10);                   // single package
        test(new int[]{1,2,3,4}, 1, 10);              // all in one day
        test(new int[]{1,2,3,4}, 4, 4);               // one package/day max case
        test(new int[]{5,5,5,5,5}, 5, 5);             // equal weights
        test(new int[]{5,5,5,5,5}, 2, 15);            // tighter grouping
        test(new int[]{7,2,5,10,8}, 2, 18);           // classic partition-like case
        test(new int[]{9,8,7,6,5,4,3,2,1}, 3, 17);    // mixed descending
    }

    private static void test(int[] weights, int days, int expected) {
        int result = shipWithinDays(weights, days);
        System.out.println("weights = " + Arrays.toString(weights)
                + ", days = " + days
                + ", result = " + result
                + ", expected = " + expected
                + " -> " + (result == expected ? "PASS" : "FAIL"));
    }

	/* 
    We need to find the capacity that can be enough to ship all pkg
    within given days. 
    
    So, how shall we idetify the capacity? 
    Capacity is going to be max weight that ship can carry and deliver in 1 day.
    
    We have given number of days and the capacity should be enough to deliver all
    the pkg in the given days.
    
    We will run binary search on weights, each mid element we will treat as capacity 
    and run it against the days to see if all the pkg can be shipped
    using the capacity withing the the given number of days
    */

    // Time: O(n log(sum(weights))), bianry search
    // Space: O(1)
    public static int shipWithinDays(int[] weights, int days) { 
        int left = 0;   // minimum valid capacity
        int right = 0;  // maximum possible capacity

        // Find search range:
        // left = max weight, right = sum of all weights
        // why left = max weight?
        // because ship must be able to carry every single package by itself', 
        // as problem states "A conveyor belt has packages that must be shipped"
        // so, we can start from left (as max weight) to find the right capacity
        for(int weight : weights) {
            left = Math.max(left, weight);
            right += weight; 
        }

        // Binary search for the minimum capacity that works
        while(left < right) {
            int mid = left + (right - left)/2;
            // If mid capacity can ship within given days, try smaller
            if(canShip(weights, days, mid)) {
                right = mid;
            } else {
                // Otherwise, capacity is too small
                left = mid + 1;
            }
        }

        return left;
    }

    // Greedy check: can we ship all packages within 'days'
    // if ship capacity is 'capacity'?
    private static boolean canShip(int[] weights, int days, int capacity) {
        int usedDays = 1;   // start with day 1
        int currentLoad = 0;
        for (int w : weights) {
            // If adding this package exceeds capacity, use a new day
            if(currentLoad + w > capacity) {
                usedDays++;
                currentLoad = 0;
            }
            currentLoad += w;

            // Early stop if days exceeded
            if(usedDays > days) {
                return false;
            }
        }
        return true;
    }
}
