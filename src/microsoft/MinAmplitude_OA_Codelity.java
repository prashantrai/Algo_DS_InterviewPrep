package microsoft;

public class MinAmplitude_OA_Codelity {

	public static void main(String[] args) {
		int[] nums = new int[]{3,5,1,3,9,8};
        int k = 4;
        System.out.println("Expected: 1, Actual: " + minAmplitude(nums, k));
        System.out.println("Expected: 1, Actual: " + solve(nums, k));
        
        int[] nums2 = new int[]{5,3,6,1,3};
        int k2 = 2;
        System.out.println("Expected: 2, Actual: " + minAmplitude(nums2, k2));
        System.out.println("Expected: 2, Actual: " + solve(nums2, k2));
        
        int[] nums3 = new int[]{8,8,4,3};
        int k3 = 2;
        System.out.println("Expected: 0, Actual: " + minAmplitude(nums3, k3));
        System.out.println("Expected: 0, Actual: " + solve(nums3, k3));
	}
	
	/* https://leetcode.com/discuss/interview-question/1523646/google-find-the-min-amplitude-after-removing-k-consecutive-elements-in-an-array
		
		You are given an array A of N integers and integer K. you want to remove K consecutive elements from 
		A in such a way that the amplitude of the remaining elements will be minimal. The amplitude is the 
		difference between the maximal and minimal elements.
	
		Write a function that given an array A of N integers and an integer K, returns an integer denoting 
		the minimal amplitude that can be obtained after the removal of K consecutive elements from A.
	
		Examples :
		a. Given A = [5,3,6,1,3 ] and K = 2, the function should return 2. 
			You can remove the third and fourth elements to obtain the following array: [5,3,3]. 
			Its maximal elements is 5 and its minimal element is 3, so the amplitude is 2.
			
		b. Given A = [8,8,4,3] and K=2, the function should return 0. 
			You can remove the third and fourth elements to obtain the array [8,8], 
			whose amplitude is equal to 0.
			
		c. Given A=[3,5,1,3,9,8] and K = 4 , the function should return 1. 
			You can remove the first, second, third and fourth elements to obtain the array [9,8], 
			whose amplitude equals 1.


	Similar: https://www.geeksforgeeks.org/minimize-the-sum-of-differences-of-consecutive-elements-after-removing-exactly-k-elements/
	*/
	
	
	private static int minAmplitude(int[] nums, int k) {
        int[][] left = new int[nums.length][2];        
        int leftMin = nums[0];
        int leftMax = nums[0];
        left[0][0] = Integer.MAX_VALUE;
        left[0][1] = Integer.MIN_VALUE;
        for(int i = 1; i < nums.length; i++) {
            left[i][0] = leftMin;
            left[i][1] = leftMax;
            leftMin = Math.min(leftMin, nums[i]);
            leftMax = Math.max(leftMax, nums[i]);
        }
        
        int[][] right = new int[nums.length][2];
        int rightMin = nums[nums.length - 1];
        int rightMax = nums[nums.length - 1];
        right[nums.length - 1][0] = Integer.MAX_VALUE;
        right[nums.length - 1][1] = Integer.MIN_VALUE;
        for(int i = nums.length - 2; i >= 0; i--) {
            right[i][0] = rightMin;
            right[i][1] = rightMax;
            rightMin = Math.min(rightMin, nums[i]);
            rightMax = Math.max(rightMax, nums[i]);
        }
        
        
        int res = Integer.MAX_VALUE;
        //{8,7,4,1};
        for(int i = 0; i <= nums.length - k; i++) {
            int min = Math.min(left[i][0], right[i + k - 1][0]);
            int max = Math.max(left[i][1], right[i + k - 1][1]);
            res = Math.min(res, max - min);
        }
        
        return res;
    }
	   
	
	//Refer: https://binarysearch.com/problems/Minimize-Amplitude-After-Deleting-K-Length-Sublist/solutions/3297891
	
	/*
	
	Intuition: 
		Let's take any sublist of length k in the array and remove it. After removing, we are basically 
		left with a prefix part and a suffix part of the original array.
		
		So the amplitude on removing that sublist would be : maxima(pref,suf)-minima(pref,suf)
		
		Implementation
		Maintain four arrays of minPrefix , maxPrefix , minSuffix and maxSuffix.
		Iterate through every k length sublist and keep updating minimum amplitude.
		
		Time Complexity:
		It takes O(N) time to precompute the minima and maxima for the suffixes and prefixes, 
		and O(1) time to check for each possible sublist, hence total time complexity of O(N)
		
		Space Complexity:
		O(N) as 4 arrays of size N for both prefix and suffix minima/maxima
			
	
	*/
	public static int solve(int[] nums, int k) {
        int n = nums.length;
        int[] pmin = new int[n];
        int[] pmax = new int[n];
        int[] smin = new int[n];
        int[] smax = new int[n];
        pmin[0] = nums[0];
        pmax[0] = nums[0];
        smin[n - 1] = nums[n - 1];
        smax[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            int j = n - 1 - i;
            pmin[i] = Math.min(pmin[i - 1], nums[i]);
            pmax[i] = Math.max(pmax[i - 1], nums[i]);
            smin[j] = Math.min(smin[j + 1], nums[j]);
            smax[j] = Math.max(smax[j + 1], nums[j]);
        }
        int ans = Integer.MAX_VALUE;
        for (int i = k - 1; i < n; i++) {
            if (i == k - 1) {
                ans = Math.min(ans, smax[i + 1] - smin[i + 1]);
                // System.out.println(smax[i+1]+" "+smin[i+1]);
            } else if (i == n - 1) {
                ans = Math.min(ans, pmax[i - k] - pmin[i - k]);
            } else {
                ans = Math.min(
                    ans, Math.max(pmax[i - k], smax[i + 1]) - Math.min(pmin[i - k], smin[i + 1]));
            }
        }
        return ans;
    }
	
	
}
