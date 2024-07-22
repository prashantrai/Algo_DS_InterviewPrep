package doordash;

public class MaximumGap_164_Medium {

	public static void main(String[] args) {
		int[] nums = {3,6,9,1};
		System.out.println("Expected: 3, Actual: " + maximumGap(nums));
	}
	
	/* Also, look at Bucket Sort for this, seems simple, refer below URLs or Leetcode solutions
	 * https://leetcode.com/problems/maximum-gap/discuss/1241681/JavaPython-Bucket-Idea-with-Picture-Clean-and-Concise-O(N)
	 * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
	 */
	
	// Time: O(N)
    // Space: O(N)
    // Radix Sort
    public static int maximumGap(int[] nums) {
        
        if (nums == null || nums.length < 2) {
            return 0;
        }
        
        // Find the maximum number in the input array
        // This determines the number of iteration we need to sort
        int maxVal = Integer.MIN_VALUE;
        for(int num : nums) {
            maxVal = Math.max(maxVal, num);
        }
        
        int[] output = new int[nums.length];
        
        for(int exp = 1; maxVal/exp > 0; exp *= 10) {
            // count array of size 10 is used to store the count 
            // of occurrences of each digit (0-9).
            int[] count = new int[10];
            
            // Store the occurrences in count[]
            for(int num : nums) {
                // update the count at index based Least Significant Digit 
                // (LSD i.e. right most digit), e.g, for num 170 and 93 LSD 
                // is 0 and 3 respectively and index 0 and 3 value will 
                // be incremented by 1.
                count[(num/exp)%10]++;
            }
            
            // Cumulative Count: Modify the count array such that each element
            // at index i contains the sum of previous counts. This step helps 
            // place the numbers in the correct position in the output array.
            for(int i=1; i<count.length; i++) {
                count[i] += count[i-1];
            }
            
            // output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            // count[(arr[i] / exp) % 10]--;
            
            // build the output array
            for(int i= nums.length-1; i>=0; i--) {
                int lsd = (nums[i] / exp) % 10;
                int idx = count[lsd] - 1;
                output[idx] = nums[i];
                count[lsd]--;
            }
            // Copy the output[] to nums[], so that nums[] now contains 
            // sorted numbers according to current digit
            //System.arraycopy(output, 0, nums, 0, nums.length);
            for (int i = 0; i < nums.length; i++) {
                nums[i] = output[i];
            } 
            
        }
        
        int maxGap = 0;
        for(int i=1; i<nums.length; i++) {
            maxGap = Math.max(maxGap, nums[i] - nums[i-1]);
        }
        
        return maxGap;
    }
	
	
}


