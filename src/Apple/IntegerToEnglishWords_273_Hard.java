package Apple;

public class IntegerToEnglishWords_273_Hard {

	public static void main(String[] args) {

		int num = 123;
		System.out.println("123 : " + numberToWords(num));
		
		num = 12345;
		System.out.println("12345 : " + numberToWords(num));
		
		num = 1234567;
		System.out.println("1234567 : " + numberToWords(num));
		
	}

	
	/* https://algo.monster/liteproblems/273#:~:text=Performance%20and%20Complexity%3A,word%20representation%20of%20the%20number.
	 * 
	 * https://leetcode.com/problems/integer-to-english-words/discuss/3434886/Clean-JAVA-Soln.-oror-Easy-to-Understand-oror-Explanation-oror-9ms-Beats-63-oror-Recursion
	 * 
	 * Complexity:: [not sure if this is correct]
		Time complexity: O(log n)
		where n is the input number. This is because the number of recursive calls 
		made is proportional to the number of digits in the input number, which is log n.
		
		Space complexity: O(log n)
		because at most log n recursive calls are made on the call stack, 
		each of which takes up O(1) space for the arrays and StringBuilder.
	 *
	 * From Algo Master:: 
	 * 
	 *  Performance and Complexity:
		 Time complexity: The algorithm runs in O(n) where n is the number of digits in the 
		 input number since each digit or group of digits is processed once. 
		 
		 The SPACE complexity is also O(n) due to the storage required for the word 
		 representation of the number.
	 * 
	 * */
	
	
	final static String ones[]= {"","One", "Two","Three","Four","Five","Six","Seven","Eight","Nine"};
	final static String tens[]= {"","", "Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
	final static String special[] ={"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};

	
	public static String numberToWords(int num) {
        if (num == 0) return "Zero";
        return solve(num);
    }
	
    private static String solve(int num) {
        StringBuilder result = new StringBuilder();
        
        if(num < 10) result.append(ones[num]);
        else if (num < 20) result.append(special[num-10]);
        else if (num < 100) result.append(tens[num/10] + " " + solve(num % 10));
        else if (num < 1000) result.append(solve(num/100) + " Hundred " + solve(num % 100));
        else if (num < 1000_000) result.append(solve(num/1000) + " Thousand " + solve(num % 1000));
        else if (num < 1000_000_000) result.append(solve(num/1000_000) + " Million " + solve(num % 1000_000));
        else result.append(solve(num/1000_000_000) + " Billion " + solve(num % 1000_000_000));
        
        return result.toString();
    }
}
