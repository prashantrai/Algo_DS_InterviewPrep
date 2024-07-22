package doordash;

import java.util.Stack;

//--https://tech.pic-collage.com/algorithm-largest-area-in-histogram-84cc70500f0c

public class LargestRectangleInHistogram_84_Hard {

	/**
	 * Histogram is basically X and Y axis where for every X you have some Y value.
	 * */
	
	//--https://www.geeksforgeeks.org/largest-rectangle-under-histogram/
	//--https://www.programcreek.com/2014/05/leetcode-largest-rectangle-in-histogram-java/
	
	public static void main(String[] args) {
		//int hist[] = { 6, 2, 5, 4, 5, 1, 6 };
		//int hist[] = { 2,1,5,6,2,3 };
		int hist[] = { 1, 1};
        System.out.println("Maximum area is " + largestRectangleArea(hist));
	}
	
	
	//--https://leetcode.com/problems/largest-rectangle-in-histogram/submissions/
	public static int largestRectangleArea(int[] heights) {
	       
        if(heights == null || heights.length == 0) 
            return 0;
               
        
        int i = 0; //--track the array pos
        int max = 0; //--max area
        Stack<Integer> stk = new Stack<Integer>();
        
        while (i<heights.length) {
            
            if(stk.isEmpty() || heights[i] >= heights[stk.peek()]) {
                stk.push(i);
                i++;
            } else {
                int item = stk.pop();
                int right_index = i;
                int width = stk.isEmpty() ? (i-1) : (right_index - stk.peek() - 1);
                
                int area = heights[item] * width;
                max = max < area ? area : max;
            }
        }
        
        while (!stk.isEmpty()) {
            int item = stk.pop();
            int right_index = i;
            int width = stk.isEmpty() ? i : right_index - stk.peek() -1;
            
            int area = heights[item] * width;
            max = max < area ? area : max;
            
        }
        
        return max;
    }
	
	

}
