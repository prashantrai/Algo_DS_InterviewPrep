package Ripple;

import java.util.Arrays;

public class StringCompression_443_Medium {

	public static void main(String[] args) {
		
		char[] chars = {'a','a','b','b','c','c','c'};
		// ["a","2","b","2","c","3"]
		System.out.println("Expected: 6, Actual: " + compress(chars)); 
		
		char[] chars2 = {'a'};
		System.out.println("Expected: 1, Actual: " + compress(chars2));
		
		char[] chars3 = {'a','b','b','b','b','b','b','b','b','b','b','b','b'};
		// ["a","b","1","2"]
		System.out.println("Expected: 4, Actual: " + compress(chars3));
		
		

	}
	
	/*
    Reference: https://leetcode.com/problems/string-compression/discuss/92559/Simple-Easy-to-Understand-Java-solution/302657
    
    Time: O(N)
    Space: O(1), in place
    */
    
    public static int compress(char[] chars) {
        
        if(chars == null || chars.length == 0) 
            return 0;
        
        int len = 0;    // also a pointer to modify array in-place
        int count = 1;  //represent the times char appears;
        
        for(int i=1; i<=chars.length; i++) {
            if(i < chars.length && chars[i-1] == chars[i]) {
                count++;
            } else {
                chars[len++] = chars[i-1];  //copy the char
                if(count < 2) continue;     //do nothing if count < 2 and continue;
                String s = String.valueOf(count);
                for(char c : s.toCharArray()) {
                    chars[len++] = c;
                }
                count = 1;//after append, reset to 1
            }
        }
        System.out.println(Arrays.toString(chars));
        return len;
    }

}
