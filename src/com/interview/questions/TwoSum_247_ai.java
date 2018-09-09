package com.interview.questions;

import java.util.HashMap;

public class TwoSum_247_ai {

	public static void main(String[] args) {

		int[] arr = {31, 4, -17, 67, -1};
		
		System.out.println(twoSum(arr, 14));
		System.out.println(twoSum_2(arr, 14));
		
	}
	
	public static boolean twoSum_2(int[] arr, int sum) {
		
		if (arr == null || arr.length == 0) {
			return false;
		}
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < arr.length; i++) {
			int target = sum - arr[i];
			
			if (map.containsKey(target)) {
				return true;
			} else {
				map.put(arr[i], arr[i]);
			}
		}
		return false;
		
	}

	public static boolean twoSum(int[] arr, int sum) {

		if (arr == null || arr.length == 0) {
			return false;
		}

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < arr.length; i++) {
			map.put(arr[i], arr[i]);
		}

		for (int i = 0; i < arr.length; i++) {
			int target = sum - arr[i];

			if (map.containsKey(target)) {
				return true;
			}
		}

		return false;
	}

}
