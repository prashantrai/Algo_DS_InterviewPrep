package doordash;

import java.util.ArrayDeque;
import java.util.Queue;

public class SimilarStringGroups_839_Hard {

	public static void main(String[] args) {
		String[] strs = {"tars","rats","arts","star"};
		System.out.println("Expected: 2, Actual: " + numSimilarGroups(strs));

	}

	/* Code Reference: https://leetcode.com/problems/similar-string-groups/discuss/132464/Simple-JAVA-with-BFS
	 * 
	 * For some Explanation: 
	 * https://leetcode.com/problems/similar-string-groups/discuss/132431/Easy-to-understand-BFS-solution-with-detailed-explanation
	 * 
	 * Time: O(N^2 * W), where N is length of array and W is length of each word
	 * Space: O(N) 
	 */
	public static int numSimilarGroups(String[] strs) {
        
		if(strs == null || strs.length <= 1) return 0;
		
		int group = 0;
		
		// record index of each as visited/processed element/word
		boolean[] visited = new boolean[strs.length]; 
		
		for(int i=0; i<strs.length; i++) {
			
			if(visited[i]) continue;
			
			group++;
			
			Queue<String> q = new ArrayDeque<>();
			q.offer(strs[i]);
			
			visited[i] = true;
			
			while(!q.isEmpty()) {
				String currStr = q.poll();
				
				for(int j=0; j<strs.length; j++) {
					if(visited[j]) continue;

					/* find hamming distance - Hamming distance between two strings of equal length 
					   is the number of positions at which the corresponding symbols are different
					   for this problem valid hamming distance is 2 
					 */
					String nextStr = strs[j];
					
					boolean isValidDiff = isValidHammingDistance(nextStr, currStr);
					
					if(isValidDiff) {
						visited[j] = true;
						q.offer(nextStr);
					}
					
				}
			} //while closed
		}// for closed
		
		return group;
    }

	/*
	 * for this problem valid hamming distance will be 2.
	 * We can also update this method to accept distance count
	 */
	private static boolean isValidHammingDistance(String nextStr, String currStr) {
		
		if(nextStr.equals(currStr)) return true; //NOTE: "aaaaa", "aaaaa" should be grouped together
		
		int diff = 0;
		for(int i=0; i<currStr.length(); i++) {
			if(currStr.charAt(i) != nextStr.charAt(i)) { 
				diff++;
				if(diff > 2) return false;
			}
		}
		//important here, "aaaaa", "aaaaa" should be grouped together
		// and this case diff will be ZERO and we should return true
		//return diff == 2 || (diff == 0 && currStr.length() >= 2);
		return diff == 2;
	}
}
