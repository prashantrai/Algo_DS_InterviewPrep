package test.sorting;

import java.util.Arrays;


//https://www.interviewbit.com/tutorial/merge-sort-algorithm/
public class MergeSortDemo {

	
	public static Integer[] intArr = {5, 1, 12, -5, 20, 16, -1};
	private static Integer[] helper = new Integer[intArr.length]; //--Auxiliary space to merge the sorted arrays back.
	
	public static void main(String[] args) {
		doMergeSort();
	}

	private static void doMergeSort() {
		mergeSort(0, intArr.length-1);
	}

	private static void mergeSort(int low, int high) {
		
		if(low < high) {
			
			int mid = (low+high)/2;
			
			System.out.println("low="+ low +", mid="+ mid);
			mergeSort(low, mid);
			
			System.out.println("(mid+1)="+ (mid+1) +", high="+ high);
			mergeSort(mid+1, high);
			
			merge(low, mid, high);
		}
		
	}
	
	private static void merge(int low, int mid, int high) {
		System.out.println("low="+ low +", mid="+ mid+", high="+high);
		
		//--copy elements to helper arr
		for(int i=0; i<=high; i++) {
			helper[i] = intArr[i];
		}
		
		System.out.println("helper="+Arrays.deepToString(helper));
		System.out.println("intArr="+Arrays.deepToString(intArr));
		
		int i = low;
		int j = mid+1;
		int k = low;
		
		//--sort and copy to intArr
		while(i<=mid && j<=high) {
			
			if(helper[i] > helper[j]) {
				intArr[k] = helper[j];
				j++;
			} else {
				intArr[k] = helper[i];
				i++;
			}
			k++;
		}
		
		//--copy the rest element to intArr
		while(i<=mid) {
			intArr[k] = helper[i];
			i++;
			k++;
		}
		
		System.out.println("intArr="+Arrays.deepToString(intArr));
	}
	
	
	

}
