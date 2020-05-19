package leetcode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class LetterCasePermutation_784_Easy {
	
	public static void main(String[] args) {

		//letterCasePermutation2("abc");
		
		//Input: S = "3z4"
	    //Expected Output: ["3z4", "3Z4"]

		//letterCasePermutation2("3z4");
		
		//Input: S = "12345"
		//Expected Output: ["12345"]
		//letterCasePermutation2("12345");
		
		//Input: S = "a1b2"
		//Expected	Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
		//letterCasePermutation2("a1b2");
		
		//Input: S = "Jw"
		//Expected	Output: ["jw","JW", "jW","Jw"]
		//letterCasePermutation2("Jw");

		//input: "dnTJ"
		//Actual:           ["DNTJ","Dntj","DnTJ","DNtJ","DNTj","dNTJ","dNtj","dntj","dnTj","dntJ"]
		//Expected	Output: ["dntj","dntJ","dnTj","dnTJ","dNtj","dNtJ","dNTj","dNTJ","Dntj","DntJ","DnTj","DnTJ","DNtj","DNtJ","DNTj","DNTJ"]
		//letterCasePermutation3("dnTJ");
		letterCasePermutation3("abc");
		//letterCasePermutation2("YPkaXb");
		
		
	}
	
	//https://leetcode.com/problems/letter-case-permutation/discuss/115671/Java-9-lines-iterative-code-using-BFSbacktracking.
	public static List<String> letterCasePermutation(String S) {
        List<String> ans = new ArrayList<>(Arrays.asList(S));
        for (int i = 0; i < S.length(); ++i) { // Traverse string S char by char.
            for (int j = 0, sz = ans.size(); Character.isLetter(S.charAt(i)) && j < sz; ++j) { // S.charAt(i): letter, not digit.
                char[] ch = ans.get(j).toCharArray(); // transform to char[] the string @ j of ans.
                ch[i] ^= (1 << 5); // toggle case of charAt(i).
                ans.add(String.valueOf(ch)); // append to the end of ans.
            }
        }
        return ans;
    }
	
	
	public static List<String> letterCasePermutation3(String s) {
        Queue<String> dq = new ArrayDeque<>();
		dq.offer(s);

        for(int i=0; i<s.length(); i++) {
            if(Character.isDigit(s.charAt(i))) continue;
            int size = dq.size();
            for(int k=0; k<size; k++) {
                String st = dq.poll();
                char[] arr = st.toCharArray();
                
                arr[i] = Character.toUpperCase(arr[i]);
                dq.offer(String.valueOf(arr));
 
                arr[i] = Character.toLowerCase(arr[i]);
                dq.offer(String.valueOf(arr));

            }	
        }
		
        System.out.println(dq);
		return new ArrayList<String>(dq); 
    }
	
	//All test cases passed on Leetcode
	//https://leetcode.com/problems/letter-case-permutation/
	//working solution: mine
	//BFS
	public static List<String> letterCasePermutation2(String s) {
		
		Queue<String> dq = new ArrayDeque<>();
		dq.offer(s);
		Set<String> seen = new HashSet<String>();
		
		while(!dq.isEmpty()) {
		
			for(int k=0; k<dq.size(); k++) {
				String st = dq.poll();
				seen.add(st);
				for(int i=0; i<st.length(); i++) {
					
					char[] arr = st.toCharArray();
					if(!Character.isDigit(st.charAt(i))) { 
						if (Character.isLowerCase(arr[i]))
							arr[i] = (char) (arr[i] - 32);
						else 
							arr[i] = (char) (arr[i] + 32);
					}
					
					String temp = String.valueOf(arr);
					if(!seen.contains(temp))
						dq.offer(String.valueOf(arr));
					
					seen.add(String.valueOf(arr));
					
				}	
			}
		}
		
		return new ArrayList<String>(seen); 
	}

}
