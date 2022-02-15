package microsoft;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ArrayWith2NumDifferBy1_OA_Codelity {

	public static void main(String[] args) {
		
		int[] arr = {9};
		System.out.println("Expected: 0, Actual: " + solution(arr));
		
		int[] arr2 = {5,4};
		System.out.println("Expected: 1, Actual: " + solution(arr2));
		
		int[] arr3 = {12, 2, 9, 13, 15};
		System.out.println("Expected: 1, Actual: " + solution(arr3));
	}
	
	/*
	3- given an array arr of n positive integers returns 1 if arr contains at 
	least two items which differ by 1 otherwise 0.
	
	Given, arr=[9], the method should return 0.
	Given, arr=[5,4], the method should return 1.
	Given, arr=[12, 2, 9, 13, 15], the method should return 1. Pair of items which differ by 1 is (12, 13)

	*/
	
	public static int solution(int[] arr) {
		
		if(arr == null || arr.length == 0 || arr.length == 1) return 0;
		
		//When array is of type Object in this case Integer
		//Set<Integer> set = new HashSet<>(Arrays.asList(arr));

		//for primitive 
		Set<Integer> set = Arrays.stream(arr).boxed().collect(Collectors.toSet());
		
		for(int i : arr) {
			if(set.contains(i+1)) {
				return 1;
			}
		}
		return 0;
	}
}
