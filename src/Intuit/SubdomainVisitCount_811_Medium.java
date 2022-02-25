package Intuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubdomainVisitCount_811_Medium {

	public static void main(String[] args) {
		
		String[] cpdomains = {"9001 discuss.leetcode.com"};

		System.out.println("Expected: [9001 leetcode.com,9001 discuss.leetcode.com, 9001 com]");
		System.out.println("Actual: " + subdomainVisits(cpdomains));
	}
	
	/*
    Reference: https://leetcode.com/problems/subdomain-visit-count/discuss/121738/C%2B%2BJavaPython-Easy-Understood-Solution
    
    Time and Space: O(N)
    
    */
    public static List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> map = new HashMap<>();
        
        for(String cpdomain : cpdomains) {
            
            int i = cpdomain.indexOf(" "); // 9001 discuss.leetcode.com
            int count = Integer.valueOf(cpdomain.substring(0,i));
            String domain = cpdomain.substring(i+1);
            
            map.put(domain, map.getOrDefault(domain, 0) + count);
            
            for(int j=0; j<domain.length(); j++) {
                if(domain.charAt(j) == '.') {
                    String d = domain.substring(j+1); 
                    map.put(d, map.getOrDefault(d, 0) + count);
                }
            }
        }
        
        List<String> res = new ArrayList<>();
        for(String key : map.keySet()) {
            res.add(map.get(key) + " " + key);
        }
        return res;
    }

}
