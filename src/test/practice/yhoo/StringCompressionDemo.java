package test.practice.yhoo;

public class StringCompressionDemo {

	public static void main(String[] args) {

		String s = "";
		s = "aabcccccaaa";
//		s = "aab";
//		String result = getCompressedStr(s);
		String result = getCompressedStr_2(s);
		
		System.out.println("Input="+s +", Output="+result);
		
	}

	private static String getCompressedStr(String s) {
		
		int countConsecutive = 0;
		
		StringBuilder compressedStr = new StringBuilder();
		
		for(int i=0; i<s.length(); i++) {
			countConsecutive++;
			
			//System.out.println(">>> "+s.charAt(i)+" --- "+countConsecutive);
			
			if(i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)) {
				compressedStr.append(s.charAt(i));
				compressedStr.append(countConsecutive);
				countConsecutive = 0;
			}
			
		}
		
		return compressedStr.length() > s.length() ? s : compressedStr.toString();
	}
	
	
	private static String getCompressedStr_2(String s) {
		
		int compressedLength = getCompressedLength(s);
		
		System.out.println("compressedLength="+compressedLength);
		
		if(compressedLength > s.length())
			return s;
		
		StringBuilder compressedStr = new StringBuilder(compressedLength);
		int countConsecutive = 0;
		
		for(int i=0; i< s.length(); i++) {
			countConsecutive++;
			
			if(i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)) {
				compressedStr.append(s.charAt(i));
				compressedStr.append(countConsecutive);
				countConsecutive = 0;
			}
		}
		
		
		return compressedStr.toString();
	}

	private static int getCompressedLength(String s) {

		int countConsecutive = 0;
		int compressedLength = 0;
		
		for(int i=0; i<s.length(); i++) {
			countConsecutive++;
			
			if(i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)) {
				compressedLength += 1 + String.valueOf(countConsecutive).length(); //--length of count as they could be 2 digits as a part of string a
				countConsecutive = 0;
			}
		}
		return compressedLength;
	}

}
