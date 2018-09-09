package ctci.ch3.stack.and.queues;

import java.util.Stack;

public class StackMinDemo {

	public static void main(String[] args) {

		StackWithMin stack = new StackWithMin();
		
		stack.push(5);
		stack.push(6);
		stack.push(3);
		stack.push(7);
		
		System.out.println(">> Stack:: "+stack);
		System.out.println("Min = "+stack.min());
		
		System.out.println("Poped value : "+stack.pop());
		System.out.println("Min = "+stack.min());
		System.out.println(">> Stack:: "+stack);
		
		System.out.println("Poped value : "+stack.pop());
		System.out.println("Min = "+stack.min());
		System.out.println(">> Stack:: "+stack);
		
	}

}

class StackWithMin extends Stack<Integer> {
	
	private Stack<Integer> s2;
	public StackWithMin() {
		s2 = new Stack<Integer>();
	}
	
	public void push (int value) {
		
		if(value <= min()) {
			s2.push(value);
		}
		super.push(value);
	}
	
	public Integer pop() {
		int value = super.pop();
		if(value == min()) {
			s2.pop();
		}
		return value;
	}
	
	public Integer min() {
		if(s2.isEmpty()) {
			return Integer.MAX_VALUE;
		}
		return s2.peek();
	}
	
}