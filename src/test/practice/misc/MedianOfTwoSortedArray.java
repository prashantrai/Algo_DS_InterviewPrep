package test.practice.misc;

public class MedianOfTwoSortedArray {
	

	//--Refer YouTube video of Tushar Roy
	
	public static void main(String[] args) {

		int[] arr1 = {1, 3, 8, 9, 15};
		int[] arr2 = {7, 11, 18, 19, 21, 25};

		System.out.println("Result: "+ findMedianOfSortedArr(arr1, arr2));
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
					return (Integer.max(maxLeftX, maxLeftY) + Integer.min(minRightX, minRightY))/2;
				} else  { //--if odd
					return Integer.max(maxLeftX, maxLeftY);
				}
			} else if (maxLeftX > minRightY) {
				high = partitionX-1;
			} else {
				low = partitionX + 1;
			}
			
			
		}
		
		throw new IllegalArgumentException();
		
	}
	

}
