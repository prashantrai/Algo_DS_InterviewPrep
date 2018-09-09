package test.practice.misc;

import java.util.Stack;

public class LargestRectangleInHistogramDemo {

	/**
	 * Histogram is basically X and Y axis where for every X you have some Y value.
	 * */
	
	//--https://www.geeksforgeeks.org/largest-rectangle-under-histogram/
	//--https://www.programcreek.com/2014/05/leetcode-largest-rectangle-in-histogram-java/
	
	public static void main(String[] args) {
		//int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
		int hist[] = { 2,1,5,6,2,3 };
        System.out.println("Maximum area is " + getMaxRectArea(hist));
	}
	
	
	public static int getMaxRectArea(int[] hist) {

		if(hist == null || hist.length == 0) 
			return 0;
		
		Stack<Integer> stack = new Stack<Integer>();
		int i = 0;
		int maxArea = 0;
		
		while (i < hist.length) {
			
			if(stack.isEmpty() || hist[i] >= hist[stack.peek()]) {
				stack.push(i);  //--push the index of current rectangle
				i++;
				
			} else {  //--if the lower rectangle seen pop the stack and calculate the area
				
				int item = stack.pop();
				int rightIndex = i;  //--it should be i as we are incrementing this after every PUSH
				int leftIndex = !stack.isEmpty() ? stack.peek() : 0; //--left index of current rectangle
				
//				int area = hist[item] * (stack.isEmpty() ? i :  rightIndex - leftIndex - 1); 
				//--we don't need extra checkk as we have already calculated left for empty stack
				int area = hist[item] * (rightIndex - leftIndex - 1);
				
				maxArea = area > maxArea ? area : maxArea;
				
			}
		}
		
		while (!stack.isEmpty()) {
			int item = stack.pop();
			int rightIndex = i;  //--it should be i as we are incrementing this after every PUSH
			int leftIndex = !stack.isEmpty() ? stack.peek() : 0; //--left index of current rectangle
			
//			int area = hist[item] * (stack.isEmpty() ? i :  rightIndex - leftIndex - 1); 
			//--we don't need extra checkk as we have already calculated left for empty stack
			int area = hist[item] * (rightIndex - leftIndex - 1);
			
			maxArea = area > maxArea ? area : maxArea;
		}
		
		return maxArea;
	}

	
	public static int getMaxRectArea2(int[] hist) {
		
		int max = 0;
		int i = 0;
		
		Stack<Integer> stk = new Stack<Integer>();

		while (i<hist.length) {
			
			if(stk.isEmpty() || stk.peek() < hist[i]) {
				stk.push(i++);
			} else {
				int top = stk.pop();
				int rightIndex = i;
				int leftIndex = stk.peek();
				max = 
			}
			
			
		}
		
		return 0;
	}
	
	
}
