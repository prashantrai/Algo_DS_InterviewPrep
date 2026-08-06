package Amazon;

import java.util.LinkedList;
import java.util.List;

public class MergeCommitHistoriesWhileMinimizingMergeConflicts_OA {

	public static void main(String[] args) {
        System.out.println(mergeHistories(
                new String[]{"A", "B", "C"},
                new String[]{"B", "D", "C"}
        )); // [A, B, D, C]

        System.out.println(mergeHistories(
                new String[]{"A", "B", "C"},
                new String[]{"A", "B", "C"}
        )); // [A, B, C]

        System.out.println(mergeHistories(
                new String[]{"A", "C"},
                new String[]{"B", "D"}
        )); // one valid SCS, e.g. [A, C, B, D]

        System.out.println(mergeHistories(
                new String[]{},
                new String[]{"X", "Y"}
        )); // [X, Y]

        System.out.println(mergeHistories(
                new String[]{},
                new String[]{}
        )); // []

        System.out.println(mergeHistories(
                new String[]{"A", "B", "A"},
                new String[]{"B", "A"}
        )); // [A, B, A]
    }
	
	/* 
	 Problem Statement:
	 	Merging two commit histories while minimizing merge conflicts
	 	
	 	You are given two commit histories, history1 and history2.

		Each history is an ordered sequence of commits.
		The relative order of commits within each history must be preserved in the final merged history.
		If the same commit appears in both histories, it should appear only once in the merged result.
		Return a merged history that:
		
		Contains all commits from both histories
		Preserves the relative order of commits from each input history
		Includes common commits only once
		Has the minimum possible length
		If multiple valid merged histories exist, return any one of them.
	 	
	 	Goal: Find the minimum number of conflicts possible across any valid interleaving 
	 	of the two strings.
	*/

	/*
	 Interview Script
	 	1. “First, I want to restate the problem: I need to merge two commit histories, 
	 		preserve the order inside each history, and include common commits only once. 
	 		So the real goal is to build the shortest common supersequence.”
	 		
		2. “Whenever the same commit exists in both histories, I should keep just one copy. 
			That suggests I should first identify the commits both histories share in order, 
			which is exactly the Longest Common Subsequence (LCS).”
			
		3. “Why LCS? Because the longer the common subsequence I preserve, the fewer 
			duplicate commits I need to include in the final merged history.”
			
		4. “So I compute LCS with dynamic programming, where dp[i][j] tells me the LCS 
			length for the first i commits of history1 and first j commits of history2.”
			
		5. “After filling the table, I backtrack to construct the merged history:
			if commits match, I add it once
			otherwise, I follow the direction that keeps the better LCS and add the corresponding commit”
			
		6. “This guarantees order is preserved and the merged result has minimum length.”
	 */
	
	/*
	 Algorithm
		1. Let m = history1.length and n = history2.length.
		
		2. Create a DP table dp[m+1][n+1] where:
		
			dp[i][j] = length of the Longest Common Subsequence (LCS) between:
				history1[0...i-1]
				history2[0...j-1]
		
		3. Fill the DP table:
		
			If history1[i-1] == history2[j-1]:
				dp[i][j] = dp[i-1][j-1] + 1
			Otherwise:
				dp[i][j] = max(dp[i-1][j], dp[i][j-1])
		
		4. Reconstruct the merged history by backtracking from dp[m][n]:
		
			Start at i = m, j = n
			While both i > 0 and j > 0:
				If history1[i-1] == history2[j-1]:
					add this commit once
					move diagonally: i--, j--
				Else if dp[i-1][j] >= dp[i][j-1]:
					add history1[i-1]
					do i--
				Else:
					add history2[j-1]
					do j--
		5. If any commits remain in history1, add them.
		
		6. If any commits remain in history2, add them.
		
		7. Reverse the collected result if built backward, or use a front-inserting structure.
	 */
	
	
	/* Complexity: 
	 	Time: O(m * n)
		Space: O(m * n)
	 * */
	
	// This is the Shortest Common Super-sequence (SCS) problem.
	
	// Merge two commit histories while minimizing conflicts
    public static List<String> mergeHistories(String[] history1, String[] history2) {

        int m = history1.length;
        int n = history2.length;

        // dp[i][j] stores the length of the LCS between
        // history1[0..i-1] and history2[0..j-1].
        int[][] dp = new int[m+1][n+1];
        
        // Step 1: Build the DP table for LCS (Longest Common Subsequence).
        for(int i=1; i<=m; i++) {
        	for(int j=1; j<=n; j++) {
        		
        		// Matching commits extend the LCS by one.
        		if(history1[i-1].equals(history2[j-1])) {
        			dp[i][j] = dp[i-1][j-1] + 1;
        		}
        		// Otherwise inherit the better solution.
        		else {
        			dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
        		}
        		
        	}
        }
        
        
        // Step 2: Reconstruct the Shortest Common Super-sequence (SCS)
        // Backtrack from the bottom-right to reconstruct the merge (SCS) history
        
        LinkedList<String> merged = new LinkedList<>();
        int i = m, j = n;
        while (i > 0 && j > 0) {
        	if(history1[i-1].equals(history2[j-1])) {
        		// Common commit: write it a single time (no conflict here)
        		merged.addFirst(history1[i-1]);
        		i--;
        		j--;
        	}
        	else if (dp[i-1][j] >= dp[i][j-1]) {
        		// Divergent commit unique to history1
        		merged.addFirst(history1[i-1]);
        		i--;
        		
        	} else {
        		// Divergent commit unique to history2
        		merged.addFirst(history2[j-1]);
        		j--;
        	}
        }
        
        // Add any remaining commits from history1
        while(i > 0) {
        	merged.addFirst(history1[--i]);
        }
        // Add any remaining commits from history2
        while(j > 0) {
        	merged.addFirst(history2[--j]);
        }
        
        return merged;
    }
	

}
