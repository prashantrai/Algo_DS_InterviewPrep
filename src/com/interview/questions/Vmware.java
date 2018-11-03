package com.interview.questions;

import java.util.Scanner;


/**
 * Photon Controller (Hacker Rank)  - Working Solution
 * 
 * Input 1:  
  3
  3 1 2
  3 1 2 5
  6 3 2
 * 
 * Expected Result: 
	SUCCESS => RECEIVED: 3
	FAILURE => RECEIVED: 4, EXPECTED: 5
	FAILURE => RECEIVED: 3, EXPECTED: 6
 * 
 * 
 * Input 2:
  4
  1 p 3
  4 2
  1 2
  2 1
 * 				
 * Expected Result:
	FAILURE => WRONG INPUT (LINE 2)
	FAILURE => RECEIVED: 2, EXPECTED: 4
	SUCCESS => RECEIVED: 2
	SUCCESS => RECEIVED: 2
 * 
 * 
 * 
 * */

public class Vmware {
    public static void main(String args[] ) throws Exception {
        
        Scanner sc = new Scanner(System.in); 
        String[] input = new String[sc.nextInt()];
        sc.nextLine(); //consuming the <enter> from input above

        for (int i = 0; i < input.length; i++) {
            input[i] = sc.nextLine();
        }

        int countLine = 1;
        for (String s : input) {
            countLine++;
            String[] temp = s.split(" ");
            helper(temp, countLine);
        }
        
    }
    
    public static void helper(String[] list, int countLine) {
        
        boolean isValid = isValidInput(list);
        int size = list.length;
        
        if(!isValid) {
            System.out.println("FAILURE => WRONG INPUT (LINE "+countLine+")");
        }
        else {
            int maxEvents = getMaxEvents(list);

            if(maxEvents == size) { //SUCCESS
                System.out.println("SUCCESS => RECEIVED: "+ size);

            }
            else if(maxEvents != size) {  //--Number of received and expected events differ
                System.out.println("FAILURE => RECEIVED: "+size+", EXPECTED: "+maxEvents);
            }
        }
    }
    
    public static boolean isValidInput(String[] arr) {
        
        for(String s : arr) {
           if(!isNumeric(s)) return false;
            
        }
        return true;
    }
    
    public static int getMaxEvents(String[] arr) {
        
        int max = 0;
        
        for(String s : arr) {
            int v = Integer.parseInt(s);
            max = max < v ? v : max;
        }
        return max;
    }
    public static boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }
    
    
    
}