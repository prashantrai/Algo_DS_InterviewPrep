package ctci.ch10.sorting.and.searching;

import java.util.Arrays;

public class SortedMergeDemo {

	public static void main(String[] args) {
		
		int[] a = {5,6,7,8,0,0,0,0};
		int[] b = {1,2,3,4};
		merge(a, b, 4, b.length);
		
		System.out.println(">>Result:: "+Arrays.toString(a));
		
	}
	
	public static void merge(int[] a, int[] b, int lastA, int lastB) {
	
		int indexA = lastA - 1;
		int indexB = lastB - 1;
		int indexMerged = lastA + lastB - 1;
		
		while(indexB >=0) {
			
			if(indexA >=0 && a[indexA] > b[indexB]) {
				a[indexMerged] = a[indexA];
				indexA--;
			} else {
				a[indexMerged] = b[indexB];
				indexB--;
			}
			indexMerged--;
		}
		
	
	}

}



/*
 * Design a data structure that would be used to store categories of
 * restaurants (i.e. American, Chinese, Mexican, etc) 
 * and this data structure should have inserts, removes, search and getRandom in O(1)
 */