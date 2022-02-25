package leetcode;

import java.util.LinkedList;

public class RemoveKDigits_402_Medium {

	public static void main(String[] args) {

		String num = "1432219"; int k = 3;
		System.out.println("Expected: 1219, Actual: " + (removeKdigits(num, k)));
		
		num = "10200"; k=1;
		System.out.println("Expected: 200, Actual: " + (removeKdigits(num, k)));
		
		num = "1234567890"; k=9;
		System.out.println("Expected: 0, Actual: " + (removeKdigits(num, k)));
		
		num = "10"; k=2;
		System.out.println("Expected: 0, Actual: " + (removeKdigits(num, k)));
		
	}

	// Time & Space : O(N)
	public static String removeKdigits(String num, int k) {
	     
        LinkedList<Character> stk = new LinkedList<>();
        
        for(char digit : num.toCharArray()) {
            while(stk.size() > 0 && k > 0 && stk.peekLast() > digit) {
                stk.removeLast();
                k--;
            }
            stk.addLast(digit);
        }
        
        // remove the remo=aning digit from the stk
        while(k > 0) stk.removeLast();
        
        // build the final string
        StringBuilder sb = new StringBuilder();
        boolean isLeadingZero = true;
        for(char digit : stk) {
        	if(digit == '0' && isLeadingZero) 
                continue;
            isLeadingZero = false;
            sb.append(digit);
        }
        
        return sb.length() == 0 ? "0" : sb.toString();
    }
	
}
