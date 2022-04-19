package Google;

public class MostFrequentCharacterInStringGroupedByCharacters {

	public static void main(String[] args) {
		assert getMostFreqCharInString("hhzzzzaaa") == 'z';
        assert getMostFreqCharInString("hhzzzzaaaaaaa") == 'a';
        assert getMostFreqCharInString("hza") == 'h';
        assert getMostFreqCharInString("hhhhhhhhhhhhhh") == 'h';
        assert getMostFreqCharInString("hhhhaaa") == 'h';
        
        System.out.println("Expected: z, Actual: " + getMostFreqCharInString("hhzzzzaaa"));
        System.out.println("Expected: a, Actual: " + getMostFreqCharInString("hhzzzzaaaaaaa"));
        System.out.println("Expected: h, Actual: " + getMostFreqCharInString("hza"));
        System.out.println("Expected: h, Actual: " + getMostFreqCharInString("hhhhhhhhhhhhhh"));
        System.out.println("Expected: h, Actual: " + getMostFreqCharInString("hhhhaaa"));
	}
	
	
	//Time: O(log N), entire algorithm runs in k * log(n) but k is constant
	
	public static char getMostFreqCharInString(String s) {
		char[] chars = s.toCharArray();
		int n = chars.length;
		
		int maxLen = 1; // track the current max length
		char freqChar = chars[0]; // track the most frequent char
		
		int start = 0;
		
		while(start < n) {
			
			int end = findLastIndexForStart(chars, start, n-1);
			
			// len of same group of char
			int len = end - start + 1;
			if(maxLen < len) {
				maxLen = len;
				freqChar = chars[start];
			}
			start = end+1;	
		}
		
		return freqChar;
	}

	//binary search
	private static int findLastIndexForStart(char[] chars, int start, int end) {

		while(start < end) {
			int mid = (start+end)/2;
			
			if(chars[start] != chars[mid]) {
				end = mid;
			} else {
				start = mid+1;
			}
			
		}
		return start;
	}

}


/*
https://leetcode.com/discuss/interview-question/1761891/Google-or-Phone-Screen-or-Most-frequent-character-in-string-grouped-by-characters/1263735

You are given a string that is grouped together by characters. For example a sample 
input could be: "hhzzzzaaa", and we need to output the most frequently occurring character 
so for our example we would output 'z'.

Solve this in O(Log N) time complexity. Using binary search. 

Brute Force: Time and space: O(n) [Space can be O(1) if we use array of size 26 assuming string has only alphabets] 
	Keep a Character -> Integer map to count the frequency of each character.
	Return the max count character that the end
	Linear time and space
	Disadvantage is that we are not taking into consideration that the characters are grouped together...

Optimization 1:
	Since the characters are kept in groups, we need to find the index where the character changes.
	To get the count of a specific character, we need to subtract i - pivot.
	Linear Time and Constant Space



*/