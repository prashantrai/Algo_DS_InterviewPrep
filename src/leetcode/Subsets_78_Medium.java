package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Subsets_78_Medium {

	public static void main(String[] args) {
		int[] nums = {1,2,3};
		List<List<Integer>> res = subsets(nums);
		
		System.out.println("Expected: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]");
		System.out.println("Actual: " + res);
		
		List<List<Integer>> res2 = subsets2(nums);
		System.out.println("Expected: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]");
		System.out.println("Actual: " + res2);
	}
	
	// DFS
	// Recursive - Time: O(N 2^N)
	public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        //res.add(new ArrayList<Integer>());
        
        if(nums == null || nums.length == 0) 
            return res;
        
        helper(nums, 0, res);
        return res;
    }
    
    public static void helper(int[] nums, int idx, List<List<Integer>> res) {
        if(nums.length == idx) {
            res.add(new ArrayList<Integer>());
            return;
        }
        
        helper(nums, idx+1, res);
        
        List<List<Integer>> moreSubset = new ArrayList<>();
        for(List<Integer> list : res) {
            List<Integer> subset = new ArrayList<>();
            subset.addAll(list);
            subset.add(nums[idx]);
            moreSubset.add(subset);
        }
        res.addAll(moreSubset);
    }
 
    //Iterative - BFS
    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<Integer>());
        
        for(int num : nums) {
        	int size = res.size(); // this has to be fecthed before starting the intration on res otherwise it will go into infinite loop
        	for(int i=0; i<size; i++) {
//        		List<Integer> subset = new ArrayList<>(res.get(i));
        		List<Integer> subset = new ArrayList<>();
        		subset.addAll(res.get(i));
        		subset.add(num);
        		res.add(subset);
        	}
        }
        
        return res;
    }
}
