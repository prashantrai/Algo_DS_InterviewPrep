package leetcode;

import java.util.ArrayList;
import java.util.List;

public class FindCommonCharacters_1002_Easy {

	public static void main(String[] args) {

		String[] s = {"bella","label","roller"};
		System.out.println("Expected: [e, l, l], Actual" + commonChars(s));
		
	}
	
	// Time: O(N) Space: O(1)
	
	public static List<String> commonChars(String[] A) {
	     
		int[] dict = new int[26];

		// 1. update dict with first string's char count
        for(char c : A[0].toCharArray()) {
            dict[c -'a']++;    
        }
        
        /* 2. for rest of the strings in the array first create the char count and 
         * then compare the count with dict for each index and update the dict with the min count.
         * 
         *  Idea is: At the end dict will have count for each char common in all the strings
         */
        for(int i=1; i<A.length; i++) {
            int[] charArr = new int[26];
            for(char c : A[i].toCharArray()) {
                charArr[c - 'a']++;
            }
            for(int j=0; j<26; j++) {
                dict[j] = Math.min(charArr[j], dict[j]);
            }
        }
        
        List<String> res = new ArrayList<>();
        for(int i=0; i<26; i++) {
            int k = dict[i];
            while(k-- > 0) {
                res.add("" + (char)(i + 'a'));
                
            }
        }
        
        return res;
    }

}
