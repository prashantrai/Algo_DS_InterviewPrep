package microsoft;

public class DivideSticksToFindSideLengthOfLargestSquare_OA_Codelity {

	/*
	 * See these as well (not exactly same problem):
	 * https://www.geeksforgeeks.org/dividing-sticks-problem/
	 * https://leetcode.com/problems/matchsticks-to-square/
	 */

	public static void main(String[] args) {

		System.out.println("Expected: 7, Actual: " + solution(10, 21));
		System.out.println("Expected: 5, Actual: " + solution(13, 11));
		System.out.println("Expected: 0, Actual: " + solution(2, 1));
		System.out.println("Expected: 2, Actual: " + solution(1, 8));
		
	}

	/*
		There are two wooden sticks of lengths A and B respectively. Each of them can be cut into shorter 
		sticks of integer lengths. Our goal is to construct the largest possible square. In order to do this, 
		we want to cut the sticks in such a way as to achieve four sticks of the same length 
		(note that there can be some leftover pieces). 
		
		What is the longest side of square that we can achieve?
		
		Write a function that given two integers A, B, returns the side length of the largest square that 
		we can obtain. If it is not possible to create any square, the function should return 0.
		
		Examples :
		a. Given A = 10, B =21, the function should return 7. We can split the second stick into three sticks of length 7 and shorten the first stick by 3.
		b. Given A = 13, B=11, the function should return 5. We can cut two sticks of length 5 from each of the given sticks.
		c. Given A = 2, B = 1, the function should return 0. it is not possible to make any square from the give sticks.
		d. Give A = 1, B = 8, the function should return 2. We can cut stick B into four parts.
	
	
	*/
	private static int solution(int A, int B) {
		if (A > B) {
			//swap(A, B);
			int temp = A;
			A = B;
			B = temp;
		}
		if (A + B <= 3)
			return 0;

		if (B / 4 > A)
			return B / 4;
		else if (B / 3 >= A)
			return A;
		else if (B / 3 < A)
			return Math.max(A / 2, B / 3);
		return 0;
	}

}
