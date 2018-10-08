package com.interview.questions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Autodesk {

	/*
	Function to implement:
	customSort(String input, String sortOrder)
	Goal: To sort string "input", in the order defined by "sortOrder"

	Example usage:
		customSortString ("abdcdfs", "dacg")
	
	Output: ddacfbs
		Any characters from "input" not present in "sortOrder" can appear in any order at the end of the output string.
	*/
	
	public static void main(String[] args) {

		String input = "abdcdfs";
		String sortOrder = "dacg";
		System.out.println("1. "+ customSort("abdcdfs", "dacg"));
		System.out.println("2. "+ customSort2("abdcdfs", "dacg"));

	}
	
	
	//--Space efficient compare to "customSort()" implementation
	//--Runtime: O(n*c), where n=length of sortOrder str and c=freq of each char in input str, note that in worst case (str has only one char repeating e.g. 'aaaaaaaaa') 'c' can be equal to str length i.e. 'n'
	public static String customSort2(String input, String sortOrder) {
		
		//build freq table for input string
		HashMap<Character, Integer> map = new HashMap<>();
		for(char c : input.toCharArray()) {
			
			if(map.containsKey(c)) {
				map.put(c,	map.get(c)+1);
			} else {
				map.put(c, 1);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(char c : sortOrder.toCharArray()) {
			
			if(map.containsKey(c)) {
				int len = map.get(c);
				for(int i=0; i<len; i++) {
					sb.append(c);
					map.put(c,	map.get(c)-1);
				}
			} 
			
		}
		
		for(char c : map.keySet()) {
			if(map.get(c) != 0) {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	
	//--Taking extra space
	public static String customSort(String input, String sortOrder) {

		Set<Character> set = new HashSet<>();
		Set<Character> set2 = new HashSet<>();
		for (char c : input.toCharArray()) {
			set.add(c);
		}
		for (char c : sortOrder.toCharArray()) {
			set2.add(c);
		}

		//System.out.println("set=" + set);

		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < sortOrder.length(); i++) {
			for (int j = 0; j < input.length(); j++) {
				if (!set.contains(sortOrder.charAt(i)) && !set2.contains(input.charAt(j))) {
					sb2.append(input.charAt(j)); // --char not in
					continue;
				}
				if (input.charAt(j) == sortOrder.charAt(i)) {
					sb.append(input.charAt(j));
					continue;
				}
			}
		}
		sb.append(sb2);
		return sb.toString();

	}
}
