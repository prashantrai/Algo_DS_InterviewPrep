package test.practice.atlassian;

import java.util.Scanner;

/**
	Complete the function that takes an integer as its argument   
	and returns the encoded string in base 7 using the following encoding rule: 
	
	base 10 0 1 2 3 4 5 6 
	base 7 0 a t l s i N 
	
	Sample Input: 7 
	
	Sample Output: a0 

 * */

public class EncodeStringDemo {

	public static void main(String[] args) {
		
		/* print decimal ASCII value of a hex
		 * 
		 * int value1 = 0x7e;
		int value2 = new Scanner("7e").nextInt(16);
		System.out.println("value2="+value2);*/
		
		
		char [] b7={'0','a', 't', 'l', 's', 'i', 'n'};
        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
		int temp = i;
		StringBuilder sb = new StringBuilder();
		
		int base=7;
		
		while(i>0) {
			int m = i%7;
			sb.append(b7[m]);
			i=i/7;
		}
		System.out.println(">>Result: "+sb.reverse());
		
       
        in.close();

    }
		
}
