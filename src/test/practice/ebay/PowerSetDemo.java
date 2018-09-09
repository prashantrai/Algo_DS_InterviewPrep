package test.practice.ebay;

import java.util.ArrayList;

public class PowerSetDemo {

	public static void main(String[] args) {

		//ArrayList<Integer> set = (ArrayList<Integer>) Arrays.asList(1,2,3);
		ArrayList<Integer> set = new ArrayList<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		ArrayList<ArrayList<Integer>> allSubSets = getSubsets(set, 0);
		ArrayList<ArrayList<Integer>> allSubSets2 = getSubsets_2(set, 0);
		ArrayList<ArrayList<Integer>> allSubSets3 = getSubsets_3(set, 0);
		ArrayList<ArrayList<Integer>> allSubSets4 = getSubsets_4(set, 0);
		
		System.out.println(">>>allSubSets :: "+allSubSets);
		System.out.println(">>>allSubSets2 :: "+allSubSets2);
		System.out.println(">>>allSubSets3 :: "+allSubSets3);
		System.out.println(">>>allSubSets4 :: "+allSubSets4);
	}

	public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> allSubSets = null;
		
		if(index == set.size()) {
			allSubSets = new ArrayList<ArrayList<Integer>>();
			allSubSets.add(new ArrayList<Integer>());
			return allSubSets;
		}
		
		allSubSets = getSubsets(set, index+1);
		ArrayList<ArrayList<Integer>> moreSubSets = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> subSet : allSubSets) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			int item = set.get(index);
			list.addAll(subSet);
			list.add(item);
			moreSubSets.add(list);
		}
		allSubSets.addAll(moreSubSets);
		
		return allSubSets;
		
	}
	
	
	public static ArrayList<ArrayList<Integer>> getSubsets_2(ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> allSubsets;
		
		//--base case
		if(set.size() == index) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			allSubsets = new ArrayList<ArrayList<Integer>>();
			allSubsets.add(list);
			return allSubsets;
		}
		
		allSubsets = getSubsets_2(set, index+1);
		int val = set.get(index);
		
		ArrayList<ArrayList<Integer>> moreSubsets = new ArrayList<ArrayList<Integer>>();
		
		for (ArrayList<Integer> subSet : allSubsets) {  
			
			/*
				{}
				{}{1}
				{}{1}{2}{1,2}	
			 * 
			 * */
			//moreSubsets.add(subSet);
			ArrayList<Integer> set2 = new ArrayList<Integer>();
			set2.add(val);
			set2.addAll(subSet);
			
			moreSubsets.add(set2);
			
		}
		
		allSubsets.addAll(moreSubsets);
		
		return allSubsets;
	}


	public static ArrayList<ArrayList<Integer>> getSubsets_3 (ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> allSets = new ArrayList<ArrayList<Integer>>();
		
		if(index == set.size()) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			allSets.add(list);
			return allSets;
		}
		
		allSets = getSubsets_3 (set, index+1);
		int curr = set.get(index);
		ArrayList<ArrayList<Integer>> moreSets = new ArrayList<ArrayList<Integer>>();
		
		for (ArrayList<Integer> sets : allSets) {
			
			ArrayList<Integer> set2 = new ArrayList<Integer>();
			set2.add(curr);
			set2.addAll(sets);
			moreSets.add(set2);
		}
		allSets.addAll(moreSets);
		return allSets;
		
	}
	
	public static ArrayList<ArrayList<Integer>> getSubsets_4 (ArrayList<Integer> set, int index) {
		
		/*
		{}
		{}{1}
		{}{1}{2}{1,2}	
	 * 
	 * */
		
		ArrayList<ArrayList<Integer>> allSubset = new ArrayList<ArrayList<Integer>>();
		
		if(index == set.size()) {
			
			allSubset.add(new ArrayList<Integer>());
			return allSubset;
		}
		
		allSubset = getSubsets_4(set, index+1);
		
		ArrayList<ArrayList<Integer>> moreSubset = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> subset = null;
		
		for(ArrayList<Integer> list : allSubset) {
			subset = new ArrayList<Integer>();
			subset.addAll(list);
			subset.add(set.get(index));
			moreSubset.add(list);
			moreSubset.add(subset);
		}
		
		return moreSubset;
	}
	
}
