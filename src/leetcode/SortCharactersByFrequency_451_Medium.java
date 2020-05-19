package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SortCharactersByFrequency_451_Medium {

	public static void main(String[] args) {
		
		System.out.println("Expected: eert, Actual: "+frequencySort("tree"));
		
		/*Input: "cccaaa"	Output: "cccaaa"

		Explanation:
		Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
		Note that "cacaca" is incorrect, as the same characters must be together.
		*/
		System.out.println("Expected: aaaccc, Actual: "+frequencySort("cccaaa"));
		System.out.println("Expected: bbAa, Actual: "+frequencySort("Aabb"));
		
		// Using Proirity Queue - works well
		System.out.println("Expected: eert, Actual: "+frequencySort3("tree"));
		System.out.println("Expected: aaaccc, Actual: "+frequencySort3("cccaaa"));
		System.out.println("Expected: bbAa, Actual: "+frequencySort3("Aabb"));

	}
	
	// https://leetcode.com/problems/sort-characters-by-frequency/
	
	/*
    Complexity Analysis

    Let n be the length of the input String. The k (number of unique characters in the input String that we considered for the last approach makes no difference this time).

    Time Complexity : O(n).

    Like before, the HashMap building has a cost of O(n).
    The bucket sorting is O(n), because inserting items has a cost of O(k) (each entry from the HashMap), and building the buckets initially has a worst case of O(n) (which occurs when k = 1). Because k ≤ n, we're left with O(n).
    So in total, we have O(n).

    It'd be impossible to do better than this, because we need to look at each of the n characters in the input String at least once.

    Space Complexity : O(n).
    Same as above. The bucket Array also uses O(n) space, because its length is at most n, and there are k items across all the buckets.
    
	 */
    
    public static String frequencySort (String s) {
        if (s == null || s.isEmpty()) return s;
        
        // Count up the occurances.
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        
        int maxFrequency = Collections.max(counts.values());

        // Make a list of the keys, sorted by frequency.
        List<List<Character>> buckets = new ArrayList<>(); 
        for(int i=0; i<=maxFrequency; i++) {
            buckets.add (new ArrayList<Character>());
        }
        
        // iterate the keyset and add value in the bucket by taking count as index of bucket
        for(Character key : counts.keySet()) {
            int freq = counts.get(key);
            buckets.get(freq).add(key);
        }
        
        //build string
        StringBuilder sb = new StringBuilder();
        for(int i=buckets.size()-1; i>=0; i--) { // why reverse? because the max_index = max_freq
            for(Character c : buckets.get(i)) {
                for(int j=0; j<i; j++) {
                    sb.append(c);
                }
            }           
            
        }
        
        return sb.toString();
    }
	
	
    /* Approach: Using priority queue
     * 
     * Time Complexity : nlogm, adding into PriorityQueue is logm work. 
     * However in (nlogm), m is the distinguish character, can be O(1) since only 26 letters. 
     * So the overall time complexity should be O(n), the same as the buck sort with less memory use.
     * */
    
    public static String frequencySort3 (String s) {
        if (s == null || s.isEmpty()) return s;
        
        // Count up the occurances.
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());
        pq.addAll(counts.entrySet());
        
        //build string
        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()) {
        	Map.Entry<Character, Integer> entry = pq.poll();
        	for(int i=0; i<entry.getValue(); i++) {
        		sb.append(entry.getKey());
        	}
        }
        
        return sb.toString();
        
    }
    
	
	/*  Approach 2: HashMap and Sort (From Article and not the most efficient could be done in linear, see other implementation):: 
    
	Complexity Analysis::

	Let nn be the length of the input String and kk be the number of unique characters in the String.

	We know that k ≤ nk≤n, because there can't be more unique characters than there are characters in the String. We also know that kk is somewhat bounded by the fact that there's only a finite number of characters in Unicode (or ASCII, which I suspect is all we need to worry about for this question).

	There are two ways of approaching the complexity analysis for this question.

	We can disregard kk, and consider that in the worst case, k = n.
	We can consider kk, recognising that the number of unique characters is not infinite. This is more accurate for real world purposes.
	I've provided analysis for both ways of approaching it. I choose not to bring it up for the previous approach, because it made no difference there.

	Time Complexity : O(nlogn) OR O(n+klogk).

	Putting the characterss into the HashMap has a cost of O(n), because each of the nn characterss must be put in, and putting each in is an O(1) operation.

	Sorting the HashMap keys has a cost of O(klogk), because there are kk keys, and this is the standard cost for sorting. If only using nn, then it's O(nlogn). For the previous question, the sort was carried out on nn items, not kk, so was possibly a lot worse.

	Traversing over the sorted keys and building the String has a cost of O(n), as nn characters must be inserted.

	Therefore, if we're only considering nn, then the final cost is O(nlogn).

	Considering kk as well gives us O(n+klogk), because we don't know which is largest out of n and klogk. We do, however, know that in total this is less than or equal to O(nlogn).

	Space Complexity : O(n).

	The HashMap uses O(k) space.

	However, the StringBuilder at the end dominates the space complexity, pushing it up to O(n), as every character from the input String must go into it. Like was said above, it's impossible to do better with the space complexity here.

	What's interesting here is that if we only consider nn, the time complexity is the same as the previous approach. But by considering kk, we can see that the difference is potentially substantial.
	    
	    */
	public static String frequencySort2(String s) {
		
		// build frequency table
		Map<Character, Integer> counts = new HashMap<>();
		for(char ch : s.toCharArray()) {
			counts.put(ch, counts.getOrDefault(ch, 0)+1);
		}
		
		//PriorityQueue<Character> q = new PriorityQueue<>();
		List<Character> characters = new ArrayList<>(counts.keySet());
		Collections.sort(characters, (a, b) -> counts.get(b) - counts.get(a));

		// build sorted string
		StringBuilder sb = new StringBuilder();
		for(char ch : characters) {
			for(int i=0; i<counts.get(ch); i++) {
				sb.append(ch);
			}
		}
		
		return sb.toString();
		
	}

}
