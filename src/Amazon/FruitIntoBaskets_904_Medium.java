package Amazon;

import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets_904_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Pattern Recognition
		Sliding Window + HashMap
		
		The key phrase is:
			Longest contiguous subarray with at most 2 distinct values
		
		That strongly suggests a variable-size sliding window.
	 * */
	
	/* Interview Explanation Before Coding

		“I’m going to use a sliding window because we're looking for 
		the longest contiguous range satisfying a constraint.
		
		I'll keep two pointers, left and right, and a HashMap containing 
		the frequency of each fruit type inside the current window.
		
		As I move right, I'll add that fruit to the map. If the map grows 
		beyond two distinct fruit types, the window is invalid, so I'll move 
		left forward, decrementing frequencies and removing a fruit type from 
		the map when its count becomes zero.
		
		Once the window contains at most two fruit types again, it's valid, 
		and I'll update the maximum window length.
		
		Each element is added once and removed at most once, 
		so the total time complexity is linear.”
	 
	 * */
	
	/* Complexity Analysis Before Coding

		Time: O(n), Although there is a while loop inside the for loop, 
					left only moves forward.
		
		Each fruit is: added to the window once removed from the window 
		at most once, so, total work is O(n).
		
		Space: O(1), the map contains at most 3 fruit types temporarily 
		before we shrink it back to 2. Therefore the auxiliary space is constant.
	 
	 * */
	
	/* Step-by-Step Algorithm
		1. Create a HashMap<Integer, Integer> to store fruit frequencies.
		2. Initialize left = 0 and maxFruits = 0.
		3. Move right from 0 to fruits.length - 1.
		4. Add fruits[right] to the map.
		5. While the map contains more than 2 fruit types:
			- decrement the count of fruits[left]
			- remove it if its count becomes 0
			- increment left
		6. The window is now valid.
		7. Update: maxFruits = Math.max(maxFruits, right - left + 1);
		8. Return maxFruits.
	*/
	
	
	public int totalFruit(int[] fruits) {
		Map<Integer, Integer> freqMap = new HashMap<>();
		
		int left = 0;
		int maxFruits = 0;
		
		for(int right = 0; right < fruits.length; right++) {
			// Add current fruit to the window
			freqMap.put(fruits[right], freqMap.getOrDefault(fruits[right], 0)+1);
			
			// More than 2 fruit types -> shrink from left
			while(freqMap.size() > 2) {
				int fruit = fruits[left];
				freqMap.put(fruit, freqMap.get(fruit) - 1);
				if(freqMap.get(fruit) == 0) {
					freqMap.remove(fruit);
				}
				left++;
			}
			maxFruits = Math.max(maxFruits, right - left + 1); // se at the bottom for explanation
		}
		
		return maxFruits;
	}
}


/* right - left + 1 explanation
 
	Suppose:
	
	fruits = [1, 2, 3, 2, 2]
	          0  1  2  3  4
	
	At some point, our valid window is:
	
	[2, 3, 2, 2]
	
	Those are indexes:
	left = 1
	right = 4
	
	So the window is:
	
	index:   0  1  2  3  4
	fruit:  [1, 2, 3, 2, 2]
	            L        R
	
	How many elements are between index 1 and index 4, inclusive?
	
	indexes = 1, 2, 3, 4
	count = 4
	
	And:
	
	right - left + 1
	= 4 - 1 + 1
	= 4
	
	The +1 is needed because both left and right are included.
*/