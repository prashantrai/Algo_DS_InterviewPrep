package test.practice.yhoo;

public class PermutationOfPalindromeDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean result = isPermutationOfPalindrom("Tact Coa");
		
		System.out.println(">> result="+result);
	}

	private static boolean isPermutationOfPalindrom(String s) {

		//Get char frequency
		int[] table = getCharFrequency(s);
		
		//--check max one odd
		return hasMaxOneOdd(table);
		
	}

	private static boolean hasMaxOneOdd(int[] table) {

		boolean isOdd = false;
		
		for(int i : table) {
			
			if(i%2 != 0) {
				
				if(isOdd) {
					return false;
				}
				isOdd = true;
			}
		}
		return true;
	}

	private static int[] getCharFrequency(String s) {

		int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a')+1];
		
		for(char c : s.toCharArray()) {
			if(getNumericValue(c) != -1)
				table[getNumericValue(c)]++; 
		}		
				
		return table;
	}
	
	private static int getNumericValue(char c) {
		
		int a  = Character.getNumericValue('a');
		int z  = Character.getNumericValue('z');
		int v  = Character.getNumericValue(c);
		
		if(v >= a && v <= z) {
			return v - a;	
		}
		
		return -1;
	}
	
	
	
	
	

}
