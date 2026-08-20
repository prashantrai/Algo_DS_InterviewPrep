package Oracle;

public class MinimumWindowSubstring_76_Hard {

	public static void main(String[] args) {

		String s = "ADOBECODEBANC", t = "ABC";
		
		System.out.println("Expected: BANC, Actual: " + minWindow(s, t));
	}
	
	
	/*
	 * Interview Explanation Before Coding
	 * 
	 * "I'm going to use a sliding window. I'll first build a frequency array
	 * representing how many of each character from t we still need.
	 * 
	 * I'll keep a remaining count initialized to t.length(), which tells me how
	 * many total required character occurrences are still missing.
	 * 
	 * As I move the right pointer, if the current character was still needed, I'll
	 * decrement remaining. I'll always decrement its frequency because that also
	 * lets the array represent extra characters in the window using negative
	 * values.
	 * 
	 * Once remaining becomes zero, the current window contains everything we need.
	 * At that point I'll shrink from the left while the window remains valid,
	 * recording the smallest window along the way.
	 * 
	 * If removing the left character makes its frequency positive, that means we've
	 * removed a required occurrence, so the window is no longer valid and we
	 * continue expanding again."
	 */
	
	/* Step-by-Step Algorithm::  
	 * 1. Build a frequency array containing the required counts from t. 
	 * 2. Set remaining = t.length(). 
	 * 3. Expand right through s. 
	 * 4. If the current character is still required, decrement remaining. 
	 * 
	 * 5. Decrement its frequency count. 
	 * 
	 * 6. When remaining == 0, the window is valid. 
	 * 
	 * 7. Record the window if it's smaller than the current best. 
	 * 
	 * 8. Move left forward: 
	 * 	restore the removed character's frequency; 
	 * 	if its frequency becomes positive, we removed something required, 
	 * 	so increment remaining. 
	 * 
	 * 9. Continue until right reaches the end. 
	 * 
	 * 10. Return the smallest recorded substring, or "" if none exists.
	 */
	
	/* Time: O(N), Space: O(1)
	 * */
	
	public static String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        int[] need = new int[128];

        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int remaining = t.length();

        int left = 0;
        int minStart = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            // This occurrence was still needed.
            if (need[rightChar] > 0) {
                remaining--;
            }

            // Negative value means we have extra copies in the window.
            need[rightChar]--;

            // Current window contains everything from t.
            while (remaining == 0) {
                int windowLength = right - left + 1;

                if (windowLength < minLength) {
                    minLength = windowLength;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                need[leftChar]++;
                left++;

                // We removed a required occurrence, so window is invalid again.
                if (need[leftChar] > 0) {
                    remaining++;
                }
            }
        }

        return minLength == Integer.MAX_VALUE
                ? ""
                : s.substring(minStart, minStart + minLength);
    }

}
