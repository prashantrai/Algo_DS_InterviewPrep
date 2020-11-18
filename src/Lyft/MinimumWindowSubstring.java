package Lyft;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {

	public static void main(String[] args) {

		// String s = "ADOBECODEBANC";
		String s = "ABAACBAB";
		String t = "ABC";

		String res = minWindow2(s, t);
		System.out.println(res);
	}

	//--https://leetcode.com/articles/minimum-window-substring/
	//--76. Minimum Window Substring
	//-- https://leetcode.com/problems/minimum-window-substring/
	//--https://leetcode.com/problems/minimum-window-substring/discuss/26810/Java-solution.-using-two-pointers-%2B-HashMap
	public static String minWindow2(String s, String t) {
		if (s == null || s.length() < t.length() || s.length() == 0) {
			return "";
		}
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : t.toCharArray()) {
			if (map.containsKey(c)) {
				map.put(c, map.get(c) + 1);
			} else {
				map.put(c, 1);
			}
		}
		int left = 0;
		int minLeft = 0;
		int minLen = s.length() + 1;
		int count = 0;

		/*
		 * First part: when the right pointer is getting incremented we are decrementing
		 * the map count of char if it's part of 't' string. When we see that the map
		 * count of that char after decrementing is positive/zero means that the right
		 * ptr has found a useful char and hence we increment the 'count' variable
		 * (which is keeping track of the number of useful chars)
		 * 
		 * Second part: when the left pointer is getting incremented we are essentially
		 * making the window smaller and giving back the chars to the map (i.e.
		 * incrementing the map count). If we find that for the particular char the map
		 * count has now become positive means that we actually gave back a useful char
		 * and hence the 'count' is to be decremented.
		 * 
		 * At this point then we again start increasing our window and see each time if
		 * the count has become equal to the number of chars in 't' string.
		 */

		for (int right = 0; right < s.length(); right++) {
			char c_right = s.charAt(right); //--temp for debugging only not used in code..this is just to see the char count in variable tab of eclipse while debugging
			if (map.containsKey(s.charAt(right))) {
				map.put(s.charAt(right), map.get(s.charAt(right)) - 1);
				if (map.get(s.charAt(right)) >= 0) {
					count++;
				}
				while (count == t.length()) {

					if (right - left + 1 < minLen) {
						minLeft = left;
						minLen = right - left + 1;
					}
					
					char c_left = s.charAt(left); //--temp for debugging only not used in code..this is just to see the char count in variable tab of eclipse while debugging
					if (map.containsKey(s.charAt(left))) {
						map.put(s.charAt(left), map.get(s.charAt(left)) + 1);
						if (map.get(s.charAt(left)) > 0) {
							count--;
						}
					}
					left++;
				}
			}
		}
		if (minLen > s.length()) {
			return "";
		}

		return s.substring(minLeft, minLeft + minLen);
	}

	public static String minWindow(String s, String t) {
		if (s == null || s.length() == 0 || t == null || t.length() == 0)
			return "";
		// Keep character frequency of the pattern (here, t)
		Map<Character, Integer> map = new HashMap<>();
		for (char ch : t.toCharArray())
			map.merge(ch, 1, Integer::sum);

		int minWindow = s.length() + 1; // Instead of using Integer max, use one character more than the longer string
		int minStart = 0;

		int start = 0;
		int matchCount = 0;

		/**
		 * The idea is to change the count of character inside the pattern map. As long
		 * as the right character is in the pattern, match occurs.
		 * 
		 * Next step : As long as the matched count is same as pattern length, calculate
		 * the min window and shrink. Shrink means that move the start pointer to the
		 * right. While doing that check if the character is already in the pattern, if
		 * yes, then pattern count is increased. At any time, if a character is
		 * exhausted completely in the pattern and it needs to be removed, then
		 * obviously the match count is reduced indicating that current window has
		 * unmatched characters.
		 **/
		for (int end = 0; end < s.length(); end++) {
			char right = s.charAt(end);

			if (map.containsKey(right)) {
				map.put(right, map.get(right) - 1);
				if (map.get(right) >= 0)
					matchCount++;
			}
			while (matchCount == t.length()) {
				// Calculate window size
				if (minWindow > end - start + 1) {
					minStart = start;
					minWindow = end - start + 1;
				}
				char left = s.charAt(start++); // Shrink the window
				if (map.containsKey(left)) {
					if (map.get(left) == 0)
						matchCount--;
					map.put(left, map.get(left) + 1);
				}
			}
		}
		return minWindow > s.length() ? "" : s.substring(minStart, minStart + minWindow);
	}

}
