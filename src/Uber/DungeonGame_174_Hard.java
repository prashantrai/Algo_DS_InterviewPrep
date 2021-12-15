package Uber;

import java.util.Arrays;

public class DungeonGame_174_Hard {

	public static void main(String[] args) {
		
		DungeonGame_174_Hard obj = new DungeonGame_174_Hard();
		
		int[][] dungeon = {{-2,-3,3},{-5,-10,1},{10,30,-5}};
		
		int res = obj.calculateMinimumHP_2(dungeon);
		System.out.println(res);
	}
	
	//https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start

	
	// Time: O(N^2) Space: O(N)
	public int calculateMinimumHP_2(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[n-1] = 0;

        for(int i = m-1; i >= 0; i--){
            for(int j = n-1; j >= 0; j--){

                int res = Math.max(dp[j], dp[j+1]);

                if( res > 0 && dungeon[i][j] < 0 ) {
                    dp[j] = dungeon[i][j];
                }
                else{
                    dp[j] = dungeon[i][j] + res;
                }
            }
        }
        if( dp[0] > 0) return 1;
        return 1 - dp[0];
    }
	
	int inf = Integer.MAX_VALUE;
	MyCircularQueue dp;
	int rows, cols;

	public int calculateMinimumHP(int[][] dungeon) {
		this.rows = dungeon.length;
		this.cols = dungeon[0].length;
		this.dp = new MyCircularQueue(this.cols);

		int currCell, rightHealth, downHealth, nextHealth, minHealth;
		for (int row = 0; row < this.rows; ++row) {
			for (int col = 0; col < this.cols; ++col) {
				currCell = dungeon[rows - row - 1][cols - col - 1];

				rightHealth = getMinHealth(currCell, row, col - 1);
				downHealth = getMinHealth(currCell, row - 1, col);
				nextHealth = Math.min(rightHealth, downHealth);

				if (nextHealth != inf) {
					minHealth = nextHealth;
				} else {
					minHealth = currCell >= 0 ? 1 : 1 - currCell;
				}
				this.dp.enQueue(minHealth);
			}
		}

		// retrieve the last element in the queue
		return this.dp.get(this.cols - 1);
	}
	
	public int getMinHealth(int currCell, int nextRow, int nextCol) {
		if (nextRow < 0 || nextCol < 0)
			return inf;

		int index = cols * nextRow + nextCol;
		int nextCell = this.dp.get(index);
		// hero needs at least 1 point to survive
		return Math.max(1, nextCell - currCell);
	}

	static class MyCircularQueue {
		protected int capacity;
		protected int tailIndex;
		public int[] queue;

		public MyCircularQueue(int capacity) {
			this.queue = new int[capacity];
			this.tailIndex = 0;
			this.capacity = capacity;
		}

		public void enQueue(int value) {
			this.queue[this.tailIndex] = value;
			this.tailIndex = (this.tailIndex + 1) % this.capacity;
		}

		public int get(int index) {
			return this.queue[index % this.capacity];
		}
	}
}
