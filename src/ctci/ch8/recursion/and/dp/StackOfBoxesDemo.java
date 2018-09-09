package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.Collections;

public class StackOfBoxesDemo {

	public static void main(String[] args) {

		ArrayList<Box> boxes = new ArrayList<Box>();
		boxes.add(new Box(4, 9, 6));
		boxes.add(new Box(1, 7, 4));
		boxes.add(new Box(2, 6, 9));
		
		//boxes.add(new Box(10, 12, 8));
		boxes.add(new Box(6, 2, 5));
		/*boxes.add(new Box(3, 8, 5));
		boxes.add(new Box(5, 7, 7));
		boxes.add(new Box(2, 10, 16));
		boxes.add(new Box(12, 15, 9));*/
	      
		int stackHeight = createStack(boxes);
		
		System.out.println(">>Result:: "+stackHeight);
	}
	
	public static int createStack(ArrayList<Box> boxes) {
		
		int maxHeight = 0; //stack height
		System.out.println(">>boxes:: "+boxes);
		Collections.sort(boxes);
		int[] stackMap = new int[boxes.size()];
		
		System.out.println("boxes:: "+boxes);
		
		for(int i=0; i<boxes.size(); i++) {
			
			int height = createStack(boxes, i, stackMap);
			maxHeight = Math.max(maxHeight, height);
		}
		
		return maxHeight;
	}

	private static int createStack(ArrayList<Box> boxes, int index, int[] stackMap) {

		if(index < boxes.size() && stackMap[index] > 0) {
			return stackMap[index];
		}
		
		Box bottom = boxes.get(index);
		
		int maxHeight = 0;
		
		for(int i=1+index; i<boxes.size(); i++) {
			
			Box box = boxes.get(i);
			if(box.canBeAbove(bottom)) {
				int height = createStack(boxes, i, stackMap);
				maxHeight = Math.max(maxHeight, height);
			}
		}
		maxHeight += bottom.height;
		stackMap[index] = maxHeight;
		
		return maxHeight;
	}

}

class Box implements Comparable<Box>{
    int height;
    int width;
    int depth;
    
    public Box(int height, int width, int depth){
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public int compareTo(Box o) {
        /*int area = o.depth*o.width;
        int thisArea = this.depth*this.width;

        return  area - thisArea;*/
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
