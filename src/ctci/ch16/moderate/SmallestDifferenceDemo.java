package ctci.ch16.moderate;

import java.util.Arrays;

public class SmallestDifferenceDemo {

	public static void main(String[] args) {

		int[] a = {1, 2, 11, 15};
		int[] b = {4, 12, 19, 23, 127, 235};
		
		findSmallestDifference(a, b);
		
	}

	private static void findSmallestDifference(int[] a, int[] b) {

		Arrays.sort(a);
		Arrays.sort(b);
		
		int difference = Integer.MAX_VALUE;;
		int i = 0;
		int j = 0;
		
		while(i < a.length && j < b.length) {
			if (Math.abs(a[i] - b[j]) < difference) {
				difference = Math.abs(a[i] - b[j]);
			}
			if(a[i] > b[j]) {
				j++;
			} else {
				i++;
			}
		}
		System.out.println(difference);
	}

}
