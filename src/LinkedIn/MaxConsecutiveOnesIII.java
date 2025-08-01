package LinkedIn;
public class MaxConsecutiveOnesIII {
	
	// Test cases
    public static void main(String[] args) {
        MaxConsecutiveOnesIII sol = new MaxConsecutiveOnesIII();
        
        System.out.println(sol.longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2)); // 6
        System.out.println(sol.longestOnes(new int[]{0,0,1,1,1,0,0}, 0));         // 3
        System.out.println(sol.longestOnes(new int[]{1,1,1,1}, 0));               // 4
        System.out.println(sol.longestOnes(new int[]{0,0,0,0}, 2));               // 2
        System.out.println(sol.longestOnes(new int[]{}, 1));                      // 0 (edge case: empty array)
    }
	
	/* Time: O(n) — each element is visited at 
        most twice (once by right, once by left).

       Space: O(1) — constant extra space.
    */
    public int longestOnes(int[] nums, int k) {
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

Now:

pgsql
Copy code
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
 