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
 * How to Say This in an Interview (Your Script), When the interviewer asks why?
 * 
 you are using atMost(k) - atMost(k - 1), deliver this exact 3-step
 
 explanation:
 The Core Obstacle:"If I try to find exactly (k) odd numbers using a standard sliding window, 
 even numbers cause a problem. If my window has exactly (k) odds and I slide the right 
 pointer over an even number, my odd count stays the same, but I just formed a brand new valid subarray. 
 Tracking these extra even numbers directly makes the window shrink/expand logic highly complex and error-prone."
 
 The "At Most"
 Simplification:"To solve this cleanly, I change the question from 'exactly (k)' 
 to 'at most (k)'. A sliding window is incredibly efficient at finding 'at most (k)' 
 because the rule is simple: if our odd count goes over (k), we shrink from the left. 
 Otherwise, every sub-segment within that window is automatically valid."
 
 The Mathematical
 Bridge:"By definition, the set of subarrays with at most (k) odds includes subarrays 
 with \(0, 1, 2, \dots\) up to (k) odds. The set of subarrays with at most (k-1) odds 
 includes everything except the ones with (k) odds. Therefore, if I calculate atMost(k) 
 and subtract atMost(k - 1), the math naturally cancels out all subarrays with fewer than (k) 
 odds, leaving me with the exact count of subarrays containing exactly (k) odds."
 * 
 * 
 * Why does the code use this (countAtMost with k and k-1)?
 * 
 Trying to use a sliding window to find "exactly (k)" directly is messy
 because trailing or leading even numbers (like [2, 1, 1, 2]) don't change the
 odd count but change the subarray count, making the window logic complex. The
 atMost(k) - atMost(k - 1) formula lets us reuse a simple window algorithm
 twice to get the perfect answer.
 
 * **Example: The Interview Analogy: The Concert Ticket Scale **
 
 Imagine we are standing at the entrance of a concert arena. 
 Security is letting people inside based on how many cash bills 
 they have in their pockets. 
 
 We are tasked with counting how many people have exactly 2 cash bills. 
 Instead of checking everyone's pockets ourself, we stand next to two weight scales:
 
 Scale A: counts everyone who has 2 or fewer bills (0, 1, or 2 bills). 
 Scale B: counts everyone who has 1 or fewer bills (0 or 1 bill).
 
 If 100 people pass through Scale A, and 80 people pass through Scale B, how
 many people had exactly 2 bills?
 
 The answer is 20 (100 - 80.Why?
 
 Because the only difference between the group of people allowed through 
 Scale A and Scale B is that Scale A included the people with exactly 2 bills, 
 while Scale B stopped them at the door.
 * 
 */
	

