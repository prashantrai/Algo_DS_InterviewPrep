package Google;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HandOfStraights_846_Medium {

	public static void main(String[] args) {

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
	            + isNStraightHand(arr1, 3));
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
	            + isNStraightHand(arr2, 3));
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
	            + isNStraightHand(arr3, 3));
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
	            + isNStraightHand(arr4, 3));
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
	            + isNStraightHand(arr5, 3));
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
	            + isNStraightHand(arr6, 3));
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
	            + isNStraightHand(arr7, 1));
	}
	
	/*
	 * Interview Explanation Before Coding
	 * 
	 * “First, if the number of cards is not divisible by groupSize, we can
	 * immediately return false because every card must belong to a complete group.
	 * 
	 * Then I’ll build a frequency map using a TreeMap, which keeps card values
	 * sorted.
	 * 
	 * The greedy observation is that the smallest remaining card has to start a
	 * group. There cannot be some smaller card later that allows it to sit in the
	 * middle of a consecutive sequence.
	 * 
	 * So while cards remain, I take the smallest key and try to consume groupSize
	 * consecutive values starting from it. For each required card, if its frequency
	 * is missing, I return false. Otherwise I decrement its frequency and remove it
	 * once its count reaches zero.
	 * 
	 * If I successfully consume every card, then the hand can be rearranged into
	 * valid groups.”
	 */
	
	
	/* Complexity:
	    Time:  O(N)
	    Space: O(N)
	
	    Build frequency map        O(N)
	    Backward/start searching   O(N) amortized
	    Consume all cards          O(N)
	    --------------------------------
	    Overall                    O(N)
	
	 */
	public static boolean isNStraightHand(int[] hand, int groupSize) {
	    if (hand == null || hand.length == 0 || groupSize <= 0) {
	        return false;
	    }
	
	    if (hand.length % groupSize != 0) return false;
	
	    // Building frquency map: O(N)
	    Map<Integer, Integer> count = new HashMap<>();
	    for (int num : hand) {
	        count.put(num, count.getOrDefault(num, 0) + 1);
	    }
	
	    for (int num : hand) {
	        int startCard = num;
	        // as the array is not sorted, we have to find the start
	        // position of the group while decrementing the value of startCard
	        // Find the start of the potential straight sequence
	        while(count.getOrDefault(startCard-1, 0) > 0) {
	            startCard--;
	        }
	
	        while(startCard <= num) {
	            while (count.getOrDefault(startCard, 0) > 0) {
	                for(int i = startCard; i < startCard + groupSize; i++) {
	                    if(count.getOrDefault(i, 0) == 0) 
	                    	return false;
	                    
	                    count.put(i, count.get(i) - 1);
	                }
	            }
	            startCard++;
	        }
	
	    }   
	
	    return true;
	
	}
	
	
	/* Algorithm
    1. If the total number of cards is not divisible by groupSize:
        return false immediately (grouping is impossible)
    2. Count the frequency of each card value using a map.
    3. Sort the hand in increasing order.
    4. Iterate through each card value in the sorted hand:
        If the current card is already used up (its count is 0), skip it
        Otherwise, try to form a group starting from this card
    5. To form a group starting at num:
        For every value from num to num + groupSize - 1:
            If that value does not exist in the count map, return false
            Otherwise, decrement its count by 1
    6. If all cards are successfully grouped without failure:
        return true
	*/
	/* HashMap + sort is better than TreeMap as we performing sorting only 
	once int he HashMap+sort solution whereas in TreeMap (even in Heap solution) 
	every entry to TreeMap will be O(n log n). So, eventhough overall 
	time complexity is same for both, HashMap is a better option.
	*/
	
	// Time: O(n log n)
	// Space: O(n)
	public boolean isNStraightHand_HashMap(int[] hand, int groupSize) {
		if (hand == null || hand.length == 0 || groupSize <= 0) {
		    return false;
		}
		
		if (hand.length % groupSize != 0) return false;
	
	    Map<Integer, Integer> count = new HashMap<>();
	    for (int num : hand) {
	        count.put(num, count.getOrDefault(num, 0) + 1);
	    }
	
	    Arrays.sort(hand); // O(n log n)
	    for (int num : hand) {
	        if (count.get(num) > 0) {
	            for (int i = num; i < num + groupSize; i++) {
	                if (count.getOrDefault(i, 0) == 0) return false;
	                count.put(i, count.get(i) - 1);
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
	public boolean isNStraightHandTreeMap(int[] hand, int groupSize) {
	    if(hand == null || hand.length == 0 || groupSize == 0) return false;
	    
	    if(hand.length % groupSize != 0) return false;
	
	    TreeMap<Integer, Integer> frqMap = new TreeMap<>();
	    for(int card : hand) {
	        frqMap.put(card, frqMap.getOrDefault(card, 0)+1);
	    }
	
	    while(!frqMap.isEmpty()) {
	        int start = frqMap.firstKey();
	
	        for(int x=start; x<start+groupSize; x++) {
	
	            Integer count = frqMap.get(x);
	
	            if( count == null) return false;

	            count--;

	            if (count == 0) {
	            	frqMap.remove(x);
	            } else {
	            	frqMap.put(x, count);
	            }
	        }
	    }
	
	    return true;
	
	}
	

}
