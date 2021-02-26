package Affirm;

import java.util.HashMap;
import java.util.Map;

public class ShortestUniqueSubstring {

	/**
	 * https://leetcode.com/discuss/interview-question/447215/affirm-phone-shortest-unique-substring
	 * 
	 * Given an input list of names, for each name, find the shortest substring that
	 * only appears in that name.
	 * 
	 * Input: ["cheapair", "cheapoair", "peloton", "pelican"] 
	 * 
	 * Output: 
	 * "cheapair": "pa" // every other 1-2 length substring overlaps with cheapoair 
	 * "cheapoair": "po" // "oa" would also be acceptable 
	 * "pelican": "ca" // "li", "ic", or "an" would also be acceptable 
	 * "peloton": "t" // this single letter doesn't occur in any other string
	 * 
	 */

	public static void main(String[] args) {
		String[] sArr = {"cheapair", "cheapoair", "peloton", "pelican"};
		Map<String, String> result = shortestUniqueSubstr(sArr);

        for(String key : result.keySet()) {
            System.out.println(key + ": " + result.get(key));
        }
	}

	
	/* Below is brute force solution but this problem can be solved using suffix tree or suffix array
	 * 
	 */
	public static Map<String, String> shortestUniqueSubstr(String[] strArr) {
        Map<String, String> result = new HashMap<>();

        for(String str : strArr) {
            result.put(str, str);

            for(int i = 0; i < str.length(); i++) {
                for(int j = i + 1; j <= str.length(); j++) {
                    String subStr = str.substring(i, j);
                    boolean addSub = true;
                    for(String str2 : strArr) {
                        if(str2.equals(str)) continue;

                        if(str2.contains(subStr)) {
                            addSub = false;
                            break;
                        }
                    }

                    if(addSub && subStr.length() < result.get(str).length()) {
                        result.put(str, subStr);
                    }
                }
            }
        }

        return result;
    }

	
}
