package Facebook;

public class MinimumAddToMakeParenthesesValid_921_Medium {

	public static void main(String[] args) {
		String s = "())";
		System.out.println("Expected: 1, Acual: "+minAddToMakeValid(s));
		
		s = "(((";
		System.out.println("Expected: 3, Acual: "+minAddToMakeValid(s));
		
		s = "()";
		System.out.println("Expected: 0, Acual: "+minAddToMakeValid(s));
		
		s = "()))((";
		System.out.println("Expected: 4, Acual: "+minAddToMakeValid(s));

	}
	
	
	// Time: O(N)
    // Space: O(1)
	public static int minAddToMakeValid(String S) {
        int left = 0, right = 0;
        for(char c : S.toCharArray()) {
            if(c == '(') {
                right++;
            } else if(right > 0) {
                right--;
            } else {
                left++;
            }
        }
        return left + right;
    }

}
