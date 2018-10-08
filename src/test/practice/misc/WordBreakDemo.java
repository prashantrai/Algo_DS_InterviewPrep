package test.practice.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreakDemo {

	//--Asked by Amazon during phone screen
	//- URL: https://www.geeksforgeeks.org/word-break-problem-dp-32/
	//--  [This is what was expected to be implemented in Amazon interview] https://www.geeksforgeeks.org/word-break-problem-using-backtracking/
	//--https://www.youtube.com/watch?v=uHtj_ehsMno
	
	public static void main(String[] args) {
		
		String dictionary[] = {"mobile","samsung","sam","sung", 
                "man","mango", "icecream","and", 
                "go","i","love","ice","cream"}; 
		
		//String dictionary[] = {"mobile","samsung","sam","sung", "mango", "and", "i","love","ice","cream"}; 

		Set<String> set = new HashSet<>(Arrays.asList(dictionary));
		
		wordBreak("iloveicecreamandmango", set); 
		  
	    wordBreak("ilovesamsungmobile", set); 
		
	}
	
	
	public static void wordBreak(String s, Set<String> dict) {
		
		StringBuilder result = new StringBuilder();
		
		int length = s.length();
		//wordBreakUtil(s, length, result, dict);
		
		//System.out.println(result);
		//--2nd Sol - Working
		Map<String, List<String>> map = new HashMap<>();
		List<String> res = helper(s, map, dict);
		System.out.println("res:: "+res);
		
		//-3rd sol
		Map<String, List<String>> map2 = new HashMap<>();
		List<String> res2 = helper(s, map, dict);
		System.out.println("res2:: "+res2);
		
	}


	private static void wordBreakUtil(String s, int length, StringBuilder result, Set<String> dict) {

		//int length = s.length();
		for(int i=1; i<=length; i++) {
			
			String prefix = s.substring(0, i);
			
			if(dict.contains(prefix)) {
				if(i == length) { //--reached end of string
					result.append(prefix);
					System.out.println(result);
					result.setLength(0); //--clear buffer
					return;
				}
				result.append(prefix);
				result.append(" ");
				//System.out.println(">>"+ (length-i));
				//System.out.println(">>s.substring("+i+", "+length+"-"+i+")" );
				
				wordBreakUtil(s.substring(i), length-i, result, dict);
			}
			
		}
		
		
	}
	
	//--https://www.youtube.com/watch?v=9B4f4ZR00jM
	public static List<String> helper (String s, Map<String, List<String>> map, Set<String> dict) {
		
		if(map.containsKey(s)) return map.get(s);
		
		List<String> res = new ArrayList<String>();
		if(s.length() == 0) {
			res.add("");
			return res;
		}
		
		for(int i=s.length(); i>=0; i--) {
			String temp = s.substring(i);
			//System.out.println("temp: "+temp);
			if(dict.contains(temp)) {
				List<String> l = helper(s.substring(0, i), map, dict);
				for(String str : l) {
					
					if("".equals(str))
						res.add(temp);
					else 
						res.add(str+" " + temp);
				}
			}
			
		}
		
		map.put(s, res);
		return res;
	}
	
	
	public static List<String> helper2 (String s, Map<String, List<String>> map, Set<String> dict) {
		
		if(map.containsKey(s)) return map.get(s);
		
		List<String> res = new ArrayList<String>();
		
		if(s.isEmpty()) {
			res.add("");
			return res;
		}

		for(int i=1; i<s.length(); i++) {
			String temp = s.substring(0, i);
			
			if(dict.contains(temp)) {
				
				List<String> l = helper2(s.substring(i), map, dict);
				
				for(String str : l) {
					if(str.equals("")) {
						res.add(temp);
					} else {
						res.add(temp + " " + str);
					}
				}
			}
		}
		map.put(s, res);
		return res;
		
	}
 	

}
