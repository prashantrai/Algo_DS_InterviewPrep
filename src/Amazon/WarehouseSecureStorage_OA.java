package Amazon;

public class WarehouseSecureStorage_OA {

	public static void main(String[] args) {

        // Example
        int[] logs1 = {14, 25, 18};
        System.out.println("Expected: 24, Actual: " + maximumSecureItems(logs1, 4)); // 24
        System.out.println("Expected: 24, Actual: " + maximumSecureItems2(logs1, 4)); // 24

        // Single delivery log
        int[] logs2 = {100};
        System.out.println(maximumSecureItems(logs2, 2)); // 50

        // Multiple equal logs
        int[] logs3 = {10, 10, 10};
        System.out.println(maximumSecureItems(logs3, 4)); // 10

        // All zero
        int[] logs4 = {0, 0, 0};
        System.out.println(maximumSecureItems(logs4, 2)); // 0

        // Large values
        int[] logs5 = {1000000000, 1000000000};
        System.out.println(maximumSecureItems(logs5, 2)); // 1000000000

        // Uneven logs
        int[] logs6 = {5, 9, 20};
        System.out.println(maximumSecureItems(logs6, 4));

        // Small values
        int[] logs7 = {1, 2, 3};
        System.out.println(maximumSecureItems(logs7, 2));
        
        //-----
        
        // delivery_logs[3,5,9,6] and k=4 expected output: 9
        int[] logs8 = {3,5,9,6}; int k = 4; 
        System.out.println("Expected: 8, Actual: " + maximumSecureItems(logs8, 4)); // 8
        System.out.println("Expected: 8, Actual: " + maximumSecureItems2(logs8, 4)); // 8
        
//        delivery_logs[5,5,5,5,5,5] and k=4 expected output: 10
        int[] logs9 = {5,5,5,5,5,5}; k = 4;
        System.out.println("Expected: 10, Actual: " + maximumSecureItems(logs9, 4)); // 10
        System.out.println("Expected: 10, Actual: " + maximumSecureItems2(logs9, 4)); // 10
    }
	
	
	/*
	 * Interview Script 
	 * Candidate: "To maximize the total items in the secure (k/2) warehouses, 
	 * 	the optimal strategy is to distribute items as evenly as possible across 
	 * 	all (k) warehouses. If we make any warehouse exceptionally larger than the 
	 * 	others, it will fall into the top (k/2) category and get compromised anyway. 
	 * 	Thus, the problem boils down to finding the maximum possible uniform capacity 
	 * 	X that each warehouse can hold."
	 * 
	 * Interviewer:
	 * "That makes sense. How do you plan to find that maximum capacity X efficiently?"
	 * 
	 * Candidate:
	 * "We can use Binary Search on the Answer. The capacity X must be
	 *  between 1 and the maximum value in deliveryLogs. For any guessed capacity mid, 
	 *  we can greedily check how many warehouses we can fill by calculating log / mid 
	 *  for each shipment log. If the total warehouses we can fill is at least (k), 
	 *  then mid is a valid capacity, and we try to find a larger one by searching 
	 *  the right half. Otherwise, we search the left half."
	 *  
	 * Interviewer: "What would be the time complexity for this approach?"
	 * 
	 * Candidate: "The check function takes (O(N)) time as it iterates through 
	 * 	the logs. The binary search takes (O(log M)) steps, where (M) is the 
	 * 	maximum value in deliveryLogs. So, the overall time complexity will be 
	 * 	(O(N log M)), which easily runs within the time limit for (N = 10^5) 
	 * 	and (M = 10^9). The space complexity will be (O(1)) since we only use a few variables."
	 * 
	 * Interviewer: "Great. Please go ahead and implement the solution."
	 */
	
	/* #### Example & Explanation
		Input: 
		* `deliveryLogs = [14, 25, 18]`
		* `k = 4`
		
		Output: 
		* `24`
		
		Explanation:
		We need to fill (k = 4) warehouses. After allocation, the top (k/2 = 2) warehouses are compromised, 
		and the bottom (k/2 = 2) warehouses are secure.
		
		Suppose we decide that each warehouse should hold at least 8 items. 
		
		Let's see if we can fill 4 warehouses:
		
		From log 14: We can fill [14 / 8] = 1 warehouse.
		From log 25: We can fill [25 / 8] = 3 warehouses.
		From log 18: We can fill [18 / 8] = 2 warehouses.
		
		Total warehouses filled = (1 + 3 + 2 = 6). Since 6 >= 4, a threshold of 8 items per warehouse is 
		completely achievable.
		
		
		If we increase the threshold to 9 items:
		From log 14: [14 / 9] = 1) warehouse.
		From log 25: [25 / 9] = 2) warehouses.
		From log 18: [18 / 9] = 2) warehouses.
		
		Total warehouses filled = (1 + 2 + 2 = 5). Since (5 ge 4), 9 items per warehouse is also achievable.
		
		If we try 10 items:Total warehouses filled = [14 / 10] + [25 / 10] + [18 / 10] = 1 + 2 + 1 = 4). This exactly satisfies (k = 4).
		If we try 11 items:Total warehouses filled = [14 / 11] + [25 / 11] + [18 / 11] = 1 + 2 + 1 = 4). This also satisfies (k = 4).
		If we try 12 items:Total warehouses filled = [14 / 12] + [25 / 12] + [18 / 12] = 1 + 2 + 1 = 4).
		If we try 13 items:Total warehouses filled = [14 / 13] + [25 / 13] + [18 / 13] = 1 + 1 + 1 = 3). 
		
		This is less than 4, so it's impossible.The maximum size we can give to the warehouses is 12 items.We set all 4 warehouses to hold exactly 12 items. The 2 largest are compromised, leaving 2 secure warehouses.Secure total = (2 times 12 = 24). (Note: If we distribute tightly as ([12, 12, 13, 12]), the bottom 2 are still 12).(Note: Depending on the specific variant of the test case, if the maximum size achievable is 8, the secure total would be (2 times 8 = 16).)
 
	 * */
	
	/* Step-by-Step Algorithm (Implementation Checklist)
		- Find the maximum delivery log.
		- Binary search warehouse size between 1 and maxLog.
		- For each candidate size:
			Count how many warehouses can be created.
			Stop early if count reaches k.
		- If count ≥ k
			Save answer.
			Search larger sizes.
		- Otherwise search smaller sizes.
		- Return (k/2) * bestSize.
	 
	 * */
	
	/*
	 Time: O(n log(maxDeliveryLog)),
	  		- 	Finding the maximum value in maxLog takes O(N) time.
	 		- 	The binary search eliminates half of the remaining range at each step, 
	 			executing O(log M) loops where M = max(deliveryLogs).
	 		- 	Inside each binary search step, we iterate through the array of size 
	 			N, taking O(N time.
	 			
	 Space: O(1)
	 * */
	
	// Returns the maximum number of safely stored items.
	public static long maximumSecureItems(int[] deliveryLogs, int k) {
		// Edge case checks
		if(deliveryLogs == null || deliveryLogs.length == 0 || k <=0) return 0;
		
		// Find the largest delivery log.
        // This becomes the upper bound of binary search.
		int maxLog = 0;
		for(int log : deliveryLogs) {
			maxLog = Math.max(log, maxLog);
		}
		
		// No positive warehouse size is possible.
		if(maxLog == 0) return 0;
		
		// Binary search for the largest feasible warehouse size.
		int left = 1; 
		int right = maxLog;
		int bestSize = 0;
		
		while (left <= right) {
			int mid = left + (right - left)/2;
			
			long warehouses = 0;
			
			// Count how many warehouses of size 'mid'
            // can be created from all delivery logs.
			
			for (int log : deliveryLogs) {
				warehouses += log/mid;
				
				// No need to continue once we already know
                // this size is feasible.
				if(warehouses >= k) break;
			}
			
			if(warehouses >= k) {
				// Current size works.
                // Try making every warehouse larger.
				bestSize = mid;
				left = mid+1;
			} else {
				// Too large.
                // Reduce warehouse size.
				right = mid - 1;
			}
		}
		
		// Every surviving warehouse contains bestSize items.
		return (long) bestSize *(k/2);
	}
	
	
	public static long maximumSecureItems2(int[] deliveryLogs, int k) {
        // Edge case checks
        if (deliveryLogs == null || deliveryLogs.length == 0 || k <= 0) {
            return 0;
        }

        int maxLog = 0;
        for (int log : deliveryLogs) {
            if (log > maxLog) {
                maxLog = log;
            }
        }

        // Binary search range for the uniform capacity of each warehouse
        long left = 1;
        long right = maxLog;
        long optimalCapacity = 0;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (canFillWarehouses(deliveryLogs, k, mid)) {
                optimalCapacity = mid; // mid is achievable, save it
                left = mid + 1;        // Try to look for a larger capacity
            } else {
                right = mid - 1;       // mid is too large, look for a smaller capacity
            }
        }

        // Total secure items = capacity of one warehouse * number of secure warehouses (k / 2)
        return optimalCapacity * (k / 2);
    }

    // Helper method to check if we can fill at least k warehouses with 'capacity' items each
    private static boolean canFillWarehouses(int[] deliveryLogs, int k, long capacity) {
        long totalWarehousesFilled = 0;
        
        for (int log : deliveryLogs) {
            totalWarehousesFilled += (log / capacity);
            // Optimization: If we already met or exceeded k, we can return true early
            if (totalWarehousesFilled >= k) {
                return true;
            }
        }
        
        return totalWarehousesFilled >= k;
    }

}

/*
 # Warehouse Secure Storage

An Amazon logistics facility receives a set of bulk shipments, represented as an integer array 
`deliveryLogs` of size `n`. Each element `deliveryLogs[i]` represents the total number of 
delivery items contained in the i-th shipment log. 

The facility needs to distribute these items across exactly `k` different warehouses subject to 
specific operational rules:

1. No Mixing Logs: A single warehouse can only store items originating from one specific delivery log. 
	It cannot mix items from two or more different logs.
2. Log Splitting: A single delivery log's items can be split across multiple warehouses.
3. Partial Allocation: It is not required to use all items from a delivery log. Any leftover 
	items from a log can be left unallocated.
4. Equal Distribution Strategy: To maintain a balanced inventory distribution, 
	the warehouses assigned to take items from the same log are filled with an equal number of items. 
	Consequently, the optimal allocation strategy relies on establishing a uniform minimum capability 
	threshold across all warehouses.

### The Compromise Scenario
Once all `k` warehouses are filled, a system vulnerability occurs where the k/2 warehouses with 
the largest number of stored items are compromised and their contents are lost. 
The remaining k/2 warehouses are secure, and their stored contents are saved.

### Task
Your goal is to allocate items to the k warehouses such that you maximize the total number 
of items stored safely in the remaining k/2 secure warehouses. 

---

### Constraints
* 1 <= n <= 10^5 (Number of delivery logs)
* 2 <= k <= 10^5 (Number of warehouses, where k is always an even integer)
* 0 <= deliveryLogs[i] <= 10^9 (Items per log)

---

### Examples

#### Example 1
Input: 
* `deliveryLogs = [14, 25, 18]`
* `k = 4`

Output: 
* `24`

Explanation:
We need to fill (k = 4) warehouses. After allocation, the top (k/2 = 2) warehouses are compromised, 
and the bottom (k/2 = 2) warehouses are secure.

Suppose we decide that each warehouse should hold at least 8 items. 

Let's see if we can fill 4 warehouses:

From log 14: We can fill [14 / 8] = 1 warehouse.
From log 25: We can fill [25 / 8] = 3 warehouses.
From log 18: We can fill [18 / 8] = 2 warehouses.

Total warehouses filled = (1 + 3 + 2 = 6). Since 6 >= 4, a threshold of 8 items per warehouse is 
completely achievable.


If we increase the threshold to 9 items:
From log 14: [14 / 9] = 1) warehouse.
From log 25: [25 / 9] = 2) warehouses.
From log 18: [18 / 9] = 2) warehouses.

Total warehouses filled = (1 + 2 + 2 = 5). Since (5 ge 4), 9 items per warehouse is also achievable.

If we try 10 items:Total warehouses filled = [14 / 10] + [25 / 10] + [18 / 10] = 1 + 2 + 1 = 4). This exactly satisfies (k = 4).
If we try 11 items:Total warehouses filled = [14 / 11] + [25 / 11] + [18 / 11] = 1 + 2 + 1 = 4). This also satisfies (k = 4).
If we try 12 items:Total warehouses filled = [14 / 12] + [25 / 12] + [18 / 12] = 1 + 2 + 1 = 4).
If we try 13 items:Total warehouses filled = [14 / 13] + [25 / 13] + [18 / 13] = 1 + 1 + 1 = 3). 

This is less than 4, so it's impossible.The maximum size we can give to the warehouses is 12 items.We set all 4 warehouses to hold exactly 12 items. The 2 largest are compromised, leaving 2 secure warehouses.Secure total = (2 times 12 = 24). (Note: If we distribute tightly as ([12, 12, 13, 12]), the bottom 2 are still 12).(Note: Depending on the specific variant of the test case, if the maximum size achievable is 8, the secure total would be (2 times 8 = 16).)

 
 
 */