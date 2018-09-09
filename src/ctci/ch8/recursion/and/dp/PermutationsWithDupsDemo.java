package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.HashMap;

public class PermutationsWithDupsDemo {

	public static void main(String[] args) {
		
//		String s = "abc";
		String s = "aabb";
		System.out.println(">>Result :: "+printPerm(s));

	}
	
	//Get frequency of each char in map
	public static ArrayList<String> printPerm(String s) {
		ArrayList<String> result = new ArrayList<String>();
		HashMap<Character, Integer> freq = buildFreq(s);
		System.out.println("freq: "+freq);
		
		printPerm("", s.length(), result, freq);
		
		return result;
	}

	private static void printPerm(String prefix, int remaining, ArrayList<String> result, HashMap<Character, Integer> map) {
		
		if(remaining == 0) {
			result.add(prefix);
			return;
			
		}
		
		for (char c : map.keySet()) {
			int count = map.get(c);
			if(count > 0) {
				map.put(c, count-1);
				printPerm(prefix+c, remaining-1, result, map);
				map.put(c, count); //put count back
			}
		}
		
		
	}

	private static HashMap<Character, Integer> buildFreq(String s) {
		
		HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
		
		char[] sArr = s.toCharArray();
		for (char c : sArr) {
			
			if(!freq.containsKey(c)) {
				freq.put(c, 1);
			} else {
				freq.put(c, freq.get(c)+1);
			}
			
		}
		
		return freq;
	}
	
	
	//--calculate perm

}
