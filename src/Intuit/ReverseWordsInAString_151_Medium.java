package Intuit;

public class ReverseWordsInAString_151_Medium {

	public static void main(String[] args) {
		String s1 = "  Bob    Loves  Alice   ";
		String s2 = "a good   example";
		String s3 = "  hello world  ";
		String s4 = "the sky is blue";
		
		System.out.println(reverseWords(s1));
		
	}

	// https://leetcode.com/problems/reverse-words-in-a-string/
	
	// From: https://leetcode.com/problems/reverse-words-in-a-string/discuss/47706/My-accepted-Java-solution
	public static String reverseWords(String s) {
		
		/*
		 * "\s" is a regex class for any kind of whitespace (space, tab, newline, etc). 
		 * Since Java uses "\" as an escape character in strings (e.g. for newlines: "\n"), 
		 * we need to escape the escape character. 
		 * 
		 * So it becomes "\\s". The "+" means one or more of them.
		 * 
		 * Algo:
		 * 1. Remove heading and trailing spaces with trim()
		 * 2. split the string by space (space is delimiter one or more as regex "\s+" )
		 * */
		
		String[] parts = s.trim().split("\\s+");
		String out = "";
		for (int i = parts.length - 1; i > 0; i--) {
		    out += parts[i] + " ";
		}
		
		return out + parts[0];
	}
	
	

}
