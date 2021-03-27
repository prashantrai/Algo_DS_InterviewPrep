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


/*
 /*
 * Click `Run` to execute the snippet below!
 */



// Below is a section of a city with roads and stoplights. The letters indicate entry points where we measure traffic flow. Each intersection has a traffic light - We want to build a program to simulate traffic flowing through the city so we can test traffic light control algorithms with sample data.

// For purposes of this exercise, your simulation should operate on 1 minute intervals, where every minute lights (potentially) change and cars drive along roads, with each section of road taking 1 minute to drive. 

//       J    
//       |    
//  A ---🚦---
//       |     
//  B ---🚦---
//       | 



// Example of traffic flow:
// A car enters road J at minute 2
// It takes 1 minute to drive to light A-J
// Once light A-J is green for road J, it takes 1 minute to drive to light B-J
// Once light B-J is green for road J, it takes 1 minute to drive to the exit of the city
// Total travel time would be 3 minutes + number of minutes spent waiting at lights
// Assuming the car hit only green lights, it would exit the city at minute 5



// PART 1
// Let's start by simulating one road - Road J. 

// - First, create a data model to represent the road, lights, and cars driving along it.
// - Next, enter one car into the road and write a function that simulates time in 1 minute intervals, moving the car along the road and stopping it at red lights until they turn green. It should run until the car has exited the city.

// For right now we can use a simple traffic light control: Start with both lights green on the first minute, and then toggle back and forth between red and green each minute after that.

// Your function should return the total time it took the car to travel through the city.

//   J    
//   |    
//  🚦
//   |     
//  🚦
//   | 

 * */
