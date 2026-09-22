package Oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CombinationSum_39_Medium {

	public static void main(String[] args) {
		CombinationSum_39_Medium sol = new CombinationSum_39_Medium();

        int[][] candidatesList = {
            {2, 3, 6, 7},
            {2, 3, 5},
            {2},
            {2},
            {7},
            {8, 7, 4, 3},
            {2, 3, 5, 7}
        };

        int[] targets = {
            7,
            8,
            1,
            2,
            7,
            11,
            10
        };

        List<List<List<Integer>>> expectedList = new ArrayList<>();

        expectedList.add(Arrays.asList(
            Arrays.asList(2, 2, 3),
            Arrays.asList(7)
        ));

        expectedList.add(Arrays.asList(
            Arrays.asList(2, 2, 2, 2),
            Arrays.asList(2, 3, 3),
            Arrays.asList(3, 5)
        ));

        expectedList.add(new ArrayList<>());

        expectedList.add(Arrays.asList(
            Arrays.asList(2)
        ));

        expectedList.add(Arrays.asList(
            Arrays.asList(7)
        ));

        expectedList.add(Arrays.asList(
            Arrays.asList(3, 4, 4),
            Arrays.asList(3, 8),
            Arrays.asList(4, 7)
        ));

        expectedList.add(Arrays.asList(
            Arrays.asList(2, 2, 2, 2, 2),
            Arrays.asList(2, 2, 3, 3),
            Arrays.asList(2, 3, 5),
            Arrays.asList(3, 7),
            Arrays.asList(5, 5)
        ));

        for (int t = 0; t < candidatesList.length; t++) {
            int[] candidates = candidatesList[t];
            int target = targets[t];

            List<List<Integer>> actual = sol.combinationSum(candidates, target);
            List<List<Integer>> expected = expectedList.get(t);

            // Normalize expected
            List<String> expectedNormalized = new ArrayList<>();
            for (List<Integer> list : expected) {
                List<Integer> copy = new ArrayList<>(list);
                Collections.sort(copy);
                expectedNormalized.add(copy.toString());
            }
            Collections.sort(expectedNormalized);

            // Normalize actual
            List<String> actualNormalized = new ArrayList<>();
            for (List<Integer> list : actual) {
                List<Integer> copy = new ArrayList<>(list);
                Collections.sort(copy);
                actualNormalized.add(copy.toString());
            }
            Collections.sort(actualNormalized);

            boolean pass = expectedNormalized.equals(actualNormalized);

            System.out.println("Test Case #" + (t + 1));
            System.out.println("Candidates: " + Arrays.toString(candidates));
            System.out.println("Target: " + target);
            System.out.println("Expected: " + expectedNormalized);
            System.out.println("Actual:   " + actualNormalized);
            System.out.println("Result:   " + (pass ? "PASS" : "FAIL"));
            System.out.println("--------------------------------------------------");
        }
    }
	
	/* Interview explanation:
    - “I’ll use DFS/backtracking to build combinations incrementally.”
    - “At each step, I choose a candidate starting from a given index.”
    - “Because reuse is allowed, when I pick candidates[i], the next recursive call still starts from i.”
    - “To avoid duplicate combinations, I never go backward; I only explore from the current index onward.”
    - “After sorting, if a candidate is greater than the remaining target, I can stop the loop early.”

    Short Interview Summary (Same explanation jst shorter)
        If the interviewer asks for a quick summary, say:

        “I sort the array, then use backtracking.”
        “At each step, I try candidates from a start index onward.”
        “I pass the same index in recursion because a number can be reused.”
        “Sorting lets me stop early when the candidate exceeds the remaining target.”
        “This avoids duplicate combinations and keeps the implementation clean.”
	 */
	
	
	/* 
	   Time Complexity: O(n log n + n^(T/M)) worst case, here, T is target and M is smallestCandidate
	
	        Sorting: O(n log n)
	        DFS/backtracking: exponential in the worst case
	        A common interview expression:
	            Worst case: O(n^(target / smallestCandidate))
	        Practical pruning is good because:
	        target <= 40
	        valid answers are limited
	    Space Complexity
	    Recursion depth + current path: O(target / smallestCandidate)
	    Output space is extra and not counted in auxiliary space.
	
	    interview script:
	    Time: 
	    “For time complexity, this is a backtracking solution, so the worst case is exponential.”
	    “If T is the target and M is the smallest candidate, the maximum depth of recursion is T / M, because each step reduces the remaining sum by at least M.”
	    “At each level, in the worst case I may try multiple candidates, so a common upper bound is O(n^(T/M)), where n is the number of candidates.”
	    “There sorting cost of O(n log n).”
	    “In practice, it performs better because sorting allows pruning: once candidates[i] > remaining, I stop exploring that branch.”
	    “Also, since the problem asks for all combinations, the runtime is naturally output-sensitive.”
	    
	    For space complexity, say:
	    “Ignoring the output, auxiliary space is O(T / M) due to the recursion stack and the current path.”
	    “If we include the returned answer, then we add the space needed to store all valid combinations.”
	    */
	
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
	    // Interview note:
	    // Sorting helps in two ways:
	    // 1) keeps combinations in a fixed order
	    // 2) allows early stopping when candidate > remaining target
	    Arrays.sort(candidates);
	
	    List<List<Integer>> result = new ArrayList<>();
	    List<Integer> path = new ArrayList<>();
	
	    // Start DFS from index 0 with the full target
	    dfs(candidates, target, 0, path, result);
	    return result;
	}
	
	/**
	 * DFS / backtracking helper.
	 *
	 * start     -> we only choose from this index onward to avoid duplicate combinations
	 * remaining -> how much sum is still needed
	 * path      -> current combination being built
	 * result    -> all valid combinations
	 */
	private void dfs(int[] candidates, int remaining, int start,
	                 List<Integer> path, List<List<Integer>> result) {
	
	    // If remaining becomes 0, we found a valid combination
	    if (remaining == 0) {
	        result.add(new ArrayList<>(path));
	        return;
	    }
	
	    // Try every candidate from 'start' onward
	    for (int i = start; i < candidates.length; i++) {
	        int current = candidates[i];
	
	        // Crucial pruning:
	        // Since array is sorted, no need to continue if current > remaining
	        if (current > remaining) {
	            break;
	        }
	
	        // Choose current candidate
	        path.add(current);
	
	        // Reuse is allowed, so next call uses index i (not i + 1)
	        dfs(candidates, remaining - current, i, path, result);
	
	        // Backtrack: remove the last chosen number and try the next candidate
	        path.remove(path.size() - 1);
	    }
	}

}
