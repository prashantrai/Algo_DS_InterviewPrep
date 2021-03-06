package Intuit;

public class MaximumLengthOfRepeatedSubarray_718_Medium {

	public static void main(String[] args) {
		int[] A = {1,2,3,2,1};
		int[] B = {3,2,1,4,7};
	
		System.out.println("Expected: 3, Actual: " + findLength(A, B));
		
		//test
		/*Map<Integer, ArrayList<Integer>> Bstarts = new HashMap();
        for (int j = 0; j < B.length; j++) {
            Bstarts.computeIfAbsent(B[j], x -> new ArrayList()).add(j);
            System.out.println(Bstarts);
        }
        
        int sum_1 = Arrays.stream(A).sum();
        int sum_2 = IntStream.of(A).sum();
        
        System.out.println(sum_1 +", "+ sum_2);*/
        
		
	}
	
	/* https://leetcode.com/problems/maximum-length-of-repeated-subarray/
	 * https://leetcode.com/problems/maximum-length-of-repeated-subarray/solution/
	 * 
	 * 
	 * Complexity Analysis
		Time Complexity: O(M*N), where M, N are the lengths of A, B.
		Space Complexity: O(M*N), the space used by dp.
	 * 
	 * */

	public static int findLength(int[] A, int[] B) {
        int max = 0;
        int[][] dp = new int[A.length+1][B.length+1];

        for(int i=1; i<=A.length; i++) {
            for(int j=1; j<=B.length; j++) {
                if(A[i-1] == B[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }
}
