package ctci.ch10.sorting.and.searching;

import java.util.BitSet;

// See this too: http://www.geeksforgeeks.org/find-duplicates-of-array-using-bit-array/



public class FindDuplicatesDemo {

	public static void main(String[] args) {
		
		int[] arr = {1, 5, 1, 10, 12, 10};
		checkDuplicates(arr);

	}
	
	public static void checkDuplicates(int[] arr) {
		
		BitSet bitSet = new BitSet(32000);  //--to hold 4kb of data as available size limit is 4kb (in problem)  
		
		for(int i=0; i<arr.length; i++) {
			
			int num = arr[i];
			int num0 = num -1;
			System.out.println("num="+num+", num0="+num0);
			if(bitSet.get(num0)) {
				System.out.println(num);
			} else {
				bitSet.set(num0);
			}
		}
		
		System.out.println("bitSet: "+bitSet);
		
	}

}
