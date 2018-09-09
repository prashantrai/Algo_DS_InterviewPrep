package test.practice.misc;

import java.util.ArrayList;
import java.util.List;

public class HackingTimeTwitterDemo {

	/*
	 * Twitter interview questions
	 * For question: https://aonecode.com/getArticle/213
	 * 				 https://aonecode.com/getArticle/214
	 * 
	 * */
	
	public static void main(String[] args) {

	
		String msg = "Otjfvknou kskgnl, K mbxg iurtsvcnb ksgq hoz atv. Vje xcxtyqrl vt ujg smewfv vrmcxvtg rwqr ju vhm ytsf elwepuqyez. -Atvt hrqgse, Cnikg";
		
		String sign = msg.substring(msg.lastIndexOf("-")+1, msg.length());
		
		System.out.println(sign);
		System.out.println("A="+(int)('A') + ", Z="+ (int)('Z'));
		System.out.println("a="+(int)('a') + ", z="+ (int)('z'));
		System.out.println("t="+(int)('t') + ", o="+ (int)('o'));
		System.out.println("q="+(int)('q') + ", i="+ (int)('i'));
		
		getSecretKey(sign);
	
	}
	
	public static List<Integer> getSecretKey(String sign) {
		
		String signPlainTxt = "Your friend, Alice";
		List<Integer> key = new ArrayList<Integer>();
		
		for(int i=0; i<signPlainTxt.length(); i++) {
			
			if(sign.charAt(i) == ' ' || !Character.isLetter(sign.charAt(i)) ) continue;
			
			if(sign.charAt(i) >= signPlainTxt.charAt(i) ) {
				
				/*
				 * Y=89
				 * 65+26 = 91 - 89
				 * 
				 * IF new < old
				 * 	key = (new+26)-old
				 * 
				 * t=116  we need 5
				 * 116 - 111 = 5
				 * 
				 * 
				 * IF new > old
				 * 	key = new - old
				 * 
				 * 
				 * Result:  2, 5, 1, 2, 2, 0, 8, 
				 * 			2, 5, 1, 2, 2, 0, 8, 2
				 * 
				 * */
				
				key.add(sign.charAt(i) - signPlainTxt.charAt(i));
				
			} else if (sign.charAt(i) < signPlainTxt.charAt(i)) {
				int v = (sign.charAt(i) + 26) - signPlainTxt.charAt(i); 
				key.add(v);
			} else {
				
			}
		}
		
		System.out.println("key="+key);
		
		return null;
	}
	
	

}
