package Grammarly;

import java.util.Stack;

public class RemoveAllAdjacentDuplicatesInString_II_1209_Medium {

	public static void main(String[] args) {
		String s = "abcd"; int k = 2;
		System.out.println("Expected: abcd, Actual: " + removeDuplicates(s, k));
		System.out.println("Expected: abcd, Actual: " + removeDuplicates2(s, k));
		
		s = "deeedbbcccbdaa"; k = 3;
		System.out.println("Expected: aa, Actual: " + removeDuplicates(s, k));
		System.out.println("Expected: aa, Actual: " + removeDuplicates2(s, k));
		
		s = "pbbcggttciiippooaais"; k = 2;
		System.out.println("Expected: ps, Actual: " + removeDuplicates(s, k));
		System.out.println("Expected: ps, Actual: " + removeDuplicates2(s, k));
	}

	/* Also look at the implementation with 2 stack added after this
    
    Time and Space: O(N)
    */
    // Use StringBuilder as stack and int[] count to maintain count of char
    public static String removeDuplicates(String s, int k) {
        int[] count = new int[s.length()];
        StringBuilder sb = new StringBuilder();
        
        for(char c : s.toCharArray()) {
            sb.append(c);

            int len = sb.length() - 1;
            
            /*
            if len is greater than 0 and current char is matching last char 
            then increament the count in count[] at last index i.e. current 
            else  insert 1 in count[] for current index
            */
            count[len] 
                = 1 + (len > 0 && sb.charAt(len) == sb.charAt(len-1) ? count[len-1] : 0);
            /* another way - works
            if(len > 0 && sb.charAt(len) == sb.charAt(len-1)) {
                count[len] = 1 + count[len-1]; //increment the char count
            } else {
                count[len] = 1;
            } */
            
            
            if(count[len] >= k) {
                sb.delete(sb.length() - k, sb.length());
            }
        }
        
        return sb.toString();
    }
    
    
    /* Using 2 stacks
    Time and Space: O(N)
    */
    public static String removeDuplicates2(String s, int k) {
        Stack<Character> charStk = new Stack<>();
        Stack<Integer> countStk = new Stack<>();
        
        for(char c : s.toCharArray()) {
            if(!charStk.isEmpty() && charStk.peek() == c)
                countStk.push(countStk.peek() + 1);
            else 
                countStk.push(1);
            
            charStk.push(c);
            
            if(countStk.peek() == k) {
                for(int i=0; i<k; i++) {
                    charStk.pop();
                    countStk.pop();
                }
            }            
        }
        
        StringBuilder sb = new StringBuilder();
        for(char c : charStk) {
            sb.append(c);
        }
        
        return sb.toString();
    }
	
}
