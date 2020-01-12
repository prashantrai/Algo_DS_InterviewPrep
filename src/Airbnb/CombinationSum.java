package Airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
	
	public static void main(String[] args) {
		
		int[] candidates = {2,3,6,7};
		int target = 7;
		
		List<List<Integer>> res = combinationSum(candidates, target);
		System.out.println(res);
	}

	public static List<List<Integer>> combinationSum(int[] candidates, int target) {

		if (candidates == null || candidates.length == 0)
			return null;

		List<List<Integer>> res = new ArrayList<>();
		List<Integer> track = new ArrayList<>();
		Arrays.sort(candidates);
		helper(res, candidates, target, track, 0);

		return res;
	}

	public static void helper(List<List<Integer>> res, int[] candidates, int target, List<Integer> track, int idx) {
		
		if(target < 0) 
			return;
		if(target == 0) {
			res.add(new ArrayList<Integer>(track));
			return;
		}
		
		for(int i=idx; i<candidates.length; i++) {
			if(target < candidates[idx]) {
				return;
			}
			
			track.add(candidates[i]);
			helper(res, candidates, target - candidates[i], track, i);
			track.remove(track.size()-1);
		}
		
	}
}