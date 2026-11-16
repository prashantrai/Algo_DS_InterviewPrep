package Apple;

public class LastStoneWeightII_1049_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Interview Script:
    Every smash is effectively subtraction. Instead of simulating all 
    possible smashing sequences, I can treat the stones as two groups and 
    minimize the difference between their sums.

    If the total weight is S, and one group has weight x, the difference is S 
    − 2x, assuming x is at most S/2.

    So my goal is to find the largest subset sum not exceeding S/2. This 
    becomes a 0/1 Knapsack problem.

    I'll use a boolean DP array where dp[j] represents whether a subset sum j 
    is achievable. I'll iterate backwards for each stone because each stone 
    can only be used once.

    Finally, I'll find the largest achievable sum and return 
    total − 2 × that sum.

    Three things to remember for interviews
    1. dp[s] tells us whether some subset of processed stones 
    can produce sum s.
    2. dp[s - stone] checks whether including the current stone 
        can produce sum s.
    3. Backward iteration ensures that each stone is used at most once.
	*/
	
	/* Complexity: Time: O(n × S) and Space: O(S) space, 
	    where S is the total weight. */
	public int lastStoneWeightII(int[] stones) {
	    int total = 0;
	    for(int stone : stones) {
	        total += stone;
	    }
	
	    int target = total/2;
	
	    // dp[s] = true if sum s is achievable
	    boolean[] dp = new boolean[target+1];
	    dp[0] = true;
	
	    for(int stone : stones) {
	        // Traverse backwards to use each stone once
	        for(int s = target; s >= stone; s--) {
	            if(dp[s - stone]) {
	                dp[s] = true;
	            }
	        }
	    }
	
	    // Find largest achievable sum <= target
	    for(int s = target; s >= 0; s--) {
	        if(dp[s]) {
	            return total - (2 * s);
	        }
	    }
	    return 0;
	}
	
}
