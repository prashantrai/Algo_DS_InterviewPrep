package ctci.ch1.arr.and.str;

public class RotateStringDemo {

	public static void main(String[] args) {

		// 1,2,3,4,5,6,7 is rotated to 5,6,7,1,2,3,4]
		
		/*1,2,3,4,5,6,7
		5,6,7,1,2,3,4
		
		waterbottle
		aterbottlew
		terbottlewa
		erbottlewat*/
		
		String s1 = "waterbottle";
		String s2 = "erbottlewat";
		
		System.out.println(">>Result: "+isRotation(s1, s2));
		
		
	}
	
	public static boolean isRotation(String s1, String s2) {
		
		int len = s1.length();
		
		if(s2.length() > 0 && s2.length() == len) {
			
			String s1s1 = s1 + s1;
			return isSubstring(s1s1, s2);
			
		}
		
		return false;
		
	}

	private static boolean isSubstring(String s1s1, String s2) {

		if(s1s1.indexOf(s2) != -1)
			return true;
		
		return false;
	}
	

}
