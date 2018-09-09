package test.practice.ebay;

import java.util.ArrayList;
import java.util.HashMap;

public class PermutationsWithDupsDemo {

	public static void main(String[] args) {
		
//		String s = "abc";
		String s = "aabb";
		System.out.println(">>Result :: "+printPerm(s));

	}

	private static ArrayList<String> printPerm(String s) {

		if(s==null) return null;
		
		ArrayList<String> result = new ArrayList<String>();
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		buildFreq(map, s);
		
		printPerm(result, map, "", s.length());
		
		return result;
	}

	
	private static void printPerm(ArrayList<String> result, HashMap<Character, Integer> map, String prefix, int remaining) {
		
		if(remaining == 0) {
			result.add(prefix);
			return;
		}
		
		for (char c : map.keySet()) {
			
			int count = map.get(c);
			if(count > 0) {
				map.put(c, count-1);
				printPerm(result, map, prefix+c, remaining-1);
				map.put(c, count); //put count back
			}
		}
	}

	private static void buildFreq(HashMap<Character, Integer> map, String s) {
		
		int count=0;
		for(char c : s.toCharArray()) {
			if(map.containsKey(c)) {
				map.put(c, map.get(c)+1);
			} else {
				map.put(c, count+1);
			}
		}
		
	} 
}
