package leetcode;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class RottingOranges_994_Medium {

	public static void main(String[] args) {

		int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		// int res = orangesRotting_DFS(grid); //--working but failng on leetcode
		System.out.println("Expected: 4, Actual:: "+ orangesRotting(grid));

		int[][] grid2 = { { 0, 2 } };
		System.out.println("Expected: 0, Actual:: " + orangesRotting(grid2));
		
		int[][] grid3 = { { 1, 2, 1, 1, 2, 1, 1 } };
		int res = orangesRotting(grid3);
		System.out.println("Expected: 2, Actual:: " + res);

	}

	
	/*
	 * Time complexity : O(n) 
	 * */
	public static int orangesRotting(int[][] grid) {
		if (grid == null || grid.length == 0)
			return -1;

		Queue<Pair<Integer, Integer>> dq = new ArrayDeque<>();
		int freshOranges = 0;

		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 2) {
					dq.offer(new Pair<Integer, Integer>(r, c));
				} else if (grid[r][c] == 1) {
					freshOranges++;
				}
			}
		}

		if (freshOranges == 0)
			return 0;

		int count = 0;
		int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

		while (!dq.isEmpty()) {
			count++;
			int size = dq.size();

			/**
			 * count will increment once each iteration queue size i.e. if q size is 2 here
			 * then it will itrate 2 times and will increment the count or we can also say
			 * that in each min/count we are computing the entire q (i.e. populated in each
			 * iteration)
			 */
			for (int i = 0; i < size; i++) {
				Pair<Integer, Integer> p = dq.poll();

				int r = p.getKey();
				int c = p.getValue();

				for (int[] dir : dirs) {
					int neighbour_r = r + dir[0];
					int neighbour_c = c + dir[1];

					if (inBound(grid, neighbour_r, neighbour_c) && grid[neighbour_r][neighbour_c] == 1) {

						grid[neighbour_r][neighbour_c] = 2;
						dq.offer(new Pair<Integer, Integer>(neighbour_r, neighbour_c));
						freshOranges--;
					}
				}
			}
		}

		return freshOranges == 0 ? count - 1 : -1;
	}

	private static boolean inBound(int[][] grid, int r, int c) {
		return !(r < 0 || r > grid.length - 1 || c < 0 || c > grid[0].length - 1);
	}

	// --working
	public static int orangesRotting_DFS(int[][] grid) {
		if (grid == null || grid.length == 0)
			return -1;

		// Queue<Pair<Integer, Integer>> dq = new ArrayDeque<>();

		// int count = 0;
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 2) {
					helper(grid, r, c);
					// count++;
				}
			}
		}

		for (int[] row : grid) {
			for (int col : row) {
				if (col == 1)
					return -1;
			}
		}

		return count;

	}

	static Set<Pair<Integer, Integer>> set = new HashSet<>();
	static int count = 0;
	static int currCount = 0;

	public static void helper(int[][] grid, int r, int c) {

		if (r < 0 || r > grid.length - 1 || c < 0 || c > grid[0].length - 1) {
			return;
		}

		if (grid[r][c] == 0)
			return;

		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(r, c);

		if (set.contains(pair))
			return;

		if (grid[r][c] == 2)
			set.add(pair);

		if (grid[r][c] == 1) {
			grid[r][c] = 2;
			currCount++;
			return;
		}

		helper(grid, r - 1, c);
		helper(grid, r, c - 1);
		helper(grid, r + 1, c);
		helper(grid, r, c + 1);

		if (currCount > 0) {
			count++;
			currCount = 0;
		}
		return;
	}

	static class Pair<K, V> {
		K key;
		V value;

		Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		@Override
		public int hashCode() {

			int code = key.hashCode() * 3 + value.hashCode() * 5;
			return code;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pair))
				return false;

			Pair<K, V> pair = (Pair) o;
			return this.key == pair.key && this.value == pair.value;

		}

		@Override
		public String toString() {
			return "[key=" + key + ", value=" + value + "]";
		}

	}

}
