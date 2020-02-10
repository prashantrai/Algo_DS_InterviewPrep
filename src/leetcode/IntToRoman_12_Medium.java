package leetcode;

public class IntToRoman_12_Medium {

	public static void main(String[] args) {

		System.out.println(intToRoman(33));
		System.out.println(intToRoman2(33));
	}
	
	//--https://leetcode.com/problems/integer-to-roman/
	//--https://leetcode.com/problems/integer-to-roman/submissions/
	public static String intToRoman(int num) {
		
		String[] dict = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
	    int[] val = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
	    //--var result = "";  //--var is working as String and this is java code
		StringBuilder result = new StringBuilder();
	    for(int i=0; i<val.length; i++) {
	    	int v = val[i];
	    	
	    	if(num >= v) {
	    		int count = num/v;
	    		num = num % v;
	    	
	    		for(int j=0; j<count; j++) {
	    			result.append(dict[i]);
	    		}
	    	}
	    }
	    return result.toString();
	}
	
	//--https://leetcode.com/problems/integer-to-roman/discuss/492037/JAVA-EASIEST-Solution-(MUST-READ)!
	public static String intToRoman2(int num) {

		String[] thousands = {"","M","MM","MMM"};
		String[] hundreds = {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
		String[] tens = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
		String[] ones = {"","I","II","III","IV","V","VI","VII","VIII","IX"};

		StringBuilder sol = new StringBuilder();
		sol.append(thousands[num/1000])
			.append(hundreds[(num%1000)/100])
			.append(tens[(num%100)/10])
			.append(ones[num%10]);
			
		return  sol.toString();
	}

}
