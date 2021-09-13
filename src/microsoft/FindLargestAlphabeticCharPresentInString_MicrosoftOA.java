package microsoft;

public class FindLargestAlphabeticCharPresentInString_MicrosoftOA {

	public static void main(String[] args) {
		String str = "admeDCAB";
        System.out.println("Expected: D, Actual: " + largestCharacter(str));
        
        str = "dAeB";
        System.out.println("Expected: -1, Actual: " + largestCharacter(str));
        
	}

	// https://www.geeksforgeeks.org/find-the-largest-alphabetic-character-present-in-the-string/
	private static String largestCharacter(String str) {
		 
		boolean[] uppercase = new boolean[26];
	    boolean[] lowercase = new boolean[26];
	     
	    for(char c : str.toCharArray()) {
	    	if(Character.isLowerCase(c)) {
	    		lowercase[c - 'a'] = true;
	    		
	    	} else if(Character.isUpperCase(c)) {
	    		uppercase[c - 'A'] = true;
	    	}
	    } 
		
	    for(int i=25; i>=0; i--) {
	    	if(uppercase[i] && lowercase[i]) return ""+(char)('A' + i);
	    }
	    
	    return "-1";
	}

	
}
