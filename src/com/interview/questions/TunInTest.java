package com.interview.questions;

import java.util.Arrays;
import java.util.HashMap;

public class TunInTest {
	
	/* Given a string, such as "Bookkeepers are cool", 
	 * count the number of individual occurrences of a repeated letter.  
	 * A repeated run of characters of any length should COUNT ONLY ONCE towards the total. 
	 * Ignore case and only consider alphabetic characters (i.e. ignore all spaces and punctuation in the input string) 
	 * 
	 * Example: 
	 *  "Bookkeepers are cool" = 4
		"woooow" = 1
		"Programming is so Ordinary" = 3
		"aa.aa" = 1
	 * 
	 * */
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "Bookkeepers are cool";  //4
		s = "woooow";  //1
		s= "Programming's @@ $$ ## %%%% 111 ___ ** is so Ordinary"; //3
		s= "aa.aa"; //1
		s = "докттторд дтор";
		
		
//		System.out.println(">> "+ countRepetition(s));
		System.out.println("2>> "+ countRepetition_2(s));
		System.out.println("3>> "+ countRepetition_3(s));
		
		System.out.println(">>>Unicode ::  "+Character.codePointAt("Ж".toLowerCase(), 0));
		
		//--to remove punctuation and spaces from string
		String m = "Madam, I'm'',.,.''   Adam";
		m = m.toLowerCase().replaceAll("\\W", "");
		System.out.println("m: "+m);
		
		String mystr= "How's your day going?";
		mystr = mystr.replaceAll("[^A-Za-z]+", "").toUpperCase();
		System.out.println("mystr ="+ mystr);
		
	}
	

	
	public static int countRepetition_3(String target) {
		
		if(target == null) return 0;
		
		target = target.toLowerCase();
		
		boolean isCyrillic = isCyrillic(target);
		
		System.out.println(">> isCyrillic: "+isCyrillic);
		
		if(!isCyrillic) {

			/**  https://stackoverflow.com/questions/1576789/in-regex-what-does-w-mean
			 * 	1. The "\w" means "any word character" which usually means alphanumeric (letters, numbers, regardless of case) plus underscore (_)
				2. The "^" "anchors" to the beginning of a string, and the "$" "anchors" To the end of a string, which means that, in this case, 
				   the match must start at the beginning of a string and end at the end of the string.
				3. The [] means a character class, which means "match any character contained in the character class".*/
			
			//--both statement works
//			target = target.toLowerCase().replaceAll("\\W", ""); //replaceAll is O(n) : to replace all punctuation and space with EMPTY string
			target = target.replaceAll("[^a-z]", ""); //replacing all punctuation and space with empty String 
		} else {
			target = target.replaceAll("\\s", "");
		}
		
		char[] chArr = target.toCharArray();
		
		int count = 0;
		int tempCount = 1;
		for(int i=1; i<chArr.length; i++) {
			
			if(chArr[i] == chArr[i-1]) {
				tempCount++;
				continue;
				
			} else if(tempCount > 1) {
				count = count +1;
				tempCount = 1; //reset;
			}
			
		}
		//--if repetition till end of string
		if(tempCount > 1) {
			count = count +1;
		}
		
		return count;
	}
	
	//--Cyrillic :: https://stackoverflow.com/questions/35660688/how-to-detect-in-java-if-string-contains-cyrillic
	public static boolean isCyrillic(String s) {
		
		for(int i = 0; i < s.length(); i++) {
			if(Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
		        return true;
		    }
		}

		return false;
		//Pattern.matches(".*\\p{InCyrillic}.*", text)
		
	}
	
	public static int countRepetition_2(String target) {
		
		target = target.toLowerCase().replaceAll("\\W", ""); //replaceAll is O(n)
		
		char[] chArr = target.toCharArray();
		
		int count = 0;
		int tempCount = 1;
		for(int i=1; i<chArr.length; i++) {
			
			if((chArr[i] >= 97 && chArr[i] <= 122) ||
					(chArr[i] >= 97 && chArr[i] <= 122))
			
			if(chArr[i] == chArr[i-1]) {
				tempCount++;
				continue;
				
			} else if(tempCount > 1) {
				count = count +1;
				tempCount = 1; //reset;
			}
			
		}
		
		
		return count;
	}
	
	public static int countRepetition(String s) {
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		s = s.toLowerCase();
		
		for(int i=0; i< s.length(); i++) {
			
			char key = s.charAt(i);
			
			//--For A-Z and a-z skipping other special chars
//			if((key >= 65 && key <= 90) || (key >= 97 && key <= 122)) { 
			if((key >= 97 && key <= 122)) {  //we dont; need to look for upper case as we converted string to lower case 
			
				Integer count = map.get(key);
				
				if(count == null) {
					count = 0;
				} 
				map.put(key, count+1);
				
				/*if(map.containsKey(key) ) {
					 int count = map.get(key);
					 count++;
					 map.put(key, count);
				} else {
					map.put(key, 1);
				}*/
			}
		}
		
		
		System.out.println(map);
		int count = 0;
		for(int i : map.values()) {
			if(i > 1) {
				count++;
			}
		}
		return count;
		
	}
	
	
	//--ooww 
		/*public static int countRepetition_2(String target) {
			
			target = target.toLowerCase();
			char[] c = target.toCharArray();
			Arrays.sort(c); 
			System.out.println(Arrays.toString(c));
			
			int count = 0;
			int tempCount = 0;
			for(int i=1; i<c.length; i++) {
				if(c[i-1] == c[i]) {
					tempCount++;
				} else if(tempCount >= 1){
					tempCount = 0;
					count++;
				}
			}
			System.out.println("+++ "+count);
			return count;
		}*/
	
}
