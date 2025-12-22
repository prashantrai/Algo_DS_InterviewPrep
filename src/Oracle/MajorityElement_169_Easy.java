package Oracle;

public class MajorityElement_169_Easy {

	public static void main(String[] args) {
		int[] nums1 = {3,2,3};
		System.out.println("Expected: 3, Actual: " + majorityElement(nums1));
		
		int[] nums2 = {2,2,1,1,1,2,2};
		System.out.println("Expected: 2, Actual: " + majorityElement(nums2));
	}

	/*
	 * Time: O(n) Space: O(1)
	 */
	public static int majorityElement(int[] nums) {
		int count = 0;
		Integer candidate = null;

		for (int num : nums) {
			if (count == 0) {
				candidate = num;
			}

			count += (num == candidate) ? 1 : -1;
		}
		return candidate;
	}

}
