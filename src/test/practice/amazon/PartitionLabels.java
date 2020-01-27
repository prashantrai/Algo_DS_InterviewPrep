package test.practice.amazon;

import java.util.ArrayList;
import java.util.List;

public class PartitionLabels {

	// -- Leetcode Medium :: https://leetcode.com/problems/partition-labels/
	// -- https://www.youtube.com/watch?v=ED4ateJu86I
	
	
	/** See leetcode and youtube link above for more understading 
	 * Example 1:
		Input: S = "ababcbacadefegdehijhklij"
		Output: [9,7,8]
		Explanation:
		The partition is "ababcbaca", "defegde", "hijhklij".
		This is a partition so that each letter appears in at most one part.
		A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
	 */
	public static void main(String[] args) {

		String s = "abccaddbeffe";
		List<Integer> ans = partitionLabels(s);
		System.out.println("Expected: [8, 4], Actual: " + ans);
		
		s = "ababcbacadefegdehijhklij";
		ans = partitionLabels(s);
		System.out.println("Expected: [9,7,8], Actual: " + ans);

	}

	public static List<Integer> partitionLabels(String S) {
		int[] last_index_of_char = new int[26];

		// --record last seen index of each char in the string
		for (int i = 0; i < S.length(); i++) {
			last_index_of_char[S.charAt(i) - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int j = 0;
		int anchor = 0;

		for (int i = 0; i < S.length(); i++) {
			j = Math.max(j, last_index_of_char[S.charAt(i) - 'a']);

			if (i == j) {
				ans.add(i - anchor + 1); // Add one to return 7th index of array as 8th
				anchor = i + 1;
			}

		}
		return ans;
	}

}
