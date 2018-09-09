package ctci.ch1.arr.and.str;


/**
 * Ch01 : Array and String
 * 1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters. 
 * What if you cannot use additional data structures?
 * */

public class IsUniqueDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//boolean flag = isUnique("abcd");
		boolean flag = isUnique_2 ("abcd");
//		boolean flag = isUnique_3 ("abcdc");
		
		System.out.println("Is Unique :: "+flag);
	}
	
	public static boolean isUnique(String s) {
		
		for(int i=0; i<s.length(); i++) {
			
			for(int j=0; j<s.length(); j++) {
				
				if(i==j) continue;
				
				if(s.charAt(i) == s.charAt(j)) 
					return false;
				else continue;
			}
		}
		
		return true;
	} 
	
	//--with additional data structure :: array
	
	public static boolean isUnique_2(String s) {
		
		System.out.println("isUnique_2 invoked...");
		
		boolean[] charSet = new boolean[128];
		
		for(int i=0; i<s.length(); i++) {
			
			int val = s.charAt(i);
			
			if(charSet[val] == true) {  //--already found this char in string
				return false;
			}
			charSet[val] = true;
		}
		return true;
	}
	
	public static boolean isUnique_3(String s) {
		
		int checker = 0;
		
		for(int i=0; i<s.length(); i++) {
			
			int val = s.charAt(i) - 'a';
			
			System.out.println("val :: "+val);
			System.out.println("(1 << val) :: " + (1 << val));
			//System.out.println("(val << 1) :: " + (val << 1));
			System.out.println("checker :: "+checker);
			System.out.println("( checker & ( 1 << val)) :: "+ ( checker & ( 1 << val)));
			
			if( (checker & ( 1 << val)) > 0 ) {
				return false;
			} 
			checker |= (1 << val);
		}
		
		return true;
	}
	
	
}
