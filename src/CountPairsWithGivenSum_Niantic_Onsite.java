import java.util.HashMap;
import java.util.Map;

public class CountPairsWithGivenSum_Niantic_Onsite {

	public static void main(String[] args) {
        int[] numbers = { 4, 3, 2, 9, -2, 3}; 
        int n = 7;
        System.out.println("Expected: 3, Actual: "+ getPairs(numbers, n));
        int[] numbers2 = {4, 4, 2, 9, -2, 4}; 
        n = 8;
        
        System.out.println("Expected: 3, Actual: " + getPairs(numbers2, n));
        
        int[] numbers3 = {4, 4, 2, 9, -2, 4, 4 }; 
        n = 8;
        
        System.out.println("Expected: 6, Actual: " + getPairs(numbers3, n));
        
    }
	
	// from https://www.geeksforgeeks.org/count-pairs-with-given-sum/
	
	/**
	 * Return the number of pairs of integers in a list whose values add up to n
	 * A given element in the array may appear in multiple pairs.
	 * Only the count of the number of pairs needs to be returned, not the actual pairs.
	 *
	 * Examples:
	 * numbers = [ 4, 3, 2, 9, -2, 3], n = 7
	 * return: 3  (The pairs are:  [4,3]  [9,-2]  [4,3])
	 *
	 * numbers = [ 4, 4, 2, 9, -2, 4], n = 8
	 * return: 3  (The pairs are:  [4,4]  [4,4]  [4,4])
	 *
	 * number = [ 4, 4, 2, 9, -2, 4, 4 ], n = 8
	 * return: 6  (The pairs are:  [4,4] [4,4] [4,4] [4,4] [4,4] [4,4])
	 **/
	
	//Time: O(N)
    // Space: O(N)
    static int getPairs(int[] arr, int sum) {
        
        if(arr == null || arr.length == 0) 
            return Integer.MIN_VALUE;
        
        Map<Integer, Integer> hm = getFreqMap(arr);
        
        int count = 0;
        for(int i=0; i<arr.length; i++) {
            if(hm.get(sum - arr[i]) != null) {
                count += hm.get(sum - arr[i]);
            }
            
            if(sum - arr[i] == arr[i]) 
                count--;
        }
        
        return count/2;
    }
    
    static Map<Integer, Integer> getFreqMap(int[] arr) {
        Map<Integer, Integer> hm = new HashMap<>();
        
        for(int i=0; i<arr.length; i++){
            if(!hm.containsKey(arr[i])) {
                hm.put(arr[i], 0);
            }
            hm.put(arr[i], hm.get(arr[i]) + 1);
        }
        
        return hm;
    }

}
