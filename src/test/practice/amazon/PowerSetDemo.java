package test.practice.amazon;

import java.util.ArrayList;

public class PowerSetDemo {
	
	public static void main(String[] args) {

		ArrayList<Integer> set = new ArrayList<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		ArrayList<ArrayList<Integer>> allSubSets = getSubsets(set, 0);
		
		System.out.println(">>>allSubSets :: "+allSubSets);
	}
	
	
	public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> allSubsets;
		
		if(index == set.size()) {
			allSubsets = new ArrayList<ArrayList<Integer>>();
			allSubsets.add(new ArrayList<Integer>());
			return allSubsets;
			
		} else {
			allSubsets = getSubsets(set, index+1);
			int item = set.get(index);
			
			ArrayList<ArrayList<Integer>> moreSubset = new ArrayList<ArrayList<Integer>>();
			
			for(ArrayList<Integer> allSubset : allSubsets) {
				ArrayList<Integer> subset = new ArrayList<Integer>();
				subset.addAll(allSubset);
				subset.add(item);
				moreSubset.add(subset);
			}
			allSubsets.addAll(moreSubset);
		}
		
		return allSubsets;
	}
	

}
