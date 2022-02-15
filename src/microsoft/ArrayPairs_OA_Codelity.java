package microsoft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArrayPairs_OA_Codelity {

	public static void main(String[] args) {
		int[] arr = new int[]{1, 2, 2, 2, 2, 1};
		System.out.println("Expected: true, Actual: " + canGetPairBigOn(arr));
		
		arr = new int[]{1, 2, 2, 1};
		System.out.println("Expected: true, Actual: " + canGetPairBigOn(arr));
		
		arr = new int[]{7, 7, 7};
		System.out.println("Expected: false, Actual: " + canGetPairBigOn(arr));
		
		arr = new int[]{1, 2, 2, 2};
		System.out.println("Expected: false, Actual: " + canGetPairBigOn(arr));
	}
	
	/* 
	 * 
	 * Given an array N, return true if it is possible we can pair all the numbers in the array with equal 
		values. 
		E.g N = [1, 2, 2, 1] -> true as we can pair (N[0], N[3]) and (N[1], N[2]). 
		N = [7, 7, 7] would return false.
		
		But [1,1,2,2,2] can't because there in an extra 2 that can't be paired with anything.
	*/

	private static Boolean canGetPairBigOn(int[] arr) {
	    Map<Integer, Integer> mp = new HashMap<>();
	    for (int i : arr) {
	        if (!mp.containsKey(i)) {
	            mp.put(i, 1);
	        } else {
	            mp.put(i, mp.get(i) + 1);
	        }
	    }

	    for (Integer value : mp.values()) {
	        if (value % 2 != 0) return false;
	    }

	    return true;
	}
	
	//another approach
	private static boolean pair(int[] arr) {
        int len = arr.length;
        if(len == 0 || len == 1 || arr.length % 2 == 1) {
            return false;
        }
        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < len; i++) {
            if(set.contains(arr[i])) {
                set.remove(arr[i]);
            } else {
                set.add(arr[i]);
            }
        }
        if(set.size() > 0) {
            return false;
        }
        return true;
    }
}
