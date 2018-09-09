package test.practice.ebay;

import java.util.ArrayList;
import java.util.List;

public class PermutationWithoutDups {

	public static void main(String[] args) {

		System.out.println(">>>"+"abc".substring(0, 0));
		
		String s = "abc";
		System.out.println(">>1. Result :: "+getPerms(s));
		System.out.println(">>2. Result :: "+getPerms_2(s));
//		System.out.println(">>Result :: "+getPerms_3(s));
		System.out.println(">>Result :: "+getPerms_4(s));
	}
	
	public static List<String> getPerms_3(String s) {
		
		if(s == null || s.isEmpty()) return null;
		
		List<String> result = new ArrayList<String>();
		
		getPerms_3(result, "", s);
		
 		return result;
	}
	
	public static void getPerms_3(List<String> result, String prefix, String s) {
		
		if(s.length() == 0) {
			result.add(prefix);
		}
		
		for(int i=0; i<s.length(); i++) {
			
			char c  = s.charAt(i);
			
			String before  = s.substring(0, i);
			String after  = s.substring(i+1);
			
			getPerms_3(result, prefix+c, before+after);
		}
	}

	
	public static ArrayList<String> getPerms_2(String remainder) {
		
		if(remainder == null) return null;
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(remainder.length() == 0)
			result.add("");
		
		for(int i=0; i<remainder.length(); i++) {
			
			char c = remainder.charAt(i);
			String before = remainder.substring(0, i);
			String after = remainder.substring(i+1);
			
			ArrayList<String> partials = getPerms(before+after);
			
			for(String s : partials) {
				result.add(c+s);
			}
			
		}
		return result;
	}
	
	
	public static ArrayList<String> getPerms(String s) {
		if(s== null) return null;
		
		ArrayList<String> result = new ArrayList<String>();
		if (s.length() == 0) {
			result.add("");
			return result;
		}
		
		char first = s.charAt(0); //get first;
		String remainder = s.substring(1); //--remove the first char
		
		ArrayList<String> words = getPerms(remainder);
		
		for(String word : words) {
			for (int j=0; j<=word.length(); j++) {
				String s2 = insertCharAt(word, j, first);
				result.add(s2);
			}
		}
		return result;
	}
	
	public static String insertCharAt(String word, int j, char first) {
		String before  = word.substring(0,j);
		String after = word.substring(j);
		return before + first + after;
	}
	
	
	public static ArrayList<String> getPerms_4 (String s) {
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(s.length() == 0) {
			result.add("");
			return result;
		}
		
		for(int i=0; i<s.length(); i++) {
			String before = s.substring(0, i);
			String after = s.substring(i+1);
			ArrayList<String> partials = getPerms(before + after);
			
			for(String word : partials) {
				
				result.add(s.charAt(i)+word);
				
			}
		}
			
		return result;
	}
	
	
}
