package test.practice.ebay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KSumDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(">>Result: "+ threeSum(-1, 0, 1, 2, -1, -4));
		System.out.println(">>Result: "+ threeSum_2(-1, 0, 1, 2, -1, -4));
		System.out.println(">>Result: "+ threeSum_3(-1, 0, 1, 2, -1, -4));

	}
	
	public static List<List<Integer>> threeSum_3 (int...num) {
		
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		for(int i=0; i<num.length-2; i++) {
			
			if(i==0 || (i>=0 && num[i] != num[i-1])) {
				
				/**
				 * We'll be taking 3 pointers. Keeping one at start (i.e. i), one at start+1 and third one at
				 * end of array. 
				 * 
				 * For each value of i we'll move and 2nd (i.e. lo) and 3rd pointer (i.e. high) and will add value at their location 
				 * with the value and first pointer's location (here num[i])
				 * 
				 * If it's ZERO add to result and continue to find the next set of values
				 * 
				 * Complexity: O(n^2)
				 * */
				
				int lo = i+1;
				int high = num.length-1;
				int sum = 0 - num[i]; //-- x+y+z=0 i.e. x+y = 0-z
				
				while(lo<high) {
					if(num[lo] + num[high] == sum) {
						res.add(Arrays.asList(num[lo], num[high], num[i]));
						
						//--skipping same result/value
						while(lo < high && num[lo] == num[lo+1]) lo++; 
						while(lo < high && num[high] == num[high-1]) high--; 
						lo++;
						high--;
					} else if(num[lo] + num[high] < sum) {
						lo++;
					} else {
						high--;
					}
				}
				
			}
			
		}
		
		return res;
	}
	
	
	//--Leet Code Solutions:  https://leetcode.com/problems/3sum/description/
	public static List<List<Integer>> threeSum(int... num) {
	    Arrays.sort(num);
	    List<List<Integer>> res = new java.util.LinkedList<List<Integer>>(); 
	    for (int i = 0; i < num.length-2; i++) {
	        if (i == 0 || (i > 0 && num[i] != num[i-1])) {
	            int lo = i+1, hi = num.length-1, sum = 0 - num[i];
	            
	            System.out.println("lo=" + lo +", hi=" + hi + ", sum="+sum);
	            
	            while (lo < hi) {
	                if (num[lo] + num[hi] == sum) {
	                    res.add(Arrays.asList(num[i], num[lo], num[hi]));
	                    while (lo < hi && num[lo] == num[lo+1]) lo++;
	                    while (lo < hi && num[hi] == num[hi-1]) hi--;
	                    lo++; hi--;
	                } else if (num[lo] + num[hi] < sum) lo++;
	                else hi--;
	           }
	        }
	    }
	    return res;
	}
	
	public static List<List<Integer>> threeSum_2(int... nums) {
	    List<List<Integer>> res = new ArrayList<List<Integer>>();
	    Arrays.sort(nums);
	    for (int i = 0; i + 2 < nums.length; i++) {
	        if (i > 0 && nums[i] == nums[i - 1]) {              // skip same result
	            continue;
	        }
	        int j = i + 1, k = nums.length - 1;  
	        int target = -nums[i];
	        while (j < k) {
	            if (nums[j] + nums[k] == target) {
	                res.add(Arrays.asList(nums[i], nums[j], nums[k]));
	                j++;
	                k--;
	                while (j < k && nums[j] == nums[j - 1]) j++;  // skip same result
	                while (j < k && nums[k] == nums[k + 1]) k--;  // skip same result
	            } else if (nums[j] + nums[k] > target) {
	                k--;
	            } else {
	                j++;
	            }
	        }
	    }
	    return res;
	}

}
