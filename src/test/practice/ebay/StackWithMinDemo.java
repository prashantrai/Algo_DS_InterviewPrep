package test.practice.ebay;

import java.util.Stack;


public class StackWithMinDemo {

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
	
	Stack<Integer> s2;
	
	public StackWithMin() {
		s2 = new Stack<Integer>();
	}
	
	
	public void push(int value) {
		
		if(min() >= value) {
			s2.push(value);
		}
		super.push(value);
	}
	
	public Integer pop() {

		int v = super.pop();
		if(min() == v) {
			s2.pop();
		}
		return v;
	}
	
	
	public int min() {
		if(s2.isEmpty()) {
			return Integer.MAX_VALUE;
		} else 
			return s2.peek();
	}
	
}
