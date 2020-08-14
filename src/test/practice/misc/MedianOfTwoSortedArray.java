package test.practice.misc;

public class MedianOfTwoSortedArray {
	

	//--Refer YouTube video of Tushar Roy
	
	public static void main(String[] args) {

//		int[] arr1 = {1, 3, 8, 9, 15};
//		int[] arr2 = {7, 11, 18, 19, 21, 25};
		int[] arr1 = {2};
		int[] arr2 = {};

		System.out.println("Result: "+ findMedianOfSortedArr(arr1, arr2));
		System.out.println("Result: "+ findMedianSortedArrays(arr1, arr2));
	}

	//--Looks good-Working slution
	public static double findMedianOfSortedArr (int[] arr1, int[] arr2) throws IllegalArgumentException {
		
		if(arr1.length > arr2.length) {
			return findMedianOfSortedArr(arr2, arr1);
		}
		
		int x = arr1.length;
		int y = arr2.length;

		//--partition
		int low = 0;
		int high = x;
		//--do a binary search
		while (low <= high) {
			
			int partitionX = (low+high)/2;
			int partitionY = (x + y + 1) /2 - partitionX;
			
			/* if partitionX is 0 means nothing is there of left side and return MIN of integer
			 * if partitionX is equal to length of input then there is nothing on the right side and return MAX of integer 
			 * 
			 * */
			int maxLeftX  = partitionX == 0 ? Integer.MIN_VALUE : arr1[partitionX -1];
			int minRightX = partitionX == x ? Integer.MAX_VALUE : arr1[partitionX];
			
			int maxLeftY  = partitionY == 0 ? Integer.MIN_VALUE : arr2[partitionY -1];
			int minRightY = partitionY == y ? Integer.MAX_VALUE : arr2[partitionY];
			
			if(maxLeftX <= minRightY && maxLeftY <= minRightX) {
				//--We have partitioned the array at correct place
				
				//-if even length
				if( (x+y) % 2 == 0) {
					return ((double)Integer.max(maxLeftX, maxLeftY) + Integer.min(minRightX, minRightY))/2;
				} else  { //--if odd
					return (double)Integer.max(maxLeftX, maxLeftY);
				}
			} else if (maxLeftX > minRightY) {
				high = partitionX-1;
			} else {
				low = partitionX + 1;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static double findMedianSortedArrays(int input1[], int input2[]) {
        //if input1 length is greater than switch them so that input1 is smaller than input2.
        if (input1.length > input2.length) {
            return findMedianSortedArrays(input2, input1);
        }
        int x = input1.length;
        int y = input2.length;

        int low = 0;
        int high = x;
        while (low <= high) {
            int partitionX = (low + high)/2;
            int partitionY = (x + y + 1)/2 - partitionX;

            //if partitionX is 0 it means nothing is there on left side. Use -INF for maxLeftX
            //if partitionX is length of input then there is nothing on right side. Use +INF for minRightX
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : input1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : input1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : input2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : input2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                //We have partitioned array at correct place
                // Now get max of left elements and min of right elements to get the median in case of even length combined array size
                // or get max of left for odd length combined array size.
                if ((x + y) % 2 == 0) {
                    return ((double)Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY))/2;
                } else {
                    return (double)Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) { //we are too far on right side for partitionX. Go on left side.
                high = partitionX - 1;
            } else { //we are too far on left side for partitionX. Go on right side.
                low = partitionX + 1;
            }
        }

        //Only we we can come here is if input arrays were not sorted. Throw in that scenario.
        throw new IllegalArgumentException();
    }
	
	
	public static double findMedianSortedArraysLeet(int[] nums1, int[] nums2) {
        
        int x = nums1.length;
        int y = nums2.length;
        
        if(x > y) { //make sure we always search in shoter array to reduce the search time
            findMedianSortedArrays(nums2, nums1);
        }
    
        // partition
        int low = 0;
        int high = x;
        
        // do a binary search
        while(low <= high) {
            
            int partitionX = (low + high)/2;
            
            //having +1 handles both odd and even len arrays
            int partitionY = (x + y + 1)/2 - partitionX;
            
            /* if partitionX is 0 means nothing is there of left side and return MIN of integer
			 * if partitionX is equal to length of input then there is nothing on the right side and return MAX of integer 
			 * */
            int maxLeftX  = partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX - 1]; // partitionX == 0 means all the element on the right side after partition
            int minRightX = partitionX == x ? Integer.MAX_VALUE : nums1[partitionX]; // partitionX == x means all the element on the left side after partition
            
            int maxLeftY   = partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY  = partitionY == y ? Integer.MAX_VALUE : nums2[partitionY];
        
            if(maxLeftX <= minRightY && maxLeftY <= minRightX) {
                //even length
                if((x+y)%2 == 0) {
                    return ((double) Integer.min(minRightX, minRightY) + Integer.max(maxLeftX, maxLeftY))/2;
                } else { // odd
                    return (double) Integer.max(maxLeftX, maxLeftY); 
                }
            } else if(maxLeftX > minRightY){
                high = partitionX - 1;
            } else {
                low = partitionX + 1;
            }
        
        }
        
        throw new IllegalArgumentException();
        
    }

}
