package LinkedIn;

public class MinimumWindowSubstring_76_Hard {

	public static void main(String[] args) {

		String s = "ADOBECODEBANC", t = "ABC";
		
		System.out.println("Expected: BANC, Actual: " + minWindow(s, t));
	}
	
	
	/* To understand the algorithm follow below videos: 
	 * 		https://www.youtube.com/watch?v=PzAHfUY6GGk&ab_channel=EdRoh
	 * 		https://www.youtube.com/watch?v=jSto0O4AJbM&ab_channel=NeetCode
	 * 		https://www.youtube.com/watch?v=0GOyCIJ2ajQ&ab_channel=AmellPeralta
	 * 
	 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
	 * 
	 * Time: O(N), Space: O(1)
	 * */
	
	public static String minWindow(String s, String t) {
        
        if(s == null || t == null || s.length() == 0) return "";
        
        //record char freq
        int[] charCount = new int[128];
        int left = 0, right = 0;
        int minStart = 0; //to record min substring start index
        int minLen = Integer.MAX_VALUE;
        int count = 0;
        
        for(char c : t.toCharArray()) {
            charCount[c]++; //increament the count/freq
        }
        
        for(right=0; right<s.length(); right++) {
            char c1 = s.charAt(right);
            if(charCount[c1] > 0) {    
                count++;
            }
            charCount[c1]--;
            while(count == t.length()) { // we have found all the 
                if(minLen > right - left) {
                    minLen = right-left+1;
                    minStart = left;
                }
                char c2 = s.charAt(left);
                charCount[c2]++;
                if(charCount[c2] > 0) {
                    count--;
                }
                left++;            
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart+minLen);
        
    }

}
