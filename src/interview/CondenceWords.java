package interview;

import java.util.LinkedList;
import java.util.List;

import sun.text.resources.es.CollationData_es;

public class CondenceWords {

	//https://www.youtube.com/watch?v=bK0o-8GMRss
	
	public static void main(String[] args) {
		
		String s1 = "hello";
		String s2 = "";
		System.out.println("=>>"+ s1.compareTo(s2));
		
		System.out.println(">>"+condenceWords("Live", "verses"));
		//no one ever run so often
		List<String> list = new LinkedList<String>();
		list.add("Live");
		list.add("verses");
		list.add("are");
		list.add("cool");
		
		for(int i=1; i<list.size(); i++) {
			String s = condenceWords(list.get(i-1), list.get(i));
			if(s != null) {
				list.remove(i-1);
				list.add(i-1, s);
				String temp = list.remove(i);
				System.out.println("removed ele: "+temp);
			}
		}
		
		System.out.println(">>result:: "+list);
	}
	
	public static String condenceWords(String left, String right) {
		
		for(int i=0; i<left.length(); i++) {
			
			String leftSubstring  = left.substring(i);
			System.out.println("leftSubstring = "+leftSubstring);
			
			if(right.startsWith(leftSubstring)) {
				
//				System.out.println(">>>>"+ right.substring(leftSubstring.length()));
				//return leftSubstring;
				return left+right.substring(leftSubstring.length());
			}
			
		}
		return null;
	}

}
