package Intuit;

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
	 */
	public static List<List<String>> groupAnagrams(String[] strs) {

		Map<String, List<String>> map = new HashMap<>();
		int[] count = new int[26];
		for (String s : strs) {

			String key = buildAndGetKey(s, count);

			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}
			map.get(key).add(s);

		}
		List<List<String>> res = new ArrayList<>(map.values());
		return res;
	}

	public static String buildAndGetKey(String s, int[] count) {
		Arrays.fill(count, 0);
		for (char c : s.toCharArray()) {
			count[c - 'a']++;
		}

		//return Arrays.toString(count);  // another approach - Just return the the array in string format no need to use StringBuilder 
		
		StringBuilder sb = new StringBuilder();
		for (int frequency : count) {
			sb.append("#");
			sb.append(frequency);
		}

		return sb.toString();
	}

}
