import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parking_Dilemma {

	public static void main(String[] args) {
		int[] cars = { 2, 10, 8, 17 };
		int k = 3;
		System.out.println(ParkingDilemma(cars, k));

	}

	/**
	 * @param cars: integer array of length denoting the parking slots where cars
	 *              are parked
	 * @param k:    integer denoting the number of cars that have to be covered by
	 *              the roof
	 * @return: return the minium length of the roof that would cover k cars
	 */
	public static int ParkingDilemma(int[] cars, int k) {
		// write your code here
		Arrays.sort(cars);
		int len = cars.length;
		int min = cars[k - 1] - cars[0];
		for (int i = 1; i <= len - k; i++) {
			// if (min > cars[k-1+i] - cars[i]) {
			// min = cars[k-1+i] - cars[i];
			// }
			min = Math.min(min, cars[k - 1 + i] - cars[i]);
		}
		return min + 1;
	}

	// https://leetcode.com/discuss/interview-question/402014/IBM-or-OA-2019-or-Parking-Dilemma
	public static long ParkingDilemma(List<Long> cars, int k) {
		if (cars.size() == 0 || k < 0) {
			return 0;
		}

		Collections.sort(cars);
		long minDist = Long.MAX_VALUE;

		for (int i = 0; i <= cars.size() - k; i++) {
			minDist = Math.min(minDist, cars.get(i + k - 1) - cars.get(i));
		}

		return minDist + 1;
	}
}
