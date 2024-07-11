package Facebook;

public class ValidNumber_65_Hard {

	public static void main(String[] args) {

		String s = "0";
		System.out.println("Expected: true, Actual: " + isNumber(s));
		
		s = "e";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
		s = ".";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
		
		// Valid
		s = "3e+7";
		System.out.println("Expected: true, Actual: " + isNumber(s));
		
		s = "-90E3";
		System.out.println("Expected: true, Actual: " + isNumber(s));
		
		s = "-123.456e789";
		System.out.println("Expected: true, Actual: " + isNumber(s));
		
		
		// Invalid
		s = "1e";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
		s = "e3";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
		s = "99e2.5";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
		s = "--6";
		System.out.println("Expected: false, Actual: " + isNumber(s));
		
	}
	
	/*
    Time: O(n)
    Space: O(1)
    */
    
    public static boolean isNumber(String s) {
        boolean seenDigit = false;
        boolean seenExponent = false;
        boolean seenDot = false;
        
        for(int i=0; i<s.length(); i++) {
            char curr = s.charAt(i);
            if(Character.isDigit(curr)) {
                seenDigit = true;
            }
            else if(curr == '+' || curr == '-') {
                /*if sign is present and it's not the first char then
                check if there is exponent available, if not then return false
                because sign can only be at the first position or after an
                exponent*/
                if(i>0 && s.charAt(i-1) != 'e' && s.charAt(i-1) != 'E') {
                    return false;
                }
            }
            else if(curr == 'e' || curr == 'E') {
                // Exponent should be only once 
                // and must be after a digit
                if(seenExponent || !seenDigit) {
                    return false;
                }
                seenExponent = true;
                seenDigit = false; // to start cosidering digit after e ro E
            }
            else if(curr == '.') {
                // if dot has seen before 
                // or if dot is after exponenent, is invalid
                if(seenDot || seenExponent) {
                    return false;
                }
                seenDot = true;
            }
            else 
                return false;
        }
        
        return seenDigit;
    }

}
