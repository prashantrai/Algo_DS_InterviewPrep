package test.practice.misc;

import java.util.HashSet;
import java.util.Stack;

public class FindCelebrity {

	public static void main(String[] args) {

		boolean[][] celeb = {	{false, false, true},
								{true,  false, true},
								{false, false, false}
							};

		
		boolean[][] celeb_1 = {	{false, false, true},
								{false,  false, true},
								{true, false, false}
							};

		
		
		findCelebrity(celeb);
		findCelebrity2(celeb);
	}

	public static void findCelebrity2(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return;
		}
		
		Stack<Integer> stack = new Stack<Integer>();

		for(int i=0; i<celeb.length; i++) {
			stack.push(i);
		}
		
		while (stack.size() > 1) {
			int a = stack.pop();
			int b = stack.pop();
		
			if(knows(a, b, celeb)) {
				stack.push(b);
			} else {
				stack.push(a);
			}
		}
		
		if (stack.isEmpty()) {
			System.out.println("No celeb");
		} else {
			System.out.println("Celeb is "+ (stack.peek() + 1));
		}
		
	}
	
	private static boolean knows(int a, int b, boolean[][] celeb) {
		
		return celeb[a][b];
	}
	
	
	
	
	
	
	public static void findCelebrity(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return;
		}
		
		HashSet<Integer> set = new HashSet<Integer>();
		int r = 0, c = 0;
		
		while (r < celeb.length && c < celeb[0].length) {
			
			if(!set.contains(r)) {
				if(celeb[r][c]) {
					set.add(r);
					if(celeb[c][r]) {  //--check if both person knows each other (by using column num as row and row as col num), if TRUE then add to set to skip iteration for that row
						set.add(c);
					}
					c = 0;
					r++;
				} else {
					c++;
				}
			} else {
				r++;
			} 
		}
		
		for(int i=0; i<celeb.length; i++) {
			if(!set.contains(i)) {
				System.out.println("celbrity is :: "+(i+1));
				return;
			} 
		}
		
		System.out.println("No celebrity found");
	}
	
}
