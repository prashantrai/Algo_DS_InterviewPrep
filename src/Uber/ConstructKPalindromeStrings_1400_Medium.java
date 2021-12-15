package Uber;

public class ConstructKPalindromeStrings_1400_Medium {

	public static void main(String[] args) {
		String s = "annabelle"; 
		int k = 2;
		System.out.println("Expected: true, Acual: "+ canConstruct(s, k));
		
		s = "leetcode"; k = 3;
		System.out.println("Expected: false, Acual: "+ canConstruct(s, k));
	}
	
	// Reference: https://leetcode.com/problems/construct-k-palindrome-strings/discuss/563379/JavaC%2B%2BPython-Straight-Forward
    //Time: O(N) Space: O(1)
    public static boolean canConstruct(String s, int k) {
      
        int oddCount = 0;
        int[] arr = new int[26];
        
        // count chars and store in array at respective index
        for(char c : s.toCharArray()) {
            arr[c - 'a']++;
        }
        // count odds
        for(int i : arr) {
            if(i % 2 == 1) oddCount++;
        }
        
        return oddCount <= k && k <= s.length();
    }

}
