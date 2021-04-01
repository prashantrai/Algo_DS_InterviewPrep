package microsoft;

public class FindSignOfProductOfAllElementsInArray_MicrosoftOnlineTest {

	public static void main(String[] args) {
		
		int[] arr_1 = {1, -1, 5, 6, -4, -3}; // number of negative elements - 3 i.e. Odd
		int[] arr_2 = {1, -1, -5, 6, -4, -3}; // number of negative elements - 4 i.e. Even
		
		signOfProduct(arr_1); // Expected: Negative
		signOfProduct(arr_2); // Expected: Positive
	}
	
	public static void signOfProduct(int[] arr) {
		
		if(arr == null || arr.length == 0) return;
		
		int count = 0;
		
		for(int n : arr) {
			if(n < 0) {
				count++;
			}
		}
		
		if(count % 2 == 0) 
			System.out.println("Sign of product of all element: Positive");
		else 
			System.out.println("Sign of product of all element: Negative");
		
	} 
	
	public int solution(int[] A) {
        //if(A == null || A.length == 0) return 0;
        int count = 0;
        for(int n : A) {
            if(n == 0) return 0;
            if(n < 0) 
                count++;
        }

        if(count > 0 && count%2 == 0) return 1;
        else if (count > 0 && count%2 != 0 ) return -1;

        return 0;
    }

}
