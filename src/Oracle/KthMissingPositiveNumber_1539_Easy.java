package Oracle;

public class KthMissingPositiveNumber_1539_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* 
    Interview Script
    Before coding, explain to the interviewer:

    "This problem asks us to find the k-th missing positive integer from a sorted array. 
    
    Let me think through the approach.

    First, I notice that since the array is sorted, we can leverage this property. 
    The key insight is understanding how many numbers are missing before any given 
    position in the array.

    For any element at index i, the expected value if no numbers were missing would 
    be i + 1 (since we start counting from 1). So the actual number of missing elements 
    before arr[i] is arr[i] - (i + 1) = arr[i] - i - 1.

    Since we need the k-th missing number and the missing count increases as we move 
    right in the array, this creates a monotonic relationship perfect for binary search. 
    We can find the position where exactly k missing numbers occur.

    The binary search will help us find the smallest index where the missing count 
    is >= k. Once we find this boundary, we can calculate the exact k-th missing number."
    */

    /**
    * Finds the k-th missing positive integer from a sorted array.
    * 
    * Approach: Binary Search
    * - For any index i, the number of missing positive integers before arr[i] 
    *   is calculated as: arr[i] - i - 1
    * - We use binary search to find the position where we have exactly k missing numbers
    * - The answer will be: k + left (where left is the final search boundary)
    * 
    * Time Complexity: O(log n) where n is the length of the array
    * Space Complexity: O(1) - only using constant extra space
    */
    public int findKthPositive(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        
        // Binary search to find the boundary
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Calculate missing numbers before arr[mid]
            int missingCount = arr[mid] - mid - 1;
            
            if (missingCount < k) {
                // Need more missing numbers, search right half
                left = mid + 1;
            } else {
                // Too many missing numbers, search left half
                right = mid - 1;
            }
        }
        
        // At this point, left = right + 1
        // The k-th missing number is: k + left
        return k + left;
    }

}
