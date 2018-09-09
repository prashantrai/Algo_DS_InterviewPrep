package test.practice.misc;

import java.util.HashSet;

public class LongestConsecutiveSubsequenceDemo {

	public static void main(String[] args) {

		int[] arr = {1,9,3,10,4,20,2};  //--Result will be 4 because of 1,2,3,4
		findLongestConsecutiveSubsequence(arr);
		
		int[] arr2 = {36, 41, 56, 35, 44, 33, 34, 92, 43, 32, 42};
		findLongestConsecutiveSubsequence(arr2);
		
	}
	
	public static void findLongestConsecutiveSubsequence (int[] arr) {
		
		HashSet<Integer> set = new HashSet<Integer>();
		int ans = 0;
		
		for(int i : arr) {
			set.add(i);
		}
		
		for(int i : arr) {
			int count = 0;
			if(!set.contains(i-1)) {  //--if there is no value less than the current value then it's the starting point else continue to next value in arr
				int curr = i;
				count = 1;
				while(set.contains(curr + 1)) {
					count++;
					curr +=1;
				}
				ans = Math.max(ans, count);
			}
		}//--for closed
		
		System.out.println(ans);
	}

}
