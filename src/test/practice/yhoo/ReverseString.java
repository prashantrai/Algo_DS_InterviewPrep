package test.practice.yhoo;

public class ReverseString {

	public static void main(String[] args) {
	
//		String result = reverse("prashant");
		String result = reverseStr("prashant");
		System.out.println("==> "+result);
		
//		String stmt = reverseStmt("recursively reverse this statement");
//		System.out.println("==> "+stmt);
		String stmt = reverseStmt_2("recursively reverse this statement");
		System.out.println("==> "+stmt);
		
		
	}
	
	public static String reverseStr(String s) {
		
		if(s.length() == 0) return "";
		
		return reverse(s.substring(1, s.length()))
				+s.charAt(0);
		
	}
	
	public static String reverseStmt_2(String s) {
		
		if(s.length()<=1 || s.indexOf(" ") < 1) return s;
		
		return	reverseStmt_2(s.substring(s.indexOf(" ")+1, s.length()))
				+ " "
				+ s.substring(0, s.indexOf(" "));
	}
	

	public static String reverse(String s) {
		
		if(s.length() == 0) return "";
		
		return reverse(s.substring(1, s.length())) + s.charAt(0);
	} 
	
	public static String reverseStmt(String s) {
		System.out.println(">>> "+s);
		if(s.length() <= 1 || s.indexOf(" ") < 1) return s;
		
		return reverseStmt(s.substring(s.indexOf(" ")+1, s.length())) 
				+" "
				+ s.substring(0, s.indexOf(" "));
		
	}
	
}
