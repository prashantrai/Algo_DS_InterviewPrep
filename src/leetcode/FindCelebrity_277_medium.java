package leetcode;

import java.util.HashSet;
import java.util.Stack;

public class FindCelebrity_277_medium {

	public static void main(String[] args) {

		boolean[][] celeb = {	{false, false, true},
								{true,  false, true},
								{false, false, false}
							};

		
		boolean[][] celeb_1 = {	{false, false, true},
								{false,  false, true},
								{true, false, false}
							};

		
		
		findCelebrity(celeb);
		findCelebrity2(celeb);
		System.out.println (findCelebrity3(celeb));
	}
	
	
	
	/*
	We want to minimize calls to knows(a, b).
	
	1) Single-pass candidate elimination (O(n))
	- Start with candidate = 0.
	- Scan i = 1 … n-1:
		- If knows(candidate, i) is true, then candidate cannot be 
		  the celebrity (a celeb knows nobody). Set candidate = i.
		- Otherwise, i cannot be the celebrity (since candidate does not know i), 
		  keep candidate as-is.
	- After this pass, candidate is the only possible celebrity.
	
	Why this works:
	Each comparison discards exactly one person from being the celebrity. 
	After n-1 checks, only one candidate remains.
	
	2) Verify the candidate (O(n))
	
	- For every i != candidate, check both:
		- knows(candidate, i) must be false (celebrity knows nobody).
		- knows(i, candidate) must be true (everyone knows the celebrity).
	- If any check fails → return -1.
	- Otherwise → return candidate.
	
	Total calls: up to (n-1) in step 1 + up to 2(n-1) in step 2 → O(n) calls.
	 */
	

	// 	Time: O(n) knows-queries.
	//	Space: O(1) extra space.
	 
	// Note: signature has been update to test here, actual leetcode question just 
	// passes the length and alrady provide a Relation class that takes care of knows() implementation
	//--Leetcode video:: https://www.youtube.com/watch?v=LZJBZEnoYLQ
	public static int findCelebrity3(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return -1;
		}
		
		// 1) Candidate elimination in one pass
		int candidate = 0;
		for (int i=1; i<celeb.length; i++) {
			if(knows(candidate, i, celeb)) {
				// candidate knows i => candidate cannot be celebrity
				candidate = i;
			}
		}
		
		// 2) Verification pass
		for (int i=0; i<celeb.length; i++) {
			
			 /**
			  * candidate != i :: Candidate is not himself
			  * &&
			  * knows(candidate, i, celeb) :: candidate knows the person - then also candidate is not a celeb
			  * OR
			  * !knows(i, candidate, celeb) :: If the person doesn't know the candidate
			  */
			// Candidate must know nobody, and everyone must know candidate
			if(candidate != i && knows(candidate, i, celeb) || !knows(i, candidate, celeb)) {
				return -1;
			}
		
		}		
		return candidate;	
				
	}

	public static void findCelebrity2(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return;
		}
		
		Stack<Integer> stack = new Stack<Integer>();

		for(int i=0; i<celeb.length; i++) {
			stack.push(i);
		}
		
		while (stack.size() > 1) {
			int a = stack.pop();
			int b = stack.pop();
		
			if(knows(a, b, celeb)) {
				stack.push(b);
			} else {
				stack.push(a);
			}
		}
		
		if (stack.isEmpty()) {
			System.out.println("No celeb");
		} else {
			System.out.println("Celeb is "+ (stack.peek() + 1));
		}
		
	}
	
	private static boolean knows(int a, int b, boolean[][] celeb) {
		
		return celeb[a][b];
	}
	
	
	
	
	
	
	public static void findCelebrity(boolean[][] celeb) {
		
		if(celeb == null || celeb.length == 0) {
			return;
		}
		
		HashSet<Integer> set = new HashSet<Integer>();
		int r = 0, c = 0;
		
		while (r < celeb.length && c < celeb[0].length) {
			
			if(!set.contains(r)) {
				if(celeb[r][c]) {
					set.add(r);
					if(celeb[c][r]) {  //--check if both person knows each other (by using column num as row and row as col num), if TRUE then add to set to skip iteration for that row
						set.add(c);
					}
					c = 0;
					r++;
				} else {
					c++;
				}
			} else {
				r++;
			} 
		}
		
		for(int i=0; i<celeb.length; i++) {
			if(!set.contains(i)) {
				System.out.println("celbrity is :: "+(i+1));
				return;
			} 
		}
		
		System.out.println("No celebrity found");
	}
	
}
