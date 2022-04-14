package interview;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Marqeta {

	public static void main(String[] args) throws Exception {
        
        String input = "The quick brown fox jumped over the lazy brown dog's back";
        String[] output = analyze(input);
        
        // The quick brown fox jumped over the lazy brown dog's back
        System.out.println("Result: "+ Arrays.toString(output));
        
    }
    // Working as expected
    public static String[] analyze(String s) {
        
        if(s == null || s.isEmpty()) return null;
        
        Map<String, Integer> map = getFreqMap(s);
        List<String> list = sortByLengthThenLexographicalOrder(map.keySet());
        
        String[] res = new String[map.size()];
        int i = 0;
        for(String key : list) {
        	res[i++] = map.get(key) + " " + key;
        }
        
        return res;
    }
    
    public static List<String> sortByLengthThenLexographicalOrder(Set<String> set) {
    	List<String> list = new ArrayList<>(set);
    	Collections.sort(list, new Comparator<String>() {
    		@Override
    		public int compare(String s1, String s2) {
    			if(s1.length() != s2.length()) {
    				return s1.length() - s2.length();
    			} else {
    				return s1.compareTo(s2);
    			}
    		}
    	});
    	
    	return list;
    }
    
    private static Map<String, Integer> getFreqMap(String s) {
       Map<String, Integer> map = new HashMap<>();
       String[] sArr = s.split(" ");
        for(String str : sArr) {
            if(!map.containsKey(str)) {
                map.put(str, 1);
            } else {
                map.put(str, map.get(str)+1);
            }
        }
        return map; 
    } 
}


/*
 * Develop a text analyzer that processes an input `String` (sentence,
 * paragraph, etc) and outputs a report which shows the number of times
 * each word appears in the `String`. The report is sorted by a primary
 * order of word length, and a secondary order based upon the natural
 * lexical ordering of the word.
 *
 * Input:
 *   The quick brown fox jumped over the lazy brown dog's back
 *
 * Output:
 *   1 The
 *   1 fox
 *   1 the
 *   1 back
 *   1 lazy
 *   1 over
 *   2 brown
 *   1 dog’s
 *   1 quick
 *   1 jumped
 */
