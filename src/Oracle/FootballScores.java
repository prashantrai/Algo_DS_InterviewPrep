package Oracle;
import java.util.Arrays;

public class FootballScores {
	
	/*
	 Time: Sort teamA	O(n log n)
		Binary search for each teamB	O(m log n)
		Total	O((n + m) log n)
		
	 Space: 
	 	O(1) (excluding output)
	 * */

    public static int[] counts(int[] teamA, int[] teamB) {
        // Sort teamA to enable binary search
        Arrays.sort(teamA);

        int[] result = new int[teamB.length];

        // For each match of teamB
        for (int i = 0; i < teamB.length; i++) {
            result[i] = countLessThanOrEqual(teamA, teamB[i]);
        }

        return result;
    }

    
    // Returns count of elements <= target
    private static int countLessThanOrEqual(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // +1 because index is zero-based
        return answer + 1;
    }
}


/*
 	Given two arrays teamA and teamB, representing the goals scored by two football 
 	teams in their respective matches, for each match of team B, compute the total 
 	number of matches of team A where team A has scored less than or equal to the 
 	number of goals scored by team B in that match.

	Return an array of size m (where m is the length of teamB), where the i-th element 
	represents the total number of elements from teamA satisfying teamA[j] <= teamB[i] for all valid j.
	
	
	What the problem statement literally says?

	“For each match of team B, compute the total number of matches of team A  had to play
	where team A has scored less than or equal to the number of goals scored by team B in that match.”
	
	We need to find for each matched of B, so, there will an array of values in result.
 * */
 