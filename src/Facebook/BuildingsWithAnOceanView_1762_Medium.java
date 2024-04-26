package Facebook;

import java.util.ArrayDeque;
import java.util.Deque;

public class BuildingsWithAnOceanView_1762_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	Assume the first building can see the ocean and push it on to the stack.
	Traverse through the list of building heights.
	
	While the current building is taller than the building at the top of the stack 
	we need to pop.
	
	If stack is empty or the current building is shorter than the top of the stack 
	push the building onto the stack.
	
	At the end the stack will contain an ascending list of the buildings that 
	have a view of the ocean.
	
	Time Complexity: O(N) We only need one scan of the heights array
	Space Complexity: O(N) In the worst case the stack will contain every building and thus so will the output array 
	  
	 * */
	
	public static int[] findBuildings(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
		// Assume that the first building can see the ocean
        stack.push(0);
		// Walk through list of buildings
        for(int i = 1; i<heights.length;i++){
			// If the height of the current building is taller than whats in the stack
			// it needs to be the first building in the stack
            while(!stack.isEmpty() && heights[i] >= heights[stack.peek()]){
                stack.pop();
            }
				// We know that we have the next tallest building in the input array
                stack.push(i);
        }
		
		//Our stack now contains only the buildings that have a view of the ocean and we need to return it in the appropriate form
        int[] result = new int[stack.size()];
        int n = stack.size();
        for(int i = n-1; i>=0; i--){
            result[i] = stack.pop();
        }
        return result;
    }

}
