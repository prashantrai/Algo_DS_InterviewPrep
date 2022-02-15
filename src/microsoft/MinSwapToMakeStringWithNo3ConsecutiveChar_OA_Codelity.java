package microsoft;

public class MinSwapToMakeStringWithNo3ConsecutiveChar_OA_Codelity {

	public static void main(String[] args) {
		
		
		System.out.println("Expected: 1, Actual: " + minSwap("aaaba"));
		System.out.println("Expected: 2, Actual: " + minSwap("aaabbbaa"));
		
		System.out.println("Expected: 1, Actual: " + minSwap("baaaaa"));
		System.out.println("Expected: 2, Actual: " + minSwap("baaabbaabbba"));
		System.out.println("Expected: 0, Actual: " + minSwap("baabab"));

	}
	
	/*
	 * Given a string consisting of only a and b, what is the minimum number of swaps
		(replace a with b or b with a) needed such that string doesn't contain 3 consecutive character 
		which are same. 
		
		e.g aaaba -> 1 (aabba)
		
		
		aaabbbaa -> aabbbbaa -> aabbabaa
		
		https://molchevskyi.medium.com/microsoft-interview-tasks-min-moves-to-make-string-without-3-identical-consecutive-letters-abe61ed51a10
		https://github.com/jolly-fellow/microsoft/blob/master/min_moves_to_make_string_without_3_identical_consecutive_letters/main.cpp	
	 * */
	
	
	/*
	 * https://github.com/jolly-fellow/microsoft/blob/master/min_moves_to_make_string_without_3_identical_consecutive_letters/main.cpp	
	 * 
	 To solve this task we need to find sequences of the same letters and if the sequence is longer than 3
	 divide length of this sequence to 3 and add result of the division to counters of needed swaps
	 Example of work:
		3 consecutive : baaab, replace the middle a (3 / 3 == 1)
		4 consecutive : baaaab, replace the third a (4 / 3 == 1)
		5 consecutive : baaaaab, replace the third a (5 / 3 == 1)
		6 consecutive : baaaaaab -> baabaaab -> baaab -> bab with 2 replancement (6 / 3 == 2)
		10 consecutive : baaaaaaaaaab -> baabaaaaaaab -> baaaaaaab -> baaaab -> baab with 3 replacement (10 / 3 == 3)
		therefore, we can see the rule, counter += (consecutive char number) / 3
	 */
	
	private static int minSwap(String s){
	    int res = 0; 
	    int s_size = s.length();
	    char[] sArr = s.toCharArray();
	    
	    for(int i = 0; i < s_size;) {
	        int next = i + 1;
	        // if we meet sequence of the same letters
	        // scanning the string to find length of this sequence
	        while((next < s_size) && (sArr[i] == sArr[next]) ) {
	        	next++; 
	        }
	        // Here "next - i" is length of the sequence
	        // Each third letter should be changed to remove too long sequences
	        res += (next - i) / 3;
	        i = next; // skip processed letters
	    }
	    return res;
	}
	

	
	
}
