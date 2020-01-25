package Airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * https://github.com/allaboutjst/airbnb/blob/master/src/main/java/menu_combination_sum/MenuCombinationSum.java
 * 
 * https://leetcode.com/discuss/interview-question/algorithms/124782/airbnb-phone-screen
 * https://xkcd.com/287/
 * https://leetcode.com/problems/combination-sum/description/
 * https://leetcode.com/problems/combination-sum/discuss/470555/Accepted-2ms-Java-solution-(backtrack)-beating-99.85
 */

public class CombinationSum {
	
	public static void main(String[] args) {
		
		int[] candidates = {2,3,6,7};
		int target = 7;
		
		//int[] candidates = {1002, 111, 222, 301, 402, 200, 503};
		//int target = 703;
		
		List<List<Integer>> res = combinationSum(candidates, target);
		System.out.println(res);
		
		double[] prices = {10.02, 1.11, 2.22, 3.01, 4.02, 2.00, 5.03};
        List<List<Double>> combos = getCombos(prices, 7.03);
        System.out.println(combos);

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
	
	public static List<List<Double>> getCombos(double[] prices, double target) {
        List<List<Double>> res = new ArrayList<>();
        if (prices == null || prices.length == 0 || target <= 0) {
            return res;
        }

        int centsTarget = (int) Math.round(target * 100);
        Arrays.sort(prices);
        int[] centsPrices = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            centsPrices[i] = (int) Math.round(prices[i] * 100);
        }

        search(res, centsPrices, 0, centsTarget, new ArrayList<>(), prices);
        return res;
    }
	
	private static void search(List<List<Double>> res, int[] centsPrices, int start, int centsTarget,
            List<Double> curCombo, double[] prices) {
		if (centsTarget == 0) {
			res.add(new ArrayList<>(curCombo));
			return;
		}

		for (int i = start; i < centsPrices.length; i++) {
			if (i > start && centsPrices[i] == centsPrices[i - 1]) {
				continue;
			}
			if (centsPrices[i] > centsTarget) {
				break;
			}
			curCombo.add(prices[i]);
			search(res, centsPrices, i + 1, centsTarget - centsPrices[i], curCombo, prices);
			curCombo.remove(curCombo.size() - 1);
		}
	}
}