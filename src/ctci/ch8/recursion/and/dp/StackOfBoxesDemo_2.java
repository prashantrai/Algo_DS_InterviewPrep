package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.Collections;

public class StackOfBoxesDemo_2 {

	public static void main(String[] args) {

		ArrayList<Box> boxes = new ArrayList<Box>();
		boxes.add(new Box(4, 9, 6));
		boxes.add(new Box(1, 7, 4));
		boxes.add(new Box(2, 6, 9));
		
//		boxes.add(new Box(10, 12, 8));
//		boxes.add(new Box(6, 2, 5));
//		boxes.add(new Box(3, 8, 5));
//		boxes.add(new Box(5, 7, 7));
//		boxes.add(new Box(2, 10, 16));
//		boxes.add(new Box(12, 15, 9));
	      
		int stackHeight = createStack(boxes);
		
		System.out.println(">>Result:: "+stackHeight);
	}
	
	public static int createStack(ArrayList<Box> boxes) {
		Collections.sort(boxes);
		int[] stackMap = new int[boxes.size()];
		
		int maxHeight = createStack(boxes, null, 0, stackMap);
		
//		Collections.sort(boxes, Collections.reverseOrder());
//		int maxHeight = createStack_2(boxes, null, 0, stackMap);
		
		return maxHeight;
	}

	private static int createStack(ArrayList<Box> boxes, Box bottom, int index, int[] stackMap) {

		if(index >= boxes.size()) {
			return 0;
		}
		
		Box newBottom = boxes.get(index);
		int heightWithBottom = 0;
		if(bottom == null || newBottom.canBeAbove(bottom)) {
			
			if(stackMap[index] == 0) {
				stackMap[index]  = createStack(boxes, newBottom, index+1, stackMap);
				stackMap[index] += newBottom.height;
			} 
				
			heightWithBottom = stackMap[index];
		}
		
		//--without bottom
		int heightWithoutBottom  = createStack(boxes, bottom, index+1, stackMap);
		
		System.out.println("heightWithoutBottom="+heightWithoutBottom);
		System.out.println("heightWithBottom="+heightWithBottom);
		
		return Math.max(heightWithoutBottom , heightWithBottom );
	}
	
	
	
	//--This one also works: key is to reverse the order of boxes
	private static int createStack_2(ArrayList<Box> boxes, Box bottom, int index, int[] stackMap) {
		if(index >= boxes.size()) {
			return 0;
		}
		
		Box newBottom = boxes.get(index);
		int heightWithBottom = 0;
		if(bottom == null || newBottom.canBeAbove(bottom)) {
			
			if(stackMap[index] == 0) {
				stackMap[index]  = createStack_2(boxes, newBottom, index+1, stackMap);
				stackMap[index] = bottom != null ? Math.max(newBottom.height, bottom.height) : newBottom.height;
				
			} 
				
			heightWithBottom = stackMap[index];
		}
		
		System.out.println("heightWithBottom="+heightWithBottom);
		return heightWithBottom;
	}


}

/*class Box implements Comparable<Box>{
    int height;
    int width;
    int depth;
    
    public Box(int height, int width, int depth){
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public int compareTo(Box o) {
        int area = o.depth*o.width;
        int thisArea = this.depth*this.width;

        return  area - thisArea;
    	return this.height - o.height;
    }
    
    public boolean canBeAbove(Box b) {
    	
    	//--can be further improved with width and depth param comparison
    	
    	return this.height < b.height;
    }
    
    public String toString() {
    	return "["+this.height+"]";
    }
}
*/