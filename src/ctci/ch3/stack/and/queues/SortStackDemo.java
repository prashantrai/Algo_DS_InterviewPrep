package ctci.ch3.stack.and.queues;

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
		
		SortStack sort = new SortStack();
		System.out.println("Output: "+sort.sortStackASC(stack));
		
	}
}

class SortStack {
	
	public Stack<Integer> sortStackASC(Stack<Integer> mainStack) {

		Stack<Integer> bufferStack = new Stack<Integer>();

		while(!mainStack.isEmpty()) {
			
			//--Pop value from stack and hold in a temp variable
			int temp = mainStack.pop();
		
			//--Pop value from bufferStack and push into main stack till we get it is greater than the value of temp variable
			while (!bufferStack.isEmpty() && bufferStack.peek() > temp) {
				mainStack.push(bufferStack.pop());
			} 
			
			//--push temp to bufferStack
			bufferStack.push(temp);
		}
		
		//--Copy element from bufferStack back to mainStack
		while(!bufferStack.isEmpty()) {
			mainStack.push(bufferStack.pop());
		}
		
		return mainStack;
	}
}
