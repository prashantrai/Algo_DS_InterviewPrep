package Google;

import java.util.TreeMap;

public class HandOfStraights_846_Medium {

	public static void main(String[] args) {

	}
	
	/* Interview Explanation Before Coding

    “First, if the number of cards is not divisible by groupSize, we can immediately return false because every card must belong to a complete group.

    Then I’ll build a frequency map using a TreeMap, which keeps card values sorted.

    The greedy observation is that the smallest remaining card has to start a group. There cannot be some smaller card later that allows it to sit in the middle of a consecutive sequence.

    So while cards remain, I take the smallest key and try to consume groupSize consecutive values starting from it. For each required card, if its frequency is missing, I return false. Otherwise I decrement its frequency and remove it once its count reaches zero.

    If I successfully consume every card, then the hand can be rearranged into valid groups.”   
	*/
	
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
	public boolean isNStraightHand(int[] hand, int groupSize) {
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
	
	            if(count == 1) {
	                frqMap.remove(x);
	                continue;
	            }
	
	            frqMap.put(x, frqMap.get(x) - 1);
	        }
	    }
	
	    return true;
	
	}
	

}
