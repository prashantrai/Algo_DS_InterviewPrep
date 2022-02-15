package microsoft;

public class CheckIfTwoConvertedStringAreSame_OA_Codelity {

	public static void main(String[] args) {

		// test unzip
		System.out.println(unzip("2pLe"));
		System.out.println(unzip("Ap2e"));
		System.out.println(unzip("Ap10e"));
		
		
		System.out.println("Expected: true, Actual: " + isSameWord("2pLe", "Ap2e"));
		System.out.println("Expected: false, Actual: " + isSameWord("b1d", "1aD"));
	}
	
	/* You are reading strings from OCR, the machine transcription tool, that reads images of documents 
		and returns strings. If OCR can not recognize some characters, it will replace with '?' 
		
		example: word "AppLe" if OCR could not recognize the first two characters, it will return "??pLe"
		
		Once OCR returns its strings, the strings are minimized further, for every consecutive '?' of length K, 
		the '?''s are replaced by the Integer value of K.
		
		example: "??pLe" becomes "2pLe" because there were 2 consecutive '?' at the start so they are replaced by the number 2
		
		You are given two strings, S and T of lengths m and n, outputted from OCR, determine if the two 
		strings could potentially be the same word. 
		
		example "2pLe" and "Ap2e" could be the same word, return True
		
		"b1d" and 1aD" cannot be the same word because the last characters 'd' and 'D' are not the same, return False
		
		Constraints: the original strings in the images are only english letters
	*/
	
	// working solution
	private static boolean isSameWord(String S, String T) {
		
		if(S == null || T == null || S.isEmpty() || T.isEmpty() || S.length() != T.length())
			return false;
		
		String s2 = unzip(S);
		String t2  = unzip(T);
		
		if(s2.length() != t2.length()) return false;
		
		for(int i=0; i<s2.length(); i++) {
			char ch1 = s2.charAt(i);
			char ch2 = t2.charAt(i);
			
			if(ch1 != '?' && ch2 != '?' && ch1 != ch2) return false;
			
		}
		return true;
		
	}
	
	//unzip the word
	private static String unzip(String s) {
		StringBuilder sb = new StringBuilder();
		int num = 0;
		
		for(int i=0; i<s.length(); i++) {
			char ch = s.charAt(i);
			if(Character.isDigit(ch)) {
				num = num*10 + Character.getNumericValue(ch);
			} else {
				while(num > 0) {
					sb.append('?');
					num--;
				}
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}

}
