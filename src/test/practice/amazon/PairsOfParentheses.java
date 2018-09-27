package test.practice.amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PairsOfParentheses {

	/*
	Input: 3
	Output: (( () ) ) , ( () () ) , ( () ) () , () ( () ) , () () ()
	*/
	
	public static void main(String[] args) {
		
		System.out.println(generateParens(3));

	}

	private static ArrayList<String> generateParens(int i) {
		
		char[] s = new char[i*2];
		ArrayList<String> res = new ArrayList<>();
		
		helper(s, 3, 3, 0, res);
		
		return res;
	}
	
	private static void helper(char[] s, int leftParen, int rightParen, int index, ArrayList<String> res) {
		
		//--syntax check 
		if(leftParen < 0 || leftParen > rightParen) {
			return;
		}
		
		if(leftParen == 0 && rightParen == 0) {
			res.add(String.copyValueOf(s));
			return;
		}
		
		//--left
		
		s[index] = '(';
		helper(s, leftParen-1, rightParen, index+1, res);
		
		//--right
		s[index] = ')';
		helper(s, leftParen, rightParen-1, index+1, res);
		
	}
	
	

}
