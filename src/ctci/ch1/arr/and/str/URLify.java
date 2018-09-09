package ctci.ch1.arr.and.str;

import java.util.Arrays;

/**
 * URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
has sufficient space at the end to hold the additional characters, and that you are given the "true"
length of the string. (Note: If implementing in Java, please use a character array so that you can
perform this operation in place.)
EXAMPLE
Input:  "Mr John Smith    "  --13 is the trueLenght (i.e string length without extra buffer at the end)
Output: "Mr%20John%20Smith"
 * */


public class URLify {
	
	public static void main(String[] args) {
		String s = "Mr John Smith    ";  //--true lenght=13 with extra buffer=17
		int trueLenght = s.trim().length();
		
		replaceSpace(s.toCharArray(), trueLenght);
		
	}
	
	public static void replaceSpace(char[] str, int trueLenght) {
		int spaceCount=0, index=0, i=0;
		
		for (i=0; i<trueLenght; i++) {
			
			if(str[i] == ' ') {
				spaceCount++;
			}
		}
		index = trueLenght + spaceCount*2;
		
		System.out.println("trueLenght="+trueLenght+", spaceCount="+spaceCount+", index="+index);
		
		//str = new char[index];
		
		/*
		 * 3 space 
		 * 1st space = current 1 needs 3 then add 2 more space to make 3 (1*2)
		 * 2nd space = current 1 needs 3 then add 2 more space to make 3 (so far 2spaces * 2spaces = 4 extra spaces required)
		 * 3rd space = current 1 needs 3 then add 2 more space to make 3 (so far 3spaces * 2spaces = 6 extra spaces required)
		 * 
		 * We can  here that we need twice the more space in array what we have available 
		 * */
//		System.out.println("index="+index);
		
		if(trueLenght < str.length)
			str[trueLenght] = '\0';	
		
		for (i=trueLenght-1; i>=0; i--) {
//			System.out.println(">>i="+i+", index="+index +", str.lenght="+str.length);
			if(str[i] == ' ') {
//				System.out.println(">str["+i+"]");
				str[index - 1] = '0';
				str[index - 2] = '2';
				str[index - 3] = '%';
				index = index-3;
			}
			else {
//				System.out.println(">>>i="+i+", index="+index +"str[index-1]="+str[index-1]);
				str[index-1] = str[i];
				index--;
			}
		}
		
		String s = new String(str);
		System.out.println("str="+s);
	}
 	
	
	
	public static void urlify(String s) {
		
		char[] chArr = s.toCharArray();
		
		for(int i=0; i<chArr.length; i++) {
			
			if(chArr[i] == ' ') {
				System.out.print("%20");
			} else {
				System.out.print(chArr[i]);
			}
		}
		
//		System.out.println("Urlify :: "+ Arrays.deepToString(chArr));
		
	}

}
