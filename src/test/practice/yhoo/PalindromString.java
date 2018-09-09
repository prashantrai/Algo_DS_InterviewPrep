package test.practice.yhoo;

public class PalindromString {

	public static void main(String[] args) {
	
		String s = "hannah";
		
		String result = reverse(s);
		
		System.out.println("==> "+result);
		
		isPalindrom(s);
		
		
		System.out.println("isPalindrom_2 => "+isPalindrom_2(s));
		
	}
	
	//--to verify Permutation of a palindrom
	public static boolean isPalindrom_2(String s) {
		
		int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a')+1]; //26
		int countOdd = 0; //--if countOdd is more than 1 return false
		
		for(char c : s.toCharArray()) {
			
			int index = getCharNumber(c);
			
			if(index != -1) { 
				
				table[index]++;
			
				if(table[index] % 2 == 1) 
					countOdd++;
				else 
					countOdd--;
			}
		}
		
		return countOdd <= 1;
	}
	
	public static int getCharNumber(char c) {
		
		int v = Character.getNumericValue(c);
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		
		if(v >= a && v <= z) {
			return v-a;
		}
		
		return -1;
		
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
