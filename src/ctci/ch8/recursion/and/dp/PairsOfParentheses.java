package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PairsOfParentheses {

	/*
	Input: 3
	Output: (( () ) ) , ( () () ) , ( () ) () , () ( () ) , () () ()
	*/
	
	public static void main(String[] args) {
		
		//System.out.println(generateParens(3));
		System.out.println(generateParens_2(3));

	}
	
	public static ArrayList<String> generateParens_2(int count) {
		
		char[] s = new char[count*2];
		ArrayList<String> list = new ArrayList<String>();
		addParens(s, list, count, count, 0);
		
		return list;
	}
	
	private static void addParens(char[] s, ArrayList<String> list, int leftParen, int rightParen, int index) {

		if(leftParen < 0 || rightParen < leftParen) {  //--syntax error
			return;
		}
		
		if(leftParen == 0 && rightParen == 0) {
			list.add(String.valueOf(s));
		} 
		else {
			s[index] = '(';
			System.out.print("[a1]: ");
			printArray(s);
			addParens(s, list, leftParen-1, rightParen, index+1);
			System.out.print("[a2]: ");
			printArray(s);
			
			s[index] = ')';
			System.out.print("[b1]: ");
			printArray(s);
			addParens(s, list, leftParen, rightParen-1, index+1);
			System.out.print("[b2]: ");
			printArray(s);
		}
		
	}

	
	
	public static Set<String> generateParens(int remaining) {
		
		Set<String> set = new HashSet<String>();
		
		if(remaining == 0) {
			set.add("");
		}
		else {
			Set<String> parensSet = generateParens(remaining-1);
			
			for (String str : parensSet) {
				for(int i=0; i<str.length(); i++) {
					
					if(str.charAt(i) == '(') {
						String s = insertInside(str, i);
						set.add(s);
					}
				}
				set.add("()"+str);
			}
		}
		
		return set;
	}

	private static String insertInside(String s, int i) {

		String before = s.substring(0, i+1);
		String after = s.substring(i+1, s.length());
		
		return before + "()" + after;
		
	}
	
	public static void printArray(char[] arr) {
		System.out.print("[");
		for (char ch : arr) {
			System.out.print(ch+", ");
		}
		System.out.print("]\n");
	}

}
