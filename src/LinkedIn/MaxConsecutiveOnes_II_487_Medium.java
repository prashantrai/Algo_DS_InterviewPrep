package LinkedIn;

public class MaxConsecutiveOnes_II_487_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// NOTE: Wasn't asked in Linkedin interview
	// Time: O(n)
    // Space: O(1)
	public int findMaxConsecutiveOnes(int[] nums) {
        
        int seeZero = 0;
        int max = 0;
        int curr = 0;
        int prev = 0; // store prev curr value before setting it 0 when we see a 0.

        for(int i=0; i<nums.length; i++) {
            if(nums[i] == 0) {
                seeZero = 1;
                // store current curr value before resetting it
                prev = curr;
                curr = 0;
            } else {
                curr++;
            }
            max = Math.max(max, curr + prev + seeZero);
        }

        return max;
    }

}
