
public class Test2 {

	public static void main(String[] args) {
		
		System.out.println("1>>> Expected: aabbaa: Actual: " + solution("?ab??a"));
		System.out.println("2>>> Expected: NO: Actual: " + solution("bab??a"));
		System.out.println("2>>> Expected: aaa: Actual: " + solution("?a?")); // "zaz" is also correct

	}

	/* Deepak's Codeity Test: 
	 * Make and return palindrome by replacing '?' in the input string 
	 * with characters from a-z, return "NO" if not possible.
	 * */
	public static String solution(String S) {
        
        int i=0, j=S.length()-1;
        char[] arr = S.toCharArray();
        while (i < j) {
            char i_char = arr[i];
            char j_char = arr[j];
            
            // when char are not same
            if(i_char != '?' && j_char != '?' && i_char != j_char) {
                return "NO";
            }
            
            if(i_char != '?' || j_char != '?') {
                if(i_char == '?') {
                    arr[i] = arr[j];
                } else {
                    arr[j] = arr[i];
                }
            }
            else if (i_char == '?' && j_char == '?') {
                arr[i] = 'a';
                arr[j] = 'a';
            }
            
            i++;
            j--;
        }

     return new String (arr);
    }
	
}
