package Snowflake;

public class CalculateAmountPaidInTaxes_2303_Easy {

	public static void main(String[] args) {
		int[][] brackets = { { 3, 50 }, { 7, 10 }, { 12, 25 } };
		int income = 10;
		double res = calculateTax(brackets, income);
		System.out.println("Epected: 2.65000, Actual: " + res);
	}

	/*
	 * Complexity Time complexity: O(n) Space complexity: O(1)
	 * 
	 * 
	 * Solution:
	 * https://leetcode.com/problems/calculate-amount-paid-in-taxes/discuss/2141006/
	 * JavaPython-3-Simple-O(n)-codes.
	 */
	public static double calculateTax(int[][] brackets, int income) {
		double tax = 0;
		int prev = 0;

		for (int[] bracket : brackets) {
			int upper = bracket[0];
			int percent = bracket[1];

			if (income >= upper) {
				tax += (upper - prev) * percent / 100d;
				prev = upper;
			} else {
				tax += (income - prev) * percent / 100d;
				return tax;
			}
		}
		return tax;
	}

}
