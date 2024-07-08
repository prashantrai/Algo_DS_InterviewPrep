package Facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupShiftedStrings_249_Medium {

	public static void main(String[] args) {
		String[] strings = {"abc","bcd","acef","xyz","az","ba","a","z"};
		System.out.println("Expected: [[acef],[a,z],[abc,bcd,xyz],[az,ba]]");
		System.out.println("Actual: " + groupStrings(strings));

	}

	
	/*
	 Let N be the length of strings and 
	 K be the maximum length of a string in strings.

	### Time Complexity: O(N * K)
		We iterate over all N strings and for each string, 
		we iterate over all the characters to generate the Hash value, 
		which takes O(K) time. 
		To sum up, the overall time complexity is O(N*K) ).

	### Space Complexity: O(N*K)

	We need to store all the strings plus their 
	Hash values in `mapHashToList`. In the worst scenario, 
	when each string in the given list belongs to a different 
	Hash value, the maximum number of strings stored in 
	`mapHashToList` is ( 2*N ). 
	
	Each string takes at most O(K) space. 
	
	Hence the overall space complexity is O(N*K).

	*/
	
	public static List<List<String>> groupStrings(String[] strings) {
	    Map<String, List<String>> map = new HashMap<>();

	    for(String s : strings) {
	        String key = getKey(s);
//	        String key = getHash(s); // working
	        List<String> list = map.getOrDefault(key, new ArrayList<>());
	        list.add(s);
	        map.put(key, list);
	    }
	    return new ArrayList<>(map.values());
	}

	private static String getKey(String s) {
	    char[] chars = s.toCharArray();
	    StringBuilder key = new StringBuilder();
        //String key = "";
	    for(int i = 1; i < chars.length; i++) {
	        int diff = chars[i] - chars[i-1];
	        //key += diff < 0 ? diff + 26 : diff;
	        //key += ",";
            //String s = diff < 0 ? diff + 26 : diff;
            key.append(diff < 0 ? diff + 26 : diff);
            key.append(",");
                
	    }
	    //System.out.println(key);
	    return key.toString();
	}
	
	// Create a hash value - working
	/*
	 * Calculate relative distance:
	 *  relative_distance = (s.charAt(i) - s.charAt(0) + 26 ) % 26 
	 *  
	 *  '%26' here ensures that relative_distance is always in 
	 *  the range 0-25, regardless of how the characters wrap 
	 *  around the end of the alphabet. 
	 */
    private static String getHash(String s) {
        char[] chars = s.toCharArray();
        StringBuilder hashKey = new StringBuilder();
        
        for (int i = 1; i < chars.length; i++) {
            hashKey.append((char) ((chars[i] - chars[i - 1] + 26) % 26 + 'a'));
        }
        
        return hashKey.toString();
    }
	
	
	
	// Another approach
	// https://leetcode.com/problems/group-shifted-strings/discuss/67459/1-4-lines-in-Java
	public List<List<String>> groupStrings2(String[] strings) {
    return new ArrayList(Stream.of(strings).collect(Collectors.groupingBy(
            s -> s.chars().mapToObj(c -> (c - s.charAt(0) + 26) % 26)
                  .collect(Collectors.toList())
        )).values());
    }
	
}
