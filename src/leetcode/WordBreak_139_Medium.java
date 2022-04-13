package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordBreak_139_Medium {

	public static void main(String[] args) {
		String s = "leetcode";
		List<String> wordDict = Arrays.asList("leet","code");
		System.out.println("1. Expected: true, Actual: " + wordBreak(s, wordDict));
		
		s = "applepenapple";
		wordDict = Arrays.asList("apple","pen");
		System.out.println("2. Expected: true, Actual: " + wordBreak(s, wordDict));
		
		s = "catsandog";
		wordDict = Arrays.asList("cats","dog","sand","and","cat");
		System.out.println("3. Expected: false, Actual: " + wordBreak(s, wordDict));
		
		// BFS
		s = "leetcode";
		wordDict = Arrays.asList("leet","code");
		System.out.println("BFS: Expected: true, Actual: " + wordBreak(s, wordDict));
		
		// Using Dynamic Programming
		System.out.println("DP: Expected: true, Actual: " + wordBreak_DP(s, wordDict));
		
	}
	
	/*  Time and space complexity is same for all the solutions/approaches	
	 * 
	 * Time: O(n^3) : No of times wordBreak() called (n)
	 * 					 * for each wordBreak() there is FOR loop (n)
	 * 						* for every iteration of for loop there is substring() call (n)
	 * 					=> n * n * n = n^3
	
	 	Space: O(n). The depth of recursion tree can go up to n
	 
	 */
	
	public static boolean wordBreak(String s, List<String> wordDict) {
        return helper(s, new HashSet<String>(wordDict), 0, new Boolean[s.length()]);
    }
    
    private static boolean helper(String s, Set<String> dict, int start, Boolean[] memo) {
        if(start == s.length()) 
            return true;
        
        if(memo[start] != null) 
            return memo[start];
        
        for(int end=start+1; end<=s.length(); end++) {
            String curr = s.substring(start, end);
            if(dict.contains(curr) 
                && helper(s, dict, end, memo)) {
                
                memo[start] = true;
                return true;
            }
        }
        
        memo[start] = false;
        return false;
    }
    
    
    // BFS
    public static boolean wordBreakBFS(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[s.length()];
        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            if (visited[start]) {
                continue;
            }
            for (int end = start + 1; end <= s.length(); end++) {
                if (wordDictSet.contains(s.substring(start, end))) {
                    queue.add(end);
                    if (end == s.length()) {
                        return true;
                    }
                }
            }
            visited[start] = true;
        }
        return false;
    }

    
    // Using Dynamic Programming
    public static boolean wordBreak_DP(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
