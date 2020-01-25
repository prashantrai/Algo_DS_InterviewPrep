package com.interview.questions;

import java.util.ArrayList;
import java.util.List;

public class Sysdig {

	
	/*
	 * To execute Java, please define "static void main" on a class
	 * named Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */


	/*
	 * Sysdig Java Interview:
	 * Given a Long number N, write a function that calculates the highest number lower than N obtained by permute only one digit of N.
	 * If no permutation exists to return a lower value, return N.
	 * Example:
	 * N = 153
	 * permuteToPrevious(N) = 135

	 * N = 12345
	 * permuteToPrevious(N) = 12345
	 *
	 * Please note:
	 *   Use Long as type. Digits can repeat
	 */
	
	static final List<Long> exampleData = new ArrayList<>();

    static {
        exampleData.add(153L);
        exampleData.add(12345L);
        exampleData.add(92145112L);
        exampleData.add(54321L);
        exampleData.add(295468L);
        /*
         * Example output for this data:

          135
          12345
          92142115
          54312
          294568
          
          
          153 % 10 = 3
          153 /10 = 15
          15 % 10 = 5
          15/10 = 1
          
          rest = ((1*10) + 3 ) * 10 +5 = 135
          
          531
          
          res = 5
          3*10 + result = 35
          res = 35
          1*10 =10
          
          
         */
    }

	public static void main(final String[] args) {
        for (final Long input : exampleData) {
            System.out.println(permuteToPrevious(input));
        }
    }

    private static Long permuteToPrevious(Long input) {
        
      if (input == 0 || input == 1) return input;
      
      ArrayList<Long> nums = new ArrayList<>();
      long val = input;
      while (val > 10) {
        nums.add(val%10);
        val = val/10;       
        
      }
      nums.add(val);
      
      //Collections.sort(nums);
      for(int i=1; i< nums.size(); i++) {
        if(nums.get(i-1) < nums.get(i)) {
          
          long temp = nums.get(i-1);
          nums.set(i-1, nums.get(i));
          nums.set(i, temp);
          break;
        }
        
      }
      
      long result = 0;
      
     
      for(int i=nums.size()-1; i >= 0; i--) { 
       
         result = result*10 + nums.get(i);
        
        
      } 
      
      return result;
    }
}



