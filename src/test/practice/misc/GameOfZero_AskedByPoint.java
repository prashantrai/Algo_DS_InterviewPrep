package test.practice.misc;
import java.util.*;

public class GameOfZero_AskedByPoint {
	
	/*
	"The Game of Zero"
	This is a game that allows user input. The user will input a list of integers that is comma delimited, and the game program should determine if we can traverse to 0 by following the list of numbers.

	Ex.:
	input> 1,2, 0

	Do not expect the input to always be properly formatted.

	The game logic will traverse the list from Left to Right, based on the number at its current index. We start with index 0. If we can no longer move Right, we attempt to move Left. If we can not move Left the game is over.

	**The program must ALWAYS terminate, no matter what the inputs are. No infinite loop stalls are allowed.**

	Ex 1.
	   [1, 2, 0] would fail because:

	   [1, 2, 0] 1 tells us to move Right 1 index.
	    ^

	   [1, 2, 0] 2 tells us to move Right 2 indices, but since we cannot, we attempt to move Left 2 indices. Since we cannot do that either, the game terminates with a failure condition (could NOT reach 0).

	Ex 2.
	  [1, 2, 0, 1] will succeed because:

	  [1, 2, 0, 1] (shift 1 to the right)
	   ^
	  [1, 2, 0, 1] (shift 2 to the right)
	      ^

	  We cannot move Right anymore, so we attempt to move Left.
	  [1, 2, 0, 1] (shift 1 to the left)
	            ^

	  The game is won!
	  [1, 2, 0, 1]
	         ^

	Ex 3.
	  [2, 0, 2] <- Make sure cases like this WILL terminate with failure.


	Test Cases:

	[]: Fail
	[0]: Succeed
	[1]: Fail
	[2, 0, 1, 1]: Fail
	[1, 2, 0, 2]: Fail
	[2, 1, 3, 0]: Fail
	[3, 0, 1, 1]: Fail
	*/
	

	public static void main(String[] args) {

		/* Test Cases
		 * []: Fail 
		 * [0]: Succeed 
		 * [1]: Fail 
		 * [2, 0, 1, 1]: Fail 
		 * [1, 2, 0, 2]: Fail 
		 * [2, 1, 3, 0]: Fail 
		 * [3, 0, 1, 1]: Fail
		 */
		int[] arr0 = {0};
	    int[] arr1 = {1, 2, 0, 1};
	    int[] arr2 = {1, 2, 0};
	    int[] arr3  = {2, 0, 2};
	    
	    int[] arr4  = {2, 0, 1, 1};
	    int[] arr5  = {1, 2, 0, 2};
	    int[] arr6  = {2, 1, 3, 0};
	    int[] arr7  = {3, 0, 1, 1};
	    
	    System.out.println("Expected: true,  Actual: "+gameZero(arr0));
	    System.out.println("Expected: true,  Actual: "+gameZero(arr1));
	    System.out.println("Expected: false, Actual: "+gameZero(arr2));
	    System.out.println("Expected: false, Actual: "+gameZero(arr3));
	    System.out.println("Expected: false, Actual: "+gameZero(arr4));
	    System.out.println("Expected: false, Actual: "+gameZero(arr5));
	    System.out.println("Expected: false, Actual: "+gameZero(arr6));
	    System.out.println("Expected: false, Actual: "+gameZero(arr7));
	}

	//--Working Solution
	public static boolean gameZero(int[] arr) {
		if (arr == null || arr.length == 0)
			return false;

		Set<Integer> visited = new HashSet<Integer>();

		return helper(arr, 0, false, true, visited);
	}

	public static boolean helper(int[] arr, int i, boolean goLeft, boolean goRight, Set<Integer> visited) {

		boolean result = false;

		if (i < 0 || i > arr.length)
			return false;

		if (goRight && arr[i] > arr.length - 1 - i)
			return false;

		if (goLeft && arr[i] > i)
			return false;

		if (visited.contains(i)) {
			return false;
		}

		visited.add(i);

		if (arr[i] == 0)
			return true;

		if (goRight)
			i += arr[i];
		if (goLeft)
			i -= arr[i];

		result = helper(arr, i, false, true, visited);

		// go left
		if (!result) {
			result = helper(arr, i, true, false, visited);
		}

		return result;
	}

}
