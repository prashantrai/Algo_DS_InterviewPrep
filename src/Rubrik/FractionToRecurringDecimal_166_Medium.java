package Rubrik;

import java.util.HashMap;
import java.util.Map;

public class FractionToRecurringDecimal_166_Medium {

	public static void main(String[] args) {

		int numerator = 2, denominator = 3;
		//System.out.println(fractionToDecimal(numerator, denominator));
		
		numerator = -1; denominator = -2147483648;
		System.out.println(fractionToDecimal(numerator, denominator));
		
	}
	
	/*
    https://leetcode.com/problems/fraction-to-recurring-decimal/discuss/51106/My-clean-Java-solution
    https://leetcode.com/problems/fraction-to-recurring-decimal/discuss/51140/Short-Java-solution
    
    https://leetcode.com/problems/fraction-to-recurring-decimal/submissions/
    
    
    Time and space : O(n)
    */

	public static String fractionToDecimal(int numerator, int denominator) {
        
        if(numerator == 0) 
        	return "0";
        
        StringBuilder result = new StringBuilder();
        
        // XOR takes two boolean operands and returns true if and only if the operands are different
        String sign = (numerator < 0 ^ denominator < 0) ? "-" : "";
        
        /* below are also another way for sign
        int sign = (numerator < 0 == denominator < 0 || numerator == 0) ? "" : "-";
        int sign = (numerator < 0 == denominator < 0) ? "" : "-"; //--coz numerator == 0 is already handeled in base condition
        
        int sign = (numerator < 0 && denominator < 0 || numerator == 0) ? "" : "-";
        
        String sign = (Math.signum(numerator) * Math.signum(denominator) < 0) ? "-" : "";
        */
        
        long nume = Math.abs((long) numerator);
        long denom = Math.abs((long) denominator);

        result.append(sign);
        result.append(nume/denom);
            
        long remainder = nume%denom;
        if(remainder == 0) 
            return result.toString();
        
        result.append(".");
        
        Map<Long, Integer> map = new HashMap<>();
        boolean flag = false; // if ramainer is ZERO and else never executed in that case "()" will not be appended in result
        int decimal_idx = result.length(); // if ramainer is ZERO and else never executed in that case "()" will not be appended in result
        
        while(remainder != 0) {
            if(!map.containsKey(remainder)) {
                map.put(remainder, result.length());
            } else {
                int idx = map.get(remainder);
                result.insert(idx, "(");
                result.append(")");
                flag = true;
                break;
            }
            remainder = remainder*10;
            result.append(remainder/denom);
            remainder = remainder%denom;
        }
        
        // if ramainer is ZERO and else never executed in that case "()" will not be appended in result
        if(!flag) {
        	result.insert(decimal_idx, "(");
            result.append(")");
        }
        
        return result.toString();
        
    }
	
}
