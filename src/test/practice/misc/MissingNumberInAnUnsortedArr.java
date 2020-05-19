package test.practice.misc;
public class MissingNumberInAnUnsortedArr {
 
	public static void main(String[] args) {
 
		int[] arr1={7,6,4,3};
		//int[] arr1={3,0,1};
		System.out.println("Missing number from array arr1: "+missingNumber(arr1));
		int[] arr2={5,3,1,2};
		System.out.println("Missing number from array arr2: "+missingNumber(arr2));
 
	}
 
	public static int missingNumber(int[] arr)
	{
		int n=arr.length+1;
		int sum=n*(n+1)/2;
		int restSum=0;
		for (int i = 0; i < arr.length; i++) {
			restSum+=arr[i];
		}
		int missingNumber=sum-restSum;
		return Math.abs(missingNumber);
	}
}