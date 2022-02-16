package Affirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PhoneScreen_Feb152022 {

	public static void main(String[] args) {
		RandomSet randSet = new RandomSet();
        randSet.put(11, 111);
        randSet.put(22, 111);
        randSet.put(33, 111);
        randSet.put(44, 444);
        
        randSet.delete(33);
        //getRandomValue() //--> 111 or 444
        
        System.out.println("Get: " + randSet.get(33));
        System.out.println("Get: " + randSet.get(11));
        
        System.out.println("Rand: " + randSet.get_random_value());
        System.out.println("Rand: " + randSet.get_random_value());
        
        System.out.println("Delete: " + randSet.delete(22));
	}

}

/*
Implement a data structure to support below methods (no dplicates) 

put(k, v)
get(k) -> v
delete(k)
get_random_value() -> v


Follow-up: If we have duplicate values for different keys like below  
	11 -> 111
	22 -> 111
	33 -> 111
	44 -> 444
	
	with this case current implementation will return random for 111 with 3/4 probability 
	and 444 with 1/4 probability. How would you make sure that values has equal probability
	i.e. 1/2 for 111 and 1/2 for 444
	
	Solution: Maintain 2 more DS HashSet (to check if the values has already been seen)
	and a LinkedHashMap<Integer, List<Integer>> to map the value (as key) against key (used here as value and added in the list).

	e.g. LinkeHashMap : {
							111 = {11, 22, 33},
							444 = {444}
						}
	 Whenever we call delete we can update LinkedHashMap by deleting one of the KEY (i.e. 11, 22, 33) 
	 based what key has been passed with delete. 
	 
 	 If LIST in LinkedHashMap is empty (after several delete) remove the entry from LinkedHashMap 
	  
*/
class RandomSet {
    
    Map<Integer, Integer> map;
    List<Integer> list;
    Random rand = new Random();
    
    public RandomSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }    
    
    public boolean put(Integer k, Integer v) {
    
        if(!map.containsKey(k)) {
            list.add(k);
        }
        map.put(k, v);
        
        return true;
    }
    
    // O(n) for list
    public boolean delete(Integer k) {
        if(!map.containsKey(k)) 
            return false;
        
        int idx = getIndex(k);
        if(idx == -1)
            return false;
            
        list.remove(idx); //O(N)
        map.remove(k); //O(1)
        
        return true;
    }
    
    public Integer get(Integer k) {
        return map.get(k);
    }
    
    public Integer get_random_value() {
        return map.get(getRandom());
    }
    // returns random key stored in list
    private Integer getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
    
    
    private int getIndex(Integer k) {
        
        for(int i=0; i<list.size(); i++) {
            if(list.get(i) == k) 
                return i;
        }
        return -1;
    }
    
    
}


