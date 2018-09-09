package test.string;

public class PalindromString {

	public static void main(String[] args) {
	
		String s = "hannah";
		
		String result = reverse(s);
		
		System.out.println("==> "+result);
		
		isPalindrom(s);
		
	}
	
	public static void isPalindrom(String s) {
		
		if(reverse(s).equalsIgnoreCase(s)) {
			System.out.println("Palindrom");
			return;
		}
		System.out.println("Not Palindrom");
		
	}

	private static String reverse(String string) {
		System.out.println(">> "+string);
		if(string.length()<=1) {
			return string;
		}
		
		return string.charAt(string.length()-1) + reverse(string.substring(0, string.length()-1));
	}
	

}
