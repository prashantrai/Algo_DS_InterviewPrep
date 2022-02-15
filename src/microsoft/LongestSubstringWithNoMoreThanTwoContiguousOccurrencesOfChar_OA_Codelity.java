package microsoft;

public class LongestSubstringWithNoMoreThanTwoContiguousOccurrencesOfChar_OA_Codelity {

	public static void main(String[] args) {

		System.out.println("Expected: aabbaa, Actual: "+ longestValidString("aabbaaaaabb"));
		System.out.println("Expected: aabbaabbaabbaa, Actual: "+ longestValidString("aabbaabbaabbaa"));
		
		System.out.println("Expected: aabbaa, Actual: "+ longestValidSubString("aabbaaaaabb"));
		System.out.println("Expected: aabbaabbaabbaa, Actual: "+ longestValidSubString("aabbaabbaabbaa"));
		
		
		/* String Without 3 Identical Consecutive Letters */
		System.out.println("\n\n****** String Without 3 Identical Consecutive Letters ******");
		
		System.out.println("Expected: eedaad, Actual: "+ filterString("eedaaad"));
		System.out.println("Expected: xxtxx, Actual: "+ filterString("xxxtxxx"));
		System.out.println("Expected: uuxaaxum, Actual: "+ filterString("uuuuxaaaaxum"));
		
	}

	/*
	 * Given string str containing only a and b, find the longest substring of str such that 
	 * str does not contain more than two contiguous occurrences of a and b.
	 * 
	 * https://molchevskyi.medium.com/best-solutions-for-microsoft-interview-tasks-5a738a6f8ba9
	 * https://github.com/jolly-fellow/microsoft/tree/master/longest_substring_without_2_contiguous_occurrences_of_letter
	 * 
	 * Given a string s containing only a and b, find longest substring of s such that s does not contain more than two contiguous occurrences of a and b.

		Example 1:
		Input: "aabbaaaaabb"
		Output: "aabbaa"

		Example 2:
		Input:  "aabbaabbaabbaa"
		Output: "aabbaabbaabbaa"
		
	 * */
	
	
	/*public static String longestValidSubStringWith2ContiguousOccurrences(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s.charAt(0));
		sb.append(s.charAt(1));
		
	}*/
	
	public static String longestValidSubString(String s) {
		if(s == null || s.length() == 0)
	        return "";
		
		int start = 0;
		int end = 0;
		int count = 1; // length of current processing character
		
		char[] sArr = s.toCharArray();
		for(int i=1; i<s.length(); i++) {
			
			if(sArr[i] == sArr[i-1]) {
				count++;
			} else {
				count = 1;
			}
			
			if(count > 2) {
				end = i;
				break;
			} else {
				end = i;
			}
		}
		
		return s.substring(start, end+1);
	}
	
	public static String longestValidString(String str) {

	    if(str == null || str.length() == 0)
	        return "";
	    
	    int left = 0;
	    int right = 0;
	    
	    int start = 0;
	    int end = 0;
	    
	    int count_a = 0;
	    int count_b = 0;
	    
	    while(right < str.length())
	    {
	        char ch = str.charAt(right);
	        
	        if(ch == 'a' && count_a < 2)
	        {
	            count_a++;
	            count_b = 0;
	            // System.out.println(left + " : " + right + ", " + start + " : " + end +  ", " + count_a + " : " + count_b);
	            right++;
	        }
	        
	        else if(ch == 'b' && count_b < 2)
	        {
	            count_b++;
	            count_a = 0;
	            right++;
	        }
	    
	        else
	        {
	            if(end - start < right - left)
	            {
	                start = left;
	                end = right;
	            }
	            
	            left = right - 1;
	            right++;
	        }
	    }
	    
	    if(end - start < right - left)
	    {
	        start = left;
	        end = right;
	    }
	    
	    return str.substring(start, end);
	    
	}
	
	
	/* https://algo.monster/problems/string_without_3_identical_consecutive_letters
	 * 
	 * Microsoft Online Assessment (OA) - String Without 3 Identical Consecutive Letters
	 * 
	 * 
	 * 
	 * */
	public static String filterString(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        sb.append(s.charAt(1));
        for (int i = 2; i < s.length(); ++i) {
            if (s.charAt(i) != s.charAt(i - 1) || s.charAt(i) != s.charAt(i - 2)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
	
}
