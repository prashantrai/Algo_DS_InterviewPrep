package leetcode;

public class SingleNumber_LeetCode136 {

	public static void main(String[] args) {

	}

	//--https://leetcode.com/problems/single-number/
	//--https://www.youtube.com/watch?v=CvnnCZQY2A0&list=PLi9RQVmJD2fZgRyOunLyt94uVbJL43pZ_&index=2
	//--Using XOR [refer video comments]
	//-- Runtime O(n) Space O(n)
	public int singleNumber(int[] nums) {
		int result = 0;
		for (int value : nums) {
			result ^= value;
		}
		return result;
	}
}
