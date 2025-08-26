package LinkedIn;
public class MaxConsecutiveOnes_III_1004_Medium {
	
	// Test cases
    public static void main(String[] args) {
        System.out.println(longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2)); // 6
        System.out.println(longestOnes(new int[]{0,0,1,1,1,0,0}, 0));         // 3
        System.out.println(longestOnes(new int[]{1,1,1,1}, 0));               // 4
        System.out.println(longestOnes(new int[]{0,0,0,0}, 2));               // 2
        System.out.println(longestOnes(new int[]{}, 1));                      // 0 (edge case: empty array)
        
        // Follow-up: Circluar array
        System.out.println("\n---- Circular Array ---\n");
        System.out.println(longestOnesCircular(new int[]{1,0,1}, 1));       // 3 (wrap)
        System.out.println(longestOnesCircular(new int[]{1,1,0,0,1}, 1));   // 4 (wrap)
        System.out.println(longestOnesCircular(new int[]{1,1,1}, 0));       // 3 (all ones)
        System.out.println(longestOnesCircular(new int[]{0,0,0}, 2));       // 2 (flip any two)
        System.out.println(longestOnesCircular(new int[]{}, 3));            // 0 (empty)
    }
	
    /* 
    Approach: Sliding Window
	- Initialize pointers: Use left and right pointers to represent the current window.
	- Expand window: Move right pointer to include new elements.
	- Count zeros: For each 0 encountered, increment a zero count.
	- Shrink window: If zero count exceeds k, move left pointer to reduce zeros in window.
	- Track maximum: Keep track of the maximum window size seen so far.
    
    Time and Space Complexity
        Time: O(n) — each element is visited at 
        most twice (once by right, once by left).

    Space: O(1) — constant extra space.
    */
    public static int longestOnes (int[] nums, int k) {
        int left = 0;
        int maxCount = 0;
        int zeroCount = 0;

        for(int right = 0; right < nums.length; right++) {
            // If current element is 0, increment zeroCount
            if(nums[right] == 0) {
                zeroCount++;
            }

            // If zeroCount exceeds k, move left pointer to right
            // until zeroCount is back to <= k
            while (zeroCount > k) {
                if(nums[left] == 0) {
                    zeroCount--;
                }    
                left++;
            }

            // Update maxLength if current window is larger
            maxCount = Math.max(maxCount, right - left + 1);
        }

        return maxCount;
    }
    
    /*  - Now, the array is circular, meaning: The end connects back to the start.
		- A consecutive sequence can wrap around the array.
     *  
     *  The trick for circular problems:
		- Simulate circularity by duplicating the array (concatenate it to itself).
		- Run the same sliding window algorithm on the doubled array.
		- But make sure window size never exceeds the original array length — 
		  because even though we doubled it, the real array only has that many distinct elements.
     * 
	 *  How we handle circular arrays (quick recap)
		- Simulate circularity by Duplicating the array: 
			extended = nums + nums.
		- Run the same sliding window on doubled array.
		- Invariant A: keep zeroCount ≤ k.
		- Invariant B (circular cap): keep window length ≤ n (the original array length).
		- Track the max window length seen.
		
     *  Step-by-Step Algorithm
		- Let n = nums.length.
		- Create extended = nums + nums (size 2n).
		- Use the same sliding window technique:
		- Track zeroCount and shrink window when zeroCount > k.
		- Ensure window length ≤ n (since that's the longest possible in a circular array).
		- Return the maximum valid window length found.
		
	
	Time: O(n) — scanning at most 2n elements with sliding window.
	Space: O(n) — for extended array.
     * */
    
    // Follow-up: Handling Circular Array for Max Consecutive Ones III
    // Extension of above solution, new lines have comment "Follow-up"
    public static int longestOnesCircular(int[] nums, int k) {
    	int n = nums.length;
    	int[] extended = new int[n * 2]; // Follow-up: simulate circular by doubling
    	System.arraycopy(nums, 0, extended, 0, n); // Follow-up
    	System.arraycopy(nums, 0, extended, n, n); // Follow-up
    	
    	int left = 0; 
    	int zeroCount = 0; 
    	int maxCount = 0;
    	for(int right=0; right < 2*n; right++) {	// Follow-up
    		if(extended[right] == 0) 
    			zeroCount++;
    		
    		// Follow-up: keep both invariants — at most 
    		// k zeros, and window size ≤ n
    		if(zeroCount > k || (right - left + 1) > n) {
    			if(extended[left] == 0) 
    				zeroCount--;
    			
    			left++;
    		}
    		maxCount = Math.max(maxCount, right - left + 1);
    	}
    	
    	return maxCount;
    }
    
    
    
    
    
	/* Another approach: without follow-up
	 * 
	 * Follow-up (not solved): What if the array is circular array (i.e. wrapped around)
	 * 
	 * Time: O(n) — each element is visited at 
        most twice (once by right, once by left).

       Space: O(1) — constant extra space.
    */
    public int longestOnes2(int[] nums, int k) {
        int left = 0;  // Left boundary of window

        for (int right = 0; right < nums.length; right++) {
            // If current num is 0, we consider flipping it, so decrease k
            if (nums[right] == 0) {
                k--;
            }

            // If k is negative, we’ve flipped too many 0s
            // Move the left pointer to make it valid again
            if (k < 0) {
                if (nums[left] == 0) {
                    k++; // "unflip" the 0 at left
                }
                left++; // shrink window from left
            }
        }

        // right is at the end of valid window, left is start
        return nums.length - left;
    }
    
}


/*
 Let’s say we’re only allowed to flip k = 2 zeros.

Suppose our array is:
[1, 1, 0, 0, 1, 1, 0, 1]

As we go right:

At right = 0, 1 → nums[right] = 1, no problem.

At right = 2 → nums[2] = 0 → flip it (zeroCount = 1).

At right = 3 → nums[3] = 0 → flip it (zeroCount = 2).

At right = 4 → nums[4] = 1 → continue, still only 2 zeros flipped.

At right = 5 → nums[5] = 1 → continue.

Window: [0 to 5] → [1, 1, 0, 0, 1, 1]
zeroCount = 2 → valid (within k)
window size = 6
At right = 6 → nums[6] = 0 → another zero!
Now zeroCount = 3 > k → invalid window!

So now, we move left forward to try and exclude one of the previously flipped zeros:

Move left = 0 → nums[0] = 1 → just skip it

Move left = 1 → nums[1] = 1 → skip

Move left = 2 → nums[2] = 0 → we "unflip" this, so zeroCount = 2 again

Now window is valid again (only 2 flipped zeros), from left = 3 to right = 6.

That’s how the sliding window ensures we always track the longest possible window that uses at most k flips.
  
  
| Step | `right` | nums\[right] | Zero Count | Action                         | `left` | Window Length |
| ---- | ------- | ------------ | ---------- | ------------------------------ | ------ | ------------- |
| 0    | 0       | 1            | 0          | Expand window                  | 0      | 1             |
| 1    | 1       | 1            | 0          | Expand window                  | 0      | 2             |
| 2    | 2       | 1            | 0          | Expand window                  | 0      | 3             |
| 3    | 3       | 0            | 1          | Flip 0                         | 0      | 4             |
| 4    | 4       | 0            | 2          | Flip 0                         | 0      | 5             |
| 5    | 5       | 0            | 3          | Exceeds k, move left to shrink | 1→2→3  | 3             |
| 6    | 6       | 1            | 2          | Expand valid window            | 3      | 4             |
| 7    | 7       | 1            | 2          | Expand valid window            | 3      | 5             |
| 8    | 8       | 1            | 2          | Expand valid window            | 3      | 6             |
| 9    | 9       | 1            | 2          | Expand valid window            | 3      | 7 ✅ max      |
| 10   | 10      | 0            | 3          | Exceeds k, move left to shrink | 4→5→6  | 5             |

  
 * */
 