package Google;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DivideArrayInSetsOfKConsecutiveNumbers_1296_Medium {

	public static void main(String[] args) {
		
		// These tests are for follow-up question/part only. Main problem is very simple so, no test.

	    // ------------------------------------------------------------
	    // Test 1: Normal case - multiple valid groups
	    // Expected groups:
	    // [1,2,3]
	    // [2,3,4]
	    // [6,7,8]
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr1 = {1, 2, 3, 6, 2, 3, 4, 7, 8};

	    System.out.println("Test 1");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr1, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 2: Important trace case - input is NOT sorted
	    //
	    // Frequencies:
	    // 1 -> 1
	    // 2 -> 2
	    // 3 -> 2
	    // 4 -> 1
	    //
	    // First n = 3:
	    //
	    // start = 3
	    // 2 exists -> start = 2
	    // 1 exists -> start = 1
	    //
	    // Build:
	    // [1,2,3]
	    //
	    // Remaining:
	    // 1 -> 0
	    // 2 -> 1
	    // 3 -> 1
	    // 4 -> 1
	    //
	    // start becomes 2
	    // Build:
	    // [2,3,4]
	    //
	    // Expected: true
	    //
	    // This is probably the BEST test to manually dry-run
	    // during the interview.
	    // ------------------------------------------------------------
	    int[] arr2 = {3, 2, 1, 2, 3, 4};

	    System.out.println("Test 2 - Best Dry Run");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr2, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 3: Missing value inside required consecutive group
	    //
	    // Possible first group:
	    // [1,2,3]
	    //
	    // Remaining:
	    // [2,4,4]
	    //
	    // Starting at 2 would require:
	    // [2,3,4]
	    //
	    // But no 3 remains.
	    //
	    // Expected: false
	    // ------------------------------------------------------------
	    int[] arr3 = {1, 2, 2, 3, 4, 4};

	    System.out.println("Test 3");
	    System.out.println("Expected: false");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr3, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 4: Length not divisible by groupSize
	    //
	    // 4 elements cannot be divided into groups of 3.
	    //
	    // Expected: false
	    // ------------------------------------------------------------
	    int[] arr4 = {1, 2, 3, 4};

	    System.out.println("Test 4");
	    System.out.println("Expected: false");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr4, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 5: Duplicate values require repeatedly creating
	    // groups from the same starting value.
	    //
	    // Frequencies:
	    // 1 -> 2
	    // 2 -> 2
	    // 3 -> 2
	    //
	    // Groups:
	    // [1,2,3]
	    // [1,2,3]
	    //
	    // This specifically exercises:
	    //
	    // while (freqMap.getOrDefault(start, 0) > 0)
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr5 = {1, 1, 2, 2, 3, 3};

	    System.out.println("Test 5");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr5, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 6: Single group
	    //
	    // Group:
	    // [5,6,7]
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr6 = {7, 5, 6};

	    System.out.println("Test 6");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr6, 3));
	    System.out.println();


	    // ------------------------------------------------------------
	    // Test 7: groupSize = 1
	    //
	    // Every individual element forms its own valid group.
	    //
	    // Expected: true
	    // ------------------------------------------------------------
	    int[] arr7 = {5, 2, 9, 2};

	    System.out.println("Test 7");
	    System.out.println("Expected: true");
	    System.out.println("Actual  : "
	            + isPossibleDivide(arr7, 1));
	}

	/* Interview Explanation Before Coding

    “First, if the number of cards is not divisible by groupSize, we can immediately return false because every card must belong to a complete group.

    Then I’ll build a frequency map using a TreeMap, which keeps card values sorted.

    The greedy observation is that the smallest remaining card has to start a group. There cannot be some smaller card later that allows it to sit in the middle of a consecutive sequence.

    So while cards remain, I take the smallest key and try to consume groupSize consecutive values starting from it. For each required card, if its frequency is missing, I return false. Otherwise I decrement its frequency and remove it once its count reaches zero.

    If I successfully consume every card, then the hand can be rearranged into valid groups.”   
	*/
	
	// Most efficient
	// Time: O(n), Space: O(n)
	public static boolean isPossibleDivide(int[] arr, int k) {
		if(arr.length % k != 0) return false;
		
		Map<Integer, Integer> frqMap = new HashMap<>();
		for(int n : arr) {
			frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
		}
		
		for(int n : arr) {
			// as the array is not sorted, we have to find the start
	        // position of the group while decrementing the value of startCard
	        // Find the start of the potential sequence
			int start = n;
			while(frqMap.getOrDefault(start-1, 0) > 0) start--;
			
			while(start <= n) {
				while(frqMap.getOrDefault(start, 0) > 0) {
					for(int i=start; i<start+k; i++) {
						
						if(frqMap.getOrDefault(i, 0) == 0) return false;
						
						frqMap.put(i, frqMap.get(i)-1);
					}
				}
				start++;
			}
		}
		return true;
	}
	
	
	// Time: O(nlogn), Space: O(n)
	public static boolean isPossibleDivide_HashMap(int[] arr, int groupSize) {
		
		if(arr.length % groupSize != 0) return false;
		
		Map<Integer, Integer> frqMap = new HashMap<>();
		for(int n : arr) {
			frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
		}
		
		Arrays.sort(arr);
		for(int n : arr) {
			if(frqMap.get(n) > 0) {
				for(int i=n; i<n+groupSize; i++) {
					if(frqMap.getOrDefault(i, 0) == 0) return false;
					frqMap.put(i, frqMap.get(i) - 1);
				}
			}
		}
		
		return true;
	}
	
	
	/* Step-by-Step Algorithm
	    1. If hand.length % groupSize != 0, return false.
	    2. Build a TreeMap storing the frequency of every card.
	    3. While the map is not empty:
	        1. Get the smallest remaining card using firstKey().
	        2. For groupSize consecutive values starting from it:
	            Check whether that value exists.
	            If not, return false.
	            Decrement its frequency.
	            Remove it when the frequency reaches 0.
	    4. If all cards are consumed, return true.
	*/
	
	// Time: O(n log n)
	// Space: O(n)
	
	public boolean isPossibleDivide_TreeMap(int[] nums, int k) {
	    
	    TreeMap<Integer, Integer> freqMap = new TreeMap<>();
	
	    for(int n : nums) {
	        freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
	    }
	
	    while (!freqMap.isEmpty()) {
	        int start = freqMap.firstKey();
	        
	        for(int x = start; x<start+k; x++) {
	            Integer count = freqMap.get(x);
	
	            if(count == null) return false;
	
	            if(count == 1) {
	                freqMap.remove(x);
	                continue;
	            }
	            freqMap.put(x, freqMap.get(x)-1);
	        }
	    }
	    return true;
	}
	
}
