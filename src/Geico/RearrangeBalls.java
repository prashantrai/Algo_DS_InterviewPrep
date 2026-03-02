package Geico;

public class RearrangeBalls {

	// Test cases
    public static void main(String[] args) {

        // Basic case
        String[] test1 = {"b", "", "", "b"};
        printArray(rearrange(test1));

        // Already separated
        String[] test2 = {"b", "", "b", ""};
        printArray(rearrange(test2));

        // All empty
        String[] test3 = {"", "", ""};
        printArray(rearrange(test3));

        // Single ball
        String[] test4 = {"b"};
        printArray(rearrange(test4));

        // Impossible case
        String[] test5 = {"b", "b", "b", ""};
        printArray(rearrange(test5));

        // Large mixed case
        String[] test6 = {"b", "", "b", "", "", "b", "", ""};
        printArray(rearrange(test6));
    }
	
	/*
	 You are given an array representing balls and empty spaces:

		"b" → ball
		"" → empty
		
		You need to rearrange the array so that no two balls are adjacent.
		
		Example
		Input:  ["b", "", "", "b"]
		Output: ["b", "", "b", ""]
		
		Goal: Place empty spaces between balls as much as possible.
		
		
		Interview Script (What to Say)

		“First, I count how many balls exist.
		To avoid adjacency, balls must be separated by empty spaces.
		If the number of balls is more than (n+1)/2, it’s impossible.
		Otherwise, I place balls at even indices and fill the rest with empty strings.
		This ensures no two balls touch.
		The solution runs in O(n) time and uses O(n) extra space.”
		
	 * */

    // The solution runs in O(n) time and uses O(n) extra space.
    /**
     * Rearranges the array so that no two "b" are adjacent.
     *
     * @param arr Input array containing "b" and ""
     * @return Rearranged array or null if not possible
     */
    public static String[] rearrange(String[] arr) {

        int n = arr.length;

        // Step 1: Count number of balls
        int ballCount = 0;

        for (int i = 0; i < n; i++) {
            if ("b".equals(arr[i])) {
                ballCount++;
            }
        }

        // Step 2: Check if rearrangement is possible
        if (ballCount > (n + 1) / 2) {
            // Not possible to separate balls
            return null;
        }

        // Step 3: Create result array filled with ""
        String[] result = new String[n];

        for (int i = 0; i < n; i++) {
            result[i] = "";
        }

        // Step 4: Place balls at even indices
        int index = 0;

        while (ballCount > 0) {

            // Place "b" at current even position
            result[index] = "b";

            ballCount--;

            // Move to next even position
            index = index + 2;
        }

        return result;
    }

    // Helper method to print array
    private static void printArray(String[] arr) {

        if (arr == null) {
            System.out.println("Not possible");
            return;
        }

        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {

            System.out.print("\"" + arr[i] + "\"");

            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
	
}
