package PaloAltoNetworks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams_49_Medium {

	public static void main(String[] args) {
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };

		List<List<String>> res = groupAnagrams(strs);

		System.out.println("Result: " + res);

	}

	// https://leetcode.com/problems/group-anagrams/
	// https://leetcode.com/problems/group-anagrams/solution/

	/**
	 * Complexity Analysis
	 * 
	 * Time Complexity: O(NK), where N is the length of strs, and K is the
	 * maximum length of a string in strs. Counting each string is linear in the
	 * size of the string, and we count every string.
	 * 
	 * Space Complexity: O(NK), the total information content stored in ans. 
	 * 	k = length of one string
	 * 	n = number of strings in the input array
	 */
	public static List<List<String>> groupAnagrams(String[] strs) {
        if(strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> ans = new HashMap<>();
        for(String s : strs) {
            String key = buildAndGetKey(s);
            ans.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(ans.values());
    }
	
    private static String buildAndGetKey(String s) {
        int[] count = new int[26];
        for(char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        return Arrays.toString(count);
    }

}
