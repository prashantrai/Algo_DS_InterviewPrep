package leetcode;

public class ReverseString_II_541_Easy {

	public static void main(String[] args) {
		String s = "abcdefg"; 
		int k = 2;
		System.out.println("Expected: bacdfeg, Actual: "+ reverseStr(s, k));
	}
	
	// Time: O(N)
    // Space: O(N)
    public static String reverseStr(String s, int k) {
        char[] a = s.toCharArray();
        
        for(int i=0; i < a.length; i += 2*k) {
            int left = i;
            int right = Math.min(i + k - 1, a.length-1);
            while(left < right) {
                char temp = a[left];
                a[left++] = a[right];
                a[right--] = temp;
            }
        }
        return new String(a);
    }

}
