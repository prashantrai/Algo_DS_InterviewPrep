package test.math;

import java.util.ArrayList;

public class PowerSetDemo {

	/* Subsets of a set
	 * Exmple: 
	 * 
	 * Input: {1,2,3}
	 * Output: [{}, {1}, {2}, {3}, {1,2}, {2,3}, {1,2,3}]  //-- 2^n combination where n=3 number of elements
	 * 
	 * */
	
	public static void main(String[] args) {
		ArrayList<Integer> set = new ArrayList<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		subSets(set);
		
	}
	
	public static void subSets(ArrayList<Integer> set) {
		
		ArrayList<ArrayList<Integer>> sets = new ArrayList<ArrayList<Integer>>();
		subSets(set, 0);
		
		System.out.println(subSets(set, 0));
		
	}

	private static ArrayList<ArrayList<Integer>> subSets(ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> subSetsList = null;
		
		if(index == set.size()) {
			subSetsList = new ArrayList<ArrayList<Integer>> ();
			subSetsList.add(new ArrayList<Integer>());
			
			return subSetsList;
		}
		
		ArrayList<ArrayList<Integer>>moreSubSets = new ArrayList<ArrayList<Integer>> ();
		
		subSetsList = subSets(set, index+1);  
		
		for (ArrayList<Integer> subSet : subSetsList) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.addAll(subSet);
			list.add(set.get(index));
			moreSubSets.add(list);
		}
		
		subSetsList.addAll(moreSubSets);
		
		return subSetsList;
	}

}
