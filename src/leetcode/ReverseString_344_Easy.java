package leetcode;

public class ReverseString_344_Easy {

	public static void main(String[] args) {
		String s = "hello";
		reverseString(s.toCharArray());

	}

	/*
    Time: O(N), to swap N/2 elements
    Space: O(1)
    */
    public static void reverseString(char[] s) {
        int left = 0;
        int right = s.length-1;
        while (left < right) {
            char temp = s[left];
            s[left++] = s[right];
            s[right--] = temp;
        }
        System.out.println(new String(s));
    }
}
