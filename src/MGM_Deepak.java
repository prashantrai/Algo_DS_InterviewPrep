import java.util.Arrays;
import java.util.List;

public class MGM_Deepak {

	public static void main(String[] args) {

		List<Integer> a = Arrays.asList(7, 5, 9);
		List<Integer> b = Arrays.asList(13, 1, 4);
		int d = 3;
		
		int res = comparatorValue(a, b, d);
		System.out.println("Expeted: 1, Actual: " + res);
	}
	
	/* [https://leetcode.com/discuss/interview-question/1519415/red-hat-se-intern-oa#:~:text=Reductor%20Array,than%20or%20equal%20to%20d.]
	 Reductor Array
		For two integer arrays, the comparator value is the total number of elements in the 
		first array such that there exists no integer in the second array with an absolute difference 
		less than or equal to d. Find the comparator value. 
		
		For example there are two arrays a = [7, 5, 9], 
										 b = [13, 1, 4], and the integer d = 3. 
		
		The absolute difference of a[0] to b[0] = |7 – 13| = 6, 
											b[1] = |7 - 1| = 6, 
											and b[2] = |7 - 4| = 3, 
		to recap, the values are 6,6,3. 
		
		In this case, the absolute difference with b[2] is equal to d = 3, so this element 
		does not meet the criterion. 
		
		A similar analysis of a[1] = 5 yields absolute differences of 8, 4, 1 
		and of a[2] 9 yields 4, 8, 5. 
		
		The only element of array a that has an absolute difference with each element of b that is 
		always greater than d is element a[2], thus the comparator value is 1.
		
		Function Description: Complete the function comparatorValue in the editor below.
		The function must return an integer that denotes the comparator value of the arrays.
		
		ComparatorValue has the following parameter(s): a[a[O],..a[n – 1]]: an array of 
		integers b[b[0],..b[m – 1]]: an array of integers d: an integer 
	 * */

	
	public static int comparatorValue(List<Integer> a, List<Integer> b, int d) {
		// Write your code here

		int count = 0;
		int tempCount = 0;
		for (int i : a) {
			for (int j : b) {
				int absDiff = Math.abs(i - j);
				if (absDiff > d) {
					tempCount += 1;
				}
			}
			if (tempCount == d) {
				count++;
			}
			tempCount = 0;
		}
		return count;

	}

	
	public static int comparatorValue2(List<Integer> a, List<Integer> b, int d) {
		// Write your code here

		int count = 0;
		int tempCount = 0;
		for (int i : a) {
			for (int j : b) {
				int absDiff = Math.abs(i - j);
				if (absDiff <= d) {
					tempCount += 1;
				}
			}
			if (tempCount == 0) {
				count += 1;
			}
			tempCount = 0;
		}
		return count;

	}

}
