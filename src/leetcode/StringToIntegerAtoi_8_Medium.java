package leetcode;

public class StringToIntegerAtoi_8_Medium {

	public static void main(String[] args) {
		int res = myAtoi("42");
		System.out.println(res);

	}

	// --https://leetcode.com/problems/string-to-integer-atoi/
	// --https://leetcode.com/problems/string-to-integer-atoi/discuss/4643/Java-Solution-with-4-steps-explanations
	public static int myAtoi(String str) {
		int index = 0;
		int sign = 1;
		int total = 0;

		// 1. Empty string
		if (str.length() == 0)
			return 0;

		// 2. Remove Spaces
		while (index < str.length() && str.charAt(index) == ' ') {
			index++;
		}

		// 3. Handle signs
		if (index < str.length() && (str.charAt(index) == '-' || str.charAt(index) == '+')) {
			sign = str.charAt(index) == '+' ? 1 : -1;
			index++;
		}

		// 4. Convert number and avoid overflow
		while (index < str.length()) {
			int digit = str.charAt(index) - '0';

			if (digit < 0 || digit > 9)
				break;

			// https://stackoverflow.com/questions/56468515/java-integer-overflow-and-why-integer-max-value-10

			/*
			 * why Integer.MAX_VALUE/10? Because we are multiplying by 10 so before we
			 * multiply we should check that if we devide MAX_VAL by 10 and if the result is
			 * less than total then that means integer overflow will occur in multiplication
			 * step
			 */
			if (Integer.MAX_VALUE / 10 < total || Integer.MAX_VALUE / 10 == total && Integer.MAX_VALUE % 10 < digit) {

			//if((Integer.MAX_VALUE-digit)/10<total) ---this also works	
				
				return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}

			total = total * 10 + digit;
			index++;
		}
		return total * sign;

	}
}
