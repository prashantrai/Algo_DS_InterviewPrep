package LinkedIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertDeleteGetRandom_380_Medium {

	public static void main(String[] args) {
		RandomizedSet obj = new RandomizedSet();
		boolean param_1 = obj.insert(1);
		System.out.println("param_1: "+param_1);
		
		boolean param_2 = obj.remove(2);
		System.out.println("param_2: "+param_2);
		
		int param_3 = obj.getRandom();
		System.out.println("param_3: "+param_3);
	}

	/*
	 * Complexity Analysis: 
		Time complexity. GetRandom is always O(1). 
			Insert and Delete both have O(1) average time complexity, 
			and O(N) in the worst-case scenario when the operation exceeds 
			the capacity of currently allocated array/hashmap and invokes space reallocation.

		Space complexity: O(N), to store N elements.
	 * 
	 */
	
	private static class RandomizedSet {
		
		Map<Integer, Integer> map; //key= input value, value = index of input value in list 
		List<Integer> list; // input value
		Random rand = new Random();

	    /** Initialize your data structure here. */
	    public RandomizedSet() {
	        map = new HashMap<>();
	        list = new ArrayList<>();
	    }
	    
	    /** Inserts a value to the set. Returns true if the set did 
	     * not already contain the specified element. */
	    public boolean insert(int val) {
	        if(map.containsKey(val)) return false;
	        
	        map.put(val, list.size());
	        list.add(list.size(), val);
	        
	        return true;
	    }
	    
	    /*
	    Retrieve an index of element to delete from the hashmap.

	    Move the last element to the place of the element to delete, O(1) time.

	    Pop the last element out, O(1) time.
	    */
	    
	    /** Removes a value from the set. Returns true if 
	     * the set contained the specified element. */
	    public boolean remove(int val) {
	    	if(!map.containsKey(val)) return false;
	    	
	    	// Get the index of the value to be deleted from map
	    	int idx = map.get(val);
	    	
	    	// Get the last index  value from list and store in a variable
	    	int lastElement = list.get(list.size()-1);
	    	
	    	// Swap the last index value with the value that needs to be deleted
	    	list.set(idx, lastElement);
	    	//list.set(list.size()-1, val); // no required as we are deleting this node 
	    	
	    	// Add/Update new element in to the map with the stored last index value as key and index of the deleted element as value
	    	map.put(lastElement, idx);
	    	
	    	// Delete the last list element
	    	list.remove(list.size()-1);
	    	map.remove(val);
	    	
	    	
	    	return true;
	    }
	    
	    /** Get a random element from the set. */
	    public int getRandom() {
	       return list.get(rand.nextInt(list.size()));
	    }
	}
}
