package leetcode;
public class SecondLargestElementInArray {
    static int secondLargest(int[] a) {
    	int max1 = a[0];
    	int max2 = a[0];
    	
    	for(int i=1; i<a.length; i++) {
    		if(a[i] > max1) {
    			max2 = max1; 
    			max1 = a[i];
    		}
    		else if(a[i] > max2) {
    			max2 = a[i];
    		}
    	}
    	return max2;
    }

    public static void main(String[] args) {
    	int[] array = {12, 21, 333, 23, 201, 202, 9, 202};
        System.out.println("Expected: 202, Actual: "+secondLargest(array));
        
        int[] array2 = {2, 2, 2, 2, 2, 2, 2, 2};
        System.out.println("Expected: 2, Actual: "+secondLargest(array2));

    }
}