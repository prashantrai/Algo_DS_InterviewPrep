package leetcode;

import java.util.PriorityQueue;

public class FurthestBuildingYouCanReach_1642_Medium {

	public static void main(String[] args) {
		int[] heights = {4,12,2,7,3,18,20,3,19}; 
		int bricks = 10; 
		int ladders = 2;
		
		// With MaxHeap : this approach is more intutive than MinHeap
		System.out.println("Expected: 7, Actual: " + furthestBuilding(heights, bricks, ladders));
		//min heap
		System.out.println("Expected: 7, Actual: " + furthestBuilding2(heights, bricks, ladders));
		
		int[] heights2 = {14,3,19,3}; 
		bricks = 17; 
		ladders = 0;
		
		// With MaxHeap : this approach is more intutive than MinHeap
		System.out.println("Expected: 3, Actual: " + furthestBuilding(heights2, bricks, ladders));
		//min heap
		System.out.println("Expected: 3, Actual: " + furthestBuilding2(heights2, bricks, ladders));

	}
	
	/* We used MAX Heap here
	 * Idea is to in for loop of array of heights
	 * 
	 * 1. Get the difference between current building and next building heights 
	 * 		> int climb = heights[i+1] - heights[i];
	 * 
	 * 2. Use bricks first and at the same time add the used no of bricks (i.e. climb)in
	 * a PriorityQueue (MaxHeap).
	 * 		> pq.add(climb);
	 * 
	 * 3. Update remaining bricks. 
	 *     > bricks -= climb;
	 *     
	 * 4. If neither bricks nor ladder left. return the current index as we can't move beyond 
	 * this as we have exhausted all the resources.
	 *     
	 * 5 . If we ran out of bricks (i.e) and we have ladders then use ladder 
	 * and remove the last used bricks (i.e. top of pq) from Heap. Update bricks update ladder
	 * 	   > if(bricks < 0) {
                bricks += pq.remove();
                ladders--;
            }   
	 * 
	 * 6. Finally, if have reached the end i.e. outside of loop then return the height-1 
	 * i.e. last buidling index
	 * */
	
	// Time: O(N LogN) Space: O(N)
	//max heap
    public static int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b-a); //max heap
        
        for(int i=0; i<heights.length-1; i++) {
            int climb = heights[i+1] - heights[i];
            
            // If this is actually a "jump down", skip it.
            if(climb <= 0) continue;
            
            // Otherwise, allocate a bricks for this climb.
            pq.add(climb);
            bricks -= climb;
            
            // If we've used all the bricks, and have no ladders remaining, then 
            // we can't go any further.
            if(bricks < 0 && ladders == 0) {
                return i;
            }
         
            // Otherwise, if we've run out of bricks, we should replace the largest
            // brick allocation with a ladder.
            if(bricks < 0) {
                bricks += pq.remove();
                ladders--;
            }
            
        }
        
        // If we got to here, this means we had enough materials to cover every climb.
        return heights.length - 1;
        
    }

	/*
    Use PriorityQueue to maintain an active set of height-diff we've seen so far. 
    Because in the end, anyhow, we need to use both ladders and bricks.
    We can use bricks when we run out of ladders by using the sorting functionality     
    provided by PriorityQueue.
    
    Reference: https://leetcode.com/problems/furthest-building-you-can-reach/discuss/918515/JavaC%2B%2BPython-Priority-Queue
    
    Explanation
        1. Heap heap store k height difference that we need to use ladders.
        2. Each move, if the height difference d > 0,
        3. we push d into the priority queue pq.
        4. If the size of queue exceed ladders,
        5. it means we have to use bricks for one move.
        6. Then we pop out the smallest difference, and reduce bricks.
        7. If bricks < 0, we can't make this move, then we return current index i.
        8. If we can reach the last building, we return A.length - 1.
    */
    // Time: O(N LogN) Space: O(N)
    // min heap
    public static int furthestBuilding2(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(); //min heap
        for(int i=0; i<heights.length-1; i++) {
            int d = heights[i+1] - heights[i]; // height diff between buildings
            
            // we assume that this dif will be managed by ladder
            if(d > 0) {
                pq.offer(d); // we can use add() too
            }
            // min diff is managed here by bricks
            if(pq.size() > ladders) {
                bricks -= pq.poll();
            }
            // no more bricks left and ladder is also utilized
            // then return the current index i.e. buiding num
            if(bricks < 0) {
                return i;
            }
        }
        
        //if we reach at this means we have reached the end of the buildings
        // return the last index
        return heights.length-1;
    }
	
}
