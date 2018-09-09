package test.practice.ebay;

import java.util.HashSet;
import java.util.Set;

public class ParensDemo {

	public static void main(String[] args) {
		
		System.out.println(generateParens(3));
//		System.out.println(generateParens_2(3));

	}

	private static Set<String> generateParens(int remaining) {
		
		Set<String> res  = new HashSet<String>();
		if (remaining == 0) {
			res.add("");
			
		} else {
			Set<String> set = generateParens(remaining - 1);
			for(String s : set) {
				for(int i=0; i< s.length(); i++) {
					if(s.charAt(i) == '(') {
						res.add(insertInside(s, i));
					}
				}
				res.add("()" + s);
			}
			
		}
		
		return res;
	}

 	private static String insertInside(String s, int i) {
 		String before = s.substring(0, i+1);
 		String after  = s.substring(i+1, s.length());
 		return before + "()" + after;
 		
 	}
	
}
