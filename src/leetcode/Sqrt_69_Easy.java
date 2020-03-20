package leetcode;

public class Sqrt_69_Easy {

	public static void main(String[] args) {

		mySqrt(4);
	}

	
	//https://leetcode.com/problems/sqrtx/discuss/542382/Easy-to-understand-O(logn)
	public static int mySqrt(int x) {

		if (x == 0)
			return 0;

		int start = 1;
		int end = x;
		int ans = 0;

		while (start <= end) {

			int mid = (start + end) / 2;

			if (mid <= x / mid) {
				ans = mid;
				start = mid + 1;
			} else {
				end = mid - 1;
			}

		}

		return ans;

	}
}
