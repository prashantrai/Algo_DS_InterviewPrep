package Facebook;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class KClosestPointsToOrigin_973_Medium {

	public static void main(String[] args) {

		int[][] points = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
		int k = 2;
		System.out.println("Expected (order doesn't matter): [[3,3],[-2,4]]");
		System.out.println("With QuickSelect :: Actual: " + Arrays.deepToString(kClosest_QuickSelect(points, k)));
		System.out.println("With PriorityQueue :: Actual: " + Arrays.deepToString(kClosest_PQ(points, k)));

	}

	/** Using Quick Select
	 * 
	 * Time: O(N)
	 * 		QuickSelect solution has a worst-case time complexity of O(N^2) 
	 * 		if the worst pivot is chosen each time. On average, however, 
	 * 		it has a time complexity of O(N) because it halves (roughly) 
	 * 		the remaining elements needing to be processed at each iteration. 
	 * 		This results in 𝑁+𝑁/2+𝑁/4+𝑁/8+...+𝑁/𝑁=2N total processes, 
	 * 		yielding an average time complexity of O(N).
	 * 
	 * Space: O(1), QuickSelect algorithm conducts the partial sort of points 
	 * in place with no recursion, so only constant extra space is required.
	 */
	
	public static int[][] kClosest_QuickSelect(int[][] points, int k) {
        return quickSelect(points, k);
    }
    
    private static int[][] quickSelect(int[][] points, int k) {
        int left = 0, right = points.length - 1;
        int pivotIndex = points.length;
        while (pivotIndex != k) {
            // Repeatedly partition the array
            // while narrowing in on the kth element
            pivotIndex = partition(points, left, right);
            if (pivotIndex < k) {
                left = pivotIndex;
            } else {
                right = pivotIndex - 1;
            }
        }
        
        // Return the first k elements of the partially sorted array
        return Arrays.copyOf(points, k);
    }

    private static int partition(int[][] points, int left, int right) {
        int[] pivot = choosePivot(points, left, right);
        int pivotDist = squaredDistance(pivot);
        while (left < right) {
            // Iterate through the range and swap elements to make sure
            // that all points closer than the pivot are to the left
            if (squaredDistance(points[left]) >= pivotDist) {
                int[] temp = points[left];
                points[left] = points[right];
                points[right] = temp; 
                right--;
            } else {
                left++;
            }
        }
        
        // Ensure the left pointer is just past the end of
        // the left range then return it as the new pivotIndex
        if (squaredDistance(points[left]) < pivotDist)
            left++;
        return left;
    }

    private static int[] choosePivot(int[][] points, int left, int right) {
        // Choose a pivot element of the array
        return points[left + (right - left) / 2];
    }
	
    private static int squaredDistance(int[] point) {
        // Calculate and return the squared Euclidean distance
        return point[0] * point[0] + point[1] * point[1];
    }
    
	
	/** Using PriorityQueue
	 * 
	 * Time: O(N logK), add/remove from heap takes O(logK) time because cap has been
	 * kept as K.
	 * 
	 * Space: O(K), the heap will contain at most K elements
	 */
	public static int[][] kClosest_PQ(int[][] points, int k) {
		/*
		 * 1. maxHeap, because we want to keep the higher value on top so that we can
		 * remove them to keep the heap hight to k elements only.
		 * 
		 * 2. points higher value (or squared distance) will NOT be closer to the
		 * origin.
		 */
		Queue<int[]> maxPQ = new PriorityQueue<>((a, b) -> b[0] - a[0]);

		for (int i = 0; i < points.length; i++) {
			// entry = {squaredDistanceValue, indexOfPointInArray}
			int[] entry = { squaredDistance_PQ(points[i]), i };
			if (maxPQ.size() < k) {
				maxPQ.add(entry);

			} else if (entry[0] < maxPQ.peek()[0]) {
				// If the max PQ is full and a closer point is found,
				// discard the farthest point and add this one
				maxPQ.poll();
				maxPQ.add(entry);
			}
		}
		
		// Return all points stored in the max PQ
		int[][] ans = new int[k][2];
		for (int i = 0; i < k; i++) {
			int entryIdx = maxPQ.poll()[1];
			ans[i] = points[entryIdx];
		}

		return ans;
	}

	/* 
	 * The distance between two points on the X-Y plane is the 
	 * Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
	 * 
	 * When comparing two points using the squared Euclidean distance and 
	 * the precise Euclidean distance, the results yield the same relative 
	 * comparison because the squared distance preserves the order of distances.
	 * */
	
	private static int squaredDistance_PQ(int[] point) {
		// Calculate and return the squared Euclidean distance
		return point[0] * point[0] + point[1] * point[1];
	}

	// DO NOT USE IN INTERVIEW :: This wont's work in interview
	// Time: O(N LogN)
	// Space: O(Log N), Java's sort methods will require an average of O(logN) extra
	// space.
	public static int[][] kClosest_2(int[][] points, int k) {

		// Sort the array with a custom lambda comparator function
		Arrays.sort(points, (a, b) -> squaredDistance(a) - squaredDistance(b));

		// Return the first k elements of the sorted array
		return Arrays.copyOf(points, k);

	}

}
