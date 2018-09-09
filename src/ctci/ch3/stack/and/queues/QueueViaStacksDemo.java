package ctci.ch3.stack.and.queues;

import java.util.Stack;

public class QueueViaStacksDemo {

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
	
	private Stack<T> newestStack, oldestStack;
	
	public QueueViaStack() {
		newestStack = new Stack<T>();
		oldestStack = new Stack<T>();
	}
	
	public int size() {
		return newestStack.size() + oldestStack.size();
	}
	
	public void add(T vlaue) {
		newestStack.push(vlaue);
	}
	
	private void shiftStacks() {
		//--push everything element to oldestStack till it's full
		if(oldestStack.isEmpty()) {
			while(!newestStack.isEmpty()) {
				oldestStack.push(newestStack.pop());
			}
		}
	}
	
	public T peek() {
		shiftStacks(); //--make sure oldest stack has current element
		return oldestStack.peek();
	}
	
	public T remove() {
		shiftStacks(); //--makes sure stack oldest has the current element
		return oldestStack.pop();
	}
	
	public String toString() {
		System.out.println(">>oldestStack :: "+oldestStack);
		System.out.println(">>newestStack :: "+newestStack);
		
		return "";
	}
}
