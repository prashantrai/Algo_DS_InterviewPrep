package Amazon;

import java.util.Arrays;

public class MaximumCoinsFromKConsecutiveBags_3413_Medium {

	public static void main(String[] args) {

	}
	
	/* Interview Explanation Before Coding

		“I don’t want to expand these intervals because coordinates can 
		be up to a billion. Instead, I’ll sort the intervals and build a 
		prefix sum where each entry stores all coins from the complete 
		intervals before it.

		The important observation is that I only need to consider windows 
		where either the left side aligns with an interval start, or the 
		right side aligns with an interval end. 
		Otherwise, I could move the window until one of those boundaries 
		is reached without losing the maximum.
		
		So for every interval [l, r], 
		I’ll test two possible window starts: l, and r - k + 1.
		
		To calculate the value of each candidate efficiently, I’ll implement 
		a prefix query coinsUpTo(x). Using binary search I find the last 
		interval whose start is at or before x; the prefix array gives me all 
		earlier complete intervals, and then I calculate the possible partial 
		contribution from that last interval.
		
		Therefore each candidate takes O(log n), there are 2n candidates, 
		and sorting dominates the overall complexity at O(n log n).”
	 * */
	
	/* Complexity Analysis Before Coding
		Time: O(n log n)
			Sort the intervals: O(n log n)
			Build prefix sums: O(n)
			We test 2n candidate windows.
			Each candidate makes two binary-search prefix queries: O(log n).
	
		Space: O(n),  for the prefix-sum array.
	
		The problem allows up to 10^5 intervals, so this comfortably fits the constraints.
	*/
	
	/* Step-by-Step Algorithm
		1. Sort coins by interval start.
		2. Build a prefix array where:
			prefix[i + 1]
		
		   contains the total coins in intervals 0 ... i.
		
		3. For every interval [l, r, c], evaluate two candidate windows:
			start = l
			start = r - k + 1
			
		4. For a candidate:
				end = start + k - 1
		
		   calculate: coinsUpTo(end) - coinsUpTo(start - 1)
		
		5. coinsUpTo(x):
			- binary search for the last interval whose left <= x
			- take all complete intervals before it from prefix
			- add the overlapping portion of that interval.
		
		6. Return the maximum.
			 
	 * */
	
	public long maximumCoins(int[][] coins, int k) {
		Arrays.sort(coins, (a, b) -> Integer.compare(a[0], b[0]));
		
		int n = coins.length;

		// prefix[i] = total coins from intervals [0 ... i-1]
		long[] prefix = new long[n+1];
		
		// prefix[i + 1] contains the total coins in intervals 0 ... i.
		// i.e. each entry stores all coins from the complete intervals before it.
		for(int i=0; i<n; i++) {
			// r - l +1
			long length = coins[i][1] - coins[i][0] + 1;
			prefix[i + 1] = prefix[i] + length * coins[i][2];
		}
		
		long maxCoins = 0;
		
		for(int[] coin : coins) {
			long left = coin[0];
			long right = coin[1];
			
			// Case 1: window starts at an interval's left boundary
			maxCoins = Math.max(maxCoins, 
							rangeSum(coins, prefix, left, left+k-1));
			
			// Case 2: window ends at an interval's right boundary
			long start = right - k + 1L;
			
			maxCoins = Math.max(maxCoins,
	                		rangeSum(coins, prefix, start, right));
			
			
			
		}
		
		return maxCoins;
		
	}
	
	
	private long rangeSum(int[][] coins, long[] prefix,
            long left, long right) {
		
		return coinsUpTo(coins, prefix, right)
				- coinsUpTo(coins, prefix, left-1);
	}
	
	private long coinsUpTo(int[][] coins, long[] prefix, long x) {
		int lo = 0;
        int hi = coins.length - 1;
        int index = -1;
        
        // Find last interval whose start <= x
        while (lo <= hi) {
        	int mid = lo + (hi - lo) / 2;

        	if (coins[mid][0] <= x) {
                index = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        if (index == -1) {
            return 0;
        }
        
        // All intervals before index are fully included.
        long total = prefix[index];
        
        long left = coins[index][0];
        long right = coins[index][1];
        long coinsPerBag = coins[index][2];
        
        // Add the covered portion of interval[index].
        if(x >= left) {
        	long coveredRight = Math.min(x, right);
        	total += (coveredRight - left + 1) * coinsPerBag;
        }
        return total;
	}
}


/* Problem Explanation: 

	the key idea is:
	
	There is one bag at every integer position on the number line.
	Each interval [l, r, c] means every bag from l to r has c coins.
	Bags not covered by any interval have 0 coins.
	You must choose exactly k consecutive positions and maximize the total.
	Example:
	coins = [[8,10,1],[1,3,2],[5,6,4]], k = 4
	
	Number line:
	
	pos:   1 2 3 4 5 6 7 8 9 10
	coin:  2 2 2 0 4 4 0 1 1  1
	
	Choose any 4 consecutive bags:
	
	[1..4]  = 2+2+2+0 = 6
	[2..5]  = 2+2+0+4 = 8
	[3..6]  = 2+0+4+4 = 10   <- best
	[4..7]  = 0+4+4+0 = 8
	[5..8]  = 4+4+0+1 = 9
	
	So the answer is 10.

*/