package ctci.ch3.stack.and.queues;

import java.util.ArrayList;
import java.util.List;


public class StackOfPlatesDemo {

	public static void main(String[] args) {
		
		SetOfStacks stacks = new SetOfStacks(3);
		
		stacks.push(11);
		stacks.push(22);
		stacks.push(33);
		stacks.push(44);
		stacks.push(55);
		stacks.push(66);
		stacks.push(77);
		stacks.push(88);
		stacks.push(99);
		
		System.out.println(stacks);
		
//		System.out.println("stacks.pop() :: "+stacks.pop());
		System.out.println("stacks.pop() :: "+stacks.popAt(1));
		
	}

}

class SetOfStacks {
	
	private int capacity;
	List<Stack> stacks = new ArrayList<Stack>();
	
	public SetOfStacks(int capacity) {
		this.capacity = capacity;
	}
	
	public Stack getLastStack() {
		
		Stack stack = null;
		
		if(stacks != null && stacks.size() > 0)
			stack = stacks.get(stacks.size() - 1);
	
		return stack;
	}
	
	
	public void push(int v) {
		
		Stack lastStack = getLastStack();
		
		if(lastStack != null && !lastStack.isFull()) {
			lastStack.push(v);
			
		} else {
			Stack stack = new Stack(capacity);
			stack.push(v);
			stacks.add(stack);
		}
	}
	
	public int pop() {
		
		Stack last = getLastStack();
		
		if(last == null) {
			System.out.println(">>Empty stack ");
			return -1;
		}
		
		int value = last.pop();
		
		if(last.isEmpty()) {
			stacks.remove(stacks.size()-1);
		}
		
		return value;
	}
	
	public boolean isEmpty() {
		Stack last = getLastStack();
		
		return last == null || last.isEmpty();
	}
	
	public int popAt(int index) {
		
		System.out.println("Size = "+stacks.size());
		if(index > stacks.size()-1) {
			System.err.println(">> Invalid index.");
			return -1; 
		}
		return leftShift(index, true);
	}
	
	public int leftShift(int index, boolean removeTop) {	
		
		
		Stack stack = stacks.get(index);
		int removedItem;
		if(removeTop) {
			removedItem = stack.pop();
			
		} else {
			removedItem =  stack.removeBottom();
		}
		
		if(stack.isEmpty()) {
			stacks.remove(index);
			
		} else if(stacks.size() > index+1) {
			int v = leftShift(index+1, false);
			stack.push(v);
		}
		
		return removedItem;
	}
	
	public String toString(){
		System.out.println("stacks -> "+stacks);
		return "";
	}
	
	
}


class Stack {

	private int capacity;
	private Node top, bottom;
	private int size = 0;
	
	public Stack(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean isFull() { 
		return size == capacity; 
	}
	public boolean isEmpty() {
		return size == 0;
 	}
	
	public void join(Node above, Node below) {
		
		if(below != null) 
			below.above = above;
		
		if(above != null)
			above.below = below;
	}
	
	public boolean push(int v) {
		if(size >= capacity) 
			return false;
		
		size++;
		Node n = new Node(v);
		
		if(size == 1) 
			bottom = n;
		
		join(top, n);
		
		top = n;
		
		return true;
	}
	
	public int pop() {
		Node t = top;
		top = top.below;
		size--;
		return t.value;
	}

	public int removeBottom() {
		Node t = bottom;
		
		bottom = bottom.above;
		
		if(bottom != null) {
			bottom.below = null;
		}
		
		size--;
		
		return t.value;
	}
	
	/*public String toString() {
		String s = "";
		
		s += "{top:" + top + ", bottom: "+bottom +"}";
		
		return s;
	}*/
	
	static class Node {
		int value;
		Node above;
		Node below;
		
		public Node(int v) {
			this.value = v;
		}
		
		/*public String toString() {
			String s = "";
			s += "[value:"+value+"]";
			
			return s;
		}*/

	}
	
}
