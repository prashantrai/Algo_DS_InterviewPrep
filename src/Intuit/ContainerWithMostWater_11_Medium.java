package Intuit;

public class ContainerWithMostWater_11_Medium {

	public static void main(String[] args) {
		int[] height = {1,8,6,2,5,4,8,3,7};
		System.out.println("Expected: 49, Actual: " + maxArea(height));
	}

	// Time: O(N) Space: O(1)
    public static int maxArea(int[] height) {
        
        //1. initialize max to zero
        int max = 0;
        //2. initialize 2 pointers left with 0 and right with lenght of array
        int left = 0, right = height.length-1;
        
        //3. while left < right loop
        while(left < right) {
            //4. Calculate current area
            int currentArea = Math.min(height[right], height[left]) * (right - left);
            max = Math.max(max, currentArea);
            //5. Move pointer
            if(height[left] < height[right]) 
                left++;
            else 
                right--;
        }
        //6. return the max area
        return max;
    }
}
