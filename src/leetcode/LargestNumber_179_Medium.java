package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LargestNumber_179_Medium {

	public static void main(String[] args) {
		int[] nums = {10,2};
		System.out.println("Expected: 210, Actual: " + largestNumber(nums));
		System.out.println("Expected: 210, Actual: " + largestNumber2(nums));
		
		int[] nums2 = {3,30,34,5,9};
		System.out.println("Expected: 9534330, Actual: " + largestNumber(nums2));
		System.out.println("Expected: 9534330, Actual: " + largestNumber2(nums2));
		
		int[] nums3 = {0,0};
		System.out.println("Expected: 0, Actual: " + largestNumber(nums3));
		System.out.println("Expected: 0, Actual: " + largestNumber2(nums3));
		
	}
	
	// Time: O(nlogn), Space: O(n)
	public static String largestNumber(int[] nums) {
        
        List<String> list = new ArrayList<>();
        for(int num : nums) {
            list.add(Integer.toString(num)); //convert to String
        }
        
        // we can also add elements in String[] and use Java 8 comparator as below
        // Arrays.sort(s_num, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));
        
        /*Collections.sort(list, 
                         (a, b) -> 
                            (int) (Long.parseLong(b+a) - Long.parseLong(a+b)));
        */
        // or, this better for large numbers
        Collections.sort(list, 
                         (s1, s2) -> 
                            (s2+s1).compareTo(s1+s2));
         
        StringBuilder sb = new StringBuilder();
        for(String s : list) {
            sb.append(s);
        }
        
        //remove leading zero
        while(sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }
            
        
        return sb.toString();
    }
	
	
	// Another approach with array
	public static String largestNumber2(int[] num) {
		if(num == null || num.length == 0)
		    return "";
		
		// Convert int array to String array, so we can sort later on
		String[] s_num = new String[num.length];
		for(int i = 0; i < num.length; i++)
		    s_num[i] = String.valueOf(num[i]);
			
		// Works: Comparator to decide which string should come first in concatenation
		/*Comparator<String> comp = new Comparator<String>(){
		    @Override
		    public int compare(String str1, String str2){
		        String s1 = str1 + str2;
				String s2 = str2 + str1;
				return s2.compareTo(s1); // reverse order here, so we can do append() later
		    }
	     };
	     Arrays.sort(s_num, comp);
	     */
	     
	     // or use Java 8 comparator
	     // we can also add elements in String[] and use Java 8 comparator as below
	     Arrays.sort(s_num, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));
		
		// An extreme edge case by lc, say you have only a bunch of 0 in your int array
		if(s_num[0].charAt(0) == '0')
			return "0";
            
		StringBuilder sb = new StringBuilder();
		for(String s: s_num)
	        sb.append(s);
		
		return sb.toString();
		
	}

}
