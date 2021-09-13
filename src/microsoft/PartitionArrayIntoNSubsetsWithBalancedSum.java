package microsoft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PartitionArrayIntoNSubsetsWithBalancedSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<List> part(int[] T, int n) {
		int[] sums = new int[n];
		
		PriorityQueue pq = new PriorityQueue<>(new Comparator() {
			public int compare(Integer a, Integer b) {
				return sums[a.intValue()] - sums[b.intValue()];
			}
		});

		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			result.add(new ArrayList<>());
			pq.add(i);
		}

		for (int i = T.length - 1; i >= 0; i--) {
			int c = pq.poll();
			result.get(c).add(T[i]);
			sums[c] += T[i];
			pq.add(c);
		}

		return result;
	}

}
