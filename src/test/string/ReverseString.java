package test.string;

public class ReverseString {

	public static void main(String[] args) {
	
//		String result = reverse("prashant");
//		System.out.println("==> "+result);
		
		String stmt = reverseStmt("recursively reverse this statement");
		System.out.println("==> "+stmt);
		
		
	}
	
	//--Recursive
	public static String reverse(String s) {
		
		String result = "";
		
		if(s.length()<=1) {
			return s;
		}
		
		System.out.println("Input="+s);
		result = s.charAt(s.length()-1) + reverse( s.substring(0, s.length()-1) );
		
		return result;
	}
	
	
	// Space: O(N), because of recursive calls
	public static String reverseStmt(String stmt) {
		
		System.out.println("stmt=> "+stmt);
		
		//--recursively reverse this statement
		if(stmt.length() <= 1 || stmt.indexOf(" ") <1) {
			return stmt;
		}
		
		return  reverseStmt(stmt.substring(stmt.indexOf(" ")+1, stmt.length()))
				+ " "
				+ stmt.substring(0, stmt.indexOf(" ")); 
		
	}
	

}
