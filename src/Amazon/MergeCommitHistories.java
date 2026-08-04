package Amazon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MergeCommitHistories {

	public static void main(String[] args) {

        String[] h1 = {"A", "B", "C", "D", "F"};
        String[] h2 = {"A", "C", "E", "F"};

        System.out.println(mergeHistories(h1, h2));

        // Edge Cases

        System.out.println(
                mergeHistories(
                        new String[]{"A", "B"},
                        new String[]{"A", "B"}));

        System.out.println(
                mergeHistories(
                        new String[]{"A", "B"},
                        new String[]{"C", "D"}));

        System.out.println(
                mergeHistories(
                        new String[]{},
                        new String[]{"A", "B"}));
    }
	
	/* Problem Summary::
	 * We are given two commit histories represented as sequences of commit IDs (or commit hashes). 
	 * We want to merge them while minimizing merge conflicts.
	 * 
	 *                 			/---> [Option 1] Pull from History A (Calculate new conflicts)
		State: DP[i][j] -------
                       			\---> [Option 2] Pull from History B (Calculate new conflicts)
	 * 
	 * 
	 */
	

    // Merge two commit histories while minimizing conflicts
    public static List<String> mergeHistories(String[] history1, String[] history2) {

        int m = history1.length;
        int n = history2.length;

        // dp[i][j] stores the length of the LCS between
        // history1[0..i-1] and history2[0..j-1].
        int[][] dp = new int[m + 1][n + 1];

        // Build the DP table for LCS.
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                // Matching commits extend the LCS by one.
                if (history1[i - 1].equals(history2[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                // Otherwise inherit the better solution.
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Recover the LCS by backtracking through the DP table.
        LinkedList<String> lcs = new LinkedList<>();

        int i = m;
        int j = n;

        while (i > 0 && j > 0) {

            // Matching commits belong to the LCS.
            if (history1[i - 1].equals(history2[j - 1])) {
                lcs.addFirst(history1[i - 1]);
                i--;
                j--;
            }
            // Move toward the larger LCS value.
            else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        List<String> merged = new ArrayList<>();

        int p1 = 0;
        int p2 = 0;

        // Merge histories around each common commit.
        for (String commit : lcs) {

            // Add commits unique to history1.
            while (!history1[p1].equals(commit)) {
                merged.add(history1[p1]);
                p1++;
            }

            // Add commits unique to history2.
            while (!history2[p2].equals(commit)) {
                merged.add(history2[p2]);
                p2++;
            }

            // Add the common commit exactly once.
            merged.add(commit);
            p1++;
            p2++;
        }

        // Append remaining commits from history1.
        while (p1 < m) {
            merged.add(history1[p1++]);
        }

        // Append remaining commits from history2.
        while (p2 < n) {
            merged.add(history2[p2++]);
        }

        return merged;
    }
	

}

/*
 Approach

First, compute the Longest Common Subsequence between the two histories.

The LCS represents commits that both histories already agree on.

Then reconstruct the merged history:

Walk through both sequences.
Before each common commit, add commits that appear only in the first history.
Add commits appearing only in the second history.
Add the common commit once.
Continue until all common commits are processed.
Finally append any remaining commits.

This produces a merged history while preserving the order from both branches and minimizing conflicts.

Interview Script (Before Coding)

Candidate:

"The important observation is that a merge conflict only arises where the two histories disagree. So instead of thinking about conflicts directly, I'd try to maximize the part of the histories that already agree.

If a commit appears in both histories and in the same relative order, we can safely keep it only once in the merged history. That immediately suggests finding the Longest Common Subsequence.

Once we know those common commits, they naturally divide both histories into matching sections. Everything between two common commits exists in only one branch, so we simply append those commits while preserving their original order.

This gives us a merged history that preserves the ordering of both branches while minimizing duplicated or conflicting commits."

Interviewer:

Why LCS instead of longest common substring?

Candidate:

"A substring requires commits to be consecutive, but Git histories often have independent commits inserted between shared commits. We only care about preserving relative order, not adjacency. LCS models that exactly."

Interviewer:

Why does maximizing the common commits minimize conflicts?

Candidate:

"Every common commit we identify is one less commit that needs reconciliation. If we used a smaller common subsequence, we'd unnecessarily treat shared commits as different. Since LCS is the largest possible shared ordered sequence, it minimizes the remaining unmatched commits."

Interviewer:

What are the complexities?

Candidate:

"Computing LCS takes O(m × n) time and O(m × n) space, where m and n are the two history lengths. Reconstructing the merged history is linear in the history sizes."

Candidate:

"If that sounds good, I'll go ahead and implement it."

6. Step-by-Step Algorithm (Implementation Checklist)
Let histories be A and B.
Build the LCS DP table.
Backtrack to recover the LCS.
Walk through both histories and the LCS simultaneously.
Add commits unique to A.
Add commits unique to B.
Add each LCS commit once.
Append remaining commits.
Return merged history.
 * */
