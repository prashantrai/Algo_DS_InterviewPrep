package leetcode;

public class ValidMountainArray_941_Easy {

	public static void main(String[] args) {
		int[] arr = {0,3,2,1};
		System.out.println("Expected: true, Actual: " + validMountainArray(arr));
		
		int[] arr2 = {3,5,5};
		System.out.println("Expected: false, Actual: " + validMountainArray(arr2));
	}
	
	// Time: O(N), Space: O(1)
	public static boolean validMountainArray(int[] arr) {
        int n = arr.length-1;
        int i = 0;
        
        // walk up
        while(i+1 <= n && arr[i] < arr[i+1])
            i++;
        
        // there no path down from peak or it never started i.e. plain
        if(i == 0 || i == n) 
            return false;
        
        // walk down
        while(i+1 <= n && arr[i] > arr[i+1])
            i++;
        
        return i == n;
    }

}
