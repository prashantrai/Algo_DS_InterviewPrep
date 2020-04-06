package test.practice.atlassian;

import java.util.ArrayList;
import java.util.List;

public class PairsOfParentheses {

	/*
	Input: 3
	Output: (( () ) ) , ( () () ) , ( () ) () , () ( () ) , () () ()
	*/
	
	public static void main(String[] args) {
		
		System.out.println(generateParens(3));

	}
	
	public static List<String> generateParens(int count) {
		
		char[] arr = new char[count*2];
		List<String> list = new ArrayList<>();
		
		addParen(list, count, count, arr, 0);
		
		
		return list;
	}

	private static void addParen(List<String> list, int leftCount, int rightCount, char[] arr, int idx) {

		if(leftCount < 0 || rightCount < leftCount) {
			return;
		}
		
		if(leftCount == 0 && rightCount == 0) {
			list.add(String.copyValueOf(arr));
			//list.add(String.valueOf(arr));
		} else {
			arr[idx] = '(';
			addParen(list, leftCount-1, rightCount, arr, idx+1);
			
			arr[idx] = ')';
			addParen(list, leftCount, rightCount-1, arr, idx+1);
		}
		
		
	}
	
	
	

}
