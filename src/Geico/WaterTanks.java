package Geico;
public class WaterTanks {

	
	/*
	 	Suppose you have an array of houses "-H-H-H", each house needs 
	 	to be close to a water tank on either its left or right side. 
	 	How many water tanks do you need? Water Tanks can only go where 
	 	there is a dash. 
	 	
	 	Ex. "-H-H-H" -> 
	 		solution: 2 tanks needed, one option is '-HTHTH', or 'TH-HTH' 
	 */
	
	// Time: O(N), We scan once.
	// Space: O(N), We use a char array.
    // Returns minimum number of tanks needed
    // Returns -1 if impossible
    public static int minTanks(String s) {

        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] arr = s.toCharArray();
        int n = arr.length;

        int count = 0;

        // Traverse from left to right
        for (int i = 0; i < n; i++) {

            // If current is a house
            if (arr[i] == 'H') {

                // Case 1: Already covered by left tank
                if (i - 1 >= 0 && arr[i - 1] == 'T') {
                    continue;
                }

                // Case 2: Try placing tank on right
                if (i + 1 < n && arr[i + 1] == '-') {
                    arr[i + 1] = 'T';
                    count++;
                }

                // Case 3: Try placing tank on left
                else if (i - 1 >= 0 && arr[i - 1] == '-') {
                    arr[i - 1] = 'T';
                    count++;
                }

                // Case 4: Impossible
                else {
                    return -1;
                }
            }
        }

        return count;
    }

    // Test cases
    public static void main(String[] args) {

        test("-H-H-H");      // 2
        test("H-H");         // 1
        test("H");           // -1
        test("-H--H-");      // 2
        test("----");        // 0
        test("H-H-H-H");     // 2
        test("-H-H--H-H-");  // 3
        test("");            // 0
        test(null);          // 0
    }

    private static void test(String s) {
        System.out.println("Input: " + s +
                " -> Tanks: " + minTanks(s));
    }
}