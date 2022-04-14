package interview;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Stream;

public class GuideWire {

	public static void main(String[] args) {

		//String s = "13 DUP 4 POP 5 DUP + DUP + -"; //--returns 7
		//String s = "3 DUP 5 - -"; //-- returns -1
		//String s = "5 6 + -"; //-- returns -1
		String s = "5 6 - +"; //-- returns -1
		
		GuideWire gw = new GuideWire();
		int res = gw.solution(s);
		System.out.println("Result::: "+res);
		
	}
	
	public int solution(String S) {
        // write your code in Java SE 8
		String[] arr = S.split(" ");
		System.out.println(Arrays.toString(arr));
		
		Stack<Integer> stk = new Stack<Integer>();
		
		for(String s : arr) {
			
			if("DUP".equalsIgnoreCase(s)) {
				if(stk.size() > 0)
					stk.push(stk.peek());
				
			} else if("POP".equalsIgnoreCase(s)) {
				if(stk.size() > 0)
					stk.pop();
			} else if("+".equalsIgnoreCase(s)) {
				
				if(stk.size() < 2) return -1;

				int v1 = stk.pop();
				int v2 = stk.pop();
				stk.push(v1 + v2);
					
				
			} else if("-".equalsIgnoreCase(s)) {
				
				if(stk.size() < 2) return -1;
				
				int v1 = stk.pop();
				int v2 = stk.pop();
				stk.push(v1 - v2);
				
				
			} else { //--assumption if none of the above then num only
				stk.push(Integer.parseInt(s));
			}
			System.out.println(stk);
		}
		
		return stk.size() > 0 ? stk.peek() : -1;
    }
    
    
}
