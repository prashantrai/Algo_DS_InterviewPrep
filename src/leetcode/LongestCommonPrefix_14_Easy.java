package leetcode;

public class LongestCommonPrefix_14_Easy {

	public static void main(String[] args) {

		String[] strs = {"flower","flow","flight"};
		System.out.println("1.Expected: fl, Actual: "+longestCommonPrefix(strs));
		System.out.println("2.Expected: fl, Actual: "+longestCommonPrefix2(strs));
		
		String[] strs2 = {"dog","racecar","car"};
		System.out.println("1.Expected: \"\", Actual: "+longestCommonPrefix(strs2));
		System.out.println("2.Expected: \"\", Actual: "+longestCommonPrefix2(strs2));
	}

	// Time: O(N), Space: O(1)
    // vertical scanning - compare characters from top to bottom on the same column (same character index of the strings) before moving on to the next column
    public static String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        
        for(int i=0; i<strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for(int j=1; j<strs.length; j++) {
                if(i == strs[j].length() || c != strs[j].charAt(i)) {
                    return prefix.substring(0, i);
                }
            }
        }
        return prefix;
    }
    
    // Horizontal scanning
    // Time: O(N), Space: O(1)
    public static String longestCommonPrefix2(String[] strs) {
     
        if(strs == null || strs.length == 0) return "";
        
        String prefix = strs[0];
        
        for(int i=1; i<strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length()-1);
                if(prefix.isEmpty()) return "";
            }
        } 
        
        return prefix;
    }
}
