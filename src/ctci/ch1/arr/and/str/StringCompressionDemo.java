package ctci.ch1.arr.and.str;

public class StringCompressionDemo {

	public static void main(String[] args) {

		String s = "";
//		s = "aabcccccaaa";
		s = "aab";
		//String result = getCompressedStr(s);
		String result = getCompressedStr_Sol2(s);
		
		System.out.println("Input="+s +", Output="+result);
		
	}

//	aabcccccaaa
	private static String getCompressedStr_Sol2(String s) {
		
		
		int countConsecutive = countCompression(s);
		System.out.println("countConsecutive = " + countConsecutive);
		
		if(countConsecutive > s.length())
			return s;
		
		StringBuilder compressedStr = new StringBuilder();
		int count = 0;
		
		for(int i=0; i<s.length(); i++) {
			
			count++;
			
			if(i+1 == s.length() || s.charAt(i) != s.charAt(i+1)) {
				compressedStr.append(s.charAt(i));
				compressedStr.append(count);
				count=0;
			}
			
		}
		
		return compressedStr.length() > s.length() ? s : compressedStr.toString();
		
	}
	
	private static int countCompression(String s) {
		
		int countConsecutive = 0;
		int compressedLenght = 0;
		
		for(int i=0; i<s.length(); i++) {
			
			countConsecutive++;
			
			if(i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)) {
				
				
				System.out.println(">> String.valueOf(countConsecutive)="+String.valueOf(countConsecutive));
				System.out.println(">> String.valueOf(countConsecutive).length()="+String.valueOf(countConsecutive).length());
				
				System.out.println("1. compressedLenght = "+compressedLenght);
				compressedLenght += 1+ String.valueOf(countConsecutive).length();
				
				System.out.println("2. compressedLenght = "+compressedLenght);
				countConsecutive = 0;
			}
		}
		
		return compressedLenght;
		
	}
	
	
	
	//aabcccccaaa
	private static String getCompressedStr(String s) {

		int count = 1;

		StringBuilder resultBuilder = new StringBuilder();
		int i = 0;

		while(i < s.length()-1) {
			
			if(s.charAt(i) == s.charAt(i+1)) {
				count++;

			} else {
				resultBuilder.append(s.charAt(i));
				resultBuilder.append(count);
				count = 1;
				
			}
			i++;
		}
		
		resultBuilder.append(s.charAt(i));
		resultBuilder.append(String.valueOf(count));
		
		
		if(s.length() < resultBuilder.length()) {
			return s;
		}
			
		return resultBuilder.toString();
	}

	
	
}
