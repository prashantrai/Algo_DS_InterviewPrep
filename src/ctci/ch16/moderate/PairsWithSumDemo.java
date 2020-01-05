package ctci.ch16.moderate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PairsWithSumDemo {

	public static void main(String[] args) {
		//int arr[] = { 2, 3, 4, -2, 6, 8, 9, 11, 4 };
		int arr[] = {1,8,8,9};
		System.out.println(pairsWithSum(arr, 9));
		pairsWithSum2(arr, 9);
		
	}

	//--Using sort
	//--Runtime O(N log N) (because of sorting ), search time in O(N)
	public static void pairsWithSum2(int[] arr, int sum) {
		
		Arrays.sort(arr);
		int left = 0;
		int right = arr.length-1;
		
		while (right > left) {
			int s = arr[left] + arr[right];
			
			if(s == sum) {
				System.out.println(arr[left] + ", " + arr[right]);
				left++;
				right--;
			} else {
				if(s < sum) 
					left++; //-- because array is in sorted order and if the sum is lower that means we need to move to next index from left
				else 
					right --;
			}
		}
		
	}
	//--Using HashMap
	public static List<Pair> pairsWithSum(int[] arr, int sum) {
		
		if(arr == null || arr.length == 0) return null;
		
		List<Pair> pairs = new ArrayList<>();
		HashMap<Integer, Integer> unpairedCount = new HashMap<>();
		
		for(int x : arr) {
			int y = sum - x;
			
			if(unpairedCount.getOrDefault(y, 0) > 0) {
				pairs.add(new Pair(x,y));
				
				/*
				 * decrement counter, because the value has been used once and 
				 * should not be paired with other numbers (if the occurence is one for this num)
				 */
				unpairedCount.put(y, unpairedCount.get(y)-1); 
			} else {
				if(unpairedCount.containsKey(x))
					unpairedCount.put(x, unpairedCount.get(x)+1);
				else 
					unpairedCount.put(x, 1);
			}
		}
		return pairs;
	}
	
	private static class Pair {
		
		int a, b;
		public Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public String toString() {
			return "[" + a + ", " + b + "]";
		}
	}
}
