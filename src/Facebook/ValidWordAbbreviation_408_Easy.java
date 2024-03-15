package Facebook;

public class ValidWordAbbreviation_408_Easy {

	public static void main(String[] args) {

		String word = "internationalization";
		String abbr = "i12iz4n";
		System.out.println("Expected: true  Actual: " + validWordAbbreviation(word, abbr));
		
		word = "apple"; 
		abbr = "a2e";
		System.out.println("Expected: false  Actual: " + validWordAbbreviation(word, abbr));
		
	}
	
	/*
	 * Time: O(N), N is the length of the abbr. In worst case there could 
	 *             be 1 for a char and that abbr lenght will be same word length.
	 *             
	 * Space: O(1) 
	 * */
	public static boolean validWordAbbreviation(String word, String abbr) {
        int number = 0;
        int i=0;
        int j=0;
        
        while (i<word.length() && j<abbr.length()) {
            if(Character.isDigit(abbr.charAt(j))) {
                number = number*10 + abbr.charAt(j) - '0';
                
                if(number == 0) return false;
                j++;
            } else {
                // skip to char position
                i += number;
                
                // if i is beyond word length 
                // OR char is not same at position i, then return false
                if(i >= word.length() || word.charAt(i) != abbr.charAt(j))
                    return false;
                
                // reset number to start for another position 
                // e.g. in 'i12iz4n' aftter postion z number will be set to 0 from 12
                // which was set bfore for position 1 and 2.
                number = 0;
                i++;
                j++;
            }
        }
        
        // update i by adding current number value and check 
        // if it's same as word length
        i += number; 
        
        return i == word.length() && j == abbr.length(); 
    }

}
