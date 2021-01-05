package leetcode;

import java.util.Arrays;

public class PartitionToKEqualSumSubsets_698_Medium {

	public static void main(String[] args) {

		int[] nums = {4, 3, 2, 3, 5, 2, 1}; 
		int k = 4;
		System.out.println("Expected: true, Actual: " + canPartitionKSubsets(nums, k));
	}
	
    
	// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/solution/
	
	
    /*
    Time : O(k * 2^n) 
    Space : O(N), the space used by recursive calls to search in our call stack.
    
    Reference: https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/180014/Backtracking-Thinking-Process
    
    Time complexity is O(k * 2^n), at least it's an upper bound. Because it takes the inner recursion 2^n     
    time to find a good subset. Once the 1st subset is found, we go on to find the second, which would       
    take 2^n roughly (because some numbers have been marked as visited). 
    So T = 2^n + 2^n + 2^n + ... = k * 2^n.
    
    how 2^n?
         The idea is that, for each number in the inner recursion, we have two choices. (1) include it in          
         the subset OR (2) not include it in the subset. We have n such numbers. Whether to include a              
         number is independent from whether to include another number. Therefore, the number of cases for 
         events of including/excluding all numbers is: 2*2*2*..(multiply by n times)..*2 = 2^n.

         Another way to reason about it is to visualize all possible states by drawing a tree. Let 
         outgoing branches on each level be a decision for the current number. In the first level, we 
         have 2 choices for the first number. Concretely, those choices are: including the first number 
         in the current subset or not. The second level therefore has 2 nodes. For each of the nodes in 
         the second level, we have 2 similar choices. As a result, the third level has 2^2 nodes, and so 
         on. The last level must have 2^n nodes.
    */
    
	
	/*
    First we need to check some Base Cases,
        1. If K is 1, then we already have our answer
        2. If N < K, then it is not possible to divide array into subsets
        3. If sum of array is not divisible by K, then it is not possible to divide the array. We will                proceed only if k divides sum. 
    */
    public static boolean canPartitionKSubsets(int[] nums, int k) {
        
        if(nums == null || nums.length == 0 || nums.length < k || k<1) {
            return false;
        }
        
        if(k == 1) 
            return true;
        
        int sum = 0;
        // for (int num : A) sum += num;
        // sum = IntStream.of(nums).sum();  // sum using stream
        sum = Arrays.stream(nums).sum();  // sum using stream
        
        if(sum < k || sum % k != 0) return false;
        
        boolean[] visited = new boolean[nums.length];
        
        return dfs(nums, visited, 0, 0, sum/k, k); // nums, visited, startIdx, currentSum, targetSum, k
    }
    
    public static boolean dfs (int[] nums, boolean[] visited, int stIdx, int currSum, int target, int k) {
        
        if(k == 0) return true;
        
        if(currSum == target) {
            return dfs(nums, visited, 0, 0, target, k-1);
        }
        
        if(currSum > target) return false;
        
        for(int i=stIdx; i<nums.length; i++) {
            if(!visited[i]) { // not a visited element
                visited[i] = true;
                if(dfs(nums, visited, i+1, currSum+nums[i], target, k))  //DO NOT write currSum+=nums[i] 
                    return true;
                
                visited[i] = false;
            }
        }
        
        return false;
    }
    
    //Another approach - similar to above just not using the visited array
    // https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
    public static boolean dfs2(int[] nums,int start,int cur,int target,int k){
        if(k==0) {
            return true;
        }
        if(cur==target) {
            return dfs2(nums,0,0,target,k-1);
        }
        if(cur>target) { return false; }
        
        boolean flag=false;
        for(int i=start;i<nums.length;i++){
            if(nums[i]!=-1){
                int out=nums[i];
                nums[i]=-1;
                if(dfs2(nums, i+1, cur+out, target, k)) return true;
                nums[i]=out;
            }
        }
        return flag;
    } 
}
