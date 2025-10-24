package Ripple;

import java.util.Arrays;

public class PeopleAndBoat {

	public static void main(String[] args) {// Basic tests
        runTest(new int[]{3, 2, 2, 1}, 3, 3);        // example: (1+2), (2), (3)
        runTest(new int[]{3, 5, 3, 4}, 5, 4);        // none can pair with 5, result 4

        // Additional / edge cases
        runTest(new int[]{1, 1, 1, 1}, 2, 2);        // pairs of (1+1), total 2 boats
        runTest(new int[]{2, 2, 2, 2, 2}, 3, 5);     // each must go alone (limit small)
        runTest(new int[]{70, 50, 80, 50}, 100, 3);  // (50+50), (70), (80)
        runTest(new int[]{100}, 100, 1);             // single person equals limit
        runTest(new int[]{}, 100, 0);                // empty input
        runTest(new int[]{1, 2, 3, 4, 5}, 5, 4);     // (1+4), (2+3), (5) -> 3 boats? actually 4 (explain below)
    }
	
	// helper to run and print test results
    public static void runTest(int[] people, int limit, int expected) {
        int result = numRescueBoats(people.clone(), limit);
        System.out.printf("people=%s, limit=%d -> boats=%d (expected=%d)%n",
                Arrays.toString(people), limit, result, expected);
    }
	
    /*
    Algo: 
    1. If we sort people by weight, the lightest and heaviest are the best 
    candidates to pair: pairing the heaviest with the lightest gives the 
    best chance to fit two in one boat.

    2. Use two pointers on the sorted array:

    	- i at the lightest (start), j at the heaviest (end).

    	- If people[i] + people[j] <= limit then pair them (use one boat), advance i++, j--.

    	Otherwise the heaviest people[j] must go alone: use one boat and do j--.	

    3. Count boats until i > j.

    This greedy is optimal: whenever the heaviest can be paired, pairing it with the lightest 
    frees the most remaining capacity for other pairings. Sorting + two-pointer yields an efficient solution.

    
    
    Complexity:
    Time: O(n log n) for sorting + O(n) for two-pointer = O(n log n).
    Space: O(1) extra (or O(n) if sorting uses extra space), so O(1) additional beyond input.
     * */
    
	/*
     * Returns minimum number of boats to carry all people where each boat can carry
     * at most two people and weight limit per boat is 'limit'.
     */
	public static int numRescueBoats(int[] people, int limit) {
		if (people == null || people.length == 0) return 0;

        Arrays.sort(people);           // O(n log n)
        int i=0;
        int j= people.length-1;
        int boats = 0;
        
        while (i<=j) {
        	// Always try to pair heaviest (people[j]) with lightest (people[i])
        	if(people[i] + people[j] <= limit) {
        		i++;
        		j--;
        	} 
        	else {
        		// Can't pair: heaviest goes alone
                j--;
        	}
        	boats++; // one boat used in either case
        	
        }
        return boats;
	}
	

}

/*
 peoples and boat: there is an array of people, each people has a weight, 
 and there are unlimited number of boats, every boat has a weight threshold 
 to hold people, and for every boat it can hold at most two people, find the 
 minimum number of boats required to hold all people.
 */


