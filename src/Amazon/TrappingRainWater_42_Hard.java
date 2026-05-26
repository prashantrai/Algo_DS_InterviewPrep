package Amazon;

public class TrappingRainWater_42_Hard {

	public static void main(String[] args) {

        int[][] tests = {
            {},                           // 0: empty
            {5},                          // 1: single bar
            {2, 3},                       // 2: two bars
            {1, 2, 3, 4},                 // 3: increasing
            {4, 3, 2, 1},                 // 4: decreasing
            {3, 3, 3, 3},                 // 5: flat
            {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, // 6: classic
            {4, 2, 0, 3, 2, 5},           // 7: multiple basins
            {5, 0, 0, 0, 5},              // 8: wide flat bottom
            {2, 0, 2},                    // 9: simple valley
            {3, 0, 1, 3, 0, 5}            // 10: irregular
        };

        int[] expected = {
            0,  // {}
            0,  // {5}
            0,  // {2, 3}
            0,  // {1, 2, 3, 4}
            0,  // {4, 3, 2, 1}
            0,  // {3, 3, 3, 3}
            6,  // classic
            9,  // multiple basins
            15, // wide flat bottom
            2,  // simple valley
            8   // irregular
        };

        for (int i = 0; i < tests.length; i++) {
            int result = trap(tests[i]);
            System.out.println("Test " + i +
                    " -> result: " + result +	
                    ", expected: " + expected[i] +
                    " -> " + (result == expected[i] ? "PASS" : "FAIL"));
        }
    }
	
	
	/*
    Naive way:
    For every index, scan left and right to find maxima → O(n²).

    Better way (two pointers, O(n), O(1) space):

    Use two indices:
    - left starting at 0, right starting at n-1.
    - Track leftMax = highest bar seen so far from the left.
    - Track rightMax = highest bar seen so far from the right.
    
    Key observation:
    - The side with the smaller current bar limits the water on that side.
    - So:
        -- If height[left] < height[right]:
        -- leftMax is the limiting factor for position left.
        -- If height[left] >= leftMax, update leftMax.
        -- Else, trapped water at left = leftMax - height[left].
        -- Move left++.
    - Else (right side smaller or equal):
        -- Symmetric logic with right and rightMax.
        -- Move right--.
    
    Sum all trapped water while moving the pointers toward each other.
    
    Time: O(n) — each index visited at most once.
    Space: O(1) — only a few variables.
    */
    
	public static int trap(int[] height) {

        // Edge case: less than 3 bars -> no water can be trapped
        if(height == null || height.length < 3) {
            return 0;
        }

        int len = height.length;
        int left = 0;        // pointer from the left
        int right = len - 1; // pointer from the right
        int leftMax = 0;     // max height seen so far from the left
        int rightMax = 0;    // max height seen so far from the right
        int water = 0;       // total trapped water

        // Process until the two pointers meet
        while(left < right) {
            // Decide which side is the limiting side
            if (height[left] < height[right]) {
                // Left side is lower -> water level here is limited by leftMax
                if(height[left] > leftMax) {
                    // Update leftMax if current bar is higher
                    leftMax = height[left];
                } else {
                    // leftMax is higher -> water can be trapped
                    water += leftMax - height[left];
                }
                left++; // move towards center
            } else {
                // Right side is lower or equal -> handle symmetric case
                if(rightMax < height[right]) {
                    // Update rightMax if current bar is higher
                    rightMax = height[right];
                } else {
                    // rightMax is higher -> water can be trapped
                    water += rightMax - height[right];
                }
                right--;    // move towards center
            }              
        }

        return water;
    }


}


/*
Short Interview Script (You Explaining to Interviewer)
You: “The problem is to compute how much water can be trapped between bars after raining.”
Interviewer: “How would you model water at an index?”
You: “Water at index i is min(maxLeft, maxRight) - height[i] if positive.”
Interviewer: “Naive complexity?”
You: “If for each index I scan left and right, it’s O(n²).”
Interviewer: “Can you do better?”
You: “Yes, using two pointers and tracking the maximums from both ends in O(n) time and O(1) space.”
Interviewer: “Explain the two-pointer idea.”
You: “Keep left at 0, right at n-1, leftMax, rightMax. At each step, I move the side with the smaller height, because the water level on that side is limited by that smaller side.”
Interviewer: “Why is that correct?”
You: “If height[left] < height[right], then the right side is at least as tall, so the minimum of both sides is determined by the left side’s max. Thus, water at left depends only on leftMax, not rightMax. Symmetric for the right side.”
Interviewer: “Complexity?”
You: “O(n) time, O(1) extra space.”
 
 * */
