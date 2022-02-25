package Vmware;

import java.util.Arrays;

public class RangeAddition_370_Medium_Premium {

	public static void main(String[] args) {
		
		int length = 5; 
		int[][] updates = {{1,3,2},{2,4,3},{0,2,-2}};
		int[] res = getModifiedArray(length, updates);
		System.out.println("Expected: [-2, 0, 3, 5, 3], Actual: [" + Arrays.toString(res) + "]");

		length = 10;
		int[][] updates2 = {{2,4,6},{5,6,8},{1,9,-4}};
		int[] res2 = getModifiedArray(length, updates2);
		System.out.println("Expected: [0, -4, 2, 2, 2, 4, 4, -4 , -4, -4], Actual: [" + Arrays.toString(res2) + "]");
		
	}
	
	
	/* Time Complexity : O(n+k), each of the k update operations is done in 
    constant O(1) time. The final cumulative sum transformation takes 
    O(n) time always.
    
    Space: O(1)
    
    For better explaination refer: 
    https://www.youtube.com/watch?v=-SDHYqxI-Hc
    
    Algorithm: 
    1. Iterated thourgh the updates array and 
    2. for each startIndex update the result array (i.e. addthe Value) 
    3. and for endIndex update the value at endIndex+1 with -Value
    4. Once finish perform a prefix sum on etire array
    
    */
    public static int[] getModifiedArray(int length, int[][] updates) {
        
        int[] res = new int[length];
        
        for(int[] update : updates) {
            int startIdx = update[0];
            int endIdx = update[1];
            int value = update[2];  //increment
            
            res[startIdx] += value;
            if(endIdx < res.length - 1) {
                res[endIdx + 1] -= value;
            }
        }
        
        //prefix sum
        for(int i=1; i<res.length; i++) {
            res[i] += res[i-1];
        }
        
        return res;
    }

}

/*

Question: 
You are given an integer length and an array updates where 
updates[i] = [startIdxi, endIdxi, inci].

You have an array arr of length length with all zeros, and you have some operation to apply 
on arr. In the ith operation, you should increment all the elements arr[startIdxi], 
arr[startIdxi + 1], ..., arr[endIdxi] by inci.

Return arr after applying all the updates.

Example 1: 

Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
Output: [-2,0,3,5,3]


Example 2:

Input: length = 10, updates = [[2,4,6],[5,6,8],[1,9,-4]]
Output: [0,-4,2,2,2,4,4,-4,-4,-4]
 
 
 */ 
