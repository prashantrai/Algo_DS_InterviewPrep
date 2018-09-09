package test.practice.misc;

import java.util.Arrays;
import java.util.HashSet;

class Main
{
    static boolean checkDuplicatesWithinK(int arr[], int k)
    {
        // Creates an empty hashset
        HashSet<Integer> set = new HashSet<Integer>();
 
        // Traverse the input array
        for (int i=0; i<arr.length; i++)
        {
            // If already present n hash, then we found 
            // a duplicate within k distance
            if (set.contains(arr[i]))
               return true;
 
            // Add this item to hashset
            set.add(arr[i]);
 
            // Remove the k+1 distant item
            if (i >= k)
              set.remove(arr[i-k]);
        }
        return false;
    }
 
    // Driver method to test above method
    public static void main (String[] args)
    {
        
    	/*int arr[] = {3, 5, 10, 4, 7, 6, 1, 8, 3};
        if (checkDuplicatesWithinK(arr, 3))
           System.out.println("Yes");
        else
           System.out.println("No");*/
    	
    	int n = countWays(5);
    	System.out.println(n);
    }
    
    public static int countWays(int n) {
    	Integer[] memo = new Integer[n+1];
		Arrays.fill(memo, -1);
    	int res = countWays(n, memo);
    	return res;
    }
    
    public static int countWays(int n, Integer[] memo) {
    	
    	if(n<0) return 0;
    	
    	if(n == 0) return 1;
    	
    	if(memo[n] == -1) {
    		memo[n] = countWays(n-1, memo) + countWays(n-2, memo) + countWays(n-3, memo);
    	}
    	
    	return memo[n];
    }
}