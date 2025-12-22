package Oracle;

import java.util.Stack;

public class MinStack_155_Medium {

	public static void main(String[] args) {
		MinStack minStack = new MinStack();
		minStack.push(-2);
		minStack.push(0);
		minStack.push(-3);
		minStack.getMin(); // return -3
		minStack.pop();
		minStack.top();    // return 0
		minStack.getMin(); // return -2
		 
	}

	/*
	 * The key insight is that we need to track the minimum value efficiently as
	 * elements are added and removed. Since we can only access the top of a stack,
	 * whenever we push a new element, we should also keep track of what the minimum
	 * is at that point in the stack's history.
	 * 
	 * Approaches: Use 2 Stacks: One main stack for elements, and a separate
	 * 'min-tracker' stack
	 * 
	 * - The main stack stores all the values normally - The min-stack only stores
	 * values when they're less than or equal to the current minimum - When pushing:
	 * if the new value ≤ current min (top of min-stack), push it to min-stack too -
	 * When popping: if the popped value equals the current min, pop from min-stack
	 * as well - getMin() simply returns the top of min-stack
	 * 
	 * This ensures all operations remain O(1) time, and space complexity is O(n) in
	 * worst case."
	 */

	static class MinStack {

		/*
		 * Time: O(1) for all oprations; 
		 * Space: O(n), where n is the number of elements
		 */

		// Main stack to store all elements
		private Stack<Integer> stack;
		// Min stack to keep track of minimums
		private Stack<Integer> minStack;

		public MinStack() {
			stack = new Stack<>();
			minStack = new Stack<>();
		}

		/**
		 * Push element val onto stack. Time Complexity: O(1)
		 * 
		 * We push to main stack always. We push to minStack only if val is <= current
		 * minimum (<= handles duplicate minimum values correctly)
		 */
		public void push(int val) {
			stack.push(val);
			// If minStack is empty or val is <= current minimum, update minStack
			if (minStack.isEmpty() || val <= minStack.peek())
				minStack.push(val);
		}

		/**
		 * Removes the element on top of the stack. Time Complexity: O(1)
		 * 
		 * Pop from main stack always. If popped value equals current minimum, pop from
		 * minStack too.
		 */
		public void pop() {
			int popped = stack.pop();
			if (popped == minStack.peek())
				minStack.pop();
		}

		/**
		 * Get the top element. Time Complexity: O(1)
		 */
		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return minStack.peek();
		}
	}

			 

}
