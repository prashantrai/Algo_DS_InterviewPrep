package test.practice.yhoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/** Find and group anagrams
input: aba, baa, aab, aabb, zzz, zzcc, cczz, c, c

output: aba, aabb, zzz, zzcc, c
        baa             cczz  c
        aab            
*/


public class TestAnagram {

	public static void main(String[] args) {
		
		String[] arr = {"aba", "baa", "aab", "aabb", "zzz", "zzcc", "cczz", "c", "c" };
		List<List<String>> res = findAnagrams(Arrays.asList(arr));
		
		System.out.println("res: "+res);

	}

	public static List<List<String>> findAnagrams(List<String> input){

		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		Collections.sort(input); //-- this will sort the list
	    //aba, zdccc, aab, c, baa

		for (String s : input) {
				
			char[] c = s.toCharArray();
			Arrays.sort(c);
			String key = new String(c);
		
			if(!map.containsKey(key)) {
			 	map.put(key, new ArrayList<String>());
			} else {
				map.get(key).add(s);		
			}
	
		}
	
		return new ArrayList<List<String>>(map.values());

	} 

}
