package Facebook;

public class AddStrings_415_Easy {

	public static void main(String[] args) {
		String num1 = "11", num2 = "123";
		System.out.println("Expected: 134, Actual: " + addStrings(num1, num2));
	}
	
	/*
    Time: O(N), where N is size of the the largest string 
    between num1 and num2.
    
    Space: O(N), space required is based on the size of the the largest string 
    between num1 and num2.
    */
    public static String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        
        int carry = 0;
        int p1 = num1.length()-1;
        int p2 = num2.length()-1;
        
        while(p1>=0 || p2>=0) {
            int x1 = p1>=0 ? num1.charAt(p1) - '0' : 0;
            int x2 = p2>=0 ? num2.charAt(p2) - '0' : 0;
            int value = (x1+x2+carry) % 10;
            carry = (x1+x2+carry) / 10;
            res.append(value);
            p1--;
            p2--;
        }
        
        if(carry != 0) {
            res.append(carry);
        }
        
        return res.reverse().toString();
    }

}
