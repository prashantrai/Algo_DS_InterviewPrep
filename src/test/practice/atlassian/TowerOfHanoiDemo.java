package test.practice.atlassian;

import java.util.Stack;

//https://www.cs.cmu.edu/~cburch/survey/recurse/hanoiimpl.html

public class TowerOfHanoiDemo {

	public static void main(String[] args) {
		
		int n = 3;
		
		Stack<Integer> src = new Stack<Integer>();
		Stack<Integer> buff = new Stack<Integer>();
		Stack<Integer> dest = new Stack<Integer>();
		
		for(int i=n-1; i>=0; i--) {
			src.push(i);
		}
		System.out.println("src: "+src);
		
		moveDisks(n, src, dest, buff);
		
		System.out.println("dest: "+dest);
	}
	
	public static void moveDisks(int n, Stack<Integer> src, Stack<Integer> dest, Stack<Integer> buff) {
		
		if(n>0) {
			
			moveDisks(n-1, src, buff, dest);
			
			System.out.println("src: "+src + ", buff: "+buff + ", dest: "+dest);
			
			moveDisk(src, dest);
			moveDisks(n-1, buff, dest, src);
			
		}
		
	}

	private static void moveDisk(Stack<Integer> src, Stack<Integer> dest) {
		
		int top = src.pop();
		
		if(!dest.isEmpty() && dest.peek() < top) {
			System.out.println("Can't push.");
			
		} else {
			dest.push(top);
		}
	}

}
