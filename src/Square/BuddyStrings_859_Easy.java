package Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuddyStrings_859_Easy {

	public static void main(String[] args) {
		String s = "ab", goal = "ba";
		System.out.println("Expected: true, Actual: " + buddyStrings(s, goal));
		
		s = "ab"; goal = "ab";
		System.out.println("Expected: false	, Actual: " + buddyStrings(s, goal));
		
		s = "aa"; goal = "aa";
		System.out.println("Expected: true, Actual: " + buddyStrings(s, goal));

	}
	
	//https://leetcode.com/problems/buddy-strings/discuss/141780/Easy-Understood
    // Time: O(N)
    // Space: O(1), we are using set and it's size will never go beyond 26
    
    public static boolean buddyStrings(String s, String goal) {
        if(s.length() != goal.length()) return false;
        
        // when contains dullicated like s=aa and goal=aa
        if(s.equals(goal)) {
            Set<Character> set = new HashSet<>();
            for(char c : s.toCharArray()) 
                set.add(c);
            
            // if 2 strings are equal then set will have no duplicates and 
            // size will be smaller than the original string
            return set.size() < s.length();
        }
        
        List<Integer> diff = new ArrayList<>();
        for(int i=0; i<s.length(); i++) {
            if(s.charAt(i) != goal.charAt(i))
                diff.add(i);
        }
        
        return diff.size() == 2
                && s.charAt(diff.get(0)) == goal.charAt(diff.get(1))
                && s.charAt(diff.get(1)) == goal.charAt(diff.get(0));
        
    }

}
