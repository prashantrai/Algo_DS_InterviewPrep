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
    Follow-up 1: Very large space / memory constraints
    
    Algorithm (Interview-ready explanation)
    - In the normal solution, we store every visited cell in a HashSet<String>.
    - In a very large room, this can blow up memory. 
    - Replace HashSet with Bloom filter. Memory smaller, small chance of missing 
      a few cells, but no infinite loops.
    
    - Idea: Use a Bloom filter (probabilistic set):
    	- Much more memory efficient.
    	- Allows false positives (thinks some cells are already visited when they aren’t).
    	- But still safe: worst case, we skip cleaning a few cells, but 
    	  the robot never revisits infinitely.
    
    - Implementation: Use a BitSet with a few hash functions to simulate a Bloom filter.
    */
	/*
	Complexity:  
	✅ Time Complexity: Still O(N)
		We still explore each cell at most once.
		Membership check in a bitset or Bloom filter is still O(1).
		False positives (Bloom filter) might add tiny overhead but asymptotically still O(1).
	✅ Space Complexity: Reduced from O(N) full HashSet to: 
		Bitset/compression: O(N) but much smaller constant factor.
		So, it stays same O(N) but way more memory-efficient
	 * */
	
	// Follow-up: mem constraints
	class SolutionMemConstraint {
	    private final int[][] DIRS = {{-1,0},{0,1},{1,0},{0,-1}}; // up, right, down, left
	    private Robot robot;
	    private BloomFilter visited;
	    
	    public SolutionMemConstraint(int capacity, int hashCount) {
	        this.visited = new BloomFilter(capacity, hashCount); // mem-constraint
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

	    // mem-constraint: Simple Bloom filter implementation
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
