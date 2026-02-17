package SoFi;

import java.util.LinkedList;
import java.util.List;

public class FindTheWinnerOfTheCircularGame_1823_Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Time : O(N)
    // Space: O(1)
    public int findTheWinner(int n, int k) {

        int previousWinner = 0;
        for(int i=2; i<=n; i++) {
            // previousWinner = (previousWinner + shift) % i
            previousWinner = (previousWinner + k) % i; //
        }
        
        return previousWinner + 1; // convert 0-based to 1-based
    }

    // Other solution: Time O(N^2), Space: O(N)
    public int findTheWinner2(int n, int k) {
    // Initialize list of N friends, labeled from 1-N
        List<Integer> circle = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) {
            circle.add(i);
        }

        // Maintain the index of the friend to start the count on
        int startIndex = 0;

        // Perform eliminations while there is more than 1 friend left
        while (circle.size() > 1) {
            // Calculate the index of the friend to be removed
            int removalIndex = (startIndex + k - 1) % circle.size();

            // Erase the friend at removalIndex
            circle.remove(removalIndex);

            // Update startIndex for the next round
            startIndex = removalIndex;
        }

        return circle.getFirst();
    }

}
