package Flexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flexport2 {

	public static void main(String[] args) {
	    String digits = "12";
	    digitsHelper("123");
	    System.out.println(possibleCombinations(digits));
	}
	
	private static Map<String, String> phone = new HashMap() {
	    {
	      put("1", "ab");
	      put("2", "cd");
	      put("3", "ef");
	      put("12", "gh");
	      put("23", "ij");
	      put("123", "kl");
	    }
	  }; 
	  
	  
	  private static List<String> res = new ArrayList<>();
	  private static List<String> digitList = new ArrayList<>();
	  
	  public static List<String> possibleCombinations(String digits) {
	  
	      if(digits != null && digits.length() != 0) {
	        //helper(digits, "", 0);
	      }
	    
	      for(String digit : digitList) {
	        helper(digit, "", 0);  // when multple digits, i.e. 123=> 1,2,3,12,23,123 
	      }
	    
	      return res;
	  }
	  
	  public static void helper(String digits, String combination, int idx) {
	    
	    if(idx >= digits.length()) {
	      res.add(combination);
	      return;
	    }
	    
	    
	    String letters = phone.get(""+digits.charAt(idx));
	    
	    for(int i=0; i<letters.length(); i++) {
	      String curr = ""+letters.charAt(i);
	      
	      helper(digits, combination + curr, idx+1);
	    }
	  
	  }
	  
	  // 123 => 1, 2, 3, 12, 23, 123
	  
	  private static void digitsHelper(String d) {
	    
	      for(int i=0; i<d.length(); i++) {
	        for(int j=i+1; j<=d.length(); j++) {
	          
	          digitList.add(d.substring(i, j));
	        }
	        
	      }
	    //System.out.println("digitList>> "+digitList);
	    
	  }

}

/*
 * Given a mapping of digits to lists of letters, 
 
 write a function that takes as input a string of numbers, and returns an enumeration of possible combinations. 
 
 For example: if 1 maps to ‘a’ or ‘b’ and 2 maps to ‘c’ or ‘d’ then the input string ‘12’ would return the list of strings [‘ac’, ‘ad’, ‘bc’, ‘bd’].
 
 {
   1: [a, b],
   2: [c, d]
 }
 
 input: '12'
 ouptut: [‘ac’, ‘ad’, ‘bc’, ‘bd’].

 
 Imagine now that the mapping dictionary is arbitrarily large (10,000+ keys) and keys are allowed to be arbitrarily long (10 or 20 digits long). 
 For example for a mapping where 1 maps to ‘a’ or ‘b’ and 11 maps to ‘x’ the input ‘11’ would return [‘aa’, ‘ab’, ‘bb’, ‘ba’, ‘x’]. 
 
 {
   1: [a, b],
   11: [x]
   111: [y],
 }
 
 input: '11'
 output: [‘aa’, ‘ab’, ‘bb’, ‘ba’, ‘x’]
 -----
 
 1234
 
 */

