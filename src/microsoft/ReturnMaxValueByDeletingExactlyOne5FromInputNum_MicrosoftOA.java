package microsoft;

public class ReturnMaxValueByDeletingExactlyOne5FromInputNum_MicrosoftOA {

	public static void main(String[] args) {
		System.out.println("Expected: 198, Actual: " + findLargeNum(1598));
		System.out.println("Expected: 15,098, Actual: " + findLargeNum(150958));
		System.out.println("Expected: -589, Actual: " + findLargeNum(-5859));
		
		System.out.println("\nExpected: 198, Actual: " + findLargeNum2(1598));
		System.out.println("Expected: 15,098, Actual: " + findLargeNum2(150958));
		System.out.println("Expected: -589, Actual: " + findLargeNum2(-5859));
	}
	
	/*
	 	Given an integer(+ve or -ve) consisting of at least one 5 in its digits(can have more than one 5). 
		
		Return the maximum value by deleting exactly one 5 from its digit.
		
		Ex: N = 1598 => result = 198(remove the only 5 from the sequence)
		N = 150,958 => result = 15,098(we wanna return the maximum value 15,098 > 10,958)
		N = -5859 => result = -589 ( -589>- 859)
	 */

	
	public static int findLargeNum2(int num) {
		if(num >= -10 && num <= 10) //check this when you see question, whether we need this or not
	        return num;
		
		int res = Integer.MIN_VALUE;
		StringBuilder sb = new StringBuilder(Integer.toString(num));
		
		for(int i=0; i<sb.length(); i++) {
			if(sb.charAt(i) == '5') {
				sb.deleteCharAt(i);
				res = Math.max(res, Integer.valueOf(sb.toString()));
				sb.insert(i, '5');
			}
		}
		
		return res > Integer.MIN_VALUE ? res : num;
	}
	
	public static int findLargeNum(int num){
	    if(num >= -10 && num <= 10)
	        return num;

	    StringBuilder sb = new StringBuilder(num+"");
	    int res = Integer.MIN_VALUE;
	    boolean flg = false;

	    for(int i=0; i<sb.length(); i++){

	        if(sb.charAt(i) == '5'){
	            sb.deleteCharAt(i);
	            res = Math.max(res, Integer.valueOf(sb.toString()));
	            sb.insert(i, '5');
	            flg = true;
	        }

	    }

	   if(flg) return res;
	    return  num;
	}
}
