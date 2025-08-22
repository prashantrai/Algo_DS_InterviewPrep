package LinkedIn;

import java.util.BitSet;

public class RobotRoomCleaner_With_Followup_MemConstraints_489_Hard {

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
	 
	Complexity:  
	Let A = number of reachable cells.
	Time: O(A) because we clean each cell once, 
		and each edge is traversed at most twice (forward + backtrack).

	Space: O(A) for visited and recursion/stack.
	 * */
	
	// Follow-up: mem constraints
	class SolutionMemConstraint {
	    private final int[][] DIRS = {{-1,0},{0,1},{1,0},{0,-1}}; // up, right, down, left
	    private Robot robot;
	    private BloomFilter visited;

	    public SolutionMemConstraint(int capacity, int hashCount) {
	        this.visited = new BloomFilter(capacity, hashCount);
	    }

	    public void cleanRoom(Robot robot) {
	        this.robot = robot;
	        dfs(0, 0, 0);
	    }

	    private void dfs(int x, int y, int dir) {
	        String key = x + "," + y;
	        if (visited.contains(key)) return;
	        visited.add(key);

	        robot.clean();

	        int d = dir;
	        for (int i = 0; i < 4; i++) {
	            int nx = x + DIRS[d][0];
	            int ny = y + DIRS[d][1];

	            if (!visited.contains(nx + "," + ny) && robot.move()) {
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

	    // Simple Bloom filter implementation
	    class BloomFilter {
	        private final BitSet bits;
	        private final int capacity;
	        private final int hashCount;

	        BloomFilter(int capacity, int hashCount) {
	            this.capacity = capacity;
	            this.hashCount = hashCount;
	            this.bits = new BitSet(capacity);
	        }

	        void add(String key) {
	            for (int i = 0; i < hashCount; i++) {
	                int h = Math.abs((key + i).hashCode()) % capacity;
	                bits.set(h);
	            }
	        }

	        boolean contains(String key) {
	            for (int i = 0; i < hashCount; i++) {
	                int h = Math.abs((key + i).hashCode()) % capacity;
	                if (!bits.get(h)) return false;
	            }
	            return true;
	        }
	    }
	}
	
	
}
