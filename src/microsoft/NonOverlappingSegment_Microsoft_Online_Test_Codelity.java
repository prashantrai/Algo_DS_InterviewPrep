package microsoft;

public class NonOverlappingSegment_Microsoft_Online_Test_Codelity {

	public static void main(String[] args) {
		int arr[] = {10, 1, 3, 1, 2, 2, 1, 0, 4};
		int targetSum = 4;
		System.out.println("Expected: 3, Acutual: "+countNonOverlappingSegments(arr, targetSum));
	}

	/* Found on Discord: 
	 * 
	 * Given nums = [10, 1, 3, 1, 2, 2, 1, 0, 4], there are three non-overlapping
	 * segments, each whose sum is equal to 4: (1, 3), (2, 2), (0, 4). Expected
	 * output = 3 Also for test case : [9, 9, 9, 9, 9] the code should return 2.
	 */

	public static int countNonOverlappingSegments(int[] arr, int targetSum) {
		int count = 0;
		for(int i=1; i<arr.length; i++) {
			if(arr[i-1] > arr[i]) continue;
			
			int sum = arr[i-1] + arr[i];
			if(sum == targetSum) {
				count++;
			}
		}
		return count;
	}
	 	
}
