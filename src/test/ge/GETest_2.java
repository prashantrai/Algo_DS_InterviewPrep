package test.ge;

import java.util.ArrayList;
import java.util.Arrays;

public class GETest_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] nums = {2,10,5,4,8};
		int[] maxes = {3,1,7,8};
		//int[] maxes = {8};
		
		int arr[]  = counts(nums, maxes);
		for(int ii : arr) {
        	System.out.println("%%%:: "+ii);
        }
		

	}
	
	static int[] counts(int[] nums, int[] maxes) {
        int[] arr = new int[maxes.length];
        int valCount = 0;
        
		Arrays.sort(nums); //n log(n)
		int j;
        for(int i=0; i<maxes.length; i++) {
            valCount = 0;
            j = 0;
            while(nums[j] <= maxes[i]) {
                valCount++;
                j++;
            }
            arr[i] = valCount;
        }
        return arr;
        
    }

}
