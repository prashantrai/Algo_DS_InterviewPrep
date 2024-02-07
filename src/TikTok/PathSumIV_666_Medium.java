package TikTok;

import java.util.HashMap;
import java.util.Map;

public class PathSumIV_666_Medium {

	public static void main(String[] args) {
		int[] nums = {113,215,221};
		
		System.out.println("Expected: 12, Actual: " + pathSum(nums));
	}

	/* 
	reference: 
		https://leetcode.com/problems/path-sum-iv/discuss/106892/Java-solution-Represent-tree-using-HashMap
    
    
    Now each tree node is represented by a number. 1st digits is the level, 
    2nd is the position in that level (note that it starts from 1 instead of 0). 
    3rd digit is the value. We need to find a way to traverse this tree and get the sum.

	The idea is, we can form a tree using a HashMap. 
		>The KEY is first two digits which marks the position of a node in the tree. 
		>The VALUE is value of that node. Thus, we can easily find a node's left and right 
		children using math.
		
	Formula: For node xy? its left child is (x+1)(y*2-1)? and right child is (x+1)(y*2)?
    
    Time: O(N)
    Space: O(N)
    */
    
    static int sum;
    static Map<Integer, Integer> map = new HashMap<>();
    
    public static int pathSum(int[] nums) {
        if(nums == null || nums.length == 0) 
            return 0;    
        
        for(int n : nums) {
            map.put(n/10, n%10);
        }
        
        traverse(nums[0]/10, 0);
        
        return sum;
    }
    
    private static void traverse(int root, int preSum) {
        int parentLevel = root/10; // gives the node level (depth)
        int pos = root % 10; // node position at a level
        
        /*
        Example Calculation: 
        for 215: 
            leftNode = (1+1) * 10 + 1 * 2 - 1 =>  20 + 2 - 1 => 21
        
        for 221: 
            rightNode = (1+1) * 10 + 1 * 2 =>  20 +2 => 22
        */
        
        int leftNode = (parentLevel + 1) * 10 + pos * 2 - 1;
        int rightNode = (parentLevel + 1) * 10 + pos * 2;
        
        int curSum = preSum + map.get(root);
        
        if(!map.containsKey(leftNode) && !map.containsKey(rightNode)) {
            sum  += curSum;
            return;
        }
        
        // call recurively
        if(map.containsKey(leftNode)) 
            traverse(leftNode, curSum);
        
        if(map.containsKey(rightNode)) 
            traverse(rightNode, curSum);
        
    }
	
}
