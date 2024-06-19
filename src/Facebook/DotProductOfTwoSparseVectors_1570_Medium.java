package Facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotProductOfTwoSparseVectors_1570_Medium {

	public static void main(String[] args) {
		
		int[] nums1 = {1,0,0,2,3}; 
		int[] nums2 = {0,3,0,4,0};
		
		SparseVector v1 = new SparseVector(nums1);
		SparseVector v2 = new SparseVector(nums2);
		int ans = v1.dotProduct(v2);
		System.out.println("Expected: 8, Actual: "+ans);
		
		
		// Follow-up : What if only one of the vectors is sparse and also smaller than other
		
		int[] sparseNums = {1, 0, 0, 2, 3};
		int[] denseNums = {1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		
		SparseVector_3 sparseVector3 = new SparseVector_3(sparseNums);
		SparseVector_3 sparseVector3_dense = new SparseVector_3(denseNums);
		
		int res = sparseVector3.dotProduct(sparseVector3_dense);
		System.out.println("Expected: 29, Actual: " + res);
		
		
		//int[] sparseNums = {1, 0, 0, 2, 3};
//		int[] denseNums = {0, 3, 0, 4, 0};
		
		//SparseVector sparseVector = new SparseVector(sparseNums);
		
		// Compute dot product between a sparse vector and a non-sparse vector
		//int result = sparseVector.dotProduct(denseNums);
        //System.out.println("Expected: 8, Actual: " + result); // Output should be 8 (2*4 + 3*0)

	}
	
	/*
    Using ArrayList - Use in interview
    
    Time complexity is similar to HashMap solution: 
    
    Let N be the length of the input array and 
    L be the number of non-zero elements.
    
    Time complexity: O(N) for creating the List; 
                     For traverse : O(L1 + L2)
                     

    Space complexity: 
        O(L) for the List (worst case O(N) if every element is non-zero), 
             as we only store elements that are non-zero. 
        
        O(1) for calculating the dot product.
    
    Ref: https://leetcode.com/problems/dot-product-of-two-sparse-vectors/discuss/4147952/Java-two-pointer-and-Meta-follow-up
    */
	static class SparseVector {
	    
	    List<int[]> lst;
	    SparseVector(int[] nums) {
	        lst = new ArrayList<>();
	        for (int i = 0; i < nums.length; i++) {
	            if (nums[i] != 0) { // add non-zero only
	                // create integer array of indices and value like map
	                // and add to the list
	                lst.add(new int[] {i, nums[i]}); 
	            }
	        }
	        
	    }
	    
		// Return the dotProduct of two sparse vectors
	    public int dotProduct(SparseVector vec) {
	        // lst and vec.lst
	        int i = 0, j = 0;
	        int res = 0;
	        
	        // Time: O(L1 + L2)
	        while (i < this.lst.size() && j < vec.lst.size()) {
	            int index1 = this.lst.get(i)[0];
	            int index2 = vec.lst.get(j)[0];
	            
	            if (index1 < index2) {
	                i++;
	            } else if (index1 > index2) {
	                j++;
	            } else { // when same index value, calculate the product
	                res += this.lst.get(i)[1] * vec.lst.get(j)[1];
	                i++; 
	                j++;
	            }
	        }
	        return res;
	    }
	}

/*  
 * Ref: https://leetcode.com/problems/dot-product-of-two-sparse-vectors/discuss/4147952/Java-two-pointer-and-Meta-follow-up
 * 
 * Follow - Up: If one vector is large/small and not sparse. 
    For the follow-up question, if the length of one sparse vector’s 
    non-zero element is much greater than the other one’s, we could use 
    binary search on the long sparse vector.
	
	Constructor:
			Time Complexity: O(n), length of input array, as we are iterating entire array
			Space Complexity: O(n), in worst case when all elements in the input array are non-
                                     zero as it stores non-zero elements of the input array.

		dotProduct Method:
			Time Complexity: O(L1⋅logL2), where L1 is the size of lst that we iterate 
											and logL2 is binary search

			Space Complexity: O(1) (constant space for variables and no additional data structures)

	   This analysis assumes that L1 and L2 represent the number of non-zero elements 
	   in the two sparse vectors being compared.
	 * 
	 * 
	 * */
	
	static class SparseVector_3 {
	    List<int[]> lst;
	    
	    SparseVector_3(int[] nums) {
	        lst = new ArrayList<>();
	        for (int i = 0; i < nums.length; i++) {
	        	if (nums[i] != 0) {   // add non-zero only
	            	// create integer array of indices and value like map
	                // and add to the list
	            	lst.add(new int[]{i, nums[i]});
	            }
	        }
	    }
	    
		// Return the dotProduct of two sparse vectors
	    public int dotProduct(SparseVector_3 vec) {
	        // suppose lst is small
	        int res = 0;
	        
	        /* O(L1⋅logL2), where L1 is lst length and logL2 is binary search operation
	         * */
	        for (int[] arr : lst) { 
	            int target = arr[0];
	            // System.out.println(arr[1] + " " +binarySearch(target, vec.lst) );
	            res += arr[1] * binarySearch(target, vec.lst);
	        }
	        return res;
	    }

	    /*The binarySearch method performs a binary search on the list lst of size L2.
		  Thus, the time complexity of the binarySearch method is O(logL2).
	     * */
	    // One of the assumption: it seems the sparse vectors are in sorted order
	    private int binarySearch(int target, List<int[]> lst) {
	        int l = 0; 
	        int r = lst.size() - 1;
	        
	        while (l <= r) { // need <=
	        	int mid = l + (r - l) / 2;
	            if (lst.get(mid)[0] > target) {
	            	r = mid -1;
	            } else if (lst.get(mid)[0] < target) {
	            	l = mid + 1;
	            } else {
	                return lst.get(mid)[1];
	            }
	        }
	        return 0;
	    }
	}

	
	

	/* 
	 * Working solution with hashmap - commented just to run the ArrayList implementation.
	 * user ArrayList in interview
	 */
	
	static class SparseVector2 {
	    
		/*
	    Let N be the length of the input array and 
	    L be the number of non-zero elements.
	    
	    Time complexity: O(N) for creating the Hash Map; 
	                     O(L) for calculating the dot product.

	    Space complexity: 
	        O(L) for creating the Hash Map (worst case O(N) if every element is non-zero), 
	             as we only store elements that are non-zero. 
	        
	        O(1) for calculating the dot product.

	    */
	    
	    
	    // Map the index to value for all non-zero values in the vector
	    Map<Integer, Integer> map;
	    
	    SparseVector2(int[] nums) {
	        map = new HashMap<>();
	        for (int i=0; i < nums.length; i++) {
	            if(nums[i] != 0) {
	                map.put(i, nums[i]);
	            }
	        }
	    }
	    
		// Return the dotProduct of two sparse vectors
	    public int dotProduct(SparseVector2 vec) {
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
	    
	    /* Not sure if this is correct approach - some solutions have used binary search
	     * find out why binary search?
	     * 
	     * 
	    Follow up: What if only one of the vectors is sparse?
	    
	    If only one of the vectors is sparse, we should adapt our implementation to handle this 
	    efficiently. In this scenario, the non-sparse vector will be represented as a regular 
	    array, and the sparse vector will be represented using the SparseVector class.
	    
	    1. SparseVector class: This remains the same, representing the sparse vector.

	    2. Dot Product Method: We will implement a new method to compute the dot product 
	     between a SparseVector and a regular array (non-sparse vector).


	    Dot Product with a Non-Sparse Vector (dotProduct(int[] vec)):
	        - Time Complexity: O(k)
	        - Space Complexity: O(1)
	    */
	    
	    // Method to compute the dot product of this SparseVector with a non-sparse vector (array)
	    public int dotProduct(int[] vec) {
	        int result = 0;
	        for (Integer index : this.map.keySet()) {
	            // Check if the index is within bounds of the array
	            if (index < vec.length) {
	                result += map.get(index) * vec[index];
	            }
	        }
	        return result;
	    }
	}

}
