package ctci.ch1.arr.and.str;

public class PermutationOfPalindromDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//boolean result = isPermutationOfPalindrom("Tact Coa");
		boolean result = isPermutationofPalindrom_Sol2("Tact Coa");
		
		System.out.println(">> result="+result);
	}
	
	//--Solution: 2
	private static boolean isPermutationofPalindrom_Sol2 (String phrase) {
		char[] chArr = phrase.toCharArray();
		int[] table = new int[(Character.getNumericValue('z') - Character.getNumericValue('a'))+1]; //26
		
		int countOdd = 0;
		for (char ch : chArr) {
			
			int index = getCharNumber(ch);

			if(index != -1) {
				table[index]++;
				
				if(table[index]%2 == 1) {
					countOdd++;
					
				} else {
					countOdd--;
				}
			}
		}
		
		return countOdd <= 1;
	}
	
	
	//--Solution: 1
	private static boolean isPermutationOfPalindrom (String phrase) {
		
		int table[] = buildCharFrequency (phrase.toCharArray());
		return checkMaxOneOdd(table);
	}

	private static boolean checkMaxOneOdd(int[] table) {
		
		boolean foundOdd = false;
		
		for (int count : table) {
			if(count%2 == 1) {
				if(foundOdd) {
					return false;
				}
				foundOdd = true;
			}
		}
		return true;
	}

	private static int[] buildCharFrequency(char[] charArray) {
		
		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		
		int[] table = new int[(z-a)+1];  //--26
		
		for (char c : charArray) {
			
			int index = getCharNumber(c);
			if( index != -1) {
				table[index]++;
			}
		}
		return table;
	}

	private static int getCharNumber(char ch) {

		int a = Character.getNumericValue('a');
		int z = Character.getNumericValue('z');
		int val = Character.getNumericValue(ch);
		
		if( val >= a && val <= z) {
			return val - a ;
		}
		
		return -1;
	}
	
	

}
