package test.practice.ebay;

import java.util.Stack;

public class QueueViaStackDemo {

	public static void main(String[] args) {
		
		QueueViaStack<Integer> myQueue = new QueueViaStack<Integer>();
		
		myQueue.add(11);
		myQueue.add(22);
		myQueue.add(33);
		myQueue.add(44);
		myQueue.add(55);
		myQueue.add(66);
		myQueue.add(77);
		myQueue.add(88);
		myQueue.add(99);	
		
		System.out.println(myQueue);
		
		System.out.println(myQueue.peek());
		
		System.out.println(myQueue);
		
		System.out.println(myQueue.remove());
		
		System.out.println(myQueue);
	}

}

class QueueViaStack<T> {
	
	Stack<T> oldestStack;
	Stack<T> newestStack;
	
	public QueueViaStack() {
		oldestStack = new Stack<T>();
		newestStack = new Stack<T>();
	}
	
	public void push(T value) {
		newestStack.push(value);
	}
	
	public T remove() {
		shiftStack();
		return oldestStack.pop();
	}
	
	public T peek() {
		shiftStack();
		return oldestStack.peek();
	}
	
	public void shiftStack() {
		
		if(oldestStack.isEmpty()) {
			while(!newestStack.isEmpty()) {
				oldestStack.push(newestStack.pop());
			}
		}
	}
}
