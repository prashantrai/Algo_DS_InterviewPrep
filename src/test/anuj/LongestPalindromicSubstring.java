package test.anuj;

import java.util.HashSet;

public class LongestPalindromicSubstring {

	public static void main(String[] args) {

		//String s = "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab";
		
		String s = "babad";
		//s = "b";
		//s = "abcda";
		
//		s="civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		
		int k=1,j=-1;
		System.out.println("%%%% "+ (k-(-1)-1));
		
		long startTime = System.nanoTime(); 

		//System.out.println("Result: "+longestPalindromeString(s));
		System.out.println("Result: "+longestPalindrome3(s));
		//System.out.println(">> ::"+isPalindrom("abbad"));
		//s = "cbbd";
		//System.out.println(">>>>Result::  "+longestPalindrome(s));///654 645 59
																  ///533 024 362

		long elapsedTime = System.nanoTime() - startTime;

		System.out.println("Total Time taken:" + elapsedTime);//304951 -- 302965
		
		
	}
	static int lo, maxLen;
	
	public static String longestPalindrome3(String s) { //--https://leetcode.com/problems/longest-palindromic-substring/#/solutions
		int len = s.length();
		if (len < 2)
			return s;
		
	    for (int i = 0; i < len-1; i++) {
	     	extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
	     	extendPalindrome(s, i, i+1); //assume even length.
	    }
	    return s.substring(lo, lo + maxLen);
	}

	private static void extendPalindrome(String s, int j, int k) {
		
		System.out.println("j="+j+", k="+k +"  s.charAt(j)= "+s.charAt(j)+", s.charAt(k)= "+s.charAt(k));
		
		while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
			j--;
			k++;
		}
		
		System.out.println(">>> j="+j+", k="+k +"  maxLen= "+maxLen+", lo= "+lo);
		
		if (maxLen < k - j - 1) {
			lo = j + 1;
			maxLen = k - j - 1;
		}
		
		System.out.println("**>>> j="+j+", k="+k +"  maxLen= "+maxLen+", lo= "+lo);
	}
	
	
	//310810
	public static String longestPalindrome (String s) {
		
		String result = "";
		//String temp = "";
		StringBuilder temp = new StringBuilder();
		
		if(s.length() == 1) {
			return s;
		}
		
		for(int i=0; i<s.length(); i++) {
			//temp = ""+s.charAt(i);
			temp.setLength(0);
			temp.append(s.charAt(i));

			for(int j=i+1; j<s.length(); j++) {
				
//				temp += ""+s.charAt(j);
				temp.append(s.charAt(j));
				
				if(isPalindrom(temp.toString())) {
					if(temp.length() > result.length()) {
						result = temp.toString();	
					}
					
				}
			}
		}
		return result.length() == 0 ? temp.toString() : result;
	}
	
	public static boolean isPalindrom(String s) {
		
		int len = s.length();
		
		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();
		
		if(len%2 == 0) { //--even lenght
			s1.append(s.substring(0, len/2));
			s2.append(s.substring(len/2, len));
			
		} else { //--odd length
			s1.append(s.substring(0, len/2));
			s2.append(s.substring(len/2+1, len));
		}
		
		return s1.toString().equals(s2.reverse().toString());
	}
	
	

	public static String longestPalindromeString(String s) {
		if (s.length() == 1 || s.length() == 0) {
			return s;
		}

		// Keep track of the maximum here itself in this loop.
		// Initialization complete.
		int maxLength = -1;
		int maxIIndex = -1;
		int maxJIndex = -1;
		HashSet<String> currentlyFoundPalindrome = new HashSet<String>();
//		List<String> currentlyFoundPalindrome = new ArrayList<String>();
		for (int i = 0; i < s.length(); i++) {
			currentlyFoundPalindrome.add(s.substring(i, i + 1));
		}

		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j <= i; j++) {
				if (s.charAt(j) == s.charAt(i)) {
					// check the difference between i and j
					int diff = i - j;
					if (diff == 0) {
						// same index nothing to do
					} else if (diff == 1) {
						// right next to each other.
						String str = s.substring(j, i + 1);
						currentlyFoundPalindrome.add(str);
						if (str != null && maxLength < str.length()) {
							maxIIndex = i;
							maxJIndex = j;
							maxLength = str.length();
						}
					} else if (diff > 1) {
						int indexOfj = j + 1;
						if (currentlyFoundPalindrome.contains(s.substring(indexOfj, i))) {
							String str = s.charAt(i) + s.substring(indexOfj, i) + s.charAt(i);
							if (str != null && maxLength < str.length()) {
								maxIIndex = i;
								maxJIndex = j;
								maxLength = str.length();
							}
							currentlyFoundPalindrome.add(str);
						}
					}
				}
			}
		}
		if (maxLength == -1) {
			return s.substring(0, 1);
		}
		return s.substring(maxJIndex, maxIIndex + 1);
	}

}
