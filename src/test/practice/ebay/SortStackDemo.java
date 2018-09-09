package test.practice.ebay;

import java.util.Stack;

public class SortStackDemo {

	public static void main(String[] args) {

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(7);
		stack.push(10);
		stack.push(5);
		stack.push(12);
		stack.push(8);
		stack.push(3);
		stack.push(1);

		System.out.println(">>Input Stack: " + stack);

		//SortStack sort = new SortStack();
		
		sortStackASC(stack);
		System.out.println("Output: " + stack);

	}
	
	public static void sortStackASC(Stack<Integer> stack) {
		Stack<Integer> bufferStack = new Stack<Integer>();
		
		while(!stack.isEmpty()) {
			
			int temp = stack.pop();
			
			while(!bufferStack.isEmpty() && bufferStack.peek() > temp) {
				
				stack.push(bufferStack.pop());
			}
			bufferStack.push(temp);
		}
		
		while(!bufferStack.isEmpty()) {
			stack.push(bufferStack.pop());
		}
		
	}

}
