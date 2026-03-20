package Apple;

public class ReverseInteger_7_Medium {

	public static void main(String[] args) {
        // ✅ Normal cases
        System.out.println(reverse(123));          //  321
        System.out.println(reverse(-123));         // -321
        System.out.println(reverse(120));          //  21

        // ✅ Edge cases
        System.out.println(reverse(0));            //  0
        System.out.println(reverse(1));            //  1

        // ✅ Overflow cases → should return 0
        System.out.println(reverse(1534236469));   //  0
        System.out.println(reverse(Integer.MAX_VALUE)); // 0
        System.out.println(reverse(Integer.MIN_VALUE)); // 0
    }
	
	// Time: O(log x), one iteration per digit (log o base 10 because x/10 )
    // Space: O(1)
    private static int reverse(int x) {
        int rev = 0;
        while(x != 0) {
            int pop = x % 10;
            x = x/10;
            // Overflow check using dynamic boundary digits
            if(rev > Integer.MAX_VALUE / 10 
                || (rev == Integer.MAX_VALUE / 10 
                    && pop > Integer.MAX_VALUE % 10)) {
                return 0;
            }
            if(rev < Integer.MIN_VALUE / 10 
                || (rev == Integer.MIN_VALUE / 10 
                    && pop < Integer.MIN_VALUE % 10)) { 
                return 0;
            }
            rev = rev * 10 + pop;
        }
        return rev;
    }

}
