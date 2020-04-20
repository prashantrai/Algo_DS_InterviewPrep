package leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

public class LongestSubstringWithAtMostKDistinctCharacters_340_Hard {

	public static void main(String[] args) {

		String s = "eceba"; int k = 2;
		int res = lengthOfLongestSubstringKDistinct(s, k);
		System.out.println("Expected: 3, Actual: "+res);
		
		s = "aa"; 
		k = 1;
		res = lengthOfLongestSubstringKDistinct(s, k);
		System.out.println("Expected: 2, Actual: "+res);
	}

    /*
    Complexity Analysis
        Time complexity : O(N) since all operations with ordered dictionary : insert/get/delete/popitem (put/containsKey/remove) are done in a constant time.

        Space complexity : O(k) since additional space is used only for an ordered dictionary with at most k + 1 elements.
    */

	// Premium
	//https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
	//https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/submissions/
	//https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/solution/
	
	// --Snap
	public static int lengthOfLongestSubstringKDistinct(String s, int k) {

		if (s.length() * k == 0) { // either s or k is zero then return zero
			return 0;
		}

		Map<Character, Integer> map = new LinkedHashMap<>();
		int left = 0;
		int right = 0;
		int maxLen = 1;

		while (right < s.length()) {
			char c = s.charAt(right);
			if (map.containsKey(c)) {
				map.remove(c);
			}

			map.put(c, right++);

			if (map.size() > k) {
				// --remove the left most element i.e. value with the lowest index in map
				Map.Entry<Character, Integer> leftMost = map.entrySet().iterator().next();
				map.remove(leftMost.getKey());
				left = leftMost.getValue() + 1;
			}
			maxLen = Math.max(maxLen, right - left);
		}

		return maxLen;
	}

}

/*
 * 340. Longest Substring with At Most K Distinct Characters Hard
 * 
 * 
 * Share Given a string, find the length of the longest substring T that
 * contains at most k distinct characters.
 * 
 * Example 1:
 * 
 * Input: s = "eceba", k = 2 
 * Output: 3 
 * Explanation: T is "ece" which its length is 3. 
 * 
 * Example 2:
 * 
 * Input: s = "aa", k = 1 
 * Output: 2 
 * Explanation: T is "aa" which its length is
 * 
 *
 * Company | NoOfTimesAsked
 * Facebook | 18
 * 
 * Microsoft | 8
 * 
 * Google | 5
 * 
 * Amazon | 2
 * 
 * Snapchat | 2
 * 
 * Citadel | 2
 * 
 * Wish | 2
 */
