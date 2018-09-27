package com.interview.questions;

import java.util.Scanner;

public class Workday {

	final static String N = "N";
    final static String S = "S";
    final static String E = "E";
    final static String W = "W";
	
	public static void main(String[] args) { 
	
	Scanner in = new Scanner(System.in);
    String[] tokens = in.nextLine().split(" ");
    
    //--for testing only - remove once done
    for (int i=0; i<tokens.length; i++) {
       // System.out.println(tokens[i]);
    }
    /* Enter your code here. */
    helper(tokens);}
	
	public static void helper(String[] a) {
        int x=0;
        int y=0;
        String curDir = N; 
        
        //if(isNum[0])
        //y = Integer.parseInt(a[0]);
        
        
        for (int i=0; i<a.length; i++) {
            
            String s = a[i];
            System.out.println(s);
            boolean isNum = isNumeric(s);
            
            //-- 5 r r 5
            if(isNum) {
                
                if(N.equals(curDir)) {
                     y = y + Integer.parseInt(s);
                } else if(S.equals(curDir)) {
                    y = y - Integer.parseInt(s);
                } else if(E.equals(curDir)) {
                    x = x + Integer.parseInt(s);
                } else if(W.equals(curDir)) {
                    x = x - Integer.parseInt(s);
                }
                
            } else {
               // System.out.println("old Direction = "+curDir);
                curDir = getDirection(s, curDir);          //--s here is new direction e.g. 'r' or 'l'      
                //System.out.println("new Direction = "+curDir);
                
            }
            
        }
        System.out.println(x+", "+y);
        //System.out.println("x = "+x);
        //System.out.println("y = "+y);
    }
    
    public static String getDirection(String newDirec, String curDirec) {
        
        //--NORTH
        if("r".equalsIgnoreCase(newDirec) && N.equals(curDirec)) {
            curDirec = E;
        }
        else if("l".equalsIgnoreCase(newDirec) && N.equals(curDirec)) {
            curDirec = W;
        }
        //--SOUTH
        else if("r".equalsIgnoreCase(newDirec) && S.equals(curDirec)) {
            curDirec = W;
        }
        else if("l".equalsIgnoreCase(newDirec) && S.equals(curDirec)) {
            curDirec = E;
        }
        //--EAST
        else if("r".equalsIgnoreCase(newDirec) && E.equals(curDirec)) {
            curDirec = S;
        }
        else if("l".equalsIgnoreCase(newDirec) && E.equals(curDirec)) {
            curDirec = N;
        }
        //--WEST
        else if("r".equalsIgnoreCase(newDirec) && W.equals(curDirec)) {
            curDirec = N;
        }
        else if("l".equalsIgnoreCase(newDirec) && W.equals(curDirec)) {
            curDirec = S;
        }
        return curDirec;
    }
    public static boolean isNumeric (String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
