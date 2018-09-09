package test.practice.yhoo;

import java.util.Arrays;

public class URLify {

	public static void main(String[] args) {

		String s = "Mr John Smith    ";  //--true length=13 with extra buffer=17
		int trueLength = s.trim().length();
		
		replaceSpace(s.toCharArray(), trueLength);
		
	}
	
	public static void replaceSpace(char[] str, int trueLength) {
		
		//count space
		int spaceCount = 0;
		for(int i=0; i<trueLength; i++) {
			if(str[i] == ' ') 
				spaceCount++;
		}
		
		int index = trueLength + spaceCount*2;
		
		for(int i=trueLength-1; i>=0; i--) {
			
			if(str[i] == ' ') {
				str[index-1] = '0';
				str[index-2] = '2';
				str[index-3] = '%';
				index = index-3;
			} else {
				str[index-1] = str[i];
				index--;
			}
			
		}
		
		System.out.println(str);
	}

}
