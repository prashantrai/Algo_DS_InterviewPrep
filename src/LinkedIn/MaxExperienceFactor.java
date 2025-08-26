package LinkedIn;

public class MaxExperienceFactor {

	// quick test harness with multiple test cases
    public static void main(String[] args) {
        // Example 1 (given in prompt)
        String s1 = "10101";
        int[][] groups1 = {{1,3}, {2,5}, {1,5}};
        System.out.println("Expected 6, got -> " + maxExperience(s1, groups1));

        // Example 2 (all ones)
        String s2 = "11111";
        int[][] groups2 = {{1,5}, {2,4}};
        System.out.println("Expected 0, got -> " + maxExperience(s2, groups2));

        // Example 3 (all zeros)
        String s3 = "0000";
        int[][] groups3 = {{1,4}, {2,3}};
        System.out.println("Expected 0, got -> " + maxExperience(s3, groups3));

        // Example 4 (single booth)
        String s4 = "1";
        int[][] groups4 = {{1,1}, {1,1}};
        System.out.println("Expected 0, got -> " + maxExperience(s4, groups4));

        // Example 5 (mixed, overlapping)
        String s5 = "1100101";
        int[][] groups5 = {{1,7}, {3,5}, {2,6}, {4,4}};
        System.out.println("Got -> " + maxExperience(s5, groups5));
    }
    
    
    /*
     Algorithm (simple steps)
		Build a prefix-sum array pref where pref[i] = number of 1s in booths[0..i-1].
			- pref[0] = 0
			- pref[i] = pref[i-1] + (boothChar == '1' ? 1 : 0)
		
		For each group range [l, r] (assume 1-based input):
			- Validate/sanitize l and r (swap if l>r, clamp within [1..m]).
			- Compute ones = pref[r] - pref[l-1].
			- len = r - l + 1
			- zeros = len - ones
			- experience = (long) ones * zeros (use long to avoid overflow)
			- Track the maximum experience.
		
		Return the maximum.
		
		Why this is efficient: prefix sums let us compute ones in O(1) per query. 
		No scanning each range.
	*/
    
    /*
	Complexity: 
		Time: O(m + n) where m = length of string, n = number of groups.
		(O(m) to build prefix sums, O(1) per group → O(n))
		
		Space: O(m) for the prefix array (size m+1). 
     * */
    
    /* interview script:: 
    Problem: “Given a binary string of booths and multiple visitor ranges, 
    for each range the experience = (#1s)×(#0s). Return the maximum experience across ranges.”

	Key idea: “Use prefix-sums of ones so each range’s ones count is O(1). 
		Zeros = range length − ones.”
	
	Complexity: “O(m + n) time, O(m) extra space for prefix. 
		This is optimal for arbitrary queries.”
	
	Edge cases/validations: “Handle 1-based indices, clamp out-of-range queries, 
	swap if l>r, and use long for product to avoid overflow.”
	
	Finish: “I’ll implement prefix build (m steps) then loop groups (n steps) — done.”
     * */
    
    /*
    1) Idea in one sentence: 
	Make a running count of how many 1s appear up to each position. 
	Then for any range [l..r], subtract the running counts to get how 
	many 1s are inside that range instantly.
	
	2) Build the prefix array (what it is)
		- pref[0] = 0 (no characters seen yet)
		- For i = 1..m: pref[i] = pref[i-1] + (booths[i] == '1' ? 1 : 0)

		pref[i] = number of 1s in the first i booths (positions 1..i).
	
    3) Why subtraction gives the ones in [l..r]
		pref[r] = #1s in 1..r
		pref[l-1] = #1s in 1..(l-1)
		
		So pref[r] - pref[l-1] removes all 1s before l and leaves just the 1s in l..r.
		
		Example A — range l=2, r=5 (substring 0 1 0 1)
		ones = pref[r] - pref[l-1]
		ones = pref[5] - pref[1] = 3 - 1 = 2 (there are two 1s at positions 3 and 5)
		len = r - l + 1 = 5 - 2 + 1 = 4
		zeros = len - ones = 4 - 2 = 2
		experience = ones * zeros = 2 * 2 = 4
		
		Example B — range l=1, r=5 (whole string 1 0 1 0 1)
		ones = pref[5] - pref[0] = 3 - 0 = 3
		len = 5
		zeros = 5 - 3 = 2
		experience = 3 * 2 = 6
     * */
	
	/**
     * Compute maximum experience factor across given groups.
     * @param booths binary string of '0'/'1' (length m)
     * @param groups array of ranges, each group is int[]{l, r} (1-based indices)
     * @return maximum experience factor (long)
     */
    public static long maxExperience(String booths, int[][] groups) {
        int m = booths.length();
        // prefix[i] = number of '1's in booths[0..i-1]
        int[] prefix = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            prefix[i] = prefix[i - 1] + (booths.charAt(i - 1) == '1' ? 1 : 0);
        }

        long maxExp = 0L;
        for (int[] g : groups) {
            if (g == null || g.length < 2) continue;
            int l = g[0];
            int r = g[1];

            // Basic sanitization: swap if l > r and clamp to [1..m]
            if (l > r) {
                int t = l; l = r; r = t;
            }
            if (l < 1) l = 1; // If the given range starts before the first booth
            if (r > m) r = m; // If the range extends past the last booth (e.g., (2,10) when m=5)
            if (l > r) continue; // empty after clamp

            int ones = prefix[r] - prefix[l - 1];
            int len = r - l + 1;
            int zeros = len - ones;

            long exp = (long) ones * (long) zeros;
            if (exp > maxExp) maxExp = exp;
        }

        return maxExp;
    }

}

/*
m booths are setup and n groups of people would be visiting these booths. 
The booths will display robots (type 0) and drones (type 1). So in binary 
format the booth string will look in 0's and 1's format. Each group will 
visit the booths from row i to j'th position only. There is an experience 
factor which is calculated as no of drone booths visited * no of robots booths 
visited. You need to maximize the experience factor.
 
 
Problem (restated)
We have a binary string s of length m (0=robot, 1=drone).
There are n groups, each group gives a range [i, j] (inclusive) of booths they’ll visit.
The group’s experience factor = (number of drones in [i..j]) * (number of robots in [i..j]).
For each group we can compute its factor; we may also want the maximum across all groups.

Problem Restatement (in simpler terms):

You have m booths in a row. Each booth is either:
Robot booth → 0
Drone booth → 1

So the entire row can be represented as a binary string of length m.

There are n groups of people visiting.
Each group only visits a subrange of booths, from index i to j (inclusive).

For each group, the experience factor is calculated as:

experience factor=(# of drones in range) * (# of robots in range)

Your task: maximize the experience factor.
That means find the subrange [i..j] (not necessarily one of the groups? 
or from their allowed ranges?) that gives the maximum product.
 * */
