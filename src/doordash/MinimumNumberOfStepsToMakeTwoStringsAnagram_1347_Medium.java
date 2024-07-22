package doordash;

public class MinimumNumberOfStepsToMakeTwoStringsAnagram_1347_Medium {

	public static void main(String[] args) {
		String s = "bab", t = "aba";
		System.out.println("Expected: 1, Actual: " + minSteps(s, t));
		
		s = "leetcode"; t = "practice";
		System.out.println("Expected: 5, Actual: " + minSteps(s, t));
		

	}
	
	/*
    Refer: https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/discuss/503450/JavaPython-3-Count-occurrences-and-sum-the-difference-w-analysis.
    
    Time: O(n)
    Space: O(1)
    */
    
    public static int minSteps(String s, String t) {
        int[] count = new int[26];
        
        // Storing the difference of frequencies of characters in t and s.
        for(int i=0; i<s.length(); i++) {
            count[t.charAt(i) - 'a']++;
            count[s.charAt(i) - 'a']--; // after this array will contain char occurances that needs to be changed/updated
        }
        
        int ans = 0;
        
        // Adding the difference where string t has more instances than s.
        // Ignoring where t has fewer instances as they are redundant and
        // can be covered by the first case.
        
        // works
        // for(int i=0; i<26; i++) {
        //     ans += Math.max(0, count[i]);
        // }
        
        // OR
        for(int step : count) {
            if(step > 0) {
                ans += step;
            }
        }
        
        return ans;
    }

}
