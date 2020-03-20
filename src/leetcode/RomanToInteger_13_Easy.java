package leetcode;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger_13_Easy {

	public static void main(String[] args) {
		
		System.out.println(romanToInt("III"));
		System.out.println(romanToInt("IV"));
		System.out.println(romanToInt("CCC"));

	}
	
	//--https://leetcode.com/problems/roman-to-integer/submissions/
	public static int romanToInt(String s) {
        int result = 0;
        for(int i=0; i<s.length(); i++) {
            
            char c = s.charAt(i);
            
            if(i< s.length()-1 && getIntValue(c) < getIntValue(s.charAt(i+1))) {
                result -= getIntValue(c);
            } else {
                result += getIntValue(c);
            }
        }
        return result;
    }
    
    public static int getIntValue(char c) {
        
        Map<Character, Integer> map = new HashMap<>(); 
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        
        return map.get(c);
    }

}
