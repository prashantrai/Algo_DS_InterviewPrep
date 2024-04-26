package Facebook;

import java.util.HashMap;
import java.util.Map;

public class DotProductOfTwoSparseVectors_1570_Medium {

	public static void main(String[] args) {
		
		int[] nums1 = {1,0,0,2,3}; 
		int[] nums2 = {0,3,0,4,0};
		
		SparseVector v1 = new SparseVector(nums1);
		SparseVector v2 = new SparseVector(nums2);
		int ans = v1.dotProduct(v2);
		System.out.println("Expected: 8, Actual: "+ans);

	}
	
	static class SparseVector {
	    
	    /*
	    Let N be the length of the input array and 
	    L be the number of non-zero elements.
	    
	    Time complexity: O(N) for creating the Hash Map; 
	                     O(L) for calculating the dot product.

	Space complexity: O(L) for creating the Hash Map, as we only store elements that are non-zero. O(1) for calculating the dot product.

	    */
	    
	    
	    // Map the index to value for all non-zero values in the vector
	    Map<Integer, Integer> map;
	    
	    SparseVector(int[] nums) {
	        map = new HashMap<>();
	        for (int i=0; i < nums.length; i++) {
	            if(nums[i] != 0) {
	                map.put(i, nums[i]);
	            }
	        }
	    }
	    
		// Return the dotProduct of two sparse vectors
	    public int dotProduct(SparseVector vec) {
	        int result = 0;
	        
	        // iterate through each non-zero element in this sparse vector
	        // update the dot product if the corresponding index has a non-zero value in the other vector
	        
	        for(Integer i : this.map.keySet()) {
	            if(vec.map.containsKey(i)) {
	                result += this.map.get(i) * vec.map.get(i);
	            }
	        }
	        
	        return result;
	    }
	}

}
