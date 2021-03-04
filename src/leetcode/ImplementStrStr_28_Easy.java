package leetcode;

public class ImplementStrStr_28_Easy {

	public static void main(String[] args) {
		String str = "abcxabcdabcdabcy"; //16char => 15-pattern_lenght => 15-8 = 7+1
        String subString = "abcdabcy";
		
        //int idx = strStr(str, subString);
        
        //System.out.println("1. Result:: "+idx);
        
        str = "hello";
        subString = "ll";
        
        int idx = strStr(str, subString);
        System.out.println("2. Result:: "+idx);
	}

	/* https://leetcode.com/problems/implement-strstr/
	 * 
	 * 
	 * https://www.youtube.com/watch?v=GTJr8OvyEVQ
	 * https://github.com/mission-peace/interview/blob/master/src/com/interview/string/SubstringSearch.java
	 * 
	 * https://www.youtube.com/watch?v=PcYtBG29Dz4
	 * https://www.programcreek.com/2012/12/leetcode-implement-strstr-java/
	 * 
	 * https://leetcode.com/problems/implement-strstr/discuss/535326/Java-KMP-Solution-O(m%2Bn)-Clean-code
	 * */
	
	public static int strStr(String haystack, String needle) {
		return KMP(haystack, needle);
	}
	
	// KMP algorithm
	/**
	 * Complexity:
	 * Time: O(m+n), where m is needle.length, n is haystack.length
	 * Space: O(m) 
	 */
	public static int KMP(String text, String pattern) {
		int[] pi = computePrefix(pattern);

		int i=0, j=0;
		int m = text.length();
		int n = pattern.length();
		
		while(i<m && j<n) {
			if(text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else {
				if(j != 0) {
					j = pi[j-1];
				} else {
					i++;
				}
			}
		}
		
		if(j == pattern.length()) {
			return i - n;
		}
		return -1;
	}
	
	public static int[] computePrefix(String pattern) {
		int[] pi = new int[pattern.length()];
		
		int j = 0;
		char[] p = pattern.toCharArray();
		
		for(int i=1; i<p.length;) {
			if(p[i] != p[j]) {
				if(j != 0) {
					j = pi[j-1];
					
				} else {
					pi[i] = 0;
					i++;
				}
			}
			else { //i.e. p[i] == p[j]  //when match
				pi[i] = j + 1;
				i++;
				j++;
			}
			
		}
		return pi;
	}
	
	// Time: O(m*n) - Brute Force
    public int strStr_2(String haystack, String needle) {
        if(needle.length() == 0) return 0;
        
        int m = haystack.length();
        int n = needle.length();
        
        for(int i=0; i<=m-n; i++) {
            
            int j;
            for(j=0; j<n; j++) {
                if(haystack.charAt(i+j) != needle.charAt(j)) 
                    break;
            }
            if(j == n) {
                return i;
            }
        }
        
        return -1;
    }
}
