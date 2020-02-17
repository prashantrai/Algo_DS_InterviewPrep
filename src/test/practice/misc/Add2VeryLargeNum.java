package test.practice.misc;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



public class Add2VeryLargeNum {
    public static void main(String[] args) throws IOException {
        
        String result = addStrings2("3.14", "0.9");
        System.out.println("1. result="+result);
        
        result = addStrings2("9.14", "2.9");
        System.out.println("2. result="+result);
        
        String result2 = addStrings2("914023", "2998761234");
        
        System.out.println("Expected: "+getExpected("914023", "2998761234") + ", Actual: "+result2); 
        
        result2 = addStrings2("91402323424598354324345", "29345345598761234");
        String expected = getExpected("91402323424598354324345", "29345345598761234");
        System.out.println("Expected: "+expected + ", Actual: "+result2); 
        
        

    }

    /*
     * Complete the 'addStrings' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING string1
     *  2. STRING string2
     */

	//--working
    public static String addStrings2(String str1, String str2) {
		
    	StringBuilder res = new StringBuilder();
    	
		String small = str1.length() > str2.length() ? str2 : str1;
    	String large = str1.length() > str2.length() ? str1 : str2;
    	
    	int delta = Math.abs(str1.length() - str2.length());
    	
    	small = getPaddedStr(small, delta);
    	
    	int carry = 0;
    	
    	for(int i=small.length()-1; i>=0; i--) {
    		
    		if(small.charAt(i) == '.') {
    			res.append(small.charAt(i));
    			continue;
    		}
    		int n1 = ((int)small.charAt(i) - '0');
    		int n2 = ((int)large.charAt(i) - '0');
    		
    		int sum = n1 + n2 + carry;
    		res.append(sum % 10);
    		carry = sum/10;
    	}
    	
    	if(carry > 0) {
    		res.append(carry);
    	}
    	
    	//System.out.println(">>res:: "+res.reverse());
    	
		return res.reverse().toString();
    	
	}
    
    //--working
    public static String addStrings(String str1, String str2) {
		
		String res = "";
		
    	
    	String small = str1.length() > str2.length() ? str2 : str1;
    	String large = str1.length() > str2.length() ? str1 : str2;
    	
    	int delta = Math.abs(str1.length() - str2.length());
    	
    	String paddedStr = getPaddedStr(small, delta);
    	
    	small = paddedStr;
    	
    	int carry = 0;
    	char[] smalArr  = small.toCharArray();
    	char[] largeArr = large.toCharArray();
    	
    	for(int i=smalArr.length-1; i>=0; i--) {
    		if(smalArr[i] == '.') {
    			res += smalArr[i];
    			continue;
    		}
    		int n1 = ((int)smalArr[i] - '0');
    		int n2 = ((int)largeArr[i] - '0');
    		
    		int sum = n1 + n2 + carry;
    		res += sum % 10;
    		carry = sum/10;
    	}
    	
    	if(carry > 0) {
    		res += carry;
    	}

    	res = new StringBuilder(res).reverse().toString();
    	
    	System.out.println(">>res:: "+res);
    	
		return res;
	}
	
	public static String getPaddedStr(String small, int delta) {
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<delta; i++) {
			sb.append("0");
		}
		if(small.indexOf('.') != -1) {
			sb.insert(0, small);
		} else {
			sb.append(small);
		}
		//System.out.println("getPaddedStr:: "+sb);
		return sb.toString();
	}

	public static String getExpected(String s1, String s2) {
		BigInteger b1 = new BigInteger(s1);
		BigInteger b2 = new BigInteger(s2);
		
		return b1.add(b2).toString();
	}
	
}
