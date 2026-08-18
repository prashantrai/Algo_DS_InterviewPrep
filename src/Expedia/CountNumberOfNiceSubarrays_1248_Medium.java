package Expedia;

public class CountNumberOfNiceSubarrays_1248_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Time: O(N)
    // Space: O(1)
    public int numberOfSubarrays(int[] nums, int k) {
        // Exactly(k) = AtMost(k) - AtMost(k - 1)
        return countAtMost(nums, k) - countAtMost(nums, k - 1);
    }

    // Helper function to count subarrays with AT MOST 'k' odd numbers
    private int countAtMost(int[] nums, int k) {
        if (k < 0) return 0;

        int left = 0;
        int oddCount = 0;
        int subarrays = 0;

        for (int right = 0; right < nums.length; right++) {
            // If the current element is odd, increment our odd counter
            if (nums[right] % 2 != 0) {
                oddCount++;
            }

            // Shrink the window from the left if we have too many odd numbers
            while (oddCount > k) {
                if (nums[left] % 2 != 0) {
                    oddCount--;
                }
                left++;
            }

            // The size of the window represents the number of valid subarrays ending at 'right'
            subarrays += (right - left + 1);
        }

        return subarrays;
    }
}

/* 
Why does the code use this (countAtMost with k and k-1)?

Trying to use a sliding window to find "exactly (k)" directly is messy because trailing or leading even numbers (like [2, 1, 1, 2]) don't change the odd count but change the subarray count, making the window logic complex. 
The atMost(k) - atMost(k - 1) formula lets us reuse a simple window algorithm twice to get the perfect answer.
*/
	
}
