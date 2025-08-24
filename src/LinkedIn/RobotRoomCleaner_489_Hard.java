package LinkedIn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class RobotRoomCleaner_489_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
	
	private static final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // up, right, down, left
	private static Set<String> visited = new HashSet<>();
	private Robot robot;
	
	public void cleanRoom(Robot r) {
		this.robot = r;
		// dfs(x, y, dir)
		dfs(0,0,0); // start at (0,0), facing up
	}
	private void dfs(int x, int y, int dir) {
        String key = x + "," + y;   // cell
        if(!visited.add(key)) return;

        // clean current cell
        robot.clean();

        // Try 4 directions in order, always relative to current 'dir'
        int d = dir;
        for(int i=0; i<4; i++) {
            int newX = x + DIRS[d][0];
            int newY = y + DIRS[d][1];
            
            String newKey = newX + "," + newY;

            // Only attempt to enter if we haven't visited the target coordinate
			// and moved successfully into (newX,newY), move() returns false if 
            // move() returns false if next cell is obstacle, robot can't move
            if(!visited.contains(newKey) && robot.move()) {
                 dfs(newX, newY, d); // explore from there, keeping facing 'd'
				 goBack(); // backtrack to (x,y) and restore facing 'd'
            }

            robot.turnRight();
            // going clockwise : 0: 'up', 1: 'right', 2: 'down', 3: 'left'
            d = (d+1) % 4;
        }
    }

	// Move back one cell and restore original facing:
    // Turn 180°, move forward once, turn 180° -> back to previous cell with same heading.
	private void goBack() {
		robot.turnRight(); robot.turnRight();
		robot.move();
		robot.turnRight(); robot.turnRight();	
	}
	
	
	
	/** Iterative Solution */
	/*
	Iterative Approach: 
	Instead of recursion, I maintain a stack of (x,y,dir,step) states.
	- step tells how many directions I’ve already tried.
	- If all 4 directions are done, I pop and backtrack.
		This is equivalent to DFS but avoids recursion depth issues.
	 */
	
	
    private static class State {
        int x, y, dir, step;
        State(int x, int y, int dir, int step) {
            this.x = x; this.y = y; this.dir = dir; this.step = step;
        }
    }
    
    public void cleanRoom_Iter(Robot robot) {
        this.robot = robot;
        Deque<State> stack = new ArrayDeque<>();
        stack.push(new State(0, 0, 0, 0));
        visited.add("0,0");
        robot.clean();

        while (!stack.isEmpty()) {
            State cur = stack.peek();

            if (cur.step == 4) {
                stack.pop();
                goBack();
                continue;
            }

            int d = (cur.dir + cur.step) % 4;
            cur.step++;

            int nx = cur.x + DIRS[d][0];
            int ny = cur.y + DIRS[d][1];

            if (!visited.contains(nx + "," + ny) && robot.move()) {
                visited.add(nx + "," + ny);
                robot.clean();
                stack.push(new State(nx, ny, d, 0));
            }
            robot.turnRight();
        }
    }
    
    
    // Robot interface implementation
    class RobotMock implements Robot {
        private final int[][] room;
        private final Set<Long> cleaned = new HashSet<>();
        private int r, c, dir; // 0 up, 1 right, 2 down, 3 left

        RobotMock(int[][] room, int startR, int startC) {
            this.room = room;
            this.r = startR; this.c = startC; this.dir = 0; // starts facing up
        }

        @Override public boolean move() {
            int nr = r + (dir == 0 ? -1 : dir == 2 ? 1 : 0);
            int nc = c + (dir == 1 ?  1 : dir == 3 ? -1 : 0);
            if (nr < 0 || nr >= room.length || nc < 0 || nc >= room[0].length) return false;
            if (room[nr][nc] == 0) return false;
            r = nr; c = nc; return true;
        }

        @Override public void turnLeft()  { dir = (dir + 3) & 3; }
        @Override public void turnRight() { dir = (dir + 1) & 3; }

        @Override public void clean() {
            cleaned.add((((long)r) << 32) ^ (c & 0xffffffffL));
        }

        // Helper: count reachable '1' cells from start via BFS for validation
        int reachableCount() {
            int m = room.length, n = room[0].length;
            boolean[][] vis = new boolean[m][n];
            if (room[r][c] == 0) return 0;
            int count = 0;
            Deque<int[]> dq = new ArrayDeque<>();
            dq.add(new int[]{r,c}); vis[r][c] = true;
            int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};
            while (!dq.isEmpty()) {
                int[] cur = dq.poll();
                count++;
                for (int[] v : d) {
                    int nr = cur[0] + v[0], nc = cur[1] + v[1];
                    if (nr>=0 && nr<m && nc>=0 && nc<n && !vis[nr][nc] && room[nr][nc]==1) {
                        vis[nr][nc] = true; dq.add(new int[]{nr,nc});
                    }
                }
            }
            return count;
        }

        int cleanedCount() { return cleaned.size(); }
    }
	
}
