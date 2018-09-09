package ctci.ch8.recursion.and.dp;

import java.util.ArrayList;
import java.util.Arrays;

public class PowerSetDemo {

	/**Return all sub-set of a set*/
	
	public static void main(String[] args) {

		//ArrayList<Integer> set = (ArrayList<Integer>) Arrays.asList(1,2,3);
		ArrayList<Integer> set = new ArrayList<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		ArrayList<ArrayList<Integer>> allSubSets = getSubsets(set, 0);
		
		System.out.println(">>>allSubSets :: "+allSubSets);
	}
	
	public static ArrayList<ArrayList<Integer>> getSubsets (ArrayList<Integer> set, int index) {
		
		ArrayList<ArrayList<Integer>> allSubSets = null;
		
		if(index == set.size()) {
			allSubSets = new ArrayList<ArrayList<Integer>>();
			allSubSets.add(new ArrayList<Integer>());
			return allSubSets;
		}
		else {
			allSubSets = getSubsets(set, index+1);
			int item = set.get(index);
			
			ArrayList<ArrayList<Integer>> moreSubSets = new ArrayList<ArrayList<Integer>>();
			
			for (ArrayList<Integer> allSubSet : allSubSets) {
				ArrayList<Integer> subSet = new ArrayList<Integer>();
				subSet.addAll(allSubSet);
				subSet.add(item);
				//allSubSets.add(subSet);//--we can't modify list because we are currently reading it will result ConcurrentModificationException
				moreSubSets.add(subSet);
			}
			allSubSets.addAll(moreSubSets);
		}
		return allSubSets;
	}

	
	
}
