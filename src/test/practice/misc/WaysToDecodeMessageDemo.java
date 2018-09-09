package test.practice.misc;

public class WaysToDecodeMessageDemo {

	//--https://www.youtube.com/watch?v=qli-JCrSwuk
	
	public static void main(String[] args) {

		char[] arr = new char[26+1];
		
		for(int i=0; i<arr.length; i++) {
			arr[i+1] = (char) (97 + i);
		}
		
		String data = "12345";
		int res = numWays(arr, data);
		
	}
	
	public static int numWays(char[] arr, Stirng data) {
		
		return helper(arr, data);
		
	}
	
	public static int helper (char[] arr, String data, int k) {
		
		if(k == 0) return 1;
		
		if()
	}

}
