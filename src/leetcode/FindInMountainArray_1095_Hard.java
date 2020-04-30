package leetcode;

public class FindInMountainArray_1095_Hard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * // This is MountainArray's API interface. // You should not implement it, or
	 * speculate about its implementation interface MountainArray { public int
	 * get(int index) {} public int length() {} }
	 */
	
	//-- https://leetcode.com/problems/find-in-mountain-array/
	//https://leetcode.com/problems/find-in-mountain-array/discuss/378052/Binary-Search-Thinking-Process
	
	//--Runtime : log N

	public int findInMountainArray(int target, MountainArray mountainArr) {
		int peek = peakIndexInMountainArray(mountainArr);

		// --search in left or increasing sub-array
		int left = 0;
		int right = peek;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (mountainArr.get(mid) == target) {
				return mid;
			} else if (mountainArr.get(mid) < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		// --search in right or decreasing sub-array
		left = peek;
		right = mountainArr.length() - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (mountainArr.get(mid) == target) {
				return mid;
			} else if (mountainArr.get(mid) < target) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}

		return -1;
	}

	public int peakIndexInMountainArray(MountainArray mountainArr) {
		int left = 0;
		int right = mountainArr.length() - 1;
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return left;
	}

}

interface MountainArray {
	public int get(int index);
	public int length();
}
