package ZScaler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LargestNumberAfterDigitSwapsByParity_2231_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Time: O(n log n), 
	 * 			- Inserting n elements into a PriorityQueue takes O(n log n) time.
				- Removing n elements from a PriorityQueue also takes O(n log n) time.
				- The other operations (separating digits, building the result) are linear, O(n).
	 * 
	 * Space: O(n), where n is the number of digits in the input number. 
	 * 				We use additional space to store the digits in separate 
	 * 				PriorityQueue and to build the result string.

	 * */

	public static int largestInteger(int num) {
		// Convert the number to a string for easy digit manipulation
		String numStr = String.valueOf(num);
		int n = numStr.length();

		// Create max heaps (PriorityQueues) for odd and even digits
		PriorityQueue<Integer> oddDigits = new PriorityQueue<>((a, b) -> b - a);
		PriorityQueue<Integer> evenDigits = new PriorityQueue<>((a, b) -> b - a);

		// Separate odd and even digits
		for (char c : numStr.toCharArray()) {
			int digit = c - '0';
			if (digit % 2 == 0) {
				evenDigits.offer(digit);
			} else {
				oddDigits.offer(digit);
			}
		}

		// Build the result by placing the largest digits in their respective positions
		StringBuilder result = new StringBuilder();

		for (char c : numStr.toCharArray()) {
			int digit = c - '0';
			if (digit % 2 == 0) {
				result.append(evenDigits.poll());
			} else {
				result.append(oddDigits.poll());
			}
		}

		// Convert the result back to an integer and return
		return Integer.parseInt(result.toString());
	}

	
	// Using List
	/*
	 * Time: O(n log n), This is due to the sorting of odd and even digits. 
	 * 		Separating digits, building the result) are linear, O(n).
	 * 
	 * Space: O(n), where n is the number of digits in the input number. 
	 * 				We use additional space to store the digits in separate 
	 * 				lists and to build the result string.

	 * */
	public int largestInteger2(int num) {
        // Convert the number to a string for easy digit manipulation
        String numStr = String.valueOf(num);
        int n = numStr.length();
        
        // Create lists to store odd and even digits
        List<Integer> oddDigits = new ArrayList<>();
        List<Integer> evenDigits = new ArrayList<>();
        
        // Separate odd and even digits
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            if (digit % 2 == 0) {
                evenDigits.add(digit);
            } else {
                oddDigits.add(digit);
            }
        }
        
        // Sort the odd and even digits in descending order
        Collections.sort(oddDigits, Collections.reverseOrder());
        Collections.sort(evenDigits, Collections.reverseOrder());
        
        // Build the result by placing the largest digits in their respective positions
        StringBuilder result = new StringBuilder();
        int oddIndex = 0, evenIndex = 0;
        
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            if (digit % 2 == 0) {
                result.append(evenDigits.get(evenIndex++));
            } else {
                result.append(oddDigits.get(oddIndex++));
            }
        }
        
        // Convert the result back to an integer and return
        return Integer.parseInt(result.toString());
    }
	
}
