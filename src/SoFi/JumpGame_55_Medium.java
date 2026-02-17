package SoFi;

public class JumpGame_55_Medium {

	public static void main(String[] args) {

		int[] arr1 = {2,3,1,1,4};
		System.out.println(canJump(arr1));
		
		int[] arr2 = {3,2,1,0,4};
		System.out.println(canJump(arr2));
		
	}
	
	/*
	 * OTHER SIMILAR PROB:
	 * Medium: https://leetcode.com/problems/jump-game-iii/
	 * Hard: https://leetcode.com/problems/jump-game-ii/
	 * 
	 * 
	 * Time: O(N)
	 * Space: O(1)
	 * */
	
	// start from last position and travel backward till we reach 0th index
	public static boolean canJump(int[] nums) {
        int lastPos = nums.length-1; 
        
        for(int i=nums.length-1; i>=0; i-- ) {
            
        	int nextPos = i + nums[i];
            if(nextPos >= lastPos) {
                lastPos = i;
            }
        }
        return lastPos == 0;
    }

}
