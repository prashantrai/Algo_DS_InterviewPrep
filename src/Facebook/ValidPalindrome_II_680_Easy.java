package Facebook;

public class ValidPalindrome_II_680_Easy {

	public static void main(String[] args) {
		String s = "aba";
		System.out.println("Expected: true, Actual: "+ validPalindrome(s));

	}
	
	/*
    Time: O(N), because we are scanning all chars
    Space: O(1)
    */
    public static boolean validPalindrome(String s) {
       int i = 0;
       int j = s.length()-1;

       while(i <= j){
        if(s.charAt(i) == s.charAt(j)){
            i++;
            j--;
        } else {
        	// now check, by call helper method after deleting/ignoring current left 
            // char (i.e. start=i+1) OR current right (end = j-1), if the 
            // rest of the string is palindrom or not (in either case), 
            // if yes, then return true 
            // i.e. by deleting one char input string will be a valid palindrom 
            return isPalindrome(s, i+1, j) || isPalindrome(s, i, j-1);
        }
       } return true;

    }
    
    public static boolean isPalindrome(String s, int start, int end){
        while(start <= end){
            if(s.charAt(start) == s.charAt(end)){
                start++;
                end--;
            }  else return false;
        } return true;
    }

}
