package leetcode;
public class TrappedRainWater_42_Hard {

	public static void main(String[] args) {

		int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
		//int[] height = {1, 0, 2, 1, 3};
		System.out.println(trappedWater(height));
	}

	/** 
	 * Refer Leet code article for this problem's solution (below is taken from there)
	 * 
	 * https://leetcode.com/problems/trapping-rain-water/
	 * https://leetcode.com/problems/trapping-rain-water/submissions/
	 * 
	 * Time: O(n)
	 * Space: O(1)
	 * 
	 */
	public static int trappedWater (int[] height) {
		
		if(height == null || height.length == 0) 
			return 0;
		
		int ans = 0;
		int left_max = 0;
		int right_max = 0;
		int left = 0;
		int right = height.length - 1;
		
		while (left < right) {
			
			if(height[left] < height[right]) {
				
				if(height[left] > left_max) {  //--if current height big update that as left_max and move left pointer
					left_max = height[left];  
					
				} else {
					ans += left_max - height[left];
				}
				left++;
				
			} else {
				
				if(height[right] > right_max) {
					right_max = height[right];
				
				} else {
					ans += right_max - height[right];
				} 
				right--;
				
			}
		}
		
		return ans;
	}
	
}