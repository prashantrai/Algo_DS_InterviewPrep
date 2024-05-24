package Grammarly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepeatedDNASequences_187_Medium {

	public static void main(String[] args) {
		String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
		System.out.println("Expected: [AAAAACCCCC, CCCCCAAAAA], Actual: " + findRepeatedDnaSequences(s));
		
		s = "AAAAAAAAAAAAA";
		System.out.println("Expected: [AAAAAAAAAA], Actual: " + findRepeatedDnaSequences(s));
		
	}

	/*
    Time : O(N)
    */
    public static List<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> repeated = new HashSet<>();
        
        for(int i=0; i+9<s.length(); i++) {
            String str = s.substring(i, i+10);
            
            /* Below code works too
            if(seen.contains(str)) {
                repeated.add(str);
            } else {
                seen.add(str);
            }*/
            
            if(!seen.add(str)) {
                repeated.add(str);
            }
        }
        
        return new ArrayList<String>(repeated);
    }
	
}
