package Dropbox;

public class MedianOfTwoSortedArrays_4_Hard {

	//--Refer YouTube video of Tushar Roy
	
	public static void main(String[] args) {

		int[] arr1 = {1, 3, 8, 9, 15};
		int[] arr2 = {7, 11, 18, 19, 21, 25};
		//int[] arr1 = {2};
		//int[] arr2 = {};

		System.out.println("Result: "+ findMedianSortedArrays(arr1, arr2));
	}

	// https://leetcode.com/problems/median-of-two-sorted-arrays/
	// https://www.youtube.com/watch?v=LPFhl65R7ww
	// https://github.com/mission-peace/interview/blob/master/src/com/interview/binarysearch/MedianOfTwoSortedArrayOfDifferentLength.java
	//https://medium.com/@hazemu/finding-the-median-of-2-sorted-arrays-in-logarithmic-time-1d3f2ecbeb46
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        int x = nums1.length;
        int y = nums2.length;
        
        if(x > y) { //make sure we always search in shoter array to reduce the search time
            return findMedianSortedArrays(nums2, nums1); // we can also simpy swap the arrays
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
