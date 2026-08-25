package Amazon;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class CarPooling_1094_Medium {

    public static void main(String[] args) {
        // Basic example: exceeds capacity
        runTest(new int[][]{{2, 1, 5}, {3, 3, 7}}, 4, false);

        // Basic example: exactly enough capacity
        runTest(new int[][]{{2, 1, 5}, {3, 3, 7}}, 5, true);

        // One trip only
        runTest(new int[][]{{3, 2, 8}}, 3, true);

        // Exact capacity during overlap
        runTest(new int[][]{{2, 1, 5}, {3, 5, 7}}, 3, true);

        // Drop happens before next pickup at same location
        runTest(new int[][]{{3, 2, 7}, {3, 7, 9}}, 3, true);

        // Complex overlapping case but still valid
        runTest(new int[][]{{2, 1, 3}, {3, 3, 7}, {1, 5, 6}, {2, 6, 8}}, 5, true);

        // Complex overlapping case invalid
        runTest(new int[][]{{4, 0, 4}, {3, 2, 5}, {2, 3, 6}}, 6, false);

        // Edge case: many passengers picked up at same point
        runTest(new int[][]{{2, 0, 5}, {3, 0, 7}, {1, 0, 3}}, 6, true);

        // Edge case: exceeds immediately
        runTest(new int[][]{{7, 0, 2}}, 6, false);

        // Edge case: capacity exactly matches maximum load
        runTest(new int[][]{{2, 1, 4}, {1, 2, 5}, {2, 4, 6}}, 3, true);
    }
    // Helper method for local testing
    private static void runTest(int[][] trips, int capacity, boolean expected) {
        boolean result = carPooling(trips, capacity);
        System.out.println("Capacity = " + capacity +
                ", Expected = " + expected +
                ", Got = " + result);
    }
    
	
	public static boolean carPooling(int[][] trips, int capacity) {

		return carPooling_Arr(trips, capacity);
        // return carPooling_PQ(trips, capacity); // working
        // return carPooling_TreeMap(trips, capacity); // working
        
    }
	
	// Time: O(max(N, 10001)), since we need to iterate over trips and then iterate over our 1001 buckets. N here is length of trips. 
    // Space: O(1)
    private static boolean carPooling_Arr(int[][] trips, int capacity) {
        // Constraints: 1 <= trips.length <= 1000
        int[] stops = new int[1001];

        for(int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            
            stops[from] += passengers;
            stops[to] -= passengers;
        }

        int usedCapacity = 0;
        for(int passengers : stops) {
            usedCapacity += passengers;
            if(usedCapacity > capacity) return false;
        }

        return true;
    }

    // Time: O(n log n)
    // Space: O(n)
    private static boolean carPooling_PQ(int[][] trips, int capacity) {
        // Sort by pickup location
        Arrays.sort(trips, (a, b) -> Integer.compare(a[1], b[1]));

        // Min-heap: [dropLocation, passengers]
        PriorityQueue<int[]> minPQ 
            = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        int usedSeats = 0;

        for(int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];

            // Remove all trips that have already ended
            while(!minPQ.isEmpty() && minPQ.peek()[0] <= from) {
                usedSeats -= minPQ.poll()[1];
            }

            // Add current trip
            usedSeats += passengers;

            if(usedSeats > capacity) 
                return false;

            minPQ.offer(new int[]{to, passengers});
        }

        return true;
    }

    // Time: O(n log n)
    // Space: O(n)
    private static boolean carPooling_TreeMap (int[][] trips, int capacity) {
        Map<Integer, Integer> timestampMap = new TreeMap<>();

        // trips[i] = [numPassengersi, fromi, toi] 
        for(int[] trip : trips) {
            int passengers = trip[0];
            int from = trip[1];
            int to = trip[2];

            int startPassenger = timestampMap.getOrDefault(from, 0) + passengers;
            timestampMap.put(from, startPassenger);  

            int endPassenger = timestampMap.getOrDefault(to, 0) - passengers;
            timestampMap.put(to, endPassenger);  
        }

        int usedCapacity = 0;
        for(int passengerChange : timestampMap.values()) {
            usedCapacity += passengerChange;
            if(usedCapacity > capacity) return false;
        }

        return true;
    }

}
