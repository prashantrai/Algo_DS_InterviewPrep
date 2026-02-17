package SoFi;

public class JumpGame2_45_Hard {

	// https://leetcode.com/problems/jump-game-ii/
	// https://leetcode.com/problems/jump-game-ii/discuss/495895/1-ms-100-fast-Very-Easy-Logic-Most-optimal-and-Easiest-Solution
	// https://www.programcreek.com/2014/06/leetcode-jump-game-ii-java/
	public static void main(String[] args) {
		
		int[] nums = {2,3,1,1,4};
		System.out.println("1. Expected: 2, Actual: " +jump_BFS(nums));
		System.out.println("2. Expected: 2, Actual: " +jump_DP(nums));
		System.out.println("3. Expected: 2, Actual: " +jump1(nums));
		System.out.println("4. Expected: 2, Actual: " +jump2(nums));

	}
	
	/*
	 * Below URL for DP and BFS solutions: 
	 * https://leetcode.com/problems/jump-game-ii/discuss/507246/Java%3A-2-Methods-DP-and-BFS-both-with-Time-%3AO(n)-Space%3A-O(1)
	 */
	
	public static int jump_BFS(int[] nums) {
		if (nums.length == 1)
			return 0;
		
		int maxStep = 0;
		int minVal = 0;
		int lastMax = 0;
		int i=0;
		
		while(lastMax - i+1 > 0) {
			
			minVal++;
			for(;i<=lastMax; i++) {
				maxStep = Math.max(maxStep, i + nums[i]);
				if(maxStep >= nums.length - 1) 
					return minVal;
			}
			
			lastMax = maxStep;
			
		}
		return 0;		
		
	}
	
	//-- Greedy approach with range boundaries: [My submission]: https://leetcode.com/problems/jump-game-ii/submissions/
	//-- Runtime: O(n) Space: O(1)
	/*
		Think of It Like Floors in a Building
		Imagine you're on Floor 0 (index 0), and you want to reach the top floor.
		Current range = which floors you can reach with the current number of jumps. * */
	public int jump(int[] nums) {
        
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        
        /*
        Why Loop Until nums.length - 1?
		You don't need to process the last index because:
		Once you can reach the last index (or beyond), you're done
		You never need to jump FROM the last index
		*/
        for(int i=0; i<nums.length-1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // When i == currentEnd = you've reached the boundary, 
            // you've checked everything, time to jump!
            if(i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                
            }
        }
        return jumps;
    }

	
	
    //--// https://leetcode.com/problems/jump-game-ii/discuss/495895/1-ms-100-fast-Very-Easy-Logic-Most-optimal-and-Easiest-Solution
	public static int jump1(int[] nums) {
		if (nums.length == 1)
			return 0;

		int currEnd = 0; 
		int currfar = 0; 
		int jump = 0;

		for (int i = 0; i < nums.length - 1; i++) {  // we need to iterate till second last index
			/*
			 * Calculate the max of how far we can got by adding current index (i.e. i) 
			 * and value at that index and store the max value
			 */
			currfar = Math.max(currfar, i + nums[i]);  
			
			if (i == currEnd) {
				if (currfar <= i)
					return 0;
				jump++;
				currEnd = currfar;
			}

		}
		return jump;
	}
	
	
	//--https://www.programcreek.com/2014/06/leetcode-jump-game-ii-java/
	public static int jump2(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;
	 
		int lastReach = 0;
		int reach = 0;
		int step = 0;
	 
		for (int i = 0; i <= reach && i < nums.length; i++) {
			//when last jump can not read current i, increase the step by 1
			if (i > lastReach) {
				step++;
				lastReach = reach;
			}
			//update the maximal jump 
			reach = Math.max(reach, nums[i] + i);
		}
	 
		if (reach < nums.length - 1)
			return 0;
	 
		return step;
	}
}
