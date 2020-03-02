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
        
        //String result = addStrings2("3.14", "0.9");
        String result = addStrings3("3.14", "0.9");
        System.out.println("1. Actual: "+getExpectedFloat("3.14", "0.9") +", result="+result);
        
        result = addStrings2("9.14", "2.9");
        System.out.println("2. Actual: "+getExpectedFloat("9.14", "2.9") +", result="+result);
        
        result = addStrings3("9.14", "12.9");
        System.out.println("3. Actual: "+getExpectedFloat("9.14", "12.9") +", result="+result);
        
        result = addStrings3("19.14", "12.9");
        System.out.println("4. Actual: "+getExpectedFloat("19.14", "12.9") + ", result="+result);
        
        result = addStrings3("1190.14", "000001.900");
        System.out.println("5. Actual: "+getExpectedFloat("1190.14", "000001.900") + ", result="+result);
        
        result = addStrings3("10.14", "1");
        System.out.println("6. Actual: "+getExpectedFloat("10.14", "1") + ", result="+result);
        
        result = addStrings3("10.14", "0");
        System.out.println("7. Actual: "+getExpectedFloat("10.14", "0") + ", result="+result);
 
        result = addStrings3("0", "0");
        System.out.println("8. Actual: "+getExpectedFloat("0", "0") + ", result="+result);
        
        String result2 = addStrings3("914023", "2998761234");
        System.out.println("Expected: "+getExpected("914023", "2998761234") + ", Actual: "+result2); 
        
        result2 = addStrings3("91402323424598354324345", "29345345598761234");
        String expected = getExpected("91402323424598354324345", "29345345598761234");
        System.out.println("Expected: "+expected + ", Actual: "+result2); 
        
    }
    
    
    //-Working for all the cases
    public static String addStrings3(String str1, String str2) {
		
    	StringBuilder res = new StringBuilder();
    	
    	if(str1.indexOf(".") < 0) {
    		str1 = str1+".0";
    	}
    	if(str2.indexOf(".") < 0) {
    		str2 = str2+".0";
    	}
    	
    	List<String> list = getPaddedStrList(str1, str2);
    	str1 = list.get(0);
    	str2 = list.get(1);
    	
    	int carry = 0;
    	
    	for(int i=str1.length()-1; i>=0; i--) {
    		
    		if(str1.charAt(i) == '.') {
    			res.append(str1.charAt(i));
    			continue;
    		}
    		int n1 = ((int)str1.charAt(i) - '0');
    		int n2 = ((int)str2.charAt(i) - '0');
    		
    		int sum = n1 + n2 + carry;
    		res.append(sum % 10);
    		carry = sum/10;
    	}
    	
    	if(carry > 0) {
    		res.append(carry);
    	}
    	
    	return removeZeros(res.reverse().toString());
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
	
	//--Working
	public static List<String> getPaddedStrList(String str1, String str2) {
		
		List<String> list = new ArrayList<>();
		int s1_left_len  = 0;
		int s1_right_len = 0;
		int s2_left_len  = 0;
		int s2_right_len = 0;
		
		if(str1.indexOf(".") != -1) {
			s1_left_len = (str1.substring(0, str1.indexOf("."))).length();
			s1_right_len = (str1.substring(str1.indexOf(".")+1, str1.length())).length();
		}
    	
		if(str2.indexOf(".") != -1) {
			s2_left_len = (str2.substring(0, str2.indexOf("."))).length();
			s2_right_len = (str2.substring(str2.indexOf(".")+1, str2.length())).length();
		}
    	
    	//--pad left side
    	String str1_padded_left = "";
    	String str2_padded_left = "";
    	int left_delta = Math.abs(s1_left_len - s2_left_len);
    	if(left_delta > 0) {
    		if(s1_left_len < s2_left_len) {
    			str1_padded_left = getPaddedStr(str1.substring(0, str1.indexOf(".")), left_delta, true);
    			str2_padded_left = str2.substring(0, str2.indexOf("."));
    		
    		} else {
    			str2_padded_left = getPaddedStr(str2.substring(0, str2.indexOf(".")), left_delta, true);
    			str1_padded_left = str1.substring(0, str1.indexOf("."));
    		}
    		
    	} else {
    		str1_padded_left = str1.substring(0, str1.indexOf("."));
    		str2_padded_left = str2.substring(0, str2.indexOf("."));
    	}
    	
    	//--pad right side
    	String str1_padded_right = "";
    	String str2_padded_right = "";
    	int right_delta = Math.abs(s1_right_len - s2_right_len);
    	if(right_delta > 0) {
    		if(s1_right_len < s2_right_len) {
    			str1_padded_right = getPaddedStr(str1.substring(str1.indexOf(".")+1, str1.length()), right_delta, false);
    			str2_padded_right = str2.substring(str2.indexOf(".")+1, str2.length());
    		
    		} else {
    			str2_padded_right = getPaddedStr(str2.substring(str2.indexOf(".")+1, str2.length()), right_delta, false);
    			str1_padded_right = str1.substring(str1.indexOf(".")+1, str1.length());
    		}
    		
    	} else {
    		str1_padded_right = str1.substring(str1.indexOf(".")+1, str1.length());
    		str2_padded_right = str2.substring(str2.indexOf(".")+1, str2.length());
    	}
    	
    	list.add(str1_padded_left + "." +str1_padded_right);
    	list.add(str2_padded_left + "." + str2_padded_right);
    	
		return list;
	}
	
	public static String getPaddedStr(String str, int delta, boolean padLeft) {
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<delta; i++) {
			sb.append("0");
		}
		if(!padLeft) {
			sb.insert(0, str);
		} else {
			sb.append(str);
		}

		return sb.toString();
	}
	
	public static String removeZeros(String str) {
		int i = 0;
		int j = str.length()-1;
		while (i < str.length()) {
			if(str.charAt(i) == '0') {
				i++;
				continue;
			} else {
				break;
			}
		}
		
		while (j >= 0 ) {
			if(str.charAt(j) == '0') {
				j--;
				continue;
			} else {
				break;
			}
		}
		
		String s = str.substring(i, j+1);
		
		if(s.length() == 1 && s.equals("."))
			s = "0";
		if(s.charAt(s.length()-1) == '.') {
			s = s.substring(0, s.length()-1);
		}
		
		return s;
		
	}
	
	//--for testing only
	public static String getExpected(String s1, String s2) {
		BigInteger b1 = new BigInteger(s1);
		BigInteger b2 = new BigInteger(s2);
		
		return b1.add(b2).toString();
	}
	
	//--for testing only
	public static String getExpectedFloat(String s1, String s2) {
		float n1 = Float.parseFloat(s1);
		float n2 = Float.parseFloat(s2);
		return ""+(n1 + n2);
	}
	
}
