package TikTok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalAccountBalancing_465_Hard {

	public static void main(String[] args) {
		int[][] transactions = {{0,1,10},{2,0,5}};
		System.out.println("Expected: 2, Acual: "+ minTransfers(transactions));
		
		int[][] transactions2 = {{0,1,10},{1,0,1},{1,2,5},{2,0,5}};
		System.out.println("Expected: 1, Acual: "+ minTransfers(transactions2));
	}

	public static int minTransfers(int[][] transactions) {
        Map<Integer, Integer> debt = new HashMap<>();
        for(int[] t : transactions) {
            debt.put(t[0], debt.getOrDefault(t[0], 0) + t[2]); // giver
            debt.put(t[1], debt.getOrDefault(t[1], 0) - t[2]); // taker
        }
        
        return dfs(0, new ArrayList<>(debt.values()));
    }
    
    /*** Premium Question ***
    Time Complexity: O(N!)
        Since we are incrementing the start for each recursive call, 
        the loop will be in the fashion of N, N-1, N-2, ... 1 which is N!
        
        >So the Time complexity is O(N!)
        
    Space Complexity: O(N) for the map.
        N is the number of transaction.
        
    Reference: 
    https://happygirlzt.com/code/465.html
    https://www.youtube.com/watch?v=I8lLGTgb9LU&t=48s&ab_channel=happygirlzt
    
    https://leetcode.com/problems/optimal-account-balancing/discuss/95355/Concise-9ms-DFS-solution-(detailed-explanation)
    https://leetcode.com/problems/optimal-account-balancing/discuss/130895/Recursion-Logical-Thinking
    
    
    *
    */
    
    private static int dfs(int k, List<Integer> list) {
        if(k == list.size()) return 0;
        
        int curr = list.get(k);
        if(curr == 0) {
            return dfs(k+1, list);
        }
        
        int min = Integer.MAX_VALUE;
        
        for(int i=k+1; i<list.size(); i++) {
            int next = list.get(i);
            
            if(curr * next < 0) {
                list.set(i, curr + next);
                
                min = Math.min(min, dfs(k+1, list) + 1);
                
                list.set(i, next); // reset to prev value
                
                if(curr + next == 0) break;
            }
        }
        
        return min;
    }
}


/*
 465. Optimal Account Balancing - Hard


You are given an array of transactions transactions where 
transactions[i] = [from, to, amount] indicates that the person with ID = from gave amount $ to 
the person with ID = to.

Return the minimum number of transactions required to settle the debt.

 

Example 1:

Input: transactions = [[0,1,10],[2,0,5]]
Output: 2
Explanation:
Person #0 gave person #1 $10.
Person #2 gave person #0 $5.
Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
Example 2:

Input: transactions = [[0,1,10],[1,0,1],[1,2,5],[2,0,5]]
Output: 1
Explanation:
Person #0 gave person #1 $10.
Person #1 gave person #0 $1.
Person #1 gave person #2 $5.
Person #2 gave person #0 $5.
Therefore, person #1 only need to give person #0 $4, and all debt is settled.
  
  
 * */
