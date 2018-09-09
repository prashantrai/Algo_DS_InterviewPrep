package test.samsung.pay;

import java.util.Arrays;

public class SumDistanceDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//int[] A = {1, 3, -3};
		int[] A = {-8, 4, 0, 5, -3, 6};
		
		System.out.println(sumDistance(A));
		
	}
	
	
	public static int sumDistance(int[] A) {
		int index = 0;
		int result = 0;

		for(int i = 0; i < A.length; i++) {
		    int total = A[i] + i + A[index] - index;
		    
		    System.out.println("=> "+A[i] +"+"+ i +"+"+ A[index] +"-"+ index);
		    
		    System.out.println("total:: "+total);
		    
		    result = Math.max(result, total);
		    
		    total = A[i] + i + A[i] - i;
		    result = Math.max(result, total);
		    
		    if(A[i] - i > A[index] - index){
		        index = i;
		    }
		}
		return result;
	}
	
}
