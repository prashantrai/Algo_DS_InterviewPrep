package test.practice.yhoo;

import java.util.Arrays;

public class CheckPermutationDemo {

	public static void main(String[] args) {

		// isPermutation("hannahb", "hannaha");
		System.out.println(">>Result:: " + checkPermutaion("abcbae", "abcbae"));
		// checkPermutaionByCharFrequency("Tact Coa", "Taco Cat");

	}

	// --ByCharFrequency
	public static boolean checkPermutaion(String s, String t) {

		if (s.length() != t.length())
			return false;

		int[] arr = new int[128];

		for (int i = 0; i < s.length(); i++) {

			arr[s.charAt(i)]++;
		}
//		System.out.println("arr:: "+Arrays.toString(arr));
		
		for (int i = 0; i < t.length(); i++) {

			int c = (int) t.charAt(i);
			arr[c]--;
			
			if (arr[c] < 0) {
				return false;
			}
		}
		return true;
	}

}
