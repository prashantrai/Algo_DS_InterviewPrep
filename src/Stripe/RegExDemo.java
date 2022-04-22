package Stripe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExDemo {

	public static void main(String[] args) {

		printMathchingStr(); // "bend", "bending"
	}
	
	/*
	 * For some quick look. Suggestion in this post is only used for regex 
	 * 		https://stackoverflow.com/questions/10520566/regular-expression-wildcard-matching
	 * */

	
	/*
	 	Replace all '?' characters with '\w'
		Replace all '*' characters with '\w*'
		The '*' operator repeats the previous item '.' (any character) 0 or more times.
		
		This assumes that none of the words contain '.', '*', and '?'.
		
		This is a good reference
		
		http://www.regular-expressions.info/reference.html
	 */
	
	public static void printMathchingStr() {

		// Replace all '*' characters with '\w*'
		
		// https://stackoverflow.com/questions/23602045/wild-card-search-in-java-strings
		List<String> list = new ArrayList<String>();
		list.add("Test");
		list.add("Test Test");
		list.add("Test Second");
		//String queryString = "Te*s";
		String queryString = "Te*";
		queryString = queryString.replace("*", "\\w*");
		queryString = queryString.concat(".*"); // .* will match everything else
		for (String str : list) {
			if (str.matches(queryString))
				System.out.println(str);
		}
		
		// Replace all '*' characters with '\w*'
		//2nd use case
		String[] arr = { "ben", "bend", "bending", "been" };
		String searchStr = "ben*";
		searchStr = searchStr.replace("*", "\\w*");
		System.out.println("searchStr: "+searchStr);
		//String searchStr = "ben\\w*";
		
		searchStr = searchStr.concat(".*"); //.* will match everything else
		for (String str : arr) {
			if (str.matches(searchStr))
				System.out.println(str);
		}
		
		// Replace all '*' characters with '\w*'
		//3rd use case
		String[] arr2 = {"en-US", "en-CA", "en-GB", "es-ES", "fr-FR"}; 
		searchStr = "en*";
		searchStr = searchStr.replace("*", "\\w*");
		System.out.println("searchStr: "+searchStr);
		//searchStr = "en\\w*";
		searchStr = searchStr.concat(".*"); //.* will match everything else
		for (String str : arr2) {
			if (str.matches(searchStr))
				System.out.println(str);
		}
		
		// Replace all '?' characters with '\w'
		// Replace all '*' characters with '\w*'
		
		//4th use case
		String[] arr3 = { "ben", "bend", "bending", "been", "bet" };
		searchStr = "b?t*";
		searchStr = searchStr.replace("?", "\\w");
		searchStr = searchStr.replace("*", "\\w*");
		System.out.println("searchStr: "+searchStr);

		searchStr = searchStr.concat(".*"); //.* will match everything else
		for (String str : arr3) {
			if (str.matches(searchStr))
				System.out.println(str);
		}
		
	}


	// print all string matching wildcard expression
	public static void printMathchingStr2(String searchStr) {

		String[] arr = { "ben", "bend", "bending" };

		String regex = "\\w*";
//		String regex = "\\w+";

		// Creating a Pattern object
		Pattern p = Pattern.compile(regex);
		// Creating a Matcher object

		for (String s : arr) {
			Matcher m = p.matcher(s);
			while (m.find()) {
				System.out.println(m.group());
			}
		}
	}

}
