package leetcode;

public class SignOfTheProductOfAnArray_1822_Easy {

	public static void main(String[] args) {
		
		int[] arr_1 = {1, -1, 5, 6, -4, -3}; // number of negative elements - 3 i.e. Odd
		int[] arr_2 = {1, -1, -5, 6, -4, -3}; // number of negative elements - 4 i.e. Even
		int[] arr_3 = {1,5,0,2,-3}; // there is 0 so product will be 0
		
		System.out.println("Expected: -1, Actual: "+arraySign(arr_1));
		System.out.println("Expected: 1, Actual: "+arraySign(arr_2));
		System.out.println("Expected: 0, Actual: "+arraySign(arr_3));
	}
	// Codelity - Microsoft OA
	public static int arraySign(int[] A) {
		int sign = 1;
        for(int n : A) {
            if(n == 0) return 0;
            if(n < 0) 
                sign = -sign;
        }
        return sign;
    }

}
