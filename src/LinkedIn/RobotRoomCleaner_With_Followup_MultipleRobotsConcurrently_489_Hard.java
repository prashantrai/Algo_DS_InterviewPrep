package LinkedIn;

import java.util.BitSet;

public class RobotRoomCleaner_With_Followup_MultipleRobotsConcurrently_489_Hard {

	public static void main(String[] args) {

	}

	// Given by platform (find implementation at bottom)
	interface Robot {
	    boolean move();
	    void turnLeft();
	    void turnRight();
	    void clean();
	}
	
	//Dfs
	
	/*
	I’ll treat the robot’s start position as coordinate (0,0) in my own system.
	I’ll keep a visited set to avoid revisiting cells.
	
	DFS Approach: 
	1. Clean current cell and mark it visited.
	
	2. Try 4 directions in order: up, right, down, left.
		- If the next cell hasn’t been visited and move() succeeds, recurse.
		- After recursion, backtrack by turning 180°,
		  moving one step, and turning back 180°.
	
	3. Turn right at the end of each loop to rotate through all directions.
	   This guarantees we eventually visit all reachable cells.
	   
	
	* Algorithm (Interview-ready explanation for this follw-up)
	- If there are multiple robots, we want to avoid overlap.
	- Idea: Use a shared visited set (thread-safe) that all robots check before cleaning.
	- Partition strategy:
		-- Robots simply start DFS independently.
		-- Before cleaning a cell, they check the shared set. 
			If already cleaned, they skip it.
	- This keeps code simple but still avoids duplicate work.
	- Thread-safety: Use ConcurrentHashMap for visited.
	 
	 
	Complexity:  
	Let A = number of reachable cells.
	Time: O(A) because we clean each cell once, 
		and each edge is traversed at most twice (forward + backtrack).

	Space: O(A) for visited and recursion/stack.
	 * */
	
	// Follow-up: Multiple Robots working concurrently
	
	
	
}
