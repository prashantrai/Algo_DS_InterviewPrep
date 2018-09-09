package test.practice.misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class QuipHackerRank1 {

	public static void main(String[] args) {

		//int arr[] = {3,4,2};
		int arr[] = {2, 2, 3, 3, 3, 4};
//		int arr[] = {1,2,1,3,2,3};
//		int arr[] = {1,2,3,4};

		//System.out.println(deleteAndEarn(arr));
		System.out.println(deleteAndEarn2(arr));
		//System.out.println(maxSum(arr));
		
	}
	
	
	public static int maxSum (int[] nums) {
		
		int incl = nums[0];
		int excl = 0;
		int prev_incl;
		
		for(int i=1; i<nums.length; i++) {
			prev_incl = incl;
			int currEle = nums[i];
			incl = Math.max(prev_incl, (excl + currEle) );
			excl = prev_incl;
		}
		return Math.max(incl, excl);
	}
	
	public static int deleteAndEarn(int[] nums) {
//        int n = 10001;
		int n=10;
        int[] values = new int[n];
        for (int num : nums)
            values[num] += num;

        int take = 0, skip = 0;
        for (int i = 0; i < n; i++) {
            int takei = skip + values[i];
            int skipi = Math.max(skip, take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take, skip);
    }
	
	//--https://leetcode.com/articles/delete-and-earn/
	public static int deleteAndEarn2(int[] nums) {
        //int[] count = new int[10001];
        
		int n = 10;
		int[] count = new int[n];
        for (int x: nums) 
        	count[x]++;
        
        int avoid = 0, using = 0, prev = -1;

        for (int k = 0; k <= n-1; ++k) {
        	
        	if (count[k] > 0) {
	            int m = Math.max(avoid, using);
	            if (k - 1 != prev) {
	                using = k * count[k] + m;
	                avoid = m;
	            } else {
	                using = k * count[k] + avoid;
	                avoid = m;
	            }
	            prev = k;
        	}
        }
        return Math.max(avoid, using);
    }
	
	//--slow performance
	public static int deleteAndEarn5(int[] elements) {
		final Map<Integer, Integer> values = new TreeMap<Integer, Integer>();
        for (final int num : elements) {
            int n = values.get(num) != null ? values.get(num) : 0;
            values.put(num, n + num);
        }
        int pre = 0, cur = 0;
        for (final int num : values.keySet()) {
            if (!values.containsKey(num - 1)) {
                pre = cur;
                cur += values.get(num);
            } else {
                final int temp = Math.max(pre + values.get(num), cur);
                pre = cur;
                cur = temp;
            }
        }
        return cur;
		
		
	}

}
