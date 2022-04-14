package interview;

public class BlackRock2 {
	
	public static void main (String args[]) {
		
		String msg = "hello";
		String encodeStr = encode(msg);
		System.out.println("encode :: "+ encodeStr);
		System.out.println("decode :: "+ decode(encodeStr));
	}
	
	private static String decode(String text) {
		
		//StringBuilder b = new StringBuilder(text);
		//text = b.reverse().toString();
		int k = text.length()-1;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			char res = (char)((c-(k-i))/2);
			result.append(res);
		}
		return result.reverse().toString();
	}
	
	private static String encode(String text) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			b.append(c += c + i);
		}
		return b.reverse().toString();
	}
	
}
