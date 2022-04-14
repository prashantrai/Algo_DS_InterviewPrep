package interview;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intuit_OnSite {
	
	public static void main(String[] args) {
	    String s = "It is a very sunny day, wouldn't you say.";
	    Demo d = new Demo(s);
	    List<String> res = d.sortByLen(3);
	    System.out.println(res);
	    
	    res = d.sortByLen(2);
	    System.out.println(res);

	    
	}

}

/*
 * 0.1 Create a class that takes a String of words separated by spaces as a
 * constructor argument. The class should Index each word in the string by the
 * length of the word. The class should provide one public method that takes an
 * integer argument that returns the words in the string of specified length in
 * alphabetical order.
 * 
 * 
 * Example: "It is a very sunny day, wouldn't you say."
 * 
 * wordsOfLength(3) -> day say you 
 * wordsOfLength(2) -> is It
 * 
 * {2:[]}
 */

class Demo {

	private String s;
	Map<Integer, List<String>> map;

	public Demo(String s) {
		this.s = s;
		map = new HashMap<>();
		removeSpecialChars();
		buildAndReturmMapStoredByWordLength();
	}

	public List<String> sortByLen(int len) {
		List<String> res = new ArrayList<>();
		if (map.containsKey(len)) {
			res = map.get(len);
			Collections.sort(res);
		}
		return res;
	}
	
	// split the string my space and then store in map length of word as key and value is list of all words with same length 
	private Map<Integer, List<String>> buildAndReturmMapStoredByWordLength() {
		String[] arr = s.split(" ");
		for (String str : arr) {
			int key = str.length();
			if (!map.containsKey(key)) {
				List<String> list = new ArrayList<>();
				map.put(key, list);
			}
			List<String> l = map.get(key);
			l.add(str);
			map.put(key, l);
		}
		
		return map;
	}
	
	private String removeSpecialChars() {
		char[] chArr = s.toCharArray();
		for (int i = 0; i < chArr.length; i++) {
			char c = chArr[i];
			if (!Character.isAlphabetic(c)) {
				// s.charAt(i) = '';
				chArr[i] = ' ';
			}
		}
		s = new String(chArr);
		
		return s;
	}

}
