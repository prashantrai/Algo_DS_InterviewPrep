package Facebook;


import java.util.Random;

public class RandomPickWithWeight_528_Medium {

	public static void main(String[] args) {
		int idx = -99;
		int[] w = {1, 3};
		Solution solution = new Solution(w);
		idx = solution.pickIndex(); // return 1. It's returning the second element (index = 1) that has probability of 3/4.
		System.out.println("1. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("2. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("3. idx="+idx);
		
		idx = solution.pickIndex(); // return 1
		System.out.println("4. idx="+idx);
		
		idx = solution.pickIndex(); // return 0. It's returning the first element (index = 0) that has probability of 1/4.
		System.out.println("5. idx="+idx);
	}

	/*
	 * https://leetcode.com/problems/random-pick-with-weight/submissions/
	 * 
	 * https://leetcode.com/problems/random-pick-with-weight/discuss/154044/Java-accumulated-freq-sum-and-binary-search
	 * 
	 * Time: O(n) to init/constructor, O(logn) for one pick 
	 * Space: O(n)
	 * 
	 * Refer below for some more epxalination about the problem:
	 * https://leetcode.com/problems/random-pick-with-weight/discuss/671445/Question-explained
	 */

	static class Solution {

		Random random;
	    int[] wSums; //prefixSum
	    int totalSum;
	    public Solution(int[] w) {
	        wSums = new int[w.length];
	        random = new Random();
	        
	        // prefix sum
	        int prefixSum = 0;
	        for(int i=0; i<w.length; i++) { 
	            prefixSum += w[i]; 
	            wSums[i] = prefixSum;
	        }
	        //for(int i=1; i<w.length; i++) { 
	          //  w[i] += w[i-1];
	        //}
	        //wSums = w;
	        totalSum = prefixSum;
	    }

		public int pickIndex() {
			int len = wSums.length;
//			int idx = random.nextInt(wSums[len - 1]) + 1; // works

			// ensures that the generated random number 
	        // falls within the desired range [0, totalSum)
	        double idx = this.totalSum * Math.random();

			// search position - binary search
			int left = 0, right = len - 1;
			while (left < right) {
				int mid = left + (right - left) / 2;
				if (wSums[mid] == idx) {
					return mid;
				} else if (wSums[mid] < idx) {
					left = mid + 1;
				} else {
					right = mid;
				}
			}
			return left;
		}
	}

	/**
	 * Your Solution object will be instantiated and called as such: 
	 * Solution obj = new Solution(w);
	 * int param_1 = obj.pickIndex();
	 */

}
