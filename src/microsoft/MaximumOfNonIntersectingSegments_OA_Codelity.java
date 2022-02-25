package microsoft;

import java.util.HashMap;
import java.util.Map;

public class MaximumOfNonIntersectingSegments_OA_Codelity {

	public static void main(String[] args) {

		int[] nums = {10, 1, 3, 1, 2, 2, 1, 0, 4};
		System.out.println("Expected: 3, Actual: " +maxNonOverlapping(nums));
	}
	
	/*
	Find the maximum of non-intersecting segments of length 2 (two adjacent elements), 
	such that segments have an equal sum

	Example: given nums = [10, 1, 3, 1, 2, 2, 1, 0, 4], there are three non-overlapping segments, 
	each whose sum is equal to 4: (1, 3), (2, 2), (0, 4).
	Expected output = 3

	
	https://www.chegg.com/homework-help/questions-and-answers/java-find-maximum-non-intersecting-segments-length-2-two-adjacent-elements-segments-equal--q85314561
	
	https://leetcode.com/discuss/interview-question/398023/microsoft-online-assessment-questions/1271816

	https://leetcode.com/discuss/general-discussion/1505371/java-find-the-maximum-of-non-intersecting-details-mentioned-in-description
	
	https://leetcode.com/discuss/interview-question/1364630/oa-max-number-of-non-intersecting-elements-of-len-2-having-same-sum
	*/
	

	//---- working solution
    
	//Look here: https://massivealgorithms.blogspot.com/2015/07/lesson-14-max-non-overlapping-segments.html
    //https://leetcode.com/discuss/interview-question/1364630/oa-max-number-of-non-intersecting-elements-of-len-2-having-same-sum
    
    public static int maxNonOverlapping(int[] arr) {
		int max = 0;
		for (int i = 0; i < arr.length - 1; i++) { // for every adj pair check how many other pairs are there with same
													// sum/
			int reqSum = arr[i] + arr[i + 1];
			int reqSumCount = 1;
			for (int j = 0; j < arr.length - 1;) {
				if ((j < i && (j + 1) < i) || (j > (i + 1))) {
					if ((arr[j] + arr[j + 1]) == reqSum) {
						reqSumCount++;
						j += 2;
					} else {
						j++;
					}
				} else
					j++;
			}
			if (max < reqSumCount)
				max = reqSumCount;
			// System.out.println(reqSum + " has total count = " + reqSumCount);
		}
		return max;

	}
	
	
	
	//---- solution
	public int solution(int[] A) {
        // write your code in Java SE 8
        int n = A.length; 
        
        int ans = 0;
        for (int i = 0; i < n-1; i++) {
            int sum = A[i] + A[i+1];
            ans = Math.max(ans, 1 + getCount(i+2, sum, A));
        }

        return ans;
    }

    private int getCount(int idx, int sum, int[] arr) {
        int n = arr.length;
        if (idx >= n-1) {
            return 0;
        }

        int count = 0;
        if (arr[idx] + arr[idx+1] == sum)
            count = 1 + getCount(idx+2, sum, arr);
        

        return Math.max(count, getCount(idx+1, sum, arr));
    }
    
}
