package test.samsung.pay;

public class DecimalDigitDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int solution(int A, int B) {
        // write your code in Java SE 8
        
     //   final int range = 100_000_000;
        final int RANGE = 100000000;
        
        int result;
        
        if( (A<0 || A > RANGE ) || (B<0 || B > RANGE )) {
        	return -1;
        	
        }
        
        char[] arrA = String.valueOf(A).toCharArray();
        char[] arrB = String.valueOf(B).toCharArray();
        
        String offset = "";
        //Former number mixed.
        for (int i = 0; i < arrA.length || i < arrB.length; i++) {
            if (i < arrA.length) {
                offset += arrA[i];
            }
            if (i < arrB.length) {
                offset += arrB[i];
            }
        }
        
        result = Integer.parseInt(offset);
        if (result > 100000000) {
            return -1;
        }
        return result;
        
    }

}
