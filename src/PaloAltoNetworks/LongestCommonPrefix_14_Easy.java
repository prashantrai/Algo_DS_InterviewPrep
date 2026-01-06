package PaloAltoNetworks;
public class LongestCommonPrefix_14_Easy {

	public static void main(String[] args) {

		String[] strs = {"flower","flow","flight"};
		System.out.println("1.Expected: fl, Actual: "+longestCommonPrefix(strs));
		
		String[] strs2 = {"dog","racecar","car"};
		System.out.println("1.Expected: \"\", Actual: "+longestCommonPrefix(strs2));
	}

	/* 
     * Time Complexity: O(n * m), where 
        n = number of strings in the array
        m = average length of each string
     * Space Complexity: O(1) (excluding space for output string)
     */

    public static String longestCommonPrefix(String[] strs) {
        // Edge case: empty input
        if (strs == null || strs.length == 0) return "";

        // Compare character by character at each position
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);

            // Check this character position in all other strings
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
    
}