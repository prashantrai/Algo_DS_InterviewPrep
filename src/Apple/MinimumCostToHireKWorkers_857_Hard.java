package Apple;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class MinimumCostToHireKWorkers_857_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* This suggests an algorithm: 
	 * 1. Calculate each worker's minimum wage-to-quality ratio. 
	 * 2. Sort workers by that ratio in ascending order. 
	 * 3. Process workers in sorted order. The current worker's ratio is the maximum
	 *    ratio among all workers considered so far. 
	 * 4. Maintain the k workers with the smallest qualities using a max-heap. 
	 *    If the heap grows beyond k, remove the worker with the largest quality. 
	 * 
	 * 5. Whenever the heap contains exactly k workers, 
	 *    calculate totalQuality × currentRatio and update the minimum cost.
	 * 
	 * Why a max-heap? Because we want to remove the worker with the largest quality
	 * whenever we have too many workers.
	 */
	
	/* Complexity: 
    - Time: (O(nlogn + nlogk)), which simplifies to (O(nlog n)).
    - Space: (O(n+k)), which simplifies to (O(n)).
     */

    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;

        // Each worker: [ratio, quality]
        double[][] workers = new double[n][2];

        for(int i=0; i<n; i++) {
            workers[i][0] = (double) wage[i]/quality[i];
            workers[i][1] = quality[i]; 
        }
        
       // Max-heap: remove largest quality when size exceeds k
        PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>(Collections.reverseOrder());
        ///

        // Sort by required wage-to-quality ratio
        Arrays.sort(workers, (a, b) -> Double.compare(a[0], b[0]));

        // Max-heap: remove largest quality when size exceeds k
        PriorityQueue<Integer> maxHeap2 
                = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        int totalQuality = 0;
        double minCost = Double.MAX_VALUE;
        
        for(double[] worker : workers) {
            double ratio = worker[0];
            int q = (int) worker[1];

            maxHeap.offer(q);
            totalQuality += q;

            // Keep at most k workers
            if(maxHeap.size() > k) {
                totalQuality -= maxHeap.poll();
            }

            // Evaluate a group of exactly k workers
            if(maxHeap.size() == k) {
                double cost = totalQuality * ratio;
                minCost = Double.min(cost, minCost);
            }

        }

        return minCost;
    }

}
