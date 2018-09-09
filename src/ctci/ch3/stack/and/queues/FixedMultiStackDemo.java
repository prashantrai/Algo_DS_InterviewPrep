package ctci.ch3.stack.and.queues;

import java.util.Arrays;

/**
 * POP doesn't work, needs to be fixed 
 * */

public class FixedMultiStackDemo {

	public static void main(String[] args) {

		FixedMultiStack fixedMultiStack = new FixedMultiStack(3);
		fixedMultiStack.push(1, 11);
		fixedMultiStack.push(1, 12);
		fixedMultiStack.push(1, 13);
		
		fixedMultiStack.push(2, 21);
		
		fixedMultiStack.push(3, 31);
		fixedMultiStack.push(3, 32);
		
		
		System.out.println(fixedMultiStack);
		
		System.out.println(fixedMultiStack.pop(1));  /* pop needs to be fixed, it's not working, we need make sure getIndexOfTop() returning right index  */
		
	}
}

class FixedMultiStack {
	
	private int numOfStacks = 3;
	private int stackCapacity;
	private int[] values;
	private int[] sizes;
	
	public FixedMultiStack(int stackSize) {
		
		this.stackCapacity = stackSize;
		values = new int[stackSize * numOfStacks];
		sizes = new int[numOfStacks];
	}
	
	/* Push values onto the stack  */
	public void push(int stackNum, int value) {
		
		//--Return if stack is full
		if(isStackFull(stackNum)) {
			System.out.println(">> Stack is full.");
			return;
		}
		
		//--If stack is not Full get the top of the stack
		int topOfStack = getIndexOfTop(stackNum, stackCapacity);
		values[topOfStack] = value;
		sizes[stackNum-1]++;
		
	}
	
	/* Pop values from top stack  */
	public int pop(int stackNum) {
		
		//--Return if stack is empty
		if(isEmptyStack(stackNum)) {
			System.out.println(">> Empty stack.");
			return -1;
		}
		
		int topOfIndex = getIndexOfTop(stackNum, stackCapacity); 
		
		return values[topOfIndex];
	}
	
	
	
	/* Get the top of the stack*/
	public int getIndexOfTop(int stackNum, int stackCapacity) {
		
		/*
		 * Total num of stack=3 with capacty 3 each
		 assume, stackNum=2 and stackCapacity=3, currentSize=1
		
		(stackNum-1)*stackCapacity = 2*3 = 6 
		size = 2
		6+2-1 = 7
		
		   
		 **/
		//stackNum--;  //--reducing by 1 because arrays starts with index 0
		int offset = stackNum * stackCapacity;
		int size = sizes[stackNum-1];
		return offset - size -1;
	}
	
	/* Return if stack is empty */
	public boolean isEmptyStack(int stackNum) {
		
		if(sizes[stackNum-1] == 0) return true;
		
		return false;
	}
	
	/* Return if stack is full */
	public boolean isStackFull(int stackNum) {
		
		if(sizes[stackNum-1] == stackCapacity) return true;
		
		return false;
	}
	
	public String toString() {
		System.out.println(">> numOfStacks="+numOfStacks+", stackCapacity="+stackCapacity);
		
		System.out.print("sizes = ");
		for(int i : sizes) {
			System.out.print(i + ", ");
		}
		System.out.print("\n\n values= ");
		for (int i : values) {
			System.out.print(i + ", ");
		}
		
		System.out.println();
		
		return "";
	}
	
}