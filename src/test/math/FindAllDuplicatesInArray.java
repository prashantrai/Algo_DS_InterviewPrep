package test.math;

import java.util.Arrays;

/*
 * Find duplicates in O(n) time and O(1) extra space
 * array of n elements which contains elements from 0 to n-1
 * 
 * http://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/
 * 
 * */

public class FindAllDuplicatesInArray {

	public static void main(String[] args) {

//		Integer[] arr = {1, 2, 3, 1, 3, 6, 6};
		Integer[] arr = {4, 2, 4, 5, 2, 3, 1, 6};
		
		findDuplicates(arr);
//		printRepeating(arr);
		
	}

	private static void findDuplicates(Integer[] arr) {
		
		System.out.println(Arrays.deepToString(arr));

		for(int i=0; i<arr.length; i++) {
			
			//--positive convert to negative
			if(arr[Math.abs(arr[i])] >= 0) {
				arr[Math.abs(arr[i])] = -arr[Math.abs(arr[i])];
				
			} else {
				System.out.println("Dupe: "+ (Math.abs(arr[i])) );
			}
			System.out.println(">>  "+Arrays.deepToString(arr));
		}
		System.out.println(Arrays.deepToString(arr));

		
	}
	
	static void printRepeating(Integer arr[])
    {
        int i;  
        int size = arr.length;
        System.out.println("The repeating elements are : ");
    
        for (i = 0; i < size; i++)
        {
            if (arr[Math.abs(arr[i])] >= 0)
                arr[Math.abs(arr[i])] = -arr[Math.abs(arr[i])];
            else
                System.out.print(Math.abs(arr[i]) + " ");
        }         
    } 

}
