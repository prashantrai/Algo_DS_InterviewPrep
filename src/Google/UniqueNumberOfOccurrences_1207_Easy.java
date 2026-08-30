package Google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniqueNumberOfOccurrences_1207_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time and Space: O(n)
	public static boolean uniqueOccurrences(int[] arr) {
        
        Set<Integer> seen = new HashSet<>();
        Map<Integer, Integer> frqMap = new HashMap<>();
        for(int n : arr) {
            frqMap.put(n, frqMap.getOrDefault(n, 0)+1);
        }
        for(int n : frqMap.values()) {
            if (seen.contains(n)) return false;
            seen.add(n);
        }
        return true;
    }

}
