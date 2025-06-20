package leetcode;

public class ReverseWordsInAString_III_557_Medium {

	public static void main(String[] args) {
		String s = "Let's take LeetCode contest";
		System.out.println("Expected: s'teL ekat edoCteeL tsetnoc");
		System.out.println("Actual: " + reverseWords(s));
	}
	
	
	// Time: O(N)
    // Space: O(N)
    public static String reverseWords(String s) {
        char[] chArr = s.toCharArray();
        for(int i=0; i<chArr.length; i++) {
            if(chArr[i] == ' ') continue;
            
            int j = i;
            // move j to the end of the word
            while( j+1 < chArr.length && chArr[j+1] != ' ') 
                j++;
            
            reverse(chArr, i, j);
            
            i = j;
        }
        return new String(chArr);
    }
    
    private static void reverse(char[] a, int left, int right){
        while(left < right) {
            char temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

}
