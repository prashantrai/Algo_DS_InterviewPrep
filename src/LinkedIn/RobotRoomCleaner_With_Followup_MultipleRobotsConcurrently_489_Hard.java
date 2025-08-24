package LinkedIn;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
	*/
	/*
	* Follow-up: Multiple Robots working concurrently
	* Algorithm (Interview-ready explanation for this follow-up)
	- If there are multiple robots, we want to avoid overlap.
	- Idea: Use a shared visited set (thread-safe) that all robots check before cleaning.
	- Partition strategy:
		-- Robots simply start DFS independently.
		-- Before cleaning a cell, they check the shared set. 
			If already cleaned, they skip it.
	
	- This keeps code simple but still avoids duplicate work.
	- Thread-safety: Use ConcurrentHashMap for visited.

	 -------
	- Time Complexity: O(N), Still O(N) total across all robots. 
		Each cell is visited at most once across the system.
		If there are k robots, per robot average complexity is O(N/k).
		Coordination (lock/check in shared visited set) adds small overhead → still O(1) per operation.
	- Space Complexity: O(N), Shared visited set is still O(N).
		Each robot’s recursion/DFS stack: O(N/k) worst case.
		Overall still O(N).
	➡️ Conclusion:
	Time remains O(N).
	Space remains O(N), just distributed across robots.
	 * */
	
	// Follow-up: Multiple Robots working concurrently
    private static final int[][] DIRS = {{-1,0},{0,1},{1,0},{0,-1}};
    // follow-up
    private static Set<String> sharedVisited = ConcurrentHashMap.newKeySet(); // shared map
    private Robot robot;
    
    public void cleanRoom(Robot robot) {
        this.robot = robot;
        dfs(0, 0, 0);
    }
    private void dfs(int x, int y, int dir) {
        String key = x + "," + y;
        if (!sharedVisited.add(key)) return; // skip if already cleaned globally

        robot.clean();
        int d = dir;
        for (int i = 0; i < 4; i++) {
            int nx = x + DIRS[d][0];
            int ny = y + DIRS[d][1];
            if (!sharedVisited.contains(nx + "," + ny) && robot.move()) {
                dfs(nx, ny, d);
                goBack();
            }
            robot.turnRight();
            d = (d + 1) % 4;
        }
    }

    private void goBack() {
        robot.turnRight(); robot.turnRight();
        robot.move();
        robot.turnRight(); robot.turnRight();
    }
}
