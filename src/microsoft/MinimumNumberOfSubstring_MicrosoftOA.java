package microsoft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinimumNumberOfSubstring_MicrosoftOA {

	public static void main(String[] args) {
		
		String s = "world"; //[world]
		System.out.println("Expected: 1, Actual: " + minimalSubstrings(s) + "\n");
		
		s = "dddd"; // [d,d,d,d]
		System.out.println("Expected: 4, Actual: " + minimalSubstrings(s) + "\n");
		
		s = "cycle"; // [cy, cle]
		System.out.println("Expected: 2, Actual: " + minimalSubstrings(s) + "\n");
		
		s = "abba"; //[ab, ba]
		System.out.println("Expected: 2, Actual: " + minimalSubstrings(s) + "\n");  
	}
	
	/*
	 * You are given a string consisting of lower case letters of the English alphabet. 
	 * You must split this string into a minimal number of substring in such a way that no 
	 * letter occurs more than one in each substring. Write a function that give a String S of length
	 * N, returns the minimum number of substrings into which the string has to be split.  
	 * 
	 * Example: Here are some correct splits for "abacdec": [a,bac,dec], [a,bacd,ec] and [ab,ac,dec]  
	 * */
	
	public static int minimalSubstrings(String s) {
		
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		
		int start = 0;
		for(int i=1; i<s.length(); i++) {
			char c1 = s.charAt(i-1);
			char c2 = s.charAt(i);
			if(c1 == c2 || set.contains(""+c2)) {
				String temp = s.substring(start, i);
				start = i;
				list.add(temp);
				set.clear();
				continue;
			}
			set.add(""+c1);
			set.add(""+c2);
		}
		
		list.add(s.substring(start));
		System.out.println("Substrings: " + list);
		
		return list.size();
	}

}
