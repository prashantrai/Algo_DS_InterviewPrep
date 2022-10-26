package Bloomberg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class DesignALeaderboard_1244_Medium {

	public static void main(String[] args) {
		
		/* Every implementation is working in this class including 'Leaderboard_With_PriorityQueue'
		 * and can be check/run on LeetCode
		 * Debug to see the expected output as not all the method returns
		 */
		
		Leaderboard leaderboard = new Leaderboard();
		leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
		leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
		leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
		leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
		leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
		leaderboard.top(1);           // returns 73;
		leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
		leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
		leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
		leaderboard.top(3);           // returns 141 = 51 + 51 + 39;

	}

	/*
	 * HashMap + TreeMap [Use this approach is interview]
	 
    Time Complexity:

        1. O(logN) for 'addScore'. This is because each addition 
        	to the BST takes a logarithmic time for search. The 
        	addition itself once the location of the parent is known, 
        	takes constant time.
        	
        2. O(logN) for 'reset' since we need to search for the score 
        	in the BST and then update/remove it. Note that this complexity 
        	is in the case when every player always maintains a unique score.
        	
        3. O(K) for our 'top' function since we simply iterate over the 
        	keys of the TreeMap and stop once we're done considering 
        	K scores. Note that if the data structure doesn't provide a 
        	natural iterator, then we can simply get a list of all the 
        	key-value pairs and they will naturally be sorted due to the 
        	nature of this data structure. In that case, the complexity 
        	would be O(N) since we would be forming a new list.

	Space Complexity:
        O(N) used by the scores dictionary. Also, if you obtain all the 
        key-value pairs in a new list in the top function, then an 
        additional O(N) would be used.
    */ 
	
	private static class Leaderboard {

		Map<Integer, Integer> map; // score
		TreeMap<Integer, Integer> treeMap; // sortedScore

		public Leaderboard() {
			// key=playerId and value=score
			map = new HashMap<>();

			// key=score and value is frequency Of Same Scrore
			// i.e. how many player has same score
			treeMap = new TreeMap<>(Collections.reverseOrder());
		}

		public void addScore(int playerId, int score) {

			if (!map.containsKey(playerId)) {

				map.put(playerId, score);
				treeMap.put(score, treeMap.getOrDefault(score, 0) + 1);

			} else {
				// get the current score for player and keep as oldScore
				// because we need this to update tree map
				int oldScore = map.get(playerId);

				// now, since the current score is changing,
				// in treeMap the oldScore count has to be updated by -1
				// i.e. oldCount -1
				// because ther player with that count has new score now.
				treeMap.put(oldScore, treeMap.get(oldScore) - 1);

				// if the count is zero for the score in treeMap then remove
				// as there is no player with that score
				if (treeMap.get(oldScore) == 0) {
					treeMap.remove(oldScore);
				}

				// calculate new score
				int newScore = oldScore + score;

				// update map value with new score for player
				map.put(playerId, newScore);

				// add new score with count in treeMap
				treeMap.put(newScore, treeMap.getOrDefault(newScore, 0) + 1);
			}
		}

		public int top(int K) {
			int count = 0;
			int sum = 0;
			for (int key : treeMap.keySet()) { // will be sorted (reverse/descending)
				int scoreFreq = treeMap.get(key); // get count of each score
				// now use the score count/freq to fetch top K
				for (int i = 0; i < scoreFreq; i++) {
					sum += key;
					count++;

					// Found top-K scores, break.
					if (count == K)
						break;
				}

				// Found top-K scores, break.
				// we have break here as well to come out of 2nd for
				if (count == K)
					break;
			}
			return sum;
		}

		public void reset(int playerId) {
			int oldScore = map.get(playerId);

			// reduce the count from treeeMap and if it reaches 0 remove it
			treeMap.put(oldScore, treeMap.get(oldScore) - 1);
			if (treeMap.get(oldScore) == 0)
				treeMap.remove(oldScore);

			map.remove(playerId);

		}
	}

	/*
	 * Heap solution is good but this solution can be further improved by use
	 * TreeMap on cost of increasing time complexity addScore() but even after that
	 * the overall complexity of the solution will be less than the Heap solution.
	 *
	 * Above implementation is a using TreeMap. Reference:
	 * https://leetcode.com/problems/design-a-leaderboard/discuss/418833/Java-
	 * TreeMap-%2B-Map-Solution
	 *
	 */

	/*
	 * Space: O(N+K), where O(N) is used by HashMap and O(K) by the ProrityQueue.
	 */
	class Leaderboard_With_PriorityQueue {

		Map<Integer, Integer> map;

		public Leaderboard_With_PriorityQueue() {
			map = new HashMap<>();
		}

		// O(1)
		public void addScore(int playerId, int score) {
			map.put(playerId, map.getOrDefault(playerId, 0) + score);
		}

		/*
		 * O(K)+O(NlogK) = O(NlogK).
		 * 
		 * It takes O(K) to construct the initial heap and then for the rest of the N−K
		 * elements, we perform the extractMin and add operations on the heap each of
		 * which take (log K) time.
		 */
		public int top(int K) {
			/*
			 * Always build PQ here in top() because if reset called before this method then
			 * PQ and MAP will be out of sync
			 */

			PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // max heap
			int res = 0;

			for (int score : map.values()) {
				pq.offer(score);
			}

			while (K-- > 0) {
				res += pq.poll();
			}

			return res;
		}

		// O(1)
		public void reset(int playerId) {
			map.put(playerId, 0);
		}
	}
}
