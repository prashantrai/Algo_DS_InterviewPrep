package Facebook;

public class SquaresOfASortedArray_977_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/*
    Strategy is to travese using 2 pointer compare the individual value and
    pick the smaller one, square it and add to the result array.
    This was in a single pass we will have squared sorted array wihtout explicit
    sorting.  
    
    Time Complexity: O(N), where N is the length of A.

    Space Complexity: O(N) if you take output into 
    account and O(1) otherwise.

    */
    
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            int square;
            if (Math.abs(nums[left]) < Math.abs(nums[right])) {
                square = nums[right];
                right--;
            } else {
                square = nums[left];
                left++;
            }
            result[i] = square * square;
        }
        return result;
    }

}
