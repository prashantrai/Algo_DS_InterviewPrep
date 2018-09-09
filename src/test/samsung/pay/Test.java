package test.samsung.pay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		int n = 8754365;
		getLargestNum(n);
	}
	
	public static void getLargestNum(int n) {
		
		String s = ""+n;
		String[] sArr = s.split("");
		Arrays.sort(sArr, Collections.reverseOrder());
		System.out.println(Arrays.toString(sArr));
		
		char[] c = s.toCharArray();
		System.out.println(Arrays.toString(c));
		
	} 

}



//-----
