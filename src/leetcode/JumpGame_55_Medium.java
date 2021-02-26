package leetcode;

public class JumpGame_55_Medium {

	public static void main(String[] args) {

		int[] arr1 = {2,3,1,1,4};
		System.out.println(canJump(arr1));
		
		int[] arr2 = {3,2,1,0,4};
		System.out.println(canJump(arr2));
		
	}
	
	//--https://leetcode.com/problems/jump-game/
	//--https://leetcode.com/problems/jump-game/solution/
	
	/*
	 * OTHER SIMILAR PROB:
	 * Medium: https://leetcode.com/problems/jump-game-iii/
	 * Hard: https://leetcode.com/problems/jump-game-ii/
	 * 
	 * 
	 * Time: O(N)
	 * Space: O(1)
	 * */
	
	public static boolean canJump(int[] nums) {
        int lastPos = nums.length-1; 
        
        for(int i=nums.length-1; i>=0; i-- ) {
            
            if( i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        return lastPos == 0;
    }

}
