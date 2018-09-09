package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;

public class PermutationsWithoutDupsDemo {

	public static void main(String[] args) {

		System.out.println(">>>"+"abc".substring(0, 0));
		
		String s = "abc";
		//System.out.println(">>Result :: "+getPerms(s));
		System.out.println(">>Result :: "+getPerms_2(s));
		//System.out.println(">>Result :: "+getPerms_3(s));
	}
	
	
	
	//--Approach 3
	public static ArrayList<String> getPerms_3(String remainder) {
		 
		ArrayList<String> result = new ArrayList<String>();
		getPerms_3("", remainder, result);
		
		return result;
	}
	public static void getPerms_3(String prefix, String remainder, ArrayList<String> result) {
		
		if(remainder.length() == 0) {
			result.add(prefix); 
		}
		
		int len = remainder.length();
		for(int i=0; i< len; i++) {
			String before = remainder.substring(0, i);
			String after = remainder.substring(i+1, len);
			char c = remainder.charAt(i);
			
			System.out.println("prefix="+(prefix+c)+", remainder="+(before+after)+", result="+result);
			
			getPerms_3(prefix+c, before+after, result);
		}
	}
	
	
	//--Approach 2
	public static ArrayList<String> getPerms_2(String remainder) {
		
		int len = remainder.length();
		ArrayList<String> result = new ArrayList<String>();
		
		if(len == 0) {
			result.add("");
			return result;
		}
		
		ArrayList<String> partials;
		for(int i=0; i<len; i++) {
			
			//--Removing char i and find permutations of remaining chars
			String before = remainder.substring(0, i);
			String after = remainder.substring(i+1, len);
			
			partials = getPerms_2(before + after); 
			
			for (String word : partials) {
				result.add(remainder.charAt(i) + word);
			}
			
		}
		
		return result;
		
	}
	
	
	
	public static ArrayList<String> getPerms(String str) {
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(str == null) 
			return null;
		
		if(str.length() == 0) {
			result.add("");
			return result;
		}
		
		char start = str.charAt(0);
		String remainder = str.substring(1); 
		
		ArrayList<String> words = getPerms(remainder);
		
		for (String word : words) {
			for(int i=0; i<=word.length(); i++) {
				String perm = insertCharAt(word, start, i);
				result.add(perm);
			}
		}
		
		return result;
	}

	private static String insertCharAt(String word, char c, int i) {

		String start = word.substring(0,i);
		String end = word.substring(i);
		
		return start + c + end;
	}

}
