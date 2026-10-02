package Motive;

public class ContainerWithMostWater_11_Medium {

	public static void main(String[] args) {
		int[] height = {1,8,6,2,5,4,8,3,7};
		System.out.println("Expected: 49, Actual: " + maxArea(height));
	}

	// Time: O(N) Space: O(1)
	public static int maxArea(int[] height) {
		// Why <2? Because we need at least 2 lines to form a container
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        // Start with the widest container and shrink inward.
        while (left < right) {

            int width = right - left;
            int containerHeight = Math.min(height[left], height[right]);

            // Calculate the water held by the current pair.
            int currentArea = width * containerHeight;
            maxArea = Math.max(maxArea, currentArea);

            /*
             * Move the shorter side because it limits the current area.
             * Moving the taller side would only reduce the width while
             * keeping the same limiting height.
             */
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
