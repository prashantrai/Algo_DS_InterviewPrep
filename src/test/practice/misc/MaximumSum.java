package test.practice.misc;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

//--https://discuss.leetcode.com/topic/102381/delete-and-earn

public class MaximumSum {
	
	public static void main(String[] args) {
		MaximumSum sum = new MaximumSum();
		//int arr[] = new int[] { 2, 3, 4 };
//		int arr[] = {5, 5, 10, 100, 10, 5};
		//int arr[] = {1,20,3};
		//System.out.println(sum.FindMaxSum(arr, arr.length));
		//System.out.println(sum.getMaxSum(arr, arr.length));
		
//		int arr[] = {3,4,2};
		int arr[] = {1,2,1,3,2,3};
		Arrays.sort(arr);
		System.out.println(sum.getMaxSum2(arr, arr.length));
		
		System.out.println(">>> " + rob(arr));
		
		//int arr2[] = {3,4,2};
		//System.out.println(maxPoints(arr));
		System.out.println(deleteAndEarn2(arr));
		System.out.println(deleteAndEarn3(arr));
		System.out.println(deleteAndEarn4(arr));
		System.out.println(deleteAndEarn5(arr));
	}

	//--Perfect answer
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
	
	public static int deleteAndEarn4(int[] elements) {
		
		final Map<Integer, Integer> values = new TreeMap<Integer, Integer>();
        for (final int num : elements) {
            values.put(num, values.getOrDefault(num, 0) + num);
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
	public static int deleteAndEarn3(int[] nums) {
		//https://discuss.leetcode.com/topic/112807/java-c-clean-code-with-explanation
	        final int[] values = new int[10001];
	        for (int num : nums) {
	            values[num] += num;
	        }
	        int take = 0, skip = 0;
	        for (final int value : values) {
	            final int temp = Math.max(skip + value, take);
	            skip = take;
	            take = temp;
	        }
	        return take;
    }
	
	
	public static int deleteAndEarn2(int[] nums) {
        
		int[] count = new int[10001];
        
		for (int x: nums) {
        	count[x]++;
        }
        
		int avoid = 0, using = 0, prev = -1;

        for (int k = 0; k <= 10000; ++k) {
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
	
	
	
	//--DP Problem
	//--https://leetcode.com/problems/delete-and-earn/solution/
	public static int deleteAndEarn(int[] nums) {
        
		int[] count = new int[10001];
        
		for (int x: nums) {
        	count[x]++;
        }
        
		int avoid = 0, using = 0, prev = -1;

        for (int k = 0; k <= 10000; ++k) {
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
	
	
	
	/*
	 * { 2, 3, 4 }
	 * 
	 * */
	
	
	public static int rob(int[] nums) {
	    if(nums==null||nums.length==0)
	        return 0;
	 
	    if(nums.length==1)
	        return nums[0];
	 
	    int[] dp = new int[nums.length];
	    dp[0]=nums[0];
	    dp[1]=Math.max(nums[0], nums[1]);
	 
	    for(int i=2; i<nums.length; i++){
	        dp[i] = Math.max(dp[i-2]+nums[i], dp[i-1]);
	    }
	 
	    return dp[nums.length-1];
	}
	
	
	
	public int getMaxSum3(int arr[], int n) {
		
		 int[] count = new int[10001];
	        //ArrayList al = new ArrayList(10001);
	        
	        for (int x: arr) { 
	            count[x]++;
	            //al.add(x)   
	        }     
	       Arrays.asList(count);
		
	        
	        return 0;
		/*int [] remaining = new int[arr.length];
		int points = arr[n];
		
		for(int i=n-1; i>=0; i--) {
			if(arr[i] == arr[i+1]) {
				points += arr[i]
			} 
			if(arr[i+1] - arr[i] > 1) {
				remaining += 
			}
			
		}*/
		
	}
	
	
	public int getMaxSum2(int arr[], int n) {
		
		int incl = arr[0];
		int excl = 0;
		int prev_incl;
		int max = 0;
		
		
		/*
		 * incl = currentElement + excl
		 * excl = max(excl, prev_incl)
		 * */
		
		for (int i=1; i< n; i++) {
			prev_incl = incl;
			incl = arr[i] + excl;
			excl = Math.max(prev_incl, excl);
			
		}
		max += Math.max(incl, excl);
		
		return Math.max(incl, excl);
		
	}
	
	
	
	public int getMaxSum(int arr[], int n) {
		
		int incl = arr[0];
		int excl = 0;
		int prev_incl;
		
		/*
		 * incl = currentElement + excl
		 * excl = max(excl, prev_incl)
		 * */
		
		for (int i=1; i< n; i++) {
			prev_incl = incl;
			incl = arr[i] + excl;
			excl = Math.max(prev_incl, excl);
			
		}
		return Math.max(incl, excl);
		
	}
	
	/*
	 * Function to return max sum such that no two elements are adjacent
	 */
	int FindMaxSum(int arr[], int n) {
		int incl = arr[0];
		int excl = 0;
		int excl_new;
		int i;

		for (i = 1; i < n; i++) {
			/* current max excluding i */
			excl_new = (incl > excl) ? incl : excl;

			/* current max including i */
			incl = excl + arr[i];
			excl = excl_new;
		}

		/* return max of incl and excl */
		return ((incl > excl) ? incl : excl);
	}

}