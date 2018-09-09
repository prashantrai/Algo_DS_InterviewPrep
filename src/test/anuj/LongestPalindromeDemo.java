package test.anuj;

public class LongestPalindromeDemo {

	public static void main(String[] args) {

		// String s =
		// "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab";

		String s = "babad";
		// s = "b";
		// s = "abcda";

		// s="civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";

		long startTime = System.nanoTime();

		System.out.println(">>>>Result::  " + longestPalindrome(s));/// 654 645
																	/// 59
																	/// 533 024
																	/// 362

		long elapsedTime = System.nanoTime() - startTime;

		System.out.println("Total Time taken:" + elapsedTime);// 304951 --
																// 302965

	}

	public static String longestPalindrome(String s) {
		String result = "";
		String temp = "";

		if (s.length() == 1) {
			return s;
		}

		for (int i = 0; i < s.length(); i++) {
			temp = "" + s.charAt(i);

			for (int j = i + 1; j < s.length(); j++) {

				temp += "" + s.charAt(j);

				if (isPalindrom(temp)) {
					if (temp.length() > result.length()) {
						result = temp;
					}

					if (result.length() == s.length()) { // --same as input
															// string
						break;
					}
				}
			}
		}
		return result.length() == 0 ? temp : result;
	}

	public static boolean isPalindrom(String s) {

		int len = s.length();

		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();

		if (len % 2 == 0) { // --even lenght
			s1.append(s.substring(0, len / 2));
			s2.append(s.substring(len / 2, len));

		} else { // --odd length
			s1.append(s.substring(0, len / 2));
			s2.append(s.substring(len / 2 + 1, len));
		}

		return s1.toString().equals(s2.reverse().toString());
	}

}
