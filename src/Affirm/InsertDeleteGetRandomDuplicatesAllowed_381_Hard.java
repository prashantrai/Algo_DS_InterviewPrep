package Affirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class InsertDeleteGetRandomDuplicatesAllowed_381_Hard {

	public static void main(String[] args) {
		RandomizedCollection obj = new RandomizedCollection();
		
		// return true since the collection does not contain 1.
		boolean param_1 = obj.insert(1); 
		System.out.println("param_1: "+param_1);
		
		// return false since the collection contains 1.
		boolean param_2 = obj.insert(1);
		System.out.println("param_2: "+param_2);
		
		// return true since the collection does not contain 2.
		boolean param_3 = obj.insert(2);
		System.out.println("param_3: "+param_3);
		
		// getRandom should:
        // - return 1 with probability 2/3, or
        // - return 2 with probability 1/3.
		int param_4 = obj.getRandom();
		System.out.println("param_4: "+param_4);
		
		// return true since the collection contains 1.
		boolean param_5 = obj.remove(1);
		System.out.println("param_5: "+param_5);
		
		// getRandom should return 1 or 2, both equally likely.
		int param_6 = obj.getRandom();
		System.out.println("param_6: "+param_6);
		
		
	}
	
	/* 
	 * Time complexity : O(N), with N being the number of operations. 
	 * 		All of our operations are O(1), giving N * O(1) = O(N).
	 * 
	 * Space complexity : O(N), with N being the number of operations. 
	 * 		The worst case scenario is if we get N add operations, in which 
	 * 		case our ArrayList and our HashMap grow to size N.
	 * */
	
	private static class RandomizedCollection {

	    Map<Integer, Set<Integer>> map; //key= input value, value = index of input value in list 
	    List<Integer> list; // input value
		Random rand = new Random();
	    
	    /** Initialize your data structure here. */
	    public RandomizedCollection() {
	        map = new HashMap<>();
		    list = new ArrayList<>();
	    }
	    
	    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
	    public boolean insert(int val) {
	        if(!map.containsKey(val)) {
	            /* LinkedHashSet because the set.iterator() might be costly when a number 
	             * has too many duplicates. Using LinkedHashSet can be considered as O(1) 
	             * if we only get the first element to remove.
	             * 
	             * In other words, think in case of remove: 
	             * The first time, we can update the last object of a List, to reflect the 
	             * move of last element in the list. But consider the second time, since last 
	             * time we moved the last one to somewhere upper, it's not last one any more. 
	             * 
	             * Then second time you cannot assume the last object of the List is the one you 
	             * want to update. You have to loop to find the correct one to update which is not O(1)
	             * 
	             * Why not List? see below link for explaination,
	             * http://buttercola.blogspot.com/2018/07/leetcode-381-insert-delete-getrandom-o1.html
	             * 
	             */
	            map.put(val, new LinkedHashSet<Integer>());    
	        }
	        
	        
	        map.get(val).add(list.size());
	        
	        list.add(val);
	        
	        return map.get(val).size() == 1;  // return true when first element is added. When dulicate this will return false as size() will be >1
	        
	        
	    }
	    
	    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
	    public boolean remove(int val) {
	        if(!map.containsKey(val) || map.get(val).size() == 0) 
	            return false;
	        
	        int remove_idx = map.get(val).iterator().next();
	        map.get(val).remove(remove_idx); // remove the index from LinkedHashSet
	        int last = list.get(list.size() - 1);
	        list.set(remove_idx, last);
	        
	        map.get(last).add(remove_idx);
	        // remove the last (if exist) from LinkedHashSe to avoid IndexOutOfBound
	        map.get(last).remove(list.size() - 1); 
	            
	        list.remove(list.size() - 1);
	        
	        return true;
	    }
	    
	    /** Get a random element from the collection. */
	    public int getRandom() {
	        return list.get(rand.nextInt(list.size()));
	    }
	}

}
